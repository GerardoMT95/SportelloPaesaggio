import { DestinatarioComunicazioneDTO, CorrispondenzaDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { CONST } from 'src/shared/constants';
import { LocalSessionServiceService } from './../../../../services/local-session-service.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, FormArray } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable } from 'rxjs';
import { AllegatoUlterioreDocumentazione } from './../../../model/entity/allegato.models';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { checkFileSizeTypeFn } from 'src/app/components/functions/genericFunctions';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-aggiungi-ulteriore-documentazione',
  templateUrl: './aggiungi-ulteriore-documentazione.component.html',
  styleUrls: ['./aggiungi-ulteriore-documentazione.component.css']
})
export class AggiungiUlterioreDocumentazioneComponent implements OnInit
{
  @Input("idFascicolo") idFascicolo: number;
  @Input("entiOptions") entiOptions$: Observable<SelectItem[]>;
  @Output("onComplete") complete: EventEmitter<AllegatoUlterioreDocumentazione> = new EventEmitter < AllegatoUlterioreDocumentazione>();
  @Output("onCanceled") canceled: EventEmitter<boolean> = new EventEmitter<boolean>();
  
  public form: FormGroup;
  public inviato: boolean = false;
  public gruppoScelto: string;
  public tipoGruppoScelto: string;
  public destinatariFissi:any;
  public template: CorrispondenzaDTO = new CorrispondenzaDTO();
  public const = CONST;
  public destinatari: DestinatarioComunicazioneDTO[] = [];

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
              private lss: LocalSessionServiceService,
              private showAlertDialog:ShowAlertService,
              private translateService:TranslateService,
              private autpaeSvc: AutorizzazioniPaesaggisticheService) { }

  ngOnInit()
  {
    this.autpaeSvc.getTemplateCorrispondenza(this.idFascicolo).subscribe(res =>
    {
        if(res.codiceEsito==CONST.OK){
          //  console.log("res ", res);
          this.template.id = res.payload.id;
          this.template.oggetto = res.payload.oggetto;
          this.template.testo = res.payload.testo;
          this.template.destinatari = res.payload.destinatari;
          this.setDestinatariFissi();
          
        }
      }
    );
  
    this.gruppoScelto = this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    this.tipoGruppoScelto = this.gruppoScelto.split("_")[0] + "_";
    this.inviato = false;
    this.buildForm();
  }

  public setDestinatariFissi(){
    this.destinatariFissi = this.template.destinatari;
  }

  //get formNotifica(): FormArray { return <FormArray>this.form.controls.notifica;}

  private buildForm(): void
  {
    this.form = this.formBuilder.group
    ({
      titolo: new FormControl(null, Validators.required),
      descrizione: new FormControl(null, Validators.required),
      allegato: new FormControl(null, [Validators.required]),
      enti: new FormControl(this.valoreDefaultEnti() ? [this.valoreDefaultEnti()] : [] , Validators.required),
      //notifica: this.formBuilder.array([this.buildSingleEmail()])
    });
  }

  private valoreDefaultEnti(): string {
    let template: SelectItem[] = CONST.entiTemplate;
    let defaultValue: SelectItem = template.find(elem => elem.value === this.tipoGruppoScelto);
    let valoreDefault: string;
    if (defaultValue) {
      valoreDefault = defaultValue.value;
    }
    return valoreDefault;
  }

  public addDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    address.tipo = 'CC';
    if(this.destinatari.length === 0) address.tipo = 'TO';
    if(!this.destinatari.some(s => s.email === address.email))
    {
      this.destinatari.push(address);
      this.form.markAsDirty();
    }
  }

  public removeDestinatario(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if(index != -1)
    {
      this.destinatari.splice(index, 1);
      this.form.markAsDirty();
    }
  }

  public cambiaTipologia(address: DestinatarioComunicazioneDTO): void
  {
    let index: number = this.destinatari.map(m => m.email).indexOf(address.email);
    if(index != -1)
    {
      this.destinatari[index].tipo = address.tipo;
      this.form.markAsDirty();
    }
  }

  public allegaFile(event: any): void
  {
    if (event.files && event.files.length > 0){
      let file=event.files[0];
      let fileCheck=checkFileSizeTypeFn(null,CONST.MAX_50MB);
      let errorValidation=fileCheck(file);
			if(errorValidation){
				this.showAlertDialog.showAlertDialog(true, null, this.translateService.instant(errorValidation));
				return;
			}
      this.form.controls.allegato.setValue(event.files[0]);
    }
  }

  public svuotaAllegato(): void { this.form.controls.allegato.setValue(null); }

  public onSubmit(): void
  {
    this.inviato = true;
    if (this.form.valid && this.destinatari.length > 0)
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

  private doSubmit(): void
  {
    let obj: AllegatoUlterioreDocumentazione = this.form.getRawValue();
    obj.notifica = this.destinatari;
    obj.enti.push(this.gruppoScelto);
    this.complete.emit(obj);
  }

  public onCanceled(): void 
  {
    if(this.form.dirty)
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

  get destinatariValid(): boolean { return this.destinatari.length > 0 && this.destinatari.some(s => s.tipo === 'TO'); }
}
