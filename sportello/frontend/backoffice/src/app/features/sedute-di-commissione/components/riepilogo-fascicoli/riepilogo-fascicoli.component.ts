import { SeduteDiCommissioneConfig } from './../../configuration/seduteDICommissione.config';
import { TableConfig } from 'src/app/core/models/header.model';
import { Component, OnInit, Input } from '@angular/core';
import { Fascicolo } from 'src/app/shared/models/models';

@Component({
  selector: 'app-riepilogo-fascicoli',
  templateUrl: './riepilogo-fascicoli.component.html',
  styleUrls: ['./riepilogo-fascicoli.component.scss']
})
export class RiepilogoFascicoliComponent implements OnInit
{
  @Input("fascicoli") fascicoli: Fascicolo[];

  constructor() { }

  ngOnInit()
  {
  }

  get tableHeaders(): TableConfig[] { return SeduteDiCommissioneConfig.tableHeadersDetails; }

}
