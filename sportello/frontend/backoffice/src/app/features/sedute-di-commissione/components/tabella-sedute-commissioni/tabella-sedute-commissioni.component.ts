import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableConfig } from '../../../../core/models/header.model';
import { SedutaDiCommissione } from '../../../../shared/models/models';
import { SeduteDiCommissioneConfig } from '../../configuration/seduteDICommissione.config';
import { BasicSearch, EventType, OperationEventType } from '../../models/seduta.models';

@Component({
  selector: 'app-tabella-sedute-commissioni',
  templateUrl: './tabella-sedute-commissioni.component.html',
  styleUrls: ['./tabella-sedute-commissioni.component.scss']
})
export class TabellaSeduteCommissioniComponent
{
  @Input("isRup") isRup: boolean = false;
  @Input("seduta") sedute: Array<SedutaDiCommissione> = [];
  @Input("totalRecords") totalRecords: number = 0;
  @Output("nuovaSeduta") nuovaSeduta: EventEmitter<void> = new EventEmitter<void>();
  @Output("onTableChange") tableChange: EventEmitter<BasicSearch> = new EventEmitter<BasicSearch>();
  @Output("operationSelected") operation: EventEmitter<OperationEventType> = new EventEmitter<OperationEventType>();

  public rowsOnPage: number = 5;
  public page: number = 1;

  constructor() { }

  public eventDispatcher(row: SedutaDiCommissione, eventType: EventType): void
  {
    let eventContainer: OperationEventType =
    {
      selected: row,
      operation: eventType
    }
    this.operation.emit(eventContainer);
  }

  public pagingChanges(event: any): void
  {
    if (event.page) this.page = event.page;
    if (event.limit) this.rowsOnPage = event.limit;
    this.sortByOrPaging();
  }

  public sortEvt(event: any): void { this.sortByOrPaging({ sortField: event.field, sortOrder: event.order}); }

  public sortByOrPaging(event?: any): void
  {
    let container: BasicSearch = new BasicSearch();
    //container.page = event.first === 0 ? 1 : event.first / this.rowsOnPage;
    container.page = this.page;
    container.limit = this.rowsOnPage;
    if (event && event.sortField)
      container.sortBy = event.sortField;
    if (event && event.sortOrder)
      container.sortType = event.sortOrder === 1 ? "ASC" : "DESC";
    this.tableChange.emit(container);
  }

  public editSession(): void { this.nuovaSeduta.emit(); }
  get tableHeaders(): TableConfig[] { return SeduteDiCommissioneConfig.tableHeaders; }
}
