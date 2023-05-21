import { Component, OnInit,  EventEmitter, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { SearchConfiguration } from 'src/app/shared/components/form-search/configuration/search.configuration';
import { Paging } from 'src/app/shared/components/rows-number-handler/rows-number-handler.component';
import { CONST } from 'src/app/shared/constants';
import { SearchFields } from 'src/app/shared/models/form-search.configurations.models';
import { TemplateEmailDestinatariDto, TemplateEmailDTO } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
  selector: 'app-testi-comunicazioni-table',
  templateUrl: './testi-comunicazioni-table.component.html',
  styleUrls: ['./testi-comunicazioni-table.component.scss']
})
export class TestiComunicazioniTableComponent implements OnInit {
  //old
  //@Input() comunicazioniTableHeaders: TableConfig[] = [];
  //@Input() tableData: ComunicazioniTemplate[];
  //@Output() navigateChange: EventEmitter<any> = new EventEmitter<any>();
  //------

  //parametri ricerca
  @Input("cancellabile") cancellabile:boolean; //indica il set di template su cui sta lavorando
  public configuration: SearchFields[]=SearchConfiguration.getTemplateEmailSearchFields();
  //public triggerSearch: EventEmitter<boolean> = new EventEmitter<boolean>();
  public initForm: {[key: string]: any} = {};
  //------------------
  // PARAMETRI TABELLA TEMPLAETE COMUNICAZIONI
  public toggleInsert: boolean = false;
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public pageObj = {page:1,limit:CONST.DEFAULT_PAGE_NUMBER,cancellabile:false};

  
  public itemsTotal: number = 0;
  public totalRecords: number = 0;
  
  public data: TemplateEmailDTO[];

  public dettaglio: TemplateEmailDestinatariDto;

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
              private adminService: AdminFunctionsService,
              private translateService:TranslateService)
  {
  }

  ngOnInit()
  {
    this.pageObj.cancellabile=this.cancellabile;
    this.loadingService.emitLoading(true);
    this.effettuaRicerca();
  }

  
  /**
   * @description Cambia il numero di righe per pagina della tabella
   * @param event 
   */
  public changeRows(event: Paging): void
  {
    if(event.limit) this.pageObj.limit = event.limit;
    if(event.page) this.pageObj.page = event.page;
    this.effettuaRicerca();
  }
  /**
   * @description Cambia la pagina della tabella
   * @param pageNumber 
   */
  public pageChangedAction(pageNumber: number): void
  {
    this.pageObj.page = pageNumber;
    this.effettuaRicerca();
  }

  private effettuaRicerca(searchParam?:any){
    this.loadingService.emitLoading(true);
    let searchObj=this.pageObj;
    if(searchParam){
      searchObj={...searchObj,...searchParam};
    }
    this.adminService.searchTemplateEmail(searchObj).subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
        {
          this.data = response.payload.list;
          this.itemsTotal = response.numeroTotaleOggetti;
          this.totalRecords=response.numeroOggettiRestituiti;
        }
        this.loadingService.emitLoading(false);
      });
  }
  /**
   * @description
   * @param row 
   */
  public openDettaglio(row: TemplateEmailDTO) { 
    this.loadingService.emitLoading(true);
    this.adminService.infoTemplateEmail(row.codice).subscribe(el=>{
      this.dettaglio = el.payload; 
      this.loadingService.emitLoading(false);
    });
  }

  public deleteTemplate(row: TemplateEmailDTO) { 
    this.alertData =
          {
            title: "generic.warning",
            content: 'generic.vuoiEliminare',
            typ: "warning",
            display: true,
            extraData: {operazione:'deleteRow',row:row},
            isConfirm: true
          };
  }

  public doDeleteTemplate(row: TemplateEmailDTO) { 
    this.loadingService.emitLoading(true);
    this.adminService.deleteTemplateEmail(row.codice).subscribe(el=>{
      if(el.codiceEsito==CONST.OK && el.payload==1){
        this.alertData =
          {
            title: "Successo",
            content: "Template cancellato con successo",
            typ: "success",
            display: true,
            extraData: null,
            isConfirm: false
          };
      }
      this.effettuaRicerca();
      this.loadingService.emitLoading(false);
    });
  }

  /**
   * @description 
   * @param event 
   */
  public onAction(event: TemplateEmailDestinatariDto)
  {
    if(event)
    {
      this.loadingService.emitLoading(true);
      //chiamata al BE per l'update
      this.adminService.saveTemplateEmail(event).subscribe(response =>
      {
        this.loadingService.emitLoading(false);
        if(response.codiceEsito === CONST.OK)
        {
          this.alertData =
          {
            title: "Successo",
            content: "Template salvato con successo",
            typ: "success",
            display: true,
            extraData: null,
            isConfirm: false
          };
          let index = this.data.map(m => m.codice).indexOf(event.template.codice);
          if(index>=0){
            this.data[index] = event.template;
            this.dettaglio = null;
          }else{
            this.dettaglio = null;
            this.effettuaRicerca();//ricarico la pagina
          }
          
        }
      });
    }
    else
      this.dettaglio = null;
  }

  public ricerca(event: any): void {
    let filter= {};
    filter['page'] = this.pageObj.page;
    filter['limit'] = this.pageObj.limit;
    let keys = Object.keys(event);
    if (keys) {
      keys.forEach(key => {
        filter[key] = event[key] && event[key].value ? event[key].value : event[key];
      });
    }
    this.effettuaRicerca(filter);
  }

  public resetRicerca(event: any): void {
    this.effettuaRicerca();
  }



  public reset(event: any): void
  { return;
    /*if(event.template)
      this.resettaTemplate(event.object.codice);
    else if(event.destinatari)
      this.resettaDestinatari(event.object.codice);*/
  }
/*
  private resettaTemplate(codice: TipoTemplate): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.resetEmail(codice).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        let destinatari = this.dettaglio.destinatari;
        this.dettaglio = response.payload;
        this.dettaglio.destinatari = destinatari;
      }
      this.loadingService.emitLoading(false);
    });
  }

  private resettaDestinatari(codice: TipoTemplate): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.resetDestinatari(codice).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
        this.dettaglio.destinatari = response.payload.destinatari;
      this.loadingService.emitLoading(false);
    });
  }
*/
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
          case 'event_id':
            break
          case 'deleteRow':
            this.doDeleteTemplate(event.extraData.row);
              break  
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  addTemplate(){
    this.loadingService.emitLoading(true);
    this.adminService.getNewTemplateEmail().subscribe(el=>{
      this.dettaglio = el.payload; 
      this.loadingService.emitLoading(false);
    });
  }

}
