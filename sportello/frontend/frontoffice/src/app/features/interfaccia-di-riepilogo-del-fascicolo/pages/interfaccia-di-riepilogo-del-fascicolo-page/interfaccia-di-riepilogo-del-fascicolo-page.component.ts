import { AllegatoService } from './../../../../shared/services/allegato.service';
import { Component, EventEmitter, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { paths } from 'src/app/app-routing.module';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import
  {
    ButtonType,
    DialogButtons
  } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { isInIstruttoria, downloadFile } from 'src/app/shared/functions/generic.utils';
import { AltroTitolare, AttivitaDaEspletareEnum, Fascicolo,  PagamentoDto,  ValidInfoDto } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../shared/constants';
import { IntegrazioneDocumentale } from './../../../../shared/models/models';
import { IntegrazioneDocumentaleService } from './../../../../shared/services/Integrazione-documentale/integrazione-documentale.service';
import { SharedDataService } from './../../../../shared/services/shared-data/shared-data.service';
import { TranslateService } from '@ngx-translate/core';
import { IButton } from 'src/app/core/models/dialog.model';
import { PagoPaService } from 'src/app/shared/services/pago-pa.service';
import { environment } from 'src/environments/environment';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';
import { SchedaTecnicaService } from 'src/app/shared/services/scheda-tecnica/scheda-tecnica.service';
import { BaseResponse } from 'src/app/shared/components/model/base-response';
import { CambioOwnershipService } from 'src/app/features/cambio-ownership/services/cambio-ownership.service';


@Component({
  selector: 'app-interfaccia-di-riepilogo-del-fascicolo-page',
  templateUrl: './interfaccia-di-riepilogo-del-fascicolo-page.component.html',
  styleUrls: ['./interfaccia-di-riepilogo-del-fascicolo-page.component.scss']
})
export class InterfacciaDiRiepilogoDelFascicoloPageComponent implements OnInit, OnDestroy
{
  codiceFascicolo: string;
  form: FormGroup;
  fascicolo: Fascicolo;
  pagamento: PagamentoDto=null;
  isDevelMode=environment.developer;

  public isConfirm: boolean = false;
  public display: boolean = false;
  public title: string = "";
  public content: string = "";
  public typ: string = "";
  public extraData: any;
  public dateCambiamenti: Date = new Date();
  public enableChiamate : boolean = true;
  
  public integrazioni: IntegrazioneDocumentale[] = [];
  
  fascicoloSend: Fascicolo;
  tipiProcedimento: SelectOption[];
  entiDelegati: SelectOption[];
  unsubscribe$ = new Subject<void>();
  loadedFascicolo = new EventEmitter<boolean>();
  //pagamentiRegistrati:{ OK?: number; KO?: number; INCORSO?: number; };

  constructor(private route: ActivatedRoute,
              private router: Router,
              private store: FascicoloStore,
              private formBuilder: FormBuilder,
              private serviceDialog: DialogService,
              private dialogService: DialogService,
              private httpDominioService: HttpDominioService,
              private loadingService: LoadingService,
              private fascicoloService: FascicoloService,
              private sharedData: SharedDataService,
              private translateService:TranslateService,
              private integrazioneService: IntegrazioneDocumentaleService,
              private allegati: AllegatoService,
              private schedaTecnicaService: SchedaTecnicaService,
              private pagamentiSvc:PagoPaService,
              private cambioOwnershipSvc: CambioOwnershipService)
  {
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
  }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit()
  {
    this.store.setBreadcrumbs([{ label: 'Dettaglio' }]);
    this.caricamentoDati();
  }

  public callback(event:any):void{
    if(event.isConfirm){
      if(this.extraData == "sollevaIncarico")
        this.confirmSollevaIncarico();
      this.extraData = null;
      this.display = false;
    } else {
      if(this.extraData == "esitoSollevaIncarico")
        this.reloadCurrentRoute();
    }
  }

  public caricamentoDati(){
    this.loadingService.emitLoading(true);
    let tipiProcedimento$ = this.httpDominioService.getTipiProcedimento();
    let entiDelegati$ = this.httpDominioService.getEnteDelegato();
    this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codiceFascicolo;
    this.loadingService.emitLoading(true);
    let obsFascicolo$ = this.fascicoloService.getFascicolo(this.fascicolo);
    let pagamenti$=this.pagamentiSvc.getPagamentoByCodPratica(this.codiceFascicolo)
    //let obsPagamenti$ = this.fascicoloService.totPagamentiPerStato(null,this.codiceFascicolo);
    combineLatest([obsFascicolo$, tipiProcedimento$, entiDelegati$,pagamenti$
     // ,obsPagamenti$
    ])
      .pipe(takeUntil(this.unsubscribe$)).subscribe(([f, tipiProc, entiDeleg,pag
        //,resMappaTotali
      ]) =>
    {
      if(pag.codiceEsito==CONST.OK){
        this.pagamento=pag.payload;
      }
      if(f.codiceEsito === CONST.OK)
      {
        this.fascicolo = f.payload;
        this.sharedData.fascicolo = this.fascicolo;
        this.form = this.buildForm();
        this.form.controls.codiceSegreto.setValue(this.fascicolo.codiceSegreto);
        this.form.controls.codiceSegreto.disable();
        if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA' || !this.fascicolo.currentUserOwner){
          this.form.disable();
        }
        this.tipiProcedimento = tipiProc.map(item=>
          {
            item.label=item.description;
            return item;
          });
        this.entiDelegati = entiDeleg.map(item=>
          {
            item.label=item.description;
            item.value=item.value+'';
            return item;
          });
        this.afterLoadFascicolo();
        this.loadedFascicolo.next(true);
        if (![AttivitaDaEspletareEnum.COMPILA_DOMANDA, 
             AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI,
             AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA,
            ].includes(this.fascicolo.attivitaDaEspletare))
        {
          this.integrazioneService.search(this.fascicolo.id, "descrizione", "ASC").subscribe(intResponse =>
          {
            this.loadingService.emitLoading(false);
            if(intResponse.codiceEsito === CONST.OK)
              this.integrazioni = intResponse.payload;
          });
        }
        else
          this.loadingService.emitLoading(false);
      }
    });
  }

  public afterLoadFascicolo(): void
  {
    ViewMapper.mapObjectToForm(this.form, this.fascicolo);
    if (this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.IN_PREISTRUTTORIA)
    {
      this.form.disable();
    }
  }

  public buildForm(): FormGroup
  {
    return this.formBuilder.group({
      id: [null], //non messo in input
      codicePraticaAppptr: [''],
      enteDelegato: ['', Validators.required],
      oggetto: ['', Validators.required],
      tipoProcedimento: ['', Validators.required],
      isInSanatoria: [null],
      dataModifica: [null],
      attivitaDaEspletare: [AttivitaDaEspletareEnum.IN_PREISTRUTTORIA],
      codiceSegreto: []
    });
  }

  public sezioniDetails(event: 'istanza'|'scheda'|'allegati'): void
  {
    switch (event)
    {
      case 'istanza':
        this.router.navigate([paths.instance(this.fascicolo.codicePraticaAppptr)]);
        break;
      case 'scheda':
        this.router.navigate([paths.dataSheet(this.fascicolo.codicePraticaAppptr)]);
        break;
      case 'allegati':
        this.router.navigate([paths.attachment(this.fascicolo.codicePraticaAppptr)]);
        break;
      default:
        break;
    }
  }
  private parseResponse(data:any)
  {
    this.fascicolo.validatoIstnza = data.payload.validatoIstnza;
    this.fascicolo.validatoAllegati = data.payload.validatoAllegati;
    this.fascicolo.validatoSchedaTecnica = data.payload.validatoSchedaTecnica;
    if (data.codiceEsito === CONST.OK)
    {
      this.serviceDialog.showDialog(
        true,
        'SAVE_BUTTON_MESSAGE',
        'SAVE_DOCUMENT_TITLE',
        DialogButtons.CHIUDI
      );
      this.form.markAsUntouched();
      this.form.markAsPristine();
    }
  }

  public save(action?:string): void
  {
    const formValue = this.form.value;
    console.log("fascicolo al salvataggio {}", formValue);
    this.loadingService.emitLoading(true);
    if(action && action=='cambioEnte'){
      this.fascicoloService.cambioEnte({ ...this.fascicolo, ...formValue })
      .then(data=>this.parseResponse(data))
      .finally(() => this.loadingService.emitLoading(false));
    }else if(action && action=='cambioTipoProcedimento'){
      this.fascicoloService.cambioTipoProcedimento({ ...this.fascicolo, ...formValue })
      .then(data=>this.parseResponse(data))
      .finally(() => this.loadingService.emitLoading(false));;
    }else{
      this.fascicoloService.updateFascicolo({ ...this.fascicolo, ...formValue })
      .then(data=>this.parseResponse(data))
      .finally(() => this.loadingService.emitLoading(false));
    }
  }

  public indietro(): void
  {
    if (this.form.dirty)
    {
      this.dialogService.showDialog(
        true,
        'CLOSING_UNSAVED_OBJECT',
        'INFORMATION_TITLE',
        DialogButtons.OK_CANCEL,
        (buttonID: number) =>
        {
          if (ButtonType.OK_BUTTON === buttonID)
          {
            console.log(typeof ButtonType.OK_BUTTON + '===' + typeof buttonID);
            this.router.navigate([paths.list()]);
          }
        }
      );
    } else
    {
      this.router.navigate([paths.list()]);
    }
  }

  public isValidate(): boolean
  {
    return this.fascicolo.istanza &&
           this.fascicolo.istanza.valida === true &&
           this.fascicolo.schedaTecnica &&
           this.fascicolo.schedaTecnica.valida === true &&
           this.fascicolo.allegati &&
           this.fascicolo.allegati.valida === true
  }

  public generaStampa(): void
  {
    this.fascicolo.attivitaDaEspletare = AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA;
    this.store.editFascicolo(this.fascicolo);
    this.router.navigate([paths.generateStampa(this.fascicolo.codicePraticaAppptr)]);
  }

  public cambioEnte(event:any): void
  {
    this.save('cambioEnte');
  }
  
  public cambioTipoProcedimento(event:any): void
  {
    this.save('cambioTipoProcedimento');
  }


  public valida(): void
  {
    let _this=this;
    this.loadingService.emitLoading(true);
    this.fascicoloService.validaAll(this.fascicolo.codicePraticaAppptr, this.fascicolo.id).subscribe(response => 
    {
      this.loadingService.emitLoading(false);
      if (response.codiceEsito === CONST.OK)
      {
        let validazioneResponse:ValidInfoDto=response.payload;
        this.fascicolo.validatoIstnza = validazioneResponse.istanza;
        this.fascicolo.validatoSchedaTecnica = validazioneResponse.schedaTecnica;
        this.fascicolo.validatoAllegati = validazioneResponse.allegati;
        this.fascicolo.istanza.valida = validazioneResponse.istanza;
        this.fascicolo.schedaTecnica.valida = validazioneResponse.schedaTecnica;
        this.fascicolo.allegati.valida = validazioneResponse.allegati;
        if(validazioneResponse.istanza && validazioneResponse.schedaTecnica && validazioneResponse.allegati){
          this.dialogService.showDialog(true, 
            'VALIDATION.VALIDATION_SUCCESS_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
            DialogButtons.CHIUDI, null, DialogType.SUCCESS);
        }else{
          //per le sezioni invalide (non true) creo una dialog con la sezione errata e il relativo link
          let errorText = "<ul>";
          let doValidateParam="?doValidate=true";
          if(!response.payload.istanza){
            errorText += `<li><a href="${paths.instance(this.fascicolo.codicePraticaAppptr)+doValidateParam}">
                  ${this.translateService.instant('DETAILS.INSTANCE')}</a></li>`;
          }
          if(!response.payload.schedaTecnica){
            errorText += `<li><a href="${paths.dataSheet(this.fascicolo.codicePraticaAppptr)+doValidateParam}">
            ${this.translateService.instant('DETAILS.DATA_SHEET')}</a></li>`; 
          }
          if(!response.payload.allegati){
            errorText += `<li><a href="${paths.attachment(this.fascicolo.codicePraticaAppptr)+doValidateParam}">
            ${this.translateService.instant('DETAILS.ATTACHMENTS')}</a></li>`;  
          }
          errorText += "</ul>";
          let callbackPresaVisione=(bottonePremuto)=>{};
          if(validazioneResponse.avviso){
            //qualcosa è cambiato nei dati pubblicati su BP UPC o Strumenti urbanistici
            errorText += "<br>";
            errorText += "<h6>";
            errorText += validazioneResponse.avviso.replace(/(?:\r\n|\r|\n)/g, '<br>');
            errorText += "</h6>";
            callbackPresaVisione=(bottonePremuto:ButtonType)=>{
                if(bottonePremuto==ButtonType.OK_BUTTON){
                  //informo il be per spegnere la notifica
                  _this.loadingService.emitLoading(true);
                  _this.fascicoloService.presaVisione(_this.fascicolo.id).subscribe(ret=>{
                    _this.loadingService.emitLoading(false);
                    if(ret.codiceEsito==CONST.OK){}
                  },
                  error=>{
                      _this.showErrorDialog("Errore nella registrazione della presa visione del messaggio !!!")
                  })
                }
            };
          }
          let buttons:IButton[]=DialogButtons.CHIUDI;
          if(validazioneResponse.avviso){
              buttons=[
                { id: ButtonType.CLOSE_BUTTON, label: 'BUTTONS.CHIUDI_BUTTON' },
                { id: ButtonType.OK_BUTTON, label: 'BUTTONS.PRESA_VISIONE' }
              ];
          }
          this.dialogService.showDialog(true, 'VALIDATION.VALIDATION_ERROR_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
            buttons,
            callbackPresaVisione,
            DialogType.WARNING,
            null, { tabsWithErrors: errorText });
        }
        
      }
    },
    error => _this.showErrorDialog(error.message)
    )
  }

  private showErrorDialog(messaggio:string){
    this.dialogService.showDialog(true, messaggio, "generic.error", DialogButtons.CHIUDI, null, DialogType.ERROR);
    this.loadingService.emitLoading(false);
  }

  //Metodi per integrazione documentale
  public creaNuovaIntegrazione(): void
  {
    this.loadingService.emitLoading(true);
    let nuovaIntegrazione = new IntegrazioneDocumentale();
    nuovaIntegrazione.praticaId = this.fascicolo.id;
    nuovaIntegrazione.descrizione = "Integrazione_documentale_" + this.fascicolo.codicePraticaAppptr; //da rivedere
    this.integrazioneService.save(nuovaIntegrazione).subscribe(response =>
    {
      this.loadingService.emitLoading(false);
      if(response.codiceEsito === CONST.OK)
      {
        this.integrazioni.push(response.payload);
        this.visualizzaDettaglio(response.payload);
      }
    });
  }

  public visualizzaDettaglio(item: IntegrazioneDocumentale): void
  {
    this.router.navigate(['integrazione-documentale', this.fascicolo.codicePraticaAppptr, item.id]);
  }

  public cancellaIntegrazione(item: IntegrazioneDocumentale): void
  {
    if(item.stato == 'IN_BOZZA' && item.id)
    {
      this.loadingService.emitLoading(true);
      this.integrazioneService.delete(this.fascicolo.id, item.id).subscribe(response =>
      {
        this.loadingService.emitLoading(false);
        if(response.codiceEsito === CONST.OK)
        {
          let index = this.integrazioni.map(m => m.id).indexOf(item.id);
          if(index != -1)
            this.integrazioni.splice(index, 1);
          this.dialogService.showDialog(true, "Integrazione eliminata con successo", "successo", DialogButtons.CHIUDI, null, DialogType.SUCCESS);
        }
      });
    }
    else
      console.error("Non puoi effettuare questa operazione sull'integrazione: ", item);
  }

  public downloadRiepilogo(item: IntegrazioneDocumentale): void
  {
    //download documento riepilogo firmato
    this.loadingService.emitLoading(true);
    this.allegati.downloadDocumentoIntegrazione(item.praticaId, item.id).subscribe(response =>
    {
      this.loadingService.emitLoading(false);
      if(response.ok)
        downloadFile(response.body, "Integrazione_documentale_" + new Date().toLocaleDateString().replace('/', ''),response.headers);
    })
  }

  public get validato(): boolean {return this.fascicolo.validatoIstnza && this.fascicolo.validatoSchedaTecnica && this.fascicolo.validatoAllegati;}
  public get inIstruttoria(): boolean { return isInIstruttoria(this.fascicolo.attivitaDaEspletare); }

  public clonaPratica() {
    // creo una nuova pratica e poi faccio save della parte di istanza e di scheda tecnica
    if(!this.fileMock){
      alert("selezionare un file");
      return;
    }
    let newFascicolo: Fascicolo = new Fascicolo();
    newFascicolo.enteDelegato = this.fascicolo.enteDelegato;
    newFascicolo.tipoProcedimento = this.fascicolo.tipoProcedimento;
    newFascicolo.oggetto = this.fascicolo.oggetto + " clonata da " + this.fascicolo.codicePraticaAppptr + " alle " + new Date();
    newFascicolo.attivitaDaEspletare = AttivitaDaEspletareEnum.COMPILA_DOMANDA;
    newFascicolo.ruoloPratica=this.fascicolo.ruoloPratica?this.fascicolo.ruoloPratica:"PR";
    this.loadingService.emitLoading(true);
    console.log("creazione clone");
    let createFascicolo$=null;
    if(this.fascicolo.ruoloPratica=="DE"){
      createFascicolo$=this.fascicoloService.creaFascicolo(newFascicolo, this.fascicolo.istanza.delegatoPratica[0], this.fileMock);
    }else{
      createFascicolo$=this.fascicoloService.creaFascicolo(newFascicolo, null,null);
    }
    createFascicolo$
      .then(
        respCreate => {
          console.log("clone added");
          this.loadingService.emitLoading(false);
          newFascicolo = respCreate.payload;
          console.log("fetching cloned");
          this.loadingService.emitLoading(true);
          return this.fascicoloService.getFascicolo(newFascicolo)
        })
        .then(resp=>{
          console.log("clone fetched");
          this.loadingService.emitLoading(false);  
          newFascicolo = resp.payload;
          let idRich = newFascicolo.istanza.richiedente.id;
          newFascicolo.istanza.richiedente = this.fascicolo.istanza.richiedente;
          newFascicolo.istanza.richiedente.id = idRich;
          console.log("updateing richiedente");
          this.loadingService.emitLoading(true);  
          return this.fascicoloService.updateRichiedente(newFascicolo);
        })
      .then(resp => {
        console.log("clone fetched");
        this.loadingService.emitLoading(false);
        newFascicolo = resp.payload;
        newFascicolo.istanza.privacyAccettata = this.fascicolo.istanza.privacyAccettata;
        newFascicolo.istanza.dichiarazioni = this.fascicolo.istanza.dichiarazioni;
        let idRich = newFascicolo.istanza.richiedente.id;
        newFascicolo.istanza.richiedente = this.fascicolo.istanza.richiedente;
        newFascicolo.istanza.richiedente.id = idRich;
        let idTec = newFascicolo.istanza.tecnicoIncaricato.id;
        newFascicolo.istanza.tecnicoIncaricato = this.fascicolo.istanza.tecnicoIncaricato;
        newFascicolo.istanza.tecnicoIncaricato.id = idTec;
        newFascicolo.istanza.localizzazione = this.fascicolo.istanza.localizzazione;
        newFascicolo.tipoSelezioneLocalizzazione = this.fascicolo.tipoSelezioneLocalizzazione;
        this.loadingService.emitLoading(true);
        console.log("clone updating istanza ");
        return this.fascicoloService.updateFascicolo(newFascicolo);
      })
      .then(
        respUpdate => {
          console.log("clone updated istanza ");
          this.loadingService.emitLoading(false);
          newFascicolo.schedaTecnica = this.fascicolo.schedaTecnica;
          newFascicolo.schedaTecnica.idPratica = newFascicolo.id;
          this.loadingService.emitLoading(true);
          console.log("clone updating scheda tecnica ");
          return this.schedaTecnicaService.saveSchedaTecnica(newFascicolo.schedaTecnica).toPromise();
        })
      .then(respSchT => {
        console.log("clone updated scheda tecnica ",respSchT);
        this.loadingService.emitLoading(false);
        //qui creo un blocco di promise per altri titolari....
        let promise: Promise<BaseResponse<AltroTitolare>>[] = [];
        if (this.fascicolo.istanza.altriTitolari && this.fascicolo.istanza.altriTitolari.length > 0) {
          this.fascicolo.istanza.altriTitolari.forEach(titolare => {
            promise.push(this.fascicoloService.creaTitolare(newFascicolo));
          });
        }
        this.loadingService.emitLoading(true);
        console.log("clone creating altri titolari ");
        return Promise.all(promise);
      }).then(resAltriTit => {
        console.log("clone created altri titolari {}", resAltriTit);
        this.loadingService.emitLoading(false);
        if (this.fascicolo.istanza.altriTitolari && this.fascicolo.istanza.altriTitolari.length > 0) {
          if (resAltriTit.length > 0) {
            resAltriTit.forEach((newTitResp, index) => {
              let newTit = newTitResp.payload;
              let newid = newTit.id;
              Object.keys(newTit).forEach(key => {
                newTit[key] = this.fascicolo.istanza.altriTitolari[index][key];
              });
              newTit.id = newid;
              newFascicolo.istanza.altriTitolari.push(newTit);
            });
          }
        }
        this.loadingService.emitLoading(true);
        console.log("clone updating after creating altri titolari ");
        return this.fascicoloService.updateFascicolo(newFascicolo);
      })
      .then(resp=>{
        //carico documento richiedente
        return this.allegati.uploadAllegatoDocumentoReferente(this.fileMock,newFascicolo.id,newFascicolo.istanza.richiedente.id);
      })
      .then(resp=>{
        //carico documento tecnico
        return this.allegati.uploadAllegatoDocumentoReferente(this.fileMock,newFascicolo.id,newFascicolo.istanza.tecnicoIncaricato.id);
      })
      .then(
        () => {
          console.log("clone updated after creating  altri titolari ");
          this.dialogService.showDialog(true, "Pratica clonata con successo, codice:" + newFascicolo.codicePraticaAppptr, "generic.info", DialogButtons.CHIUDI, null, DialogType.INFORMATION);
        }
      )
      .finally(
        () => {
          this.loadingService.emitLoading(false);
          this.fascicolo.codicePraticaAppptr = this.codiceFascicolo;
          this.fascicoloService.getFascicolo(this.fascicolo).then(() => console.log("ricaricato vecchio fascicolo...."));
        }
      );
  }

fileMock:File;

public onSelezioneMockFile(files:File[]){
    this.fileMock = files[0];
}

public get enableSollevaIncarico(): boolean {
  return this.fascicolo.attivitaDaEspletare !== AttivitaDaEspletareEnum.ARCHIVIATA
  && this.fascicolo.attivitaDaEspletare !== AttivitaDaEspletareEnum.TRANSMITTED
  && !this.fascicolo.currentUserOwner
  && this.fascicolo.currentUserRichiedente;
}

public sollevaIncarico():void{
  this.isConfirm = true;
  this.typ = 'info'
  this.title = this.translateService.instant("cambioOwnership.titleSollevaIncarico");
  this.content = this.translateService.instant("cambioOwnership.contentSollevaIncarico");
  this.display = true;  
  this.extraData = "sollevaIncarico";
}

private confirmSollevaIncarico():void{
  this.loadingService.emitLoading(true);
  this.cambioOwnershipSvc.sollevaIncarico(this.fascicolo.id).subscribe((response) => {
    if(CONST.OK === response.codiceEsito){
      this.isConfirm = false;
      this.typ = 'success'
      this.title = this.translateService.instant("cambioOwnership.titleEsitoSollevaIncarico");
      this.content = this.translateService.instant("cambioOwnership.contentEsitoSollevaIncarico");
      this.extraData = "esitoSollevaIncarico";
      this.display = true;  
    }
    this.loadingService.emitLoading(false);
  });
}

reloadCurrentRoute() {
  let currentUrl = this.router.url;
  this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate([currentUrl]);
  });
}

}
