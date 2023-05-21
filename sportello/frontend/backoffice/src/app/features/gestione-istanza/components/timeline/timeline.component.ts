import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Observable } from 'rxjs';
import { LineaTemporaleDTO } from '../../../../shared/models/models';
import { StoricoStato } from './../../../../shared/models/models';


@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit, OnChanges
{
  @Input("data") timeline: LineaTemporaleDTO;
  private tmArray: StoricoStato[];
  public activeIds: string[] = [];
  descrTipoProcedimento$: Observable<string>;

  constructor() { }

  ngOnInit()
  {
    this.descrTipoProcedimento$ = new Observable<string>();
    this.tmArray = this.transformTimelineInArray();
    console.log("Timeline: ", this.tmArray);
  }

  ngOnChanges(changes: SimpleChanges)
  {
    if (changes.timeline)
      this.tmArray = this.transformTimelineInArray();
  }

  private transformTimelineInArray(): StoricoStato[]
  {
    let tArray: StoricoStato[] = [];
    Object.keys(this.timeline).forEach(key =>
    {
      if (this.timeline[key])
        tArray.push({ stato: key, data: new Date(this.timeline[key]) });
    })
    tArray = tArray.sort((a1, a2) =>
    {
      let toreturn = 1;
      if (a1.data === a2.data)
      {
        if (this.fromKeyToValue(a1.stato) <= this.fromKeyToValue(a2.stato))
          toreturn = 1;
        else
          toreturn = -1;
      }
      else if (a1.data < a2.data)
      {
        toreturn = -1;
      }
      return toreturn;
    });
    return tArray;
  }

  private fromKeyToValue(stato: string): number
  {
    let value = 0;
    switch (stato)
    {
      case "dataPresentazione":
        value = 1;
        break;
      case "dataInizioLavorazione":
        value = 2;
        break;
      case "dataTrasmissioneVerbale":
        value = 3;
        break;
      case "dataTrasmissioneRelazione":
        value = 4;
        break;
      case "dataTrasmissioneParere":
        value = 5;
        break;
      case "dataTrasmissioneProvvedimento":
        break;
    }
    return value;
  }
  get timelineArray(): any[] { return this.tmArray; }
}