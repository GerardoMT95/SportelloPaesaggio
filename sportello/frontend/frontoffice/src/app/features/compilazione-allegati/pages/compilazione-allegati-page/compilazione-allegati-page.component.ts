import { Allegati } from './../../../../shared/models/models';
import { Component, EventEmitter, OnInit, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { AttivitaDaEspletareEnum, Fascicolo, TipoContenuto } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { paths } from 'src/app/app-routing.module';
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
  doValidate: string="";
  sportelloConfig: SportelloConfigBean;
  
  
  constructor(public route: ActivatedRoute,
              public router: Router,
              public fb: FormBuilder,
              private fascicoloStore: FascicoloStore,
              private fascicoloService: FascicoloService,
              private loadingService: LoadingService,
              private dominioService: HttpDominioService,
              public dialogService: DialogService)
  {
    super(dialogService, fb, router,route);
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
    this.doValidate = this.route.snapshot.queryParamMap.get('doValidate');
    this.tabFormNames = ['documentazioneAmministrativa', 'documentazioneTecnica'];
  }

  ngOnInit()
  {
    this.fascicoloStore.setBreadcrumbs([{ label: 'Dettaglio' }, { label: 'Allegati' }]);
    this.caricamentoDati();
  }

  caricamentoDati()
  {
    //step di caricamento fascicolo e SelectOption
    this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codiceFascicolo;
    let obsFascicolo$ = this.fascicoloService.getFascicolo(this.fascicolo);
    this.loadingService.emitLoading(true);
    combineLatest([obsFascicolo$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([f]) =>
      {
        this.fascicolo = f.payload;
        this.loadingService.emitLoading(false);
        //dopo aver ottenuto risposta dei dati del fascicolo parto a chiedere il resto delle info legate al tipo procedimento
        this.caricamentoAltro(this.fascicolo.enteDelegato,this.fascicolo.tipoProcedimento);
        //this.afterLoadFascicolo();
        //ora prendo i dati di dominio dipendenti dal tipoProcedimento
        //this.caricamentoTipi(this.fascicolo.tipoProcedimento);
        
      });
  }

  private caricamentoAltro(enteDelegato:string,tipoProcedimento: string){
    let hasPagamenti$=this.fascicoloService.hasPagamentoIntegrato(this.fascicolo.enteDelegato);
    let tipiContenuto$=this.dominioService.getTipiContenuto(parseInt(tipoProcedimento));
    let sportelloConfig$=this.dominioService.getSportelloConfig(this.fascicolo.id);
    this.loadingService.emitLoading(true);
    combineLatest([hasPagamenti$,tipiContenuto$,sportelloConfig$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([hasPag,tipiCont,sportelloConfig])=>{
        this.loadingService.emitLoading(false);
        this.enteConPagamenti=hasPag.payload; 
        this.tipiContenuto = tipiCont; 
        this.sportelloConfig = sportelloConfig; 
        this.afterLoadFascicolo();
      });
  }


  afterLoadFascicolo()
  {
    this.mainForm = this.fb.group({
      valida: [false],
      esoneroOneri:[this.fascicolo.esoneroOneri?true:false],
      esoneroBollo:[this.fascicolo.esoneroBollo?true:false]
    });
    this.mainForm.valueChanges.subscribe(val =>{});
    //se nella rotta ho la validazione, la lancio
    if (this.doValidate == "true") {
      this.doValidate = "";
      this.doValida();
    }
    this.loadedFascicolo.next(true);
  }
  

  get metadata()
  {
    return this.fascicoloStore.state.meta;
  }

  salva()
  {
    this.saveOperation();
  }

  /**
   *salvataggio della sola parte di form prevista qui ovvero esoneri pagamento 
   * @param batch se non deve dare messaggio di successo
   */
  saveOperation(batch?:boolean,callback?:any)
  {
    const allegati = this.mainForm.value;
    this.fascicoloStore.editFascicolo({ ...this.fascicolo, allegati });
    this.loadingService.emitLoading(true);
    this.fascicoloService.updateEsoneriPagamento(this.fascicolo.id,allegati.esoneroBollo,allegati.esoneroOneri).then(()=>{
      if(!batch){
        this.displaySuccesso();
      }
      if(callback){
        console.log("doing callback");
        callback();
      }
      this.mainForm.markAsUntouched();
      this.mainForm.markAsPristine();
    }).finally(()=>this.loadingService.emitLoading(false));
  }

  displaySuccesso() {
    this.dialogService.showDialog(true, 'generic.operazioneOk', 'generic.success',
    DialogButtons.CHIUDI,
    (buttonID: number): void =>{},
    DialogType.SUCCESS,
    null);
  }

  
  checkIfViewMode()
  {
    if (this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.IN_PREISTRUTTORIA)
    {
      this.mainForm.disable();
    }
  }

  get allegati(): Allegati { return this.fascicolo ? this.fascicolo.allegati : {}}

  
  indietro(){
    if(this.mainForm.touched){
      this.dialogService.showDialog(true, 'generic.abbandonaModifiche', 'generic.info',
      DialogButtons.OK_CANCEL,
      (buttonID: number): void =>
      {
        if (buttonID === ButtonType.OK_BUTTON)
        {
          this.router.navigate([paths.details(this.codiceFascicolo)]);    
        }
      },
      DialogType.ERROR,
      null)
    }else{
      this.router.navigate([paths.details(this.codiceFascicolo)]);
    }
  }

  public valida(){
    this.saveOperation(true,this.doValida.bind(this));
  }

  public actionComplete($event){
  }

  private doValida(){
    this.loadingService.emitLoading(true);
    this.fascicoloService.validaAllegati(this.fascicolo.codicePraticaAppptr, this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.dialogService.showDialog(true,
            'VALIDATION.VALIDATION_SUCCESS_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
            DialogButtons.CHIUDI, null, DialogType.SUCCESS);
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.dialogService.showDialog(
          true,
          error.message,
          "Errore",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }
}
