import { GroupType } from './../../../../shared/models/models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { Fascicolo } from 'src/app/shared/models/models';
import { Observable } from 'rxjs';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'app-documentazione-ricerca',
  templateUrl: './documentazione-ricerca.component.html',
  styleUrls: ['./documentazione-ricerca.component.scss']
})
export class DocumentazioneRicercaComponent implements OnInit {
  //@Input() meta: { [key: string]: SelectOption[] };
  @Input() meta: SelectItem[];
  @Output() searchChange: EventEmitter<Fascicolo> = new EventEmitter<Fascicolo>();
  @Output() annullaSearch: EventEmitter<any> = new EventEmitter<any>();
  searchProcedureTypeResults$: Observable<string[]>;
  activitiesToBePerformedList: SelectOption[];
  searchForm: FormGroup;
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.createSearchForm();
  }

  createSearchForm() {
    this.searchForm = this.formBuilder.group({
      titoloDocumento: '',
      descrizioneContenuto: '',
      destinatarioNotifica: '',
      //visibileA: { value: GroupType.EnteDelegato, description: "Ente Delegato" },
      visibileA: null,
      inseritoDa: '',
      dataCondivisioneDa: '',
      dataCondivisioneA: ''
    });
  }
  searchProcedureType(event) {}

  onSubmit() {
    /* this.fixSearchFormValue(); */
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
    /*this.searchForm.setValue({
      titoloDocumento: '',
      descrizioneContenuto: '',
      destinatarioNotifica: '',
      visibileA: null,
      inseritoDa: '',
      dataCondivisioneDa: '',
      dataCondivisioneA: ''
    });*/
    //this.searchChange.emit(this.searchForm.value);
    this.onSubmit();
  }
}
