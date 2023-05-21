import {  Component, Input, OnInit, ViewChild } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { downloadFile } from 'src/app/shared/functions/generic.utils';
import { Allegato, Fascicolo } from 'src/app/shared/models/models';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { CorrispondenzaSearch, DettaglioCorrispondenzaDTO, DestinatarioComunicazioneDTO, CorrispondenzaDTO, TemplateComunicazione, TemplateEmailDestinatariDto, Template, TemplateDestinatarioDTO, TipologicaDTO,AllegatoCorrispondenza } from '../../models/corrispondenza.model';
import { DialogService } from './../../../../core/services/dialog.service';
import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../shared/constants';
import { ComunicazioniService } from './../../../../shared/services/comunicazioni/comunicazioni.service';
import { DialogService as PrimengDialogService } from 'primeng/primeng';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
@Component({
  selector: 'app-form-corrispondenza',
  templateUrl: './form-corrispondenza.component.html',
  styleUrls: ['./form-corrispondenza.component.css'],
  providers: [PrimengDialogService]
})

export class FormCorrispondenzaComponent implements OnInit {
  @Input("fascicolo") public fascicoloInput: Fascicolo;
  @Input("gestioneComunicazione") public gestioneComunicazione: boolean;
  searchObject: any = {};
  comunicazioniTableHeaders: TableConfig[] = [];
  currentUser: LoggedUser;
  templates: TemplateComunicazione[] = [];
  corrispondenzaSearch: CorrispondenzaSearch = new CorrispondenzaSearch();
  public indirizziMailDefault: DestinatarioComunicazioneDTO[] = [];
  public statoComponent = "RIEPILOGO"; //RIEPILOGO  COMPOSIZIONE (caso di editing bozza o nuova) DETTAGLIO (visualizzazione comunicazione già inviata)
  public comunicazioni: DettaglioCorrispondenzaDTO[] = [];
  public idComunicazione: number | string;
  public numeroTotaleOggetti: number = 0;
  public numeroOggettiRestituiti: number = 0;
  public dettaglioCorrispondenzaDTO: DettaglioCorrispondenzaDTO;
  public dettaglioOpened: DettaglioCorrispondenzaDTO; //utilizzato nella vista del dettaglio
  public visibilitaMailDefault: boolean = false;

  constructor(
    private customDialogService: DialogService,
    private userService: UserService,
    private gestioneIstanzaComunicazioniService: ComunicazioniService,
    private loadingService: LoadingService,
    private dominioService: HttpDominioService
  ) {
    
  }

  ngOnInit() {
    this.currentUser = this.userService.getUser();
    this.corrispondenzaSearch.idPratica = this.fascicolo.id;
    this.corrispondenzaSearch.limit = CONST.DEFAULT_PAGE_NUMBER;
    this.corrispondenzaSearch.page = 1;
    this.corrispondenzaSearch.sortBy = 'dataInvio';
    this.corrispondenzaSearch.sortType = 'DESC';
    this.retrieveComunicazioni();
    this.visibilitaRegEDMailDefault(this.fascicolo.enteDelegato);
    this.comunicazioniTableHeaders = [
      {
        header: 'Mittente',
        field: 'mittenteEmail'
      },
      {
        header: 'Destinatari',
        field: 'destinatari'
      },
      {
        header: 'Oggetto',
        field: 'oggetto'
      },
      {
        header: 'Data invio',
        field: 'dataInvio'
      },
      {
        header: '',
        field: 'displayButton',
        width: 8
      }
    ];
    //this.retrieveTemplate(); //nessun template disponibile per il richiedente...
  }


  private retrieveTemplate() {
    this.gestioneIstanzaComunicazioniService.
      getTemplatDestEmailList("", this.fascicolo.id).
      subscribe(_result => {
        if (_result.codiceEsito === CONST.OK && _result.payload) {
          _result.payload.map((m: TemplateEmailDestinatariDto) => {
            let templateTemp: TemplateComunicazione = new TemplateComunicazione()
            templateTemp.descrizione = m.template.descrizione
            templateTemp.label = m.template.descrizione
            templateTemp.value = m.template.codice
            templateTemp.template = new Template()
            templateTemp.template.oggetto = m.template.oggetto
            templateTemp.template.testo = m.template.testo
            templateTemp.template.riservata = m.template.riservata;
            templateTemp.template.protocollazione = m.template.protocollazione;
            templateTemp.template.destinatari = []
            m.destinatari.map((d: TemplateDestinatarioDTO) => {
              let destinatario: DestinatarioComunicazioneDTO = new DestinatarioComunicazioneDTO()
              destinatario.email = d.email
              destinatario.id = d.id
              destinatario.nome = d.denominazione
              destinatario.pec = d.pec == "pec" ? true : false
              destinatario.tipo = d.tipo
              templateTemp.template.destinatari.push(destinatario)
            })
            this.templates.push(templateTemp)
          })
          this.loadingService.emitLoading(false)
        } else {
          this.loadingService.emitLoading(false)
        }
      })
  }

  public inviaComunicazioneTrue(): void {
    //this.inviaComunicazione = true;
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.creaBozza(this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.idComunicazione = response.payload.corrispondenza.id;
          this.dettaglioCorrispondenzaDTO = response.payload;
          this.statoComponent = "COMPOSIZIONE";
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        //this.inviaComunicazione = false;
        this.statoComponent = "RIEPILOGO";
      }
    );
  }

  get fascicolo(): Fascicolo {
    return this.fascicoloInput;
  }

  get comunicazione() {
    if (this.comunicazioni) {
      return this.comunicazioni;
    }
  }

  insertedByCurrentUser(insertedBy: string, currentUser: string) {
    return insertedBy === currentUser;
  }

  updateQuery(searchQuery?: any) {
    let oldSearch = this.corrispondenzaSearch;
    this.corrispondenzaSearch = new CorrispondenzaSearch();
    this.corrispondenzaSearch.limit = oldSearch.limit;
    this.corrispondenzaSearch.page = oldSearch.page;
    this.corrispondenzaSearch.idPratica = oldSearch.idPratica;
    this.corrispondenzaSearch.sortBy = oldSearch.sortBy;
    this.corrispondenzaSearch.sortType = oldSearch.sortType;
    if (searchQuery) {
      Object.keys(searchQuery).forEach(key => {
        this.corrispondenzaSearch[key] = searchQuery[key];
      });
    }
    this.retrieveComunicazioni();
  }


  public sendData(corrispondenzaDTO: DettaglioCorrispondenzaDTO, withProto: boolean): void {
    if (corrispondenzaDTO.corrispondenza.bozza === true) {
      this.salvaBozza(corrispondenzaDTO);
    }
    else {
      this.sendComunicazione(corrispondenzaDTO, withProto);
    }
  }

  public showPreview(corrispondenzaDTO: DettaglioCorrispondenzaDTO): void {
    this.loadingService.emitLoading(true);
    if (corrispondenzaDTO.corrispondenza.bozza === true) {
      this.gestioneIstanzaComunicazioniService.salvaBozza(this.fascicolo.id, corrispondenzaDTO).subscribe(
        response => {
          this.loadingService.emitLoading(false);
          if (response.codiceEsito === "OK") {
            this.loadingService.emitLoading(true);
            this.gestioneIstanzaComunicazioniService.anteprimaBozza(this.fascicolo.id, corrispondenzaDTO).subscribe(
              resp => {
                if (resp.status == 200)
                  downloadFile(resp.body, "AnteprimaComunicazione.pdf");
                this.loadingService.emitLoading(false);
              }
            );
          }
        },
        error => {
          this.errorOnSend(error);
        }
      );
    } else {
      this.customDialogService.showDialog(
        true,
        'COMMUNICATIONS.NO_PREVIEW',
        'generic.warning',
        DialogButtons.CHIUDI,
        null,
        DialogType.ERROR,
        null,
        null
      );
    }
  }

  private errorOnSend(message: string) {
    this.loadingService.emitLoading(false);
    this.customDialogService.showDialog(
      true,
      message,
      "Attenzione",
      DialogButtons.CHIUDI,
      null,
      DialogType.ERROR,
      null,
      null
    );
  }

  /**
   * viene salvata e poi effettuato il send....
   */
  private sendComunicazione(corrispondenzaDTO: DettaglioCorrispondenzaDTO, withProto: boolean): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.salvaBozza(this.fascicolo.id, corrispondenzaDTO).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.loadingService.emitLoading(true);
          this.gestioneIstanzaComunicazioniService.sendComunicazione(this.fascicolo.id, response.payload, withProto)
            .subscribe(respSend => {
              this.loadingService.emitLoading(false);
              if (respSend.codiceEsito === "OK") {
                this.customDialogService.showDialog(
                  true,
                  'generic.operazioneOk',
                  "Successo",
                  DialogButtons.CHIUDI,
                  null,
                  DialogType.SUCCESS,
                  null,
                  null
                );
                this.statoComponent = "RIEPILOGO";
                this.retrieveComunicazioni();
              } else {
                this.errorOnSend("errore durante l'invio della comunicazione");
                this.statoComponent = "RIEPILOGO";
              }
            }, error => this.errorOnSend(error));
        }
      });
  }

  private salvaBozza(corrispondenzaDTO: DettaglioCorrispondenzaDTO): void {
    let dettCorrispondenzaDTO = corrispondenzaDTO;
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.salvaBozza(this.fascicolo.id, dettCorrispondenzaDTO).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          this.statoComponent = "RIEPILOGO";
          this.retrieveComunicazioni();
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.statoComponent = "RIEPILOGO";
      }
    );
  }

  public retrieveComunicazioni(): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazioni(this.corrispondenzaSearch).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.comunicazioni = response.payload.list;
          this.numeroTotaleOggetti = response.numeroTotaleOggetti;
          this.numeroOggettiRestituiti = response.numeroOggettiRestituiti;
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public retrieveDestinatariDefault(idEnteDelegato: string): void
   {
    this.loadingService.emitLoading(true)
    this.dominioService.getIndirizziMailDefault(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          this.indirizziMailDefault = response.payload;
          this.indirizziMailDefault.forEach(element => {
            element.tipo = 'TO';
          });
          if(this.indirizziMailDefault.length > 0)
            this.visibilitaMailDefault = true;
          else 
            this.visibilitaMailDefault = false;
          this.loadingService.emitLoading(false);
         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loadingService.emitLoading(false)
       }
     );
  }

  public visibilitaRegEDMailDefault(idEnteDelegato:string) {
    this.loadingService.emitLoading(true)
    this.dominioService.getTipoEnteOrg(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          if(response.payload == "REG" || response.payload == "ED") 
            this.retrieveDestinatariDefault(idEnteDelegato)
          else 
            this.visibilitaMailDefault = true;
          this.loadingService.emitLoading(false);
         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loadingService.emitLoading(false)
       }
     );

  }
  
  public azioni(container: any): void {
    if (container.azione === "modifica") {
      this.modificaBozza(container.id);
    }
    else if (container.azione === "elimina") {
      this.customDialogService.showDialog(
        true,
        "Sei sicuro di voler eliminare la comunicazione?",
        "Attenzione",
        DialogButtons.CONFERMA_CHIUDI,
        (idbutton: number) => {
          if (idbutton === 1) {
            this.eliminaBozza(container.id);
          }
        },
        DialogType.WARNING,
        null,
        null
      );
    }
    else {
      this.dettaglio(container.id, this.fascicolo.id);
    }
  }

  private dettaglio(idComunicazione: number, idFascicolo: string): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazione(idComunicazione, idFascicolo).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.openDettaglioModal(response.payload);
          console.log(response.payload);
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        console.log(error.message);
      }
    );
  }

  private openDettaglioModal(dettaglioComunicazioneMocked: DettaglioCorrispondenzaDTO): void {
    this.statoComponent = "DETTAGLIO";
    this.dettaglioOpened = dettaglioComunicazioneMocked;
  }

  private eliminaBozza(idComunicazione: number): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.eliminaBozza(idComunicazione, this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          this.retrieveComunicazioni();
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  private modificaBozza(idComunicazione: number): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazione(idComunicazione, this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.dettaglioCorrispondenzaDTO = response.payload;
          this.idComunicazione = idComunicazione;
          this.statoComponent = "COMPOSIZIONE";
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
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
   * toglie la parte di Fascicolo APPTR-XXXXX usando lo spazio come separatore...e togliendo i primi 2 tokens
   */
  private formattaStringa(oggetto: string): string {
    let oggettoInserito: string = "";
    if (oggetto) {
      let oggettoSplittato: Array<string> = oggetto.split(" ");
      let oggettoSenzaCodiceFascicolo: Array<string> = oggettoSplittato.slice(2, oggettoSplittato.length);
      oggettoInserito = oggettoSenzaCodiceFascicolo.join(" ");
    }
    return oggettoInserito;
  }

  /*private getAllegato(resp: TipologicaDTO): Allegato {
    let allegato = new AllegatoCorrispondenza();
    allegato.id = resp.codice;
    allegato.nome = resp.label;
    allegato.data = new Date();
    return allegato;
  }*/

  public uploadFile(container: any): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.upload(container.file, this.fascicolo.id, container.idComunicazione).subscribe(response => {
      this.loadingService.emitLoading(false);
      if (response.codiceEsito === CONST.OK) {
        if (!this.dettaglioCorrispondenzaDTO.allegatiInfo)
          this.dettaglioCorrispondenzaDTO.allegatiInfo = [];
        //this.dettaglioCorrispondenzaDTO.allegatiInfo.push(this.getAllegato(response.payload));
        this.dettaglioCorrispondenzaDTO.allegatiInfo.push(response.payload);
      }
      this.loadingService.emitLoading(false);
    });
  }

  public deleteFile(file: AllegatoCorrispondenza): void {
    this.customDialogService.showDialog(
      true,
      "Sei sicuro di voler eliminare l'allegato?",
      "Attenzione",
      DialogButtons.CONFERMA_CHIUDI,
      (idbutton: number) => {
        if (idbutton === 1) {
          this.eliminaFile(file.id, this.idComunicazione, this.fascicolo.id, this.fascicolo.codicePraticaAppptr);
        }
      },
      DialogType.WARNING,
      null,
      null
    );
  }

  private eliminaFile(id: any, idComunicazione: number | string, idFascicolo: string, codiceFascicole: string): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.deleteFile(idFascicolo, id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            response.descrizioneEsito,
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          let idxToRemove = this.dettaglioCorrispondenzaDTO.allegatiInfo.findIndex(all => all.id == id);
          if (idxToRemove >= 0) {
            this.dettaglioCorrispondenzaDTO.allegatiInfo.splice(idxToRemove, 1);
          }
        }
      },
      error => {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public downloadFile(file: AllegatoCorrispondenza): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.downloadAllegato(file.id, this.fascicolo.id)
      .subscribe(response => {
        if (response.status == 200)
          downloadFile(response.body, file.nome);
        this.loadingService.emitLoading(false);
      });
  }

  chiusoDettaglio($event) {
    this.statoComponent = "RIEPILOGO";
    this.retrieveComunicazioni();
  }

  chiusoDettaglioView($event) {
    this.statoComponent = "RIEPILOGO";
  }

}








//-------------------------------------------OLD
/**
 * Copiato da Orp Comunicazioni New, ha in piu' la sezione di ricerca in testa alla tabella, ma per adesso 
 * serve solo la tabella e NON la ricerca...probabilmente a seguire servirà anche la ricerca
 */
/*class FormCorrispondenzaComponentOld implements OnInit {

  @Input("fascicolo") public dettaglio: Fascicolo;
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


  //GESTIONE DETTAGLIO E NON
  isDettaglio: boolean = false;
  // paginazione e data table
  //listaComunicazioni: NuovaTabellaComunicazione[];
  itemsTotal: number = 0;
  page: number = 1;
  public sortBy: string = 'data';
  public sortOrder: string = 'desc';
  public isSort: number = 0;

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
  @ViewChild('noSearch', { static: false }) noSearchReg: NgbModal

  // PARAMETRI NUOVA TABELLA
  public cols: Colonna[] = [
    { field: 'oggetto', header: 'oggetto' },
    { field: 'mittente', header: 'mittente' },
    { field: 'destinatario', header: 'destinatario' },
    { field: 'tipologia', header: 'tipologia' },
    { field: 'numero_protocollo', header: 'nr protocollo' },
    { field: 'data', header: 'data' }
  ]

  constructor(private loadingService: LoadingService,
    private modalService: NgbModal,
    private dialog: DialogService,
    private service: ComunicazioniService) {
    this.isPermiss = true;
    this.it = CONST.IT;
  };

  closeDettaglio(event: boolean): void {
    //  this.displayDettaglio= event;
    this.isDettaglio = event;
  }

  ngOnInit() {
    this.configuration = SearchConfiguration.getCorrispondenzaSearchFields();
    this.ricerca({});
    this.isDettaglio = false
  }

  //public displayNuovaComunicazione(): void { this.display = true; }

  public createAndDisplay(): void {
    this.loadingService.emitLoading(true);
    this.service.create(this.dettaglio.id).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        this.listaComunicazioni.push(response.payload);
        this.toEdit = response.payload;
        this.display = true;
      }
      this.loadingService.emitLoading(false);
    });
  }

  changeRows(rowsOnPage: number): void {
    this.rowsOnPage = rowsOnPage;
    this.page = 1;
    this.triggerSearch.emit(true);
  }

  pageChangedAction(pageNumber: number): void {
    this.page = pageNumber;
    this.triggerSearch.emit(true);
  }

  resetRicerca(): void {
    //BUG#2470
    this.ricerca({});
  }

  open(item: DettaglioCorrispondenzaDTO) {

   

  }
  close(event: boolean) {
    this.displayDettaglio = event;
    this.dettaglioSelezionato = null;
  }

 

  closeReg(): void { this.modalService.dismissAll(); }

  // nuovo metodo ordinamento tabella 
  public customSort(event: any) {
    this.loadingService.emitLoading(true);
    this.sortBy = event.sortField;
    if (event.sortOrder == 1)
      this.sortOrder = "desc";
    else if (event.sortOrder == -1)
      this.sortOrder = "asc";
    if (this.page != 1)
      this.isSort = 1;
    this.page = 1;
    this.triggerSearch.emit(true);
  }

  public ricerca(event: any): void {
    let filter: CorrispondenzaSearch = event;
    filter.idPratica = this.dettaglio.id.toString();
    filter.comunicazione = this.gestioneComunicazione;
    filter.page = this.page;
    filter.limit = this.rowsOnPage;
    //filter.colonna = this.sortBy;
    filter.direzione = this.sortOrder ? this.sortOrder.toUpperCase() : null;
    this.loadingService.emitLoading(true);
    this.service.search(filter).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        this.listaComunicazioni = response.payload.list;
        this.itemsTotal = response.payload.count;
      }
      this.loadingService.emitLoading(false);
    });
  }

  public reset(event: any): void { this.ricerca({}); }

  public inviaComunicazione(dettaglio: DettaglioCorrispondenzaDTO): void {
    if (dettaglio.corrispondenza.bozza || this.isValid(dettaglio)) {
      this.loadingService.emitLoading(true);
      this.service.update(dettaglio).subscribe(response => {
        if (response.codiceEsito === CONST.OK) {
          let index = this.listaComunicazioni.map(m => m.corrispondenza.id).indexOf(response.payload.corrispondenza.id);
          if (index != -1)
            this.listaComunicazioni[index] = response.payload;
        }
        this.loadingService.emitLoading(false);
      });
    }
    else {
      let message = "Destinatari non validi o informazioni mancanti";
      this.dialog.showDialog(true, message, "Attenzione", DialogButtons.CHIUDI, null, DialogType.ERROR);
    }
  }

  private isValid(dettaglio: DettaglioCorrispondenzaDTO): boolean {
    let step1 = (d: DestinatarioComunicazioneDTO[]) => { return d && d.some(p => p.tipo == 'TO') };
    let step2 = (c: CorrispondenzaDTO) => { return c && c.oggetto && c.oggetto != "" && c.text && c.text != "" };
    return step1(dettaglio.destinatari) && step2(dettaglio.corrispondenza);
  }

  public edit(toEdit: DettaglioCorrispondenzaDTO): void {
    this.toEdit = toEdit;
    this.display = true;
  }

  public hide(): void { this.forceReset.emit(true); }
  public emettiSubmit(bozza: boolean): void { this.forceSubmit.emit(bozza); }

  public deleteBozza(id: number, index: number): void {
    let message = "Sei sicuro di voler eliminare la bozza di questa comunicazione?";
    this.dialog.showDialog(true, message, "Attenzione", DialogButtons.OK_CANCEL, (buttonId) => {
      if (buttonId == 1) {
        this.loadingService.emitLoading(true);
        this.service.delete(this.dettaglio.id, id).subscribe(response => {
          if (response.codiceEsito === CONST.OK)
            this.listaComunicazioni.splice(index, 1);
          this.loadingService.emitLoading(false);
        });
      }
    }, DialogType.WARNING);
  }

  public chiudiNuovaComunicazione(): void {
    console.log("chiudi");
    this.toEdit = null;
    this.display = false;
  }

}*/
