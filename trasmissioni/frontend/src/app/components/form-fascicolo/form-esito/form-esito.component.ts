import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { checkFileSizeTypeFn, downloadFile } from 'src/app/components/functions/genericFunctions';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { CONST } from 'src/shared/constants';
import { PlanFormField } from 'src/shared/models/plan-form.model';
import { AllegatoCustomDTO } from './../../model/entity/allegato.models';
import { InformazioniDTO } from './../../model/entity/info.models';

@Component({
  selector: 'app-form-esito',
  templateUrl: './form-esito.component.html',
  styleUrls: ['./form-esito.component.css']
})
export class FormEsitoComponent implements OnInit, OnChanges {
  @Input() formEsito: FormGroup;
  @Input() dettaglioFascicolo: InformazioniDTO;
  @Input() disable: boolean;
  
  esitoFormFields: PlanFormField[];
  it = CONST.IT;
  MAX_YEAR = CONST.NOW_YEAR;
  NOW_DATE = CONST.NOW_DATE;
  
  public const = CONST;

  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  /* cose mie --> */

  public colonneTabellaProvvedimentoFinale: any[] =
    [
      { header: "fascicolo.tableAllegati.descrizione" },
      { header: "fascicolo.tableAllegati.nomeFile" },
      { header: "fascicolo.tableAllegati.dataArrivo" },
    ];

  public colonneTabellaTrasmissioneProvvedimento: any[] =
    [
      { header: "fascicolo.tableAllegati.descrizione" },
    ];

  public listProvvedimentoFinale: AllegatoCustomDTO[] = [];
  public listLetteraTrasmissioneProvvedimento: AllegatoCustomDTO[] = [];
  @Input() mostraLetteraProvvedimento: boolean;
  @Input() prosegui: boolean;
  @Output() riempimentoTabellaProvvedimentoFinale: EventEmitter<File> = new EventEmitter<File>();
  @Input() allegati: Array<any>;

  /* <-- */

  constructor(private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private loadingService: LoadingService,
    private router: Router,
    private showAlertDialog:ShowAlertService,
    private autPaeSvc: AutorizzazioniPaesaggisticheService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.allegati) {
      this.popolaTabelleAllegati(changes.allegati.currentValue);
    }
  }

  ngOnInit() {
    /* this.esitoFormFields = FascicoloFormConfig.esitoFormField(this.formBuilder);
    if (this.dettaglioFascicolo.codeStatoFascicolo == "FINISHED") {
      this.formEsito.disable();
    } */
    this.initTable();
  }

  formValidator() {
    this.esitoFormFields.forEach(item => {
      if (item.required) {
        this.formEsito.controls[item.field].setValidators(Validators.required);
        this.formEsito.controls[item.field].updateValueAndValidity();
      }
    });
  }

  /*inviaComunicazione() {
    this.formValidator();
    if (this.formEsito.valid) {
      this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('OPERAZIONE_IRREVERSIBILE');
      this.alertData.content = this.translateService.instant('fascicolo.esito.msgConferma');
      this.alertData.extraData = { operazione: 'trasmetti' };
      this.alertData.display = true;
    } else {
      this.alertData.isConfirm = false;
      this.alertData.typ = "info";
      this.alertData.title = "";
      this.alertData.content = this.translateService.instant('CAMPI_INVALIDI');
      this.alertData.display = true;
    }
  }

  doTrasmetti() {
    this.loadingService.emitLoading(true);
    this.autPaeSvc.trasmettiVerificaFascicolo(this.dettaglioFascicolo.codice, this.formEsito.getRawValue()).then(
      res => {
        if (res.code == "OK") {
          this.alertData.isConfirm = false;
          this.alertData.typ = "info";
          this.alertData.title = "";
          this.alertData.content = this.translateService.instant('fascicolo.mailCompose.trasmissioneOK');
          this.alertData.isConfirm = true;
          this.alertData.display = true;
          this.alertData.extraData = { operazione: 'back' };
        } else {
          this.alertData.isConfirm = false;
          this.alertData.typ = "danger";
          this.alertData.title = "";
          this.alertData.content = this.translateService.instant('error.READ_WRITE_DATA_ERROR');
          this.alertData.display = true;

        }
        this.loadingService.emitLoading(false);
      });

  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'trasmetti':
            this.doTrasmetti();
            break;
          case 'back':
            this.router.navigate(['/private/fascicolo']);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }
*/
  /* miei metodi */

  public initTable(): any {
    let provvedimentoFinale: AllegatoCustomDTO = new AllegatoCustomDTO();
    provvedimentoFinale.tipo = "Provvedimento Finale Esito Verifica*";
    this.listProvvedimentoFinale.push(provvedimentoFinale);
  }

  public uploadFile(event: any): void {
    let provvedimentoFinale: File = event.files[0];
    let fileCheck=checkFileSizeTypeFn(null,CONST.MAX_10MB);
      let errorValidation=fileCheck(provvedimentoFinale);
			if(errorValidation){
				this.showAlertDialog.showAlertDialog(true, null, this.translateService.instant(errorValidation));
				return;
			}
    this.listProvvedimentoFinale[0].nome = provvedimentoFinale.name;
    this.listProvvedimentoFinale[0].dataCaricamento = new Date();
    this.listProvvedimentoFinale[0].id = null; /* lo setto al prosegui */
    this.formEsito.controls.tabellaProvvedimentoFinale.setValue(true);
    this.formEsito.updateValueAndValidity();
    this.riempimentoTabellaProvvedimentoFinale.emit(provvedimentoFinale);
  }

  public download(idAllegato: number, tipoFile: string): void {
    this.loadingService.emitLoading(true);
    this.autPaeSvc.downloadAllegatoFascicolo(this.dettaglioFascicolo.id+'',idAllegato.toString()).subscribe(
      response => {
        if (response.ok) {
          downloadFile(response.body, tipoFile + this.determinaEstensione(response.body.type));
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

  private determinaEstensione(type: string): string {
    let estensione: string = "";
    if (type === "application/pdf") {
      estensione = ".pdf";
    }
    else {
      estensione = ".rtf";
    }
    return estensione;
  }

  public delete(idAllegato: number): void {
    this.listProvvedimentoFinale[0].id = null;
    this.listProvvedimentoFinale[0].nome = null;
    this.listProvvedimentoFinale[0].dataCaricamento = null;
    this.formEsito.controls.tabellaProvvedimentoFinale.setValue(false);
    this.formEsito.updateValueAndValidity();
  }

  public popolaTabelleAllegati(listaAllegati: Array<any>): void {
    listaAllegati.forEach(allegato => {
      if (allegato.tipo === "PROVVEDIMENTO_FINALE_ESITO") {
        this.listProvvedimentoFinale[0].id = allegato.id;
        this.listProvvedimentoFinale[0].nome = allegato.nome;
        this.listProvvedimentoFinale[0].dataCaricamento = allegato.dataCaricamento;
      }
      /* andrebbe bene anche else solamente, ma così si capisce meglio penso */
      else if (allegato.tipo === "PROVVEDIMENTO_FINALE_ESITO_LETTERA") {
        if (this.listLetteraTrasmissioneProvvedimento.length === 0) {
          let allegatoCustomDTO: AllegatoCustomDTO = new AllegatoCustomDTO();
          allegatoCustomDTO.id = allegato.id;
          allegatoCustomDTO.tipo = "Lettera Provvedimento Finale Esito Verifica";
          this.listLetteraTrasmissioneProvvedimento.push(allegatoCustomDTO);
        }
        else {
          this.listLetteraTrasmissioneProvvedimento[0].id = allegato.id;
        }
      }
    });
  }

  public ripristina(): void {
    this.loadingService.emitLoading(true);
    this.autPaeSvc.ripristina(this.dettaglioFascicolo.id.toString()).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.listLetteraTrasmissioneProvvedimento[0].id = response.payload;
          this.alertData = {
            display: true,
            title: "Successo",
            content: "Lettera ripristinata con successo",
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

  public uploadLettera(event: any): void {
    let lettera: File = event.target.files[0];
    let fnCheckFileTypeSize=checkFileSizeTypeFn('application/pdf',CONST.MAX_5MB);
    /* se ho selezionato qualcosa */
    if (lettera) {
      let esitoCheckFIle=fnCheckFileTypeSize(lettera);
      if(esitoCheckFIle){
        this.alertData = {
          display: true,
          title: "Attenzione",
          content: esitoCheckFIle,
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
        return;
      }
      if (lettera.type === "application/pdf") {
        this.loadingService.emitLoading(true);
        this.autPaeSvc.caricaLettera(this.dettaglioFascicolo.id.toString(), lettera).subscribe(
          response => {
            if (response.codiceEsito === "OK") {
              this.listLetteraTrasmissioneProvvedimento[0].id = response.payload;
              this.alertData = {
                display: true,
                title: "Successo",
                content: "Lettera caricata con successo",
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
      else {
        this.alertData = {
          display: true,
          title: "Attenzione",
          content: "Il file selezionato non è un PDF",
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
      }
    }
  }

  callbackAlert(event){}

}
