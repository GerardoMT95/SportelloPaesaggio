import { SimplePratica } from './../../../../shared/models/models';
import { TableConfig } from 'src/app/core/models/header.model';
import { Component, OnInit, Input } from '@angular/core';
import { Fascicolo } from 'src/app/shared/models/models';

@Component({
  selector: 'app-tabella-fascicoli-verbale',
  templateUrl: './tabella-fascicoli-verbale.component.html',
  styleUrls: ['./tabella-fascicoli-verbale.component.scss']
})
export class TabellaFascicoliVerbaleComponent implements OnInit
{
  @Input("data") fascicoli: SimplePratica[] = [];
  @Input("header") header: TableConfig[] = this.defaultHeader();

  constructor() { }

  ngOnInit()
  {
  }

  private defaultHeader(): TableConfig[]
  {
    return [
      {
        field: "codicePraticaAppptr",
        header: "fascicolo.codiceFascicolo",
        width: 25
      },
      {
        field: "oggetto",
        header: "fascicolo.oggetto",
        width: 75
      }
    ]
  }

}
