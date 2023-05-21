import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { Component, EventEmitter, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { combineLatest, Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { fromAllegatoToBasicFile } from 'src/app/shared/functions/ObjectUtils';
import { BasicFile } from 'src/app/shared/models/allegati.model';
import { Fascicolo, RequestAllegato } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DichiarazioniService } from '../../services/dichiarazioni/dichiarazioni.service';
import { AllegatoService } from './../../../../shared/services/allegato.service';
import { LocalizzazioneService } from './../../services/localizzazione/localizzazione.service';



@Component({
  selector: 'app-compilazione-istanza-page',
  templateUrl: './compilazione-istanza-page.component.html',
  styleUrls: ['./compilazione-istanza-page.component.scss']
})
export class CompilazioneIstanzaPageComponent extends AbstractInputPage implements OnInit, OnDestroy
{
  dropdowns: { [id: string]: Observable<SelectOption[]> };
  tipoDocumentoOptions: SelectOption[];
  titolaritaInQualitaDi: SelectOption[];
  titolaritaInQualitaDiAltroTit: SelectOption[];
  allDisclaimerOptions: SelectOption[]; //sono tutte.. in linked ho il tipoProcedimento

  loadedFascicolo = new EventEmitter<boolean>();
  fascicolo: Fascicolo;
  activeIndex: number; //indice del pannello
  unsubscribe$ = new Subject<void>();
  //COMPILA_DOMANDA = AttivitaDaEspletareEnum.COMPILA_DOMANDA;
  files: BasicFile[] = [];

  public validation: boolean = false;
  public mostraDialog: boolean = false;
  public invalidFields: Array<any> = new Array<any>();

  constructor(private route: ActivatedRoute,
    public router: Router,
    public fb: FormBuilder,
    private httpDominio: HttpDominioService,
    private fascicoloService: FascicoloService,
    public dialogService: CustomDialogService,
    public loadingService: LoadingService,
    private dichiarazioniService: DichiarazioniService,
    private allegatoService: AllegatoService,
    private locSvc: LocalizzazioneService,
    private shared: DataService)
  {
    super(dialogService, fb, router);
    //console.log('codice fascicolo: ', this.codiceFascicolo);
    this.tabFormNames = [
      'richiedente',
      'dichiarazioni',
      'altriTitolari',
      'localizzazione',
      'tecnicoIncaricato'
    ];
    this.loadingService.emitLoading(true);
  }
  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit()
  {
    this.codiceFascicolo = this.shared.codicePratica;
    this.mainForm = this.fb.group({
      valida: [false],
      altriTitolari: this.fb.array([])
    });
    /** TODO. Delete this when everything is tested. */
    this.mainForm.disable();
    /* this.mainForm.valueChanges.subscribe((val) => this.checkIfViewMode()); */
    this.caricamentoDati();
    this.locSvc.salvaFascicolo$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(todo =>
      {
        this.saveOperation(true);
      });
  }

  caricamentoDati()
  {
    //step di caricamento fascicolo e SelectOption
    this.loadingService.emitLoading(true);
    this.dropdowns = {};
    let titRichiedente$ = this.httpDominio.getDominio('titolaritaInQualitaDi');
    let titAltro$ = this.httpDominio.getDominio('titolaritaInQualitaDiAltroTit');
    let tipoDocumento$ = this.httpDominio.getDominio('tipoDocumento');
    let allDisclaimer$ = this.httpDominio.getDisclaimerPratica(this.codiceFascicolo);
    /* this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codiceFascicolo; */
    this.loadingService.emitLoading(true);
    let obsFascicolo$ = this.fascicoloService.callFindByCode(this.codiceFascicolo);
    combineLatest([titRichiedente$, titAltro$, tipoDocumento$, allDisclaimer$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([titQual, titAltro, tipoDoc, allDisc]) =>
    {
      this.fascicolo = this.shared.fascicolo;
      console.log("Fascicolo: ", this.fascicolo);
      if (this.fascicolo.istanza &&
          this.fascicolo.istanza.dichiarazioni &&
          this.fascicolo.istanza.dichiarazioni.allegato)
      {
        let tmp = fromAllegatoToBasicFile(this.fascicolo.istanza.dichiarazioni.allegato);
        tmp.formatoFile = "Dichiarazione d'assenso";
        tmp.type = "DICHIARAZIONI_ASSENSO";
        this.files = [tmp];
      }
      this.titolaritaInQualitaDi = titQual;
      this.titolaritaInQualitaDiAltroTit = titAltro;
      this.tipoDocumentoOptions = tipoDoc;
      this.allDisclaimerOptions = allDisc;
      this.afterLoadFascicolo();
      this.loadedFascicolo.next(true);
      this.loadingService.emitLoading(false);
    });
  }

  prelevaDisclaimer()
  {
    let retArr = [];
    this.allDisclaimerOptions.forEach(
      selectOption =>
      {
        if (selectOption.linked == this.fascicolo.tipoProcedimento)
        {
          let ret = {};
          ret['value'] = selectOption.value;
          ret['label'] = selectOption.description;
          retArr.push(ret);
        }
      });
    return retArr;
  }

  initFormValues(fas: Fascicolo) { }

  afterLoadFascicolo()
  {
    ViewMapper.mapObjectToForm(this.mainForm, this.fascicolo);
    this.mainForm.disable();
  }

  saveOperation(batch: boolean = false)
  {

  }

  salva() { }

  public validateSpecificRules(): boolean
  {
    /*  reset no of Errors  */
    /*this.numberOfErrorsOnthePage['privacyAccettata'] = 0;
    const privataAccetata: boolean = (this.mainForm.get('privacyAccettata') as FormControl).value;
    if (!privataAccetata) {
      this.numberOfErrorsOnthePage['privacyAccettata'] = 1;
      return false;
    }*/
    return true;
  }

  private subscribeForAltroTitolareChange()
  {
    this.mainForm
      .get('dichiarazioni')
      .get('diAvereTitolo')
      .get('imobileInteresato')
      .valueChanges.subscribe((val) =>
      {
        console.log('Imobile interesanto value:' + val);
      });
  }

  checkIfViewMode() { this.mainForm.disable(); }

  private creaContainerDichiarazioneAssenso(event: any): any
  {
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.tipiContenuto = [500]; // id tipologia dichiarazione d'assenso
    requestAllegato.nomeContenuto = "dichiarazione d'assenso";
    let container = {
      requestAllegato: requestAllegato,
      file: event.files[0]
    }
    return container;
  }

  /*private creaContainerShapeFile(event: any): any {
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.tipiContenuto = [600]; // id tipologia shape file
    requestAllegato.nomeContenuto = "shape file";
    let container = {
      requestAllegato: requestAllegato,
      file: event.file
    }
    return container;
  }*/

  public downloadFile(event: any): void
  {
    this.loadingService.emitLoading(true);
     this.allegatoService.downloadAllegatoFascicolo(event.id,this.fascicolo.id, '/istruttoria/allegati/download.pjson').subscribe(
      response =>
      {
        var blob = new Blob([response.body], { type: response.body.type });
        this.allegatoService.downloadElemento(blob, event.nome);
        this.loadingService.emitLoading(false);
      },
      error =>
      {
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
        this.loadingService.emitLoading(false);
      }
    );
  }

  public deleteFile(): void
  {
    this.loadingService.emitLoading(true);
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.allegatoId = this.files[0].id;
    this.dichiarazioniService.deleteFile(requestAllegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.files = [];
          this.loadingService.emitLoading(false);
        }
      },
      error =>
      {
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
        this.loadingService.emitLoading(false);
      }
    );
  }

  public focusTab(indiceTab: number): void
  {
    this.mostraDialog = false;
    this.activeIndex = indiceTab;
  }

  altreOpzioniValide(disclaimer: SelectOption[]): boolean
  {
    const checked = this.mainForm.get("dichiarazioni").get("altreOpzioni").value;
    let numChecked = 0;
    if (checked && Array.isArray(checked) && checked.length > 0)
    {
      for (let i = 0; i < checked.length; i++)
      {
        disclaimer.findIndex(el => el.value == checked[i]) >= 0 ? numChecked++ : null;
      }
      if (numChecked >= disclaimer.length)
      {
        return true;
      }
    }
    return false;
  }

  public indietro(): void { 
    this.router.navigate(['gestione-istanze', this.codiceFascicolo,'istanza-presentata']); 
  }
}
