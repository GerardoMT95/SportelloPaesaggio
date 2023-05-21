import { StatoParere } from './../../../../shared/models/models';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-allega-parere',
  templateUrl: './allega-parere.component.html',
  styleUrls: ['./allega-parere.component.scss']
})
export class AllegaParereComponent
{
  @Input("disabled") isDisabled: boolean;
  @Output("allegaParere") creationOpinionEmitter: EventEmitter<any> = new EventEmitter<any>();

  constructor(private shared: DataService) { }

  public navigateToCreateOpinion(): void { this.creationOpinionEmitter.emit(); }

  private get stato(): StatoParere { return this.shared.fascicolo.statoParereSoprintendenza; }
  get emptyParere(): boolean { return this.stato === "PARERE_NON_ALLEGATO"; }
  get parereNonPrevisto(): boolean { return this.stato === "PARERE_NON_PREVISTO"; }
  get label(): string 
  { 
    return this.stato === "PARERE_IN_BOZZA_ENTE" ? "Parere in carico all'ente delegato di riferimento"
                                                 : "Parere in carico alla soprintendenza di riferimento"; 
  }
}
