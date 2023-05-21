import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { Allegati, TipoProcedimento } from './../../../../shared/models/models';
import { Component, EventEmitter, OnInit, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { Fascicolo, TipoContenuto } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { SportelloConfigBean } from 'src/app/shared/components/model/model';


@Component({
  selector: 'app-compilazione-allegati-page',
  templateUrl: './compilazione-allegati-page.component.html',
  styleUrls: ['./compilazione-allegati-page.component.scss']
})
export class CompilazioneAllegatiPageComponent extends AbstractInputPage implements OnInit
{

  // mainForm: FormGroup;
  //codiceFascicolo: string;
  //elencoAllegatiData;
  fascicolo: Fascicolo;
  activeIndex: number; //indice del pannello
  unsubscribe$ = new Subject<void>();
  loadedFascicolo = new EventEmitter<boolean>();
  tipiContenuto: TipoContenuto[];
  enteConPagamenti:boolean=false;
  sportelloConfig: SportelloConfigBean;


  constructor(public router: Router,
              public fb: FormBuilder,
              private fascicoloStore: FascicoloStore,
              private fascicoloService: FascicoloService,
              private loadingService: LoadingService,
              private dominioService: HttpDominioService,
              public dialogService: CustomDialogService,
              private shared: DataService)
  {
    super(dialogService, fb, router);
    this.tabFormNames = ['documentazioneAmministrativa', 'documentazioneTecnica'];
    this.loadingService.emitLoading(true);
  }


  ngOnInit()
  {
    this.codiceFascicolo = this.shared.codicePratica;
    this.caricamentoDati();
  }

  caricamentoDati()
  {
    //step di caricamento fascicolo e SelectOption
    this.loadingService.emitLoading(true);
    this.fascicolo = this.shared.fascicolo;
    this.afterLoadFascicolo();
    this.caricamentoTipi(this.fascicolo.tipoProcedimento);
    this.caricamentoAltro();
    
  }

  public caricamentoTipi(tp: TipoProcedimento): void
  {
    this.loadingService.emitLoading(true);
    let sportelloConfig$=this.dominioService.getSportelloConfig(this.fascicolo.id);
    let tipiContenuto$=this.dominioService.getTipiContenuto(parseInt(tp));
    this.loadingService.emitLoading(true);
    combineLatest([tipiContenuto$,sportelloConfig$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([tipiCont,sportelloConfig])=>{
        this.loadingService.emitLoading(false);
        this.tipiContenuto = tipiCont; 
        this.sportelloConfig = sportelloConfig; 
        this.loadedFascicolo.next(true);
      });

    /*this.dominioService.getTipiContenuto(parseInt(tp)).toPromise().then(ret =>
    {
      this.tipiContenuto = ret;
      this.loadedFascicolo.next(true);
      this.loadingService.emitLoading(false)
    });*/
  }

  afterLoadFascicolo()
  {
    this.mainForm = this.fb.group({
      valida: [false],
      esoneroOneri:[this.fascicolo.esoneroOneri?true:false],
      esoneroBollo:[this.fascicolo.esoneroBollo?true:false],
    });
    this.mainForm.valueChanges.subscribe(val =>
    {
      console.log('ALLEGATI FORM:', val)
    });
  }

  get metadata()
  {
    return this.fascicoloStore.state.meta;
  }

  annullaDocumentFormArray(documentFormArray: FormArray)
  {
    for (let i = 0; i < documentFormArray.length; i++)
    {
      const fg = (documentFormArray.at(i) as FormGroup);
      (fg.get('nome') as FormControl).setValue('');
      (fg.get('data') as FormControl).setValue(null);
    }
  }

  public indietro(): void { this.router.navigate(['gestione-istanze', this.codiceFascicolo,'istanza-presentata']); }

  checkIfViewMode(): void { this.mainForm.disable(); }
  get allegati(): Allegati { return this.fascicolo ? this.fascicolo.allegati : {}}

  private caricamentoAltro(){
    this.loadingService.emitLoading(true);
    this.fascicoloService.hasPagamentoIntegrato(this.fascicolo.enteDelegato).subscribe(resp=>{
      this.loadingService.emitLoading(false);
      this.enteConPagamenti=resp.payload;
    });
  }
}
