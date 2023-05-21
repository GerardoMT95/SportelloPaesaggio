import { Component, Input, OnInit, HostListener } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { CONST } from 'src/shared/constants';
import * as registrationStatus from 'src/shared/models/registration-status';
import { InformazioniDTO } from './../model/entity/info.models';
import { LineaTemporaleDTO } from './../model/entity/informazioni.models';
import { LocalizzazioneTabDTO } from '../model/entity/localizzazione.models';
import { fromWidthToSize } from '../functions/tableSizeHandler';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})

export class TimelineComponent implements OnInit
{
  @Input() data: LineaTemporaleDTO;
  @Input() stato: string;
  @Input() codice: string;
  @Input() dettaglioFascicolo: InformazioniDTO;
  @Input() localizzazione: LocalizzazioneTabDTO;

  public activeIds: string[] = ['dettaglio'];//default aperto
  /* public comuni: string[] = []; */
  descrTipoProcedimento$: Observable<string>;

  public isMedium: boolean = true;

  constructor(private autPaesSvc: AutorizzazioniPaesaggisticheService) { }

  ngOnInit()
  {
    /* this.getComuni(); */
    this.isMedium = ['medium', 'large'].includes(fromWidthToSize(window.innerWidth));
    this.descrTipoProcedimento$ = this.autPaesSvc.getTipiProcedimento().pipe(map(tipi =>
    {
      let index = tipi.findIndex(el => { return el.value == this.data.tipoProcedimento });
      if (index >= 0)
      {
        return tipi[index].label;
      }
      else
      {
        return this.data.tipoProcedimento;
      }
    }));
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any)
  {
    this.isMedium = ['medium', 'large'].includes(fromWidthToSize(window.innerWidth));
  }

  getColor(nome: string)
  {
    const regColor = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, registrationStatus.StatoFascicolo[nome], 'color', 'enumVal');
    return regColor;
  }

  getStatusLabel(nome: string)
  {
    const regLabel = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, registrationStatus.StatoFascicolo[nome], 'label', 'enumVal');
    return regLabel;
  }

  get comuni(): string[]
  {
    let comuni: string[] = [];
    if (this.localizzazione && this.localizzazione.localizzazioneComuni)
      this.localizzazione.localizzazioneComuni.forEach(loc => comuni.push(loc.comune));
    return comuni;
  }

  protocolloFiltered(value:string){
    if(value == null || value.includes("??") || value.includes("DUMMY")){
      return ""
    }  else{
      return value;
    }
  }

  /* getComuni()
  {

    if (this.dettaglioFascicolo.localizzazione['localizzazioneComuni'].length > 0)
    {
      for (let i = 0; i < this.dettaglioFascicolo.localizzazione['localizzazioneComuni'].length; i++)
      {
        this.comuni[i] = this.dettaglioFascicolo.localizzazione['localizzazioneComuni'][i]['comune'];
        // console.log("valore: ", this.dettaglioFascicolo.localizzazione['localizzazioneComuni'][i]['comune'])
      }
    }
  } */

}
