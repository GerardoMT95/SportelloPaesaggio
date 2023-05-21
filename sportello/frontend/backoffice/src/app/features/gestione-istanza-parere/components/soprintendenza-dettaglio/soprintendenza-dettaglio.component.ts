import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { EsitoParereOption } from './../../gestione-istanza-parere-config';

@Component({
  selector: 'app-soprintendenza-dettaglio',
  templateUrl: './soprintendenza-dettaglio.component.html',
  styleUrls: ['./soprintendenza-dettaglio.component.scss']
})
export class SoprintendenzaDettaglioComponent
{
  @Input("form") form: FormGroup;
  @Input("validation") validation: boolean;
  @Output("dettaglioEmitter") dettaglioEmitter: EventEmitter<void> = new EventEmitter();

  constructor() { }
  public updateDettaglioField(): void { this.dettaglioEmitter.emit(); }
  get esitoList(): SelectItem[] { return EsitoParereOption; }

}
