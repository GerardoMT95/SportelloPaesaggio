import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { ComunicazioniTemplate, Fascicolo, StatoEnum } from 'src/app/shared/models/models';

@Component({
  selector: 'app-comunicazioni-relazione-ente-table',
  templateUrl: './comunicazioni-relazione-ente-table.component.html',
  styleUrls: ['./comunicazioni-relazione-ente-table.component.scss']
})
export class ComunicazioniRelazioneEnteTableComponent implements OnInit {
  @Input() tableData: ComunicazioniTemplate[];
  @Input() tableHeaders: TableConfig[];
  @Input() fascicolo: Fascicolo;
  @Input() disableButton: boolean = false;
  @Output() editEmitter: EventEmitter<{index:number, mode:string}> = new EventEmitter<{index:number, mode:string}>();
  @Output() viewEmitter: EventEmitter<{index:number, mode:string}> = new EventEmitter<{index:number, mode:string}>();
  @Output() emitDeletion: EventEmitter<number> = new EventEmitter<number>();
  comunicazioni: any[] = [];
  statoEnum = StatoEnum;
  constructor() { }

  ngOnInit() {
    console.log('TABLE DATA', this.tableData);
  }
  deleteFile(i) {
    this.emitDeletion.emit(i);
  }
  edit(i) {
    this.editEmitter.emit({index : i, mode: 'EDIT'});
  }
  view(i) {
    this.viewEmitter.emit({index : i, mode: 'VIEW'});
  }
}
