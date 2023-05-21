import { AttivitaDaEspletareEnum } from 'src/app/shared/models/models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TableConfig } from './../../../../core/models/header.model';
import { IntegrazioneDocumentale } from './../../../../shared/models/models';

@Component({
  selector: 'app-riepilogo-integrazione-documentale',
  templateUrl: './riepilogo-integrazione-documentale.component.html',
  styleUrls: ['./riepilogo-integrazione-documentale.component.scss']
})
export class RiepilogoIntegrazioneDocumentaleComponent
{
  @Input("integrazioni") integrazioni: IntegrazioneDocumentale[] = [];
  @Input("statoPratica") statoPratica: AttivitaDaEspletareEnum;
  @Input("currentUserOwner") currentUserOwner: boolean;
  @Output("nuovaIntegrazione") newEmitter: EventEmitter<void> = new EventEmitter<void>();
  @Output("visualizzaDettaglio") viewEmitter: EventEmitter<IntegrazioneDocumentale> = new EventEmitter<IntegrazioneDocumentale>();
  @Output("cancellaIntegrazione") cancellaEmitter: EventEmitter<IntegrazioneDocumentale> = new EventEmitter<IntegrazioneDocumentale>();
  @Output("downloadRiepilogo") downloadEmitter: EventEmitter<IntegrazioneDocumentale> = new EventEmitter<IntegrazioneDocumentale>();

  public tableHeader: TableConfig[] =
  [
    {header: "integrazione.descrizione", field: "descrizione"},
    {header: "integrazione.data_richiesta", field: "dataCreazione"},
    {header: "integrazione.note", field: "note"},
    {header: "integrazione.data_caricamento", field: "dataCaricamento"},
    {header: "integrazione.stato", field: "stato"},
    {header: "integrazione.suRichiesta", field: "richiestaIntegrazione"},
    {header: "", field: "action", width: 10}
  ]

  constructor() { }

  public nuovaIntegrazione(): void { this.newEmitter.emit(); }
  public viewDetails(row: IntegrazioneDocumentale): void { this.viewEmitter.emit(row); }
  public cancellaRiga(row: IntegrazioneDocumentale): void { this.cancellaEmitter.emit(row); }
  public downloadRiepilogo(row: IntegrazioneDocumentale): void { this.downloadEmitter.emit(row); }

  public get nuovaIntegrazioneEnabled(): boolean 
  {
    let statiValidi = [AttivitaDaEspletareEnum.IN_ATTESA_DI_PROTOCOLLAZIONE, 
                       AttivitaDaEspletareEnum.IN_PREISTRUTTORIA,
                       //AttivitaDaEspletareEnum.IN_LAVORAZIONE
                      ];
    let enabled: boolean = statiValidi.includes(this.statoPratica) && this.currentUserOwner;
    if(this.integrazioni && this.integrazioni.length > 0)
      enabled =  enabled && !this.integrazioni.some(i => i.dataCaricamento === null)
    return enabled; 
  }

}
