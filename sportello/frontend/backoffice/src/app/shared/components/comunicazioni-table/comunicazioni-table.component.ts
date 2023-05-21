import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { Fascicolo, ComunicazioniTemplate, StatoEnum } from '../../models/models';

/*@Component({
  selector: 'app-comunicazioni-table',
  templateUrl: './comunicazioni-table.component.html',
  styleUrls: ['./comunicazioni-table.component.scss']
})*/
export class TO_REMOVE_ComunicazioniTableComponent implements OnInit {
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
    //console.log('TABLE DATA', this.tableData);
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
