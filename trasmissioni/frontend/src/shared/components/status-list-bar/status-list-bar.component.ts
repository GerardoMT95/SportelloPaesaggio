import { Component, Input, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { CONST } from 'src/shared/constants';
import { PlainTypeStringId } from '../../models/plain-type-string-id.model';
import { AcceleratoriDTO } from './../../../app/components/model/entity/fascicolo.models';

@Component({
  selector: 'app-status-list-bar',
  templateUrl: './status-list-bar.component.html',
  styleUrls: ['./status-list-bar.component.scss']
})

export class StatusListBarComponent implements OnInit
{
  @Input() registrationStatusSummaries: PlainTypeStringId[];
  isPublic: boolean=false;

  public list: SelectItem[] = [];
  constructor(private autPaesService: AutorizzazioniPaesaggisticheService) { }

  ngOnInit()
  {
    this.autPaesService.getForAccelerators(this.isPublic).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload)
      {
        Object.keys(result.payload).forEach(key =>
        { 
          if (!this.isPublic || !["nCancelled", "nWorking"].includes(key))
            this.list.push({label: AcceleratoriDTO.getStatus(key), value: result.payload[key]});
        });
      }
    });
  }

  getColor(nome: string)
  {
    const regColor = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, nome, 'color', 'enumVal');
    return regColor;
  }

  /**
   * 
   * @param nome valore stringa dell'enumerativo registrationStatus
   */
  getName(nome: string)
  {
    return CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, nome, 'label', 'enumVal');
  }
}
