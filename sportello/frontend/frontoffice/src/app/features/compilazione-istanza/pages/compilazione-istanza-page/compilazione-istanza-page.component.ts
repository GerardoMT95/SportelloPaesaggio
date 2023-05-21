import { Component, OnDestroy, OnInit, AfterContentInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { combineLatest, Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { clearAllValidators, printFormErrors, updateAllFormValidity } from 'src/app/shared/functions/generic.utils';
import { fromAllegatoToBasicFile } from 'src/app/shared/functions/ObjectUtils';
import { BasicFile } from 'src/app/shared/models/allegati.model';
import { AttivitaDaEspletareEnum, Fascicolo } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { IstanzaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DichiarazioniService } from '../../services/dichiarazioni/dichiarazioni.service';
import { Istanza, RequestAllegato, Richiedente } from './../../../../shared/models/models';
import { AllegatoService } from './../../../../shared/services/allegato.service';
import { LocalizzazioneService } from './../../services/localizzazione/localizzazione.service';


@Component({
  selector: 'app-compilazione-istanza-page',
  templateUrl: './compilazione-istanza-page.component.html',
  styleUrls: ['./compilazione-istanza-page.component.scss'],
  providers: [IstanzaFormService]
})
export class CompilazioneIstanzaPageComponent extends AbstractInputPage implements OnInit, OnDestroy {
  dropdowns: { [id: string]: Observable<SelectOption[]> };
  tipoDocumentoOptions: SelectOption[];
  titolaritaInQualitaDi: SelectOption[];
  titolaritaInQualitaDiAltroTit: SelectOption[];
  allDisclaimerOptions: SelectOption[]; //sono tutte.. in linked ho il tipoProcedimento
  sezioniCatastali: SelectOption[];

  loadedFascicolo = new Subject<boolean>();
  fascicolo: Fascicolo;
  activeIndex: number; //indice del pannello
  unsubscribe$ = new Subject<void>();
  COMPILA_DOMANDA = AttivitaDaEspletareEnum.COMPILA_DOMANDA;
  files: BasicFile[] = [];
  doValidate = "";



  private validation: boolean = false;
  public mostraDialog: boolean = false;
  public invalidFields: Array<any> = new Array<any>();
  public subformBuilded: boolean = false;

  public tipoAziendaOptions: SelectOption[];

  constructor(public route: ActivatedRoute,
    public router: Router,
    public fb: FormBuilder,
    private httpDominio: HttpDominioService,
    private fascicoloStore: FascicoloStore,
    private fascicoloService: FascicoloService,
    public dialogService: DialogService,
    public loadingService: LoadingService,
    private translateService: TranslateService,
    private dichiarazioniService: DichiarazioniService,
    private allegatoService: AllegatoService,
    private locSvc: LocalizzazioneService,
    private istanzaFormService: IstanzaFormService
  ) {
    super(dialogService, fb, router,route);
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
    this.doValidate = this.route.snapshot.queryParamMap.get('doValidate');
    //console.log("param map:",this.route.snapshot.queryParamMap);
    this.tabFormNames = [
      'richiedente',
      'dichiarazioni',
      'altriTitolari',
      'localizazione',
      'tecnicoIncaricato'
    ];
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  onFineBuildForm() {
    //attendo che tutti i componenti child hanno buildato il loro subform  
    //prima di lanciare la validazione per poter visualizzare il messaggio con i pannelli con campi invalidi
    combineLatest([this.istanzaFormService.richiedenteFormComplete$,
    this.istanzaFormService.dichiarazioniFormComplete$,
    this.istanzaFormService.altriTitolariFormComplete$,
    this.istanzaFormService.localizzazioneFormComplete$,
    this.istanzaFormService.tecnicoIncaricatoFormComplete$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([r, d, a, l, t]) => {
        this.loadingService.emitLoading(true);
        if (this.doValidate == "true") {
          //setTimeout(() => {
          this.doValidate = "";
          this.valida(true);
          //}, 100);
        }
        if (this.toDisableEditFascicolo(this.fascicolo)) {
          this.mainForm.disable();
        }
        this.loadingService.emitLoading(false);
        this.subformBuilded=true;
      }
      );
  }

  ngOnInit() {
    this.fascicoloStore.setBreadcrumbs([{ label: 'Dettaglio' }, { label: 'Istanza' }]);
    this.onFineBuildForm();
    this.mainForm = this.fb.group({
      valida: [false],
      altriTitolari: this.fb.array([])
    });
    /** TODO. Delete this when everything is tested. */
    //this.mainForm.valueChanges.subscribe((val) => this.checkIfViewMode());
    this.caricamentoDati();
    this.locSvc.salvaFascicolo$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(todo => {
        this.saveOperation(true);
      }
      );
  }

  caricamentoDati() {
    //step di caricamento fascicolo e SelectOption
    this.loadingService.emitLoading(true);
    this.dropdowns = {};
    let titRichiedente$ = this.httpDominio.getDominio('titolaritaInQualitaDi');
    let titAltro$ = this.httpDominio.getDominio('titolaritaInQualitaDiAltroTit');
    let tipoDocumento$ = this.httpDominio.getDominio('tipoDocumento');
    let allDisclaimer$ = this.httpDominio.getDisclaimerPratica(this.codiceFascicolo);
    let sezioniCatastali$ = this.httpDominio.getSezioniCatastali();
    this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codiceFascicolo;
    this.loadingService.emitLoading(true);
    let tipoAzienda$ = this.httpDominio.getTipiAzienda().subscribe(response =>{
      if(response.codiceEsito === CONST.OK){
        this.tipoAziendaOptions = response.payload;
      }
    })
    let obsFascicolo$ = this.fascicoloService.getFascicolo(this.fascicolo);
    combineLatest([obsFascicolo$, titRichiedente$, titAltro$, tipoDocumento$, allDisclaimer$,sezioniCatastali$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        ([f, titQual, titAltro, tipoDoc, allDisc,sezioniCatastali]) => {
          this.refreshFascicolo(f.payload);
          if(this.fascicolo.ruoloPratica == 'DE')
          {
            this.tabFormNames.splice(1, 0, 'delegato');
            if(this.mainForm && this.mainForm.controls && this.mainForm.controls.richiedente) 
              this.mainForm.controls.richiedente.clearValidators();
          }
          this.titolaritaInQualitaDi = titQual;
          this.titolaritaInQualitaDiAltroTit = titAltro;
          this.tipoDocumentoOptions = tipoDoc;
          this.allDisclaimerOptions = allDisc;
          this.sezioniCatastali = sezioniCatastali;
          this.loadedFascicolo.next(true);
          this.loadingService.emitLoading(false);
        }
      );
  }

  private refreshFascicolo(fascicolo:Fascicolo){
    this.fascicolo = fascicolo;
    if (this.fascicolo.istanza &&
      this.fascicolo.istanza.dichiarazioni &&
      this.fascicolo.istanza.dichiarazioni.allegato) {
      let tmp = fromAllegatoToBasicFile(this.fascicolo.istanza.dichiarazioni.allegato);
      tmp.labelType = "Dichiarazione d'assenso";
      tmp.type = "DICHIARAZIONI_ASSENSO";
      this.files = [tmp];
    }
    this.afterLoadFascicolo();
  }

  
  prelevaDisclaimer() {
    let retArr = [];
    this.allDisclaimerOptions.forEach(
      selectOption => {
        if (selectOption.linked == this.fascicolo.tipoProcedimento) {
          let ret = {};
          ret['value'] = selectOption.value;
          ret['label'] = selectOption.description;
          retArr.push(ret);
        }
      });
    return retArr;
  }

  initFormValues(fas: Fascicolo) { }

  afterLoadFascicolo() {
    ViewMapper.mapObjectToForm(this.mainForm, this.fascicolo, null);

    if (this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.IN_PREISTRUTTORIA) {
      this.mainForm.disable();
    }
    else if (this.mainForm.get('richiedente') && this.fascicolo.validatoRichiedente)
    {
      this.mainForm.get('richiedente').disable();
      this.mainForm.get('richiedente').clearValidators();
    }
  }

  saveOperation(batch: boolean = false,validaBe:boolean=false) {
    let rawDataFormMain = this.mainForm.value; //se alcuni formarray sono disabilitati non vengono considerati nel value.
    let altriTitFormArr=this.mainForm.get('altriTitolari') as FormArray;
    if(altriTitFormArr && altriTitFormArr.disabled){
      rawDataFormMain.altriTitolari=altriTitFormArr.getRawValue();
    }
    const istanza = this.lavoraIstanza(rawDataFormMain);
    let tmpFascicolo = this.fascicolo;
    //questi due campi sono gestiti in mainForm ma sono per la localizzazione
    tmpFascicolo.istanza = istanza;
    tmpFascicolo.hasShape = rawDataFormMain.hasShape;
    //
    tmpFascicolo.tipoSelezioneLocalizzazione = rawDataFormMain.tipoSelezioneLocalizzazione;
    this.loadingService.emitLoading(true);
    this.fascicoloService.updateFascicolo(tmpFascicolo).then(res => {
      this.loadingService.emitLoading(false)
        if(res.codiceEsito == CONST.OK)
        {
          this.fascicolo = res.payload;
          this.mainForm.markAsUntouched();
          this.mainForm.markAsPristine();
          this.afterLoadFascicolo();
          if (validaBe)
          {
            console.log("ok valida be", validaBe);
            this.validaBe();
          }
          if (!batch)
          {
            this.successSave();
          }
        }
       
      })
      .finally(() => {});
  }


  private validaRichiedenteBe() {
    this.loadingService.emitLoading(true);
    this.fascicoloService.validaRichiedente(this.fascicolo.codicePraticaAppptr).subscribe(response => {
      this.loadingService.emitLoading(false);
      if (response.codiceEsito == CONST.OK) {
        this.fascicolo.validatoRichiedente = true;
        this.dialogService.showDialog(true,
          'VALIDATION.VALIDATION_R_SUCCESS','VALIDATION.VALIDATION_DOCUMENT_TITLE', 
          DialogButtons.CHIUDI, null, DialogType.SUCCESS);
        this.mainForm.controls.richiedente.disable();
        this.validation=false;
      }
    });
  }


  private successSave(){
    this.dialogService.showDialog(
      true,
      'SAVED_SUCESSFULLY_MESSAGE',
      'SAVE_DOCUMENT_TITLE',
      DialogButtons.CHIUDI,
      (buttonID: string): void =>{},
      DialogType.SUCCESS,
      null 
    );
  }

  public  saveForRichiedente(callback?: any): void {
    let istanza = this.mainForm.getRawValue();
    let tmpFascicolo = this.fascicolo;
    /* let s = {...istanza.richiedente.societa};
    istanza.richiedente = { ...istanza.richiedente, 
                            cUo: s && s.codiceUo ? s.codiceUo : istanza.richiedente.codiceUo, 
                            societaCodiceFiscale: s && s.codiceFiscale ? s.codiceFiscale : istanza.richiedente.societaCodiceFiscale, 
                            codiceIpa: s && s.codiceIpa ? s.codiceIpa : istanza.richiedente.codiceIpa, 
                            societa: s && s.nome ? s.nome: istanza.richiedente.societa}; */
    istanza.societa = null;
    tmpFascicolo.istanza = istanza;
    this.loadingService.emitLoading(true);
    this.fascicoloService.updateRichiedente(tmpFascicolo).then(res => {
      this.loadingService.emitLoading(false);
      if (res.codiceEsito == CONST.OK) {
        this.fascicolo = res.payload;
        this.mainForm.markAsUntouched();
        this.mainForm.markAsPristine();
        this.afterLoadFascicolo();
        if (callback) {
          callback();
        }
      }
    });
  }

  salva() 
  {
    if(this.fascicolo.validatoRichiedente)
    {
      this.saveOperation();
    } 
    else{
       this.saveForRichiedente(this.successSave.bind(this));
    }
  }
  
  public validateSpecificRules(): boolean {
    return true;
  }

  private lavoraIstanza(values: any): Istanza {
    let instance: Istanza = values;
    instance.dichiarazioni.art142 = values.dichiarazioni.art142 ? values.dichiarazioni.art142.map(m => m.value) : [];
    instance.dichiarazioni.art136 = values.dichiarazioni.art136 ? values.dichiarazioni.art136.map(m => m.value) : [];
    //instance.dichiarazioni.art134 = values.dichiarazioni.art134 && values.dichiarazioni.art134.length === 1 ? values.dichiarazioni.art134[0] : null;
    //mappato con boolean
    return instance;
  }

  public uploadFile(event: any): void {
    this.loadingService.emitLoading(true);
    let container: any = this.creaContainerDichiarazioneAssenso(event);
    this.dichiarazioniService.uploadFile(container).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          let basicFile: BasicFile = {
            id: response.payload.id,
            type: "DICHIARAZIONI_ASSENSO",
            labelType: response.payload.descrizione,
            name: response.payload.nome,
            uploadDate: response.payload.data,
            path: response.payload.path,
            size: container.file.size,
            nome: response.payload.nome,
            data: response.payload.data,
            formatoFile: null,
            idCms: null,
            checksum: response.payload.checksum
          };
          this.files.push(basicFile);
          this.loadingService.emitLoading(false);
        }
      },
      error => {
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

  private creaContainerDichiarazioneAssenso(event: any): any {
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

  public downloadFile(event: any): void {
    this.loadingService.emitLoading(true);
    //this.dichiarazioniService.downloadFile(event.id).subscribe(
    this.allegatoService.downloadAllegatoFascicolo(event.id,this.fascicolo.id, '/allegati/download.pjson').subscribe(
      response => {
        var blob = new Blob([response.body], { type: response.body.type });
        this.allegatoService.downloadElemento(blob, event.name);
        this.loadingService.emitLoading(false);
      },
      error => {
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

  public deleteFile(): void {
    this.loadingService.emitLoading(true);
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.allegatoId = this.files[0].id;
    this.dichiarazioniService.deleteFile(requestAllegato).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.files = [];
          this.loadingService.emitLoading(false);
        }
      },
      error => {
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

  public validaRichiedenteFe(){
    this.validation = true;
    this.invalidFields = [];
    updateAllFormValidity(this.mainForm);
    printFormErrors(this.mainForm, "mainForm");
    if (!this.mainForm.get("richiedente").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 0,
        sezione: "Richiedente",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }else{
      this.saveForRichiedente(this.validaRichiedenteBe.bind(this));
    }
  }

  public validaRichiedente(): void
  {
    this.dialogService.showDialog(
      true,
      'REQUESTER_TAB.MESSAGGIO_VALIDA_RICHIEDENTE',
      'REQUESTER_TAB.VALIDA_RICHIEDENTE',
      DialogButtons.OK_CANCEL,
      (buttonID: number): void =>{
        if(buttonID==ButtonType.OK_BUTTON){
          this.saveForRichiedente(this.validaRichiedenteFe.bind(this));
        }
      },
      DialogType.INFORMATION,
      null 
    );
  }

  public valida(withoutSave?:boolean): void {
    this.validation = true;
    this.invalidFields = [];
    clearAllValidators(this.mainForm.get('richiedente') as FormGroup);
    updateAllFormValidity(this.mainForm);
    if (!this.mainForm.get("dichiarazioni").valid ||
      (this.mainForm.get("dichiarazioni").get("titolaritaEsclusivaIntervento").value === "N" && this.files.length === 0)
    ) {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: this.fascicolo.ruoloPratica == 'DE' ? 2 : 1,
        sezione: "Dichiarazioni",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    //qui devo effettuare il controllo sui disclaimer (altreOpzioni) tutti checked, non c'è verso di inserire un validator che non faccia casino su p-checkbox con raggruppamento in array
    if (!this.altreOpzioniValide(this.prelevaDisclaimer())) {
      this.mostraDialog = true;
      let el = this.invalidFields.find(el => el.sezione == "Dichiarazioni");
      if (!el) {
        this.invalidFields.push({
          indexPannello: 1,
          sezione: "Dichiarazioni",
          messaggi: ["Le clausole a fondo pagina sono tutte obbligatorie"]
        });
      } else {
        el.messaggi.push("Le clausole a fondo pagina sono tutte obbligatorie");
      }

    }
    if (this.mainForm.get("altriTitolari").status != "DISABLED" && !this.mainForm.get("altriTitolari").valid) {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 2,
        sezione: "Altro Titolare",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    let titolaritaEsclusivaIntervento = this.mainForm.get('dichiarazioni').get('titolaritaEsclusivaIntervento').value;

    //if(this.mainForm.get("altriTitolari").status != "DISABLED"){
    let el = this.invalidFields.find(el => el.sezione == "Altro Titolare");
    let formArrayTitolari = this.mainForm.get("altriTitolari") as FormArray
    //console.log("Valore di formArrayTitolari -> "+ formArrayTitolari.length);
    //console.log("Valore di titolaritaEsclusivaIntervento -> "+ titolaritaEsclusivaIntervento);
    if (!el) {
      if (titolaritaEsclusivaIntervento == 'S' && formArrayTitolari.length > 0) {
        // alert('altriTitolari deve essere vuoto')
        this.invalidFields.push({
          indexPannello: 2,
          sezione: "Altro Titolare",
          messaggi: ["AltriTitolari deve essere vuoto"]
        });
      } else if (titolaritaEsclusivaIntervento == 'N' && formArrayTitolari.length <= 0) {
        this.invalidFields.push({
          indexPannello: 2,
          sezione: "Altro Titolare",
          messaggi: ["Deve esistere almeno un titolare in altriTitolari"]
        });
      }
    } else {
      if (titolaritaEsclusivaIntervento == 'S' && formArrayTitolari.length > 0) {
        el.messaggi.push("AltriTitolari deve essere vuoto");
      }
      else if (titolaritaEsclusivaIntervento == 'N' && formArrayTitolari.length <= 0) {
        el.messaggi.push("Deve esistere almeno un titolare in altriTitolari");
      }
    }
    //valida localizzazione
    this.validaLocalizzazione(this.invalidFields, this.mainForm.controls)
    //valida tecnico incaricato
    if (!this.mainForm.get("tecnicoIncaricato").valid) {
      this.invalidFields.push({
        indexPannello: 4,
        sezione: "Tecnico Incaricato",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }

    if (this.invalidFields.length === 0){
      if(withoutSave){
        this.validaBe();
      }else{
        this.validaIstanza(this.fascicolo.codicePraticaAppptr, this.fascicolo.id);  
      }
    }else{
      this.mostraDialog = true;
    }
      
  }


  private validaBe(){
    this.loadingService.emitLoading(true);
    this.fascicoloService.validaIstanza(this.fascicolo.codicePraticaAppptr, this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          if (response.payload) { /* validazione ok */
            console.log(response.payload);
          }
          else { /* validazione ko */
            console.log(response.payload);
          }
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

  /**
   * 
   * @param codicePratica validazione be
   * @param idPratica 
   */
  private validaIstanza(codicePratica: string, idPratica: string): void {
    //faccio prima save e poi valido BE
    this.saveOperation(true,true);
  }

  private validaLocalizzazione(invalid: any[], form: any) {
    let localizzazioneComuni = form.localizzazione.get('localizzazioneComuni') as FormArray;
    if (localizzazioneComuni.length == 0) {
      let messaggi: string[] = new Array();
      messaggi.push('Nessun comune indicato in localizzazione!');
      invalid.push({ indexPannello: 3, sezione: 'Localizzazione', messaggi: messaggi });
    }
    else { //ci sono comuni...
      for (let i = 0; i < localizzazioneComuni.length; i++) {
        let isInterventoStrada = localizzazioneComuni.at(i).get('strade').value;
        let isInterventoStradaValido =
          isInterventoStrada &&
          localizzazioneComuni.at(i).get('indirizzo').value &&
          localizzazioneComuni.at(i).get('numCivico').value;
        let formParticelle = localizzazioneComuni.at(i).get('particelle') as FormArray;
        let hasParticelle = formParticelle.length > 0;
        let messaggi: string[] = new Array();
        if (hasParticelle && !formParticelle.valid)
          messaggi.push('Completare i dati delle particelle per : ' + localizzazioneComuni.at(i).get('comune').value);
        if (isInterventoStrada && !isInterventoStradaValido)
          messaggi.push('Inserire i dati dell\'area stradale: indirizzo e numero civico per: ' + localizzazioneComuni.at(i).get('comune').value);
        if (!isInterventoStrada && !hasParticelle)
          messaggi.push('Inserire i dati minimi per la localizzazione (almeno uno tra: particella, area stradale) per :' + localizzazioneComuni.at(i).get('comune').value);
        if (messaggi.length > 0)
          invalid.push({ indexPannello: 3, sezione: 'Localizzazione', messaggi: messaggi });
      }
    }
  }

  public focusTab(indiceTab: number): void {
    this.mostraDialog = false;
    this.activeIndex = indiceTab;
  }

  altreOpzioniValide(disclaimer: SelectOption[]): boolean {
    const checked = this.mainForm.get("dichiarazioni").get("altreOpzioni").value;
    let numChecked = 0;
    if (checked && Array.isArray(checked) && checked.length > 0) {
      for (let i = 0; i < checked.length; i++) {
        disclaimer.findIndex(el => el.value == checked[i]) >= 0 ? numChecked++ : null;
      }
      if (numChecked >= disclaimer.length) {
        return true;
      }
    }
    return false;
  }

  get validationValue(){
    return this.validation;
  }
}
