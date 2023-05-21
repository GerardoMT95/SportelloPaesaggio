import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SelectItem } from 'primeng/primeng';
import { EventActionData, TableConfig } from 'src/app/core/models/header.model';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { CONST } from 'src/app/shared/constants';
import {  Fascicolo, GroupRole, Role, StatoGroup } from 'src/app/shared/models/models';
import { Paging } from 'src/app/shared/models/search.models';
import { ProcessingStatus } from 'src/app/shared/util/UtilProcessingStatus';
import { MenuItem } from './../../../../core/models/menu.model';
import { GroupType, StatoEnum } from './../../../../shared/models/models';
import { UserService } from './../../../../shared/services/user.service';
import { IstanzeConst } from './../../constants/constant-for-table';

@Component({
  selector: 'app-istanze-table',
  templateUrl: './istanze-table.component.html',
  styleUrls: ['./istanze-table.component.scss']
})
export class IstanzeTableComponent
{
  @Input("fascioloTableHeaders") fascioloTableHeaders: TableConfig[] = [];
  @Input("tableData")            tableData: Fascicolo[];
  @Input("dataStato")            dataStato: StatoEnum[];
  @Input("totalRecords")         totalRecords: number = 0;

  @Output("updateDataChange")       updateDataChange: EventEmitter<any> = new EventEmitter<any>();
  @Output("deleteChange")           deleteChange: EventEmitter<any> = new EventEmitter<any>();
  @Output("navigateChange")         navigateChange: EventEmitter<any> = new EventEmitter<any>();
  @Output("navigaAssegnaFascicolo") navigaAssegnaFascicolo: EventEmitter<string> = new EventEmitter<string>();
  @Output("contextSelection")       callRichiesta: EventEmitter<Fascicolo> = new EventEmitter();
  @Output("paginationChange")       paginationChange: EventEmitter<any> = new EventEmitter<any>();
  @Output("emitOtherAction")       emitOtherAction: EventEmitter<EventActionData> = new EventEmitter<EventActionData>();

  //public attivitaDaEspletareEnum = AttivitaDaEspletareEnum;
  public statoEnum = StatoEnum;
  public role = Role;
  public groupType = GroupType;
  public items: MenuItem[];
  public target: string;
  public selected: any;
  public cm = "cm";
  public showCM: boolean = false;

  //private tipoProcedimento = IstanzeConst.TIPO_PROC_CONST;
  private tipoProcedimento = CONST.mappingTipiProcedimento;

  public pages: SelectItem[] = CONST.PAGINAZIONE;

  public pageObj = { page: 1, limit: CONST.DEFAULT_PAGE_NUMBER, field:'codicePraticaAppptr' , sort: -1 };


  constructor(private userService: UserService) { }

  public processingStatus(status: StatoEnum): StatoGroup
  {
    let tmp = ProcessingStatus.getStatus(status);
    return tmp;
  }

  public delete  (codiceFascicolo: string)         : void { this.deleteChange.emit(codiceFascicolo); }
  public navigate(codiceFascicolo: string)         : void { this.navigateChange.emit(codiceFascicolo); }
  public navigateForAssign(codiceFascicolo: string): void { this.navigaAssegnaFascicolo.emit(codiceFascicolo); }
  public triggerPaging(event: any)                 : void { this.paginationChange.emit(event); }

  get user(): LoggedUser { return this.userService.getUser(); }

  public handleContextmenu(row: Fascicolo): void
  {
    this.items = [];
    if (this.userService.role === GroupRole.A || (row.rup && row.attivitaDaEspletare !== StatoEnum.Archiviata))
    {
      this.items =
      [
        {
          label: 'Gestisci pratica',
          icon: 'pi pi-save',
          command: () => { this.callRichiesta.emit(this.selected); }
        }
      ];
      this.target = row.codicePraticaAppptr;
    }
    
  }

  public getTipoProcedimentoFromMetadata(field: any): any
  {
    const id = +field - 1;
    return this.tipoProcedimento[id] ? this.tipoProcedimento[id].description : null;
  }

  public showAssignButton(row: Fascicolo): boolean
  {
    return false;
  }

  /**
   * @description Cambia il numero di righe per pagina della tabella
   * @param event 
   */
  public changeRows(event: Paging): void
  {
    if (event.limit) this.pageObj.limit = event.limit;
    if (event.page) this.pageObj.page = event.page;
    this.paginationChange.emit(this.pageObj);
  }

  public onSort(event: any): void
  {
    if(event.field) this.pageObj.field = event.field;
    if (event.order) this.pageObj.sort = event.order;
    this.paginationChange.emit(this.pageObj);
  }

  public showEditButton(row: Fascicolo): boolean
  {
    return ([GroupType.EnteDelegato, GroupType.Regione].includes(this.typeGroup) &&
            row.attivitaDaEspletare !== StatoEnum.Trasmessa) ||
           (this.typeGroup === GroupType.Soprintendenza && row.attivitaDaEspletare === StatoEnum.InLavorazione);
  }

  public showEyeButton(row: Fascicolo): boolean { return !this.showEditButton(row); }
  get typeGroup(): GroupType { return this.userService.groupType; }

  public getComuniIntervento(fascicolo:Fascicolo) 
  {
    let comuniIntervento=[];
    /*if(fascicolo.istanza && fascicolo.istanza.localizzazione && fascicolo.istanza.localizzazione.localizzazioneComuni){
      fascicolo.istanza.localizzazione.localizzazioneComuni.forEach(comuneIntervento=>comuniIntervento.push(comuneIntervento.comune));
    }*/
    if(fascicolo.descrComuniIntervento){
      comuniIntervento=fascicolo.descrComuniIntervento;
    }
    return comuniIntervento;
  }

  public otherAction(nameAction:string,rowData: any): void{
    this.emitOtherAction.emit({nameAction:nameAction,rowData:rowData});
  }

}
