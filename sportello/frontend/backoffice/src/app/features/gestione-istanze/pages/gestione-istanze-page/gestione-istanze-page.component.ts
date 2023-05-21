import { BaseResponse } from './../../../../shared/components/model/base-response';
import { combineLatest, Subject } from 'rxjs';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { SelectItem } from 'primeng/primeng';
import { paths } from 'src/app/app-routing.module';
import { copyOf, downloadFile } from 'src/app/core/functions/generic.utils';
import { IButton } from 'src/app/core/models/dialog.model';
import { ActionDataButton, EventActionData, TableConfig } from 'src/app/core/models/header.model';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { SearchConfiguration } from 'src/app/shared/components/form-search/configuration/search.configuration';
import { CONST } from 'src/app/shared/constants';
import { SearchFields } from 'src/app/shared/models/form-search.configurations.models';
import { Assegnamento, Fascicolo } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { UserService } from 'src/app/shared/services/user.service';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { GestioneStatoPraticaComponent } from './../../../../shared/components/gestione-stato-pratica/gestione-stato-pratica.component';
import { Counters, DocumentoAllegato, GroupRole, GroupType, StatoEnum, StoricoASR } from './../../../../shared/models/models';
import { PraticaSearch } from './../../../../shared/models/search.models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { IstanzeConst } from './../../constants/constant-for-table';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-gestione-istanze',
  templateUrl: './gestione-istanze-page.component.html',
  styleUrls: ['./gestione-istanze-page.component.scss']
})
export class GestioneIstanzePageComponent implements OnInit,OnDestroy
{
  public fascioloTableHeaders: TableConfig[] = [];
  public configuration: SearchFields[];
  public index: number = null;
  public fascicoloList: Fascicolo[];
  public searchParams: PraticaSearch = new PraticaSearch();
  public counters: Counters = new Counters();
  public totalRecords: number = 0;

  //gestione modal
  @ViewChild("gestioneStato", {static: false}) handleState: GestioneStatoPraticaComponent;
  public data: StoricoASR;
  public validationRequest: boolean = false;
  public display: boolean = false;
  public codicePratica: string;
  public optionsStato: SelectItem[] = [];

  //gestione assegnazione
  public displayNewAssignment:boolean=false;
  public initAssignment: Assegnamento;
  public funzionariOptions: SelectItem[];
  public rupOptions: SelectItem[];
  public fascicoloAction:Fascicolo;

  private unsubscribe$: Subject<void> = new Subject<void>();
  
  constructor(private router: Router
              ,private fascicoloStore: FascicoloStore
              ,private userService: UserService
              ,private dialog: CustomDialogService
              ,private fascicoloService: FascicoloService
              ,private loading: LoadingService
              ,private allegatiService: AllegatoService
              ,private dominioService: HttpDominioService
              ,private translateService: TranslateService) 
  { 
    
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
  }

  ngOnInit()
  {
    this.fascicoloStore.setBreadcrumbs([{ label: 'Gestione Istanze' }]);
    this.fascioloTableHeaders = copyOf(IstanzeConst.FASCICOLO_TABLE_HEADERS);  
    if (![GroupType.Regione, GroupType.EnteDelegato].includes(this.userService.groupType) ){
      this.fascioloTableHeaders.shift();
    }
    if ([GroupType.Regione, GroupType.EnteDelegato].includes(this.userService.groupType) ){
      this.rimuoviColonna('descrEnteDelegato',this.fascioloTableHeaders);
    }
    if(![GroupType.Regione, GroupType.EnteDelegato,GroupType.CommissioneLocale,GroupType.Soprintendenza].includes(this.userService.groupType)){
      this.rimuoviColonna('statoAttivita',this.fascioloTableHeaders);
      this.rimuoviColonna('stato',this.fascioloTableHeaders);
    }  
    if(!this.hasGestioneAssegnazione()){
      this.rimuoviColonna('denominazioneAssegnatarioOrganizzazione',this.fascioloTableHeaders);
    }
    if (![GroupType.Regione].includes(this.userService.groupType) ){
      //aggiungo le logiche di gestione dell'assegnazione come otherActions
      let idxField=this.fascioloTableHeaders.findIndex(el=>el.field=='displayButton');
      if(idxField>=0){
        this.fascioloTableHeaders[idxField].otherActions=this.buildOtherActions();
      }
    }
    let fascicoloSearch: PraticaSearch = new PraticaSearch();
    this.loading.emitLoading(true);
    let obsCounter$  = this.fascicoloService.callCountersFascicoli(fascicoloSearch);
    this.searchParams.sortBy = null;
    let obsPratiche$ = this.fascicoloService.callSearchFascicoli(this.searchParams);
    let obsPopulate$ = this.dominioService.callPopulateSearch();
    this.loading.emitLoading(true);
    combineLatest([obsPopulate$, obsCounter$, obsPratiche$]).subscribe(([respPopulate, respCounter, respPrt]) =>
    {
      if (respPopulate.codiceEsito === CONST.OK &&
         respCounter.codiceEsito === CONST.OK &&
         respPrt.codiceEsito === CONST.OK)
      {
        this.fascicoloList = respPrt.payload.list;
        this.totalRecords = respPrt.payload.count;
        this.counters = respCounter.payload;

        let gruppo = this.userService.groupType;
        let ruolo  = this.userService.role;
        let container = respPopulate.payload;
        this.configuration = SearchConfiguration.getConfigurazioneRicerca(gruppo, container, ruolo);
      }
      this.loading.emitLoading(false);
    });
  }
  

  private rimuoviColonna(nomeCampo:string,tableHeaders:TableConfig[]){
    let indexOfcol=tableHeaders.findIndex(el=>el.field==nomeCampo);
      if(indexOfcol>=0){
        tableHeaders.splice(indexOfcol,1);
      }
  }

  public navigaAssegnaFascicolo(event: string): void { this.router.navigate([paths.assign(event)]); }
  public navigateToDetails(event: string) { this.router.navigate([paths.detailsManagement(event)]); }

  public loadFascicolo(searchQuery?: PraticaSearch)
  {
    if(searchQuery)
    {
      Object.keys(searchQuery).forEach(key =>
      {
        this.searchParams[key] = searchQuery[key] && searchQuery[key].value ? searchQuery[key].value : searchQuery[key];
      });
      this.searchParams.page = searchQuery.page ? searchQuery.page : 1;
      this.searchParams.limit = searchQuery.limit ? searchQuery.limit : 5;
    }
    this.loading.emitLoading(true);
    this.fascicoloService.callSearchFascicoli(this.searchParams).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK && result.payload)
      {
        this.fascicoloList = result.payload.list;
        this.totalRecords = result.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }


  public delete(event: string): void 
  {
    let title: string = "Conferma eliminazione";
    let content: string = "Sei sicuro di voler rimupvere il fascicolo con codice " + event;
    let buttons = DialogButtons.OK_CANCEL;
    this.dialog.showDialog(true, content, title, buttons, (buttonId: number): void =>
    {
      switch (buttonId)
      {
        case 1:
          this.fascicoloStore.removeFascicolo(event);
          break;
        default:
          //nulla
      }
    }, DialogType.INFORMATION, null, null);
  }

  public openRequestModal(event: Fascicolo): void
  {
    this.loading.emitLoading(true);
    this.prepareOptions(event);
    this.codicePratica = event.codicePraticaAppptr;
    this.fascicoloService.callGetOrCreateRequest(event.id).subscribe(response =>
    {
      this.loading.emitLoading(false)
      if(response.codiceEsito === CONST.OK)
      {
        this.data = response.payload;
        this.display = true;
      }
    });
  }

  private prepareOptions(row: Fascicolo): void
  {
    let archiviata = { label: "Archiviazione", value: "ARCHIVIAZIONE" };
    let sospendi   = { label: "Sospensione"  , value: "SOSPENSIONE" };
    let attiva     = { label: "Attivazione"  , value: "ATTIVAZIONE" };
    switch (row.attivitaDaEspletare)
    {
      case StatoEnum.Trasmessa:
        this.optionsStato = [sospendi];
        break;
      case StatoEnum.Sospesa:
        this.optionsStato = [archiviata, attiva];
        break;
      case StatoEnum.Archiviata:
        if (this.userService.role === GroupRole.A)
          this.optionsStato = [attiva];
        break;
      default:
        this.optionsStato = [sospendi, archiviata];
    }
  }

  public submitRequest(draft: boolean): void
  {
    this.handleState.validation = !draft;
    if(draft || this.handleState.form.valid)
    {
      this.data = this.handleState.form.getRawValue();
      let msg = "fascicolo.asr."+this.data.type;
      if(draft){
        msg = "fascicolo.asr.draft";
      }
      this.dialog.showDialog(true, msg, "Attenzione", DialogButtons.CONFERMA_CHIUDI, (buttonId: any) =>
      {
        if(buttonId === 1)
        {
          this.loading.emitLoading(true);
          this.data.draft = draft;
          this.fascicoloService.callUpdateASR(this.data).subscribe(response =>
          {
            this.loading.emitLoading(false);
            let message: string = "STATE.DIALOG.";
            let title: string = "STATE.DIALOG.";
            let button: IButton[] = DialogButtons.CHIUDI;
            let dialogType: DialogType;
            if (response.codiceEsito === CONST.OK)
            {
              this.display = false;
              dialogType = DialogType.SUCCESS;
              if (draft)
              {
                title += "TITLE_BOZZA_SUCCESS";
                message += this.data.type === "ATTIVAZIONE" ? "MESSAGE_ATTIVA_BOZZA_SUCCESS"
                  : this.data.type === "SOSPENSIONE" ? "MESSAGE_SOSPENDI_BOZZA_SUCCESS"
                    : "MESSAGE_ARCHIVIA_BOZZA_SUCCESS";
              }
              else
              {
                title += "TITLE_SUCCESS";
                message += this.data.type === "ATTIVAZIONE" ? "MESSAGE_ATTIVA_SUCCESS"
                  : this.data.type === "SOSPENSIONE" ? "MESSAGE_SOSPENDI_SUCCESS"
                    : "MESSAGE_ARCHIVIA_SUCCESS";
                this.ngOnInit();
              }
              this.data = null;
            }
            this.dialog.showDialog(true, message, title, button, null, dialogType);
          });
        }
      }, DialogType.INFORMATION);
    }
  }

  public uploadForRequest(event: any): void
  {
    this.loading.emitLoading(true);
    let file = event.file;
    let idRichiesta = event.id;
    this.fascicoloService.callUploadFileASR(file, idRichiesta, this.data.idPratica).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if(response.codiceEsito === CONST.OK)
      {
        if(!this.data.allegati) this.data.allegati = [];
        this.data.allegati.push(response.payload);
      }
    });
  }

  public deleteForRequest(event: any): void
  {
    let title: string = "Attenzione";
    let message: string = "Sei sicuro di voler eliminare questo documento?";
    let buttons: IButton[] = DialogButtons.CONFERMA_CHIUDI;
    this.dialog.showDialog(true, message, title, buttons, (button_id: any) =>
    {
      if(button_id === 1)
      {
        this.loading.emitLoading(true);
        this.fascicoloService.callDeleteFileASR(event.file.id, event.id, this.data.idPratica).subscribe(response =>
        {
          this.loading.emitLoading(false);
          if(response.codiceEsito === CONST.OK)
          {
            this.data.allegati.forEach((item, index, array) =>
            {
              if(item.id === event.file.id)
                array.splice(index, 1);
            });
          }
        });
      }
    }, DialogType.WARNING);
  }

  public downloadForRequest(event: DocumentoAllegato): void
  {
    this.loading.emitLoading(true);
    
    //this.fileHandler.callDownloadAllegato(event.id).subscribe(response =>
    this.allegatiService.downloadAllegatoFascicolo(event.id,this.data.idPratica, '/istruttoria/allegati/download.pjson').subscribe(response=>
    {
      this.loading.emitLoading(false);
      if(response.status === 200)
        downloadFile(response.body, event.nome);
    });
  }

  get buttonLabel(): string
  {
    let label = null;
    if(this.handleState && this.handleState.form)
    {
      switch (this.handleState.form.get("type").value)
      {
        case "ARCHIVIAZIONE":
          label = "STATE.ARCHIVIA_BTN";
          break;
        case "SOSPENSIONE":
          label = "STATE.SOSPENDI_BTN";
          break;
        case "ATTIVAZIONE":
          label = "STATE.ATTIVA_BTN";
          break;
      } 
    }
    return label;
  }

  public paging(pagintionInfo: any): void
  {
    this.searchParams.page = pagintionInfo.page ? pagintionInfo.page : 1;
    this.searchParams.limit = pagintionInfo.limit ? pagintionInfo.limit : 5;
    this.searchParams.sortBy = pagintionInfo.field ? pagintionInfo.field : null;
    this.searchParams.sortType = pagintionInfo.sort === 1 ? "ASC" : "DESC";
    this.loadFascicolo(this.searchParams);
  }

  private hasGestioneAssegnazione(){
    return [GroupRole.D,GroupRole.A].includes(this.userService.role) && 
            !(this.userService.groupType==GroupType.Regione);
  }

  private enableAssegnazioneAction(fascicolo:Fascicolo,col:TableConfig){
    return this.hasGestioneAssegnazione() &&
            'usernameAssegnatarioOrganizzazione' in fascicolo && 
            fascicolo.usernameAssegnatarioOrganizzazione==null;
  }
  private enableDisassegnazioneAction(fascicolo:Fascicolo,col:TableConfig){
    return this.hasGestioneAssegnazione() &&  
            fascicolo.usernameAssegnatarioOrganizzazione;
  }

  /**
   * costruisce i dati e le logiche per attivare le altre actions in tabella 
   * @returns 
   */
  buildOtherActions(): ActionDataButton[] {
    let actionsDataButton:ActionDataButton[]=[];
    let assegnaAction:ActionDataButton={
      nameAction:'assegna',
      btnIcon:'fa fa-user',
      cssButton:'btn btn-success',
      fnEnabled:this.enableAssegnazioneAction.bind(this)
    };
    actionsDataButton.push(assegnaAction);
    let disassegnaAction:ActionDataButton={
      nameAction:'disassegna',
      btnIcon:'fa fa-user',
      cssButton:'btn btn-delete',
      fnEnabled:this.enableDisassegnazioneAction.bind(this)
    };
    actionsDataButton.push(disassegnaAction);
    return actionsDataButton;
  }

  public doOtherActions(event: EventActionData){ 
    switch (event.nameAction) {
      case 'assegna':
        this.openAssegnaFascicolo(event.rowData);
        break;
      case 'disassegna':
        this.eliminaAssegnazione(event.rowData);
        break;
      default:
        alert("Azione non riconosciuta!!!");
        break;
    }
  }

  private hasAssegnazioneRup():boolean{
    return [GroupRole.D,GroupRole.A].includes(this.userService.role) && 
    (this.userService.groupType==GroupType.EnteDelegato);
  }

  public openAssegnaFascicolo(rowData: any): void
  {
    this.fascicoloAction=rowData as Fascicolo;
    let fascicolo=this.fascicoloAction;
    this.initAssignment = {
      idFascicolo:fascicolo.id,
      denominazioneFunzionario:fascicolo.denominazioneAssegnatarioOrganizzazione,
      usernameFunzionario:fascicolo.usernameAssegnatarioOrganizzazione,
      denominazioneRup:null,
      usernameRup:null
    };
    if(this.hasAssegnazioneRup()){
              this.initAssignment.usernameRup=fascicolo.usernameRup;
              this.initAssignment.denominazioneRup=fascicolo.denominazioneRup;
    }
    this.initFunzionariERupOptions(()=>{this.displayNewAssignment = true});
  }

  private initFunzionariERupOptions(callback?:any){
    let _this=this;
    if(!this.funzionariOptions || this.funzionariOptions.length==0){
      this.loading.emitLoading(true);
      this.fascicoloService.callGetFunzionario().pipe(takeUntil(this.unsubscribe$)).subscribe(resFun=>{
        this.loading.emitLoading(false);
        _this.rupOptions = resFun.payload.filter(p => p.rup).map(m => { return {label: m.description , value: m.value}});
        _this.funzionariOptions = resFun.payload.filter(p => !p.rup).map(m => { return { label: m.description , value: m.value } });
        if(callback){
          callback();
        }
      });
    }else{
      if(callback){
        callback();
      }
    }
  }

  public saveAssignment(assegnamento: Assegnamento): void
  {
    this.loading.emitLoading(true);
    this.fascicoloService.callNewSaveAssignement(assegnamento)
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        let fascicoloSearch: PraticaSearch = new PraticaSearch();
        this.fascicoloService.callCountersFascicoli(fascicoloSearch).subscribe(response => {
          if (response.codiceEsito === CONST.OK){
            this.counters = response.payload;
          }
          this.loading.emitLoading(false);
          this.closeAssegnaFascicolo(this.loadFascicolo.bind(this));
        })
      } else {
        this.loading.emitLoading(false);
      }
    });
  }

  closeAssegnaFascicolo(callback?:any){
    this.initAssignment = null;
    this.fascicoloAction=null;    
    this.displayNewAssignment = false;   
    if(callback){
      callback();
    }
  }

  public eliminaAssegnazione(event: any): void
  {
    let fascicoloSelected=(event as Fascicolo);
    let disassegnaData:Assegnamento={
      idFascicolo:fascicoloSelected.id,
      usernameFunzionario:fascicoloSelected.usernameAssegnatarioOrganizzazione,
      denominazioneFunzionario:fascicoloSelected.denominazioneAssegnatarioOrganizzazione,
      usernameRup:null,
      denominazioneRup:null
    };
    let tokenFunzionario="<br><b>funzionario:</b> "+fascicoloSelected.denominazioneAssegnatarioOrganizzazione;
    let tokenRup="";
    if(this.hasAssegnazioneRup()){
      tokenRup="<br><b>RUP:</b> "+fascicoloSelected.denominazioneRup;
      disassegnaData.usernameRup=fascicoloSelected.usernameRup;
      disassegnaData.denominazioneRup=fascicoloSelected.denominazioneRup;
    }
    let message = 
    this.translateService.instant('ASSEGNAZIONE_FASCICOLO.CONFERMA_DISASSEGNAZIONE',
      {tokenFunzionario:tokenFunzionario,tokenRup:tokenRup});
    this.dialog.showDialog(true, message, 'generic.warning', DialogButtons.CONFERMA_CHIUDI, (buttonid: any) =>
    {
      if (buttonid == 1)
      {
        this.loading.emitLoading(true);
        this.fascicoloService.callDisassegnaFascicolo(disassegnaData).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
          {
            let fascicoloSearch: PraticaSearch = new PraticaSearch();
            this.fascicoloService.callCountersFascicoli(fascicoloSearch).subscribe(response => {
              if (response.codiceEsito === CONST.OK){
                this.counters = response.payload;
              }
              this.loadFascicolo();
              this.closeAssegnaFascicolo(this.loadFascicolo.bind(this));
            })
          } else {
            this.loading.emitLoading(false);
          }
        });
      }
    }, DialogType.INFORMATION);
  }

}
