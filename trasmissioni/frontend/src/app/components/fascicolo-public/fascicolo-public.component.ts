import { SchemaRicerca } from './../model/model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { FascicoloAzioni, FascicoloConfig } from 'src/shared/config/fascicolo.config';
import { PageObj } from 'src/shared/models/response-object.model';
import { CONST } from '../../../shared/constants';
import { downloadFile } from '../functions/genericFunctions';
import { BaseSearch, FascicoloDTO, FascicoloPublicDto, FascicoloSearch } from '../model/entity/fascicolo.models';
import { Observable } from 'rxjs';
import { PlainTypeStringId } from 'src/shared/models/plain-type-string-id.model';
import { OrderByAndPaging } from 'src/shared/components/data-table/data-table.component';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-fascicolo-public',
  templateUrl: './fascicolo-public.component.html',
  styleUrls: ['./fascicolo-public.component.css']
})
export class FascicoloPublicComponent implements OnInit
{
  registrationStatusSummaries$: Observable<PlainTypeStringId[]>;
  searchOpenInit=false; 

  
  filters: FascicoloSearch = new FascicoloSearch();
  public schemaRicerca: SchemaRicerca = { registrazione: false, verifica: false };

  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  cols: any[];
  inCaricamento: boolean = false;
  public fascicoli: PageObj<FascicoloPublicDto>; 
  codiceParameter: string;

  constructor(private translateSvc: TranslateService,
              private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private localStorageSvc:LocalSessionServiceService,
              private autpaesvc: AutorizzazioniPaesaggisticheService, ) 
  {
    this.cols = FascicoloConfig.listTableHeaders();
  }

  doInit(){
    let filterSearchStored:FascicoloSearch=this.localStorageSvc.getValueOrNull(LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY);
    if(filterSearchStored){
      this.filters=filterSearchStored;
    }else{
      //se non esiste ne creo comunque una con la sola parte di page e limit
      this.filters = new FascicoloSearch();
      this.filters.colonna="data_rilascio_autorizzazione";
      this.filters.direzione="DESC";
      this.filters.page=1;
      this.filters.limit=5;
    }
    if(this.codiceParameter){
      this.filters.codice=this.codiceParameter;
    }
    if(this.hasFilterValue(this.filters)){
      console.log("hasfilter")
      this.searchOpenInit=true;
    }
    this.localStorageSvc.storeValue(LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY,this.filters);      
    console.log(this.filters)
    this.autpaesvc.findSchemaForSearch().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.schemaRicerca = response.payload;
        if (response.payload.registrazione) {
          let colonnaDaAggiungere: any = { field: 'stato_registrazione', header: 'operatore.table.statoRegistrazione', fieldName: 'statoRegistrazione', type: 'statoRegistrazione', valueOf: CONST.StatoRegistrazione };
          let index: number = this.cols.findIndex(elem => elem.field === "id"); /* posizione dove aggiungere la colonna */
          this.cols.splice(index, 0, colonnaDaAggiungere);
        }
        if (response.payload.verifica) {
          let colonnaDaAggiungere: any = { field: 'esito_verifica', header: 'operatore.table.esitoVerifica', fieldName: 'esitoVerifica', type: 'text',valueOf: CONST.EsitiVerifica };
          let index: number = this.cols.findIndex(elem => elem.field === "id"); /* posizione dove aggiungere la colonna */
          this.cols.splice(index, 0, colonnaDaAggiungere);
        }
      }
    });
    this.registrationStatusSummaries$ = this.autpaesvc.getContatoriFascicoloPublic();
    this.initTableData();
  }

  ngOnInit() 
  {
    //this.codiceParameter = this.route.snapshot.queryParamMap.get('codiceFascicolo');
    this.route.queryParams.subscribe(params=>{
      if(params.codiceFascicolo)   {
        this.codiceParameter = this.route.snapshot.queryParamMap.get('codiceFascicolo');
      }
      this.doInit();
    });
  }

  initTableData()
  {
    this.fascicoli = new PageObj<FascicoloDTO>();
    this.fascicoli.list = [];
    if(this.userService.hasCookieToken() && !this.userService.isLogged()){
      this.userService.updateLogin();
    }
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
    }
    else if (event.isConfirm && event.extraData && event.extraData.operazione) {
      switch (event.extraData.operazione) {
        case 'confirmExport':
          this.alertData.display = false;
          this.alertData.isConfirm = false;
          this.doExport(this.alertData.extraData.type);
        case 'navigateDettaglio':
            this.alertData.display = false;
            this.alertData.isConfirm = false;
            this.doNavigateToDettaglio(this.alertData.extraData.type);  
        default:
          this.alertData.isConfirm = false;
          this.alertData.extraData = null;
          this.alertData.display = false;
          break;
      }
    }
  }


  searchFascicoli(event?: FascicoloSearch): void
  {
    if (event) {
      Object.keys(event).forEach(key=>{
        this.filters[key]=event[key];
      });
    };
    if ((this.filters as any).rows)
    {
      this.filters.limit = (this.filters as any).rows;
      (this.filters as any).rows = null;
    }
    //se è un evento di ricerca con parametri (proveniente da search-component) rimetto il page a 1
    if(event){
      this.filters.page=1;
    }
    if(!this.filters.page) this.filters.page = 1;
    if(!this.filters.limit) this.filters.limit = 5;
    //salvo nella stored
    let filterSearchStored:FascicoloSearch=this.localStorageSvc.getValueOrNull(LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY);
    if(filterSearchStored){
      filterSearchStored.page=this.filters.page;
      filterSearchStored.limit=this.filters.limit;
      filterSearchStored.colonna=this.filters.colonna;
      filterSearchStored.direzione=this.filters.direzione;
      this.localStorageSvc.storeValue(LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY,filterSearchStored);
      //Nel caso di caricamento dal local storage dei parametri di ricerca si utilizza 
      //il metodo per formattare i campi contenenti date e comune al formato richiesto dal backend
      this.filters=this.formatSearchFields(filterSearchStored);
    }
    this.autpaesvc.searchPublicAllFascicoli(this.filters).subscribe(result =>
    {
      let p: PageObj<FascicoloPublicDto> = new PageObj<any>();
      p.pageNumber = 1;
      if (result.codiceEsito === CONST.OK)
      {
        p.list = result.payload.list;
        p.count = result.payload.count;
        p.pageNumber = 1;
      }
      else
      {
        p.list = null;
        p.count = 0;
      }
      this.fascicoli = p;
    });
  }

    /**
   * Metodo per la trasformazione dei campi del form di ricerca che necessitano di formattazione per renderli 
   * compatibili con la request accettata dal backend
   * @param search 
   * @returns 
   */
     private formatSearchFields(search: FascicoloSearch): FascicoloSearch
     {
       if (search.comuneIntervento) search.comuneIntervento = (search.comuneIntervento as any).value;
       if (search.dataRilascioAutorizzazioneDa) search.dataRilascioAutorizzazioneDa = new Date(search.dataRilascioAutorizzazioneDa).toDateString();
       if (search.dataRilascioAutorizzazioneA) search.dataRilascioAutorizzazioneA = new Date(search.dataRilascioAutorizzazioneA).toDateString();
       if (search.dataProtocolloDa) search.dataProtocolloDa = new Date(search.dataProtocolloDa).toDateString();
       if (search.dataProtocolloA) search.dataProtocolloA = new Date(search.dataProtocolloA).toDateString();
       return search;
     }

  orderByAndPaging(event: OrderByAndPaging): void
  {
    this.filters.limit = event.rows;
    this.filters.page = event.page;
    if(event.sortField){
      this.filters.colonna = event.sortField;
      this.filters.direzione = event.sortOrder;
    }
    this.searchFascicoli();
  }

  downloadProvvedimento(rowTable: FascicoloPublicDto)
  {
    let downProvv$=this.autpaesvc.downloadProvvedimentoByCodice(rowTable.codice);
    if(rowTable.applicazione && rowTable.applicazione==CONST.PAE_PRES_IST){
      downProvv$=this.autpaesvc.downloadProvvedimentoByCodiceFromIstr(rowTable.codice);
    }
    downProvv$.subscribe(
      response => {
        if (response.ok) {
          downloadFile(response.body, "provvedimento.pdf",response.headers); /* non so se sia sempre un pdf */
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
      }
    );
  }

  azioniRiga(event)
  {
    if (event.action == FascicoloAzioni.DOWNLOAD_PROVVEDIMENTO)
    {
      this.downloadProvvedimento(event.riga);
    } 
    else if (event.action == FascicoloAzioni.VIEW)
    {
      this.openDettaglioFascicolo(event);
    }
  }

  private askNavigateDettaglio(gruppoRichiesto:string,chiaveFascicolo:string,applicazione:string){
    this.alertData.title = "generic.warning";
    this.alertData.content = "generic.redirectDettaglio";
    this.alertData.typ = "warning";
    this.alertData.display = true;
    this.alertData.isConfirm = true;
    this.alertData.extraData =
    {
      operazione: 'navigateDettaglio',
      type:{
        gruppoRichiesto: gruppoRichiesto,
        chiaveFascicolo: chiaveFascicolo,
        applicazione: applicazione
      }
    };
  }

  private doNavigateToDettaglio(data:{gruppoRichiesto:string,chiaveFascicolo:string,applicazione:string}){
    if (data.applicazione == CONST.PAE_PRES_IST) {
      let urlIstruttoriaFe=environment.basepath_istruttoria_fe;
      urlIstruttoriaFe
      .concat("/gestione-istanze/")
      .concat(data.chiaveFascicolo)
      .concat("/istanza-presentata")
      .concat("?")
      .concat(CONST.GRUPPO_UTENTE_HEADER)
      .concat("=")
      .concat(data.gruppoRichiesto);
      window.open(urlIstruttoriaFe, '_blank');
  } else {
    if(this.userService.actualGroup!=data.gruppoRichiesto){
       
    }
    let urlDiPartenza='/private/fascicolo/dettaglio/' + data.chiaveFascicolo;
    this.router.navigate(['/private/gestione-gruppo', { previousUrl: urlDiPartenza,gruppoUtentePredefinito:data.gruppoRichiesto }]);
  }
  }

  private openDettaglioFascicolo(event:any) {
    if (event.riga.groupCanAccess && this.userService.isLogged()) {
      let gruppoRichiesto=event.riga.groupCanAccess;
      let chiaveFascicolo=event.riga.id;
      this.askNavigateDettaglio(gruppoRichiesto,chiaveFascicolo,event.riga.applicazione);
    } else {
      this.alertData.title = "generic.warning";
      this.alertData.content = "generic.azioneNonAmmessa";
      this.alertData.typ = "warning";
      this.alertData.display = true;
      this.alertData.isConfirm = false;
    }
  }



  export(type: "PDF" | "CSV") 
  {
    this.alertData.title = "search.exportWarning.title";
    this.alertData.content = "search.exportWarning.message";
    this.alertData.typ = "info";
    this.alertData.display = true;
    this.alertData.isConfirm = true;
    this.alertData.extraData = {operazione:"confirmExport",type:type};
  }

  public doExport(type: "PDF"|"CSV")
  {
    this.autpaesvc.exportFascicoli(this.filters, type).subscribe(result =>
    {
      if (result.ok)
      {
        downloadFile(result.body, "Risultati di ricerca." + type.toLowerCase());
      }
    });
  }

  public showDetailButton(fascicolo:FascicoloPublicDto):boolean{
    let ret=false;
    if(fascicolo.groupCanAccess){
      ret=true;
    }
    return ret;
  }

  hasFilterValue(search:FascicoloSearch){
    let hasFilter=false;
    let bs =new BaseSearch();
    bs['colonna']=null;
    bs['direzione']=null;
    let baseKeys=Object.keys(bs);
    console.log(baseKeys)
    if (search) {
      Object.keys(search).forEach(key=>{
        if(!baseKeys.includes(key) && search[key]){
          console.log("key filled " ,key,search[key])
          hasFilter=true;
          return hasFilter;
        }
      });
    }
    return hasFilter;
  }

}
