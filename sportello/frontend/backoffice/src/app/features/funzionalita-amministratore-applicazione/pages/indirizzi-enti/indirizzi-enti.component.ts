import { Component, OnInit } from "@angular/core";
import { SelectItem } from "primeng/primeng";
import { SearchConfiguration } from "src/app/shared/components/form-search/configuration/search.configuration";
import { CONST } from "src/app/shared/constants";
import { LoadingService } from "src/app/shared/services/loading.service";
import { AdminFunctionsService } from './../../../../shared/services/admin/admin-functions.service';
import { SearchFields } from "src/app/shared/models/form-search.configurations.models";
import { DestinatarioIstituzionaleDto, RubricaIstituzionaleSearch } from "../../models/admin-functions.models";
import { __classPrivateFieldSet } from "tslib";

@Component({
  selector: 'app-indirizzi-enti',
  templateUrl: './indirizzi-enti.component.html',
  styleUrls: ['./indirizzi-enti.component.css']
})
export class IndirizziEntiComponent implements OnInit
{
  //COMBO TIPO ENTE
  private listaTipologie: SelectItem[];
  // PARAMETRI TABELLA TEMPLAETE COMUNICAZIONI
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;
  public itemsTotal: number = 0;
  public index:number;
  public sortBy: string = 'denominazione';
  public sortOrder: string = 'asc';
  public data: DestinatarioIstituzionaleDto[];
  public configuration: SearchFields[];

  public filters: RubricaIstituzionaleSearch;

  // parametro per visualzzate lista o dettaglio
  public selected: DestinatarioIstituzionaleDto = null;

  constructor(private service: AdminFunctionsService,
              private loading: LoadingService)
  {
    /* super(router, lss); */

  }
  /**
   * Inizializzazione componente
   */
  ngOnInit()
  {
    this.getListaEnti();
    this.configuration = SearchConfiguration.getRubricaSearchFields(this.listaTipologie);
    this.resetFiltri();
  }

  /**
   * Popolo la combo di tipologia ente
   */
  public getListaEnti() { this.listaTipologie = CONST.listaTipologieMockup; }

  /**
  *  Reset ricerca
  */
  public resetFiltri()
  {
    this.filters =
    {
      page: 1,
      limit: 5,
      colonna: 'nome',
      direzione: 'ASC',
      nome: null,
      tipoEnte: null
    }
    this.doSearch();
  }
  /**
   * applicazione filtro di ricerca enti
   */
  public ricerca(event: any): void
  {
    this.filters = {...this.filters, ...event}
    if(this.filters.tipoEnte)
      this.filters.tipoEnte = (this.filters.tipoEnte as any).value; 
    this.doSearch();
  }

  private doSearch(): void
  {
    let _self = this;
    console.log("parametri di ricerca: ", _self.filters);
    _self.loading.emitLoading(true);
    _self.service.ricercaContatti(_self.filters).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        _self.data = response.payload.list;
        _self.itemsTotal = response.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  /**
   * @description Cambia il numero di righe per pagina della tabella
   * @param rowsOnPage 
   */
  public changeRows(rowsOnPage: number): void
  {
    this.filters.limit = rowsOnPage;
    this.filters.page = 1;
    this.doSearch();
  }
  /**
   * @description Cambia la pagina della tabella
   * @param pageNumber 
   */
  public pageChangedAction(pageNumber: number): void
  {
    this.filters.page = pageNumber;
    this.doSearch();
  }
  /**
   * @description Apertura dettaglio template, chiusura tabella
   * @param item 
   */
  public openDettaglio(item: DestinatarioIstituzionaleDto): void { this.selected = item; }
  /**
   * @description Apertura tabella, chiusura dettaglio template
   * @param event 
   */
  public onAction(event: DestinatarioIstituzionaleDto): void
  {
    console.log("dettaglio: ", event)
    if(event)
    {
      let _self = this;
      _self.loading.emitLoading(true);
      if((<any>event.tipoEnte).value)
        event.tipoEnte = (<any>event.tipoEnte).value;
      _self.service.modificaContatto(event).subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
        {
          _self.doSearch();
          _self.selected = null;
          _self.loading.emitLoading(false);
        }
        else
          _self.loading.emitLoading(false);
        _self.selected = null;
      });
    }else{
      this.selected=null;
    }
  }
}
