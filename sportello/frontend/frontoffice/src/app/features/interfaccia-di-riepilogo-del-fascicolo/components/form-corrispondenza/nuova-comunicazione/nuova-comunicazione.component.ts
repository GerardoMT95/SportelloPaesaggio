import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import {  FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { Allegato } from 'src/app/shared/models/models';
import { AllegatoCorrispondenza, DestinatarioComunicazioneDTO, DettaglioCorrispondenzaDTO, TemplateComunicazione } from '../../../models/corrispondenza.model';

@Component({
  selector: 'app-nuova-comunicazione',
  templateUrl: './nuova-comunicazione.component.html',
  styleUrls: ['./nuova-comunicazione.component.css']
})
export class NuovaComunicazioneComponent implements OnInit {
  //Input
  @Input("showButtonBar") showButtonBar: boolean = true;
  @Input("showPreviewButton") preview: boolean = false;
  @Input("templates") templateComunicazione: TemplateComunicazione[] = null;
  @Input("showUploadSection") uploadFile: boolean = true;
  @Input("idComunicazione") idComunicazione: number;
  @Input("init") toEdit: DettaglioCorrispondenzaDTO;
  @Input("codiceFascicolo") codiceFascicolo: string;
  @Input("requiredTipoComunicazione") requiredTipoComunicazione: boolean = false;
  @Input("optionLab") label: string = "label";
  @Input("hasProtocollo") hasProtocollo: boolean = false; //attiva il tasto invia con protocollo
  @Input("placeHolderSelezioneTemplate") placeHolderSelezioneTemplate: string = "-- Seleziona il tipo di procedimento --";
  @Input("indirizziMailDefault") indirizziMailDefault: DestinatarioComunicazioneDTO[] = [];
  //Output
  @Output("sendData") submitted: EventEmitter<DettaglioCorrispondenzaDTO> = new EventEmitter<DettaglioCorrispondenzaDTO>();
  @Output("sendDataWithProto") submittedWithProto: EventEmitter<DettaglioCorrispondenzaDTO> = new EventEmitter<DettaglioCorrispondenzaDTO>();
  @Output("canceled") canceled: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output("uploadFile") onUpload: EventEmitter<any> = new EventEmitter<any>();
  @Output("deleteFile") onDelete: EventEmitter<AllegatoCorrispondenza> = new EventEmitter<AllegatoCorrispondenza>();
  @Output("downloadFile") onDownload: EventEmitter<AllegatoCorrispondenza> = new EventEmitter<AllegatoCorrispondenza>();

  @Output("showPreview") onPreview: EventEmitter<DettaglioCorrispondenzaDTO> = new EventEmitter<DettaglioCorrispondenzaDTO>();

  //stato interno
  public comForm: FormGroup;
  public inviato: boolean = false;
  public optionLabel = this.label;
  public destinatari: DestinatarioComunicazioneDTO[] = [];
  public previstaProtocollazione = false;
  public selectOptionsTemplate: any[];
  public loaded: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private customDialogService: DialogService) { }

  ngOnInit() {
    if(this.templateComunicazione){
      this.selectOptionsTemplate = this.templateComunicazione.map(el => { return { label: el[this.optionLabel], value: el.value } });  
    }
    this.buildForm();
    this.loaded = true;
    if (this.toEdit && this.toEdit.destinatari)
      this.destinatari = this.toEdit.destinatari;
    this.destinatari = [...this.indirizziMailDefault,...this.destinatari];
  }

  get fileArray(): Allegato[] { return this.toEdit && this.toEdit.allegatiInfo ? this.toEdit.allegatiInfo : []; }
  
  public buildForm(): void {
    this.comForm = this.formBuilder.group
      ({
        //destinatari: this.formBuilder.array(this.buildDestinatari()),
        destinatari: [this.destinatari],
        id: [this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.id : null],
        codiceTemplate: new FormControl(this.toEdit && this.toEdit.corrispondenza.codiceTemplate, this.requiredTipoComunicazione ? Validators.required : null),
        oggetto: new FormControl(this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.oggetto : null, Validators.required),
        testo: new FormControl(this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.testo : null, Validators.required),
        allegati: new FormControl([]),
        riservata: new FormControl(this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.riservata : null)
      });
    //setto il prevista protocollazione....
    if (this.templateComunicazione && this.templateComunicazione.length > 0 && this.toEdit) {
      let templateFound = this.templateComunicazione.find(t => t.value === this.toEdit.corrispondenza.codiceTemplate);
      if (templateFound) {
        this.previstaProtocollazione = templateFound.template.protocollazione == 'S';
      }
    }

  }

  public addDestinatario(event: DestinatarioComunicazioneDTO): void {
    this.destinatari.length === 0 ? event.tipo = "TO" : event.tipo = "TO"; //mettiamo sempre to... 
    if (!this.destinatari.map(m => m.email).includes(event.email))
      this.destinatari = [...this.destinatari, event];
  }

  public removeDestinatario(event: DestinatarioComunicazioneDTO): void {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.destinatari.splice(index, 1);
  }

  public cambiaTipologia(event: DestinatarioComunicazioneDTO): void {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.destinatari[index].tipo = event.tipo;
  }

  public showPreview(): void {
    let corrispondenza: DettaglioCorrispondenzaDTO = this.internalStateToCorrispondenza(true);
    this.onPreview.emit(corrispondenza);
  }

  public download(file: AllegatoCorrispondenza): void { this.onDownload.emit(file); }
  public cancellaAllegato(file: AllegatoCorrispondenza): void { this.onDelete.emit(file); }

  public allegaFile(event: any): void {
    if (event.files) {
      let container =
      {
        idComunicazione: this.idComunicazione,
        file: event.files[0]
      };
      this.onUpload.emit(container);
    }
  }

  private doSubmit(bozza: boolean, conProtocollo: boolean) {
    //qui arrivo dopo eventuale conferma....
    let corrispondenza: DettaglioCorrispondenzaDTO = this.internalStateToCorrispondenza(bozza);
    if (conProtocollo) {
      this.submittedWithProto.emit(corrispondenza);
    } else {
      this.submitted.emit(corrispondenza);
    }
  }

  public sendData(bozza: boolean, conProtocollo: boolean): void {
    if (!bozza) {
      this.inviato = true;
      this.comForm.updateValueAndValidity();
      if (!this.comForm.valid || this.destinatari.length == 0) {
        this.customDialogService.showDialog(
          true,
          "generic.compileRequired",
          "generic.warning",
          DialogButtons.CHIUDI,
          null,
          DialogType.WARNING,
          null,
          null
        );
        return;
      }
      //chiedo ulteriore conferma ...
      let message = "";
      if (this.previstaProtocollazione && !conProtocollo && this.hasProtocollo) {
        message = 'COMMUNICATIONS.PREVISTO_PROTO';
      } else if (!this.previstaProtocollazione && conProtocollo && this.hasProtocollo) {
        message = 'COMMUNICATIONS.NON_PREVISTO_PROTO';
      } else {
        message = 'COMMUNICATIONS.CONFERMI_INVIO';
      }
      this.customDialogService.showDialog(
        true,
        message,
        "generic.warning",
        DialogButtons.CONFERMA_CHIUDI,
        (bottone => {
          if (bottone == ButtonType.OK_BUTTON)
            this.doSubmit(bozza, conProtocollo);
        }),
        DialogType.INFORMATION,
        null,
        null
      );
    } else {
      this.doSubmit(bozza, conProtocollo);
    }
  }

  public internalStateToCorrispondenza(bozza: boolean): DettaglioCorrispondenzaDTO {
    let corrispondenza = this.toEdit;
    Object.keys(this.comForm.controls).forEach(key => {
      if (key === "destinatari") {
        corrispondenza.destinatari = this.destinatari;
        //this.destinatari.forEach((destinatario) =>
        //corrispondenza.destinatari.push({ codice: destinatario.email, label: destinatario.nome }));
        /*this.formArray.controls.forEach((control: FormGroup) =>
        {
          if (control.controls.email.value !== null)
          {
            corrispondenza.destinatari.push({ codice: control.controls.email.value, label: "" });
          }
        });*/
      }
      else if (key === "oggetto") {
        corrispondenza.corrispondenza.oggetto = (this.comForm.controls[key].value ? (" " + this.comForm.controls[key].value) : "");
      }
      else
        corrispondenza.corrispondenza[key] = this.comForm.controls[key].value;
    });
    corrispondenza.corrispondenza.bozza = bozza;
    corrispondenza.corrispondenza.id = this.idComunicazione;
    return corrispondenza;
  }

  private goBack() {
    this.inviato = false;
    this.comForm.reset();
    //this.comForm.controls.destinatari = this.formBuilder.array([this.createRow()]);
    this.comForm.controls.allegati.setValue([]);
    this.canceled.emit(true);
  }

  public cancel(): void {
    if (this.comForm.dirty) {
      this.customDialogService.showDialog(
        true,
        "generic.abbandonaModifiche",
        "generic.warning",
        DialogButtons.OK_CANCEL,
        (bottonePremuto) => {
          if (bottonePremuto == ButtonType.OK_BUTTON) {
            this.goBack();
          }
        },
        DialogType.WARNING,
        null,
        null
      );
    }
    else {
      this.goBack();
    }
  }

  public cambioTemplate(event: any): void {
    this.destinatari = [];
    this.previstaProtocollazione = false;
    if (event && event.value) {
      let templateFound = this.templateComunicazione.find(t => t.value === event.value);
      //puo' essere che me lo ha tolto... no template...
      if (templateFound) {
        templateFound.template.destinatari.forEach(el => {
          this.destinatari.push(el);
        });
        this.previstaProtocollazione = templateFound.template.protocollazione == 'S';
        this.comForm.patchValue
          ({
            oggetto: templateFound.template.oggetto,
            testo: templateFound.template.testo,
            riservata: templateFound.template.riservata
          });
      }
    }
  }

  public getPrefixOggetto(){
    let codicetemplate=this.comForm.get('codiceTemplate').value;
    if(!codicetemplate){
      return "Fascicolo " +this.codiceFascicolo; //se non ha template viene impostato il prefisso
    }else{
      return ""; 
    }
  }


}
