import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SearchConfiguration } from 'src/app/shared/components/form-search/configuration/search.configuration';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { InputType, SearchFields } from 'src/app/shared/models/form-search.configurations.models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { SezioneCatastaleSearch } from '../../models/admin-functions.models';

@Component({
  selector: 'app-sezioni-catastali',
  templateUrl: './sezioni-catastali.component.html',
  styleUrls: ['./sezioni-catastali.component.scss']
})
export class SezioniCatastaliComponent implements OnInit {

  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public page: number = 1;
  public itemsTotal: number = 0;
  public index: number;
  public sortBy: string = 'denominazione';
  public sortOrder: string = 'asc';
  public data: SelectOption[];
  public filters = new SezioneCatastaleSearch();
  public selected: SelectOption = null; //fa scattare la visualizzazione del dettaglio
  public configuration: SearchFields[];
  public valida: boolean = false;

  // Form dettaglio
  public form: FormGroup;

  constructor(private service: AdminFunctionsService,
    private loading: LoadingService,
    private translateService: TranslateService,
    private fb: FormBuilder,
    private dialog: CustomDialogService) { }
  /**
   * Inizializzazione componente
   */
  ngOnInit() {
    this.configuration = [
      {
        label: "nome",
        formControlName: "nome",
        className: "form-control",
        type: InputType.text,
        extra: {}
      }];
    this.resetFiltri();
    this.buildFormDettaglio();
  }

  /**
  *  Reset ricerca
  */
  public resetFiltri() {
    this.filters =
    {
      page: 1,
      limit: 5,
      colonna: 'codCatastale',
      direzione: 'ASC',
      nome: null,
    }
    this.doSearch();
  }
  /**
   * applicazione filtro di ricerca enti
   */
  public ricerca(event: any): void {
    this.filters = { ...this.filters, ...event }
    this.doSearch();
  }

  private doSearch(): void {
    let _self = this;
    console.log("parametri di ricerca: ", _self.filters);
    _self.loading.emitLoading(true);
    _self.service.ricercaSezioniCatastali(_self.filters).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
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
  public changeRows(rowsOnPage: number): void {
    this.filters.limit = rowsOnPage;
    this.filters.page = 1;
    this.doSearch();
  }
  /**
   * @description Cambia la pagina della tabella
   * @param pageNumber 
   */
  public pageChangedAction(pageNumber: number): void {
    this.filters.page = pageNumber;
    this.doSearch();
  }
  /**
   * @description Apertura dettaglio template, chiusura tabella
   * @param item 
   */

  /**
* Build form
*/
  buildFormDettaglio(): void
  {
    this.form = this.fb.group
      ({
        value: [null,[Validators.required,Validators.pattern(/[A-Z]\d{3}/)]],
        linked: [null,[Validators.required,Validators.pattern(/[A-Z]/)]],
        description: [null, [Validators.required]],
      })
  }


  public openDettaglio(item: SelectOption): void { 
    this.form.patchValue(item); 
    this.form.get('value').disable();
    this.form.get('linked').disable();
    this.selected=item;
  }

  public addItem(): void { 
    this.selected={value:null,linked:null,description:null};
    this.form.get('value').enable();
    this.form.get('linked').enable()
  }
  
  public back(): void {
    if (this.form.touched) {
      let m = this.translateService.instant('indirizziEnti.dialog.back.content');
      let t = this.translateService.instant('indirizziEnti.dialog.back.title');
      let f = (buttonPressed) => {
        if (buttonPressed == ButtonType.OK_BUTTON) { 
          this.selected=null; 
        }
      }
      this.dialog.showDialog(true, m, t, DialogButtons.CONFERMA_CHIUDI, f.bind(this), DialogType.INFORMATION);
    } else {
      this.selected=null;
    }

  }

  /**
  * Open dialog confirm save
  */
  public openSalva() {
    this.valida = true;
    if (this.form.valid) {
      this.valida = false;
      let formData = this.form.getRawValue();
      let m = this.translateService.instant('generic.confermaSalva');
      let t = this.translateService.instant('generic.warning');
      let f = (buttonPressed) => {
        if (buttonPressed == ButtonType.OK_BUTTON) {
          this.loading.emitLoading(true);
          this.service.saveOrUpdateSezioneCatastale(formData).subscribe(response => {
            this.loading.emitLoading(false);
            if (response.codiceEsito === CONST.OK && response.payload == 1) {
              this.form.reset();
              this.dialog.showDialog(true, 'generic.operazione_ok', 'generic.info', DialogButtons.CHIUDI, ()=>{}, DialogType.SUCCESS);        
              this.selected = null;
              this.doSearch();
            }else if(response.codiceEsito === CONST.OK){
              this.dialog.showDialog(true, 'SEZIONI_CATASTALI.ERRORE_AGGIORNAMENTO', 'generic.error', DialogButtons.CHIUDI, ()=>{}, DialogType.ERROR);        
            }
          });
        }
      }
      this.dialog.showDialog(true, m, t, DialogButtons.CONFERMA_CHIUDI, f.bind(this), DialogType.INFORMATION);
    }
  }

/**
  * Open dialog confirm delete
  */
 public askDelete(item:SelectOption) {
  let m = this.translateService.instant('generic.vuoiEliminare');
  let t = this.translateService.instant('generic.warning');
  let f = (buttonPressed) => {
    if (buttonPressed == ButtonType.OK_BUTTON) {
      this.loading.emitLoading(true);
      this.service.deleteSezioneCatastale(item).subscribe(response => {
        this.loading.emitLoading(false);
        if (response.codiceEsito === CONST.OK && response.payload == 1) {
          this.dialog.showDialog(true, 'generic.operazione_ok', 'generic.info', DialogButtons.CHIUDI, ()=>{}, DialogType.SUCCESS);        
          this.doSearch();
        }else if(response.codiceEsito === CONST.OK){
          this.dialog.showDialog(true, 'SEZIONI_CATASTALI.ERRORE_AGGIORNAMENTO', 'generic.error', DialogButtons.CHIUDI, ()=>{}, DialogType.ERROR);        
        }
      });
    }
  }
  this.dialog.showDialog(true, m, t, DialogButtons.CONFERMA_CHIUDI, f.bind(this), DialogType.INFORMATION);

 }

  }
