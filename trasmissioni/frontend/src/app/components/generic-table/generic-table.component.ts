import {
  Component,
  EventEmitter,
  Input,
  Output,
  TemplateRef,
} from "@angular/core";
import { TableConfig } from "../model/entity/fascicolo.models";
import { IButton } from "../model/prompt-dialog.model";

@Component({
  selector: "app-generic-table",
  templateUrl: "./generic-table.component.html",
  styleUrls: ["./generic-table.component.scss"],
})
export class GenericTableComponent {
  @Input("header") tableHeader: TableConfig[] = [];
  @Input("items") items: any[] = [];
  @Input("paginator") paginator: boolean = true;
  @Input("page") page: number = 1;
  @Input("totalItems") totalRecords: number = 0;
  @Input("rowsPerPage") rows: number = 5;

  @Input("disableInfoButton") disableInfoButton: boolean = false;
  @Input("disableEditButton") disableEditButton: boolean = false;
  @Input("disableDeleteButton") disableDeleteButton: boolean = false;
  @Input("disableSelectButton") disableSelectButton: boolean = false;
  @Input("customButton") customButton: IButton;

  @Input("customElement") customElement: TemplateRef<any>;

  @Output("onInfo") onInfo: EventEmitter<any> = new EventEmitter<any>();
  @Output("onRemove") onRemove: EventEmitter<any> = new EventEmitter<any>();
  @Output("onEdit") onEdit: EventEmitter<any> = new EventEmitter<any>();
  @Output("onSelect") onSelect: EventEmitter<any> = new EventEmitter<any>();
  @Output("onPaging") onPaging: EventEmitter<any> = new EventEmitter<any>();
  @Output("onCustomEvent") onCustomEvent: EventEmitter<any> =
    new EventEmitter<any>();

  public modifyIndex: number = null;
  public inEditing: boolean = false;

  constructor() {}

  public emitInfoEvent(row: any): void {
    this.onInfo.emit(row);
  }
  public emitRemoveEvent(row: any): void {
    this.onRemove.emit(row);
  }
  public emitEditEvent(row: any): void {
    this.onEdit.emit(row);
  }
  public emitSelectEvent(row: any): void {
    this.onSelect.emit(row);
  }
  public emitPagingEvent(event: any): void {
    this.rows = event.limit;
    this.onPaging.emit({ page: this.page, limit: this.rows });
  }
  public pageChangedAction(page: number): void {
    this.page = page;
    this.onPaging.emit({ page: this.page, limit: this.rows });
  }

  public customEvent(row: any): void {
    this.onCustomEvent.emit(
      //{btn_id: this.customButton.id, row}
      { btn_id: -1, row }
    );
  }

  get first(): number {
    return (this.page - 1) * this.rows;
  }
  get readonly(): boolean {
    return (
      this.disableEditButton &&
      this.disableDeleteButton &&
      this.disableSelectButton &&
      !this.customButton
    );
  }

  public processingOptionWithColor(
    keyVal: string,
    header: TableConfig
  ): { label: string; color: string } {
    return GenericTableComponent.optionWithColor(
      keyVal,
      header,
      this.tableHeader
    );
    /*
    let headerData=this.tableHeader.find(el=>el.field==header.field      );
    if(!headerData) 
      return {label:'',color:''};
    let dataAttribute=headerData.optionSelect.find(selOpt=>selOpt.value==keyVal);
    if(!dataAttribute)
      return {label:'',color:''};
    return {label:dataAttribute.description,color:dataAttribute.linked};
    */
  }

  static optionWithColor(
    keyVal: string,
    header: TableConfig,
    headers: TableConfig[]
  ): { label: string; color: string } {
    let headerData = headers.find((el) => el.field == header.field);
    if (!headerData) return { label: "", color: "" };
    let dataAttribute = headerData.optionSelect.find(
      (selOpt) => selOpt.value == keyVal
    );
    if (!dataAttribute) return { label: "", color: "" };
    return { label: dataAttribute.description, color: dataAttribute.linked };
  }
}
