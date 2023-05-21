import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { CookieStorage } from 'cookie-storage';
import { loadModules } from 'esri-loader';
import * as IdentifyResult from 'esri/tasks/support/IdentifyResult';
import { SelectItem } from 'primeng/primeng';
import { combineLatest, Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TableConfig } from 'src/app/core/models/header.model';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { Allegato, ComuniCompetenza, localizzazioneInfo, RequestAllegato } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { environment } from 'src/environments/environment';
import { DichiarazioniService } from '../../services/dichiarazioni/dichiarazioni.service';
import { IstanzaConst } from './../../constants/istanza-const';
import { LocalizzazioneValues } from './../../models/comp-istanza.model';
import { LocalizzazioneService } from './../../services/localizzazione/localizzazione.service';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { attributesLower } from 'src/app/shared/functions/ObjectUtils';


@Component({
  selector: 'app-localizzazione',
  templateUrl: './localizzazione.component.html',
  styleUrls: ['./localizzazione.component.scss']
})
export class LocalizzazioneComponent extends AbstractTab implements OnInit, OnDestroy
{
  @Input("disable") disable: boolean = false;
  localizzazioneComuni: FormArray;
  mockComuniSelezionabili: SelectOption[];
  mockComuniSelezionati: SelectItem[] = [];
  formSelezioneComune: FormGroup;
  /**esri map parameter */
  public hasMap: boolean = false;
  public hasEditor: boolean = true && environment.webgis.hasEditing;
  public query: string = null;
  public user: string = null;
  bpImmobiliEAreeOptions$: Observable<SelectOption[]>;
  ucpPaesaggio$: Observable<SelectOption[]>;
  locationTableHeaders: TableConfig[] = [];
  comune: string[];
  locationSecondTableHeaders: TableConfig[] = [];
  secondTableData: any[];
  //localizzazione: any[];
  //localizzazioneSecond: any[];
  initState: Allegato[];
  public const = CONST;
  //@Input() fascicolo: Fascicolo;

  //codice: Codice[];
  localizzazioneForm: FormGroup;
  indiceFormArray: number; //indice dell'accordion aperto

  //@Input() mainForm: FormGroup;
  //builders: { [id: string]: Function };
  attivaAggiungi: boolean = false; //attiva il button aggiungiComune
  allegatiShape: Allegato[] = [];
  unsubscribe$ = new Subject<void>();

  /*
  @Output() onUploadFileShape: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDownloadFileShape: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDeleteFileShape: EventEmitter<any> = new EventEmitter<any>();
  */
  /***************************NEW COME DEMANIO  */
  public formInserimentoParticella: FormGroup;
  //public tipoSelezioneLocalizzazione:string;
  tipiSelezioneLocalizzazione: SelectItem[] = [
    { label: "Shape File", value: "SHPF", disabled: false },
    { label: "Edit Shape su mappa", value: "SHPD", disabled: false },
    { label: "Particelle/Mappa", value: "PTC" },
  ];
  public aggiornaMappa: number = 0;
  public submitted_area: boolean;
  public visibleFormSezione: boolean = false;
  public sceltaSezione: string;
  public Graphic: any;
  public esriConfig: any;
  public editLayerPolygon: any;
  public geometryZoom = null;
  checkPartSel: boolean;
  sendPartSel: boolean;
  /*************************END NEW COME DEMANIO */
  public colonneFile: any[] =
    [
      { header: "TECHNICAL_DOCUMENTATION.FILE_NAME" },
      { header: "TECHNICAL_DOCUMENTATION.UPLOAD_DATE" },
      { header: "TECHNICAL_DOCUMENTATION.NOME_CONTENUTO" },
      { header: "TECHNICAL_DOCUMENTATION.HASH" }
    ];
  public loaded = false; //per indicare quando il form e i dati snon pronti per il template.

  //oggetto utilizzato per l'alert
  public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
      labelAlertChiudi: 'generic.chiudi',
      labelAlertOk: 'generic.ok'
    };

  constructor(private fb: FormBuilder,
              private httpDominio: HttpDominioService,
              private dialogService: CustomDialogService,
              private userService: UserService,
              private loadingService: LoadingService,
              private locSvc: LocalizzazioneService,
              private dichiarazioniService: DichiarazioniService,
              private allegatoSvc: AllegatoService) { super(); }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit()
  {
    loadModules([
      "esri/layers/FeatureLayer",
      "esri/graphic",
      "esri/config",
      "esri/request"], { url: 'https://js.arcgis.com/3.26/' }).then(
        ([FeatureLayer, Graphic, esriConfig, esriRequest]) =>
        {
          this.addEsriRequestToken(esriRequest);
          this.Graphic = new Graphic();
          this.esriConfig = esriConfig;
          let urlBackEnd = new URL(environment.webgis.urlLocale);
          esriConfig.defaults.io.corsEnabledServers.push(urlBackEnd.host);
          const urlLayer = CONST.EDIT_LAYER;
          this.editLayerPolygon = new FeatureLayer(urlLayer, {
            outFields: ['*'],
            mode: FeatureLayer.MODE_ONDEMAND,
            visible: true,
            id: 'EditPolygon',
          });
        })
    this.locationTableHeaders = IstanzaConst.firstLocalizationTableHeaders;
    this.locationSecondTableHeaders = IstanzaConst.seconLocalizationTableHeaders2;
    this.initState = IstanzaConst.shapeFileAllegatoInit;
    //costruisco la select dei comuni selezionabili dai comuni di competenza
    this.mockComuniSelezionabili = [];

    this.fascicolo.comuniCompetenza.forEach(el =>
      this.mockComuniSelezionabili.push({ value: el.enteId, description: el.descrizione, linked: el.codIstat }));
    //tolgo i comuni già inseriti...
    //this.fascicolo.istanza
    this.buildForm();
    //this.addAllegaShapeFiles();

    //this.mockComuniSelezionabili.push({ label: "Comune di Bari", description: "Comune di Bari", value: 11}); //mock
    this.startWatchLocalizationsFormCheanges();
    //form per il solo inserimento.
    this.formInserimentoParticella = this.fb.group({
      id: [null, null],//hidden
      comuneIntervento: [{ value: null, disabled: this.disable }, Validators.required],
      foglioIntervento: [{ value: null, disabled: this.disable }, [Validators.required, Validators.maxLength(5), Validators.pattern('[1-9]+[0-9]*')]],
      sezioneIntervento: [{ value: "", disabled: this.disable }, null],
      particellaIntervento: [{ value: null, disabled: this.disable }, [Validators.required, Validators.maxLength(5), Validators.pattern('[1-9]+[0-9]*')]],
      codiceEnte: ["", null],
      noteIntervento: [{ value: "", disabled: this.disable }]
    });
    this.loaded = true;
    this.localizzazioneForm.disable();
    this.disable = true;
  }

  buildForm()
  {
    //ho anche un form di selezione comune 
    this.formSelezioneComune = this.fb.group({ comune: [null, Validators.required] });
    this.formSelezioneComune.get('comune').valueChanges.subscribe(event =>
    {
      if (event)
        this.attivaAggiungi = true;
    });
    this.localizzazioneComuni = this.fb.array([]);
    this.localizzazioneForm = this.fb.group({
      localizzazioneComuni: this.localizzazioneComuni,
      allegaShapeFile: this.fb.array([]),
    });
    this.mainForm.addControl('localizzazione', this.localizzazioneForm);
    this.mainForm.addControl('tipoSelezioneLocalizzazione', new FormControl(this.fascicolo.tipoSelezioneLocalizzazione));
    this.mainForm.addControl('hasShape', new FormControl(this.fascicolo.hasShape));
    this.initData();
    if (this.disable)
      this.formSelezioneComune.disable();
  }

  initData()
  {
    if (this.fascicolo.istanza.localizzazione &&
      this.fascicolo.istanza.localizzazione.localizzazioneComuni &&
      this.fascicolo.istanza.localizzazione.localizzazioneComuni.length > 0)
    {
      let value: LocalizzazioneValues = { comuneSelezionato: false, bpParchiRiserve: false, bpImmobileAreeInteresse: false };
      this.fascicolo.istanza.localizzazione.localizzazioneComuni.forEach(locComune =>
      {
        let formComune = this.buildFormComune(locComune.comuneId, locComune.comuneId);
        if (this.disable)
        {
          formComune.disable();
        }
        let comuneCompentenza = this.fascicolo.comuniCompetenza.find(el => el.enteId == locComune.comuneId);
        if(comuneCompentenza)
          this.mockComuniSelezionati.push({ label: comuneCompentenza.descrizione, value: comuneCompentenza.enteId });
        ViewMapper.mapObjectToForm(formComune, locComune);

        let isPresent: boolean = this.localizzazioneComuni.getRawValue().find(elem => elem.comuneId === formComune.get("comuneId").value) ? true : false;
        //build fomrarrayParticelle con dati
        if (locComune.particelle && locComune.particelle.length > 0)
        {
          for (let ind = 0; ind < locComune.particelle.length; ind++)
          {
            this.aggiungiFormParticella(locComune.particelle[ind], formComune.get('particelle') as FormArray, false);
          }
        }
        if (!isPresent)
        {
          this.localizzazioneComuni.push(formComune);
        }

        let tmp = this.evaluateLocalization(locComune);
        value.comuneSelezionato = value.comuneSelezionato || tmp.comuneSelezionato;
        value.bpParchiRiserve = value.bpParchiRiserve || tmp.bpParchiRiserve;
        value.bpImmobileAreeInteresse = value.bpImmobileAreeInteresse || tmp.bpImmobileAreeInteresse;
        //this.localizzazioneComuni.push(formComune);
        this.mockComuniSelezionabili = this.mockComuniSelezionabili.filter(el => el.value != locComune.comuneId);
      });
      this.locSvc.localizzazione = value;
    }
    //prepopolamento shape file
    if (this.fascicolo.istanza.localizzazione &&
      this.fascicolo.istanza.localizzazione.allegaShapeFile)
    {
      this.allegatiShape = this.fascicolo.istanza.localizzazione.allegaShapeFile;
    }
  }

  addComune()
  {
    this.loadingService.emitLoading(true);
    let comuneIdScelto = this.formSelezioneComune.get('comune').value;
    if (comuneIdScelto == null) { alert("Selezionare un comune!!"); return; }
    let comune = this.mockComuniSelezionabili.find(el => el.value == comuneIdScelto);
    //aggiungo la vincolistica
    let obsBpParchi$ = this.httpDominio.getBpParchi(comune.linked);
    let obsUcpPaesaggi$ = this.httpDominio.getUcpPaesaggi(comune.linked);
    let obsBpImmobili$ = this.httpDominio.getBpImmobili(comune.linked);
    combineLatest(obsBpParchi$, obsUcpPaesaggi$, obsBpImmobili$)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        ([bpp, ucp, bpi]) =>
        {
          let formComune = this.buildFormComune(comune.value, comune.description);
          let comuneCompentenza = this.fascicolo.comuniCompetenza.find(el => el.enteId == comune.value);
          this.mockComuniSelezionati.push({ label: comuneCompentenza.descrizione, value: comuneCompentenza.enteId });
          formComune.get('ulterioriInformazioni').get('bpParchiEReserveOptions').setValue(bpp);
          formComune.get('ulterioriInformazioni').get('ucpPaesaggioRuraleOptions').setValue(ucp);
          formComune.get('ulterioriInformazioni').get('bpImmobileAreePubblicoOptions').setValue(bpi);
          this.mockComuniSelezionabili = this.mockComuniSelezionabili.filter(el => el.value != comuneIdScelto);
          //rimuovo dalla selezione il comune inserito
          this.formSelezioneComune.get('comune').reset();
          this.formSelezioneComune.get('comune').setValue(null);
          if (this.mockComuniSelezionabili.length > 0)
          {
            //preseleziono il primo
            this.formSelezioneComune.get('comune').setValue(this.mockComuniSelezionabili[0].value);
          }
          this.localizzazioneComuni.push(formComune);
          this.localizzazioneForm.markAsDirty();
          this.loadingService.emitLoading(false);
        }
      );
  }

  //Nessun Parco o Riserva è presente per il comune di BITETTO
  hasOptions(opzioni)
  {
    return opzioni && Array.isArray(opzioni) && opzioni.length > 0
    /* if (opzioni && Array.isArray(opzioni) && opzioni.length > 0)
    {
      return true;
    } */
  }

  buildFormComune(comuneId, descrizioneComune)
  {
    return this.fb.group({
      comuneId: [comuneId, Validators.required], ///hidden
      comune: [descrizioneComune,],//hidden
      indirizzo: ['',],
      numCivico: ['',],
      piano: ['',],
      interno: ['',],
      destUsoAtt: ['',],
      destUsoProg: ['',],
      siglaProvincia: ['',],
      dataRiferimentoCatastale: ['',],
      strade: [null,],
      particelle: this.fb.array([]),
      ulterioriInformazioni: this.fb.group({
        bpParchiEReserve: [null],
        ucpPaesaggioRurale: [null],
        bpImmobileAreePubblico: [null],
        bpParchiEReserveOptions: [null],//hidden
        ucpPaesaggioRuraleOptions: [null],//hidden
        bpImmobileAreePubblicoOptions: [null],//hidden
      })
    }, { validators: [this.validatoreStradeForm, this.validatoreDatiMinimiLocalizzazioneForm] });
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
    {
      this.alertData.display = false;
      if (event.extraData && event.extraData.operazione &&
        event.extraData.operazione == 'confermaCambioSelezione' && !event.extraData.confermato)
      {
        //non vuole perdere la selezione old e quindi non cancello nulla e riporto il select botton al valore precedente.
        //this.fullForm.controls['tipoSelezioneLocalizzazione'].setValue(event.extraData.oldSel);
        this.tipoSelezioneLocalizzazione = event.extraData.oldSel;
        this.alertData.extraData = {};
      }
    } else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'deleteAllRowParticelle':
            this.onDeleteAllParticelle(event.extraData.formComune);
            break;
          case 'confermaCambioSelezione':
            this.alertData.extraData.confermato = true;
            if (this.alertData.extraData.oldSel)
            {
              this.resetDatiLocalizzazione(this.alertData.extraData.oldSel);
            }
            this.tipoSelezioneLocalizzazione = event.extraData.newSel;
            break;
          /*case 'eliminaComune':
            this.doElimina(event.extraData.index);
            break;*/
          case 'confermaDeleteAllegato':
            this.deleteGeoAllegati(event.extraData.row);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  eliminaComune(index: number)
  {
    this.dialogService.showDialog(true,
      'ANNULA.CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
          this.doElimina(index);
        }
      },
      DialogType.WARNING,
      null);
  }

  doElimina(index: number)
  {
    let formGroup = this.localizzazioneComuni.at(index) as FormGroup;
    let comuneId = formGroup.get('comuneId').value;
    let comuneCompetenza = this.fascicolo.comuniCompetenza.find(el => el.enteId == comuneId);
    if (comuneCompetenza)
    {
      this.mockComuniSelezionabili.push({
        value: comuneCompetenza.enteId,
        description: comuneCompetenza.descrizione,
        linked: comuneCompetenza.codIstat
      });
      //spelo dai selezionabili nell'array delle particelle
      this.mockComuniSelezionati = this.mockComuniSelezionati.filter(el => el.value != comuneCompetenza.enteId);
    }
    this.onDeleteAllParticelle(formGroup, false);
    this.localizzazioneComuni.removeAt(index);
    this.localizzazioneForm.markAsDirty();
    this.salvaLocalizzazione();
  }


  /**
   * sezione per gli shape file... non ancora gestita...
   */
  /*
  addAllegaShapeFiles() {
    this.initState.forEach((item, i) => {
      console.log(item.descrizione, "DESC");
      this.getAllegatiShapeFiles.push(
        this.fb.group({
          descrizione: [item.descrizione],
          nome: [this.fascicolo.istanza.localizzazione && this.fascicolo.istanza.localizzazione.allegaShapeFile ? this.getAllegatiShapeFilesFields(i, 'nome') : '', Validators.required],
          data: [this.fascicolo.istanza.localizzazione && this.fascicolo.istanza.localizzazione.allegaShapeFile ? this.getAllegatiShapeFilesFields(i, 'data') : null],
          id: [this.fascicolo.istanza.localizzazione && this.fascicolo.istanza.localizzazione.allegaShapeFile ? this.getAllegatiShapeFilesFields(i, 'id') : null]
        })
      );
    });
  }

  get getAllegatiShapeFiles() {
    return (this.localizzazioneForm.get(
      'allegaShapeFile'
    ) as FormArray);
  }


  getAllegatiShapeFilesFields(index: number, field: string) {
    if (field === 'nome') {
      return this.fascicolo.istanza.localizzazione.allegaShapeFile[index].nome;
    } else if (field === 'data') {
      return this.fascicolo.istanza.localizzazione.allegaShapeFile[index].data;
    }
    else {
      return this.fascicolo.istanza.localizzazione.allegaShapeFile[index].id;
    }
  }
*/
  /*get localizazioniInfo() {
    return this.localizzazioneForm.get('localizazioniInfo');
  }*/


  /*uploadFileFromTable(event) {
    const file = event.file;
    const fileType = event.fileType;
    if (
      fileType ===
      'AREA INTERVENTO SHAPE FILE'
    ) {
      let container: any = {
        file: file,
        idPratica: this.fascicolo.id
      };
      this.onUploadFileShape.emit(container);
    }
  }

  onFileSelect(event) {
    if (event.files.length > 0) {
      const file: File = event.files[0];
      let container: any = {
        file: file,
        idPratica: this.fascicolo.id
      };
      this.onUploadFileShape.emit(container);
    }
  }

  public downloadFileShape(event: any): void {
    this.onDownloadFileShape.emit(event);
  }

  deleteAttachment(event) {
    this.onDeleteFileShape.emit(event);
  }*/

  /************************* METHODS FOR ESRI MAP   */

  /**
   * emitter calcolo particelle...
   * @param results 
   */
  segnalaErroreComuneNonTrovato()
  {
    this.dialogService.showDialog(true,
      'LOCALIZZAZIONE.SELEZIONARE_COMUNE',
      'ATTENZIONE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
        }
      },
      DialogType.INFORMATION,
      null);
  }

  result(results): void
  {
    /*if (this.indiceFormArray < 0 ||
      this.indiceFormArray > this.localizzazioneComuni.length - 1 ||
      !this.localizzazioneComuni.at(this.indiceFormArray)) {
      this.segnalaErroreComuneNonTrovato();
      return;
    }
    let comuneId = this.localizzazioneComuni.at(this.indiceFormArray).get('comuneId').value;
    if (!comuneId) {
      this.segnalaErroreComuneNonTrovato();
      return;
    }
    let comuneCompetenza = this.fascicolo.comuniCompetenza.find(comune => comune.enteId == comuneId);
    if (!comuneCompetenza) {
      this.segnalaErroreComuneNonTrovato();
      return;
    }*/
    //ora ho anche il codice catastale con cui filtrare le particelle
    //le riverso nel formarray del comune i-esimo, controllando che il codice catastale del comune sia corretto
    let mappaForm = this.getMapComuniForm();
    if (Array.isArray(results))
    {
      this.elaboraParticelleCatturate(results, mappaForm);
    }
  }

  elaboraParticelleCatturate(results: IdentifyResult[], mappa: Map<string, { comuneCompetenza: ComuniCompetenza; formGroupComune: FormGroup }>)
  {
    this.loadingService.emitLoading(true);
    var preesistenti: string[] = new Array();
    var fuoriComune: Set<String> = new Set<String>();
    let aggiunte = 0;
    let overflowed = 0;
    let oidsToDelete = new Set<string>();
    //negli attributi della feature (results[i]) mi arriva:
    // displayName
    // feature
    // +----attributes
    //      +----allegato:"0"   
    //      +----comune:"A662"
    //      +----foglio:"29"
    //      +----livello:"PARTICELLE"
    //      +----nome_comune:"BARI"
    //      +----numero:"175"
    //      +----objectid:"12321434"
    //      +----sezione:"A"
    //      +----shape:"Poligon"
    //      +----sviluppo:"0"
    //let formGroupComune = this.localizzazioneComuni.at(this.indiceFormArray) as FormGroup;
    //const control = formGroupComune.get('particelle') as FormArray;
    for (let i in results)
    {
      let compare = results[i].feature.attributes;
      //scarto le particelle senza numero (di solito sulle strade restituisce nessuna particella)
      if (!compare.numero || compare.numero.trim() == '') { continue; }
      //scarto quelle fuori dal comune scelto
      //if (compare.comune != comuneCompentenza.codCat) {
      if (!mappa.has(compare.comune))
      {
        oidsToDelete.add(compare.oid);
        fuoriComune.add(compare.nome_comune);
        continue;
      };
      //verifico che non esiste già
      let formGroupComune = mappa.get(compare.comune).formGroupComune;
      let particellePreesistenti: localizzazioneInfo[] = formGroupComune.get('particelle').value;
      let idxPreesistente = particellePreesistenti.find(particella =>
      {
        return particella.foglio == compare.foglio &&
          this.uniforma(particella.sezione) == this.uniforma(compare.sezione) &&
          particella.particella == compare.numero;
      });
      if (idxPreesistente)
      {
        preesistenti.push(compare.numero);
        oidsToDelete.add(compare.oid);
        continue;
      }
      if (particellePreesistenti.length + aggiunte >= CONST.MAX_PARTICELLE)
      {
        overflowed++;
        oidsToDelete.add(compare.oid);
        continue; //TROPPE PARTICELLE
      }
      let nuova = new localizzazioneInfo();
      nuova.foglio = compare.foglio;
      nuova.particella = compare.numero;
      nuova.sezione = compare.sezione;
      nuova.sub = null;
      nuova.livello = compare.livello;
      nuova.oid = compare.oid;
      nuova.codCat = mappa.get(compare.comune).comuneCompetenza.codCat;
      //this.sharedDataService.subjBuildingTable$.next({ riga: nuova, tabellaRef: "particelle_" + comuneCompentenza.enteId });
      //console.log("appendo:"+mappa.get(compare.comune).comuneCompetenza.enteId)
      //this.sharedDataService.subjBuildingTable$.next({ riga: nuova, tabellaRef: "particelle_" + mappa.get(compare.comune).comuneCompetenza.enteId });
      this.aggiungiFormParticella(nuova, formGroupComune.get('particelle') as FormArray, false);
      aggiunte++;
    }
    if (oidsToDelete.size > 0)
    {
      //cancello dal layer quelle non aggiunte
      let oidsToDel = [];
      oidsToDelete.forEach(oid => oidsToDel.push(oid))
      this.httpDominio.deleteOid(oidsToDel.join(',')).then((resp) => { this.aggiornaMappa++; });
    }
    let message = "";
    if (aggiunte > 0)
    {
      message += "Numero particelle aggiunte: " + aggiunte + " ";
    }
    if (fuoriComune.size > 0)
    {
      let comuniDescr = [];
      mappa.forEach(el => comuniDescr.push(el.comuneCompetenza.descrizione));
      message += "Alcune particelle sono state escluse perchè fuori dai comuni selezionati: " +
        comuniDescr.join(',') + "  (";
      //comuneCompentenza.descrizione + "  (";
      fuoriComune.forEach(comune => { message += " " + comune; })
      message += " ). ";
    }
    if (preesistenti.length > 0)
    {
      message += "Numero particelle preesistenti: " + preesistenti.length + " ";
    }
    if (overflowed > 0)
    {
      message += "Superato limite massimo di " + CONST.MAX_PARTICELLE + " scartate :" + overflowed + " ";
    }
    //qui sincronizzo il BE
    this.salvaLocalizzazione();
    if (message != "")
    {
      this.alertData.isConfirm = false;
      this.alertData.typ = "info";
      this.alertData.title = "";
      this.alertData.content = message;
      this.alertData.display = true;
    }
  }

  /*elaboraParticelleCatturate(results: IdentifyResult[], comuneCompentenza: ComuniCompetenza) {
    this.loadingService.emitLoading(true);
    var preesistenti: string[] = new Array();
    var fuoriComune: Set<String> = new Set<String>();
    let aggiunte = 0;
    let overflowed = 0;
    //negli attributi della feature (results[i]) mi arriva:
    // displayName
    // feature
    // +----attributes
    //      +----allegato:"0"   
    //      +----comune:"A662"
    //      +----foglio:"29"
    //      +----livello:"PARTICELLE"
    //      +----nome_comune:"BARI"
    //      +----numero:"175"
    //      +----objectid:"12321434"
    //      +----sezione:"A"
    //      +----shape:"Poligon"
    //      +----sviluppo:"0"
    let formGroupComune = this.localizzazioneComuni.at(this.indiceFormArray) as FormGroup;
    const control = formGroupComune.get('particelle') as FormArray;
    let particellePreesistenti: localizzazioneInfo[] = formGroupComune.get('particelle').value;
    for (let i in results) {
      let compare = results[i].feature.attributes;
      //scarto le particelle senza numero (di solito sulle strade restituisce nessuna particella)
      if (!compare.numero || compare.numero.trim() == '') { continue; }
      //scarto quelle fuori dal comune scelto
      if (compare.comune != comuneCompentenza.codCat) {
        fuoriComune.add(compare.nome_comune);
        continue;
      };
      //verifico che non esiste già
      let idxPreesistente = particellePreesistenti.find(particella => {
        return particella.foglio == compare.foglio &&
          particella.sezione == compare.sezione &&
          particella.particella == compare.numero;
      });
      if (idxPreesistente) {
        preesistenti.push(compare.numero);
        continue;
      }
      if (particellePreesistenti.length + aggiunte >= CONST.MAX_PARTICELLE) {
        overflowed++;
        continue; //TROPPE PARTICELLE
      }
      let nuova = new localizzazioneInfo();
      nuova.foglio = compare.foglio;
      nuova.particella = compare.numero;
      nuova.sezione = compare.sezione;
      nuova.sub = null;
      nuova.livello = compare.livello;
      //let particelleData=this.fascicolo.istanza.localizzazione.localizzazioneComuni.find(el=>el.comuneId==comuneCompentenza.enteId).particelle;
      //particellePreesistenti.push(nuova);
      //this.addItem(tmpData)
      this.sharedDataService.subjBuildingTable$.next({ riga: nuova, tabellaRef: "particelle_" + comuneCompentenza.enteId });
      aggiunte++;
    }
    //ALG 2018-01-16 => CR collegare siti ad enti
    let message = "";
    if (aggiunte > 0) {
      message += "Numero particelle aggiunte: " + aggiunte + " ";
    }
    if (fuoriComune.size > 0) {
      message += "Alcune particelle sono state escluse perchè fuori dal " +
        comuneCompentenza.descrizione + "  (";
      fuoriComune.forEach(comune => { message += " " + comune; })
      message += " ). ";
    }
    if (preesistenti.length > 0) {
      message += "Numero particelle preesistenti: " + preesistenti.length + " ";
    }
    if (overflowed > 0) {
      message += "Superato limite massimo di " + CONST.MAX_PARTICELLE + " scartate :" + overflowed + " ";
    }
    this.loadingService.emitLoading(false);
    if (message != "") {
      this.dialogService.showDialog(true,
        message,
        '',
        DialogButtons.OK_CANCEL,
        (buttonID: string): void => {
          if (buttonID == "1") {
          }
        },
        DialogType.INFORMATION,
        null);
    }
  }*/

  closeMap(): void
  {
    this.hasMap = !this.hasMap;
  }

  ricaricaMappa(ricarica: boolean): void
  {
    if (ricarica)
    {
      this.hasMap = !this.hasMap;
      setTimeout(() =>
      {
        this.hasMap = !this.hasMap;
      }, 200);
    }
  }
  onLocalizza(): void { this.hasMap = !this.hasMap; }
  /**********************END METHODS FOR ESRI MAP */

  apertoAccordion(event)
  {
    this.indiceFormArray = event.index;
  }
  chiusoAccordion(event)
  {
    this.indiceFormArray = -1;
  }

  private evaluateLocalization(loc: any): LocalizzazioneValues
  {
    let value: LocalizzazioneValues = { comuneSelezionato: false, bpParchiRiserve: false, bpImmobileAreeInteresse: false };
    if (loc)
    {
      value.comuneSelezionato = true;
      if (loc.ulterioriInformazioni)
      {
        if (loc.ulterioriInformazioni.bpParchiEReserve && loc.ulterioriInformazioni.bpParchiEReserve.length > 0)
          value.bpParchiRiserve = true;
        if (loc.ulterioriInformazioni.bpImmobileAreePubblico && loc.ulterioriInformazioni.bpImmobileAreePubblico.length > 0)
          value.bpImmobileAreeInteresse = true;
      }
    }
    return value;
  }

  /*public popolaTabella(event: any): void {
    (this.mainForm.get("localizzazione").get('allegaShapeFile').value).forEach((item, index) => {
      ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(index) as FormGroup).get('nome').patchValue(event.nomeFile);
      ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(index) as FormGroup).get('data').patchValue(new Date().toLocaleString());
      ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(index) as FormGroup).get('id').patchValue(event.idFile);
    });
  }*/

  private startWatchLocalizationsFormCheanges(): void
  {
    this.localizzazioneForm.valueChanges.subscribe(values =>
    {
      let value: LocalizzazioneValues = { comuneSelezionato: false, bpParchiRiserve: false, bpImmobileAreeInteresse: false };
      if (values.localizzazioneComuni && values.localizzazioneComuni.length > 0)
      {
        values.localizzazioneComuni.forEach(v =>
        {
          let tmp = this.evaluateLocalization(v);
          value.comuneSelezionato = value.comuneSelezionato || tmp.comuneSelezionato;
          value.bpParchiRiserve = value.bpParchiRiserve || tmp.bpParchiRiserve;
          value.bpImmobileAreeInteresse = value.bpImmobileAreeInteresse || tmp.bpImmobileAreeInteresse;
        });
      }
      this.locSvc.localizzazione = value;
    });
  }

  /**
   * validatore particelle, se il livello 
   * @param control 
   */
  /*validatoreParticellaForm(control: FormGroup): ValidationErrors | null {
    const livello = control.get('livello').value;
    const particella = control.get('particella').value;
    return livello && livello == "PARTICELLE" && !particella ? { 'particellaRequired': true } : null;
  }*/

  validatoreStradeForm(control: FormGroup): ValidationErrors | null
  {
    const strade = control.get('strade').value;
    const indirizzo = control.get('indirizzo').value;
    const numCivico = control.get('numCivico').value;
    return strade && (!indirizzo || !numCivico) ? { 'stradeRequired': true } : null;
  }

  validatoreDatiMinimiLocalizzazioneForm(control: FormGroup): ValidationErrors | null
  {
    const strade = control.get('strade').value;
    const particelle = control.get('particelle') as FormArray;
    return strade || (particelle && particelle.length > 0) ? null : { 'infoLocalizzazioneRequired': true };
  }

  /***************NEW localizzazione con tipoSelezione */

  get hasShape()
  {
    return this.localizzazioneForm.parent.get("hasShape").value;
  }
  set hasShape(val: boolean)
  {
    let formControl = this.localizzazioneForm.parent.get("hasShape") as FormControl;
    formControl.patchValue(val);
    formControl.markAsDirty();
  }

  hasSelezione(tipoSelezioneLocalizzazione: string)
  {
    let totParticelle = 0;
    for (let i = 0; i < this.localizzazioneComuni.length; i++)
    {
      if (this.localizzazioneComuni.at(i).get('particelle') &&
        this.localizzazioneComuni.at(i).get('particelle').value)
        totParticelle += this.localizzazioneComuni.at(i).get('particelle').value.length;
    }
    switch (tipoSelezioneLocalizzazione)
    {
      case 'PTC':
        return totParticelle > 0;
      case 'SHPF':
        return this.allegatiShape.length > 0 || totParticelle > 0;
      case 'SHPD':
        /**e' abbastanza difficile controllare  in sincrono se ci sono feature sull'edit layer, 
         * per questo aggiungiamo un campo hasShape che indica la presenza di geometrie in mappa*/
        return this.hasShape || totParticelle > 0;
      default:
        break;
    }
    return false;
  }

  get tipoSelezioneLocalizzazione()
  {
    return this.localizzazioneForm.parent.get("tipoSelezioneLocalizzazione").value;
  }
  set tipoSelezioneLocalizzazione(val: string)
  {
    let formControl = this.localizzazioneForm.parent.get("tipoSelezioneLocalizzazione") as FormControl;
    formControl.patchValue(val);
    formControl.markAsDirty();
  }

  myAlertWarning(msg?: string, title?: string, extraData?: any)
  {
    this.resetAlertData();
    this.alertData.display = true;
    this.alertData.isConfirm = true;
    //this.hasProsegui = false;
    this.alertData.typ = "warning";
    this.alertData.extraData = extraData
    if (title) this.alertData.title = title;
    if (msg)
      this.alertData.content = msg;
    else
      this.alertData.content = "Procedura fallita, riprovare per favore";
    this.loadingService.emitLoading(false);
  }

  clickSelezione(event)
  {
    let selezioneCorrente = this.tipoSelezioneLocalizzazione;
    let hasSel = this.hasSelezione(selezioneCorrente);
    if (event && event.option.value != selezioneCorrente &&
      selezioneCorrente != null && hasSel)
    {
      //chiedo conferma per azzerare i dati relativi alla selezione vecchia  
      let content = "Attenzione la selezione preesistente in " +
        this.tipiSelezioneLocalizzazione.find(el => el.value == selezioneCorrente).label + " verrà eliminata, Proseguo?";
      let extraData = { operazione: 'confermaCambioSelezione', oldSel: selezioneCorrente, newSel: event.option.value };
      this.myAlertWarning(content, "Azzeramento selezione", extraData);
    } else
    {
      let salva = this.salvaFascicolo;
      salva = salva.bind(this);
      setTimeout(salva, 200);
    }
  }

  applyEditsCompleteCallback()
  {
    let f = (event) =>
    {
      if (event.adds && event.adds.length > 0)
      {//sicuramente ha aggiunto una geometria!
        this.hasShape = true;
      } else
      {//forse ha tolto l'ennesima e dovrei interrogare il layer per capire se ce ne sono altre
        this.getOids().then(oids => oids && oids.length > 0 ? this.hasShape = true : this.hasShape = false);
      }
    };
    return f.bind(this);
  }

  async getOids(): Promise<any[]>
  {
    let idFascicolo = this.fascicolo.id;
    let _self = this;
    _self.loadingService.emitLoading(true);
    let ret = await loadModules([
      "esri/tasks/QueryTask",
      'esri/tasks/query',
      'esri/config',
      'esri/request'],
      { url: 'https://js.arcgis.com/3.26/' }
    ).then(async ([QueryTask, Query, esriConfig, esriRequest]) =>
    {
      //init 
      let oids = [];
      _self.addEsriRequestToken(esriRequest);
      esriConfig.defaults.io.proxyUrl = environment.webgis.urlProxy;
      esriConfig.defaults.io.alwaysUseProxy = false;
      esriConfig.defaults.io.corsEnabledServers.push("webadaptor.tz.it:6443");
      esriConfig.defaults.io.corsEnabledServers.push("webadaptor.tz.it");
      let urlBackEnd = new URL(environment.webgis.urlLocale);
      esriConfig.defaults.io.corsEnabledServers.push(urlBackEnd.host);
      //init end
      var queryTask = new QueryTask(CONST.EDIT_LAYER);
      var query = new Query();
      query.where = "((id_fascicolo = '" + idFascicolo + "'))";
      query.returnGeometry = false;
      query.outFields = ["*"];
      await queryTask.execute(query).then(async resultQuery =>
      {
        //console.log(resultQuery);
        if (resultQuery && resultQuery.features && resultQuery.features.length > 0)
        {
          let fc = resultQuery.features;
          fc.forEach(feature =>
          {
            oids.push(feature.attributes.oid);
          });
        }
        _self.loadingService.emitLoading(false);
      });
      return oids;
    });
    return ret;
  }

  aggiuntoShapeFile(allegato: Allegato)
  {
    this.allegatiShape.push(allegato);
  }


  /********************gestione file shape */

  download(row: Allegato)
  {
    this.allegatoSvc.downloadAllegatoFascicolo(row.id + "",this.fascicolo.id, '/istruttoria/allegati/download.pjson').subscribe(result =>
    {
      if (result.ok)
      {
        downloadFile(result.body, row.nome);
      }
    });
  }

  /*
  public deleteFileShape(event: any): void {
    this.loadingService.emitLoading(true);
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.allegatoId = event;
    this.dichiarazioniService.deleteFile(requestAllegato).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('nome').patchValue("");
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('data').patchValue(null);
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('id').patchValue(null);
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
*/

  delete(row: Allegato)
  {
    this.alertData.title = "Attenzione";
    this.alertData.content = "Cancellazione irreversibile, verranno cancellate tutte le geometrie, proseguo?";
    this.alertData.typ = "info";
    this.alertData.isConfirm = true;
    this.alertData.extraData =
    {
      operazione: "confermaDeleteAllegato",
      row: row
    }
    this.alertData.display = true;
  }

  deleteGeoAllegati(row: Allegato): void
  {
    this.loadingService.emitLoading(true);
    let requestAllegato: RequestAllegato = new RequestAllegato();
    requestAllegato.praticaId = this.fascicolo.id;
    requestAllegato.allegatoId = row.id + "";
    this.dichiarazioniService.deleteFile(requestAllegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          let index = this.allegatiShape.map(m => m.id).indexOf(row.id);
          if (index >= 0)
          {
            this.allegatiShape.splice(index, 1);
          }
          this.removeAllOidShapeFascicolo();
          /*
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('nome').patchValue("");
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('data').patchValue(null);
          ((this.mainForm.get("localizzazione").get('allegaShapeFile') as FormArray).at(0) as FormGroup).get('id').patchValue(null);
          */
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
    /*
    this.allegatoSvc.eliminaAllegato(row.id.toString()).subscribe(result => {
      if (result.codiceEsito === CONST.OK) {
        let index = this.allegatiShape.map(m => m.id).indexOf(row.id);
        if (index >= 0)
          {
            this.allegatiShape.splice(index, 1);
          }
          this.removeAllOidShapeFascicolo();
      }
    });
    */
  }

  /********************end gestione file shape */



  /**
   * resetto tutte le geometrie sul layer di editing
   */
  removeAllOidShapeFascicolo()
  {
    let _self = this;
    this.getOids().then(async oids =>
    {
      _self.loadingService.emitLoading(true);
      /*oids.forEach(element => {
        _self.rimuoviOidParticella(element);
      });*/
      let listStringOid = oids.join(",");
      //rimozione dal featureServer degli oid corrispondenti
      await _self.httpDominio.deleteOid(listStringOid);
      _self.hasShape = false;
      _self.aggiornaMappa++;
      _self.loadingService.emitLoading(false);
    }).catch(() => _self.loadingService.emitLoading(false));
  }

  /**
   * quando rimuovo dalla mappa una geometria....
   */
  rimuoviOidParticella(oidsShapeString: string)
  {
    //cerco le particelle con oid della geometria:
    let oidsShape = oidsShapeString.split(',');
    let removed = false;
    let formArrComuni = this.localizzazioneForm.get('localizzazioneComuni') as FormArray;
    for (let i = 0; i < formArrComuni.length; i++)
    {
      let formArrParticelle = formArrComuni.at(i).get('particelle') as FormArray;
      for (let k = formArrParticelle.length - 1; k >= 0; k--)
      {
        let oid = formArrParticelle.at(k).get('oid').value;
        if (oidsShape.findIndex(el => el == oid) >= 0)
        {
          removed = true;
          formArrParticelle.removeAt(k);
        }
      }
    }
    if (removed)
    {
      this.httpDominio.deleteOid(oidsShapeString).then(() =>
      {
        this.salvaLocalizzazione();
      });
    }
  }

  /**
  * Elimina la i-esima riga dalla tabella delle aree individuate
  * 
  * @param i 
  */
  onDeleteParticella(event: { formComune: FormGroup, indexParticella: number }): void
  {
    let formArrParticelle = event.formComune.get('particelle') as FormArray;
    let oid = formArrParticelle.at(event.indexParticella).get('oid').value;
    formArrParticelle.removeAt(event.indexParticella);
    if (oid)
    {
      this.httpDominio.deleteOid(oid).then((resp) => { this.aggiornaMappa++; });
      this.salvaLocalizzazione();
      //in teoria se non ha oid non sono obbligato a salvare subito
    }
  }

  askDeleteAllParticelle(formComune: FormGroup)
  {
    let formParticelle = formComune.get('particelle') as FormArray;
    if (formParticelle.length > 0)
    {
      this.myAlertWarning("Sei sicuro di voler cancellare tutte le particelle inserite?", "Elimina tutte le particelle", { operazione: 'deleteAllRowParticelle', formComune: formComune })
    }
  }

  onDeleteAllParticelle(formComune: FormGroup, salvaLocalizzazione: boolean = true): void
  {
    let oids = [];
    let formArrParticelle = formComune.get('particelle') as FormArray;
    while (formArrParticelle.length > 0)
    {
      if (formArrParticelle.at(0).get('oid').value)
      {
        oids.push(formArrParticelle.at(0).get('oid').value);
      }
      formArrParticelle.removeAt(0);
    }
    if (oids.length > 0)
    {
      this.httpDominio.deleteOid(oids.join(',')).then((resp) => { this.aggiornaMappa++; });
    }
    //cancello tutte le righe dal formarray particelle e chiamo il be per sincronizzare
    if (salvaLocalizzazione)
    {
      this.salvaLocalizzazione();
    }
  }

  zoomMap(event: { formComune: FormGroup, indexParticella: number })
  {
    console.log(event);
    this.query = null;
    let queryZoom: string;
    this.user = this.userService.getUser().username;
    let comuneCompetenza = this.fascicolo.comuniCompetenza.find(comune => comune.enteId == event.formComune.get('comuneId').value);
    let formArrParticelle = event.formComune.get('particelle') as FormArray;
    //if (zoomParticella.comuneIntervento.label != null) {
    if (comuneCompetenza.descrizione != null)
    {
      //queryZoom = "nome_comune='" + zoomParticella.comuneIntervento.label.toUpperCase() + "'";
      queryZoom = "COMUNE='" + comuneCompetenza.codCat + "'";
    }
    let formPart = formArrParticelle.at(event.indexParticella);
    if (formPart.get('sezione').value != null && formPart.get('sezione').value.trim() != '')
    {
      queryZoom += " AND SEZIONE='" + formPart.get('sezione').value + "'";
    }
    if (formPart.get('foglio').value != null && formPart.get('foglio').value.trim() != '')
    {
      queryZoom += " AND FOGLIO='" + formPart.get('foglio').value + "'";
    }
    if (formPart.get('particella').value != null && formPart.get('particella').value.trim() != '')
    {
      queryZoom += " AND NUMERO='" + formPart.get('particella').value + "'";
    }
    var _self = this
    loadModules(["esri/tasks/QueryTask", 'esri/tasks/query', "esri/geometry/Point", "esri/SpatialReference"]
      , { url: 'https://js.arcgis.com/3.26/' }).then(([QueryTask, Query, Point, SpatialReference]) =>
      {

        var query = new Query();
        query.where = "( " + queryZoom + ")";
        query.returnGeometry = true;
        //query.outFields = ["*"];
        let queryTask = new QueryTask(CONST.CATASTO_LAYER + "/2");
        queryTask.execute(query).then((result) =>
        {
          if (result && result.features && result.features.length > 0)
          {
            let temppoint = result.features[0].geometry.rings[0][0]
            _self.geometryZoom = new Point(temppoint[0], temppoint[1], new SpatialReference({ wkid: 32633 }));
            _self.aggiornaMappa++;
          }
        });
      });
    this.hasMap = true;
  }

  getComuneCompetenzaComuneId(comuneId: number): ComuniCompetenza
  {
    let comuneCompetenza = this.fascicolo.comuniCompetenza.find(comune => comune.enteId == comuneId);
    return comuneCompetenza ? comuneCompetenza : null;
  }

  getCatastaleByComuneId(comuneId: number)
  {
    let comuneCompetenza = this.getComuneCompetenzaComuneId(comuneId);
    return comuneCompetenza ? comuneCompetenza.codCat : null;
  }

  uniforma(str: string)
  {
    if (!str)
    {
      return "";
    } else
      return str.trim();
  }

  checkDuplicato(partTemp: localizzazioneInfo, showMessage: boolean): boolean
  {
    let mappaForm = this.getMapComuniForm();
    console.log('mappaForm:', mappaForm);
    let formParticelle = mappaForm.get(partTemp.codCat).formGroupComune.get('particelle');
    console.log('formParticelle:', formParticelle);
    if (!formParticelle) return; //puoi inserire
    var compare = partTemp;
    var found = false;
    let formDataParticelle = (formParticelle as FormArray).getRawValue();
    for (let s in formDataParticelle)
    {
      var compareTwo = formDataParticelle[s];
      if (
        compare.foglio == compareTwo.foglio &&
        this.uniforma(compare.sezione) == this.uniforma(compareTwo.sezione) &&
        compare.particella == compareTwo.particella
      )
      {
        found = true;
      }
    }
    if (found && showMessage)
    {
      this.myAlertInfo("Questa particella risulta già inserita pertanto non verrà considerata");
    }
    return found;
  }

  resetAlertData()
  {
    this.alertData.isConfirm = false;
    this.alertData.display = false;
    this.alertData.extraData = null,
      this.alertData.typ = "";
    this.alertData.title = "";
    this.alertData.content = "";
    this.alertData.labelAlertChiudi = 'generic.chiudi';
    this.alertData.labelAlertOk = 'generic.ok';
  }


  myAlertInfo(msg?: string, title?: string, extraData?: any, labelChiudi?: string): void
  {
    this.resetAlertData();
    labelChiudi ? this.alertData.labelAlertChiudi = labelChiudi : this.alertData.labelAlertChiudi = 'generic.prosegui';
    this.alertData.display = true;
    this.alertData.isConfirm = false;
    this.alertData.typ = "info";
    //this.hasProsegui = true;
    this.alertData.extraData = extraData
    title ? this.alertData.title = title : this.alertData.title = "";
    if (msg)
      this.alertData.content = msg;
    else
      this.alertData.content = "Procedura fallita, riprovare per favore";
    this.loadingService.emitLoading(false);
  }

  public queryEsri(particella: localizzazioneInfo)
  {
    loadModules(["esri/tasks/query"], { url: 'https://js.arcgis.com/3.26/' }).then(
      ([Query]) =>
      {
        let query = new Query();
        if (particella.sezione)
        {
          query.where = "((COMUNE = '" + particella.codCat + "')" +
            " AND (sezione = '" + particella.sezione + "')" +
            " AND (foglio = '" + particella.foglio + "')" +
            " AND (numero = '" + particella.particella + "'))";
        } else
        {
          query.where = "((COMUNE = '" + particella.codCat + "') AND (foglio = '" + particella.foglio + "') AND (numero = '" + particella.particella + "'))";
        }
        query.returnGeometry = true;
        query.outFields = ["*"];
        //aggiungo alla feature map il risultato della query quindi se ci sono particelle aggiunge lo shape alla mappa
        this.applyEsriQuery(query, particella)
      });
  }

  //costruisco una mappa<codiceCatastale,{}>
  getMapComuniForm(): Map<string, { comuneCompetenza: ComuniCompetenza; formGroupComune: FormGroup }>
  {
    let mappaForm = new Map<string, { comuneCompetenza: ComuniCompetenza; formGroupComune: FormGroup }>();
    for (let i = 0; i < this.localizzazioneComuni.length; i++)
    {
      let comuneId = this.localizzazioneComuni.at(i).get('comuneId').value;
      let comuneCompetenza = this.fascicolo.comuniCompetenza.find(comune => comune.enteId == comuneId);
      mappaForm.set(comuneCompetenza.codCat,
        { comuneCompetenza: comuneCompetenza, formGroupComune: this.localizzazioneComuni.at(i) as FormGroup });
    }
    return mappaForm;
  }

  public showResults(results, idFascicolo)
  {

    if (results.features.length == 0)
    {
      this.myAlertInfo("Non sono state trovate particelle corrispondenti ai dati immessi, l'inserimento avverrà comunque",
        "Particella non presente");
      this.loadingService.emitLoading(false);
      return new Promise((resolve, reject) => { resolve("0") });
    }
    if (results.features.length > 1)
    {
      results.features.forEach(element => {
        attributesLower(element.attributes);
      });
      this.visibleFormSezione = true;
      this.sceltaSezione = results.features;
      this.loadingService.emitLoading(false);
      return new Promise((resolve, reject) => { resolve("") });
    }
    var polylineGraphic = this.Graphic.setGeometry(results.features[0].geometry);

    polylineGraphic.setAttributes({ 'is_custom': 1, 'id_fascicolo': idFascicolo });
    //results.features[0].geometry.attributes['user_id'] = _self.user;

    return new Promise(async (resolve) =>
    {
      this.editLayerPolygon.applyEdits(
        [polylineGraphic]
      ).then((editsResult) =>
      {
        if (editsResult.length > 0)
        {
          if (editsResult[0].objectId)
          {
            let oid: number = parseInt(editsResult[0].objectId)
            resolve(oid);
          }
          resolve("");
        }
      });
    });
  }


  public applyEsriQuery(query: string, particella: localizzazioneInfo)
  {
    loadModules(["esri/tasks/QueryTask"], { url: 'https://js.arcgis.com/3.26/' }).then(([QueryTask]) =>
    {
      let queryTask = new QueryTask(CONST.CATASTO_LAYER + "/2");
      queryTask.execute(query).then((result) =>
      {

        this.showResults(result, this.fascicolo.id).then((result_oid: number) =>
        {
          if (result_oid)
          {
            particella.oid = result_oid == 0 ? null : result_oid;
            let mappaForm = this.getMapComuniForm();
            let formComune = mappaForm.get(particella.codCat).formGroupComune;
            let formArrParticelle = formComune.get('particelle') as FormArray;
            this.aggiungiFormParticella(particella, formArrParticelle, true);
            this.submitted_area = false;
            this.aggiornaMappa++;
            this.formInserimentoParticella.reset();
            this.formInserimentoParticella.get('comuneIntervento').patchValue(formComune.get('comuneId').value);
            this.loadingService.emitLoading(false);
          }
        });
      });
    });
  }

  addParticellaManuale()
  {
    this.submitted_area = true;
    //alert('verifico i dati inseriti e se ok...');
    console.log('', this.formInserimentoParticella.getRawValue())
    if (this.formInserimentoParticella.valid)
    {
      this.loadingService.emitLoading(true);
      let comuneId = this.formInserimentoParticella.get('comuneIntervento').value;
      let partTemp = new localizzazioneInfo();
      //partTemp.comuneIntervento.label = this.formInserimentoParticella.get('comuneIntervento').value.label.toUpperCase();

      partTemp.codCat = this.getComuneCompetenzaComuneId(comuneId).codCat;
      partTemp.livello = 'PARTICELLE';
      partTemp.foglio = this.formInserimentoParticella.get('foglioIntervento').value;
      partTemp.sezione = this.formInserimentoParticella.get('sezioneIntervento').value;
      partTemp.particella = this.formInserimentoParticella.get('particellaIntervento').value;
      partTemp.note = this.formInserimentoParticella.get('noteIntervento').value;
      // cerco la particella nell'array
      if (!this.checkDuplicato(partTemp, true))
      {
        this.queryEsri(partTemp);
      }
    }
  }

  addParticelleSelected(result)
  {
    console.log("sezione", result)
    let arrayInput = result.querySelectorAll("input");
    let formData = this.formInserimentoParticella.getRawValue();
    let comuneId = this.formInserimentoParticella.get('comuneIntervento').value;
    let partTemp = new localizzazioneInfo();
    partTemp.codCat = this.getComuneCompetenzaComuneId(comuneId).codCat;
    partTemp.livello = 'PARTICELLE';
    partTemp.foglio = this.formInserimentoParticella.get('foglioIntervento').value;
    partTemp.sezione = this.formInserimentoParticella.get('sezioneIntervento').value;
    partTemp.particella = this.formInserimentoParticella.get('particellaIntervento').value;
    partTemp.note = this.formInserimentoParticella.get('noteIntervento').value;
    this.sendPartSel = false;
    this.checkPartSel = false;
    arrayInput.forEach(item =>
    {
      if (item.checked)
      {
        this.checkPartSel = true;
        this.loadingService.emitLoading(true);
        partTemp.sezione = item.getAttribute("value");
        if (!this.checkDuplicato(partTemp, true))
        {
          this.queryEsri(partTemp);
        }
        this.visibleFormSezione = false;
      }
    });
    this.sendPartSel = true;
  }


  //per ogni chiamata verso un layer, aggiungo il token
  addEsriRequestToken(esriRequest: any)
  {
    esriRequest.setRequestPreCallback(function (ioArgs)
    {
      try
      {
        if (ioArgs.url.indexOf(CONST.EDIT_LAYER) >= 0)
        {
          let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
          ioArgs.headers['Authorization'] = "Bearer " + cookieStorage.getItem("access_token");
        }
      } catch (e)
      {
        console.log(e)
      }
      return ioArgs;
    });
  }

  prepareFormBuilder(fields: TableConfig[], defaults?: any)
  {
    const form = {};
    fields.forEach((item) =>
    {
      return (form[item.field] = ['', item.validators]);
    });
    return form;
  }

  aggiungiFormParticella(particellaData: any, formParticelle: FormArray, salva: boolean = true)
  {
    //dovrei anche salvare...
    let newParticellaForm = this.fb.group({ ...this.prepareFormBuilder(this.locationTableHeaders) });
    formParticelle.push(newParticellaForm);
    ViewMapper.mapObjectToForm(newParticellaForm, particellaData);
    if (salva)
    {
      this.salvaLocalizzazione();
    }
  }

  resetAllParticelleComuni(salva: boolean = true)
  {
    let formArrComuni = this.localizzazioneForm.get('localizzazioneComuni') as FormArray;
    for (let i = 0; i < formArrComuni.length; i++)
    {
      this.onDeleteAllParticelle(formArrComuni.at(i) as FormGroup, false);
    }
    if (salva) { this.salvaLocalizzazione(); }
  }

  resetDatiLocalizzazione(oldSel: string)
  {
    //console.log('reset dati localizzazione '+oldSel)    
    this.resetAllParticelleComuni(false);
    switch (oldSel)
    {
      case 'PTC':
        break;
      case 'SHPF':
        this.allegatiShape.forEach(
          el =>
          {
            this.deleteGeoAllegati(el);
          });

        this.hasShape = false;
        break;
      case 'SHPD':
        this.removeAllOidShapeFascicolo();
        this.hasShape = false;
        break;
      default:
        break;
    }
    this.salvaFascicolo();
  }

  salvaFascicolo()
  {
    this.locSvc.salvaFascicolo$.next(true);
  }

  salvaLocalizzazione()
  {
    this.salvaFascicolo();
    /*
    this.loadingService.emitLoading(true);
    this.autPaesSvc.salvaLocalizzazioneFascicolo(this.localizzazioneForm.getRawValue(),this.id)
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(()=>{
    this.loadingService.emitLoading(false);
    this.localizzazioneForm.markAsUntouched();
  });*/
  }

  filtraErrori(errors, key: string)
  {
    if (!errors)
    {
      return errors;
    }
    if (errors && errors[key])
    {
      let obj = {};
      obj[key] = errors[key];
      return obj;
    }
    return null;
  }

}
