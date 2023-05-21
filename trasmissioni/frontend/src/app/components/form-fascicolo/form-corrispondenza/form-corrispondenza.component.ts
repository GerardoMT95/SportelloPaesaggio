import { HostListener } from '@angular/core';
import { fromWidthToSize } from 'src/app/components/functions/tableSizeHandler';
import { Component, EventEmitter, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ErrorService } from 'src/app/services/error.service';
import { LoadingService } from 'src/app/services/loading.service';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { RicercaComunicazioneService } from 'src/app/services/ricerca-comunicazione.service';
import { UserService } from 'src/app/services/user.service';
import { CONST } from 'src/shared/constants';
import { DettaglioCom } from '../../model/dettaglioComunicazione';
import { Colonna, DettaglioComunicazione, ElencoComunicazione, TipoSelect } from '../../model/model';
import { SearchConfiguration } from './../../form-search/configuration/search.configuration';
import { DettaglioCorrispondenzaDTO } from './../../model/entity/corrispondenza.models';
import { InformazioniDTO } from './../../model/entity/info.models';
import { SearchFields } from './../../model/form-search/formSearch.models';
import { CorrispondenzaSearch } from './../../model/search/search.models';

/**
 * Copiato da Orp Comunicazioni New, ha in piu' la sezione di ricerca in testa alla tabella, ma per adesso
 * serve solo la tabella e NON la ricerca...probabilmente a seguire servirà anche la ricerca
 */
@Component({
  selector: 'app-form-corrispondenza',
  templateUrl: './form-corrispondenza.component.html',
  styleUrls: ['./form-corrispondenza.component.css']
})
export class FormCorrispondenzaComponent implements OnInit
{
  //@Input() dettaglioFascicolo: DettaglioFascicolo;
  @Input("fascicolo") public dettaglio: InformazioniDTO;
  @Input("gestioneComunicazione") public gestioneComunicazione: boolean;

  public configuration: SearchFields[];
  public triggerSearch: EventEmitter<boolean> = new EventEmitter<boolean>();
  public display: boolean = false;
  //public listaComunicazioni: DettaglioComunicazione[] = [];dettaglioCorrispondenzaDTO
  public listaComunicazioni: DettaglioCorrispondenzaDTO[] = [];
  public forceReset: EventEmitter<boolean> = new EventEmitter<boolean>();
  public forceSubmit: EventEmitter<boolean> = new EventEmitter<boolean>();
  public forceSearch: EventEmitter<boolean> = new EventEmitter<boolean>();
  public forseSearchReset: EventEmitter<boolean> = new EventEmitter<boolean>();
  public toEdit: DettaglioCorrispondenzaDTO = null;

  public filter: CorrispondenzaSearch = new CorrispondenzaSearch();

  //GESTIONE DETTAGLIO E NON
  isDettaglio: boolean = false;
  // paginazione e data table
  //listaComunicazioni: NuovaTabellaComunicazione[];
  itemsTotal: number = 0;
  page: number = 1;
  public sortBy: string = 'data_invio';
  public sortOrder: string = 'DESC';
  public isSort: number = 0;

  public isMedium: boolean = true;

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

  rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER
  pages: TipoSelect[] = CONST.PAGINAZIONE;

  displayDettaglio: boolean = false;

  dettaglioCom: DettaglioCom;
  countAllegati: number;
  elencoComunicazioni: ElencoComunicazione = new ElencoComunicazione();

  dettaglioSelezionato: DettaglioComunicazione;
  filtri: TipoSelect[];
  isGestore: boolean = false;

  //LABEL P CALENDAR
  //   #2549
  it: any;
  //CONTROLLA SE L'UTENTE HA  I PERMESSI
  public isPermiss = false;

  // VERIFICA SE L'UTENTE SE' DI TIPO REGIONE
  isRegione = false;
  @ViewChild('noSearch') noSearchReg: NgbModal

  // PARAMETRI NUOVA TABELLA
  public cols: Colonna[] = [
    { field: 'oggetto', header: 'oggetto' },
    { field: 'mittente', header: 'mittente' },
    { field: 'destinatario', header: 'destinatario' },
    { field: 'tipologia', header: 'tipologia' },
    { field: 'numero_protocollo', header: 'nr protocollo' },
    { field: 'data', header: 'data' }
  ]

  constructor(private router: Router, 
              private lss: LocalSessionServiceService, 
              private loadingService: LoadingService, 
              private errorService: ErrorService, 
              private userService: UserService, 
              private modalService: NgbModal, 
              private ricercaService: RicercaComunicazioneService, 
              private fb: FormBuilder, 
              private routeActive: ActivatedRoute,
              private autpaeSvc: AutorizzazioniPaesaggisticheService)
  {
    //super(router, lss);
    /* this.loadingService.emitLoading(true); */
    //mocked
    this.isPermiss = true;
    /* this.loadingService.emitLoading(false) */
    //end mocked                   
    this.it = CONST.IT;

  };

  closeDettaglio(event: boolean): void
  {
    //  this.displayDettaglio= event;
    this.isDettaglio = event;
  }

  ngOnInit()
  {
    this.isMedium = ['medium', 'large'].includes(fromWidthToSize(window.innerWidth));
    this.filter.idFascicolo = this.dettaglio.id.toString();
    this.filter.comunicazione = this.gestioneComunicazione;
    this.filter.colonna = this.sortBy;
    this.filter.direzione = this.sortOrder;
    this.configuration = SearchConfiguration.getCorrispondenzaSearchFields();
    this.ricerca();
    if (this.router.url.indexOf("proposta-comunicazioni") > 0 || true)
    {
      console.log("in ricerca");
      this.isDettaglio = false
    }
    else
    {
      console.log("in dettaglio");
      this.isDettaglio = true
    }

  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any)
  {
    this.isMedium = ['medium', 'large'].includes(fromWidthToSize(window.innerWidth));
  }

  public displayNuovaComunicazione(): void {
    this.toEdit = null;
    this.display = true;
  }

  changeRows(rowsOnPage: number): void
  {
    this.rowsOnPage = rowsOnPage;
    this.page = 1;
    this.triggerSearch.emit(true);
  }

  pageChangedAction(pageNumber: number): void
  {
    this.page = pageNumber;
    this.filter.page = this.page;
    this.ricerca();
  }

  resetRicerca(): void
  {
    //BUG#2470
    this.ricerca();
  }

  open(item: DettaglioCorrispondenzaDTO)
  {
    this.loadingService.emitLoading(true);
    this.autpaeSvc.dettaglioComunicazione(item.corrispondenza.id).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK)
      {
        this.dettaglioCom = result.payload;
        this.dettaglioCom.pec = true;
        this.isDettaglio = true;
        this.countAllegati = this.dettaglioCom.allegati.length;
      }
      this.loadingService.emitLoading(false);
    });
  }

  close(event: boolean)
  {
    this.displayDettaglio = event;
    this.dettaglioSelezionato = null;
  }

  getElencoFromRegione(): void
  {
    if (this.isPermiss)
      this.ricercaService.getFromTo().then(response => this.filtri = response.payload);
  }

  closeReg(): void
  {
    this.modalService.dismissAll();
  }

  // nuovo metodo ordinamento tabella nuova 
  public customSort(event: any)
  {
    this.loadingService.emitLoading(true);
    if(event.sortField){
      this.sortBy = event.sortField;
    }
    if (event.sortOrder == 1)
      this.sortOrder = "DESC";
    else if (event.sortOrder == -1)
      this.sortOrder = "ASC";
    if (this.page != 1)
      this.isSort = 1;
    this.page = 1;
    this.filter.page = this.page;
    this.filter.colonna = this.sortBy;
    this.filter.direzione = this.sortOrder;
    this.ricerca();
  }

  public setFiltri(event: CorrispondenzaSearch): void
  {
    Object.keys(event).forEach(key => this.filter[key] = event[key]);
    //this.filter.comunicazione = this.gestioneComunicazione;
    this.filter.page = this.page;
    this.filter.limit = this.rowsOnPage;
    this.filter.colonna = this.sortBy;
    this.filter.direzione = this.sortOrder ? this.sortOrder.toUpperCase() : null;
  }

  public ricerca(): void
  {
    this.loadingService.emitLoading(true);
    this.autpaeSvc.searchCorrispondenza(this.filter).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK && result.payload)
      {
        this.listaComunicazioni = result.payload.list;
        this.itemsTotal = result.payload.count;
      }
      else
      {
        this.listaComunicazioni = [];
      }
      this.loadingService.emitLoading(false);
    });
  }

  public reset(): void
  {
    this.page = 1;
    this.rowsOnPage = 5;
    this.filter.destinatario = null;
    this.filter.mittente = null;
    this.filter.dataInvioA = null;
    this.filter.dataInvioDa = null;
    this.filter.oggetto = null;
    this.filter.page = this.page;
    this.filter.limit = this.rowsOnPage;
    this.ricerca();
  }

  public inviaComunicazione(event: any): void
  {
    let files = event.allegati;
    event.allegati = null;
    if (!event.idFascicoli) {
      event.idFascicoli = [];
    }
    event.idFascicoli.push(this.dettaglio.id);
    console.log("Stampo event di inviaComunicazione: ", event);
    this.autpaeSvc.inviaNuovaComunicazione(event, files,this.dettaglio.id).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload)
      {
        this.ricerca();
        this.hide();
      }
    });
  }

  public edit(toEdit: DettaglioCorrispondenzaDTO): void
  {
    console.log("stampo il valore da editare: ", toEdit);
    this.toEdit = toEdit;
    this.display = true;
  }

  public hide(): void
  {
    this.display=false;
    this.forceReset.emit(true);
  }

  public emettiSubmit(bozza: boolean): void
  {
    this.forceSubmit.emit(bozza);
  }

  public requestDeleteBozza(id: number, index: number): void
  {
    let message = "dettaglioComunicazione.c-delete";
    let title = "dettaglioComunicazione.t-delete";
    this.alertData =
    {
      content: message,
      title: title,
      typ: "warning",
      extraData: {operazione: "deleteBozza", data: {id, index}},
      isConfirm: true,
      labelAlertChiudi: 'generic.chiudi',
      labelAlertOk: 'generic.conferma',
      display: true
    };
  }

  public deleteBozza(id: number, index: number): void
  {
    //todo messaggio di conferma
    this.loadingService.emitLoading(true);
    this.autpaeSvc.deleteBozzaComunicazioni(id,this.dettaglio.id).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK)
        this.listaComunicazioni.splice(index, 1);
      this.loadingService.emitLoading(false);
    });
  }

  public callbackAlert(event: any): void
  {
    if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'deleteBozza':
            this.deleteBozza(event.extraData.data.id, event.extraData.data.index);
            break;
          default:
            break;
        }
      }
    }
    this.alertData.display = false;
  }

}
