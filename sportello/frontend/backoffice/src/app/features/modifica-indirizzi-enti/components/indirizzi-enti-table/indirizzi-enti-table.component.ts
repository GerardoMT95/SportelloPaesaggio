import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { IndirizziEnti, TipologiaEnte} from 'src/app/shared/models/models';

@Component({
  selector: 'app-indirizzi-enti-table',
  templateUrl: './indirizzi-enti-table.component.html',
  styleUrls: ['./indirizzi-enti-table.component.scss']
})
export class IndirizziEntiTableComponent implements OnInit {
  @Input() indirizziEntiTableHeaders: TableConfig[] = [];
  @Input() tableData: IndirizziEnti[];
  @Output() navigateChange: EventEmitter<any> = new EventEmitter<any>();
  tipologiaEnum = TipologiaEnte;
  constructor() { }

  ngOnInit() {
  }

  navigate(id: string) {
    this.navigateChange.emit(id);
  }
}
