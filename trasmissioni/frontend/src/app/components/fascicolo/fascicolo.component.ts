import { UserService } from './../../services/user.service';
import { LocalSessionServiceService } from './../../services/local-session-service.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faFileCsv, faFilePdf } from '@fortawesome/free-solid-svg-icons';
import { Observable, combineLatest } from 'rxjs';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { FascicoloAzioni, FascicoloConfig } from 'src/shared/config/fascicolo.config';
import { PlainTypeStringId } from 'src/shared/models/plain-type-string-id.model';
import { PageObj } from 'src/shared/models/response-object.model';
import { CONST } from '../../../shared/constants';
import { downloadFile } from '../functions/genericFunctions';
import { DettaglioFascicolo } from '../model/fascicolo.models';
import { FascicoloDTO, FascicoloSearch, AcceleratoriDTO, RegistrationStatus } from './../model/entity/fascicolo.models';
import { GroupType } from '../model/enum';
import { OrderByAndPaging } from 'src/shared/components/data-table/data-table.component';


export interface Counter {
  inLavorazione: number,
  trasmesso: number
}

@Component({
  selector: 'app-fascicolo',
  templateUrl: './fascicolo.component.html',
  styleUrls: ['./fascicolo.component.css']
})
export class FascicoloComponent implements OnInit 
{
  /*public counter: Counter;*/
  registrationStatusSummaries$: Observable<PlainTypeStringId[]>;
  faFileCsv = faFileCsv;
  faFilePdf=faFilePdf;
  filters: FascicoloSearch;
  public gruppoScelto: string;
  public acceleratori: AcceleratoriDTO;
  public rsEnum=RegistrationStatus;


  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  paging = {
		page: 1,
		limit: 5
	};
  inCaricamento: boolean = false;
  public fascicoli:PageObj<FascicoloDTO>;
  cols: any[];

  public validGroup: boolean = null;

  constructor(private autpaeSvc: AutorizzazioniPaesaggisticheService,
              private router: Router,
              private user: UserService,
              private localStorageSvc:LocalSessionServiceService) 
  { 
    this.fascicoli = new PageObj<FascicoloDTO>();
  }

  ngOnInit() 
  {
    this.cols = FascicoloConfig.privateListTableHeaders();
    this.validGroup = this.user.validGroup;
    let filterSearchStored:FascicoloSearch=this.localStorageSvc.getValueOrNull(LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY);
    if(filterSearchStored){
      this.filters = filterSearchStored;
    }else{
      //se non esiste ne creo comunque una con la sola parte di page e limit
      this.filters = new FascicoloSearch();
      this.filters.colonna="data_rilascio_autorizzazione";
      this.filters.direzione="DESC";
      this.filters.page=1;
      this.filters.limit=5;
      this.localStorageSvc.storeValue(LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY,this.filters);      
    }
    this.registrationStatusSummaries$=this.autpaeSvc.getContatoriFascicolo();
    this.gruppoScelto = this.user.actualGroup;
  }

  /*getTipoProcedimento(tipoProcedimento: string): string 
  {
    switch (tipoProcedimento) 
    {
      case CONST.TipiProcedimento[0].value: return CONST.TipiProcedimento[0].label;
      case CONST.TipiProcedimento[1].value: return CONST.TipiProcedimento[1].label;
      case CONST.TipiProcedimento[2].value: return CONST.TipiProcedimento[2].label;
      case CONST.TipiProcedimento[3].value: return CONST.TipiProcedimento[3].label;
      case CONST.TipiProcedimento[4].value: return CONST.TipiProcedimento[4].label;
      case CONST.TipiProcedimento[5].value: return CONST.TipiProcedimento[5].label;
      default: break;
    }
  }*/

  /*getEsitoProvvedimento(idEsitoProvvedimento: string): string 
  {
    switch (idEsitoProvvedimento) 
    {
      case CONST.EsitoAutorizzazione[0].value: return CONST.EsitoAutorizzazione[0].label;
      case CONST.EsitoAutorizzazione[1].value: return CONST.EsitoAutorizzazione[1].label;
      case CONST.EsitoAutorizzazione[2].value: return CONST.EsitoAutorizzazione[2].label;
      default: break;
    }
  }*/

  /*
  getTipoIntervento(idTipoIntervento: string): string 
  {
    switch (idTipoIntervento) 
    {
      case CONST.TipoIntervento[0].value: return CONST.TipoIntervento[0].label;
      case CONST.TipoIntervento[1].value: return CONST.TipoIntervento[1].label;
      case CONST.TipoIntervento[2].value: return CONST.TipoIntervento[2].label;
      case CONST.TipoIntervento[3].value: return CONST.TipoIntervento[3].label;
      case CONST.TipoIntervento[4].value: return CONST.TipoIntervento[4].label;
      default: break;
    }
  }
*/
  callbackAlert(event: any) 
  {
    //logica: arrivo on con isChiuso (ho chiuso la dialog) oppure 
    //isConfirm=true ha confermato un'azione e quindi normalmente mi aspetto un extraData
    // in cui ho tutte le info per eseguire l'azione.
    this.alertData.isConfirm = false;
    if (event.isChiuso) 
      this.alertData.display = false;
    else if (event.isConfirm && event.extraData && event.extraData.operazione) 
    {
      switch (event.extraData.operazione) 
      {
        case 'deleteFascicolo':
          this.alertData.display = false;
          this.autpaeSvc.deleteFascicolo(event.extraData.codice).then(res=>
          {
            console.log('res:{}',res);
            if(res.codiceEsito=="OK")
            {
              this.alertData.display = true;
              this.alertData.isConfirm = false;
              this.alertData.title = "Info";
              this.alertData.content = "cancellazione avvenuta con successo!!";
              this.alertData.isConfirm = false;
              this.alertData.extraData = null;
              this.alertData.typ = "info";
              /* this.updateTableData(this.paging); */
              this.fascicoli.list.splice(event.extraData.index, 1);
            }
          });
          break;
        case 'confirmExport':
            this.alertData.display = false;  
            this.alertData.isConfirm = false;
            this.doExport(this.alertData.extraData.type);
        default:
          this.alertData.isConfirm = false;
          this.alertData.extraData = null;
          this.alertData.display = false;
          break;
      }
    } 
    else 
    {
      this.alertData.isConfirm = false;
      this.alertData.extraData = null;
      this.alertData.display = false;
    }

  }

  searchFascicoli(event?: FascicoloSearch): void
  {
    if(event)
    {
      Object.keys(event).forEach(key =>
      {
        this.filters[key] = event[key];
      });
    }
    if(event){
      this.filters.page=1;
    }
    if(!this.filters.page) this.filters.page = 1;
    if(!this.filters.limit) this.filters.limit = 5;
    //salvo nella stored
    let filterSearchStored:FascicoloSearch=this.localStorageSvc.getValueOrNull(LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY);
    if(filterSearchStored){
      filterSearchStored.page=this.filters.page;
      filterSearchStored.limit=this.filters.limit;
      filterSearchStored.colonna=this.filters.colonna;
      filterSearchStored.direzione=this.filters.direzione;
      this.localStorageSvc.storeValue(LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY,filterSearchStored);
      //Nel caso di caricamento dal local storage dei parametri di ricerca si utilizza 
      //il metodo per formattare i campi contenenti date e comune al formato richiesto dal backend
      this.filters=this.formatSearchFields(filterSearchStored);
    }
    this.autpaeSvc.searchFascicoli(this.filters).subscribe(result =>
    {
      let p: PageObj<FascicoloDTO> = new PageObj<any>();
      p.pageNumber = 1;
      if (result.codiceEsito === CONST.OK)
      {
        console.log("result: ", result);
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

  downloadProvvedimento(rowTable: DettaglioFascicolo) {
    this.autpaeSvc.downloadProvvedimento(rowTable.id).subscribe(
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
    /*if (rowTable.urlProvvedimentoFinale) {
      this.todo("Download provvedimento");
    } else {
      this.alertData.title = "Download provvedimento";
      this.alertData.content = "File inesistente";
      //this.alertData.typ="info";
      this.alertData.typ = "warning";
      this.alertData.display = true;
      this.alertData.isConfirm = false;
    }*/
  }

  orderByAndPaging(event: OrderByAndPaging): void
  {
    this.filters.limit = event.rows;
    //this.filters.page = event.offset;
    this.filters.page = event.page;
    if(event.sortField){
      this.filters.colonna = event.sortField;
      this.filters.direzione = event.sortOrder;
    }
    this.searchFascicoli();
  }

  editFascicolo(codice: string) 
  {
    this.router.navigate(['/private/fascicolo/dettaglio/' + codice]);
  }
  
  eliminaFascicolo(codice: string, id: string, index: number) 
  {
    this.alertData.title = "Elimina";
    this.alertData.content = "Sicuro di eliminare " + codice + " ?";
    this.alertData.typ = "danger";
    this.alertData.isConfirm = true;
    this.alertData.extraData = { operazione: 'deleteFascicolo', codice: id, index: index};
    this.alertData.display = true;
  }

  azioniRiga(event)
  {
    if(event.action==FascicoloAzioni.DOWNLOAD_PROVVEDIMENTO){
      this.downloadProvvedimento(event.riga);
    }
    else if (event.action==FascicoloAzioni.EDIT)
    {
      this.editFascicolo(event.riga.id);
    }
    else if (event.action==FascicoloAzioni.VIEW)
    {
      this.editFascicolo(event.riga.id);
    }
    else if (event.action==FascicoloAzioni.DELETE)
    {
      this.eliminaFascicolo(event.riga.codice, event.riga.id, event.index);
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

  public doExport(type: "PDF" | "CSV")
  {
    this.autpaeSvc.exportPrivatePdfCsv(this.filters, type).subscribe(result =>
    {
      if (result.ok)
        downloadFile(result.body, "Risultati di ricerca." + type.toLowerCase());
    });
  }

  nuovoFascicolo(): void { this.router.navigate(["private/fascicolo/nuovo"]); }

  get isED(): boolean { return this.user.groupType === GroupType.ED }
  get isAbilitatoATrasmissione(): boolean { 
    return this.isED || this.user.isComuneAbilitatoTrasmissioneCondoni();
  }
}
