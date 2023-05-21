import { CONST } from './../../constants';
import { AutocompleteService } from './services/autocomplete.service';
import { LoadingService } from './../../services/loading.service';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { swipe } from './functions/formSearch.functions';
import { InputType, ListInputsOptions } from './model/form-search.model';
import { DateOptions, SearchFields } from '../../models/form-search.configurations.models';


@Component({
  selector: 'app-form-search',
  templateUrl: './form-search.component.html',
  styleUrls: ['./form-search.component.css'],
  providers: [AutocompleteService]
})
export class FormSearchComponent implements OnInit, OnDestroy
{
  /**
   * numero di colonne in cui si devisera visualizzare il form di ricerca
   */
  @Input("nColumns") nColumns: number;
  /**
   * configurazione da utilizzare per la realizzazione del form di ricerca
   */
  @Input("configuration") configuration: SearchFields[];
  /**
   * Mappa chiave valore con cui viene inizializzato il form. è importante che le chiavi
   * corrispondano pari pari al nome del form control name assegnato all'input
   */
  @Input("initData") initData: {[key: string]: any} = {};

  /**
   * Opzione per non mostrare il pulsante di ricerca
   * ATTENZIONE: se non viene passato nulla come parametro a a triggerSearchEvent verrà sollevata un'eccezione
   */
  @Input("showSearchButton") showSearchButton: boolean;
  /**
   * Opzione per non mostrare il pulsante di reset.
   * ATTENZIONE: se non viene passato nulla come parametro a a triggerResetEevent verrà sollevata un'eccezione
   */
  @Input("showResetButton") showResetButton: boolean;

  /**
   * Observable che verrà utilizzata per capire quando far triggerare l'eveto search a comando dall'esterno
   */
  @Input("triggerSearchEvent") triggerSearch$: Observable<boolean>;
  /**
  * Observable che verrà utilizzata per capire quando far triggerare l'eveto reset a comando dall'esterno
  */
  @Input("triggerResetEvent") triggerReset$: Observable<boolean>;

  /**
   * Label per customizzare il bottone di ricerca
   */
  @Input("searchLabel") searchLabel?: string;
  /**
   * Label per customizzare il bottone di reset form
   */
  @Input("resetLabel") resetLabel?: string;
  /**
   * Mappa con cui viene inizializzato il form di ricerca
   */
  @Input("init") initMap: { [key: string]: any} = {};

  /**
   * Variabile che permetterà il trigger dell'evento "onReset", restituendo a chi ha triggerato 
   * l'evento un booleano
   */
  @Output("onReset") resetEvt$: EventEmitter<boolean> = new EventEmitter<boolean>();
  /**
  * Variabile che permetterà il trigger dell'evento "onSearch" restituendo a chilo ha triggerato
  * l'evento un oggetto corrispondente ai parametri della search
  */
  @Output("onSearch") searchEvt$: EventEmitter<any> = new EventEmitter<any>();

  private subscriberSearch: Subscription;
  private subscriberReset: Subscription;

  private _organizedFormData: SearchFields[][] = [];
  public form: FormGroup;
  public ready: boolean = false;

  public suggestions: { [key: string]: string[] } = {};

  constructor(private formBuilder: FormBuilder,
              private loading: LoadingService,
              private autocomplete: AutocompleteService) { }

  ngOnInit()
  {
    let errors = this.validateConfiguration();
    if (!errors)
    {
      if (this.nColumns === undefined || this.nColumns === null || this.nColumns <= 0 || this.nColumns > 12)
        this.nColumns = 3;
      this.showResetButton = this.showResetButton === undefined || this.showResetButton == null ? true: this.showResetButton;
      this.showSearchButton = this.showSearchButton === undefined || this.showSearchButton == null ? true : this.showSearchButton;

      this.buildForm();
      this.form.patchValue(this.initMap);
      this.handleConfiguration();

      if(this.triggerSearch$)
      {
        this.subscriberSearch = this.triggerSearch$.subscribe(trigger =>
        {
          this.search();
        });
      }
      if(this.triggerReset$)
      {
        this.subscriberReset = this.triggerReset$.subscribe(trigger =>
        {
          this.reset();
        });
      } 
    }
    else
    {
      console.error("ATTENZIONE: parametro 'configuration' errato: ", errors);
      throw new Error();
    }
  }

  ngOnDestroy(): void
  {
    if(this.subscriberSearch)
      this.subscriberSearch.unsubscribe();
    if(this.subscriberReset)
      this.subscriberReset.unsubscribe();
  }

  /**
   * Metodo che lancia l'evento "onSearch" facendo tornare i valori contenuti nel form
   */
  search(): void { this.searchEvt$.emit(this.form.getRawValue()); }

  /**
   * Metodo che resetta il form e lancia l'evento onReset
   */
  reset(): void
  {
    this.form.reset();
    this.resetEvt$.emit(true);
  }

  /**
   * Metodo utile a buildare il form con le informazioni passate in "configuration"
   */
  private buildForm(): void
  {
    let formConf = {};
    this.configuration.forEach(field => 
    {
      if(field.type === this.dateRange)
      {
        let initDA = this.initData && this.initData[field.formControlName + "Da"] ? this.initData[field.formControlName + "Da"] : null;
        let initA = this.initData && this.initData[field.formControlName + "A"] ? this.initData[field.formControlName + "A"] : null; 
        formConf[field.formControlName + "Da"] = new FormControl(initDA);
        formConf[field.formControlName + "A"] = new FormControl(initA);
      }
      else
      {
        let init = this.initData && this.initData[field.formControlName] ? this.initData[field.formControlName] : null;
        formConf[field.formControlName] = new FormControl(init);
      }
    });
    this.form = this.formBuilder.group(formConf);
    this.ready = true;
  }

  /**
   * Metodo utile a trasformare la configurazione passata in input al component in una matrice (avente numero di colonne pari a "nColumns") 
   * che verrà utilizzata per creare coerentemente il form di ricerca
   * @returns true - Se gli input sono coerenti fra loro e tutto va a buon fine
   * @returns false - Se la configurazione passata in input al component è invalida o incoerente
   */
  private handleConfiguration(): void
  {
    let i: number = 0;
    let row: number = 0;
    while (i < this.configuration.length)
    {
      this._organizedFormData[row] = [];
      let column: number = 0;
      while (column < this.nColumns && i < this.configuration.length)
      {
        let field: SearchFields = this.configuration[i];
        if (field.type === InputType.dateRange && this.nColumns > 1 && this.nColumns > (i + 2))
        {
          if ((i + 1) < this.configuration.length)
          {
            swipe(this.configuration, 1, i + 1);
            field = this.configuration[i];
          }
          else
          {
            row++;
            this._organizedFormData[row] = [];
          }
        }
        this._organizedFormData[row].push(field);
        if (field.type === InputType.dateRange && this.nColumns > 1)
          column += 2;
        else
          column++;
        i++;
      }
      row++;
    }
  }

  /**
   * Metodo che valida la configurazione passata in input verificando che gli input configurati siano tutti corretti 
   * in caso di errore fa tornare una lista di tutti i campi errati con il relativo errore commesso.
   * 
   * @returns { formControlName: string, error: string }[] in caso di errore
   * @returns null in caso non siano stati trovati errori
   * 
   */
  private validateConfiguration(): { formControlName: string, error: string }[]|null
  {
    let errors: { formControlName: string, error: string }[] = [];
    let ok: boolean = this.configuration != undefined && this.configuration != null && this.configuration.length > 0;
    if(ok)
    {
      let dateInputs = [this.date, this.dateRange];
      let withOptionInputs = [this.checkbox, this.radio, this.dropdown, this.multiselect];
      this.configuration.forEach(field =>
      {
        if (dateInputs.includes(field.type) &&  field.extra &&
           (field.extra as DateOptions).minDate > (field.extra as DateOptions).maxDate)
        {
          errors.push({ formControlName: field.formControlName, error: "min date deve essere minore di max date" });
        }
        if (withOptionInputs.includes(field.type) && field.extra && !(field.extra as ListInputsOptions).options)
        {
          errors.push({ formControlName: field.formControlName, error: "options deve essere valorizzato" });
        }
        if(this.configuration.filter(f => f.formControlName === field.formControlName).length > 1 &&
          !errors.find(f => f.formControlName && f.error === "formControlName duplicato"))
        {
          errors.push({ formControlName: field.formControlName, error: "formControlName duplicato" });
        }
      });
    }
    else
    {
      errors.push({formControlName: "all", error: "configurazione vuota"});
    }
    return errors.length > 0 ? errors : null;
  }

  public getRangeDate(field: SearchFields, direction: "Da"|"A"): Date
  {
    //form.controls[col.formControlName + 'A'].value ? form.controls[col.formControlName + 'A'].value : col.extra.maxDate
    let controlName = field.formControlName + direction;
    if(this.form && field && this.form.get(controlName) && this.form.get(controlName).value)
      return this.form.get(controlName).value
    else return null;
  }

  public wrapAndExecute(endpoint: string, query: string, key: string, other: any): void 
  { 
    other = other ? other : {};
    query = query.toUpperCase();
    let params = other;
    params[key] = query;
    this.loading.emitLoading(true);
    this.autocomplete.doCall(endpoint, params).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.suggestions[key] = response.payload;
      this.loading.emitLoading(false);
    });
  }

  get organizedFormData(): SearchFields[][] { return this._organizedFormData; }

  get classCol(): string { return "col-lg-" + (12 / this.nColumns); }


  /**
   * Metodi per ottenere gli input
   */
  get text(): InputType { return InputType.text; }
  get dateRange(): InputType { return InputType.dateRange; }
  get date(): InputType { return InputType.date; }
  get multiselect(): InputType { return InputType.multiselect; }
  get dropdown(): InputType { return InputType.dropdown || InputType.select; }
  get radio(): InputType { return InputType.radio; }
  get checkbox(): InputType { return InputType.checkbox; }
  get textarea(): InputType { return InputType.textarea; }
  get autocomlete(): InputType { return InputType.autocomplete; }
}
