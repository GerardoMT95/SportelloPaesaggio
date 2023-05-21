import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { DettaglioTemplate, TemplateDestinatarioDTO, TipoTemplate } from 'src/app/components/model/entity/admin.models';
import { Paging } from 'src/shared/components/rows-number-handler/rows-number-handler.component';
import { CONST } from 'src/shared/constants';
import { AdminService } from './../../../../services/admin-service/admin.service';
import { LoadingService } from './../../../../services/loading.service';
import { BaseSearch } from './../../../model/entity/fascicolo.models';

@Component({
  selector: 'app-template-comunicazioni',
  templateUrl: './template-comunicazioni.component.html',
  styleUrls: ['./template-comunicazioni.component.css']
})
export class TemplateComunicazioniComponent implements OnInit /* extends AuthComponent */
{
  // PARAMETRI TABELLA TEMPLAETE COMUNICAZIONI
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;
  public itemsTotal: number = 0;
  public totalRecords: number = 0;
  
  public data: DettaglioTemplate[];
  public dettaglio: DettaglioTemplate;
  public addresses: TemplateDestinatarioDTO[] = [];

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
    /* super(router, lss); */
  }

  ngOnInit()
  {
    //this.getListaTemplates()
    this.loadingService.emitLoading(true);
    this.adminService.getAll().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.data = response.payload.slice(0, 5);
        this.itemsTotal = response.payload.length;
      }
      this.loadingService.emitLoading(true);
    });
  }

  /**
   * Recupero lista dei template delle comunicazioni configurabili
   */
  public getListaTemplates(): void
  {
    let filter: BaseSearch = new BaseSearch();
    filter.page = this.page;
    filter.limit = this.rowsOnPage;
    this.data = CONST.mockupTemplates;
  }
  
  /**
   * @description Cambia il numero di righe per pagina della tabella
   * @param event 
   */
  public changeRows(event: Paging): void
  {
    if(event.limit) this.rowsOnPage = event.limit;
    if(event.page) this.page = event.page;
    //richiama ricerca
    this.adminService.getAll().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.data = response.payload.slice(0, this.rowsOnPage);
      this.loadingService.emitLoading(true);
    });
  }
  /**
   * @description Cambia la pagina della tabella
   * @param pageNumber 
   */
  public pageChangedAction(pageNumber: number): void
  {
    this.page = pageNumber;
    //richiama ricerca
    this.adminService.getAll().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        let offset: number = (pageNumber - 1) * this.rowsOnPage;
        this.data = response.payload.slice(offset, this.rowsOnPage + offset);
      }
      this.loadingService.emitLoading(true);
    });
  }
  /**
   * @description
   * @param row 
   */
  public openDettaglio(row: DettaglioTemplate) 
  {
    this.addresses = row.destinatari/* .map(m => {return {...m, nome: m.denominazione}}) */;
    this.dettaglio = row; 
  }
  /**
   * @description 
   * @param event 
   */
  public onAction(event: DettaglioTemplate)
  {
    console.log("event ", event);
    if(event)
    {
      //chiamata al BE per l'update
      this.adminService.saveTemplate(event).subscribe(response =>
      {
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
          let index = this.data.map(m => m.codice).indexOf(event.codice);
          this.data[index] = event;
          this.dettaglio = null;
        }
      });
    }
    else
      this.dettaglio = null;
  }

  public reset(event: any): void
  {
    if(event.template)
      this.resettaTemplate(event.object.codice);
    else if(event.destinatari)
      this.resettaDestinatari(event.object.codice);
  }

  private resettaTemplate(codice: TipoTemplate): void
  {
    this.loadingService.emitLoading(true);
    this.adminService.resetEmail(codice).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.addresses = [];
        this.dettaglio = response.payload;
        if (this.dettaglio.destinatari)
          this.addresses = this.dettaglio.destinatari/* .map(m => { return { ...m, nome: m.denominazione } }) */;
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
      {
        this.addresses = [];
        this.dettaglio.destinatari = response.payload.destinatari;
        if (this.dettaglio.destinatari)
          this.addresses = this.dettaglio.destinatari/* .map(m => { return { ...m, nome: m.denominazione } }) */;
        console.log("indirizzi: ", this.addresses);
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
          case 'event_id':
            break
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

}
