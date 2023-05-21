import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { Fascicolo } from 'src/app/shared/models/models';


@Component({
  selector: 'app-sezioni',
  templateUrl: './sezioni.component.html',
  styleUrls: ['./sezioni.component.scss']
})
export class SezioniComponent implements OnInit 
{

  @Input("fascicolo") set _(f: Fascicolo) {this.buildSezioni(); this.fascicolo = f;}
  @Input() istanzaValidato: boolean = false;
  @Input() schedaTecnicaValidato: boolean = false;
  @Input() allegatiValidato: boolean = false;
  @Input() richiedenteValidato: boolean = false;
  @Output() navigateChange: EventEmitter<string> = new EventEmitter<string>();

  public fascicolo: Fascicolo;

  sezioni: any[];

  constructor() {}

  ngOnInit() 
  {
    this.buildSezioni();
  }

  ngOnChanges(changes: SimpleChanges): void 
  {
    this.buildSezioni();
  }

  private buildSezioni(): void
  {
    this.sezioni = 
    [
      { sezioni: 'Istanza', value: 'istanza', valid: this.istanzaValidato, canEdit: true },
      { sezioni: 'Scheda Tecnica', value: 'scheda', valid: this.schedaTecnicaValidato, canEdit: this.fascicolo && this.fascicolo.validatoRichiedente },
      { sezioni: 'Allegati', value: 'allegati', valid: this.allegatiValidato, canEdit: this.fascicolo && this.fascicolo.validatoRichiedente }
    ];
  }

  navigate(event: 'istanza' | 'scheda' | 'allegati') { this.navigateChange.emit(event); }
}
