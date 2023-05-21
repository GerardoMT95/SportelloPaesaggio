import { CONST } from './../../../../../shared/constants';
import { AllegatoCustomDTO } from 'src/app/components/model/entity/allegato.models';
import { UlterioreDocumentazione, AllegatoUD } from './../../../model/entity/allegato.models';
import { DestinatarioComunicazioneDTO, CorrispondenzaDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable } from 'rxjs';
import { TableConfig } from 'src/app/components/model/entity/fascicolo.models';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { ShowAlertService } from 'src/app/services/show-alert.service';

@Component({
  selector: 'app-aggiungi-ulteriore-documentazione-multi',
  templateUrl: './aggiungi-ulteriore-documentazione-multi.component.html',
  styleUrls: ['./aggiungi-ulteriore-documentazione-multi.component.scss']
})
export class AggiungiUlterioreDocumentazioneMultiComponent implements OnInit
{
  @Input("idFascicolo") idFascicolo: number;
  @Input("entiOptions") entiOptions$: Observable<SelectItem[]>;
  @Output("onComplete") complete: EventEmitter<{doc: UlterioreDocumentazione, files: File[]}> = new EventEmitter();
  @Output("onCanceled") canceled: EventEmitter<boolean> = new EventEmitter();

  public destinatari: DestinatarioComunicazioneDTO[] = [];
  public inviato: boolean = false;
  public destinatariFissi: any;
  public gruppoScelto: string;
  public tipoGruppoScelto: string;
  public enti: any[] = [];

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
              private autpaeSvc: AutorizzazioniPaesaggisticheService) { }

  ngOnInit()
  {
    this.autpaeSvc.getTemplateCorrispondenza(this.idFascicolo).subscribe(res =>
    {
      if (res.codiceEsito == CONST.OK)
      {
        this.template.id = res.payload.id;
        this.template.oggetto = res.payload.oggetto;
        this.template.testo = res.payload.testo;
        this.template.destinatari = res.payload.destinatari;
        this.setDestinatariFissi();
      }
    });

    this.gruppoScelto = this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    this.tipoGruppoScelto = this.gruppoScelto.split("_")[0] + "_";
    this.inviato = false;
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

  public onSubmit(): void
  {
    this.inviato = true;
    if (this.destinatari.length > 0)
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
    let obj: UlterioreDocumentazione = {
      notifica: this.destinatari,
      enti: [this.gruppoScelto],
      allegati: this.allegati,
      direzione: true,
      idFascicolo: this.idFascicolo
    }
    this.complete.emit({doc: obj, files: this.files});
  }

  private valoreDefaultEnti(): string
  {
    let template: SelectItem[] = CONST.entiTemplate;
    let defaultValue: SelectItem = template.find(elem => elem.value === this.tipoGruppoScelto);
    let valoreDefault: string;
    if (defaultValue)
    {
      valoreDefault = defaultValue.value;
    }
    return valoreDefault;
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
