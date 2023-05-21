import { Ente } from './../../../../shared/models/models';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {  Destinatario, StatoEnum, Fascicolo } from 'src/app/shared/models/models';

@Component({
  selector: 'app-destinatari-table',
  templateUrl: './destinatari-table.component.html',
  styleUrls: ['./destinatari-table.component.scss']
})
export class DestinatariTableComponent implements OnInit {
  @Input() fascicolo: Fascicolo;
  @Input() tableData: Ente[];
  @Input() isUlteriori: boolean = false;
  @Input() disabled: boolean = false;
  @Output() deleteDestinatarioEmitter: EventEmitter<number> = new EventEmitter<number>();
  statoEnum = StatoEnum;
  constructor() { }

  ngOnInit() {

  }

  delete(idDestinatario: number) {
      this.deleteDestinatarioEmitter.emit(idDestinatario);
  }
}
