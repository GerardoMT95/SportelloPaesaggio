import { FascicoloService } from './../../../../shared/services/http-fascicolo.service';
import { SchedaTecnicaConst } from './../../constants/scheda-tecnica-const';
import { Component, OnDestroy, OnInit, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, combineLatest, Observable, Subject, forkJoin } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { SchedaTecnicaService } from 'src/app/shared/services/scheda-tecnica/scheda-tecnica.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { IButton } from './../../../../core/models/dialog.model';
import { SelectOption } from './../../../../shared/components/select-field/select-field.component';
import { CONST } from './../../../../shared/constants';
import { ConfigOption, EffetiMitigazione, EventualiProcedimenti, HierarchicalOption, Inquadramento, Legittimita, PareriAtti, ProcedureEdilizie, SchedaTecnica, SchedaTecnicaDescrizione, Vincolistica, Fascicolo, DestinazioneUrbanistica, Dichiarazione, PPTR } from './../../../../shared/models/models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { SharedDataService } from './../../../../shared/services/shared-data/shared-data.service';
import { updateAllFormValidity, printFormErrors } from 'src/app/shared/functions/generic.utils';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';


@Component({
  selector: 'app-compilazione-scheda-tecnica-page',
  templateUrl: './compilazione-scheda-tecnica-page.component.html',
  styleUrls: ['./compilazione-scheda-tecnica-page.component.scss'],
  providers:[SchedaTecnicaFormService]
})
export class CompilazioneSchedaTecnicaPageComponent extends AbstractInputPage implements OnInit, OnDestroy
{
  public activeIndex: number;//indice del pannello
  public descOptions: HierarchicalOption[] = [];
  public pptrTable: ConfigOption[] = [];
  protected unsubscribe$: Subject<void> = new Subject<void>();
  protected fascicoloSubject: BehaviorSubject<Fascicolo> = new BehaviorSubject<Fascicolo>(this.sharedData.fascicolo);
  public validation: boolean = false;
  public mostraDialog: boolean = false;
  public invalidFields: Array<any> = new Array<any>();
  public casellaDiControllo: { [key: string]: Partial<SelectOption>[] };
  doValidate: string;
  loadedFascicolo = new Subject<boolean>();
  
  constructor(public route: ActivatedRoute,
    public router: Router,
    public fb: FormBuilder,
    private fascicoloStore: FascicoloStore,
    public dialogService: DialogService,
    private service: SchedaTecnicaService,
    private fascicoloService: FascicoloService,
    private dominio: HttpDominioService,
    private sharedData: SharedDataService,
    private loading: LoadingService,
    private stFormService:SchedaTecnicaFormService)
  {
    super(dialogService, fb, router,route);
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
    this.doValidate = this.route.snapshot.queryParamMap.get('doValidate');
    this.tabFormNames = SchedaTecnicaConst.tabFormNames;
    this.casellaDiControllo = SchedaTecnicaConst.casellaDiControllo;
  }

  ngOnInit()
  {
    this.fascicoloStore.setBreadcrumbs([{ label: 'Dettaglio' }, { label: 'Scheda Tecnica' }]);
    this.mainForm = this.fb.group({ valida: [false] });
    let fascicolo = new Fascicolo();
    fascicolo.codicePraticaAppptr = this.codiceFascicolo;
    this.loading.emitLoading(true);
    this.fascicoloService.getFascicolo(fascicolo).then(fr =>
    {
      if (fr.codiceEsito === CONST.OK && fr.payload)
      {
        fascicolo = fr.payload;
        this.sharedData.fascicolo = fascicolo;
        this.fascicoloSubject.next(fascicolo);
        //this.loadedFascicolo.next(true);
        let tmp = fascicolo.schedaTecnica.descrizione;
        fascicolo.schedaTecnica.descrizione = null;
        ViewMapper.mapObjectToForm(this.mainForm, this.fascicolo.schedaTecnica);
        fascicolo.schedaTecnica.descrizione = tmp;
        this.mainForm.controls.valida.setValue(this.fascicolo.validatoSchedaTecnica);
        let pptrTable$ = this.dominio.findUlterioriContestiPaesaggisticiHierarchical(parseInt(this.fascicolo.tipoProcedimento));
        let tipiEQualificazioni$ = this.dominio.findTipiEQualificazioniHasOptionHierarchical(parseInt(this.sharedData.fascicolo.tipoProcedimento));
        combineLatest(tipiEQualificazioni$, pptrTable$)
          .pipe(takeUntil(this.unsubscribe$))
          .subscribe(([tpResponse, pptrTableResponse]) =>
          {
            if (tpResponse.codiceEsito === CONST.OK && tpResponse.payload)
              this.descOptions = tpResponse.payload;
            //console.log(tpResponse)
            if (pptrTableResponse.codiceEsito === CONST.OK && pptrTableResponse.payload)
              this.pptrTable = pptrTableResponse.payload;
            this.loading.emitLoading(false);
            //se sposto piu' giu' perdevo l'eevnto di emit dei componenti child
            this.onFineBuildForm();
            this.loadedFascicolo.next(true);
          });
      }
    })

  }

  private onFineBuildForm(){
    let subFormObs=[
      this.stFormService.descrizioneFormComplete$,
      this.stFormService.destinazioneUrbanisticaFormComplete$,
      this.stFormService.leggittimitaUrbanisticaFormComplete$,
      this.stFormService.procedureEdilizieFormComplete$,
      this.stFormService.eventualiProcedimentiFormComplete$,
      this.stFormService.pareriAttiFormComplete$,
      this.stFormService.pptrFormComplete$,
      this.stFormService.vincolisticaFormComplete$,
      this.stFormService.dichiarazioneFormComplete$,
      this.stFormService.pareriTableComplete$,
      this.stFormService.attiTableComplete$,
      this.stFormService.leggittimitaTableComplete$,
    ];
    if(this.fascicolo.tipoProcedimento=="2"){
      subFormObs.push(this.stFormService.inquadramentoFormComplete$);
      subFormObs.push(this.stFormService.effettiConsMitigazioneFormComplete$);
    }
    //attendo che tutti i componenti child hanno buildato il loro subform  
    //prima di lanciare la validazione per poter visualizzare il messaggio con i pannelli con campi invalidi
    combineLatest(subFormObs)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((el) => {
        if (this.doValidate == "true") {
          //setTimeout(() => {
            this.loading.emitLoading(true);
            this.doValidate = "";
            this.valida();
            this.loading.emitLoading(false);
          //}, 200);
          
        }
        if(this.toDisableEditFascicolo(this.fascicolo))
          {
            this.mainForm.disable();
          }
      });
  }

  ngOnDestroy(): void { this.unsubscribe$.next(); }

  get fascicolo(): Fascicolo { return this.sharedData.fascicolo; }
  get descrizione(): SchedaTecnicaDescrizione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.descrizione : null; }
  get pptr(): PPTR { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.pptr : null; }
  get legittimita(): Legittimita { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.legittimita : null; }
  get inquadramento(): Inquadramento { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.inquadramento : null; }
  get effettiMitigazione(): EffetiMitigazione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.effetiMitigazione : null; }
  get eventualiProcedimenti(): EventualiProcedimenti { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.eventualiProcedimenti : null; }
  get pareriEAtti(): PareriAtti { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.parreriEAtti : null; }
  get vincolistica(): Vincolistica { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.vincolistica : null; }
  get procedureEdilizie(): ProcedureEdilizie { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.procedureEdilizie : null; }
  get destinazioneUrbanistica(): DestinazioneUrbanistica[] { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.destinazione : null; }
  get dichiarazione(): Dichiarazione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.dichiarazione : null; }

  public saveOperation(callback?:any): void
  {
    updateAllFormValidity(this.mainForm);
    let schedaTecnica: SchedaTecnica = this.lavoraSchedaTecnica(this.mainForm.getRawValue());
    this.loading.emitLoading(true);
    this.service.saveSchedaTecnica(schedaTecnica).subscribe(response =>
    {
      this.loading.emitLoading(false);
      let message: string = "generic.error";
      let title: string = "SAVE_FAILED_ST";
      let dialogType: DialogType = DialogType.ERROR;
      let buttons: IButton[] = DialogButtons.CHIUDI;
      if (response.codiceEsito === CONST.OK && response.payload)
      {
        let fascicolo = this.sharedData.fascicolo;
        fascicolo.schedaTecnica = response.payload;
        this.sharedData.fascicolo = fascicolo;
        this.mainForm.markAsUntouched();
        this.mainForm.markAsPristine();
        if(callback){
          callback();
        }else{
          message = "SAVED_SUCESSFULLY_MESSAGE";
          title = "SAVE_DOCUMENT_TITLE";
          dialogType = DialogType.SUCCESS;
          this.dialogService.showDialog(true, message, title, buttons, (buttonID: string): void =>
          {
            console.log('Button callback:', buttonID);
          }, dialogType);
        }
      }

    });
  }

  public salva(): void { this.saveOperation(); }

  private lavoraSchedaTecnica(values: any): SchedaTecnica
  {
    let scheda: SchedaTecnica;
    //descrizione
    let tmp = values.descrizione.tipoIntervento.tipologiaDiIntervento;
    values.descrizione.tipoIntervento.tipologiaDiIntervento = tmp && tmp.length > 0 ? tmp[0].value : null;
    tmp = values.descrizione.caratterizzazioneIntervento.durata;
    values.descrizione.caratterizzazioneIntervento.durata = tmp && tmp.length > 0 ? tmp[0].value : null;
    //legittimità
    values = this.aggiustamentiLegittimitaUrbanistica(values);
    //inquadramento
    values = this.aggiustamentiInquadramento(values);
    //eventuali procedimenti
    values = this.aggiustamentiEventualiProcedimenti(values);
    //procedure edilizie
    values = this.aggiustamentiProcedureEdilizie(values);
    //pptr
    values.pptr.ulteririContestiPaesaggistici = values.pptr.ulteririContestiPaesaggistici.map(m => { return { codice: m.value, specifica: m.text } });
    //altro
    scheda = values;
    scheda.idPratica = this.sharedData.fascicolo.id;
    return scheda;
  }

  private aggiustamentiLegittimitaUrbanistica(schedaTecnica: SchedaTecnica): SchedaTecnica
  {
    if (schedaTecnica.legittimita)
    {
      if (schedaTecnica.legittimita.tipoLegittimitaUrbanistica === 1)
      {
        schedaTecnica.legittimita.legittimitaInfo = [];
      }
      else if (schedaTecnica.legittimita.tipoLegittimitaUrbanistica === 2)
      {
        schedaTecnica.legittimita.legittimitaUrbanstica = null;
      }
      else if (!schedaTecnica.legittimita.tipoLegittimitaUrbanistica)
      {
        schedaTecnica.legittimita.legittimitaUrbanstica = null;
      }
      if (schedaTecnica.legittimita.tipoLegittimitaPaesaggistica === 1)
      {
        schedaTecnica.legittimita.autorizzatoPaesaggisticamenteInfo = [];
      }
      else if (schedaTecnica.legittimita.tipoLegittimitaPaesaggistica === 2)
      {
        schedaTecnica.legittimita.dettaglioLegittimitaPaesaggistica = null;
      }
    }
    return schedaTecnica;
  }

  private aggiustamentiInquadramento(schedaTecnica: SchedaTecnica): SchedaTecnica
  {
    if (schedaTecnica.inquadramento)
    {
      if (schedaTecnica.inquadramento.contestoPaesaggInterv !== 10)
      {
        schedaTecnica.inquadramento.contestoPaesaggIntervSpecifica = null;
      }
      if (schedaTecnica.inquadramento.destinazioneUso !== 6)
      {
        schedaTecnica.inquadramento.destinazioneUsoSpecifica = null;
      }
      if (schedaTecnica.inquadramento.morfologiaConPaesag !== 7)
      {
        schedaTecnica.inquadramento.morfologiaConPaesagSpecifica = null;
      }
    }
    return schedaTecnica;
  }

  private aggiustamentiEventualiProcedimenti(schedaTecnica: SchedaTecnica): SchedaTecnica
  {
    if (schedaTecnica.eventualiProcedimenti)
    {
      if (schedaTecnica.eventualiProcedimenti.contenzisoAtto === false)
      {
        schedaTecnica.eventualiProcedimenti.descrizione = null;
      }
      else if (schedaTecnica.eventualiProcedimenti.contenzisoAtto === true && 
          schedaTecnica.eventualiProcedimenti.descrizione && 
          schedaTecnica.eventualiProcedimenti.descrizione.length === 0)
      {
        schedaTecnica.eventualiProcedimenti.descrizione = null;
      }
      if (schedaTecnica.eventualiProcedimenti.descrizione && schedaTecnica.eventualiProcedimenti.descrizione.length === 0)
      {
        schedaTecnica.eventualiProcedimenti.descrizione = null;
      }
    }
    return schedaTecnica;
  }

  private aggiustamentiProcedureEdilizie(schedaTecnica: SchedaTecnica): SchedaTecnica
  {
    if (schedaTecnica.procedureEdilizie)
    {
      if (schedaTecnica.procedureEdilizie.tipoIntervento === 1)
      {
        schedaTecnica.procedureEdilizie.detagglio = null;
        schedaTecnica.procedureEdilizie.pressoData = null;
        schedaTecnica.procedureEdilizie.espressoData = null;
        if (schedaTecnica.procedureEdilizie.motivazione && schedaTecnica.procedureEdilizie.motivazione.length === 0)
        {
          schedaTecnica.procedureEdilizie.motivazione = null;
        }
      }
      else if (schedaTecnica.procedureEdilizie.tipoIntervento === 2)
      {
        schedaTecnica.procedureEdilizie.motivazione = null;
      }
      if (schedaTecnica.procedureEdilizie.motivazione && schedaTecnica.procedureEdilizie.motivazione.length === 0)
      {
        schedaTecnica.procedureEdilizie.motivazione = null;
      }
    }
    return schedaTecnica;
  }

  annulla() { 
    super.annula();
  }

  valida(){
    this.saveOperation(this.validaFe.bind(this));
  }

  validaFe()
  {
    this.validation = true;
    this.mainForm.get('valida').patchValue(true);
    this.validation = true;
    updateAllFormValidity(this.mainForm);
    let schedaTecnica: SchedaTecnica = this.lavoraSchedaTecnica(this.mainForm.getRawValue());
    this.invalidFields = [];
    if (!this.mainForm.get("descrizione").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 0,
        sezione: "Descrizione",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("destinazione").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 1,
        sezione: "Destinazione Urbanistica",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("legittimita").valid)
    {
      printFormErrors(this.mainForm.get("legittimita") as FormGroup, "legittimita");
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 2,
        sezione: "Legittimità Urbanistica",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("procedureEdilizie").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 3,
        sezione: "Procedure Edilizie",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (this.mainForm.get("inquadramento") && !this.mainForm.get("inquadramento").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 4,
        sezione: "Inquadramento",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (this.mainForm.get("effetiMitigazione") && !this.mainForm.get("effetiMitigazione").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: 5,
        sezione: "Effetti Mitigazione",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("eventualiProcedimenti").get("contenzisoAtto").valid ||
        !this.mainForm.get("eventualiProcedimenti").get("descrizione").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: Number(this.fascicolo.tipoProcedimento) === 2 ? 6 : 4,
        sezione: "Eventuali Procedimenti",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("parreriEAtti").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: Number(this.fascicolo.tipoProcedimento) === 2 ? 7 : 5,
        sezione: "Pareri e Atti",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("pptr").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: Number(this.fascicolo.tipoProcedimento) === 2 ? 8 : 6,
        sezione: "PPTR",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("vincolistica").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: Number(this.fascicolo.tipoProcedimento) === 2 ? 9 : 7,
        sezione: "Vincolistica",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (!this.mainForm.get("dichiarazione").valid)
    {
      this.mostraDialog = true;
      this.invalidFields.push({
        indexPannello: Number(this.fascicolo.tipoProcedimento) === 2 ? 10 : 8,
        sezione: "Dichiarazione",
        messaggi: ["Ricontrollare le informazioni inserite, alcuni campi sono obbligatori"]
      });
    }
    if (this.invalidFields.length > 0)
    {//mi fermo al fe
      this.mostraDialog = true;
    }
    else
    {
      this.validazioneBE();
    }
  }

  get fascicolo$(): Observable<Fascicolo> { return this.fascicoloSubject.asObservable(); }

  private validazioneBE(): void
  {
    this.loading.emitLoading(true);
    this.service.valida(this.fascicolo.id).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if (response.codiceEsito === "OK")
      {
        console.log(response.payload);
        this.dialogService.showDialog(true, 'VALIDATION.VALIDATION_SUCCESS_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
          DialogButtons.CHIUDI, null, DialogType.SUCCESS);
      }
      /*else
        this.dialogService.showDialog(true, response.descrizioneEsito, "Errore", DialogButtons.CHIUDI, null, DialogType.ERROR);*/
      
    },
    error => this.dialogService.showDialog(true, error.message, "Errore", DialogButtons.CHIUDI, null, DialogType.ERROR));
  }

  public focusTab(indiceTab: number): void
  {
    this.mostraDialog = false;
    this.activeIndex = indiceTab;
  }

}
