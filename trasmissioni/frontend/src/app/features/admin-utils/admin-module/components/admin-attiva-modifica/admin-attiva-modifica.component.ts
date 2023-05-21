import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CONST } from './../../../../../../shared/constants';
import { FascicoloDTO, FascicoloSearch } from './../../../../../components/model/entity/fascicolo.models';
import { PaginatedBaseResponse } from './../../../../../components/model/paginated-base-response';
import { AdminService } from './../../../../../services/admin-service/admin.service';
import { LoadingService } from './../../../../../services/loading.service';

@Component({
  selector: 'app-admin-attiva-modifica',
  templateUrl: './admin-attiva-modifica.component.html',
  styleUrls: ['./admin-attiva-modifica.component.css']
})
export class AdminAttivaModificaComponent implements OnInit {

  public colonneTabella: any[] =
  [
    { header: "Codice Fascicolo" },
    { header: "Oggetto" },
    { header: "Tipologia Procedimento" },
    { header: "Comune" },
    { header: "Responsabile Procedimento" },
    { header: "Numero Provvedimento" },
    { header: "Data Provvedimento" },
    { header: "Esito" },
  ];
  public fascicoliDaAttivare: Array<FascicoloDTO> = [];
  public fascicoliAttivati: PaginatedBaseResponse<FascicoloDTO>;

  public const = CONST;
  
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  public form: FormGroup;
  public attivazioneDisattivazione: boolean = false; /* quando a true mostra il messaggio di validazione */
  public checked: boolean = true; /* stato di default dello switch della tabella di sotto */
  public codiceDaCercare: string = null;
  public results: Array<string> = [];
  public codiceDaCercareTraAttivi: string = null;
  public resultsAttivi: Array<string> = [];
  /* public schemaRicerca: SchemaRicerca = { registrazione: false, verifica: false }; decommentare se ricerca con tutti i campi */

  public page: number = 1;
  public rows: number = 5;

  constructor(private adminService: AdminService,
              private formBuilder: FormBuilder,
              private loadingService: LoadingService) { }

  ngOnInit() {
    this.buildForm();
    this.initTabellaFascicoliAttivati();
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'confermaAttivazione':
            this.confermaAttivazione(event.extraData.idFascicolo);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  public buildForm(): void {
    this.form = this.formBuilder.group({
      giorni: [null, [Validators.required, Validators.min(0)]],
      modificaAttivata: [false]
    });
  }

  public initTabellaFascicoliAttivati(filters?: FascicoloSearch): void {
    if (!filters) {
      filters = new FascicoloSearch();
      filters.page = 1;
      filters.limit = 5;
    }
    this.adminService.getFascicoliInModifica(filters).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.fascicoliAttivati = response.payload;
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



  public updateData(event?: any): any 
  {
    let filters: FascicoloSearch = new FascicoloSearch();
    if(event)
    {
      if(event.first) filters.page = (event.first / event.rows) + 1;
      else if(event.page) filters.page = event.page;
      if(event.limit) filters.limit = event.limit;
    }
    this.initTabellaFascicoliAttivati(filters);
  }

  public ricerca(): void {
    this.loadingService.emitLoading(true);
    this.adminService.getFascicoloDaModificare(this.codiceDaCercare).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.fascicoliDaAttivare = [];
          if (response.payload) {
            this.fascicoliDaAttivare.push(response.payload);
          }
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

  public ricercaAttivi(): void {
    let filters: FascicoloSearch = new FascicoloSearch();
    filters.codice = this.codiceDaCercareTraAttivi;
    this.loadingService.emitLoading(true);
    this.adminService.getFascicoliInModifica(filters).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.fascicoliAttivati.list = [];
          this.fascicoliAttivati.count = 0;
          if (response.payload) {
            this.fascicoliAttivati = response.payload;
          }
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

  public resetAttivi(event: any): void {
    /* oltre a resettare la form porta la tabella allo stato originario */
    this.codiceDaCercareTraAttivi = null;
    this.resultsAttivi = [];
    this.initTabellaFascicoliAttivati();
  }

  public reset(): void {
    this.codiceDaCercare = null;
    this.results = [];
    this.fascicoliDaAttivare = [];
  }

  public attivaModifica(event: any, idFascicolo: string): void {
    this.attivazioneDisattivazione = true;
    /* se la form è valida */
    if (this.form.valid) {
      this.form.get("modificaAttivata").setValue(false);
      this.form.updateValueAndValidity();
      this.alertData = {
        display: true,
        title: "Info",
        content: "Sei sicuro di voler attivare la modifica per questo fascicolo?",
        typ: "info",
        extraData: { idFascicolo: idFascicolo, operazione: "confermaAttivazione" },
        isConfirm: true,
      };
    }
    /* se la form non è valida */
    else {
      this.alertData = {
        display: true,
        title: "Attenzione",
        content: "E' obbligatorio indicare il numero di giorni",
        typ: "danger",
        extraData: null,
        isConfirm: false,
      };
      this.form.get("modificaAttivata").setValue(false);
      this.form.updateValueAndValidity();
    }
  }

  public disattivaModifica(event: any, idFascicolo: string, index: number): void {
    this.loadingService.emitLoading(true);
    this.adminService.revocaModifica(idFascicolo).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Disattivazione modifica terminata con successo",
            typ: "success",
            extraData: null,
            isConfirm: false,
          };
          this.fascicoliAttivati.list.splice(index, 1);
          this.fascicoliAttivati.count = this.fascicoliAttivati.count - 1; 
          this.checked = true;
          this.loadingService.emitLoading(false);
        }
        else {
          this.checked = true;
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: "Errore durante lo svolgimento dell'operazione, riprovare",
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
        this.loadingService.emitLoading(false);
      }
    );
  }

  public confermaAttivazione(idFascicolo: string): void {
    this.loadingService.emitLoading(true);
    this.adminService.modificaFascicolo(idFascicolo, this.form.get("giorni").value).subscribe(
      response => {
        /* se risponde OK */
        if (response.codiceEsito === "OK") {
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Operazione terminata con successo, ora il fascicolo è visibile nella tabella sottostante",
            typ: "success",
            extraData: null,
            isConfirm: false,
          };
          this.fascicoliAttivati.list.push(this.fascicoliDaAttivare[0]);
          this.fascicoliAttivati.count = this.fascicoliAttivati.count + 1;
          this.fascicoliDaAttivare = [];
          this.loadingService.emitLoading(false);
        }
        /* se risponde KO */
        else {
          this.alertData = {
            display: true,
            title: "Errore",
            content: "Errore durante lo svolgimento dell'operazione, riprovare",
            typ: "danger",
            extraData: null,
            isConfirm: false,
          };
          this.form.get("modificaAttivata").setValue(false);
          this.form.updateValueAndValidity();
          this.loadingService.emitLoading(false);
        }
      },
      /* se c'è un errore lato server */
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: error.message,
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
        this.form.get("modificaAttivata").setValue(false);
        this.form.updateValueAndValidity();
        this.loadingService.emitLoading(false);
      }
    );
  }

  getTipoProcedimento(tipoProcedimento: string): string {
    switch (tipoProcedimento.toUpperCase()) {
      case CONST.TipiProcedimento[0].value.toUpperCase(): return CONST.TipiProcedimento[0].label;
      case CONST.TipiProcedimento[1].value.toUpperCase(): return CONST.TipiProcedimento[1].label;
      case CONST.TipiProcedimento[2].value.toUpperCase(): return CONST.TipiProcedimento[2].label;
      case CONST.TipiProcedimento[3].value.toUpperCase(): return CONST.TipiProcedimento[3].label;
      case CONST.TipiProcedimento[4].value.toUpperCase(): return CONST.TipiProcedimento[4].label;
      case CONST.TipiProcedimento[5].value.toUpperCase(): return CONST.TipiProcedimento[5].label;
      case CONST.TipiProcedimento[6].value.toUpperCase(): return CONST.TipiProcedimento[6].label;
      default: break;
    }
  }

  getEsitoAutorizzazione(esito: string): string 
  {
    switch (esito) 
    {
      case CONST.EsitoAutorizzazione[0].value: return CONST.EsitoAutorizzazione[0].label;
      case CONST.EsitoAutorizzazione[1].value: return CONST.EsitoAutorizzazione[1].label;
      case CONST.EsitoAutorizzazione[2].value: return CONST.EsitoAutorizzazione[2].label;
      default: break;
    }
  }

  public autocompleteCodice(event: any): void {
    this.adminService.autocompleteCodice(event.query,null,true).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.results = response.payload;
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

  public autocompleteCodiceAttivi(event: any): void {
    this.adminService.autocompleteCodice(event.query,null,null,true).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.resultsAttivi = response.payload;
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

  get fascicoliAttiviList() { return this.fascicoliAttivati ? this.fascicoliAttivati.list : [] }
  get totalRecords() { return this.fascicoliAttivati ? this.fascicoliAttivati.count : 0 }

}
