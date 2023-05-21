import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Fascicolo, AttivitaDaEspletareEnum, AttivitaDaEspletareEnumLabels } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';

@Component({
  selector: 'app-istanze-search',
  templateUrl: './istanze-search.component.html',
  styleUrls: ['./istanze-search.component.scss']
})
export class IstanzeSearchComponent implements OnInit {
  //@Input() meta: { [key: string]: SelectOption[] };
  @Output() searchChange: EventEmitter<Fascicolo> = new EventEmitter<Fascicolo>();
  @Output() annullaSearch:  EventEmitter<any> = new EventEmitter<any>();
  @Input() typeProcedimento:SelectOption[];
  searchProcedureTypeResults$: Observable<string[]>;
  activitiesToBePerformedList: SelectOption[];
  searchForm: FormGroup;
  /*tipoOptions$: Observable<SelectOption[]>;
  tipoOptions: any[];
  enteOptions$: Observable<SelectOption[]>;*/


  constructor(private formBuilder: FormBuilder,private   httpDominio: HttpDominioService) { }

  ngOnInit() {
    this.createSearchForm();
    this.activitiesToBePerformedList=[];
    AttivitaDaEspletareEnumLabels.forEach((value,key)=>this.activitiesToBePerformedList.push({value:key,description:value}));
    /*this.activitiesToBePerformedList = [
      {value: AttivitaDaEspletareEnum.COMPILA_DOMANDA, description: 'Compila Domanda'},
      {value: AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA, description: 'Genera Stampa Domanda'},
      {value: AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI, description: 'Allega Documenti Sottoscritti'},
      {value: AttivitaDaEspletareEnum.IN_ATTESA_DI_PROTOCOLLAZIONE, description: 'In Attesa Di Protocollazione'},
      {value: AttivitaDaEspletareEnum.IN_PREISTRUTTORIA, description: 'Istanza Presentata'},
    ];*/

  }

  createSearchForm(){
    this.searchForm = this.formBuilder.group({
      likeCodicePraticaAppptr: '',
      tipoProcedimento: '',
      oggetto: '',
      attivitaDaEspletare: ''
    });
  }
  //searchProcedureType(event) {
  //}

  onSubmit() {
    const form = this.searchForm.value;
    Object.keys(form).forEach(key => {
      if (!form[key]) {
        delete form[key];
      }
    });
    console.log(form, "FORM");
    this.searchChange.emit(form);
  }

  onClose() {
    this.searchForm.reset();
    this.searchChange.emit(this.searchForm.value);
    //this.annullaSearch.emit();

  }

}
