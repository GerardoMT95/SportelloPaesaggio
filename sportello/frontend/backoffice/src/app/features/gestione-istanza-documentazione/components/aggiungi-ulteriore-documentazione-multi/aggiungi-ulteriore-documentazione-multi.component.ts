import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { Observable } from 'rxjs';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { CorrispondenzaDTO } from 'src/app/features/gestione-istanza-comunicazioni/model/corrispondenza.models';
import { AllegatoCustomDTO } from 'src/app/features/gestione-istanza-documentazione/model/allegato.models';
import { GroupType, TemplateDestinatarioDTO } from 'src/app/shared/models/models';
import { LocalSessionServiceService } from 'src/app/shared/services/local-session-service.service';
import { UserService } from 'src/app/shared/services/user.service';
import { AllegatoUD, UlterioreDocumentazioneBean } from './../../../../shared/models/models';
import { AdminFunctionsService } from './../../../../shared/services/admin/admin-functions.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { ShowAlertService } from './../../../../shared/services/show-alert/show-alert.service';


@Component({
  selector: 'app-aggiungi-ulteriore-documentazione-multi',
  templateUrl: './aggiungi-ulteriore-documentazione-multi.component.html',
  styleUrls: ['./aggiungi-ulteriore-documentazione-multi.component.scss']
})
export class AggiungiUlterioreDocumentazioneMultiComponent implements OnInit
{
  @Input("idFascicolo") idFascicolo: number;
  @Input("entiOptions") entiOptions$: Observable<SelectItem[]>;
  @Input("indirizziMailDefault") indirizziMailDefault: DestinatarioComunicazioneDTO[] = [];
  @Output("onComplete") complete: EventEmitter<{doc: UlterioreDocumentazioneBean, files: File[]}> = new EventEmitter();
  @Output("onCanceled") canceled: EventEmitter<boolean> = new EventEmitter();

  public destinatari: DestinatarioComunicazioneDTO[] = [];
  public inviato: boolean = false;
  public destinatariFissi: any;
  public gruppoScelto: string;
  public tipoGruppoScelto: string;
  public enti: any[] = [];
  public selectedDefault: Array<any> = [];

  public data: AllegatoUD; 
  public indexModifica: number = -1;
  public template: CorrispondenzaDTO = new CorrispondenzaDTO();
  public files: File[] = [];
  public allegati: AllegatoCustomDTO[] = [];

  public openEdit: boolean = false;

  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private lss: LocalSessionServiceService,
              private showAlertDialog: ShowAlertService,
              private translateService: TranslateService,
              private adminService: AdminFunctionsService,
              private userService: UserService,
              private loading: LoadingService) { }

  ngOnInit()
  {
    this.destinatari = this.indirizziMailDefault;
    this.allegati = []
    this.inviato = false;
    this.resetVisualizzabiliDaDefault()
    this.getDestinatariDefault()
  }

  private resetVisualizzabiliDaDefault(): void
  {
    this.entiOptions$.subscribe(
      response =>
      {
        response.forEach(item =>
        {
          if (item.value === GroupType.EnteDelegato || item.disabled)
            this.selectedDefault.push(item.value);
        });
      }
    );
  }

  private getDestinatariDefault()
  {
    this.loading.emitLoading(true)
    let gt = this.userService.groupType;
    let template = ""
    if (gt == GroupType.EnteDelegato)
    {
      template = "ULTER_DOC_ED";
    } else if (gt == GroupType.CommissioneLocale)
    {
      template = "ULTER_DOC_CL";
    }
    else if (gt == GroupType.EnteTerritoriale)
    {
      template = "ULTER_DOC_ET";
    } else if (gt == GroupType.Soprintendenza)
    {
      template = "ULTER_DOC_SOP";
    }
    else if (gt == GroupType.Regione)
    {
      template = "ULTER_DOC_REG";
    }

    this.adminService.infoTemplateEmail(template).subscribe(_result =>
    {
      if (_result.codiceEsito === 'OK' && _result.payload)
      {
        _result.payload.destinatari.map((d: TemplateDestinatarioDTO) =>
        {
          let destinatario: DestinatarioComunicazioneDTO = new DestinatarioComunicazioneDTO();
          destinatario.email = d.email;
          destinatario.id = d.id;
          destinatario.nome = d.denominazione;
          destinatario.pec = d.pec == "pec" ? true : false;
          destinatario.tipo = d.tipo;
          this.destinatari.push(destinatario);
        })
      }
      this.loading.emitLoading(false);
    },
    error =>
    {
      this.loading.emitLoading(false);
    })
  }

  public setDestinatariFissi()
  {
    this.destinatariFissi = this.template.destinatari;
  }

  public addDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    address.tipo = 'CC';
    if (this.destinatari.length === 0) address.tipo = 'TO';
    if (!this.destinatari.some(s => s.email === address.email))
    {
      this.destinatari.push(address);
    }
  }

  public removeDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if (index != -1)
    {
      this.destinatari.splice(index, 1);
    }
  }

  public cambiaTipologia(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if (index != -1)
    {
      this.destinatari[index].tipo = address.tipo;
    }
  }

  public salva(obj: AllegatoUD): void
  {
    let all: AllegatoCustomDTO = {
      id: null,
      idCms: null,
      dataCaricamento: null,
      dimensione: obj.allegato.size,
      nome: obj.allegato.name,
      tipo: 'ULTERIORE_DOCUMENTAZIONE',
      utenteInserimento: null,
      mimeType: null,
      multiplo: null,
      obbligatorio: null,
      titolo: obj.titolo,
      descrizione: obj.descrizione
    };
    if(this.indexModifica == -1)
    {
      this.allegati.push(all);
      this.files.push(obj.allegato);
    }
    else
    {
      this.allegati[this.indexModifica] = all;
      this.files[this.indexModifica] = obj.allegato;
    }
    this.data = null;
    this.indexModifica = -1;
    this.openEdit = false;
  }

  public chiudi(): void
  {
    this.data = null;
    this.indexModifica = -1;
    this.openEdit = false;
  }

  public apriEdit(index: number): void
  {
    if(index != -1)
    {
      this.indexModifica = index;
      this.data = {
        titolo: this.allegati[this.indexModifica].titolo,
        descrizione: this.allegati[this.indexModifica].descrizione,
        allegato: this.files[this.indexModifica]
      };
    }
    this.openEdit = true;
  }

  public eliminaElemento(index: number): void
  {
    this.allegati.splice(index, 1);
  }

  private isValido(){
    return (this.destinatari.length > 0 && this.allegati.length > 0 && this.selectedDefault);
  }

  public onSubmit(): void
  {
    this.inviato = true;
    if(this.isValido())
    {
      this.inviato = false;
      this.alertData =
      {
        content: "Confermare per allegare la documentazione appena compilata al fascicolo",
        title: "Allega documentazione",
        isConfirm: true,
        typ: "info",
        extraData: { operazione: "submit" },
        display: true
      }
    }else{
      this.alertData =
      {
        content: "generic.compileRequired",
        title: "generic.warning",
        isConfirm: false,
        typ: "info",
        extraData: {},
        display: true
      }
    }
  }

  public onCanceled(): void 
  {
    if (this.allegati != null && this.allegati.length > 0 &&
        this.destinatari != null && this.destinatari.length > 0)
    {
      this.alertData =
      {
        content: "generic.warn-lose-all",
        title: "generic.attenzione",
        isConfirm: true,
        typ: "warning",
        extraData: { operazione: "back" },
        display: true
      }
    }
    else this.canceled.emit(true);
  }

  private doSubmit(): void
  {
    let obj: UlterioreDocumentazioneBean = {
      notifica: this.destinatari,
      enti: this.selectedDefault,
      allegati: this.allegati,
      direzione: true,
      idFascicolo: this.idFascicolo
    }
    this.complete.emit({doc: obj, files: this.files});
  }

  public callbackAlert(event: any): void
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
          case 'back':
            this.canceled.emit(true);
            break;
          case 'submit':
            this.doSubmit();
            break;
          default:
            break;
        }
      }
    }
    this.alertData.display = false;
  }

  get destinatariValid(): boolean 
  { 
    return this.destinatari.length > 0 && this.destinatari.some(s => s.tipo === 'TO'); 
  }


}

