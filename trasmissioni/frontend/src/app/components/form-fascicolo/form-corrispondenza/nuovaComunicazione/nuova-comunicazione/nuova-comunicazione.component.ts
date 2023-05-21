import { DestinatarioComunicazioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { downloadFile, copyOf, checkFileSizeTypeFn } from 'src/app/components/functions/genericFunctions';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { CONST } from 'src/shared/constants';
import { isNullOrUndefined } from 'util';
import { CorrispondenzaDTO, DettaglioCorrispondenzaDTO } from './../../../../model/entity/corrispondenza.models';
import { UlterioreDocumentazioneService } from 'src/app/services/ulteriore-documentazione.service';
import { TranslateService } from '@ngx-translate/core';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { AllegatoCustomDTO } from 'src/app/components/model/entity/allegato.models';


@Component({
  selector: 'app-nuova-comunicazione',
  templateUrl: './nuova-comunicazione.component.html',
  styleUrls: ['./nuova-comunicazione.component.css']
})
export class NuovaComunicazioneComponent implements OnInit, OnDestroy
{
  @Input("idFascicolo") idFascicolo: number;
  @Input("showButtonBar") showButtonBar: boolean;
  @Input("forceSubmit") forceSubmit: Observable<boolean>;
  @Input("forceReset") forceReset: Observable<boolean>;

  @Input("init") toEdit: DettaglioCorrispondenzaDTO;

  @Output("sendData") submitted: EventEmitter<CorrispondenzaDTO> = new EventEmitter<CorrispondenzaDTO>();
  @Output("canceled") canceled: EventEmitter<boolean> = new EventEmitter<boolean>();

  public comForm: FormGroup;
  public inviato: boolean = false;
  public fileArray: any[] = [];
  public destinatariFissi: any;
  public destinatari: DestinatarioComunicazioneDTO[] = [];
  public const = CONST;
  private subscriberReset: Subscription;
  private subscriberSubmit: Subscription;
  public template: CorrispondenzaDTO = new CorrispondenzaDTO();

  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private formBuilder: FormBuilder,
              private autpaeSvc: AutorizzazioniPaesaggisticheService,
              private translateService:TranslateService,
              private showAlertDialog:ShowAlertService,
              private docService: UlterioreDocumentazioneService) 
  { 

  }


  ngOnInit() 
  {
    this.buildForm();
    if (!this.toEdit)
    {
      this.autpaeSvc.getTemplateCorrispondenza(this.idFascicolo).subscribe(res =>
      {
        if (res.codiceEsito == CONST.OK)
        {
          //  console.log("res ", res);
          this.template.id = res.payload.id;
          this.template.oggetto = res.payload.oggetto;
          this.template.testo = res.payload.testo;
          this.template.destinatari = res.payload.destinatari;
          this.setDestinatariFissi();
          this.initForm();
        }
      });
    }
    else
    {
      this.template.testo = this.toEdit.corrispondenza.testo;
      this.template.oggetto = this.toEdit.corrispondenza.oggetto;
      this.autpaeSvc.getTemplateCorrispondenza(this.idFascicolo).subscribe(
        res =>
        {
          if (res.codiceEsito == CONST.OK)
          {
            // console.log("res ", res);
            this.template.id = res.payload.id;
            this.template.destinatari = res.payload.destinatari;
            this.setDestinatariFissi();
            this.initForm();
          }
        }
      );
    }

    if (this.toEdit && this.toEdit.allegatiInfo)
    {
      this.toEdit.allegatiInfo.forEach(allegato =>
      {
        this.fileArray.push(this.mapToFileArrayElem(allegato));
        
      });
    }

    if (isNullOrUndefined(this.showButtonBar))
      this.showButtonBar = true;
    if (this.forceReset)
      this.initCancel();
    if (this.forceSubmit)
      this.initSubmit();
  }

  ngOnDestroy(): void
  {
    if (this.subscriberReset)
      this.subscriberReset.unsubscribe();
    if (this.subscriberSubmit)
      this.subscriberSubmit.unsubscribe();
  }

  public setDestinatariFissi()
  {
    this.destinatariFissi = this.template.destinatari;
  }

  private initSubmit(): void
  {
    this.subscriberSubmit = this.forceSubmit.subscribe(bozza =>
    {
      this.sendData(bozza);
    });
  }

  private initCancel(): void
  {
    this.subscriberReset = this.forceReset.subscribe(event =>
    {
      if (event)
        this.cancel();
    });
  }

  /* get formArray(): FormArray { return <FormArray>this.comForm.controls.destinatario } */

  public buildForm(): void
  {
    this.comForm = this.formBuilder.group
    ({
      //destinatario: this.formBuilder.array(this.buildDestinatari()),
      oggetto: new FormControl(this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.oggetto : null, Validators.required),
      testo: new FormControl(this.toEdit && this.toEdit.corrispondenza ? this.toEdit.corrispondenza.testo : null),
      allegati: new FormControl([])
    });
    this.destinatari = [];
    if(this.toEdit)
    {
      for(let dest of this.toEdit.destinatari)
        this.destinatari.push(copyOf(dest));
    }
    //this.destinatari = this.toEdit && copyOf(this.toEdit.destinatari) ? this.toEdit.destinatari : [];
  }

  public initForm()
  {
    this.comForm.controls.oggetto.setValue(this.template.oggetto);
    this.comForm.controls.testo.setValue(this.template.testo);
  }

  public addDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    address.tipo = 'CC';
    if (this.destinatari.length === 0)
      address.tipo = 'TO';
    if (!this.destinatari.some(s => s.email === address.email))
    {
      this.destinatari.push(address);
      console.log("Aggiunto, indirizzi: ", this.destinatari)
      this.comForm.markAsDirty();
    } 
  }

  public removeDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if (index != -1)
    {
      this.destinatari.splice(index, 1);
      this.comForm.markAsDirty();
    }
  }

  public cambiaTipologia(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if (index != -1)
    {
      this.destinatari[index].tipo = address.tipo;
      this.comForm.markAsDirty();
    }
  }
  
  public cancellaAllegato(index: number): void
  {
    if (this.fileArray[index].id)
    {
      //cancella
      this.alertData =
      {
        display: true,
        title: "Attenzione",
        content: "La cancellazione di questo allegato sarà irreversibile, proseguo?",
        typ: "warning",
        extraData: { operazione: "delete", id: this.fileArray[index].id, index: index },
        isConfirm: true,
      };
    }
    else
    {
      if (this.comForm.controls.allegati)
      {
        let fileIndex = this.comForm.controls.allegati.value.indexOf(this.fileArray[index].file)
        if (fileIndex >= 0)
          this.comForm.controls.allegati.value.splice(fileIndex);
        this.fileArray.splice(index);
        if (!this.comForm.controls.allegati.value)
          this.comForm.controls.allegati.setValue([]);
      }
    }
  }

  public allegaFile(event: any): void
  {
    if (event.files)
    {
      let file = event.files[0];
      let fileCheck=checkFileSizeTypeFn(null,CONST.MAX_10MB);
      let errorValidation=fileCheck(file);
			if(errorValidation){
				this.showAlertDialog.showAlertDialog(true, null, this.translateService.instant(errorValidation));
				return;
			}
      if (this.toEdit)
      {
        this.upload(file, this.idFascicolo, this.toEdit.corrispondenza.id);
      }
      else
      {
        let arr = this.comForm.controls.allegati.value;
        arr.push(file);
        this.comForm.controls.allegati.setValue(arr);
        this.fileArray.push({ name: file.name, file: file });
      }
    }
  }

  public sendData(bozza: boolean): void
  {
    if(!bozza)
    {
      this.alertData =
      {
        display: true,
        title: "Invio comunicazione",
        content: "Conferma per procedere con l'invio della comunicazione",
        typ: "info",
        extraData: {operazione: "invia"},
        isConfirm: true,
      }
    }
    else
    {
      this.triggerEventSend(bozza);
    }
  }

  private triggerEventSend(bozza: boolean): void
  {
    this.inviato = true;
    //this.formArray.updateValueAndValidity();
    if (bozza || (this.comForm.valid && this.destinatariValid))
    {
      let corrispondenza: CorrispondenzaDTO = this.comForm.getRawValue();
      corrispondenza.destinatari = this.destinatari;
      corrispondenza.bozza = bozza;
      corrispondenza.id = this.toEdit ? this.toEdit.corrispondenza.id : null;
      this.submitted.emit(corrispondenza);
    }
    else
    {
      this.alertData =
      {
        display: true,
        title: "Attenzione",
        content: "Compilare i campi obbligatori",
        typ: "warning",
        extraData: null,
        isConfirm: true,
      };
    }
  }

  private doClose(): void
  {
    this.inviato = false;
    this.comForm.reset();
    this.comForm.controls.allegati.setValue([]);
    this.canceled.emit(true);
  }

  public cancel(): void
  {
    if(this.comForm.dirty)
    {
      this.alertData =
      {
        content: "generic.warn-lose-all",
        title: "generic.attenzione",
        isConfirm: true,
        typ: "warning",
        extraData: {operazione: "back"},
        display: true
      }
    }
    else this.doClose();
    
  }

  public download(id: number, filename: string): void
  {
    this.autpaeSvc.downloadAllegatoFascicolo(this.idFascicolo+'',id.toString()).subscribe(result =>
    {
      if (result.ok)
        downloadFile(result.body, filename);
    });
  }

  private deleteAllegatoFromServer(id: number, index: number)
  {
    this.autpaeSvc.eliminaAllegatoCorrispondenza(id.toString(),this.idFascicolo+'',this.toEdit.corrispondenza.id+'').subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK)
      {
        this.fileArray.splice(index, 1);
      }
    });
  }

  private upload(file: File, idFascicolo: number, idCorrispondenza: number): void
  {
    this.autpaeSvc.caricaAllegatoComunicazione(file, idFascicolo, idCorrispondenza).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload)
        //this.fileArray.push({ name: result.payload.nome, id: result.payload.id,descrizione:result.payload.descrizione,checksum:result.payload.checksum });
        this.fileArray.push(this.mapToFileArrayElem(result.payload));
    });
  }

  private mapToFileArrayElem(allegato:AllegatoCustomDTO){
    return { name: allegato.nome, id: allegato.id,descrizione:allegato.descrizione,checksum:allegato.checksum,isUrl:allegato.isUrl };
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso)
    {
      this.alertData.display = false;
    } else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'delete':
            this.deleteAllegatoFromServer(event.extraData.id, event.extraData.index)
            break;
          case 'back':
            this.doClose();
            break
          case 'invia':
            this.triggerEventSend(false);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  get destinatariValid(): boolean { return this.destinatari.length > 0 && this.destinatari.some(s => s.tipo === 'TO'); }

  checksumFile(file:any){
    if(file && file.checksum){
      return file.checksum;
    }else{
      return "";
    }
  }

  descrizioneFile(file:any){
    if(file && file.descrizione){
      return file.descrizione;
    }else{
      return "Ulteriore documentazione";
    }
  }

}
