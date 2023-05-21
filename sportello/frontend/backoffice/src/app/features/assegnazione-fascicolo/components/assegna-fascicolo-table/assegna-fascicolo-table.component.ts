import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { translateTypeProcedimento } from 'src/app/features/gestione-istanza-comunicazioni/generic-function/generic-function';
import { Assegnamento, StatoEnum, StatoGroup } from 'src/app/shared/models/models';
import { ProcessingStatus } from 'src/app/shared/util/UtilProcessingStatus';
import { TableConfig } from './../../../../core/models/header.model';
import { GroupType, TabellaAssegnamentoFascicolo } from './../../../../shared/models/models';
import { UserService } from './../../../../shared/services/user.service';
import { AssegnaFascicoloConfiguration } from './../../configuration/assegnazione-fascicolo.configuration';
import { TableSearch } from './../../models/assegna-fascicolo.models';

@Component({
  selector: 'app-assegna-fascicolo-table',
  templateUrl: './assegna-fascicolo-table.component.html',
  styleUrls: ['./assegna-fascicolo-table.component.scss']
})
export class AssegnaFascicoloTableComponent implements OnInit
{
  @Input("data")       fascicoli         : TabellaAssegnamentoFascicolo[];
  @Input("totalItems") totalObject       : number;
  @Input("assigned")   fascicoliAssegnati: boolean;

  @Output("pagingOrOrderBy")      change    : EventEmitter<TableSearch>;
  @Output("mostraStorico")        show      : EventEmitter<TabellaAssegnamentoFascicolo>;
  @Output("assegnaFascicolo")     assign    : EventEmitter<{id: string, assegnazioneAttuale?: Assegnamento}>;
  @Output("eliminaAssegnazione")  disassegna: EventEmitter<Assegnamento>;

  public tableHeaders: TableConfig[]; 
  public tableSearch : TableSearch = new TableSearch();
  public ready       : boolean = false;

  constructor(private userService: UserService) 
  {
    this.change     = new EventEmitter<TableSearch>();
    this.show       = new EventEmitter<TabellaAssegnamentoFascicolo>();
    this.assign     = new EventEmitter<{ id: string, assegnazioneAttuale?: Assegnamento }>();
    this.disassegna = new EventEmitter<Assegnamento>();
    this.tableSearch.page  = 1;
    this.tableSearch.limit = 5;
  }

  ngOnInit()
  {
    if(this.fascicoliAssegnati)
    {
      this.tableHeaders = AssegnaFascicoloConfiguration.tableHeadersA;
      if(this.userService.groupType !== GroupType.EnteDelegato)
      {
        this.tableHeaders.forEach((item, index, array) =>
        {
          if (item.field === "denominazioneRup")
            array.splice(index, 1);
        });
      }      
    }
    else
      this.tableHeaders = AssegnaFascicoloConfiguration.tableHeadersNA;
    this.ready = true;
  }

  public processingStatus(status: StatoEnum): StatoGroup
  {
    let tmp = ProcessingStatus.getStatus(status);
    return tmp;
  }

  public getTipoProcedimentoFromMetadata(field)
  {
    const id = field - 1;
    return translateTypeProcedimento(field);
  }

  public sortElements(event: any): void 
  { 
    console.log(event); 
  }
  public pagingChanges(event: any): void 
  {
    if (event.page) this.tableSearch.page = event.page;
    if (event.limit) this.tableSearch.limit = event.limit; 
    this.change.emit(this.tableSearch); 
  }
  public storicoAsegnazioni(fascicolo: TabellaAssegnamentoFascicolo): void { this.show.emit(fascicolo); }
  public assegna(row: TabellaAssegnamentoFascicolo): void { row.idFascicolo = row.id; this.assign.emit({ id: row.id, assegnazioneAttuale: row }); }
  public rimuovi(row: TabellaAssegnamentoFascicolo): void { row.idFascicolo = row.id; this.disassegna.emit(row); }

}
