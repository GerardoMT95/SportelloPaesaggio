import { LoadingService } from './../../../app/services/loading.service';
import { ConfigurazioneAssegnamento } from './../../models/assegna-fascicolo-models';
import { AssegnaFascicoloConfigService } from '../../service/assegna-fascicolo-config/assegna-fascicolo-config.service';
import { SelectItem } from 'primeng/components/common/selectitem';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ValoriAssegnamento } from 'src/shared/models/assegna-fascicolo-models';

@Component({
  selector: 'app-assegna-fascicolo-config',
  templateUrl: './assegna-fascicolo-config.component.html',
  styleUrls: ['./assegna-fascicolo-config.component.css']
})
export class AssegnaFascicoloConfigComponent implements OnInit {

  public form: FormGroup;
  /* oggetto utilizzato per l'alert */
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };
  public listaTipoSelezione: SelectItem[] = [
    {
      label: "Localizzazione",
      value: "LOCALIZZAZIONE"
    },
    {
      label: "Tipologia Procedimento",
      value: "TIPO_PROCEDIMENTO"
    }
  ];
  public listaComuneLocalizzazione: SelectItem[] = [];
  public listaFunzionarioLocalizzazione: SelectItem[] = [];
  /* public listaRupLocalizzazione: SelectItem[] = []; */
  public listaComuneLocalizzazioneRicerca: SelectItem[] = [];
  public listaFunzionarioLocalizzazioneRicerca: SelectItem[] = [];
  /* public listaRupLocalizzazioneRicerca: SelectItem[] = []; */
  public colonneTabellaLocalizzazione: any[] = [
    { header: "COMUNE" },
    { header: "FUNZIONARIO" },
  ];
  public tabellaLocalizzazione: Array<ValoriAssegnamento>;
  public listaTipologiaProcedimento: SelectItem[] = [];
  public listaFunzionarioProcedimento: SelectItem[] = [];
  /* public listaRupProcedimento: SelectItem[] = []; */
  public listaTipologiaProcedimentoRicerca: SelectItem[] = [];
  public listaFunzionarioProcedimentoRicerca: SelectItem[] = [];
  /* public listaRupProcedimentoRicerca: SelectItem[] = []; */
  public colonneTabellaProcedimento: Array<any> = [
    { header: "TIPOLOGIA PROCEDIMENTO" },
    { header: "FUNZIONARIO" },
  ];
  public tabellaProcedimento: Array<ValoriAssegnamento>;
  public configurazioneAssegnamento: ConfigurazioneAssegnamento;

  constructor(private formBuilder: FormBuilder,
    private assegnaFascicoloService: AssegnaFascicoloConfigService,
    private loadingService: LoadingService) { }

  ngOnInit() {
    this.buildForm(null); /* solo preventivo altrimenti errori in console, quello vero si trova in getConfigurazioneForOrganizzazione */
    this.getConfigurazioneForOrganizzazione();
    this.getUtentiForOrganizzazione();
  }

  public getConfigurazioneForOrganizzazione(): void {
    this.assegnaFascicoloService.getConfigurazioneForOrganizzazione().subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.configurazioneAssegnamento = response.payload;
          this.buildForm(response.payload.criterioAssegnamento);
          this.riempiTuttiListaComuneLocalizzazione(response.payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "LOCALIZZAZIONE"));
          this.riempiTuttiListaTipologiaProcedimento(response.payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "TIPO_PROCEDIMENTO"));
          this.riempiTabellaLocalizzazione(response.payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "LOCALIZZAZIONE" && elem.usernameFunzionario));
          this.riempiTabellaProcedimento(response.payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "TIPO_PROCEDIMENTO" && elem.usernameFunzionario));
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

  private riempiTabellaProcedimento(lista: ValoriAssegnamento[]): void {
    this.tabellaProcedimento = lista;
    sessionStorage.setItem("tabellaProcedimento", JSON.stringify(this.tabellaProcedimento));
  }

  private riempiTabellaLocalizzazione(lista: ValoriAssegnamento[]): void {
    this.tabellaLocalizzazione = lista;
    sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(this.tabellaLocalizzazione));
  }

  private riempiTuttiListaTipologiaProcedimento(tipi: ValoriAssegnamento[]): void {
    tipi.forEach(elem => {
      let selectItem: SelectItem = {
        label: elem.denominazioneComuneProcedimento,
        value: elem.idComuneTipoProcedimento,
      };
      this.listaTipologiaProcedimento.push(selectItem);
      this.listaTipologiaProcedimentoRicerca.push(selectItem);
    });
  }

  private riempiTuttiListaComuneLocalizzazione(comuni: ValoriAssegnamento[]): void {
    comuni.forEach(elem => {
      let selectItem: SelectItem = {
        label: elem.denominazioneComuneProcedimento,
        value: elem.idComuneTipoProcedimento,
      };
      this.listaComuneLocalizzazione.push(selectItem);
      this.listaComuneLocalizzazioneRicerca.push(selectItem);
    });
  }

  public getUtentiForOrganizzazione(): void {
    this.assegnaFascicoloService.getUtentiForOrganizzazione().subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          response.payload.forEach(elem => {
            let selectItem: SelectItem = {
              label: elem.label,
              value: elem.codice,
            };
            this.listaFunzionarioLocalizzazione.push(selectItem);
            this.listaFunzionarioLocalizzazioneRicerca.push(selectItem);
            this.listaFunzionarioProcedimento.push(selectItem);
            this.listaFunzionarioProcedimentoRicerca.push(selectItem);
          });
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

  public callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'confermaEliminazioneLocalizzazione':
            this.confermaEliminazioneLocalizzazione(event.extraData.rowData, event.extraData.index);
            break;
          case 'confermaEliminazioneProcedimento':
            this.confermaEliminazioneProcedimento(event.extraData.rowData, event.extraData.index);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  public buildForm(criterioAssegnamento: string): void {
    this.form = this.formBuilder.group({
      tipoAssegnazione: [this.daCriterioAssegnamentoATipoAssegnazione(criterioAssegnamento)], /* automatica o manuale */
      tipoSelezione: [this.daCriterioAssegnamentoATipoSelezione(criterioAssegnamento)], /* localizzazione o tipo procedimento */
      /* campi per aggiungere funzionario/rup in caso di localizzazione */
      comuneLocalizzazione: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      funzionarioLocalizzazione: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      /* rupLocalizzazione: [null], */
      /* campi per cercare funzionario/rup in caso di localizzazione */
      comuneLocalizzazioneRicerca: [null],
      funzionarioLocalizzazioneRicerca: [null],
      /* rupLocalizzazioneRicerca: [null], */
      /* campi per aggiungere funzionario/rup in caso di tipologia procedimento */
      tipologiaProcedimento: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      funzionarioProcedimento: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      /* rupProcedimento: [null], */
      /* campi per cercare funzionario/rup in caso di tipologia procedimento */
      tipologiaProcedimentoRicerca: [null],
      funzionarioProcedimentoRicerca: [null],
      /* rupProcedimentoRicerca: [null], */
    });
  }

  private daCriterioAssegnamentoATipoSelezione(criterioAssegnamento: string): string {
    let tipoSelezione: string = null;
    if (criterioAssegnamento) {
      if (criterioAssegnamento === "LOCALIZZAZIONE") {
        tipoSelezione = "LOCALIZZAZIONE";
      }
      else if (criterioAssegnamento === "TIPO_PROCEDIMENTO") {
        tipoSelezione = "TIPO_PROCEDIMENTO";
      }
    }
    return tipoSelezione;
  }

  private daCriterioAssegnamentoATipoAssegnazione(criterioAssegnamento: string): string {
    let tipoAssegnazione: string = null;
    if (criterioAssegnamento) {
      if (criterioAssegnamento === "MANUALE") {
        tipoAssegnazione = "MANUALE";
      }
      else if (criterioAssegnamento === "DISATTIVATA") {
        tipoAssegnazione = "DISATTIVATA";
      }
      else {
        tipoAssegnazione = "AUTOMATICO";
      }
    }
    return tipoAssegnazione;
  }

  public salva(): void {
    this.configurazioneAssegnamento.criterioAssegnamento = this.getCriterioAssegnamento(this.form.get("tipoAssegnazione").value, this.form.get("tipoSelezione").value);
    let tabellaLocalizzazione: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let tabellaProcedimento: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    this.configurazioneAssegnamento.valoriAssegnati = tabellaLocalizzazione.concat(tabellaProcedimento);
    this.loadingService.emitLoading(true);
    this.assegnaFascicoloService.saveConfigurazioneForOrganizzazione(this.configurazioneAssegnamento).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Salvataggio effettuato con successo",
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

  private getCriterioAssegnamento(tipoAssegnazione: string, tipoSelezione: string): string {
    let criterioAssegnamento: string;
    if (tipoAssegnazione === "AUTOMATICO") {
      if (tipoSelezione === "LOCALIZZAZIONE") {
        criterioAssegnamento = "LOCALIZZAZIONE";
      }
      else if (tipoSelezione === "TIPO_PROCEDIMENTO") {
        criterioAssegnamento = "TIPO_PROCEDIMENTO";
      }
    }else{
      criterioAssegnamento=tipoAssegnazione;
    }
    return criterioAssegnamento;
  }

  public annulla(): void {
    /* torno all'ultima configurazione salvata */
    this.getConfigurazioneForOrganizzazione();
  }

  public aggiungiFunzionarioRupLocalizzazione(): void {
    if (this.form.get("comuneLocalizzazione").valid && this.form.get("funzionarioLocalizzazione").valid) {
      if (this.controllaDuplicatoTabellaLocalizzazione(this.form.get("comuneLocalizzazione").value, this.form.get("funzionarioLocalizzazione").value)) {
        this.alertData = {
          display: true,
          title: "Attenzione",
          content: "Elemento già presente",
          typ: "info",
          extraData: null,
          isConfirm: false,
        };
      }
      else {
        let valoriAssegnamento: ValoriAssegnamento = new ValoriAssegnamento();
        valoriAssegnamento.idComuneTipoProcedimento = this.form.get("comuneLocalizzazione").value;
        valoriAssegnamento.denominazioneComuneProcedimento = this.getDenominazioneComuneProcedimento(this.form.get("comuneLocalizzazione").value);
        valoriAssegnamento.usernameFunzionario = this.form.get("funzionarioLocalizzazione").value;
        valoriAssegnamento.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioLocalizzazione").value);
        valoriAssegnamento.tipoAssegnamento = "LOCALIZZAZIONE";
        this.annullaFunzionarioRupLocalizzazione(); /* perchè se aggiungo dopo una ricerca perdo i dati */
        this.inserisciFunzionarioRupLocalizzazione(valoriAssegnamento); /* aggiungo elemento alla tabella oppure sovrascrivo funzionario in caso di comune già presente */
        sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(this.tabellaLocalizzazione));
        this.alertData = {
          display: true,
          title: "Successo",
          content: "Operazione terminata con successo",
          typ: "success",
          extraData: null,
          isConfirm: false,
        };
      }
    }
    else {
      this.alertData = {
        display: true,
        title: "Attenzione",
        content: "E' necessario selezionare un comune e un funzionario",
        typ: "danger",
        extraData: null,
        isConfirm: false,
      };
    }
  }

  private inserisciFunzionarioRupLocalizzazione(valoriAssegnamento: ValoriAssegnamento): void {
    let elemento: ValoriAssegnamento = this.tabellaLocalizzazione.find(elem => elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento);
    if (elemento) {
      this.tabellaLocalizzazione.forEach(elem => {
        if (elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento) {
          elem.usernameFunzionario = valoriAssegnamento.usernameFunzionario;
          elem.denominazioneFunzionario = valoriAssegnamento.denominazioneFunzionario;
        }
      });
    }
    else {
      this.tabellaLocalizzazione.push(valoriAssegnamento);
    }
  }

  private controllaDuplicatoTabellaLocalizzazione(idComuneTipoProcedimento: string, usernameFunzionario: string): boolean {
    let controllo: boolean = false;
    let tabellaLocalizzazione: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let valore: ValoriAssegnamento = tabellaLocalizzazione.find(elem => elem.idComuneTipoProcedimento === idComuneTipoProcedimento
      && elem.usernameFunzionario === usernameFunzionario);
    if (valore) {
      controllo = true;
    }
    return controllo;
  }

  private getDenominazioneFunzionario(usernameFunzionario: string): string {
    let denominazioneFunzionario: string = "";
    let selectItem: SelectItem = this.listaFunzionarioLocalizzazione.find(elem => elem.value === usernameFunzionario);
    denominazioneFunzionario = selectItem.label;
    return denominazioneFunzionario;
  }

  private getDenominazioneComuneProcedimento(idComuneTipoProcedimento: string): string {
    let denominazioneComuneProcedimento: string = "";
    let selectItem: SelectItem = this.listaComuneLocalizzazione.find(elem => elem.value === idComuneTipoProcedimento);
    denominazioneComuneProcedimento = selectItem.label;
    return denominazioneComuneProcedimento;
  }

  public cercaFunzionarioRupLocalizzazione(): void {
    let tabellaLocalizzazione: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    if (this.form.get("comuneLocalizzazioneRicerca").value && !this.form.get("funzionarioLocalizzazioneRicerca").value) {
      this.tabellaLocalizzazione = tabellaLocalizzazione.filter(elem => elem.idComuneTipoProcedimento === this.form.get("comuneLocalizzazioneRicerca").value);
    }
    else if (!this.form.get("comuneLocalizzazioneRicerca").value && this.form.get("funzionarioLocalizzazioneRicerca").value) {
      this.tabellaLocalizzazione = tabellaLocalizzazione.filter(elem => elem.usernameFunzionario === this.form.get("funzionarioLocalizzazioneRicerca").value);
    }
    else if (this.form.get("comuneLocalizzazioneRicerca").value && this.form.get("funzionarioLocalizzazioneRicerca").value) {
      this.tabellaLocalizzazione = tabellaLocalizzazione.filter(elem => elem.idComuneTipoProcedimento === this.form.get("comuneLocalizzazioneRicerca").value
        && elem.usernameFunzionario === this.form.get("funzionarioLocalizzazioneRicerca").value);
    }
  }

  public annullaFunzionarioRupLocalizzazione(): void {
    this.tabellaLocalizzazione = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    this.form.get("comuneLocalizzazioneRicerca").setValue(null);
    this.form.get("funzionarioLocalizzazioneRicerca").setValue(null);
    this.form.updateValueAndValidity();
  }

  public eliminaFunzionarioLocalizzazione(rowData: ValoriAssegnamento, index: number): void {
    this.alertData = {
      display: true,
      title: "Attenzione",
      content: "Eliminare l'elemento selezionato?",
      typ: "info",
      extraData: { rowData: rowData, index: index, operazione: "confermaEliminazioneLocalizzazione" },
      isConfirm: true,
    };
  }

  private confermaEliminazioneLocalizzazione(rowData: ValoriAssegnamento, index: number): void {
    this.tabellaLocalizzazione.splice(index, 1);
    /* elimino l'elemento anche dalla session storage */
    let tabellaLocalizzazione: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let i = tabellaLocalizzazione.findIndex(elem => elem.idComuneTipoProcedimento === rowData.idComuneTipoProcedimento
      && elem.usernameFunzionario === rowData.usernameFunzionario);
    tabellaLocalizzazione.splice(i, 1);
    sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(tabellaLocalizzazione));
  }

  public aggiungiFunzionarioRupProcedimento(): void {
    if (this.form.get("tipologiaProcedimento").valid && this.form.get("funzionarioProcedimento").valid) {
      if (this.controllaDuplicatoTabellaProcedimento(this.form.get("tipologiaProcedimento").value, this.form.get("funzionarioProcedimento").value)) {
        this.alertData = {
          display: true,
          title: "Attenzione",
          content: "Elemento già presente",
          typ: "info",
          extraData: null,
          isConfirm: false,
        };
      }
      else {
        let valoriAssegnamento: ValoriAssegnamento = new ValoriAssegnamento();
        valoriAssegnamento.idComuneTipoProcedimento = this.form.get("tipologiaProcedimento").value;
        valoriAssegnamento.denominazioneComuneProcedimento = this.getDenominazioneTipoProcedimento(this.form.get("tipologiaProcedimento").value);
        valoriAssegnamento.usernameFunzionario = this.form.get("funzionarioProcedimento").value;
        valoriAssegnamento.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioProcedimento").value);
        valoriAssegnamento.tipoAssegnamento = "TIPO_PROCEDIMENTO";
        this.annullaFunzionarioRupProcedimento() /* perchè se aggiungo dopo una ricerca perdo i dati */
        this.inserisciFunzionarioRupProcedimento(valoriAssegnamento); /* aggiungo elemento alla tabella oppure sovrascrivo funzionario in caso di comune già presente */
        sessionStorage.setItem("tabellaProcedimento", JSON.stringify(this.tabellaProcedimento));
        this.alertData = {
          display: true,
          title: "Successo",
          content: "Operazione terminata con successo",
          typ: "success",
          extraData: null,
          isConfirm: false,
        };
      }
    }
    else {
      this.alertData = {
        display: true,
        title: "Attenzione",
        content: "E' necessario selezionare un tipo procedimento e un funzionario",
        typ: "danger",
        extraData: null,
        isConfirm: false,
      };
    }
  }

  private inserisciFunzionarioRupProcedimento(valoriAssegnamento: ValoriAssegnamento): void {
    let elemento: ValoriAssegnamento = this.tabellaProcedimento.find(elem => elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento);
    if (elemento) {
      this.tabellaProcedimento.forEach(elem => {
        if (elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento) {
          elem.usernameFunzionario = valoriAssegnamento.usernameFunzionario;
          elem.denominazioneFunzionario = valoriAssegnamento.denominazioneFunzionario;
        }
      });
    }
    else {
      this.tabellaProcedimento.push(valoriAssegnamento);
    }
  }

  private controllaDuplicatoTabellaProcedimento(idComuneTipoProcedimento: string, usernameFunzionario: string): boolean {
    let controllo: boolean = false;
    let tabellaProcedimento: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    let valore: ValoriAssegnamento = tabellaProcedimento.find(elem => elem.idComuneTipoProcedimento === idComuneTipoProcedimento
      && elem.usernameFunzionario === usernameFunzionario);
    if (valore) {
      controllo = true;
    }
    return controllo;
  }

  private getDenominazioneTipoProcedimento(tipoProcedimento: string): string {
    let denominazioneTipoProcedimento: string = "";
    let selectItem: SelectItem = this.listaTipologiaProcedimento.find(elem => elem.value === tipoProcedimento);
    denominazioneTipoProcedimento = selectItem.label;
    return denominazioneTipoProcedimento;
  }

  public cercaFunzionarioRupProcedimento(): void {
    let tabellaProcedimento: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    if (this.form.get("tipologiaProcedimentoRicerca").value && !this.form.get("funzionarioProcedimentoRicerca").value) {
      this.tabellaProcedimento = tabellaProcedimento.filter(elem => elem.idComuneTipoProcedimento === this.form.get("tipologiaProcedimentoRicerca").value);
    }
    else if (!this.form.get("tipologiaProcedimentoRicerca").value && this.form.get("funzionarioProcedimentoRicerca").value) {
      this.tabellaProcedimento = tabellaProcedimento.filter(elem => elem.usernameFunzionario === this.form.get("funzionarioProcedimentoRicerca").value);
    }
    else if (this.form.get("tipologiaProcedimentoRicerca").value && this.form.get("funzionarioProcedimentoRicerca").value) {
      this.tabellaProcedimento = tabellaProcedimento.filter(elem => elem.idComuneTipoProcedimento === this.form.get("tipologiaProcedimentoRicerca").value
        && elem.usernameFunzionario === this.form.get("funzionarioProcedimentoRicerca").value);
    }
  }

  public annullaFunzionarioRupProcedimento(): void {
    this.tabellaProcedimento = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    this.form.get("tipologiaProcedimentoRicerca").setValue(null);
    this.form.get("funzionarioProcedimentoRicerca").setValue(null);
    this.form.updateValueAndValidity();
  }

  public eliminaFunzionarioProcedimento(rowData: ValoriAssegnamento, index: number): void {
    this.alertData = {
      display: true,
      title: "Attenzione",
      content: "Eliminare l'elemento selezionato?",
      typ: "info",
      extraData: { rowData: rowData, index: index, operazione: "confermaEliminazioneProcedimento" },
      isConfirm: true,
    };
  }

  private confermaEliminazioneProcedimento(rowData: ValoriAssegnamento, index: number): void {
    this.tabellaProcedimento.splice(index, 1);
    /* elimino l'elemento anche dalla session storage */
    let tabellaProcedimento: Array<ValoriAssegnamento> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    let i = tabellaProcedimento.findIndex(elem => elem.idComuneTipoProcedimento === rowData.idComuneTipoProcedimento
      && elem.usernameFunzionario === rowData.usernameFunzionario);
    tabellaProcedimento.splice(i, 1);
    sessionStorage.setItem("tabellaProcedimento", JSON.stringify(tabellaProcedimento));
  }

}
