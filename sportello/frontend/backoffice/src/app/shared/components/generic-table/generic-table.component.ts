import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { CONST } from '../../constants';
import { IButton } from './../../../core/models/dialog.model';

@Component({
  selector: 'app-generic-table',
  templateUrl: './generic-table.component.html',
  styleUrls: ['./generic-table.component.scss']
})
export class GenericTableComponent  {

  @Input("header") tableHeader: TableConfig[]=[];  
  @Input("items") items: any[] = [];
  @Input("paginator") paginator: boolean = true;
  @Input("page") page: number = 1;
  @Input("totalItems") totalRecords: number = 0;
  @Input("rowsPerPage") rows: number = 5;
  
  @Input("disableEditButton") disableEditButton: boolean = false;
  @Input("disableDeleteButton") disableDeleteButton: boolean = false;
  @Input("disableSelectButton") disableSelectButton: boolean = false;
  @Input("customButton") customButton: IButton;
  @Input("sortButtons") sortButtons:boolean=false;
  @Input("enableAddItem") enableAddItem:boolean=false;

  @Input("customElement") customElement: TemplateRef<any>;

  @Output("onRemove") onRemove: EventEmitter<any> = new EventEmitter<any>();
  @Output("onEdit") onEdit: EventEmitter<any> = new EventEmitter<any>();
  @Output("onSelect") onSelect: EventEmitter<any> = new EventEmitter<any>();
  @Output("onPaging") onPaging: EventEmitter<any> = new EventEmitter<any>();
  @Output("onCustomEvent") onCustomEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output("onUpEvent") onUpEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output("onDownEvent") onDownEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output("onAddItem") onAddItem: EventEmitter<any> = new EventEmitter<any>();
  

  public modifyIndex: number = null;
  public inEditing: boolean = false; 
  

  private REGEX_EMAIL = new RegExp(CONST.PATTERN_MAIL);

  
  constructor() { }

  public emitRemoveEvent(row: any): void { this.onRemove.emit(row); }
  public emitEditEvent(row: any): void { this.onEdit.emit(row); }
  public emitSelectEvent(row: any): void { this.onSelect.emit(row); }
  public emitUpEvent(index: any): void { this.onUpEvent.emit(index); }
  public emitDownEvent(index: any): void { this.onDownEvent.emit(index); }
  public emitPagingEvent(event: any): void { 
    this.rows=event.limit;
    this.onPaging.emit({page:this.page,limit:this.rows}); 
  }
  public pageChangedAction(page: number): void { 
    this.page=page;
    this.onPaging.emit(
      {page:this.page,limit:this.rows}
    ); }

  public customEvent(row: any): void { this.onCustomEvent.emit({btn_id: this.customButton.id, row}); }
    
  get first(): number { return (this.page - 1) * this.rows; }
  get readonly(): boolean { return this.disableEditButton && this.disableDeleteButton && this.disableSelectButton && !this.customButton; }

  public processingOptionWithColor(keyVal:string,header:TableConfig):{label:string,color:string}{
    return GenericTableComponent.optionWithColor(keyVal,header,this.tableHeader);
    /*let headerData=this.tableHeader.find(el=>el.field==header.field      );
    if(!headerData) 
      return {label:'',color:''};
    let dataAttribute=headerData.optionSelect.find(selOpt=>selOpt.value==keyVal);
    if(!dataAttribute)
      return {label:'',color:''};
    return {label:dataAttribute.description,color:dataAttribute.linked};*/
  }
  
  static  optionWithColor(keyVal:string,header:TableConfig,headers:TableConfig[]):{label:string,color:string}{
    let headerData=headers.find(el=>el.field==header.field      );
    if(!headerData) 
      return {label:'',color:''};
    let dataAttribute=headerData.optionSelect.find(selOpt=>selOpt.value==keyVal);
    if(!dataAttribute)
      return {label:'',color:''};
    return {label:dataAttribute.description,color:dataAttribute.linked};
  }

  public addItem(){
    this.onAddItem.emit();
  }

}
