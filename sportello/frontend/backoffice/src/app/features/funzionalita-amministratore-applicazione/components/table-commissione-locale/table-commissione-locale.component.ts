import { CommissioneLocaleConst } from './../../configurations/commissione-locale-cong';
import { TableConfig } from './../../../../core/models/header.model';
import { Paging } from './../../../../shared/components/rows-number-handler/rows-number-handler.component';
import { CommissioneLocaleOrganizzazione } from './../../models/admin-functions.models';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-table-commissione-locale',
  templateUrl: './table-commissione-locale.component.html',
  styleUrls: ['./table-commissione-locale.component.scss']
})
export class TableCommissioneLocaleComponent
{
  @Input("commissioni") data: Array<CommissioneLocaleOrganizzazione> = [];
  @Input("totalRecords") totalRecords: number = 0;
  @Input("page") set setPage(page: number) { this.paging.page  = page; }
  @Input("rows") set setRows(rows: number) { this.paging.limit = rows; }

  @Output("sortByOrPaging") tablChangeEvt: EventEmitter<Paging> = new EventEmitter();
  @Output("openEdit") openEditEvt: EventEmitter<CommissioneLocaleOrganizzazione> = new EventEmitter();
  @Output("showMembers") showMembersEvent: EventEmitter<CommissioneLocaleOrganizzazione> = new EventEmitter();

  public header: TableConfig[] = CommissioneLocaleConst.tableHeader; 
  public paging: Paging        = {page: 1, limit: 5};

  constructor() { }

  public pagingChange(event: any): void { this.tablChangeEvt.emit(event); }
  public openEdit(entity: CommissioneLocaleOrganizzazione): void { this.openEditEvt.emit(entity); }
  public showMembers(entity: CommissioneLocaleOrganizzazione): void { this.showMembersEvent.emit(entity); }
}
