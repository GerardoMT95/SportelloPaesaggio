import { ReportList } from './../../models/report.model';
import { SelectItem } from 'primeng/primeng';
import { CONST } from 'src/app/shared/constants';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { Paging } from 'src/app/shared/models/search.models';

@Component({
  selector: 'app-report-table',
  templateUrl: './report-table.component.html',
  styleUrls: ['./report-table.component.css']
})
export class ReportTableComponent implements OnInit {

  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public pageObj = { page: 1, limit: this.rowsOnPage, field: "data", sort: 1 };
  public caricato :boolean = false;
  @Input("showDownload")
  public showDownload = false;


  @Input("totalRecords")
  public totalRecords:number;
  @Input("list")
  public list:ReportList[];
  
  @Output("pagination")
  public paginationEmitter:EventEmitter<any> = new EventEmitter<any>();
  @Output("download")
  public downloadEmitter:EventEmitter<ReportList> = new EventEmitter<ReportList>();

  public headers: TableConfig[] = [];

  constructor() { }

  ngOnInit() {
    console.log(this.list)
    this.headers = [{header: ''                     ,field: 'stato'             ,width:  5}
                   ,{header: 'report.table.fileName',field: 'fileName'          ,width: 30}
                   ,{header: 'report.table.hash'    ,field: 'hash'              ,width: 40}
                   ,{header: 'report.table.data'    ,field: 'dataCreazione'     ,width: 20}
                   ,{header: ''                     ,field: 'azioni'            ,width:  5}
                   ];
     this.caricato = true;               
  }

  public changeRows(event: Paging): void {
    if (event.limit && event.limit != this.pageObj.limit)
      this.rowsOnPage = event.limit;
    if (event.limit) this.pageObj.limit = event.limit;
    if (event.page) this.pageObj.page = event.page;
    this.paginationEmitter.emit(this.pageObj);
  }

  public onSort(event: any): void {
    if(event.field === this.pageObj.field
    && event.order === this.pageObj.sort
    ){
      return ;
    }
    if (event.field) this.pageObj.field = event.field;
    if (event.order) this.pageObj.sort = event.order;
    this.paginationEmitter.emit(this.pageObj);
  }

  public download(event: ReportList): void {
    this.downloadEmitter.emit(event);
  }
}
