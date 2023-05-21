import { updateAllFormValidity } from 'src/app/core/functions/generic.utils';
import { requiredDependsOn } from 'src/app/shared/validators/customValidator';
import { UserService } from './../../../../../shared/services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SelectItem } from 'primeng/components/common/selectitem';
import { combineLatest } from 'rxjs';
import { paths } from 'src/app/app-routing.module';
import { PlainFunzRup } from 'src/app/shared/components/model/logged-user';
import { GroupType, ValoriAssegnamentoDTO } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { CustomDialogService } from './../../../../../core/services/dialog.service';
import { DialogButtons, DialogType } from './../../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../../shared/constants';
import { ConfigurazioneAssegnamento, TipoAssegnamento } from './../../../../../shared/models/models';

@Component({
  selector: 'app-assegna-fascicolo-config-page',
  templateUrl: './assegna-fascicolo-config-page.component.html',
  styleUrls: ['./assegna-fascicolo-config-page.component.css']
})
export class AssegnaFascicoloConfigPageComponent implements OnInit
{
  public form: FormGroup;
  public listaTipoSelezione: SelectItem[] = 
  [
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
  public listaRupLocalizzazione: SelectItem[] = [];
  public listaComuneLocalizzazioneRicerca: SelectItem[] = [];
  public listaFunzionarioLocalizzazioneRicerca: SelectItem[] = [];
  public listaRupLocalizzazioneRicerca: SelectItem[] = [];
  public colonneTabellaLocalizzazione: any[] = 
  [
    { header: "COMUNE" },
    { header: "FUNZIONARIO" },
  ];
  public tabellaLocalizzazione: Array<ValoriAssegnamentoDTO>;
  public listaTipologiaProcedimento: SelectItem[] = [];
  public listaFunzionarioProcedimento: SelectItem[] = [];
  public listaRupProcedimento: SelectItem[] = [];
  public listaTipologiaProcedimentoRicerca: SelectItem[] = [];
  public listaFunzionarioProcedimentoRicerca: SelectItem[] = [];
  public listaRupProcedimentoRicerca: SelectItem[] = [];

  public validazione: boolean = false;
  public submitted: boolean = false;
  public disable: boolean = false;

  public colonneTabellaProcedimento: Array<any> = 
  [
    { header: "TIPOLOGIA PROCEDIMENTO" },
    { header: "FUNZIONARIO" },
  ];
  public tabellaProcedimento: Array<ValoriAssegnamentoDTO>;
  public configurazioneAssegnamento: ConfigurazioneAssegnamento;

  private typeProcedimento =CONST.mappingTipiProcedimento;
  

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private service: FascicoloService,
              private dialog: CustomDialogService,
              private router: Router,
              private loadingService: LoadingService) 
  { 
    this.loadingService.emitLoading(true);
  }

  ngOnInit()
  {
    this.buildForm(null); /* solo preventivo altrimenti errori in console, quello vero si trova in getConfigurazioneForOrganizzazione */
    this.getInfo();
    if(this.isED) 
    {
      this.colonneTabellaProcedimento.push({ header: "RUP" });
      this.colonneTabellaLocalizzazione.push({ header: "RUP" });
    }
  }

  private getInfo(): void
  {
    this.loadingService.emitLoading(true);
    let getConf$ = this.service.callGetConfigurazioneAssegnamento();
    let getFunz$ = this.service.callGetFunzionario();
    combineLatest([getConf$, getFunz$]).subscribe(([resConf, resFunz]) =>
    {
      if(resConf && resFunz)
      {
        if(resFunz.payload && resFunz.payload.length > 0)
        {
          this.handleConfForOrg(resConf.payload);
          this.handlePlainFunzAndRup(resFunz.payload);
        }
        else
        {
          this.loadingService.emitLoading(false);
          this.errorAndRedirect();
        }
      }
      this.loadingService.emitLoading(false);
    }); 
  }

  private errorAndRedirect(): void
  {
    let message = "Nessun funzionario trovato, verrai reindirizzato alla pagina principale";
    let title = "Attenzione";
    let fn = (buttonId) => { if (buttonId == 1) this.router.navigate([paths.listManagement()]);}
    this.dialog.showDialog(true, message, title, DialogButtons.CHIUDI, fn.bind(this), DialogType.WARNING);
  }

  public getConfigurazioneForOrganizzazione(): void
  {
    this.loadingService.emitLoading(true);
    this.service.callGetConfigurazioneAssegnamento().subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
        this.handleConfForOrg(response.payload);
      this.loadingService.emitLoading(false);
    });
  }

  private handleConfForOrg(payload: ConfigurazioneAssegnamento): void
  {
    this.configurazioneAssegnamento = payload;
    this.buildForm(payload.criterioAssegnamento);
    this.riempiTuttiListaComuneLocalizzazione(payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "LOCALIZZAZIONE"));
    this.riempiTuttiListaTipologiaProcedimento(payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "TIPO_PROCEDIMENTO"));
    this.riempiTabellaLocalizzazione(payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "LOCALIZZAZIONE" && elem.usernameFunzionario));
    this.riempiTabellaProcedimento(payload.valoriAssegnati.filter(elem => elem.tipoAssegnamento === "TIPO_PROCEDIMENTO" && elem.usernameFunzionario));
  }

  private riempiTabellaProcedimento(lista: ValoriAssegnamentoDTO[]): void
  {
    this.tabellaProcedimento = lista;
    sessionStorage.setItem("tabellaProcedimento", JSON.stringify(this.tabellaProcedimento));
  }

  private riempiTabellaLocalizzazione(lista: ValoriAssegnamentoDTO[]): void
  {
    this.tabellaLocalizzazione = lista;
    sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(this.tabellaLocalizzazione));
  }

  private riempiTuttiListaTipologiaProcedimento(tipi: ValoriAssegnamentoDTO[]): void
  {
    let _this = this;
    tipi.forEach(elem =>
    {
      if(parseInt(elem.idComuneTipoProcedimento) <= 4)
      {
        let selectItem: SelectItem = {
          label: _this.translateIdInType(elem.denominazioneComuneProcedimento),
          value: elem.idComuneTipoProcedimento,
        };
        _this.listaTipologiaProcedimento.push(selectItem);
        _this.listaTipologiaProcedimentoRicerca.push(selectItem);
      }
    });
  }

  public translateIdInType(stringid: string): string
  {
    let id = parseInt(stringid);
    let label: any = stringid; 
    if(id)
    {
      label = this.typeProcedimento[id - 1];
      console.log("id -> ", label);
      label = label ? label.description : '';
    }
    return label;
  }

  private riempiTuttiListaComuneLocalizzazione(comuni: ValoriAssegnamentoDTO[]): void
  {
    comuni.forEach(elem =>
    {
      let selectItem: SelectItem = {
        label: elem.denominazioneComuneProcedimento,
        value: elem.idComuneTipoProcedimento,
      };
      this.listaComuneLocalizzazione.push(selectItem);
      this.listaComuneLocalizzazioneRicerca.push(selectItem);
    });
  }

  public getUtentiForOrganizzazione(): void
  {
    this.loadingService.emitLoading(true);
    this.service.callGetFunzionario().subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
        this.handlePlainFunzAndRup(response.payload);
      this.loadingService.emitLoading(false);
    });
  }

  private handlePlainFunzAndRup(payload: Array<PlainFunzRup>): void
  {
    payload.forEach(elem =>
    {
      let selectItem: SelectItem = { label: elem.description , value: elem.value };
      if(!elem.rup)
      {
        this.listaFunzionarioLocalizzazione.push(selectItem);
        this.listaFunzionarioLocalizzazioneRicerca.push(selectItem);
        this.listaFunzionarioProcedimento.push(selectItem);
        this.listaFunzionarioProcedimentoRicerca.push(selectItem);
      }
      else
      {
        this.listaRupLocalizzazione.push(selectItem);
        this.listaRupLocalizzazioneRicerca.push(selectItem);
        this.listaRupProcedimento.push(selectItem);
        this.listaRupProcedimentoRicerca.push(selectItem);
      }
    });
  }

  public buildForm(criterioAssegnamento: string): void
  {
    this.form = this.formBuilder.group({
      tipoAssegnazione: [this.daCriterioAssegnamentoATipoAssegnazione(criterioAssegnamento), Validators.required], /* automatica o manuale */
      tipoSelezione: [this.daCriterioAssegnamentoATipoSelezione(criterioAssegnamento), Validators.required], /* localizzazione o tipo procedimento */
      /* campi per aggiungere funzionario/rup in caso di localizzazione */
      comuneLocalizzazione: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      funzionarioLocalizzazione: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      rupLocalizzazione: [null],
      /* campi per cercare funzionario/rup in caso di localizzazione */
      comuneLocalizzazioneRicerca: [null],
      funzionarioLocalizzazioneRicerca: [null],
      rupLocalizzazioneRicerca: [null],
      /* campi per aggiungere funzionario/rup in caso di tipologia procedimento */
      tipologiaProcedimento: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      funzionarioProcedimento: [null, Validators.required], /* required solo per l'aggiunta, non per il salva */
      rupProcedimento: [null],
      /* campi per cercare funzionario/rup in caso di tipologia procedimento */
      tipologiaProcedimentoRicerca: [null],
      funzionarioProcedimentoRicerca: [null],
      rupProcedimentoRicerca: [null],
    });

    //setto la validazione per il form di aggiunta
    this.form.get("comuneLocalizzazione").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "LOCALIZZAZIONE")]);
    this.form.get("funzionarioLocalizzazione").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "LOCALIZZAZIONE")]);
    this.form.get("tipologiaProcedimento").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "TIPO_PROCEDIMENTO")]);
    this.form.get("funzionarioProcedimento").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "TIPO_PROCEDIMENTO")]);
    if(this.isED)
    {
      this.form.get("rupLocalizzazione").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "LOCALIZZAZIONE")]);
      this.form.get("rupProcedimento").setValidators([requiredDependsOn(this.form.get("tipoSelezione"), "TIPO_PROCEDIMENTO")]);
    }
  }

  private daCriterioAssegnamentoATipoSelezione(criterioAssegnamento: string): string
  {
    let tipoSelezione: string = null;
    if (criterioAssegnamento)
    {
      if (criterioAssegnamento === "LOCALIZZAZIONE")
      {
        tipoSelezione = "LOCALIZZAZIONE";
      }
      else if (criterioAssegnamento === "TIPO_PROCEDIMENTO")
      {
        tipoSelezione = "TIPO_PROCEDIMENTO";
      }
    }
    return tipoSelezione;
  }

  private daCriterioAssegnamentoATipoAssegnazione(criterioAssegnamento: string): string
  {
    let tipoAssegnazione: string = null;
    if (criterioAssegnamento)
    {
      if (criterioAssegnamento === "MANUALE")
      {
        tipoAssegnazione = "MANUALE";
      }
      else
      {
        tipoAssegnazione = "AUTOMATICO";
      }
    }
    return tipoAssegnazione;
  }

  public salva(): void
  {
    this.submitted = true;
    if(!this.invalidAssignment)
    {
      this.submitted = false;
      this.configurazioneAssegnamento.criterioAssegnamento = this.getCriterioAssegnamento(this.form.get("tipoAssegnazione").value, this.form.get("tipoSelezione").value);
      let tabellaLocalizzazione: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
      let tabellaProcedimento: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
      this.configurazioneAssegnamento.valoriAssegnati = tabellaLocalizzazione.concat(tabellaProcedimento);
      this.loadingService.emitLoading(true);
      this.service.callSaveConfigurazioneAssegnamento(this.configurazioneAssegnamento).subscribe(response =>
      {
        if (response.codiceEsito === CONST.OK)
        {
          let message = "Salvataggio effettuato con successo";
          let title = "Successo";
          this.dialog.showDialog(true, message, title, DialogButtons.CHIUDI, null, DialogType.SUCCESS);
          this.loadingService.emitLoading(false);
        }
      }); 
    }
  
  }

  private getCriterioAssegnamento(tipoAssegnazione: string, tipoSelezione: string): TipoAssegnamento
  {
    let criterioAssegnamento: TipoAssegnamento;
    if (tipoAssegnazione === "MANUALE")
    {
      criterioAssegnamento = "MANUALE";
    }
    else if (tipoAssegnazione === "AUTOMATICO")
    {
      if (tipoSelezione === "LOCALIZZAZIONE")
      {
        criterioAssegnamento = "LOCALIZZAZIONE";
      }
      else if (tipoSelezione === "TIPO_PROCEDIMENTO")
      {
        criterioAssegnamento = "TIPO_PROCEDIMENTO";
      }
    }
    return criterioAssegnamento;
  }

  public annulla(): void
  {
    /* torno all'ultima configurazione salvata */
    this.validazione = false;
    this.submitted = false;
    this.getConfigurazioneForOrganizzazione();
  }

  public aggiungiFunzionarioRupLocalizzazione(): void
  {
    /* if (this.form.get("comuneLocalizzazione").valid && this.form.get("funzionarioLocalizzazione").valid) */
    updateAllFormValidity(this.form);
    this.validazione = true;
    if(this.form.valid)
    {
      this.validazione = false;
      if (this.controllaDuplicatoTabellaLocalizzazione(this.form.get("comuneLocalizzazione").value, this.form.get("funzionarioLocalizzazione").value))
      {
        let message = "Elemento già presente";
        let title = "Attenzione";
        this.dialog.showDialog(true, message, title, DialogButtons.CHIUDI, null, DialogType.WARNING);
      }
      else
      {
        let valoriAssegnamento: ValoriAssegnamentoDTO = new ValoriAssegnamentoDTO();
        valoriAssegnamento.idComuneTipoProcedimento = this.form.get("comuneLocalizzazione").value;
        valoriAssegnamento.denominazioneComuneProcedimento = this.getDenominazioneComuneProcedimento(this.form.get("comuneLocalizzazione").value);
        valoriAssegnamento.usernameFunzionario = this.form.get("funzionarioLocalizzazione").value;
        valoriAssegnamento.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioLocalizzazione").value);
        if(this.isED)
        {
          valoriAssegnamento.usernameRup = this.form.get("rupLocalizzazione").value;
          valoriAssegnamento.denominazioneRup = this.getDenominazioneRup(this.form.get("rupLocalizzazione").value);
        }
        valoriAssegnamento.tipoAssegnamento = "LOCALIZZAZIONE";
        this.annullaFunzionarioRupLocalizzazione(); /* perchè se aggiungo dopo una ricerca perdo i dati */
        this.inserisciFunzionarioRupLocalizzazione(valoriAssegnamento); /* aggiungo elemento alla tabella oppure sovrascrivo funzionario in caso di comune già presente */
        sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(this.tabellaLocalizzazione));
      }
    }
  }

  private inserisciFunzionarioRupLocalizzazione(valoriAssegnamento: ValoriAssegnamentoDTO): void
  {
    let elemento: ValoriAssegnamentoDTO = this.tabellaLocalizzazione.find(elem => elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento);
    if (elemento)
    {
      this.tabellaLocalizzazione.forEach(elem =>
      {
        if (elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento)
        {
          elem.usernameFunzionario = valoriAssegnamento.usernameFunzionario;
          elem.denominazioneFunzionario = valoriAssegnamento.denominazioneFunzionario;
          elem.usernameRup = valoriAssegnamento.usernameRup;
          elem.denominazioneRup = valoriAssegnamento.denominazioneRup;
        }
      });
    }
    else
    {
      this.tabellaLocalizzazione.push(valoriAssegnamento);
    }
  }

  private controllaDuplicatoTabellaLocalizzazione(idComuneTipoProcedimento: string, usernameFunzionario: string): boolean
  {
    let controllo: boolean = false;
    let tabellaLocalizzazione: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let valore: ValoriAssegnamentoDTO = tabellaLocalizzazione.find(elem => elem.idComuneTipoProcedimento === idComuneTipoProcedimento
      && elem.usernameFunzionario === usernameFunzionario);
    if (valore)
    {
      controllo = true;
    }
    return controllo;
  }

  private getDenominazioneFunzionario(usernameFunzionario: string): string
  {
    let denominazioneFunzionario: string = "";
    let selectItem: SelectItem = this.listaFunzionarioLocalizzazione.find(elem => elem.value === usernameFunzionario);
    denominazioneFunzionario = selectItem.label;
    return denominazioneFunzionario;
  }

  private getDenominazioneRup(usernameRup: string): string
  {
    let denominazioneRup: string = "";
    let selectItem: SelectItem = this.listaRupLocalizzazione.find(elem => elem.value === usernameRup);
    denominazioneRup = selectItem.label;
    return denominazioneRup;
  }

  private getDenominazioneComuneProcedimento(idComuneTipoProcedimento: string): string
  {
    let denominazioneComuneProcedimento: string = "";
    let selectItem: SelectItem = this.listaComuneLocalizzazione.find(elem => elem.value === idComuneTipoProcedimento);
    denominazioneComuneProcedimento = selectItem.label;
    return denominazioneComuneProcedimento;
  }

  public cercaFunzionarioRupLocalizzazione(): void
  {
    let tabellaLocalizzazione: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let fn = (p: ValoriAssegnamentoDTO) =>
    {
      let match = true;
      let searchObj = this.form.getRawValue();
      console.log("Ricerca -> ", searchObj);
      if (searchObj.comuneLocalizzazioneRicerca && searchObj.comuneLocalizzazioneRicerca != "")
        match = p.idComuneTipoProcedimento == searchObj.comuneLocalizzazioneRicerca
      if (searchObj.funzionarioLocalizzazioneRicerca && searchObj.funzionarioLocalizzazioneRicerca != "")
        match = p.usernameFunzionario === searchObj.funzionarioLocalizzazioneRicerca;
      if (searchObj.rupLocalizzazioneRicerca && searchObj.rupLocalizzazioneRicerca != "")
        match = p.usernameRup === searchObj.rupLocalizzazioneRicerca;
      return match;
    }
    console.log("Tipo loc -> ", this.tabellaLocalizzazione);
    this.tabellaLocalizzazione = tabellaLocalizzazione.filter(fn);
  }

  public annullaFunzionarioRupLocalizzazione(): void
  {
    this.tabellaLocalizzazione = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    this.form.get("comuneLocalizzazioneRicerca").setValue(null);
    this.form.get("funzionarioLocalizzazioneRicerca").setValue(null);
    if(this.isED)
      this.form.get("rupLocalizzazioneRicerca").setValue(null);
    this.form.updateValueAndValidity();
  }

  public eliminaFunzionarioLocalizzazione(rowData: ValoriAssegnamentoDTO, index: number): void
  {
    let message = "Eliminare l'elemento selezionato?";
    let title = "Attenzione";
    let fn = (buttonId) => { if(buttonId == 1) this.confermaEliminazioneLocalizzazione(rowData, index); }
    this.dialog.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, fn, DialogType.WARNING);
  }

  private confermaEliminazioneLocalizzazione(rowData: ValoriAssegnamentoDTO, index: number): void
  {
    this.tabellaLocalizzazione.splice(index, 1);
    /* elimino l'elemento anche dalla session storage */
    let tabellaLocalizzazione: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaLocalizzazione"));
    let i = tabellaLocalizzazione.findIndex(elem => elem.idComuneTipoProcedimento === rowData.idComuneTipoProcedimento
      && elem.usernameFunzionario === rowData.usernameFunzionario);
    tabellaLocalizzazione.splice(i, 1);
    sessionStorage.setItem("tabellaLocalizzazione", JSON.stringify(tabellaLocalizzazione));
  }

  public aggiungiFunzionarioRupProcedimento(): void
  {
    updateAllFormValidity(this.form);
    this.validazione = true;
    if(this.form.valid)
    {
      this.validazione = false;
      if (this.controllaDuplicatoTabellaProcedimento(this.form.get("tipologiaProcedimento").value, this.form.get("funzionarioProcedimento").value))
      {
        let message = "Elemento già presente";
        let title = "Attenzione";
        this.dialog.showDialog(true, message, title, DialogButtons.CHIUDI, null, DialogType.WARNING);
      }
      else
      {
        let valoriAssegnamento: ValoriAssegnamentoDTO = new ValoriAssegnamentoDTO();
        valoriAssegnamento.idComuneTipoProcedimento = this.form.get("tipologiaProcedimento").value;
        valoriAssegnamento.denominazioneComuneProcedimento = this.getDenominazioneTipoProcedimento(this.form.get("tipologiaProcedimento").value);
        valoriAssegnamento.usernameFunzionario = this.form.get("funzionarioProcedimento").value;
        valoriAssegnamento.denominazioneFunzionario = this.getDenominazioneFunzionario(this.form.get("funzionarioProcedimento").value);
        if(this.isED)
        {
          valoriAssegnamento.usernameRup = this.form.get("rupProcedimento").value;
          valoriAssegnamento.denominazioneRup = this.getDenominazioneRup(this.form.get("rupProcedimento").value);
        }
        valoriAssegnamento.tipoAssegnamento = "TIPO_PROCEDIMENTO";
        this.annullaFunzionarioRupProcedimento() /* perchè se aggiungo dopo una ricerca perdo i dati */
        this.inserisciFunzionarioRupProcedimento(valoriAssegnamento); /* aggiungo elemento alla tabella oppure sovrascrivo funzionario in caso di comune già presente */
        sessionStorage.setItem("tabellaProcedimento", JSON.stringify(this.tabellaProcedimento));
      }
    }
  }

  private inserisciFunzionarioRupProcedimento(valoriAssegnamento: ValoriAssegnamentoDTO): void
  {
    let elemento: ValoriAssegnamentoDTO = this.tabellaProcedimento.find(elem => elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento);
    if (elemento)
    {
      this.tabellaProcedimento.forEach(elem =>
      {
        if (elem.idComuneTipoProcedimento == valoriAssegnamento.idComuneTipoProcedimento)
        {
          elem.usernameFunzionario = valoriAssegnamento.usernameFunzionario;
          elem.denominazioneFunzionario = valoriAssegnamento.denominazioneFunzionario;
          if(this.isED)
          {
            elem.usernameRup = valoriAssegnamento.usernameRup;
            elem.denominazioneRup = valoriAssegnamento.denominazioneRup;
          }
          
        }
      });
    }
    else
    {
      this.tabellaProcedimento.push(valoriAssegnamento);
    }
  }

  private controllaDuplicatoTabellaProcedimento(idComuneTipoProcedimento: string, usernameFunzionario: string): boolean
  {
    let controllo: boolean = false;
    let tabellaProcedimento: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    let valore: ValoriAssegnamentoDTO = tabellaProcedimento.find(elem => elem.idComuneTipoProcedimento === idComuneTipoProcedimento
      && elem.usernameFunzionario === usernameFunzionario);
    if (valore)
      controllo = true;
    return controllo;
  }

  private getDenominazioneTipoProcedimento(tipoProcedimento: string): string
  {
    let denominazioneTipoProcedimento: string = "";
    let selectItem: SelectItem = this.listaTipologiaProcedimento.find(elem => elem.value === tipoProcedimento);
    denominazioneTipoProcedimento = selectItem.label;
    return denominazioneTipoProcedimento;
  }

  public cercaFunzionarioRupProcedimento(): void
  {
    let tabellaProcedimento: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    let fn = (p: ValoriAssegnamentoDTO) =>
    {
      let match = true;
      let searchObj = this.form.getRawValue();
      if (searchObj.funzionarioProcedimentoRicerca && searchObj.funzionarioProcedimentoRicerca != "")
        match = p.usernameFunzionario === searchObj.funzionarioProcedimentoRicerca;
      if (searchObj.tipologiaProcedimentoRicerca && searchObj.tipologiaProcedimentoRicerca != "")
        match = p.idComuneTipoProcedimento === searchObj.tipologiaProcedimentoRicerca
      if (searchObj.rupProcedimentoRicerca && searchObj.rupProcedimentoRicerca != "")
        match = p.usernameRup === searchObj.rupProcedimentoRicerca;
      return match;
    }
    this.tabellaProcedimento = tabellaProcedimento.filter(fn);
  }

  public annullaFunzionarioRupProcedimento(): void
  {
    this.tabellaProcedimento = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    this.form.get("tipologiaProcedimentoRicerca").setValue(null);
    this.form.get("funzionarioProcedimentoRicerca").setValue(null);
    if(this.isED)
      this.form.get("rupProcedimentoRicerca").setValue(null);
    this.form.updateValueAndValidity();
  }

  public eliminaFunzionarioProcedimento(rowData: ValoriAssegnamentoDTO, index: number): void
  {
    let title = "Attenzione";
    let message = "Sei sicuro di voler eliminare l'elemento selezionato?";
    let fn = (buttonId) => { if (buttonId == 1) this.confermaEliminazioneProcedimento(rowData, index); }
    this.dialog.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, fn, DialogType.WARNING);
  }

  private confermaEliminazioneProcedimento(rowData: ValoriAssegnamentoDTO, index: number): void
  {
    this.tabellaProcedimento.splice(index, 1);
    /* elimino l'elemento anche dalla session storage */
    let tabellaProcedimento: Array<ValoriAssegnamentoDTO> = JSON.parse(sessionStorage.getItem("tabellaProcedimento"));
    let i = tabellaProcedimento.findIndex(elem => elem.idComuneTipoProcedimento === rowData.idComuneTipoProcedimento
      && elem.usernameFunzionario === rowData.usernameFunzionario);
    tabellaProcedimento.splice(i, 1);
    sessionStorage.setItem("tabellaProcedimento", JSON.stringify(tabellaProcedimento));
  }

  get isED(): boolean { return this.userService.groupType === GroupType.EnteDelegato; }
  get invalidAssignment(): boolean 
  {
    let invalid = true;
    if (this.form.get("tipoAssegnazione").value)
    {
      if (this.form.get("tipoAssegnazione").value === "MANUALE")
        invalid = false;
      else if (this.form.get("tipoAssegnazione").value === "AUTOMATICO" && this.form.get("tipoSelezione").value)
      {
        if (this.form.get("tipoSelezione").value === "LOCALIZZAZIONE")
          invalid = !this.tabellaLocalizzazione || this.tabellaLocalizzazione.length === 0;
        else 
          invalid = !this.tabellaProcedimento || this.tabellaProcedimento.length === 0;
      }
    }
    return invalid; 
  }
}
