import { TemplateDestinatarioDTO, TipoTemplate } from './../../../model/entity/admin.models';
import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DettaglioTemplate } from 'src/app/components/model/entity/admin.models';
import { DestinatarioComunicazioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { CONST } from './../../../../../shared/constants';


@Component({
  selector: 'app-dettaglio-template',
  templateUrl: './dettaglio-template.component.html',
  styleUrls: ['./dettaglio-template.component.css']
})
export class DettaglioTemplateComponent implements OnInit, OnChanges/* extends AuthComponent */
{
  @Input("dettaglio") dettaglio: DettaglioTemplate;
  @Input("destinatari") addresses: TemplateDestinatarioDTO[] = [];

  @Output("action") action = new EventEmitter<DettaglioTemplate>();
  @Output("reset") reset = new EventEmitter<{object: DettaglioTemplate, template: boolean, destinatari: boolean}>();

  public const = CONST;
  //public addresses: TemplateDestinatarioDTO[]; //Destinatari;
  public destinatariFissi: any;
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

  constructor(private router: Router,
              private translateService: TranslateService,
              private fb: FormBuilder)
  {
    /* super(router, lss); */
  }

  ngOnInit()
  {
    this.destinatariFissi = this.dettaglio.destinatariAutomatici;
    //this.addresses  = this.dettaglio.destinatari;
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
      codice: [this.dettaglio.codice, [Validators.required]],
      nome: [this.dettaglio.nome, [Validators.required]],
      descrizione: [this.dettaglio.descrizione, [Validators.required, Validators.maxLength(200)]],
      testo: [this.dettaglio.testo, [Validators.required, Validators.maxLength(4000)]],
      oggetto: [this.dettaglio.oggetto, [Validators.required, Validators.maxLength(200)]]
    });
  }

  private rebuildForm(): void
  {
    this.form.patchValue({
      codice: this.dettaglio.codice,
      nome: this.dettaglio.nome,
      descrizione: this.dettaglio.descrizione,
      testo: this.dettaglio.testo,
      oggetto: this.dettaglio.oggetto
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
        content=content+"<b>{"+el.codice+"}</b>:" +el.info+"<br>";
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
    if(this.form.valid)
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
  }

  public addDestinatario(address: DestinatarioComunicazioneDTO): void 
  { 
    if(!this.addresses || !this.addresses.some(s => s.email === address.email))
    {
      let tmp: TemplateDestinatarioDTO =
      {
        id: null,
        codiceTemplate: this.dettaglio.codice,
        nome: address.nome,
        email: address.email,
        pec: address.pec,
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
    if(index != -1)
      this.addresses.splice(index, 1);
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
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
            let template: DettaglioTemplate = this.form.getRawValue();
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
