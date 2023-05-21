import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { TableHeader, SortHeader } from "src/app/shared/models/table-header";
import { PageObj } from "src/app/shared/models/response-object.model";
import { AzioniRiga } from 'src/app/shared/models/azioni-riga';
import { AlertInputData } from 'src/app/shared/components/alert/alert.component';


export class RowObj{
  id:string;
  nome:string;
  cognome:string;
  data:Date;
}

@Component({
  selector: 'app-demo-table',
  templateUrl: './demo-table.component.html',
  styleUrls: ['./demo-table.component.scss']
})

export class DemoTableComponent implements OnInit {
  public alertInputData=new AlertInputData();

  cols: TableHeader[] = [
    {field:'nome',header:'Nome'},
    {field:'cognome',header:'Cognome'},
    {field:'data',header:'Data'},
    {field:'id',header:'',width:10},
  ];
 
  tableData: PageObj<RowObj>;

  @Output() actionChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() pageChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() sortField: EventEmitter<string> = new EventEmitter<string>();
  @Output() updateDataChange: EventEmitter<any> = new EventEmitter<any>();
  
  test: number = 1;
  helper: string;
  sortPage: string;
  sortedBy: SortHeader = new SortHeader();
  listaElementi:RowObj[]=[];
  azioniRiga=AzioniRiga;

  constructor() {}

  ngOnInit() {
    //mock-ed
    for (let index = 0; index < 30; index++) {
      this.listaElementi.push({nome:"nome"+index,cognome:"cognome"+index,data:new Date(),id:index+""});
    }
    
  }

  planAction(rigaObj:RowObj , action: AzioniRiga) {
    this.alertInputData.title="azione da eseguire";
    this.alertInputData.content="riga id:"+ rigaObj.id;
    this.alertInputData.extraData={azione:action,id:rigaObj.id,nome:rigaObj.nome};
    this.alertInputData.visible=true;//alla chiusura verra' invocato il callback
    this.actionChange.emit({ rigaObj, action });
  }

  callback(event){
    if(event.isChiuso)
    {
      
    }
    else if(event.isConfirm)
    {
      if (event.extraData){//effettuo azioni a seconda dell'extradata

      }
    }
    this.alertInputData.visible = false;
  }
  
  paginate(event: any) {
    this.pageChange.emit({
      page: event.page + 1,
      limit: event.rows
    });
  }

  updateData(event) {
    let data = event;
    if (event.sortOrder === 1) {
      data.field = event.sortField ? "-" + event.sortField : null;
    } else {
      data.field = event.sortField ? event.sortField : null;
    }
    data.page = event.first/event.rows + 1;
    data.limit = event.rows;
    this.updateDataChange.emit(data);
    //mocked update
    this.tableData={count:30,list:this.listaElementi.slice(((data.page-1)*data.limit),((data.page-1)*data.limit)+data.limit),pageNumber:data.page,pageSize:data.limit};
  }

  customSort(event) {
    if (event.sortOrder === 1) {
      event.field = "-" + event.sortField;
    } else {
      event.field = event.sortField;
    }
    this.sortField.emit(event.field);
  }

  
}
