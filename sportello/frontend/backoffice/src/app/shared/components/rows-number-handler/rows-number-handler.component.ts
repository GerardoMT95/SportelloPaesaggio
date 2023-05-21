import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';
import { SelectItem } from 'primeng/primeng';
import { CONST } from '../../constants';

@Component({
  selector: 'app-rows-number-handler',
  templateUrl: './rows-number-handler.component.html',
  styleUrls: ['./rows-number-handler.component.scss']
})
export class RowsNumberHandlerComponent
{
  // PARAMETRI TABELLA TEMPLAETE COMUNICAZIONI
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;

  @Input("page") set pagina(pag: number) { this.page = pag; } 
  @Input("itemsTotal") itemsTotal: number = 0;
  @Input("totalRecords") totalRecords: number = 0;
  @Input("init-rows") set initRows(init: number) { this.rowsOnPage = init; } 
  @Input("customElement") customElement: TemplateRef<any>;

  @Output("change") paginationEmitter: EventEmitter<Paging> = new EventEmitter<Paging>();

  constructor() { }

  /**
   * @description Emetto l'evento per segnalare un cambiamento nella paginazione:
   * il numero di righe per pagina da visualizzare è cambiato.
   * @param rowsOnPage 
   */
  public changeRows(rowsOnPage: number): void
  {
    this.rowsOnPage = rowsOnPage;
    this.page = 1;
    this.paginationEmitter.emit({ page: this.page, limit: this.rowsOnPage });
  }
}
export type Paging = { page: number, limit: number };
