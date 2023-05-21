import { environment } from './../../../environments/environment';
import { FascicoloSearch } from './../model/entity/fascicolo.models';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { defaultSelectModel, SchemaRicerca } from '../model/model';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ComuneService } from 'src/app/services/comune.service';
import { CONST } from 'src/shared/constants';
import { GlobalService } from 'src/app/services/global.service';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable } from 'rxjs';
import { validDate } from '../validators/customValidator';
import { Projects } from '../model/enum';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';

@Component({
  selector: 'app-search-accordion',
  templateUrl: './search-accordion.component.html',
  styleUrls: ['./search-accordion.component.css']
})
export class SearchAccordionComponent implements OnInit 
{
  @Input() isPublic: boolean;
  @Input() schemaRicerca: SchemaRicerca;
  @Input() searchOpenInit:boolean=false;
  @Output() searchChange: EventEmitter<any> = new EventEmitter<any>();

  
  public searchForm: FormGroup;
  private codiceFascicoloAutocomplete: any;
  public comuneAutocomplete: any;
  public entiCompetenti: defaultSelectModel[];

  public it: any = CONST.IT;
  public tipiProcedimento$: Observable<SelectItem[]>;
  public tipiIntervento$: Observable<SelectItem[]>;
  public statiFascicolo$: Observable<SelectItem[]>;
  public esitiProvvedimento$: Observable<SelectItem[]>;
  public statiRegistrazione$: Observable<SelectItem[]>;
  public esitiVerifica$: Observable<SelectItem[]>;

  public MAX_YEAR = CONST.MAX_YEAR;
  public const=CONST;
  index: number = null;
	

  constructor(private formBuilder: FormBuilder,
    private autorizzazioniPaesaggistiche: AutorizzazioniPaesaggisticheService,
    private globalService: GlobalService,
    private comuneService: ComuneService,
    private localStorageSvc:LocalSessionServiceService,
    private translateService:TranslateService) {
     }

  ngOnInit() 
  {
    this.tipiIntervento$ = this.autorizzazioniPaesaggistiche.getTipiIntervento();
    this.tipiProcedimento$ = this.autorizzazioniPaesaggistiche.getAllTipiProcedimento();
    this.esitiProvvedimento$ = this.autorizzazioniPaesaggistiche.getEsitiProvvedimento(this.isPublic);
    this.statiFascicolo$ = this.autorizzazioniPaesaggistiche.getStatiFascicolo();
    this.statiRegistrazione$ = this.autorizzazioniPaesaggistiche.getRegistrationStatus();
    this.esitiVerifica$ = this.autorizzazioniPaesaggistiche.getEsitiVerifica();
    this.buildForm();
    let searchData=this.localStorageSvc.getValueOrNull(
      this.isPublic?LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY:
                    LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY);
    if(searchData){
      //Se vengono presi i parametri dal local storage bisogna convertire le date per evitare problemi al form
      if (searchData.dataRilascioAutorizzazioneDa) searchData.dataRilascioAutorizzazioneDa = new Date(searchData.dataRilascioAutorizzazioneDa);
      if (searchData.dataRilascioAutorizzazioneA) searchData.dataRilascioAutorizzazioneA = new Date(searchData.dataRilascioAutorizzazioneA);
      if (searchData.dataProtocolloA) searchData.dataProtocolloA = new Date(searchData.dataProtocolloA);
      if (searchData.dataProtocolloDa) searchData.dataProtocolloDa = new Date(searchData.dataProtocolloDa);
      this.searchForm.patchValue(searchData);
    }
  }

  /**
   * form per la ricerca 
   */
  public buildForm(): void
  {
    this.searchForm = this.formBuilder.group
    ({
      codice: new FormControl(null),
      tipologiaIntervento: new FormControl(null),
      dataRilascioAutorizzazioneDa: new FormControl(null, null),
      dataRilascioAutorizzazioneA: new FormControl(null, null),
      esito: new FormControl(null),
      ufficio: new FormControl(null),
      esitoVerifica: new FormControl(null),
      stato: new FormControl(null),
      statoRegistrazione: new FormControl(null),
      codiceInternoEnte: new FormControl(''),
      protocollo: new FormControl(''),
      numeroInternoEnte: new FormControl(''),
      dataProtocolloDa: new FormControl(null, null),
      dataProtocolloA: new FormControl(null, null),
      tipoProcedimento: new FormControl(null),
      comuneIntervento: new FormControl(null)
    });
    //setto i validatori custom per le date
    this.searchForm.get("dataRilascioAutorizzazioneDa").setValidators(validDate(null, this.searchForm.get("dataRilascioAutorizzazioneA")));
    this.searchForm.get("dataRilascioAutorizzazioneA").setValidators(validDate(this.searchForm.get("dataRilascioAutorizzazioneDa")));
    this.searchForm.get("dataProtocolloDa").setValidators(validDate(null, this.searchForm.get("dataProtocolloA")));
    this.searchForm.get("dataProtocolloA").setValidators(validDate(this.searchForm.get("dataProtocolloDa")));      
    
    if(this.isPareri)
    {
      //this.searchForm.get("tipologiaIntervento").disable();
      this.searchForm.get("tipoProcedimento").disable();
    }
  }

  public onClearControl(event: string, name: string): void 
  {
    this.searchForm.get(name).setValue('');
  }

  public searchCodiceFascicolo(event: any) 
  {
    this.autorizzazioniPaesaggistiche.getResultsRicercaCodiceFascicolo(event.query).subscribe(data => 
    {
      if(data.codiceEsito === CONST.OK)
        this.codiceFascicoloAutocomplete = data.payload;
    });
  }

  public searchComune(event: any, idselect: string) 
  {
    this.comuneService.cercaComuni(event.query, 50).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.comuneAutocomplete = response.payload;
    });
  }

  private workOnRowData(): FascicoloSearch
  {
    let search: FascicoloSearch = this.searchForm.getRawValue();
    if (search.comuneIntervento) search.comuneIntervento = (search.comuneIntervento as any).value;
    if (search.dataRilascioAutorizzazioneDa) search.dataRilascioAutorizzazioneDa = new Date(search.dataRilascioAutorizzazioneDa).toDateString();
    if (search.dataRilascioAutorizzazioneA) search.dataRilascioAutorizzazioneA = new Date(search.dataRilascioAutorizzazioneA).toDateString();
    if (search.dataProtocolloDa) search.dataProtocolloDa = new Date(search.dataProtocolloDa).toDateString();
    if (search.dataProtocolloA) search.dataProtocolloA = new Date(search.dataProtocolloA).toDateString();
    return search;
  }

  get isPareri(): boolean { return CONST.isPareri(); }

  onSubmit() { 
    //salvo i parametri di ricerca nella localstorage
    let search:FascicoloSearch=this.searchForm.getRawValue();
    this.localStorageSvc.storeValue(this.isPublic?LocalSessionServiceService.PUBLIC_SEARCH_FILTER_DATA_KEY:
      LocalSessionServiceService.PRIVATE_SEARCH_FILTER_DATA_KEY,
      search);
    this.searchChange.emit(this.workOnRowData()); 
  }
  onReset()   { 
    this.searchForm.reset(); 
  }
}
