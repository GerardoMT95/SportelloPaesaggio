import {  Fascicolo } from 'src/app/shared/models/models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Role, StatoEnum } from './../../../../shared/models/models';

import { Observable } from 'rxjs';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';

@Component({
  selector: 'app-istanze-search',
  templateUrl: './istanze-search.component.html',
  styleUrls: ['./istanze-search.component.scss']
})
export class IstanzeSearchComponent implements OnInit {
  @Input() meta: { [key: string]: SelectOption[] };
  @Output() searchChange: EventEmitter<Fascicolo> = new EventEmitter<Fascicolo>();
  @Output() annullaSearch: EventEmitter<any> = new EventEmitter<any>();
  @Input() role: Role;
  searchProcedureTypeResults$: Observable<string[]>;
  activitiesToBePerformedList: SelectOption[];
  searchForm: FormGroup;
  roles = Role;
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.createSearchForm();
    this.activitiesToBePerformedList = [
      { value: StatoEnum.PresaInCarica, description: 'Presa in carico' },
      { value: StatoEnum.InLavorazione, description: 'In lavorazione' },
      /* {
        value: StatoEnum.RelazioneDaTrasmettere,
        description: 'Relazione da trasmettere alla Soprintendenza'
      },
      {
        value: StatoEnum.InAttesaDiParereSoprintedenza,
        description: 'In attesa di ricezione parere della Soprintendenza'
      },
      {
        value: StatoEnum.ParereSoprintendenzaTrasmesso,
        description: 'Parere della soprintendenza trasmesso'
      }, */
      { value: StatoEnum.InTrasmissione, description: 'In trasmissione' },
      { value: StatoEnum.Trasmessa, description: 'Trasmessa' }
    ];
  }

  createSearchForm() {
    this.searchForm = this.formBuilder.group({
      codiceFascicolo: '',
      tipoProcedimento: '',
      oggetto: '',
      stato: ''
    });
  }
  searchProcedureType(event) {}

  onSubmit() {
    const form = this.searchForm.value;
    Object.keys(form).forEach((key) => {
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
