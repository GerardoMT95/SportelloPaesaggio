import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { copyOf } from 'src/app/components/functions/genericFunctions';
import { BaseResponse } from 'src/app/components/model/base-response';
import { SearchFields } from 'src/app/components/model/form-search/formSearch.models';
import { SimpleFormControlData } from 'src/app/components/model/model';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { TableHeader } from 'src/shared/models/table-header';
import { Paging } from './../../../../../../shared/components/rows-number-handler/rows-number-handler.component';
import { CONST } from './../../../../../../shared/constants';
import { LoadingService } from './../../../../../services/loading.service';
import { OrganizzaizoneBean, OrganizzaizoneSearchBean, OrganizzazioneAbilitazioneBean, OrganizzazioneExtendedBean } from './conf/conf-organizzazione';
import { ConstConfOrganizzazioni } from './conf/conf-organizzazione-const';

@Component({
  selector: 'app-censimento-organizzazione',
  templateUrl: './censimento-organizzazione.component.html',
  styleUrls: ['./censimento-organizzazione.component.scss']
})
export class CensimentoOrganizzazioneComponent implements OnInit 
{
  public listOrganizzazioni: Array<OrganizzaizoneBean> = [];
  public totalRecords: number = 0;
  public editOrg: OrganizzazioneExtendedBean = null;
  public inEditing: boolean = false;
  
  //public paging: Paging = {page: 1, limit: 5};
  public configuration: Array<SearchFields> = copyOf(ConstConfOrganizzazioni.searchConfiguration);
  public search: OrganizzaizoneSearchBean = copyOf(ConstConfOrganizzazioni.confBean);
  public columns: Array<TableHeader> = ConstConfOrganizzazioni.columnsOrg;

  public activeIds: string[] = ['add'];
  public openPopupAttiva: boolean = false;
  public init: any = null;
  public attivaConf: Array<SimpleFormControlData> = ConstConfOrganizzazioni.attivaConf;

  public statusTab: any = ConstConfOrganizzazioni.statusTab;
  public toPrint: any = ConstConfOrganizzazioni.TipoOrganizzazioneTablePrint;

  //oggetto utilizzato per l'alert
  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private loadingService: LoadingService,
              private adminService: AdminService) 
  { 

  }

  public ngOnInit(): void 
  {
    this.ricerca();
  }

  public pagingChange(event: Paging): void
  {
    this.search.page = event.page;
    this.search.limit = event.limit;
    this.ricerca();
  }

  public openDetail(editing: boolean = true, idOrg?: number): void
  {
    if(idOrg != null)
    {
      this.loadingService.emitLoading(true);
      this.adminService.findOrganizzazioneDetails(idOrg).subscribe(response =>
      {
        if(response.codiceEsito == CONST.OK)
        {
          this.editOrg = response.payload;
          this.inEditing = editing;
        }
        this.loadingService.emitLoading(false);
      });
    }
    else
    {
      this.editOrg = null;
      this.inEditing = editing;
    }
  }

  public editComplete(event: OrganizzazioneExtendedBean): void
  {
    this.loadingService.emitLoading(true);
    let call: Observable<BaseResponse<OrganizzazioneExtendedBean>> = this.adminService.insertOrganizzazione(event);
    if(event.id) call = this.adminService.updateOrganizzazione(event);
    call.subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        this.editOrg = response.payload;
        this.alertData =
        {
          display: true,
          title: "Successo",
          content: "Operazione completata con successo",
          typ: "success",
          extraData: {},
          isConfirm: false,
        };
      }
      this.loadingService.emitLoading(false);
    });
  }

  public onSort(event: any): void
  {
    this.search.sortBy = event.field;
    this.search.sortType = event.order > 0 ? "ASC" : "DESC";
    this.ricerca();
  }

  public setFiltri(filters: any): void
  {
    Object.keys(filters).forEach(key =>
    {
      if(filters[key] && filters[key].value)
        filters[key] = filters[key].value;
    });
    this.search = {...this.search, ...filters};
  }

  public reset(): void
  {
    let limit = this.search.limit;
    this.search = {...ConstConfOrganizzazioni.confBean, limit}; 
  }

  public ricerca(): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.searchConfigurazioni(this.search).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        this.listOrganizzazioni = response.payload.list;
        this.totalRecords = response.payload.count;
      }
      this.loadingService.emitLoading(false);
    });
  }

  callbackAlert(event: any) 
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso) 
    {
      this.alertData.display = false;
    } 
    else if (event.isConfirm) 
    {
      if (event.extraData && event.extraData.operazione) 
      {
        switch (event.extraData.operazione) 
        {
          case 'delete':
            //TODO
            let id = event.extraData.value;
            this.disattiva(id);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  public openPopup(id: number, scadenza?: Date): void
  {
    this.init = {paesaggioOrganizzazioneId: id, dataTermine: scadenza};
    this.openPopupAttiva = true;
  }
  
  public attivaAggiorna(bean: OrganizzazioneAbilitazioneBean): void
  {
    if(this.init.dataTermine)
      this.aggiorna(bean)
    else
      this.attiva(bean);
  }

  private attiva(bean: OrganizzazioneAbilitazioneBean): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.enableOrganizzazione(bean).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        let item = this.listOrganizzazioni.find(f => f.id == bean.paesaggioOrganizzazioneId)
        item.dataScadenzaAbilitazione = bean.dataTermine;
        item.enabled = true;
        this.init = null;
        this.openPopupAttiva = false;
      }
      this.loadingService.emitLoading(false);
    });
  }

  private aggiorna(bean: OrganizzazioneAbilitazioneBean): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.aggEnableOrganizzazione(bean).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        let item = this.listOrganizzazioni.find(f => f.id == bean.paesaggioOrganizzazioneId)
        item.dataScadenzaAbilitazione = bean.dataTermine;
        item.enabled = true;
        this.init = null;
        this.openPopupAttiva = false;
      }
      this.loadingService.emitLoading(false);
    });
  }

  private disattiva(id: number): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.disableOrganizzazione(id).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
        this.listOrganizzazioni.find(f => f.id == id).enabled = false;
      this.loadingService.emitLoading(false);
    });
  }

  public disattivaPopup(rowdata: OrganizzaizoneBean): void
  {
    this.alertData =
    {
      display: true,
      title: "Conferma disabilitazione",
      content: "Confermi di voler disabilitare l'organizzazione \"" + rowdata.denominazione + "\" per l'applicativo corrente?",
      typ: "info",
      extraData: {operazione: 'delete', value: rowdata.id},
      isConfirm: true,
    };
  }

}
