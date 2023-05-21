import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { ProcessingStatus } from 'src/app/shared/util/UtilProcessingStatus';
import { GroupType, LineaTemporaleDTO } from './../../../../shared/models/models';
import { UserService } from './../../../../shared/services/user.service';


@Component({
  selector: 'app-dettaglio-fascicolo',
  templateUrl: './dettaglio-fascicolo.component.html',
  styleUrls: ['./dettaglio-fascicolo.component.scss']
})
export class DettaglioFascicoloComponent implements OnInit, OnChanges
{
  @Input() fascicolo: Fascicolo;

  public tipoProcedimento: string;
  public richiedente: string;
  public funzionario: string;
  public rup: string;
  public codiceTrasmissione: string;
  public statoEnum = StatoEnum;
  public comune: string;
  public groupType = GroupType;
  public currentGroup: GroupType;
  public comuniIntervento: any[] = [];
  //public timeline: LineaTemporaleDTO = new LineaTemporaleDTO();

  constructor(private userService: UserService) { }

  ngOnInit()
  {
    if (this.fascicolo.tipoProcedimento)
    {
      //this.tipoProcedimento = CONST.mappingTipiProcedimento.find((f, index) => (f.value === this.fascicolo.tipoProcedimento) || index == Number(this.fascicolo.tipoProcedimento)).description;
      this.tipoProcedimento = CONST.mappingTipiProcedimento[Number(this.fascicolo.tipoProcedimento) - 1].description;
    }
    this.comune = this.fascicolo.comune ? this.fascicolo.comune : "-";
    this.currentGroup = this.userService.groupType;
    if (this.fascicolo.istanza)
    {
      if (this.fascicolo.istanza.richiedente)
      {
        this.richiedente = this.fascicolo.istanza.richiedente.cognome 
                         + ' ' + this.fascicolo.istanza.richiedente.nome;
      }
      if (this.fascicolo.istanza.localizzazione && this.fascicolo.istanza.localizzazione.localizzazioneComuni)
      {
        this.fascicolo.istanza.localizzazione.localizzazioneComuni.forEach(comuneIntervento => this.comuniIntervento.push(comuneIntervento.comune));
      }
      if (this.fascicolo.denominazioneFunzionario)
      {
        this.funzionario = this.fascicolo.denominazioneFunzionario;
      }
      if (this.fascicolo.denominazioneRup)
      {
        this.rup = this.fascicolo.denominazioneRup;
      }
      if (this.fascicolo.codiceTrasmissione)
      {
        this.codiceTrasmissione = this.fascicolo.codiceTrasmissione;
      }
    } 
    else
    {
      this.richiedente = '-';
    }
    console.log("Fascicolo: ", this.fascicolo);
    console.log("Timeline versione base: ", this.timeline)
    //this.compileTimeline();
  }

  ngOnChanges(changes: SimpleChanges): void
  {
    if(changes["fascicolo"])
    {
      if (this.fascicolo.tipoProcedimento)
      {
        //this.tipoProcedimento = CONST.mappingTipiProcedimento.find((f, index) => (f.value === this.fascicolo.tipoProcedimento) || index == Number(this.fascicolo.tipoProcedimento)).description;
        this.tipoProcedimento = CONST.mappingTipiProcedimento[Number(this.fascicolo.tipoProcedimento) - 1].description;
      }
      //this.compileTimeline();
    }
  }

  get status(): any { return ProcessingStatus.getStatus(this.fascicolo.attivitaDaEspletare) }
  get timeline(): LineaTemporaleDTO
  {
    let timeline = new LineaTemporaleDTO();
    timeline.dataPresentazione = this.fascicolo.dataPresentazione;
    timeline.dataInizioLavorazione = this.fascicolo.dataInizioLavorazione;
    timeline.dataTrasmissioneVerbale = this.fascicolo.dataTrasmissioneVerbale;
    timeline.dataTrasmissioneRelazione = this.fascicolo.dataTrasmissioneRelazione;
    timeline.dataTrasmissioneParere = this.fascicolo.dataTrasmissioneParere;
    timeline.dataTrasmissioneProvvedimento = this.fascicolo.dataTrasmissioneProvvedimentoFinale;
    return timeline;
  }
}
