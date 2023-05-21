import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { Fascicolo } from 'src/app/shared/models/models';
import { Istanza } from './../../../../shared/models/models';

@Component({
  selector: 'app-sezioni',
  templateUrl: './sezioni.component.html',
  styleUrls: ['./sezioni.component.scss']
})
export class SezioniComponent implements OnInit {

  @Input() fascicolo: Fascicolo;
  @Output() navigateChange: EventEmitter<string> = new EventEmitter<string>();

  sezioni: any[];

  constructor() {}

  ngOnInit() {
    this.sezioni = [
      { sezioni: 'Istanza', value: 'istanza', valid: this.fascicolo.istanza?this.fascicolo.istanza['valida']: false },
      { sezioni: 'Scheda Tecnica', value: 'scheda', valid: this.fascicolo.schedaTecnica?this.fascicolo.schedaTecnica['valida']: false },
      { sezioni: 'Allegati', value: 'allegati', valid: this.fascicolo.allegati?this.fascicolo.allegati['valida']: false }
    ];
  }

  navigate(event: 'istanza' | 'scheda' | 'allegati') {
    this.navigateChange.emit(event);
  }
}
