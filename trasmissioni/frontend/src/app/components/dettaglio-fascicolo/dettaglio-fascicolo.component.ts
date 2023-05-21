import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { BehaviorSubject, combineLatest, Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { UserService } from 'src/app/services/user.service';
import { SelectOption } from 'src/shared/components/select-field/select-field.component';
import { CONST } from 'src/shared/constants';
import { PlanFormField } from 'src/shared/models/plan-form.model';
import { StatoFascicolo } from 'src/shared/models/registration-status';
import { FascicoloFormConfig } from '../form-fascicolo/form-plan-config';
import { downloadFile, logInvalidControls, saveFileToLocalStorage } from '../functions/genericFunctions';
import { Correspondence } from '../model/correspondence.model';
import { RegistrationStatus } from '../model/entity/fascicolo.models';
import { GroupType } from '../model/enum';
import { ValidationBeanDto } from '../model/fascicolo.models';
import { ValidationMessage } from '../validation-modal/validation-modal.component';
import { requiredDependsOn } from '../validators/customValidator';
import { AllegatoCustomDTO } from './../model/entity/allegato.models';
import { DestinatarioComunicazioneDTO } from './../model/entity/corrispondenza.models';
import { InformazioniDTO } from './../model/entity/info.models';
import { LineaTemporaleDTO } from './../model/entity/informazioni.models';
import { TipiEQualificazioniDTO } from './../model/entity/intervento.models';
import { RichiedenteDTO } from './../model/entity/richiedente.models';

@Component({
  selector: 'app-dettaglio-fascicolo',
  templateUrl: './dettaglio-fascicolo.component.html',
  styleUrls: ['./dettaglio-fascicolo.component.css']
})

export class DettaglioFascicoloComponent implements OnInit, OnDestroy {
  // public dettaglioFascicolo: DettaglioFascicolo = new DettaglioFascicolo();
  public rsEnum=RegistrationStatus;
  public dettaglio: InformazioniDTO = new InformazioniDTO();
  public fascicoloForm: FormGroup;
  public esitoForm: FormGroup;//lo passo al child e poi lo gestisco qui per il salvataggio dei dati
  public activeIndex: number = 0;
  public stato: string = "";

  public cfErrorRichiedente: boolean = false; //true se cf errato
  public STATI_FASCICOLO = StatoFascicolo;
  public MAX_YEAR = CONST.MAX_YEAR;
  public maxLength: string = CONST.MAX_LENGTH_TEXTAREA.toString();
  public maxLengthInput: number = CONST.MAX_LENGTH_INPUT;
  public maxLengthTextArea: number = CONST.MAX_LENGTH_TEXTAREA;
  public disable: boolean = false; //per disattivare tutti i formControl
  public submitted: boolean = false;
  public it: any = CONST.IT;
  public id: string;

  private teqSubject: BehaviorSubject<TipiEQualificazioniDTO[]>;
  public tipiEQualificazioni$: Observable<TipiEQualificazioniDTO[]>;

  public shapeFile: AllegatoCustomDTO[] = [];
  fascicoloFormField: PlanFormField[];
  richiedenteFormField: PlanFormField[];
  interventoFormField: PlanFormField[];
  //localizzazioneFormField: PlanFormField[];
  particellaFormField: PlanFormField[];
  provvedimentoFormField: PlanFormField[];
  esitoFormFields: PlanFormField[];
  invalid: ValidationMessage[] = new Array(); //Sezione Campo errato 
  unsubscribe$ = new Subject<any>();
  showPage: "FASCICOLO"|"MAIL"|"IMPORT" = "FASCICOLO"; //per switchare i vari commponent (multitab,form di composizione mail,form di import)
  correspondenceList$: Observable<Correspondence[]>;
  fileType: SelectItem[];
  //sezione intervento
  tipiIntervento$: Observable<SelectItem[]>;
  public timeline: LineaTemporaleDTO;
  sezioniCatastali: SelectOption[];

  validazioneOkDialog: boolean;
  //oggetto utilizzato per l'alert
  public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
    };
  
  public mostraLetteraProvvedimento: boolean = false;
  public prosegui: boolean = false;
  public fileProvvedimentoFinaleEsitoVerifica: File;
  public allegatiTabEsito: Array<any> = [];

  public validGroup: boolean = null;
  public isMultiComune: boolean = false;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private router: Router,
              private loadingService: LoadingService,
              private promptDialogService: ShowPromptDialogService,
              private translateService: TranslateService,
              private autPaesSvc: AutorizzazioniPaesaggisticheService,
              private allegatiService: AllegatiService,
              private userService:UserService) {
              this.loadingService.emitLoading(true);
    //poi lo spengo quando ho caricato tutti i dati 
  }

  ngOnInit() {
    localStorage.clear();
    this.validGroup = this.userService.validGroup;
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAllDropDownData();
    this.fascicoloFormField = FascicoloFormConfig.planFormConfig(this.formBuilder);
    this.richiedenteFormField = FascicoloFormConfig.richiedenteFormField(this.formBuilder);
    this.particellaFormField = FascicoloFormConfig.particellaFormField(this.formBuilder);
    this.provvedimentoFormField = FascicoloFormConfig.provvedimentoFormField(this.formBuilder);
    this.esitoFormFields = FascicoloFormConfig.esitoFormField(this.formBuilder);
    this.interventoFormField = FascicoloFormConfig.interventoFormField(this.formBuilder);
    let sezioniCatastali$=this.autPaesSvc.sezioniCatastali();
    let isMultiComune$=this.userService.isMultiComune();
    
    combineLatest(this.autPaesSvc.findInformazioniGetAll(this.id),
      this.autPaesSvc.lineaTemporale(this.id),
      sezioniCatastali$
      ,isMultiComune$
      )
      .pipe(takeUntil(this.unsubscribe$)).subscribe
      (
        ([response, timeResponse,sezCatastali, isMultiComune]) => {
          if(isMultiComune.codiceEsito === CONST.OK){
            this.isMultiComune = isMultiComune.payload;
          }
          if (response.codiceEsito === CONST.OK) {
            this.dettaglio = response.payload;
            this.sezioniCatastali=sezCatastali;
            this.disable = response.payload.stato.toString() != "WORKING";
            this.stato = response.payload.stato.toString();
            this.rebuildForm();
            this.autPaesSvc.nextTipoProcedimento(this.dettaglio.tipoProcedimento.toString());
            this.teqSubject = new BehaviorSubject<TipiEQualificazioniDTO[]>(this.dettaglio.intervento.lista);
            this.tipiEQualificazioni$ = this.teqSubject.asObservable();
            if(this.userService.groupType && this.userService.groupType==GroupType.REG){
              this.buildEsitoForm(this.dettaglio);
            }
            

            if (timeResponse.codiceEsito === CONST.OK) {
              this.timeline = timeResponse.payload
              //console.log("timeline: ", JSON.stringify(this.timeline));
            }
            if (response.payload.avviso) {
              this.alertData = {
                display: true,
                title: "Avviso",
                content: response.payload.avviso,
                typ: "info",
                extraData: null,
                isConfirm: false,
              };
            }
          }
        }
      );

    this.correspondenceList$ = this.autPaesSvc.getCorrespondence(this.id);
    //qui registro il cambio pannello quando l'utente clicca sulla relativa item nella validation modal.
    this.autPaesSvc.getActiveIndex$().subscribe(indexPannello => { this.activeIndex = indexPannello; });
    this.disable = false;
    this.autPaesSvc.salvaFascicolo$.pipe(takeUntil(this.unsubscribe$)).subscribe(()=>{this.onSubmit(false,null)});
  }

  rebuildForm() {
    this.buildInterventoFormField(this.dettaglio.intervento.lista, this.interventoFormField);
    this.createFullForm(this.dettaglio);
    let interventoForm = (this.fascicoloForm.controls.intervento as FormGroup);
    if (CONST.isPareri()) {
      interventoForm.controls.interventoDettaglio.setValidators(requiredDependsOn(interventoForm.controls.a, '172'));
    } else {
      interventoForm.controls.interventoDettaglio.setValidators(requiredDependsOn(interventoForm.controls.b, '6'));
    }
    interventoForm.controls.interventoSpecifica.setValidators(requiredDependsOn(interventoForm.controls.b, '19'));
  }


  /**
   * controls: è l'oggetto dei controlli del FomrGroup intervento form.get('intervento').controls
   */
  buildInterventoFormField(result: TipiEQualificazioniDTO[], fields: PlanFormField[],controls?:{[key: string]: AbstractControl}): void {
    if (result) {
      let mappaRaggruppamentoZona=result.reduce((m, t) => m.set(t.raggruppamento, t.zona), new Map());
      //{ a=10,b=200}
      const reducedResults = Array.from(result.reduce((m, t) => m.set(t.raggruppamento, t), new Map()).values());
      //resetto tutto
      fields.forEach(f => {
        if (f.field != "interventoDettaglio" && 
            f.field != "interventoSpecifica" ) {
          f.required = false;
          f.validator = null;
        }
      });
      reducedResults.forEach(r => {
        let p: PlanFormField = fields.find(p => p.field === r.raggruppamento);
        if (p && r.zona < 10000) {
          p.required = true;
          p.validator = Validators.required;
        }
      });
      if(controls){
        Object.keys(controls).forEach(chiave=>{
          //vedo il raggruppamento nella mappa
          let zona=mappaRaggruppamentoZona.get(chiave);
          fields.forEach(campo=>{
            if(campo.field==chiave && campo.required==false) {
              console.log("spengo field "+chiave)
              controls[chiave].clearValidators();
              controls[chiave].updateValueAndValidity();
            }
          })
        })
      }
    }
  }

  // FUNZIONA WRAPPER PER FARMI RESTITUIRE IL CODICE FASCICOLO
  getCodiceFascicolo(): string { return this.fascicoloForm['controls'].codice.value; }
  getAllDropDownData() { this.tipiIntervento$ = this.autPaesSvc.getTipiIntervento(); }

  createFullForm(data?: InformazioniDTO) {
    let intervento = FascicoloFormConfig.prepareIntervento(data.intervento.lista, data.interventoSelezionati, data.interventoDettaglio, data.interventoSpecifica);
    if (CONST.isPareri()){
      intervento['dataDelibera']=data.dataDelibera;
      intervento['deliberaNum']=data.deliberaNum;
      intervento['oggettoDelibera']=data.oggettoDelibera;
      intervento['infoDeliberePrecedenti']=data.infoDeliberePrecedenti;
      intervento['descrizioneIntervento']=data.descrizioneIntervento;
    }
    this.fascicoloForm = this.formBuilder.group
      ({
        ...FascicoloFormConfig.prepareFormBuilder(this.fascicoloFormField, data),
        richiedente: this.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(this.richiedenteFormField, data.richiedente) }),
        //responsabile: this.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(FascicoloFormConfig.responsabileFormField(this.formBuilder), data.richiedente.responsabile) }),
        intervento: this.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(this.interventoFormField, intervento) }),
        //localizzazione: this.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(this.localizzazioneFormField, data.localizzazione) }),
        localizzazione: this.formBuilder.group({
          localizzazioneComuni:this.formBuilder.array([]),
          geoAllegati:this.formBuilder.array([]),
        }),
        provvedimento: this.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(this.provvedimentoFormField, data) })
      });



    //nazionalità, provincia e comune di nascita
    if (data.richiedente.statoNascita && data.richiedente.statoNascita.toLocaleLowerCase() === "italia") {
      //attivo gli input che devo attivare
      this.fascicoloForm.get("richiedente").patchValue
        ({
          nazionalitaNascitaName: { label: data.richiedente.statoNascita, value: "IT" },
          provinciaNascitaName: { label: data.richiedente.provinciaNascita, value: null },
          comuneNascitaName: { label: data.richiedente.comuneNascita, value: null }
        });
    }
    else {
      this.fascicoloForm.get("richiedente").patchValue
        ({
          nazionalitaNascitaName: { label: data.richiedente.statoNascita, value: null },
          comuneNascitaEstero: data.richiedente.comuneNascita
        });
    }
    //nazionalità, provincia e comune di residenza
    if (data.richiedente.statoResidenza && data.richiedente.statoResidenza.toLocaleLowerCase() === "italia") {
      //attivo gli input che devo attivare
      this.fascicoloForm.get("richiedente").patchValue
        ({
          nazionalitaResidenzaName: { label: data.richiedente.statoResidenza, value: "IT" },
          provinciaResidenzaName: { label: data.richiedente.provinciaResidenza, value: null },
          comuneResidenzaName: { label: data.richiedente.comuneResidenza, value: null }
        });
    }
    else {
      this.fascicoloForm.get("richiedente").patchValue
        ({
          nazionalitaResidenzaName: { label: data.richiedente.statoResidenza, value: null },
          comuneResidenzaEstero: data.richiedente.comuneResidenza
        });
    }

    if (data.provvedimento.provvedimento && data.provvedimento.provvedimento.id)
      saveFileToLocalStorage(localStorage, data.provvedimento.provvedimento, "PROVVEDIMENTO_FINALE", this.dettaglio.id.toString(), "provvedimento");
    if (data.provvedimento.provvedimentoPrivato && data.provvedimento.provvedimentoPrivato.id)
      saveFileToLocalStorage(localStorage, data.provvedimento.provvedimentoPrivato, "PROVVEDIMENTO_FINALE_PRIVATO", this.dettaglio.id.toString(), "provvedimento");
    if (data.provvedimento.parere && data.provvedimento.parere.id)
      saveFileToLocalStorage(localStorage, data.provvedimento.parere, "PARERE_MIBAC", this.dettaglio.id.toString(), "provvedimento");
    if (data.provvedimento.parerePrivato && data.provvedimento.parerePrivato.id)
      saveFileToLocalStorage(localStorage, data.provvedimento.parerePrivato, "PARERE_MIBAC_PRIVATO", this.dettaglio.id.toString(), "provvedimento");
    if (data.allegati) {
      data.allegati.forEach(allegato => {
        saveFileToLocalStorage(localStorage, allegato, 
          (allegato.tipi && allegato.tipi.length>0)?allegato.tipi:allegato.tipo, 
          this.dettaglio.id.toString(), "allegati");
      });
    }
    //precaricamento nella localstorage dell documento di riconoscimento allegato
    if (CONST.isPareri() && 
        data.richiedente && data.richiedente.responsabile && 
        data.richiedente.responsabile.documentoRiconoscimento) {
        saveFileToLocalStorage(localStorage,data.richiedente.responsabile.documentoRiconoscimento , 
          "DOCUMENTO_RICONOSCIMENTO", 
          this.dettaglio.id.toString(), "responsabile");
      
    }
  }

  segnalaErroreEdEsci(content: string) {
    this.alertData.title = "";
    this.alertData.typ = "danger";
    this.alertData.content = content;
    this.alertData.isConfirm = false;
    this.alertData.extraData = { operazione: 'back', navigate: '/private/fascicolo' };
    this.alertData.display = true;
  }

  
  // ALERT GENERICO PER LA GESTIONE DELLA VALIDAZIONE
  myAlertErrorValidazione(msg?: string): void {
    this.alertData.display = true;
    this.alertData.isConfirm = false;
    this.alertData.typ = "danger";
    this.alertData.content = msg;
    this.loadingService.emitLoading(false);
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'validato':
            //this.doValidate(event.extraData.codice)
            break;
          case 'back':
            this.router.navigate([event.extraData.navigate]);
            break;
          case 'resetForm':
            //this.recoverFascicolo(event.extraData.codice);
            break;
          case 'resetFormEsito':
            //this.recoverFascicolo(event.extraData.codice);
            break;
          case 'confermaEsito':
            this.confermaEsito();
            break;
          case 'doInviaETerminaVerifica':
            this.doInviaETerminaVerifica();
            break;  
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  backDiretto() {
    this.router.navigate(['/private/fascicolo']);
  }

  back() {
    if ((this.fascicoloForm.touched && this.fascicoloForm.enabled) 
      || (this.esitoForm && this.esitoForm.enabled && this.esitoForm.dirty)) {
      this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('fascicolo.abbandonaModifiche');
      this.alertData.content = this.translateService.instant('fascicolo.sicuroAbbandonoModifiche');
      this.alertData.extraData = { operazione: 'back', navigate: '/private/fascicolo' };
      this.alertData.display = true;
    } else {
      this.backDiretto();
    }
  }

  salvaFascicolo(silent?: boolean): void {
    this.loadingService.emitLoading(true);
    console.log("salvo: ", this.fascicoloForm.getRawValue());
    this.autPaesSvc.updateFascicolo(this.fascicoloForm.getRawValue()).then((resp) => {
      if (resp.codiceEsito == CONST.OK) {
        if (!silent) {
          this.alertData.display = true;
          this.alertData.isConfirm = false;
          this.alertData.typ = "success";
          this.alertData.content = "Salvataggio effettuato con successo";
          this.loadingService.emitLoading(false);
          this.fascicoloForm.markAsUntouched();
        }
      }
      else {
        this.alertData.display = true;
        this.alertData.isConfirm = false;
        this.alertData.typ = "danger";
        this.alertData.content = "Procedura fallita, riprovare per favore.";
        this.loadingService.emitLoading(false);
      }
    }).catch(error => {
      this.loadingService.emitLoading(false);
    });
  }

  salvaEsitoVerifica(silent?: boolean): void {
    this.prosegui = true;
    if (this.esitoForm.valid) {
      this.alertData = {
        display: true,
        title: "Informazioni",
        content: this.messaggioEsitoVerifica(this.esitoForm.get("esitoVerifica").value),
        typ: "info",
        extraData: {operazione: "confermaEsito"},
        isConfirm: true,
      };
    }
    else {
      this.invalid = [];
      let messaggi: string[] = new Array();
      messaggi.push('Ricontrolla le informazioni inserite!');
      this.invalid.push({ indexPannello: 10, sezione: 'Esito', messaggi: messaggi });
      this.promptDialogService.showValidationDialog(true);
    }
  }

  private messaggioEsitoVerifica(esito: string): string {
    let messaggio: string = "L'esito verifica selezionato è " + this.translateService.instant('fascicolo.esito.'+esito).toUpperCase() + ". Proseguendo non sarà più possibile modificare l'esito della verifica e sarà generato il documento 'Lettera di trasmissione del Provvedimento'. Proseguire?";
    return messaggio;
  }

  resetEsitoVerifica() {
      if (this.mostraLetteraProvvedimento === false) {
      this.esitoForm.get("esitoVerifica").setValue(null);
      this.esitoForm.get("dataProvvedimentoConclucisvo").setValue(null);
      this.esitoForm.get("numeroProvvedimento").setValue(null);
      this.esitoForm.updateValueAndValidity();
    }
  }


  //forse non più utilizzato...
  formValidator() {
    let form: FormGroup;
    this.fascicoloFormField.forEach(item => {
      if (item.required) {
        this.fascicoloForm.controls[item.field].setValidators(Validators.required);
        this.fascicoloForm.controls[item.field].updateValueAndValidity();
      }
    });
    form = this.fascicoloForm.get('richiedente') as FormGroup;
    this.richiedenteFormField.forEach(item => {
      if (item.required) {
        form.controls[item.field].setValidators(Validators.required);
        form.controls[item.field].updateValueAndValidity();
      }
    });
    form = this.fascicoloForm.get('intervento') as FormGroup;
    this.interventoFormField.forEach(item => {
      if (item.required) {
        form.controls[item.field].setValidators(Validators.required);
        form.controls[item.field].updateValueAndValidity();
      }
    });
    //form.setValidators(FascicoloFormConfig.InterventoValidator);
    form = this.fascicoloForm.get('provvedimento') as FormGroup;
    this.provvedimentoFormField.forEach(item => {
      if (item.required) {
        form.controls[item.field].setValidators(Validators.required);
        form.controls[item.field].updateValueAndValidity();
      }
    });
    form.updateValueAndValidity();
  }

  findInvalidControls(formGroup: FormGroup, fields: PlanFormField[]) {
    const controls = formGroup.controls;
    const invalidArray = [];
    for (const name in controls) {
      if (controls[name].invalid) {
        //console.log('invalido:'+name)        
        const invalidField = fields.find(item => item.field === name);
        if (invalidField.label !== '') {
          invalidArray.push(invalidField.label);
        }
      }
    }
    return Array.from(new Set(invalidArray));
  }

  private getIndexPannello(sezione:string):number{
    switch (sezione) {
      case 'Richiedente':
        return  1;
      case 'Intervento':
        return  2;
      case 'fascicolo.localizzazioneSection.localizzazione':
        return  3;
      case 'Provvedimento':
        return  4;
      case 'fascicolo.allegatiSection.titolo':
        return 5;
      default:
        return 0;
    }
  }

  private addValidationMessage(sezione: string, messaggio: string, invalid: ValidationMessage[]) {
    let index = invalid.findIndex(el => el.sezione == sezione);
    if (index >= 0) {
      invalid[index].messaggi.push(messaggio);
    } else {
      let validationEmpty = { indexPannello: 0, sezione: '', messaggi: [] };
      validationEmpty.indexPannello=this.getIndexPannello(sezione);
      validationEmpty.sezione = sezione;
      invalid.push(validationEmpty);
      index = invalid.findIndex(el => el.sezione == sezione);
      if(index>=0){
        invalid[index].messaggi.push(messaggio);
      }
    }
  }

  public salvaConValidazione() {
    //chiamo il salva e se ok non mostro la dialog ma avvio la validazione...
    this.onSubmit(false,this.doValidazione.bind(this));
  }

  public doValidazione() {
    this.fascicoloForm.controls.intervento.updateValueAndValidity();
    console.log("form valido: ", this.fascicoloForm.valid);

    this.submitted = true
    if (!this.fascicoloForm.valid)
      logInvalidControls(this.fascicoloForm);

    let invalid = this.invalid;
    let submitted = this.submitted;
    let fascicoloForm = this.fascicoloForm;
    let dettaglio = this.dettaglio;
    let _this = this;

    let form = fascicoloForm.controls;

    invalid = [];
    _this.submitted = true;

    //Verifica per il tab Fascicolo
    Object.keys(form).forEach(key => {
      let tmp: any = (form[key] as FormGroup).controls;
      if (!tmp && form[key].invalid && invalid.length === 0) {
        if (key == 'tipoSelezioneLocalizzazione') {
          this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
            "Tipo selezione localizzazione non inserito", invalid);
        }
        else {
          //let messaggi: string[] = new Array();
          //messaggi.push('Ricontrolla le informazioni inserite!');
          //invalid.push({ indexPannello: 0, sezione: 'Fascicolo', messaggi: messaggi });
          this.addValidationMessage('Fascicolo',
            "Ricontrolla le informazioni inserite!", invalid);
        }
      }
    });
    //Verifica per il tab Richiedente
    let richiedenteForm = (form.richiedente as FormGroup);
    if (richiedenteForm.invalid) {
      //let messaggi: string[] = new Array();
      //messaggi.push('Ricontrolla le informazioni inserite!');
      //invalid.push({ indexPannello: 1, sezione: 'Richiedente', messaggi: messaggi });
      this.addValidationMessage('Richiedente',
            "Ricontrolla le informazioni inserite!", invalid);
    }
    //Verifica per il tab Descrizione Intervento
    if ((form.intervento as FormGroup).invalid) {
      //let messaggi: string[] = new Array();
      //messaggi.push('Ricontrolla le informazioni inserite!');
      //invalid.push({ indexPannello: 2, sezione: 'Intervento', messaggi: messaggi });
      this.addValidationMessage('Intervento',
            "Ricontrolla le informazioni inserite!", invalid);
    }
    //Verifica per il tab localizzazione nuova versione come AS-IS
    let localizzazioneComuni=form.localizzazione.get('localizzazioneComuni') as FormArray;
    if(localizzazioneComuni.length==0){
      //let messaggi: string[] = new Array();
      //messaggi.push('Nessun comune indicato in localizzazione!');
      //invalid.push({ indexPannello: 3, sezione: 'fascicolo.localizzazioneSection.localizzazione', messaggi: messaggi });
      this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
            "Nessun comune indicato in localizzazione!", invalid);
    }else{ //ci sono comuni...
      for(let i=0;i<localizzazioneComuni.length;i++){
        let isInterventoStrada=localizzazioneComuni.at(i).get('strade').value;
        let isInterventoStradaValido=
          isInterventoStrada && 
          localizzazioneComuni.at(i).get('indirizzo').value && 
          localizzazioneComuni.at(i).get('numCivico').value;
        let formParticelle=localizzazioneComuni.at(i).get('particelle') as FormArray;  
        let hasParticelle=formParticelle.length>0; 
        let formLocComune=localizzazioneComuni.at(i);
        let messaggi: string[] = new Array();
        let giaUnErrore=false;
        if(hasParticelle && !formParticelle.valid){
          this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
          'Completare i dati delle particelle per : '+localizzazioneComuni.at(i).get('comune').value,
           invalid);
           giaUnErrore=true;
          //messaggi.push('Completare i dati delle particelle per : '+localizzazioneComuni.at(i).get('comune').value);  
        }
        if(isInterventoStrada && !isInterventoStradaValido){
          this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
          'Inserire i dati dell\'area stradale: indirizzo e numero civico per: '+localizzazioneComuni.at(i).get('comune').value,
           invalid);
           giaUnErrore=true;
          //messaggi.push('Inserire i dati dell\'area stradale: indirizzo e numero civico per: '+localizzazioneComuni.at(i).get('comune').value);  
        }
        if(!isInterventoStrada && !hasParticelle){
          this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
          'Inserire i dati minimi per la localizzazione per :'+localizzazioneComuni.at(i).get('comune').value,
          invalid);
          giaUnErrore=true;
          //messaggi.push('Inserire i dati minimi per la localizzazione per :'+localizzazioneComuni.at(i).get('comune').value);  
        }
        if(formLocComune.invalid && !giaUnErrore){
          this.addValidationMessage('fascicolo.localizzazioneSection.localizzazione',
          'Correggere i campi in localizzazione per :'+localizzazioneComuni.at(i).get('comune').value,
          invalid);
        }
      }
    }

    //Verifica per il tab Provvedimento
    _this.autPaesSvc.checkAttachmentsFormValidity(dettaglio.id.toString(), 'provvedimento',
      _this.autPaesSvc.getLastTipoProcedimento()).
      pipe(takeUntil(_this.unsubscribe$)).subscribe(provvInvalidi => 
      {
        if (form.provvedimento.invalid) 
          provvInvalidi.push("Ricontrolla le informazioni inserite");
        if(provvInvalidi && provvInvalidi.length > 0)
          invalid.push({ indexPannello: 4, sezione: 'Provvedimento', messaggi: provvInvalidi });
        //Verifica per il tab Allegati
        _this.autPaesSvc.checkAttachmentsFormValidity(dettaglio.id.toString(), 'allegati', _this.autPaesSvc.getLastTipoProcedimento())
          .pipe(takeUntil(this.unsubscribe$)).subscribe(allInvalidi => {
            if (allInvalidi && allInvalidi.length > 0) {
              invalid.push({ indexPannello: 5, sezione: 'fascicolo.allegatiSection.titolo', messaggi: allInvalidi });
            }
            //test finale validita'
            _this.invalid = invalid;
            console.log(this.fascicoloForm);
            if (_this.invalid.length === 0 && this.fascicoloForm.valid) {
                this.autPaesSvc.validaBE(this.id).subscribe(
                response => {
                  if (response.codiceEsito === "OK") {
                    if (response.payload && !response.payload.valid) {
                      this.alertData = {
                        display: true,
                        title: "Errore",
                        content: this.scriviListaErrori(response.payload),
                        typ: "danger",
                        extraData: null,
                        isConfirm: false,
                      }
                    }
                    else {
                      this.showMailForm();
                    }
                  }
                },
                error => {
                  this.alertData = {
                    display: true,
                    title: "Errore",
                    content: error.message,
                    typ: "danger",
                    extraData: null,
                    isConfirm: false,
                  }
                }
              );
            }
            else {
              _this.promptDialogService.showValidationDialog(true);
            }
          });
      });
  }


  private addMessaggiPannello(errori:SelectOption[],nomePannello:string):string{
    let richiedenteForm = (this.fascicoloForm.controls.richiedente as FormGroup);
    let messaggio="";
    if(errori && errori.length>0){
      messaggio=messaggio.concat("<li> errore nel tab ").concat(nomePannello);
      messaggio=messaggio.concat("<ul type='disc'>");
      errori.forEach(errore=>{
        messaggio=messaggio.concat("<li>").concat(errore.description).concat("</li>");
        let error=errore.value;
        if (error === "RICHIEDENTE_CF" ||error === "RICHIEDENTE_CF_VUOTO") {
          richiedenteForm.controls.codiceFiscale.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_DITTA_CF") {
          richiedenteForm.controls.dittaCodiceFiscale.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_DITTA_P_IVA") {
          richiedenteForm.controls.dittaPartitaIva.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_CAP") {
          richiedenteForm.controls.cap.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_TELEFONO") {
          richiedenteForm.controls.telefono.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_EMAIL") {
          richiedenteForm.controls.email.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_PEC") {
          richiedenteForm.controls.pec.setErrors({ invalid: true });
        }
        else if (error === "RICHIEDENTE_DATA_NASCITA") {
          richiedenteForm.controls.dataNascita.setErrors({ invalid: true });
        }
      });
      messaggio=messaggio.concat("</ul>");
      messaggio=messaggio.concat("</li>");
    }
    return messaggio;
  }

  private scriviListaErrori(errorBean: ValidationBeanDto): string {
    let messaggio: string = "Sono stati rilevati i seguenti errori: <br>";
    messaggio = messaggio.concat("<ol>");
    let erroriRichiedente=errorBean.richiedenteError;
    if(erroriRichiedente && erroriRichiedente.length>0){
      //messaggio=messaggio+this.gestistiErroriTabRichiedente(erroriRichiedente);
      messaggio=messaggio+this.addMessaggiPannello(errorBean.richiedenteError,"RICHIEDENTE");
    }
    messaggio=messaggio+this.addMessaggiPannello(errorBean.localizzazioneError,"LOCALIZZAZIONE");
    messaggio=messaggio+this.addMessaggiPannello(errorBean.interventoError,"INTERVENTO");
    messaggio=messaggio+this.addMessaggiPannello(errorBean.allegatiError,"ALLEGATI");
    messaggio=messaggio+this.addMessaggiPannello(errorBean.provvedimentoError,"PROVVEDIMENTO");
    messaggio = messaggio.concat("</ol>");
    return messaggio;
  }

  errorAllert(invalid: any[]) {
    if (invalid) {
      let message = invalid[0].messaggi.
        this.alertData =
      {
        title: "Attenzione",
        content: ""
      }
    }
  }

  showMailForm() {
    this.alertData.isConfirm = false;
    this.alertData.typ = "success";
    this.alertData.title = this.translateService.instant('fascicolo.mailCompose.validazioneOK');
    this.alertData.content = this.translateService.instant('fascicolo.mailCompose.validazioneMsg');
    this.alertData.display = true;
    this.showPage = "MAIL";
  }

  
  onSubmit(dialogConferma:boolean=true,callbackFn:any) {
    //attualmente la validazione viene effettuata solo in un secondo momento rispetto all'inserimento
    //anche se in realtà, quantomeno la validazione sulla lunghezza degli input inseriti o sulla mutua esclusione
    //di alcune informazioni dovrebbero essere fatte a prescindere.
    let _fascicolo = this.fascicoloForm;
    if (this.dettaglio.stato.toString() === "WORKING" || this.dettaglio.stato.toString()==="ON_MODIFY") {
      this.autPaesSvc.salvaFascicolo(this.buildInformazioniDTO(this.fascicoloForm.getRawValue())).subscribe(resolve => {
        if (resolve.codiceEsito === CONST.OK && resolve.payload) {
          _fascicolo.get("richiedente").get("id").setValue(resolve.payload.richiedente.id);
          this.timeline.richiedenteNome = resolve.payload.richiedente.nome;
          this.timeline.richiedenteCognome = resolve.payload.richiedente.cognome;
          this.timeline.tipoProcedimento = resolve.payload.tipoProcedimento.toString();
          //this.timeline.ente = resolve.payload.ufficio; l'ente non dovrebbe cambiare... oltretutto passa a un codice e non una descrizione
          this.dettaglio.localizzazione = resolve.payload.localizzazione;
          if(dialogConferma){
            this.alertData.isConfirm = false;
            this.alertData.typ = "success";
            this.alertData.title ="";
            this.alertData.content = this.translateService.instant('dialog.operazioneOK');
            this.alertData.display = true;
          }
          _fascicolo.markAsUntouched();
          if(callbackFn){
            callbackFn();
          }
        }
      },
      error => {
        console.error(error);
      });
    }

  }

  private buildInformazioniDTO(rows: any): InformazioniDTO {
    let info: InformazioniDTO = new InformazioniDTO();
    Object.keys(rows).forEach(key => {
      if (rows[key] instanceof Object && !(rows[key] instanceof Date)) {
        if (key === "intervento") {
          let interventoList: number[] = [];
          Object.keys(rows[key]).forEach(interventoKey => {
            if (interventoKey != "interventoDettaglio" && 
                interventoKey != "interventoSpecifica" && 
                interventoKey.length==1) {
              if (rows[key][interventoKey] instanceof Array) {
                rows[key][interventoKey].forEach(elem => {
                  if (elem)
                    interventoList.push(elem);
                });
              }
              else if (rows[key][interventoKey])
                interventoList.push(rows[key][interventoKey]);
            }
            else
              info[interventoKey] = rows[key][interventoKey];
          });
          info.interventoSelezionati = interventoList;
        }
        else if (key === "provvedimento") {
          Object.keys(rows[key]).forEach(provvedimentoKey => {
            info[provvedimentoKey] = rows[key][provvedimentoKey];
          });
        }
        else if (key === "richiedente") {
          let richiedente: RichiedenteDTO = new RichiedenteDTO();
          Object.keys(rows[key]).forEach(richiedenteKey => {
            richiedente[richiedenteKey] = rows[key][richiedenteKey];
          });
          // -- nazionalità, provincia e comune di nascita -- //
          if (rows[key].nazionalitaNascitaName) {
            richiedente.statoNascita = rows[key].nazionalitaNascitaName.label;
            if (rows[key].nazionalitaNascitaName.value === "IT") {
              richiedente.provinciaNascita = rows[key].provinciaNascitaName ? rows[key].provinciaNascitaName.label : null;
              richiedente.comuneNascita = rows[key].comuneNascitaName ? rows[key].comuneNascitaName.label : null;
            }
            else {
              richiedente.comuneNascita = rows[key].comuneNascitaEstero;
            }
          }
          // -- nazionalità, provincia e comune di residenza -- //
          if (rows[key].nazionalitaResidenzaName) {
            richiedente.statoResidenza = rows[key].nazionalitaResidenzaName.label;
            if (rows[key].nazionalitaResidenzaName.value === "IT") {
              richiedente.provinciaResidenza = rows[key].provinciaResidenzaName ? rows[key].provinciaResidenzaName.label : null;
              richiedente.comuneResidenza = rows[key].comuneResidenzaName ? rows[key].comuneResidenzaName.label : null;
            }
            else {
              richiedente.comuneResidenza = rows[key].comuneResidenzaEstero;
            }
          }
          info.richiedente = richiedente;
        }
        else if (key === "localizzazione") {
         info.localizzazione=rows.localizzazione;
        }
      }
      else {
        info[key] = rows[key];
      }
    });
    return info;
  }

  reset() {
    if (this.fascicoloForm.touched) {
      this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('fascicolo.abbandonaModifiche');
      this.alertData.content = this.translateService.instant('fascicolo.sicuroAbbandonoModifiche');
      this.alertData.extraData = { operazione: 'resetForm', codice: this.dettaglio.codice };
      this.alertData.display = true;
    }
  }

  trasmetti(destinatari: DestinatarioComunicazioneDTO[]): void {
    this.autPaesSvc.trasmessiFascicolo(this.dettaglio.id.toString(), destinatari).subscribe(result => {
      if (result.codiceEsito === CONST.OK && result.payload) {
        this.dettaglio.stato = RegistrationStatus.TRANSMITTED;
        this.stato = "TRANSMITTED";
        this.disable = true;
        this.showPage="FASCICOLO";
        this.timeline.dataTrasmissione = new Date();
        this.activeIndex = 6;
        this.promptDialogService.showPromptDialog(true);
      }
    });
  }

  downloadAttestatoRicezione() {
    this.autPaesSvc.downloadRicevutaTrasmissione(this.dettaglio.id.toString()).subscribe(
      response => {
        if (response.ok) {
          downloadFile(response.body, "ricevuta di trasmissione.pdf");
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  /**
   * al cambio di tipo procedimento cambia il form e i validatori e l'obbligatorietà di alcuni allegati
   * @param tipoProcedimento 
   */
  resettaForm(tipoProcedimento: string) {
    let _this = this;
    if (tipoProcedimento) {
      this.loadingService.emitLoading(true);
      _this.autPaesSvc.getIntervento(tipoProcedimento,this.id).subscribe(response => {
        _this.loadingService.emitLoading(false);
        if (response.codiceEsito === CONST.OK) {
          let list: TipiEQualificazioniDTO[] = response.payload.lista;
          if (list && list.length > 0) {
            let controls=null;
            if(this.fascicoloForm.get('intervento')){
              controls=(this.fascicoloForm.get('intervento') as FormGroup).controls;
            }
            this.buildInterventoFormField(response.payload.lista, this.interventoFormField,controls);
            _this.teqSubject.next(list);
            _this.autPaesSvc.nextTipoProcedimento(tipoProcedimento); 
          }
        }
        else {
          console.log("0 elementi ottenuti");
        }
      },
        error => {
          console.error(error);
        });
    }

  }

  public buildEsitoForm(dettaglio: InformazioniDTO): void {
    this.esitoForm = this.formBuilder.group({
      esitoVerifica: [dettaglio.esitoVerifica ? dettaglio.esitoVerifica : null, Validators.required],
      dataProvvedimentoConclucisvo: [dettaglio.esitoDataRilascioAutorizzazione ? new Date(dettaglio.esitoDataRilascioAutorizzazione) : null, Validators.required],
      numeroProvvedimento: [dettaglio.esitoNumeroProvvedimento ? dettaglio.esitoNumeroProvvedimento : null, Validators.required],
      tabellaProvvedimentoFinale: [dettaglio.esitoVerifica ? true : false, Validators.requiredTrue],
    });
    if (dettaglio.esitoVerifica=="POSITIVE" ||dettaglio.esitoVerifica=="NEGATIVE" ) {
      this.getDatiTabelle();
    }
  }

  private getDatiTabelle(): void {
    this.autPaesSvc.getDati(this.dettaglio.id.toString()).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.allegatiTabEsito = response.payload;
          this.prosegui = true;
          this.mostraLetteraProvvedimento = true;
          this.esitoForm.disable();
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: "Errore durante lo svolgimento dell'operazione",
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
      }
    );
  }

  public confermaEsito(): void {
    this.dettaglio.esitoVerifica = this.esitoForm.get("esitoVerifica").value;
    this.dettaglio.esitoDataRilascioAutorizzazione = this.esitoForm.get("dataProvvedimentoConclucisvo").value;
    this.dettaglio.esitoNumeroProvvedimento = this.esitoForm.get("numeroProvvedimento").value;
    this.autPaesSvc.prosegui(this.dettaglio, this.fileProvvedimentoFinaleEsitoVerifica).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.esitoForm.disable();
          this.mostraLetteraProvvedimento = true;
          this.allegatiTabEsito = response.payload;
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: "Errore durante lo svolgimento dell'operazione",
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
      }
    );
    
  }

  public inviaETerminaVerifica(){
    this.alertData = {
      display: true,
      typ : "info",
      title : this.translateService.instant('OPERAZIONE_IRREVERSIBILE'),
      content : this.translateService.instant('fascicolo.esito.msgConferma'),
      extraData: {operazione:'doInviaETerminaVerifica'},
      isConfirm: true,
    };
  }

  public doInviaETerminaVerifica(): void {
    this.loadingService.emitLoading(true);
    this.autPaesSvc.inviaEterminaVerifica(this.dettaglio.id.toString()).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Pratica inviata con successo",
            typ: "success",
            extraData: null,
            isConfirm: false,
          };
          this.backDiretto();
          this.loadingService.emitLoading(false);
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: error.message,
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
        this.loadingService.emitLoading(false);
      }
    );
  }

  public riempimentoTabellaProvvedimentoFinale(event: any): void {
    this.fileProvvedimentoFinaleEsitoVerifica = event;
  }

  public okEsitoPanel():boolean{
    return this.dettaglio.stato == this.rsEnum.SELECTED ||
          (this.dettaglio.stato==this. rsEnum.FINISHED && 
            (this.dettaglio.esitoVerifica=="POSITIVE" || this.dettaglio.esitoVerifica=="NEGATIVE") );    
  }
   /* Metodo per permettere l'abilitazione di determinati campi */
    disableAndNotEditable():boolean{
    
      if(this.dettaglio.stato==this.rsEnum.ON_MODIFY  && this.dettaglio.usernameUtenteTrasmissione==this.userService.getUser().username 
      && new Date(this.dettaglio.modificabileFino)>new Date()){
        return false;
      }
      else{
       return this.disable;
      }
    }
    get isAdmin(): boolean { return this.userService.groupType == GroupType.ADMIN;/* this.user.role && this.user.role === GroupRole.A; */ }

    public isEditableOnModify():boolean{
      return this.dettaglio.stato === this.rsEnum.ON_MODIFY && 
      this.dettaglio.ufficio === this.userService.actualGroup && 
      this.dettaglio.usernameUtenteTrasmissione === this.userService.getUser().username;
    }

    public enableRichiestePostTrasmissionePanel():boolean{
      return  this.dettaglio.stato != this.rsEnum.WORKING && !this.dettaglio.statoTrasmissione
        && this.dettaglio.ufficio === this.userService.actualGroup;
    }
    
    public enableAnnotazioniInterne():boolean{
      return  (this.userService.groupType === GroupType.ETI
                || this.userService.groupType === GroupType.SBAP
                  || this.userService.groupType === GroupType.REG) && 
                  !this.userService.isStessaOrganizzazione(this.dettaglio.ufficio,this.userService.actualGroup);   

    }

    onImport() {
      this.router.navigate(['/private/fascicolo/dettaglio/import/'+this.id]);
    }
    
}
