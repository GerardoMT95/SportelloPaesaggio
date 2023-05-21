import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { TemplateDestinatarioDTO, TemplateEmailDestinatariDto } from 'src/app/shared/models/models';

@Component({
  selector: 'app-testi-comunicazioni-details',
  templateUrl: './testi-comunicazioni-details.component.html',
  styleUrls: ['./testi-comunicazioni-details.component.scss']
})
export class TestiComunicazioniDetailsComponent implements OnInit {
  /*
  @Input() form: FormGroup;
  @Output() saveEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output() cancelEmitter: EventEmitter<any> = new EventEmitter<any>();
  */

  @Input("dettaglio") dettaglio: TemplateEmailDestinatariDto;
  //@Input("destinatari") addresses: TemplateDestinatarioDTO[] = [];
  public addresses:TemplateDestinatarioDTO[]; //Destinatari;
  public destinatariFissi: any;
  @Output("action") action = new EventEmitter<TemplateEmailDestinatariDto>();
  @Output("reset") reset = new EventEmitter<{object: TemplateEmailDestinatariDto, template: boolean, destinatari: boolean}>();

  // Form dettaglio
  public form: FormGroup;
  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private translateService: TranslateService,
              private fb: FormBuilder)
  {
  }

  ngOnInit()
  {
    //this.destinatariFissi = this.dettaglio.destinatariAutomatici;
    this.addresses=this.dettaglio.destinatari;
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges): void
  {
    if(this.form)
      this.rebuildForm();
  }

  /**
  * Build form
  */
  private buildForm(): void
  {
    this.form = this.fb.group
    ({
      codice: [this.dettaglio.template.codice],
      nome: [this.dettaglio.template.nome, [Validators.required]],
      descrizione: [this.dettaglio.template.descrizione, [Validators.required, Validators.maxLength(200)]],
      testo: [this.dettaglio.template.testo, [Validators.required, Validators.maxLength(4000)]],
      oggetto: [this.dettaglio.template.oggetto, [Validators.required, Validators.maxLength(200)]],
      protocollazione: [this.dettaglio.template.protocollazione=='S']
    });
  }

  private rebuildForm(): void
  {
    this.form.patchValue({
      codice: this.dettaglio.template.codice,
      nome: this.dettaglio.template.nome,
      descrizione: this.dettaglio.template.descrizione,
      testo: this.dettaglio.template.testo,
      oggetto: this.dettaglio.template.oggetto,
      protocollazione: this.dettaglio.template.protocollazione=='S'
    });
  }

  /**
   * Open dialog for confirm go back
   */
  public back(): void
  {
    this.alertData =
    {
      title: this.translateService.instant('templateComunicazioni.dialog.back.title'),
      content: this.translateService.instant('templateComunicazioni.dialog.back.content'),
      typ: "info",
      isConfirm: true,
      extraData: {operazione: "goBack"},
      display: true
    };
  }

  /**
   * Open dialog info editor
   */
  public openInfo(): void
  {
    let content="";
    if(this.dettaglio.placeholders){
      this.dettaglio.placeholders.forEach(el=>{
        content=content+"<b>{"+el.value+"}</b>:" +el.description+"<br>";
      });
      
    }
    this.alertData =
    {
      title: this.translateService.instant('templateComunicazioni.dialog.info.title'),
      content:content, //this.translateService.instant('templateComunicazioni.dialog.info.content'),
      typ: "info",
      isConfirm: false,
      extraData: { operazione: "openInfo" },
      display: true
    };
  }

  /**
  * Open dialog confirm save
  */
  public openSalva()
  {
    //let destinatariValidi: boolean = this.addresses.some(a => a.tipo === "TO")||
      //      true;//this.dettaglio.codice==TipoTemplate.INVIO_COMUNICAZIONI; 
    if(!this.dettaglio.template.codice){//nuovo template...
              this.form.markAllAsTouched(); //fa scattare la validazione in rosso...
       }        
    if(this.form.valid )//&& destinatariValidi)
    {
      this.dettaglio.destinatari = this.addresses;
      this.alertData =
      {
        title: this.translateService.instant('templateComunicazioni.dialog.salva.title'),
        content: this.translateService.instant('templateComunicazioni.dialog.salva.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "save" },
        display: true
      };
    }
    else 
    {
      this.alertData =
      {
        title: "Errore",
        content: "Impossibile salvare il template: compilare i campi obbligatori",
        typ: "danger",
        isConfirm: false,
        extraData: null,
        display: true
      };
    }
  }

  public addDestinatario(address: DestinatarioComunicazioneDTO): void 
  { 
    console.log("Stampo il destinatario di input -> "+ JSON.stringify(address));
    if(!this.addresses || !this.addresses.some(s => s.email === address.email))
    {
      let tmp: TemplateDestinatarioDTO =
      {
        id: null,
        idOrganizzazione: this.dettaglio.template.idOrganizzazione,
        codiceTemplate: this.dettaglio.template.codice,
        denominazione: address.nome,
        email: address.pec ? null : address.email,
        pec: address.pec ? address.email : null,
        tipo: "CC"
      };
      if (!this.dettaglio.destinatari || this.dettaglio.destinatari.length === 0)
      {
        this.dettaglio.destinatari = [];
        tmp.tipo = "TO";
      }
      address.tipo = tmp.tipo;
      //this.dettaglio.destinatari.push(tmp); 
      this.addresses = [...this.addresses, tmp];
    }
    else
    {
      this.alertData =
      {
        title: "Attenzione",
        content: "Indirizzo già presente !!!",
        typ: "warning",
        isConfirm: false,
        extraData: null,
        display: true
      };
    }
  }

  public removeDestinatario(address: DestinatarioComunicazioneDTO): void 
  {
    let index = this.addresses.map(m => m.email).indexOf(address.email);
    index = index == -1 ? index = this.addresses.map(m => m.pec).indexOf(address.email) : index;
    if(index != -1){
      this.addresses.splice(index, 1);
    }
    console.log("indirizzi: ", this.addresses);
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso)
    {
      this.alertData.display = false;
    } 
    else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'goBack':
            this.action.emit(null);
            break;
          case 'save':
            let template: TemplateEmailDestinatariDto;
            template=this.dettaglio;
            let rawValue=this.form.getRawValue()
            if(rawValue.protocollazione){
              rawValue['protocollazione']='S';
            }else{
              rawValue['protocollazione']='N';
            }
            //rimetto il bean vecchio e aggiorno i dati su form
            template.template={...template.template,...rawValue};
            console.log(template);
            template.destinatari = this.dettaglio.destinatari; 
            this.action.emit(template);
            break;
          case 'resetTemplate':
            this.reset.emit({object: this.dettaglio, template: true, destinatari: false});
            break;
          case 'resetDestinatari':
            this.reset.emit({ object: this.dettaglio, template: false, destinatari: true });
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  public cambiaTipologia(destinatario: DestinatarioComunicazioneDTO): void
  {
    let index = this.addresses.map(m => m.email).indexOf(destinatario.email);
    if(index != -1)
      this.addresses[index].tipo = destinatario.tipo;
  }

  public resetTemplate(): void
  {
    this.alertData =
    {
      content: "Sei sicuro di voler ritornare al template originale? tutte le modifiche effettuate saranno perse.",
      title: "Attenzione",
      typ: "warning",
      extraData: {operazione: "resetTemplate"},
      isConfirm: true,
      display: true
    }
  }

  public resetDestinatari(): void
  {
    this.alertData =
    {
      content: "Sei sicuro di voler ripristinare i destinatari di default? gli attuali destinatari aggiunti saranno persi.",
      title: "Attenzione",
      typ: "warning",
      extraData: { operazione: "resetDestinatari" },
      isConfirm: true,
      display: true
    }
  }
}
