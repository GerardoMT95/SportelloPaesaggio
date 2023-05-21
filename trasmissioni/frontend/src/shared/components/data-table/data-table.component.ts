import { Component, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from "@angular/core";
import { faFileCsv, faFilePdf } from "@fortawesome/free-solid-svg-icons";
import { NgbPagination } from "@ng-bootstrap/ng-bootstrap";
import { Paginator } from "primeng/paginator";
import { fromSizeToPaginatorElements, fromWidthToSize } from "src/app/components/functions/tableSizeHandler";
import { FascicoloPublicDto } from "src/app/components/model/entity/fascicolo.models";
import { GroupType } from "src/app/components/model/enum";
import { DettaglioFascicolo } from "src/app/components/model/fascicolo.models";
import { UserService } from "src/app/services/user.service";
import { FascicoloAzioni } from "src/shared/config/fascicolo.config";
//import { RegistrationStatus} from "src/shared/models/registration-status";
import { CONST } from "src/shared/constants";
import { StatoFascicolo } from "src/shared/models/registration-status";
import { PageObj } from "../../models/response-object.model";
import { SortHeader, TableHeader } from "../../models/table-header";
import { Paging } from "../rows-number-handler/rows-number-handler.component";

@Component({
  selector: "app-data-table",
  templateUrl: "./data-table.component.html",
  styleUrls: ["./data-table.component.scss"]
})

export class DataTableComponent implements OnInit {
  @Input() cols: TableHeader[] = [];
  @Input() tableData: PageObj<any>;
  @Input() isPublic: boolean;
  @Input() showDetailButton: (rowData:FascicoloPublicDto)=>boolean
  =()=>false; //in caso di pubblico e utente con competenza
  @Input() addButton: boolean=false;
  @Input("initPageAndRows") set init(item: { page: number, rows: number }) {
    if (item.page && item.page >= 0)
      this.sortByAndOrder.page = item.page;
    if (item.rows && item.rows >= 0)
      this.sortByAndOrder.rows = item.rows;
  }

  @Output() actionChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() pageChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() sortField: EventEmitter<string> = new EventEmitter<string>();
  @Output() updateDataChange: EventEmitter<OrderByAndPaging> = new EventEmitter<OrderByAndPaging>();
  @Output() exportEmitter=new EventEmitter<string>();
  @Output() newItemEmitter=new EventEmitter<boolean>();
  @ViewChild("paginator") paginator: Paginator;
  fascicoloAction = FascicoloAzioni;
  registrationStatus = StatoFascicolo;
  faFileCsv = faFileCsv;
  faFilePdf = faFilePdf;
  inited=false;
  sortPage: string;
  sortedBy: SortHeader = new SortHeader();
 
  public sortByAndOrder: OrderByAndPaging = { page: 1, rows: 5, field: null, sortField: null, sortOrder: "ASC" };

  public nRows: number = 10;

  constructor(private userService: UserService) { }

  ngOnInit() {
    if (this.isPublic) {
      this.cols.splice(this.cols.length - 1, 1); //elimino la colonna utile a mostrare i bottoni azione
    }
    this.nRows = fromSizeToPaginatorElements(fromWidthToSize(window.innerWidth));
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any)
  {
    this.nRows = fromSizeToPaginatorElements(fromWidthToSize(window.innerWidth));
  }

  nuovoItem(){
    this.newItemEmitter.emit(true);
  }
  export(tipoExport:string){
    this.exportEmitter.emit(tipoExport);
  }
  planAction(riga: DettaglioFascicolo, action: FascicoloAzioni, index?: number) {
    this.actionChange.emit({ riga: riga, action: action, index: index });
  }

  isDiCompetenza(riga: DettaglioFascicolo) {
    return this.userService.getUser() && this.userService.getUser().roles;
  }

  public showEditButton(rowData: any): boolean {
    return (rowData.stato === "WORKING" && 
            (this.userService.isStessaOrganizzazione(rowData.ufficio,this.userService.actualGroup )|| this.userService.actualGroup === 'ADMIN')
           ) ||
           (rowData.stato === "ON_MODIFY" && 
            this.userService.isStessaOrganizzazione(rowData.ufficio,this.userService.actualGroup ) && 
            rowData.usernameUtenteTrasmissione === this.userService.getUser().username);
  }

  public showViewButton(rowData: any): boolean {
    if(!this.showEditButton(rowData))
    {
      switch (rowData.stato)
      {
        case 'CANCELLED':
          return !this.isPublic ||
            this.userService.groupType === GroupType.ADMIN ||
            this.userService.groupType === GroupType.ED;
        default:
          return !this.isPublic ||
            this.userService.groupType === GroupType.ADMIN ||
            this.userService.groupType === GroupType.REG ||
            this.userService.groupType === GroupType.ED ||
            this.userService.groupType === GroupType.ETI ||
            this.userService.groupType === GroupType.SBAP;
      }
    }
    
  }


  getColor(codeStatoFascicolo: string) {
    let color = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, StatoFascicolo[codeStatoFascicolo], 'color', 'enumVal');
    return color;
  }

  getStatusLabel(codeStatoFascicolo: string) {
    let label = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, StatoFascicolo[codeStatoFascicolo], 'translated', 'enumVal');
    return label;
  }

  getStatusLettera(codeStatoFascicolo: string) {
    let label = CONST.getLabelFromValue(CONST.StatoFascicoloStatusAttribute, StatoFascicolo[codeStatoFascicolo], 'lettera', 'enumVal');
    return label;
  }

  paginate(event: any) {
    this.pageChange.emit
      ({
        page: event.page,
        limit: event.rows
      });
  }

  public changeRows(event: Paging): void {
    this.sortByAndOrder.rows = event.limit;
    this.sortByAndOrder.page = event.page;
    this.updateData();
  }
  public changePage(event: number): void {
    this.sortByAndOrder.page = event;
    this.updateData();
  }

  public lazyLoad(event: any): void {
    if (event.sortOrder === 1) {
      this.sortByAndOrder.field = "-" + event.sortField;
      this.sortByAndOrder.sortField = event.sortField;
      this.sortByAndOrder.sortOrder = "DESC";
    }
    else {
      this.sortByAndOrder.field = event.sortField;
      this.sortByAndOrder.sortField = event.sortField;
      this.sortByAndOrder.sortOrder = "ASC";
    }
    this.updateData();
  }

  public updateData(): void {

    this.updateDataChange.emit(this.sortByAndOrder);
  }

  customSort(event) {
    if (event.sortOrder && event.sortField) {
      if (event.sortOrder === 1)
        event.field = "-" + event.sortField;
      else
        event.field = event.sortField;
    }
    this.sortField.emit(event.field);
  }

  showActionButtons(field: string) {
    return field === "id";
  }

  isArrayType(data: any): boolean {
    return data && Array.isArray(data) && data.length > 0;
  }

  public getWidth(col: any): any {
    let width = '';
    if (col) {
      let um = col.type && col.type.toLowerCase() === 'button' ? 'em' : '%';
      if (col.width) width = col.width + um;
    }
    return width;
  }

  get itemsTotal(): number { return this.tableData ? this.tableData.count : 0 }
  get totalRecords(): number { return this.tableData && this.tableData.list ? this.tableData.list.length : 0 }
  get gruppoScelto(): string { return this.userService.actualGroup; }

  isStessaOrganizzazione(ufficio,gruppo){
    return this.userService.isStessaOrganizzazione(ufficio,gruppo);
  }

}

export type OrderByAndPaging =
  {
    page: number,
    rows: number,
    sortField: string,
    sortOrder: string,
    field: string; //?????
  }