import { copyOf } from 'src/app/core/functions/generic.utils';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { SedutaDiCommissione } from './../../../../shared/models/models';
import { FascicoloInput } from './../../models/seduta.models';

@Component({
  selector: 'app-nuova-seduta-commissione',
  templateUrl: './nuova-seduta-commissione.component.html',
  styleUrls: ['./nuova-seduta-commissione.component.scss']
})
export class NuovaSedutaCommissioneComponent implements OnInit
{
  @Input("init") seduta: SedutaDiCommissione;
  @Input("fascicoli") fascicoli: SelectItem[];
  @Input("disableButtons") disableButtons: boolean = false;

  @Output("onSave") submit: EventEmitter<SedutaDiCommissione> = new EventEmitter<SedutaDiCommissione>();
  @Output("onCancel") cancel: EventEmitter<void> = new EventEmitter<void>();

  private _form: FormGroup;
  public valida: boolean = false;
  public isDisabled = (arg: FascicoloInput) => { return arg.stato === "ESAMINATO" ? true : false; }

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit()
  {
    this.buildForm();
  }

  private buildForm(): void
  {
    let pratiche = this.seduta && this.seduta.praticheDetails ? this.seduta.praticheDetails.map(m => m.id) : [];
    this._form = this.formBuilder.group({
      id: [this.seduta ? this.seduta.id : null],
      destinatari: [[]],
      nomeSeduta: [this.seduta ? this.seduta.nomeSeduta : null, Validators.required],
      dataSeduta: [this.seduta ? this.seduta.dataSeduta : null, Validators.required],
      pratiche: [pratiche, Validators.required],
      pubblica: [this.seduta ? this.seduta.pubblica : false]
    });
  }

  public addDestinatario(event: DestinatarioComunicazioneDTO): void
  {
    this.destinatari.length === 0 ? event.tipo = "TO" : event.tipo = "CC";
    if(!this.destinatari.map(m => m.email).includes(event.email))
      this.destinatari = [...this.destinatari, event];
  }

  public removeDestinatario(event: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if(index != -1)
      this.destinatari.splice(index, 1);
  }

  public cambiaTipologia(event: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.destinatari[index].tipo = event.tipo;
  }

  public salva(): void 
  {
    this.valida = true;
    if(this.form.valid)
    {
      this.valida = false;
      this.submit.emit(this.form.getRawValue()); 
    } 
  }
  public chiudi(): void { this.cancel.emit(); }  

  get form(): FormGroup { return this._form; }
  get formArray(): FormArray { return <FormArray>this.form.controls.destFormArray }
  get destinatari(): any[] { return this.form.controls.destinatari.value; }
  set destinatari(obj: any[]) { this.form.controls.destinatari.setValue(obj); }
  get listFascicoli(): SelectItem[] 
  {
    let f: SelectItem[] = copyOf(this.fascicoli); 
    if (this.seduta && this.seduta.praticheDetails)
    {
      this.seduta.praticheDetails.forEach(p =>
      {
        if(f.every(e => e.value != p.id))
          f.push({label: p.codicePraticaAppptr, value: p.id});
      });
    }
    return f;
  }
}
