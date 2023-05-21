import { Component, OnInit } from '@angular/core';

import { TableConfig } from 'src/app/core/models/header.model';

@Component({
  selector: 'app-istanza-presentata',
  templateUrl: './istanza-presentata.component.html',
  styleUrls: ['./istanza-presentata.component.scss']
})
export class IstanzaPresentataComponent implements OnInit {
  documentTableHeaders: TableConfig[] = [];
  tableData: any[];
  constructor() { }

  ngOnInit() {
    this.tableData = [{
      nome: 'istanza_APPPTR-1-2018'
    },
    {
      nome: 'dichiarazione_tecnica_APPPTR-1-2018'
    }
    ];
    this.documentTableHeaders = [

      {
        header: 'Descrizione',
        field: 'nome'
      }
    ];
  }

}
