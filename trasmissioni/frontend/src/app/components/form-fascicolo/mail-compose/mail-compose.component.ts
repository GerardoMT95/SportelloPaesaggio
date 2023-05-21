import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { FascicoloConfig } from 'src/shared/config/fascicolo.config';
import { CONST } from 'src/shared/constants';
import { TableHeader } from 'src/shared/models/table-header';
import { downloadFile } from '../../functions/genericFunctions';
import { DestinatarioComunicazioneDTO } from '../../model/entity/corrispondenza.models';
import { DettaglioFascicolo } from '../../model/fascicolo.models';
import { DestinatariComunicazione } from '../../model/recipient.model';
import { TipologicaDTO } from './../../model/entity/localizzazione.models';

@Component({
  selector: 'app-mail-compose',
  templateUrl: './mail-compose.component.html',
  styleUrls: ['./mail-compose.component.scss']
})

export class MailComposeComponent implements OnInit
{
  @Input() dettaglioFascicolo: DettaglioFascicolo;
  @Output() indietroEvent = new EventEmitter<any>();
  @Output() transmittedEvent = new EventEmitter<DestinatarioComunicazioneDTO>();

  /* public mailForm: FormGroup; */
  public destinatariFissi: DestinatarioComunicazioneDTO[];
  public destinatariUlteriori: DestinatarioComunicazioneDTO[] = [];
  //tableHeadersAttachment: TableHeader[];

  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };


  constructor(private autPaesSvc: AutorizzazioniPaesaggisticheService,
              private promptDialogService: ShowPromptDialogService,
              private formBulder: FormBuilder,
              private translateService: TranslateService,
              private allegatiService: AllegatiService) { }

  ngOnInit()
  {
    this.autPaesSvc.postValidazione(this.dettaglioFascicolo.id).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK && result.payload)
      {
        this.destinatariFissi = result.payload;
        /* result.payload.forEach(m =>
        { 
          this.destinatariFissi.push({ denominazione: m.label, mailAddress: m.codice, to: true })
        }); */
        //this.destinatariFissi = result.payload.map(m => { return {denominazione: m.ente, mailAddress: m.email, to: true }; });
        this.destinatariUlteriori = [];
      }
    });

    /* this.autPaesSvc.getDestinatari().then(res =>
    {
      this.destinatariFissi = res;
      this.destinatariUlteriori = [];
    }); */

    /* this.tableHeadersAttachment = FascicoloConfig.destinatariListTableHeaders();
    this.mailForm = this.formBulder.group({
      denominazione: ['', Validators.required],
      mailAddress: ['', [Validators.required, Validators.email]],
    }); */

  }

  @HostListener("document:click", ["$event"])
  click(event: any)
  {
    if (event.target.id === "download")
      this.downloadAnteprima();
  }

  /* daMarcare(formControlName: string): boolean
  {
    let controllo = this.mailForm['controls'][formControlName];
    return controllo && controllo.invalid &&
      // (controllo.dirty || controllo.touched);
      (controllo.dirty || this.mailForm.dirty);
  } */

  /* deleteDestinatario(destComunicazione: DestinatariComunicazione)
  {
    this.alertData.isConfirm = true;
    this.alertData.typ = "info";
    this.alertData.content = "Confermi la rimozione?";
    this.alertData.extraData = { operazione: 'cancella', destinatario: destComunicazione };
    this.alertData.display = true;
  } */

  /* checkDuplicato(mail: string): boolean
  {
    let ret = false;
    let addresses = new Set(
      [...this.destinatariUlteriori.map(el => el.mailAddress),
      ...this.destinatariUlteriori.map(el => el.mailAddress)]
    );
    ret = addresses.has(mail);
    if (ret)
    {
      this.alertData.isConfirm = false;
      this.alertData.typ = "info";
      this.alertData.content = this.translateService.instant('fascicolo.mailCompose.mailDupl') + " :" + mail;
      this.alertData.display = true;
    }
    return addresses.has(mail);
  } */

  public addDestinatario(item: DestinatarioComunicazioneDTO): void
  {
    if(!this.destinatariUlteriori.some(s => s.email === item.email))
    {
      item.tipo = 'CC';
      if(this.destinatariUlteriori.length === 0)
        item.tipo = 'TO';
      this.destinatariUlteriori.push(item);
    }
      
    /* if (this.checkDuplicato(this.mailForm.get('mailAddress').value))
    {
      return;
    }
    if (this.mailForm.valid)
    {
      let dest = this.mailForm.getRawValue();
      console.log("added {}", dest);
      this.destinatariUlteriori.push(dest);
      this.mailForm.reset();
    } else
    {
      this.mailForm.markAsDirty();
    } */

  }

  public cambiaTipologia(item: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatariUlteriori.map(m => m.email).indexOf(item.email);
    if (index != -1)
      this.destinatariUlteriori[index].tipo = item.tipo;
  }

  public removeDestinatario(item: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatariUlteriori.map(m => m.email).indexOf(item.email);
    if(index != -1)
      this.destinatariUlteriori.splice(index, 1);
  }

  /* editDestinatario(destComunicazione: DestinatariComunicazione)
  {
    this.mailForm.get('denominazione').setValue(destComunicazione.denominazione);
    this.mailForm.get('mailAddress').setValue(destComunicazione.mailAddress);
    this.destinatariUlteriori.splice(this.destinatariUlteriori.findIndex(el => el == destComunicazione), 1);
  } */

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    console.log(event);
    if (event.isChiuso)
    {
      this.alertData.display = false;
    } else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'cancella':
            /* let index = this.destinatariUlteriori.findIndex(el =>
            {
              return el.denominazione == event.extraData.destinatario.denominazione &&
                el.mailAddress == event.extraData.destinatario.mailAddress;
            });
            console.log("indice:" + index);
            if (index >= 0)
            {
              this.destinatariUlteriori.splice(index, 1);
            } */
            break;
          case 'back':
            this.destinatariUlteriori = [];
            /* this.mailForm.reset(); */
            this.indietroEvent.emit();
            break;
          case 'trasmetti':
            //let tp: TipologicaDTO[] = [];
            /* if (event.extraData.destinatari)
            {
              event.extraData.destinatari.forEach(d =>
              {
                //          this.destinatariFissi.push({ denominazione: m.label, mailAddress: m.codice, to: true })

                tp.push({ codice: d.mailAddress, label: d.denominazione });
              });
            } */
            this.transmittedEvent.emit(event.extraData.destinatari);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  trasmetti()
  {
    this.alertData.isConfirm = true;
    this.alertData.typ = "info";
    this.alertData.title = this.translateService.instant('fascicolo.mailCompose.titleDialog');
    this.alertData.content = this.translateService.instant('fascicolo.mailCompose.infoMessage') + "<br/><span style='color: blue; cursor: pointer' id='download'>download anteprima</span>";
    this.alertData.extraData = { operazione: 'trasmetti', destinatari: this.destinatariUlteriori };
    this.alertData.display = true;
  }

  back()
  {
    this.alertData.isConfirm = true;
    this.alertData.typ = "info";
    this.alertData.title = this.translateService.instant('fascicolo.abbandonaModifiche');
    this.alertData.content = this.translateService.instant('fascicolo.sicuroAbbandonoModifiche');
    this.alertData.extraData = { operazione: 'back' };
    this.alertData.display = true;
  }

  downloadAnteprima()
  {
    /*let body: TipologicaDTO[] = [];
    if (this.destinatariUlteriori)
    {
      this.destinatariUlteriori.forEach(destinatario =>
      {
        body.push({ codice: destinatario.email, label: destinatario.nome });
      });
    }
    this.autPaesSvc.downloadAnteprima(this.dettaglioFascicolo.id.toString(), body)*/
    this.autPaesSvc.downloadAnteprima(this.dettaglioFascicolo.id.toString(), this.destinatariUlteriori)
    .subscribe(result =>
    {
      if(result.ok)
        downloadFile(result.body, "anteprima ricevuta di trasmissione.pdf");
    });
  }
}
