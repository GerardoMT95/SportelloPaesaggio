import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { paths } from 'src/app/app-routing.module';
import { TableConfig } from 'src/app/core/models/header.model';
import { BaseSearch } from 'src/app/features/interfaccia-di-riepilogo-del-fascicolo/models/corrispondenza.model';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { AttivitaDaEspletareEnum, AttivitaDaEspletareEnumLabels, Fascicolo, FascicoloSearch, statiEditingIstanza, statiLavorazione } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-istanze-table',
  templateUrl: './istanze-table.component.html',
  styleUrls: ['./istanze-table.component.scss']
})
export class IstanzeTableComponent implements OnInit {
  //@Input() meta: { [key: string]: SelectOption[] };
  @Input() fascioloTableHeaders: TableConfig[] = [];
  @Input() tableData: Fascicolo[];
  @Input("tableSearch") tableSearch: FascicoloSearch;
  @Input() loading :boolean;
  @Output() updateDataChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() deleteChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() navigateChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() loadLazyChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() totalRecords;
  @Input() first:number;
  @Input() typeProcedimento:SelectOption[];
  @Input() entiDelegati:SelectOption[];
  idIstanza=new Date();  
  statiEditingIstanza=statiEditingIstanza;
  statiLavorazione=statiLavorazione;
  currentUser:LoggedUser;


  attivitaDaEspletareEnum = AttivitaDaEspletareEnum;
  attivitaDaEspletareEnumLabels = AttivitaDaEspletareEnumLabels;
  enteOptions$: any;
  tipoOptions$: any;
  tipoOptions: any;
  constructor( private router: Router,private httpDominio: HttpDominioService,
    private userService:UserService) { }

  ngOnInit() {
    if(this.typeProcedimento==null){console.log("arrivato null "+this.idIstanza);}
    this.currentUser = this.userService.getUser();
  }

  // updateData(event) {
  //   let data = event;
  //   if (event.sortOrder === -1) {
  //     data.field = event.sortField ? '-' + event.sortField : null;
  //   } else {
  //     data.field = event.sortField ? event.sortField : null;
  //   }
  //   data.page = event.first / event.rows + 1;
  //   data.limit = event.rows;
  //   this.updateDataChange.emit(data);
  // }
  loadLazy(event:any){
    if (event.page) this.tableSearch.page = event.page;
    if (event.limit) this.tableSearch.limit = event.limit; 
    //this.pageChange.emit(this.tableSearch); 
    this.tableSearch.page=event.page;
    //this.tableSearch.page=event.rows;
    this.loadLazyChange.emit(this.tableSearch);
    //this.first=1;
  }

  /**
   * 
   * @param event dal ptable prendo solo eventi di order by
   */
  loadLazyFromPtable(event:any){
    if(event.sortField){
      this.tableSearch.sortBy=event.sortField;
      this.tableSearch.sortType=event.sortOrder===-1?'DESC':'ASC';
      this.loadLazyChange.emit(this.tableSearch);
    }
    
  }

  delete(codiceFascicolo: string) {
    this.deleteChange.emit(codiceFascicolo);
  }

  /* navigate(codiceFascicolo: string) {
    this.navigateChange.emit(codiceFascicolo);
  } */
  
  navigate(fascicolo: Fascicolo)
  {
    this.navigateChange.emit(fascicolo);
  }

  getTipoProcedimentoFromMetadata(field){
    if(this.typeProcedimento==null){console.log("arrivato null in getTipo"+this.idIstanza);}    
    /*const id = +field-1;
    return this.meta.typeProcedimento[id].description;*/
    return this.typeProcedimento.find(el=>el.value==field).description;
  }
  navigateToCreate() {
    this.router.navigate([paths.create()]);
  }

  getEnteDelegatoFromMetadata(field){
    if(this.entiDelegati==null){console.log("arrivato null in entiDelegati");}    
    let ret=this.entiDelegati.find(el=>el.value==field);
    if(!ret){
      return "";
    }else 
    return ret.description;
  }

  canEdit(rowdata:Fascicolo){
    return statiEditingIstanza.includes(rowdata.attivitaDaEspletare) && this.currentUser.username==rowdata.owner;
  }
}
