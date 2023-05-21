import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SelectItem } from 'primeng/primeng';
import { combineLatest } from 'rxjs';
import { copyOf } from 'src/app/components/functions/genericFunctions';
import { SearchFields } from 'src/app/components/model/form-search/formSearch.models';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { Paging } from 'src/shared/components/rows-number-handler/rows-number-handler.component';
import { CONST } from 'src/shared/constants';
import { TableHeader } from 'src/shared/models/table-header';
import { TipoOrganizzazioneSpecifica } from '../censimento-organizzazione/conf/conf-organizzazione';
import { LoadingService } from './../../../../../services/loading.service';
import { EnteSummary, TipoOrganizzazione } from './../censimento-organizzazione/conf/conf-organizzazione';
import { ConstDestTrasm } from './conf/gestion-destinatari-trasmissioni-const';
import { EmailOrganizzazioneBaseBean, EmailOrganizzazioneBean, EmailOrganizzazioneSearch } from './conf/gestione-destinatari-trasmissione-conf';

@Component({
  selector: 'app-gestione-destinatari-trasmissione',
  templateUrl: './gestione-destinatari-trasmissione.component.html',
  styleUrls: ['./gestione-destinatari-trasmissione.component.scss']
})
export class GestioneDestinatariTrasmissioneComponent implements OnInit 
{
  public isSbap: boolean = false;
  public type: string = null;
  public filters: Array<TipoOrganizzazioneSpecifica> = [];
  public filterOrganizzazione: TipoOrganizzazione = null;

  public activeIds: string[] = ['add'];
  public searchConfiguration: Array<SearchFields> = null;
  public search: EmailOrganizzazioneSearch = copyOf(ConstDestTrasm.initSearch);
  public options: Array<SelectItem> = [];
  public selectedEnte: any = null;
  public enti: Array<EnteSummary> = [];
  public listEmail: Array<EmailOrganizzazioneBean> = [];
  public totalRecord: number = 0;
  public editRecord: EmailOrganizzazioneBean = null;
  public editing: boolean = false;

  public filterCache: any = this.initFilterCache();

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

  public columns: Array<TableHeader> = ConstDestTrasm.columnsWithEnte;

  constructor(private loadingService: LoadingService,
              private service: AdminService,
              private route: ActivatedRoute) 
  { 
    this.route.paramMap.subscribe(param => 
    {
      this.searchConfiguration = null;
      this.reset();
      this.type = param.get('type');
      switch(this.type)
      {
        case 'SOPRINTENDENZA':
          this.isSbap = true;
          this.filters = ['Soprintendenza'];
          this.filterOrganizzazione = "SBAP";
          this.search.tipoOrganizzazione = ['SOPRINTENDENZA'];
          this.columns = ConstDestTrasm.columnsWithEnte;
          break;
        case 'PROVINCIA':
          this.isSbap = true;
          this.filters = ['Provincia'];
          this.filterOrganizzazione = null;
          this.search.tipoOrganizzazione = ['PROVINCIA'];
          this.columns = ConstDestTrasm.columnsWithoutEnte;
          break;
        case 'ENTE_COMUNALE':
          this.isSbap = true;
          this.filters = ['Comune', 'Unione'];
          this.filterOrganizzazione = "ED";
          this.search.tipoOrganizzazione = ['COMUNE', 'UNIONE'];
          this.columns = ConstDestTrasm.columnsWithEnte;
          break;
      }
      this.search.organizzazione = this.filterOrganizzazione;
      this.init();
    });
  }

  public ngOnInit(): void 
  {
    //this.init();
  }

  private init(): void
  {
    const _this = this;
    this.loadingService.emitLoading(true);
    combineLatest(this.service.searchOrganizzazioneByTipo(this.filters), this.service.listMailOrganizzazione(this.search)).subscribe(([response1, response2]) =>
    {
      if(response1.codiceEsito == CONST.OK && response2.codiceEsito == CONST.OK)
      {
        //handle response1
        _this.options = response1.payload.map(m => {return{label: m.denominazione, value: m.id}});
        _this.searchConfiguration = ConstDestTrasm.getSearchConfigurationEmail(this.options);
        //handle response2
        _this.listEmail = response2.payload.list;
        _this.totalRecord = response2.payload.count;
      }
      _this.loadingService.emitLoading(false);
    });
  }

  public pagingChange(p: Paging): void
  {
    this.search.page = p.page;
    this.search.limit = p.limit;
    this.ricerca();
  }

  public onSort(event: any): void
  {
    this.search.sortBy = event.field;
    this.search.sortType = event.order > 0 ? "ASC" : "DESC";
    this.ricerca();
  }

  public setFiltri(event: any): void
  {
    this.search = {
      ...event, 
      organizzazioneId: event.organizzazioneId ? event.organizzazioneId.value : null,
      tipoOrganizzazione: this.search.tipoOrganizzazione,
      organizzazione: this.filterOrganizzazione,
      page: this.search.page, 
      limit: this.search.limit, 
      sortBy: this.search.sortBy, 
      sortType: this.search.sortType
    };
    this.filterCache = {...event};
  }

  public reset(): void
  {
    let f = this.search.tipoOrganizzazione; 
    this.filterCache = this.initFilterCache();
    this.search = copyOf(ConstDestTrasm.initSearch);
    this.search.tipoOrganizzazione = f;
    this.search.organizzazione = this.filterOrganizzazione;
  }

  public ricerca(): void
  {
    this.loadingService.emitLoading(true);
    this.service.listMailOrganizzazione(this.search).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        this.listEmail = response.payload.list;
        this.totalRecord = response.payload.count;
      }
      this.loadingService.emitLoading(false);
    });
  }

  public openEdit(data: EmailOrganizzazioneBean): void
  {
    this.editRecord = data;
    this.editing = true;
  }

  public saveOrUpdate(data: EmailOrganizzazioneBaseBean): void
  {
    let call = this.service.insertPaesagioEmail(data);
    if(data.id)
      call = this.service.updatePaesagioEmail(data);
    this.loadingService.emitLoading(true);
    call.subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        this.editRecord = response.payload;
        this.alertData =
        {
          display: true,
          title: "Successo",
          content: "Operazione effettuata con successo",
          typ: "success",
          extraData: null,
          isConfirm: false,
        };
        this.ricerca();
      } 
      this.loadingService.emitLoading(false); 
    });
  }

  public close(): void
  {
    this.editRecord = null;
    this.editing = false;
  }

  public elimina(data: EmailOrganizzazioneBaseBean): void
  {
    this.alertData =
    {
      display: true,
      title: "Attenzione",
      content: "Sicuro di voler eliminare il destinatario \"" + data.denominazione + "\"? L'operazione sarà irreversibile",
      typ: "info",
      extraData: {operazione: 'cancella', id: data.id},
      isConfirm: true,
    };
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
          case 'cancella':
            this.loadingService.emitLoading(true);
            this.service.deletePaesagioEmail(event.extraData.id).subscribe(response =>
            {
              if(response.codiceEsito == CONST.OK)
              {
                this.alertData =
                {
                  display: true,
                  title: "Successo",
                  content: "Operazione effettuata con successo",
                  typ: "success",
                  extraData: null,
                  isConfirm: false,
                };
                this.ricerca();
              }
              this.loadingService.emitLoading(false); 
            });
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  private initFilterCache(): any
  {
    return {
      organizzazioneId: null,
      email: null,
      denominazione: null
    };
  }

}
