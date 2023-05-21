import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { IndirizziEnti, TipologiaEnte } from 'src/app/shared/models/models';

@Component({
  selector: 'app-indirizzi-enti-search',
  templateUrl: './indirizzi-enti-search.component.html',
  styleUrls: ['./indirizzi-enti-search.component.scss']
})
export class IndirizziEntiSearchComponent implements OnInit {
  @Output() searchChange: EventEmitter<IndirizziEnti> = new EventEmitter<IndirizziEnti>();
  @Output() annullaSearch: EventEmitter<any> = new EventEmitter<any>();
  tipologiaEnteList: SelectOption[];
  searchForm: FormGroup;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.createSearchForm();
    this.tipologiaEnteList = [
      {value: TipologiaEnte.RegionePuglia, description: 'Regione Puglia'},
      {value: TipologiaEnte.ProvinciaDiFoggia, description: 'Provincia di Foggia'},
      {value: TipologiaEnte.Comuni, description: 'Comuni'}
    ];
  }

  createSearchForm() {
    this.searchForm = this.formBuilder.group({
      denominazione: [''],
      tipologia: ['']
    });
  }

  onSubmit() {
    const form = this.searchForm.value;
    Object.keys(form).forEach(key => {
      if (!form[key]) {
        delete form[key];
      }
    });
    console.log(form, 'FORM');
    this.searchChange.emit(form);
  }

  onClose() {
    this.searchForm.reset();
    this.annullaSearch.emit();

  }
}
