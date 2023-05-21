import { LocalSessionServiceService } from './../../../services/local-session-service.service';
import { downloadFile } from 'src/app/components/functions/genericFunctions';
import { SearchConfiguration } from './../../form-search/configuration/search.configuration';
import { SearchFields } from './../../model/form-search/formSearch.models';
import { UlterioreDocumentazioneSearch } from './../../model/search/search.models';
import { Observable } from 'rxjs';
import { DataService } from './../../../services/data.service';
import { AutorizzazioniPaesaggisticheService } from './../../../services/autorizzazioni-paesaggistiche.service';
import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { CONST } from 'src/shared/constants';
import { AllegatoUlterioreDocumentazione } from '../../model/entity/allegato.models';

@Component({
  selector: 'app-form-ulteriore-documentazione',
  templateUrl: './form-ulteriore-documentazione.component.html',
  styleUrls: ['./form-ulteriore-documentazione.component.css']
})
export class FormUlterioreDocumentazioneComponent implements OnInit {
  @Input("idFascicolo") id: number;
  
  private searchFilters: any;

  public triggerSearch: EventEmitter<boolean> = new EventEmitter<boolean>();

  public entiOpt$: Observable<SelectItem[]>;
  public configuration: SearchFields[];
  public toggleInsert: boolean = false;
  public docs: AllegatoUlterioreDocumentazione[] = [];
  public rows: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public totalRecords: number = 0;
  public itemsTotal: number = 0;
  public initForm: {[key: string]: any} = {};
  private sortField: string = "data_caricamento";
  private sortOrder: string = "DESC";
  public index:number;
  public colonneTable: any[] =
  [
    { header: "File" },
    { header: "Titolo documento" },
    { header: "Descrizione contenuto" },
    { header: "Data condivisione" },
    { header: "Destinatario notifica" },
    { header: "Direzione" },
    { header: "Documento visibile a" },
    { header: "Inserito da" },
    { header: "Impronta hash(SHA256)" }
  ]
  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private autpaeSvc: AutorizzazioniPaesaggisticheService,
              private dataService: DataService,
              private lss: LocalSessionServiceService) {
  }

  ngOnInit() 
  {
    let template = CONST.entiTemplate;
    let gruppoScelto: string = this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    let tipoGruppoScelto: string = gruppoScelto.split("_")[0] + "_";
    let sentinella: boolean = false;
    this.configuration = SearchConfiguration.getUlterioreDocumentazioneSearchFields();
    this.configuration[3].extra.options.forEach((item: SelectItem, index, array) =>
    {
      if (item.value === tipoGruppoScelto)
      {
        array[index].disabled = true;
        sentinella = true;
      }
    });
    if(sentinella)
      this.initForm["visibileA"] = [tipoGruppoScelto];
    this.entiOpt$ = new Observable<SelectItem[]>((observer) => 
    {
      template.forEach(elem => 
      {
        if (elem.value === tipoGruppoScelto) 
          elem.disabled = true;
      });
      observer.next(template);
      observer.complete();
    });
    this.ricerca({});
  }

  public submitted(event: any) {
    event.idFascicolo = this.id;
    /* this.autpaeSvc.postUlterioreDocumentazione(event).subscribe(result => {
      if (result.codiceEsito === CONST.OK && result.payload) {
        this.docs.push(result.payload);
        this.toggleInsert = false;
        this.alertData.content="generic.operazioneOk";
        this.alertData.title="generic.info";
        this.alertData.extraData={};
        this.alertData.isConfirm=false;
        this.alertData.typ="success";
        this.alertData.display=true;
      }
    }); */
    this.autpaeSvc.postUlterioreDocumentazioneMulti(event.doc, event.files).subscribe(result => {
      if (result.codiceEsito === CONST.OK && result.payload) {
        //this.docs.push(result.payload);
        this.ricerca(this.searchFilters);
        this.toggleInsert = false;
        this.alertData.content="generic.operazioneOk";
        this.alertData.title="generic.info";
        this.alertData.extraData={};
        this.alertData.isConfirm=false;
        this.alertData.typ="success";
        this.alertData.display=true;
      }
    });
  }

  public canceled(event: any) {
    this.toggleInsert = false;
  }

  public ricerca(event: any): void {
    let filter: UlterioreDocumentazioneSearch = new UlterioreDocumentazioneSearch();
    filter.idFascicolo = this.id;
    filter.colonna = this.sortField;
    filter.direzione = this.sortOrder;
    filter.page = this.page;
    filter.limit = this.rows;

    this.searchFilters = filter;

    let keys = Object.keys(event);
    if (keys) {
      keys.forEach(key => {
        filter[key] = event[key] && event[key].value ? event[key].value : event[key];
      });
    }

    
    this.autpaeSvc.ricercaUlterioreDocumentazione(filter).subscribe(result => {
      if (result.codiceEsito === CONST.OK && result.payload && result.payload.list) {
        this.docs = result.payload.list;
        this.totalRecords = result.payload.count;
      }
      else this.totalRecords = 0;
    });
  }

  public reset(event: any): void {
    this.ricerca({});
  }

  public updateData(event: any): void {
    if(event && event.sortField){
      this.sortField = event.sortField;
      this.sortOrder = event.sortOrder === 1 ? "ASC" : "DESC";
    }
    this.page = event.first / event.rows + 1;
    this.rows = event.rows;

    this.triggerSearch.emit(true);
  }

  changeRows(rowsOnPage: number): void {
    this.rows = rowsOnPage;
    this.page = 1;
    this.triggerSearch.emit(true);
  }

  pageChangedAction(pageNumber: number): void {
    this.page = pageNumber;
    this.triggerSearch.emit(true);
  }

  get templateEnti() { return CONST.entiTemplate; }

  public download(id: string, fileName: string): void {
    this.autpaeSvc.downloadAllegatoFascicolo(this.id+'',id).subscribe(
      response => {
        if (response.ok) {
          downloadFile(response.body, fileName);
        }
      },
      error => {
        console.log(error.message);
      }
    );
  }
  callbackAlert($event:any){}

}
