import { Paging } from 'src/shared/components/rows-number-handler/rows-number-handler.component';
import { LoadingService } from './../../../app/services/loading.service';
import { AssegnaFascicoloConfigService } from './../../service/assegna-fascicolo-config/assegna-fascicolo-config.service';
import { SelectItem } from 'primeng/components/common/selectitem';
import { TabelleAssegnamentoFascicoli, AssegnamentoFascicolo, StoricoAssegnamento } from './../../models/assegna-fascicolo-models';
import { AssegnaFascicoloService } from './../../service/assegna-fascicolo/assegna-fascicolo.service';
import { CONST } from './../../constants';
import { PaginatedBaseResponse } from './../../../app/components/model/paginated-base-response';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { StatoFascicolo } from "src/shared/models/registration-status";

@Component({
  selector: 'app-assegna-fascicolo',
  templateUrl: './assegna-fascicolo.component.html',
  styleUrls: ['./assegna-fascicolo.component.css']
})
export class AssegnaFascicoloComponent implements OnInit {

  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };
  public form: FormGroup;
  public colonneTabellaFascicoliDaAssegnare: any[] = [
    { header: "Codice Fascicolo" },
    { header: "Comune" },
    { header: "Oggetto" },
    { header: "Tipologia Procedimento" },
    { header: "Stato" },
  ];
  public fascicoliDaAssegnare: PaginatedBaseResponse<TabelleAssegnamentoFascicoli>;
  public colonneTabellaFascicoliAssegnati: any[] = [
    { header: "Codice Fascicolo" },
    { header: "Comune" },
    { header: "Tipologia Procedimento" },
    { header: "Data ultima assegnazione" },
    { header: "Stato" },
    { header: "Funzionario" },
    { header: "Numero riassegnazioni" },
  ];
  public fascicoliAssegnati: PaginatedBaseResponse<TabelleAssegnamentoFascicoli>;
  public displayAssegnaFascicolo: boolean = false;
  public displayStoricoAssegnazioni: boolean = false;
  public codiceFascicoloScelto: string;
  public fascicoloScelto: TabelleAssegnamentoFascicoli;
  public colonneTabellaStoricoAssegnazioni: any[] = [
    { header: "Funzionario" },
    { header: "Data assegnazione funzionario" },
    { header: "Tipo operazione" },
  ];
  public storicoAssegnazioni: Array<StoricoAssegnamento>;
  public listaFunzionari: SelectItem[] = [];
  public resultsDaAssegnare: Array<string> = [];
  public codiceFascicoloDaAssegnareRicerca: string
  public resultsAssegnato: Array<string> = [];
  public codiceFascicoloAssegnatoRicerca: string;
  public isAssegnamento: boolean = null; /* mi serve per capire se sto per fare un assegnamento o un riassegnamento */
  public rowsDaAssegnare: number = 5;
  public rowsAssegnati: number = 5;

  //gestione paginazione
  public pagingDaAssegnare: Paging = { page: 1, limit: 5 };
  public pagingAssegnati: Paging = { page: 1, limit: 5 };

  constructor(private formBuilder: FormBuilder,
    private assegnaFascicoloService: AssegnaFascicoloService,
    private assegnaFascicoloConfigService: AssegnaFascicoloConfigService,
    private loadingService: LoadingService) { }

  ngOnInit() {
    this.buildForm();
    /* this.initTabellaFascicoliDaAssegnare(null, 1, 5); */ /* parte in automatico il metodo della paginazione che inizializza la tabella */
    /* this.initTabellaFascicoliAssegnati(null, 1, 5); */ /* parte in automatico il metodo della paginazione che inizializza la tabella */
    this.initListaFunzionari();
  }

  public callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'confermaDisassegnamento':
            this.confermaDisassegnamento(event.extraData.rowData);
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
      /* rupAssegnato: [null], */
      funzionarioAssegnato: [null, Validators.required], /* obbligatorio solo se si vuole fare un assegnamento */
    });
  }

  public initTabellaFascicoliDaAssegnare(codice: string, page: number, limit: number): void {
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloService.tabellaAssegnamentoFascicoliSearch(false, codice, page, limit).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.fascicoliDaAssegnare = response.payload;
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

  public initTabellaFascicoliAssegnati(codice: string, page: number, limit: number): void {
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloService.tabellaAssegnamentoFascicoliSearch(true, codice, page, limit).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.fascicoliAssegnati = response.payload;
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

  public initListaFunzionari(): void {
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloConfigService.getUtentiForOrganizzazione().subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          response.payload.forEach(elem => {
            let selectItem: SelectItem = {
              label: elem.label,
              value: elem.codice,
            };
            this.listaFunzionari.push(selectItem);
          });
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

  getTipoProcedimento(tipoProcedimento: string): string {
    switch (tipoProcedimento) {
      case CONST.TipiProcedimento[0].value: return CONST.TipiProcedimento[0].label;
      case CONST.TipiProcedimento[1].value: return CONST.TipiProcedimento[1].label;
      case CONST.TipiProcedimento[2].value: return CONST.TipiProcedimento[2].label;
      case CONST.TipiProcedimento[3].value: return CONST.TipiProcedimento[3].label;
      case CONST.TipiProcedimento[4].value: return CONST.TipiProcedimento[4].label;
      case CONST.TipiProcedimento[5].value: return CONST.TipiProcedimento[5].label;
      default: break;
    }
  }

  getStatoFascicolo(statoFascicolo: string): string {
    switch (statoFascicolo) {
      case CONST.StatoFascicoloStatusAttribute[0].enumVal: return CONST.StatoFascicoloStatusAttribute[0].translated;
      case CONST.StatoFascicoloStatusAttribute[1].enumVal: return CONST.StatoFascicoloStatusAttribute[1].translated;
      case CONST.StatoFascicoloStatusAttribute[2].enumVal: return CONST.StatoFascicoloStatusAttribute[2].translated;
      case CONST.StatoFascicoloStatusAttribute[3].enumVal: return CONST.StatoFascicoloStatusAttribute[3].translated;
      case CONST.StatoFascicoloStatusAttribute[4].enumVal: return CONST.StatoFascicoloStatusAttribute[4].translated;
      case CONST.StatoFascicoloStatusAttribute[5].enumVal: return CONST.StatoFascicoloStatusAttribute[5].translated;
      default: break;
    }
  }

  public ricercaFascicoloDaAssegnare(): void {
    this.initTabellaFascicoliDaAssegnare(this.codiceFascicoloDaAssegnareRicerca, 1, this.rowsDaAssegnare);
  }

  public resetFascicoloDaAssegnare(): void {
    this.codiceFascicoloDaAssegnareRicerca = null;
    this.resultsDaAssegnare = [];
    this.initTabellaFascicoliDaAssegnare(null, 1, this.rowsDaAssegnare);
  }

  public pagingChanges(event: any, section: "da_assegnare"|"assegnati"): void
  {
    switch(section)
    {
      case "da_assegnare":
        if(event.page) this.pagingDaAssegnare.page = event.page;
        if(event.limit) this.pagingDaAssegnare.limit = event.limit;
        this.updateDataFascicoliDaAssegnare();
        break;
      case "assegnati":
        if (event.page) this.pagingAssegnati.page = event.page;
        if (event.limit) this.pagingAssegnati.limit = event.limit;
        this.updateDataFascicoliAssegnati();
        break;
    }
  }

  public updateDataFascicoliDaAssegnare(): void 
  {
    let page: number = this.pagingDaAssegnare.page; //(event.first / event.rows) + 1;
    let limit: number = this.pagingDaAssegnare.limit;
    this.initTabellaFascicoliDaAssegnare(this.codiceFascicoloDaAssegnareRicerca, page, limit);
  }

  public assegnaFascicolo(rowData: TabelleAssegnamentoFascicoli): void {
    this.displayAssegnaFascicolo = true;
    this.codiceFascicoloScelto = rowData.codice;
    this.fascicoloScelto = rowData;
    this.isAssegnamento = true;
  }

  public ricercaFascicoloAssegnato(): void {
    this.initTabellaFascicoliAssegnati(this.codiceFascicoloAssegnatoRicerca, 1, this.rowsAssegnati);
  }

  public resetFascicoloAssegnato(): void {
    this.codiceFascicoloAssegnatoRicerca = null;
    this.resultsAssegnato = [];
    this.initTabellaFascicoliAssegnati(null, 1, this.rowsAssegnati);
  }

  public updateDataFascicoliAssegnati(): void {
    let page: number = this.pagingAssegnati.page;//(event.first / event.rows) + 1;
    let limit: number = this.pagingAssegnati.limit;
    this.initTabellaFascicoliAssegnati(this.codiceFascicoloAssegnatoRicerca, page, limit);
  }

  public riassegnaFascicolo(rowData: TabelleAssegnamentoFascicoli): void {
    this.displayAssegnaFascicolo = true;
    this.codiceFascicoloScelto = rowData.codice;
    this.fascicoloScelto = rowData;
    this.isAssegnamento = false;
  }

  public salva(): void { }

  public annulla(): void { }

  public mostraStoricoAssegnazioni(rowData: TabelleAssegnamentoFascicoli): void {
    this.displayStoricoAssegnazioni = true;
    this.codiceFascicoloScelto = rowData.codice;
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloService.getStoricoAssegnamento(rowData.id).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.storicoAssegnazioni = response.payload;
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

  public assegnaRiassegnaFascicolo(): void {
    if (this.isAssegnamento) {
      this.assegna();
    }
    else {
      this.riassegna();
    }
  }

  private assegna(): void {
    if (this.form.valid) {
      let assegnamentoFascicolo: AssegnamentoFascicolo = new AssegnamentoFascicolo();
      assegnamentoFascicolo.idFascicolo = this.fascicoloScelto.id;
      assegnamentoFascicolo.usernameFunzionario = this.form.get("funzionarioAssegnato").value;
      assegnamentoFascicolo.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioAssegnato").value);
      assegnamentoFascicolo.numAssegnazioni = this.fascicoloScelto.riassegnazioni;
      assegnamentoFascicolo.dataOperazione = new Date();
      this.displayAssegnaFascicolo = false;
      this.loadingService.emitLoading(true);
      this.assegnaFascicoloService.assegnaFascicolo(assegnamentoFascicolo).subscribe(
        response => {
          if (response.codiceEsito === "OK") {
            /* this.initTabellaFascicoliDaAssegnare(null, 1, 5);
            this.initTabellaFascicoliAssegnati(null, 1, 5); */
            this.resetFascicoloDaAssegnare();
            this.resetFascicoloAssegnato();
            this.alertData = {
              display: true,
              title: "Successo",
              content: "Operazione terminata con successo",
              typ: "success",
              extraData: null,
              isConfirm: false,
            };
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
  }

  private getDenominazioneFunzionario(usernameFunzionario: string): string {
    let denominazioneFunzionario: string = "";
    let selectItem: SelectItem = this.listaFunzionari.find(elem => elem.value === usernameFunzionario);
    denominazioneFunzionario = selectItem.label;
    return denominazioneFunzionario;
  }

  private riassegna(): void {
    if (this.form.valid) {
      let assegnamentoFascicolo: AssegnamentoFascicolo = new AssegnamentoFascicolo();
      assegnamentoFascicolo.idFascicolo = this.fascicoloScelto.id;
      assegnamentoFascicolo.usernameFunzionario = this.form.get("funzionarioAssegnato").value;
      assegnamentoFascicolo.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioAssegnato").value);
      assegnamentoFascicolo.numAssegnazioni = this.fascicoloScelto.riassegnazioni;
      assegnamentoFascicolo.dataOperazione = new Date();
      this.displayAssegnaFascicolo = false;
      if (this.controlloRiassegnamentoStessoFunzionario()) {
        this.alertData = {
          display: true,
          title: "Attenzione",
          content: "Il fascicolo è già assegnato a questo funzionario",
          typ: "info",
          extraData: null,
          isConfirm: false,
        };
      }
      else {
        this.loadingService.emitLoading(true);
        this.assegnaFascicoloService.riassegnaFascicolo(assegnamentoFascicolo).subscribe(
          response => {
            if (response.codiceEsito === "OK") {
              /* this.initTabellaFascicoliDaAssegnare(null, 1, 5);
              this.initTabellaFascicoliAssegnati(null, 1, 5); */
              this.resetFascicoloDaAssegnare();
              this.resetFascicoloAssegnato();
              this.alertData = {
                display: true,
                title: "Successo",
                content: "Operazione terminata con successo",
                typ: "success",
                extraData: null,
                isConfirm: false,
              };
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
    }
  }

  private controlloRiassegnamentoStessoFunzionario(): boolean {
    let controllo: boolean = false;
    let elemento: TabelleAssegnamentoFascicoli = this.fascicoliAssegnati.list.find(elem => elem.id === this.fascicoloScelto.id);
    if (elemento.usernameFunzionario === this.form.get("funzionarioAssegnato").value) {
      controllo = true;
    }
    return controllo;
  }

  getColor(codeStatoFascicolo: string) {
    let color = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, StatoFascicolo[codeStatoFascicolo], 'color', 'enumVal');
    return color;
  }

  public autocompleteCodiceDaAssegnare(event: any): void {
    this.assegnaFascicoloService.autocompleteCodice(event.query, false).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.resultsDaAssegnare = response.payload;
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

  public autocompleteCodiceAssegnato(event: any): void {
    this.assegnaFascicoloService.autocompleteCodice(event.query, true).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.resultsAssegnato = response.payload;
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

  public disassegnaFascicolo(rowData: TabelleAssegnamentoFascicoli): void {
    this.alertData = {
      display: true,
      title: "Attenzione",
      content: "Disassegnare il fascicolo selezionato?",
      typ: "info",
      extraData: { rowData: rowData, operazione: "confermaDisassegnamento" },
      isConfirm: true,
    };
  }

  private confermaDisassegnamento(rowData: TabelleAssegnamentoFascicoli): void {
    let assegnamentoFascicolo: AssegnamentoFascicolo = new AssegnamentoFascicolo();
    assegnamentoFascicolo.idFascicolo = rowData.id;
    assegnamentoFascicolo.usernameFunzionario = rowData.usernameFunzionario
    assegnamentoFascicolo.denominazioneFunzionario = rowData.denominazioneFunzionario;
    assegnamentoFascicolo.numAssegnazioni = rowData.riassegnazioni;
    assegnamentoFascicolo.dataOperazione = rowData.ultimaAssegnazione;
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloService.disassegnaFascicolo(assegnamentoFascicolo).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          /* this.initTabellaFascicoliDaAssegnare(null, 1, 5);
          this.initTabellaFascicoliAssegnati(null, 1, 5); */
          this.resetFascicoloDaAssegnare();
          this.resetFascicoloAssegnato();
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Operazione terminata con successo",
            typ: "success",
            extraData: null,
            isConfirm: false,
          };
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

  get daAssegnareList() { return this.fascicoliDaAssegnare ? this.fascicoliDaAssegnare.list : []; }
  get daAssegnareCount() { return this.fascicoliDaAssegnare ? this.fascicoliDaAssegnare.count : 0; }

  get assegnatiList() { return this.fascicoliAssegnati ? this.fascicoliAssegnati.list : []; }
  get assegnatiCount() { return this.fascicoliAssegnati ? this.fascicoliAssegnati.count : 0; }
}
