import { formatDate } from '@angular/common';
import {  Fascicolo, Role, User } from 'src/app/shared/models/models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { Observable } from 'rxjs';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';

@Component({
  selector: 'app-comunicazioni-ricerca',
  templateUrl: './comunicazioni-ricerca.component.html',
  styleUrls: ['./comunicazioni-ricerca.component.scss']
})
export class ComunicazioniRicercaComponent implements OnInit {
  //@Input() meta: { [key: string]: SelectOption[] };
  @Input() currentUser: User;
  @Output() searchChange: EventEmitter<Fascicolo> = new EventEmitter<Fascicolo>();
  @Output() annullaSearch: EventEmitter<any> = new EventEmitter<any>();
  searchProcedureTypeResults$: Observable<string[]>;
  activitiesToBePerformedList: SelectOption[];
  searchForm: FormGroup;
  roles = Role;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.createSearchForm();
  }

  createSearchForm() {
    this.searchForm = this.formBuilder.group({//come da CorrispondenzaSearch
      mittente: [''],
      oggetto: [''],
      destinatario: [''],
      riservata: [null], //boolean
      comunicazioneInterna: [null],
      dataInvioDa: [''],
      dataInvioA: ['']
    });
  }

  onSubmit() {
    const form = this.searchForm.getRawValue();
    Object.keys(form).forEach((key) => {
      if (!form[key]) {
        delete form[key];
      }
    });
    let campiBoolean=['riservata','comunicazioneInterna'];
    if (form.dataInvioDa) form.dataInvioDa = new Date(form.dataInvioDa).toDateString();
    if (form.dataInvioA) form.dataInvioA = new Date(form.dataInvioA).toDateString();
    campiBoolean.forEach(key=>{
      if(form[key]){
        if(form[key]=="SI"){
          form[key]=true;
        }else if (form[key]=="NO"){
          form[key]=false;
        }
      }
    });
    this.searchChange.emit(form);
  }

  onClose() {
    this.searchForm.reset();
    this.searchForm.setValue({
      mittente: '',
      oggetto: '',
      destinatario: '',
      riservata: null,
      comunicazioneInterna: null,
      dataInvioDa: '',
      dataInvioA: ''
    });
    this.searchChange.emit(this.searchForm.value);
  }
}
