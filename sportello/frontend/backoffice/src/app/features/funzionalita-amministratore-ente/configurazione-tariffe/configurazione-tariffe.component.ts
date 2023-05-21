import { ApplicationRef, ChangeDetectorRef, Component, OnInit } from "@angular/core"
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms"
import { ActivatedRoute, Router } from "@angular/router"
import { TranslateService } from "@ngx-translate/core"
import { SelectItem } from "primeng/primeng"
import { CustomDialogService } from "src/app/core/services/dialog.service"
import { ButtonType, DialogButtons, DialogType } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component"
import { CONST } from "src/app/shared/constants"
import { AdminFunctionsService } from "src/app/shared/services/admin/admin-functions.service"
import { BreadcrumbService } from "src/app/shared/services/breadcrumb.service"
import { HttpDominioService } from "src/app/shared/services/http-dominio.service"
import { LoadingService } from "src/app/shared/services/loading.service"
import { LocalSessionServiceService } from "src/app/shared/services/local-session-service.service"
import { TariffaDTO } from "../../funzionalita-amministratore-applicazione/models/admin-functions.models"

const wgk = LocalSessionServiceService.WORKING_GROUP_KEY;

@Component({
  selector: 'app-configurazione-tariffe',
  templateUrl: './configurazione-tariffe.component.html',
  styleUrls: ['./configurazione-tariffe.component.css']
})
export class AdminConfigurazioneTariffeComponent implements OnInit {

  ///// DIALOG /////
  public display: boolean
  public type: string
  public number = "number"
  public hasConfirm: boolean
  public content: string
  public title: string
  public extraData: any;
  public tipiProcedimento: SelectItem[] = [];
  public procedimentoSelezionato: SelectItem;
  public lastProcedimentoRicerca: string;
  public listaTariffeForm: FormGroup;
  public listaTariffe: TariffaDTO[];
  public visualizzaTabella: boolean = false;
  public validaOn: boolean = false;
  public idTipoProcedimento: string;
  public onereIstruttorio: number = 0;
  public importo: number = 0;
  public isNoData: boolean;

  constructor(
    private translateService: TranslateService
    , private loadingService: LoadingService
    , private lss: LocalSessionServiceService
    , private formBuilder: FormBuilder
    , private dominioService: HttpDominioService
    , private cd: ChangeDetectorRef
    , private ar: ApplicationRef
    , private router: Router
    , private route: ActivatedRoute
    , private breadcrumbService: BreadcrumbService
    , private service: AdminFunctionsService
    , private dialog: CustomDialogService
  ) {}
  ngOnInit() {
    // this.loadingService.emitLoading(true);
    //Si ricava il tipo famiglia in base al ruolo scelto (working group nel session storage)
    this.idTipoProcedimento = this.route.snapshot.params['id'];
    this.ricerca(this.idTipoProcedimento);
  }

  /**
   * Metodo che ricerca le tariffe. Se riceve idTipoProcedimento lo usa per la ricerca,
   * altrimenti usa il tipo procedimento selezionato nella select
   * @param idTipoProcedimento 
   */
  public ricerca(idTipoProcedimento?: string) {
    this.loadingService.emitLoading(true);
    //Set del parametri di ricerca in base a se si è ricevuto il parametro in input o meno 
    let param = idTipoProcedimento ? idTipoProcedimento : this.procedimentoSelezionato.value;
    //Aggiornamento della variabile che tiene traccia del tipo procedimento usato per l'utlima ricerca
    //Utile in caso, dopo una ricerca, si selezioni annulla modifica in basso (potrebbe essere stato prima selezionato un altro tipo procedimento nella select).
    this.lastProcedimentoRicerca = param;
    this.service.getTariffeCorrente(param).toPromise()
      .then((tariffa) => {
        this.listaTariffe = tariffa.payload;
        this.loadData(this.listaTariffe);
        this.visualizzaTabella = true;
        if(tariffa.payload.length <= 0){
          this.primaRiga();
        }
        this.loadingService.emitLoading(false);
      }).catch(error => {
        //console.log("Error");
        this.loadingService.emitLoading(false);
      });
  }

  public annullaRicerca() {
    this.procedimentoSelezionato = null;
    this.visualizzaTabella = false;
  }

  // //Metodo che crea il form con tutte le tariffe
  private buildForm(): void {
    this.listaTariffeForm = this.formBuilder.group({
      tariffe: this.formBuilder.array([])
    });
  }

  /**
   * Metodo che carica tutti i dati delle tariffe ricevute in input nel form della tabella
   * @param tariffe 
   */
  public loadData(tariffe: TariffaDTO[]) {
    this.buildForm();
    tariffe.forEach((element, index) => {
      this.formTariffe.push(this.initFormTabella(element));
      //Dalla seconda riga in poi
      if (index > 0) {
        this.sottoscriviChangesRigaPrecedente(index);
      }
    });
  }

  /**
   * Metodo che sottoscrive la riga dell'indice passato in input ai cambi della soglia massima della riga precedente
   * in modo da aggiornare il valore della sogliaminima della riga i
   * @param index 
   * 
   */
  public sottoscriviChangesRigaPrecedente(index: number) {
    let tariffaRigaPrecedente = this.formTariffe.controls[index - 1] as FormGroup;
    let sogliaMassimaRigaPrecedente = tariffaRigaPrecedente.controls["sogliaMassima"] as FormControl;
    //Ci si sottoscrive per i valuechanges di soglia massima della riga precedente
    sogliaMassimaRigaPrecedente.valueChanges.subscribe(data => {
      //Per poter modificare in tempo reale la soglia minima della riga successiva
      let tariffaIesima = this.formTariffe.controls[index] as FormGroup;
      let sogliaMassimaJesima = tariffaIesima.controls["sogliaMinima"] as FormControl;
      sogliaMassimaJesima.patchValue(data, { emitEvent: false });
    });
  }

  /**
   * Metodo che inizializza la singola riga della tabella delle tariffe
   * 
   * @param tariffa 
   * @returns 
   */
  public initFormTabella(tariffa: TariffaDTO) {
    return this.formBuilder.group({
      // idTipoProcedimento: new FormControl({ value: tariffa ? tariffa.idTipoProcedimento : '', disabled: false }, Validators.required),
      idTipoProcedimento: new FormControl({ value: tariffa ? this.idTipoProcedimento : '', disabled: false }, Validators.required),
      sogliaMinima: new FormControl({ value: tariffa ? tariffa.sogliaMinima : '', disabled: false }, [Validators.required, Validators.min(0)]),
      sogliaMassima: new FormControl({ value: tariffa ? tariffa.sogliaMassima : '', disabled: false }, [Validators.required, Validators.min(0)]),
      deleteEccedente: new FormControl({ value: tariffa.deleteEccedente? tariffa.deleteEccedente : false, disabled: false }, Validators.required),
      percentuale: new FormControl({ value: tariffa ? tariffa.percentuale : '', disabled: false }, [Validators.required, Validators.min(0), Validators.max(100)]),
      cifraDaAggiungere: new FormControl({ value: tariffa ? tariffa.cifraDaAggiungere : '', disabled: false }, [Validators.required, Validators.min(0)]),
      percentualeFinale: new FormControl({ value: tariffa ? tariffa.percentualeFinale : '', disabled: false }, [Validators.required, , Validators.min(0), Validators.max(100)])
    });
  }

  /**
   * Ritorna un formArray contenente le tariffe
   */
  get formTariffe() { return <FormArray>this.listaTariffeForm.get('tariffe'); }

  public eliminaRiga(riga) {
    if (this.formTariffe.length > 1) {
      this.formTariffe.removeAt(riga);
    }
  }
  public primaRiga() {
    let tariffa: TariffaDTO = { sogliaMinima: 0 };
    this.formTariffe.push(this.initFormTabella(tariffa));
  }

  public aggiungiRiga(riga) {
    if (this.formTariffe.value[riga].sogliaMassima && this.formTariffe.value[riga].sogliaMassima != null) {
      let tariffa: TariffaDTO = { sogliaMinima: this.formTariffe.value[riga].sogliaMassima }
      this.formTariffe.push(this.initFormTabella(tariffa));
      this.sottoscriviChangesRigaPrecedente(riga + 1);
    } else {
      this.validaOn = false;
      this.type = 'info'
      this.hasConfirm = false;
      this.translateService.get("Bisogna prima selezionare una soglia massima per l'ultima riga disponibile")
        .subscribe(content => {
          this.content = content;
          this.translateService.get("Aggiungi riga")
            .subscribe(title => {
              this.title = title;
              this.display = true;
            })
        })
    }
  }

  /**
   * 
   * @param i Metodo per aggiornare la soglia minima della riga successiva al variare della soglia massima di una data riga
   */
  public changeSoglia(i) {
    let j = i + 1;
    if (this.formTariffe.length > 1 && (this.formTariffe.length - 1) > i) {
      let tariffaIesima = this.formTariffe.controls[j] as FormGroup;
      let sogliaMinimaIesima = tariffaIesima.controls["sogliaMinima"] as FormControl;
      sogliaMinimaIesima.setValue(this.formTariffe.value[i].sogliaMassima);
      this.cd.detectChanges();
      this.ar.tick();
      ////console.log("VALORE RIGA SUCCESSIVA: "+JSON.stringify(sogliaMinimaIesima));
    }
  }

  public salva(): void {
    //Si toglie il required dall'ultima soglia massima perchè può essere vuota
    let lastTariffa = this.formTariffe.controls[this.formTariffe.length - 1] as FormGroup;
    let sogliaMassimaLast = lastTariffa.controls["sogliaMassima"] as FormControl;
    sogliaMassimaLast.clearValidators();
    sogliaMassimaLast.updateValueAndValidity();
    this.validaOn = true;
    if (this.formTariffe.valid) {
      if (!this.verificaConsecutivitaTariffe(this.formTariffe)) {
        //console.log("VERIFICA CONSECUTIVITA");
        this.validaOn = false;
        this.type = 'warning'
        this.hasConfirm = false;
        this.translateService.get("edit.soglieNonConsecutive")
          .subscribe(content => {
            this.content = content;
            this.translateService.get("generic.BACK_CONFIRM_TITLE")
              .subscribe(title => {
                this.title = title;
                this.display = true;
              })
          })
        return;
      }
      this.validaOn = false;
      this.type = 'info'
      this.hasConfirm = true;
      this.translateService.get("tariffe.dialog.confirmSave.content")
        .subscribe(content => {
          this.content = content;
          this.translateService.get("tariffe.dialog.confirmSave.title")
            .subscribe(title => {
              this.title = title;
              this.display = true;
            })
        })
      this.extraData = "modifica";
    }
  }
  // //Metodo che visualizza la dialog di abort al click di annulla (quello in basso)
  public annulla(): void {
    this.validaOn = false;
    this.type = 'info'
    this.hasConfirm = true;
    this.translateService.get("tariffe.dialog.confirmAbort.content")
      .subscribe(content => {
        this.content = content;
        this.translateService.get("tariffe.dialog.confirmAbort.title")
          .subscribe(title => {
            this.title = title;
            this.display = true;
          })
      })
    this.extraData = "abort";
  }

  // //Azioni callback
  public callback(event: any): void {
    if (event.isConfirm) {
      if (this.extraData == "modifica") {
        this.confirmSalvataggio();
      }
      if (this.extraData == "abort") {
        this.ricerca(this.lastProcedimentoRicerca);
      }
    }
    this.display = false;
    this.extraData = "";
  }

  // //Metodo per il salvataggio delle tariffe
  private confirmSalvataggio(): void {
    this.loadingService.emitLoading(true);
    let request = this.creaResponseBean();
    this.service.saveTariffe(request)
      .subscribe(response => {
        this.loadingService.emitLoading(false);
        if (response && response.codiceEsito && response.codiceEsito == CONST.OK) {
          this.type = 'success'
          this.hasConfirm = false;
          this.translateService.get("tariffe.dialog.successSave.content")
            .subscribe(content => {
              this.content = content;
              this.translateService.get("tariffe.dialog.successSave.title")
                .subscribe(title => {
                  this.title = title;
                  this.display = true;
                });
            });
          this.loadData(response.payload);
        }
      });
  }

  // //Metodo che crea la lista di oggetti configTariffe da inviare al backend
  creaResponseBean(): TariffaDTO[] {
    let valoriTabella = this.formTariffe;
    let tariffe: TariffaDTO[] = [];
    for (let i = 0; i < valoriTabella.length; i++) {
      let tariffaTributi: TariffaDTO = {
        idTipoProcedimento: valoriTabella.at(i).get('idTipoProcedimento').value,
        sogliaMinima: valoriTabella.at(i).get('sogliaMinima').value,
        sogliaMassima: valoriTabella.at(i).get('sogliaMassima').value,
        deleteEccedente: valoriTabella.at(i).get('deleteEccedente').value,
        percentuale: valoriTabella.at(i).get('percentuale').value,
        cifraDaAggiungere: valoriTabella.at(i).get('cifraDaAggiungere').value,
        percentualeFinale: valoriTabella.at(i).get('percentualeFinale').value
      };
      tariffe.push(tariffaTributi);
    }
    return tariffe;
  }

  public verificaConsecutivitaTariffe(tariffe: FormArray): boolean {
    for (let i = 1; i < tariffe.length; i++) {
      if (tariffe.at(i).get('sogliaMassima').value < tariffe.at(i-1).get('sogliaMassima').value) {
        return false;
      }
      if (tariffe.at(i - 1).get('sogliaMassima').value != tariffe.at(i).get('sogliaMinima').value) {
        return false;
      }
    }
    return true;
  }

  public calcolaOnere(): void {
    let importo = this.importo;
    let length: number = this.formTariffe.controls.length;
    let form: FormGroup = null;
    for (let idx = 0; idx < length; idx++) {
      let formItem: FormGroup = <FormGroup>this.formTariffe.controls[idx];
      if (formItem.controls.sogliaMinima.value <= importo
        && (!formItem.controls.sogliaMassima.value || formItem.controls.sogliaMassima.value > importo)
      ) {
        form = formItem;
      }
    }
    if (form) {
      if (form.controls.deleteEccedente.value) {
        importo = importo - form.controls.sogliaMinima.value;
      }
      importo = importo * form.controls.percentuale.value / 100;
      importo = importo + form.controls.cifraDaAggiungere.value;
      importo = importo * form.controls.percentualeFinale.value / 100;
      this.onereIstruttorio = importo;
    } else {
      this.onereIstruttorio = null;
    }
  }

  public indietro(): void {
    this.dialog.showDialog(
      true,
      this.translateService.instant(
        "tariffe.dialog.confirmAbort.content"
      ),
      this.translateService.instant("tariffe.dialog.confirmAbort.title"),
      DialogButtons.CONFERMA_CHIUDI,
      (x) => {
        if (x === ButtonType.OK_BUTTON) {
          this.router.navigate(['admin', 'conf-procedimento', this.idTipoProcedimento])
        }
      },
      DialogType.INFORMATION
    );
  }
}
