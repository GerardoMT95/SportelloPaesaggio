import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { SearchConfiguration } from 'src/app/components/form-search/configuration/search.configuration';
import { downloadFile } from 'src/app/components/functions/genericFunctions';
import { VPaesaggioProvvedimentiDTO } from 'src/app/components/model/entity/import.models';
import { SearchFields } from 'src/app/components/model/form-search/formSearch.models';
import { VPaesaggioProvvedimentiSearch } from 'src/app/components/model/search/search.models';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { CONST } from 'src/shared/constants';

@Component({
  selector: 'app-import-provvedimento',
  templateUrl: './import-provvedimento.component.html',
  styleUrls: ['./import-provvedimento.component.scss']
})
export class ImportProvvedimentoComponent implements OnInit {

  @Input("idFascicolo") id: number;
  @Output("fineImport") fineImport=new EventEmitter<string>();
  
  private searchFilters: any;

  
  public configuration: SearchFields[];
  public docs: VPaesaggioProvvedimentiDTO[] = [];
  public rows: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public totalRecords: number = 0;
  public itemsTotal: number = 0;
  public initForm: {[key: string]: any} = {};
  private sortField: string = "codice_trasmissione";
  private sortOrder: string = "DESC";
  public index:number;
  public colonneTable: any[] =
  [
    { header: "Codice trasmissione",field:"codiceTrasmissione" },
    { header: "Tipo documento" ,field:"descrizioneTipoDoc" },
    { header: "Nome file" ,field:"fileName"  },
    { header: "Oggetto" ,field:"oggetto" },
    { header: "Autorità procedente",field:"nomeAutoritaProcedente"  },
    { header: "Procedimento",field:"nomeProcedimento"   },
    { header: "Famiglia" ,field:"nomeFamiglia"  },
    { header: "Soggetto coinvolto",field:"nomeSoggettoCoinvolto" },
    
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

  constructor(private autpaeSvc: AutorizzazioniPaesaggisticheService
              ,private translateSvc:TranslateService
              ) {
  }

  ngOnInit() 
  {
    this.configuration = SearchConfiguration.importSportelloSearch();
    this.ricerca({});
  }

  
  public ricerca(event: any): void {
    let filter: VPaesaggioProvvedimentiSearch = new VPaesaggioProvvedimentiSearch();
    filter.idFascicoloPaesaggio=this.id+"";
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
    this.autpaeSvc.getDocumentiImportabili(filter).subscribe(result => {
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
    this.ricerca({});
  }

  changeRows(rowsOnPage: number): void {
    this.rows = rowsOnPage;
    this.page = 1;
    this.ricerca({});
  }

  pageChangedAction(pageNumber: number): void {
    this.page = pageNumber;
    this.ricerca({});
  }

  callbackAlert(event:any){
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
      switch (event.extraData.operazione) {
        case 'fineImport':
          this.fineImport.emit();
          break;
      default:
          break;
      }
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'confermaImport':
            this.doImport(this.alertData.extraData.rowData,this.id);
            break;
        default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  doImport(rowData: VPaesaggioProvvedimentiDTO,idFascicolo:number) {
    this.autpaeSvc.importaDaSportelloAmbiente(idFascicolo+"",rowData.codiceTrasmissione,rowData.idDoc)
    .subscribe(resp=>{
      if(resp.codiceEsito=="OK"){
        this.showMessageImportSuccess(resp.payload);
      }
    });
  }

  importa(event:VPaesaggioProvvedimentiDTO){
    this.alertData.content="import.conferma";
    this.alertData.title="generic.info";
    this.alertData.typ="info";
    this.alertData.isConfirm=true;
    this.alertData.extraData={operazione:"confermaImport",rowData:event};
    this.alertData.display=true;
  }

  /**
   * 
   * @param messaggio mostro all'utente 
   */
  showMessageImportSuccess(messaggio:string){
    this.alertData.content="import.successo";
    if(messaggio){
      this.alertData.content=this.translateSvc.instant('import.successoConMessaggio',{messaggio:messaggio});
    }
    this.alertData.title="generic.info";
    this.alertData.typ="success";
    this.alertData.isConfirm=false;
    this.alertData.extraData={operazione:"fineImport"};
    this.alertData.display=true;
  }

  public download(rowData:VPaesaggioProvvedimentiDTO): void {
    this.autpaeSvc.downloadDocumentoSportello(this.id+'',rowData.codiceTrasmissione,rowData.idDoc).subscribe(
      response => {
        if (response.ok) {
          downloadFile(response.body, rowData.fileName);
        }
      },
      error => {
        console.log(error.message);
      }
    );
  }

}
