import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { requiredDependsOn, requiredIfNot } from 'src/app/shared/validators/customValidator';
import { ComuneService } from '../../services/stato_citta_prov_services/comune.service';
import { NazionalitaService } from '../../services/stato_citta_prov_services/nazionalita.service';
import { ProvinciaService } from '../../services/stato_citta_prov_services/provincia.service';

@Component({
  selector: 'app-stato-citta-prov-field',
  templateUrl: './stato-citta-prov-field.component.html',
  styleUrls: ['./stato-citta-prov-field.component.css']
})
export class StatoCittaProvFieldComponent implements OnInit {
  @Input() formGroupSet: FormGroup;
  //nomi dei formControl all'interno del formgroup
  @Input() nazionalita: string;
  @Input() provincia: string;
  @Input() comune: string;
  @Input() comuneEstero: string;
  @Input() label: string;
  @Input() submitted:boolean; //indica se accendere la validazione a seguito di submitted

  @Input() disable: boolean = false; //eventuale disabilitazione dall'esterno


  public isItalia: boolean = false;

  public comuneAutocomplete: string[];
  public provinciaAutocomplete: string[];
  public nazionalitaAutocomplete: string[];


  constructor(private provinciaService: ProvinciaService,
              private comuneService: ComuneService,
              private nazionalitaService: NazionalitaService
  ) { }

  ngOnInit() {
    let val = this.formGroupSet.controls[this.nazionalita].value;
    if (val && (val.value == "1" || (val.toString().toUpperCase() == "ITALIA"))) {
      this.isItalia = true;
    }
    this.setupValidators();
    this.formGroupSet.valueChanges.subscribe(next => console.log(next));
  }

  public onClear(idselect: string) {
    this.formGroupSet.controls[idselect].disable();
    this.formGroupSet.controls[idselect].setValue(null);
  }

  public setNazionalita(event: any, provincia: string, comune: string, comuneEstero: string): void {
    this.formGroupSet.controls[comune].setValue(null)
    this.formGroupSet.controls[provincia].setValue(null);
    if (event.value == 1) { //1=valore dell'Italia
      this.isItalia = true;
      this.formGroupSet.controls[comune].disable();
      this.formGroupSet.controls[comuneEstero].disable();
      this.formGroupSet.controls[provincia].enable();
      /* if (this.formGroupSet.controls[this.nazionalita].validator) {
        this.formGroupSet.controls[comune].clearValidators();
        this.formGroupSet.controls[comune].setValidators(Validators.required);
        this.formGroupSet.controls[provincia].clearValidators();
        this.formGroupSet.controls[provincia].setValidators(Validators.required);
      } */
    } else {
      this.isItalia = false;
      this.formGroupSet.controls[comuneEstero].enable();
      this.formGroupSet.controls[provincia].disable();
      this.formGroupSet.controls[comune].disable();
      /* if (this.formGroupSet.controls[this.nazionalita].validator) {
        this.formGroupSet.controls[comuneEstero].clearValidators();
        this.formGroupSet.controls[comuneEstero].setValidators(Validators.required);
        this.formGroupSet.controls[comuneEstero].updateValueAndValidity();
        this.formGroupSet.controls[provincia].clearValidators();
        this.formGroupSet.controls[comune].clearValidators();
        this.formGroupSet.controls[provincia].updateValueAndValidity();
        this.formGroupSet.controls[comune].updateValueAndValidity();
      } */
    }
    this.formGroupSet.updateValueAndValidity();
  }

  public abilitaComune(event: any, idselect: string): void {
    this.formGroupSet.controls[idselect].enable();
  }

  public searchProv(event: any) {
    this.provinciaService.getResults(event.query).then(data => {
      this.provinciaAutocomplete = data;
    });
  }

  public searchNaz(event: any) {
    this.nazionalitaService.getResults(event.query).then(data => {
      this.nazionalitaAutocomplete = data;
    });
  }

  public search(event: any, idselect: string) {
    let parentId = null;
    if (idselect)
      parentId = this.formGroupSet.controls[idselect].value;//this.fascicoloForm.controls[idselect].value;
    this.comuneService.getResults(event.query, parentId != null ? parentId.value : null).then(data => {
      this.comuneAutocomplete = data;
    });
  }
  
  attivaValidazione() {
    let ret=this.submitted &&
    (this.formGroupSet.get(this.nazionalita).invalid ||
    this.formGroupSet.get(this.comune).invalid ||
    this.formGroupSet.get(this.provincia).invalid ||
    this.formGroupSet.get(this.comuneEstero).invalid ) &&
    (this.formGroupSet.get(this.nazionalita).dirty ||
    this.formGroupSet.get(this.provincia).dirty ||
    this.formGroupSet.get(this.comune).dirty ||
    this.formGroupSet.get(this.comuneEstero).dirty ||
    this.formGroupSet.get(this.nazionalita).touched ||
    this.formGroupSet.get(this.comune).touched ||
    this.formGroupSet.get(this.comuneEstero).touched ||
    this.formGroupSet.get(this.provincia).touched);
    return ret;
  }

  public setupValidators(): void
  {
    let nazione = this.formGroupSet.get(this.nazionalita);
    let citta = this.formGroupSet.get(this.comuneEstero);
    let comune = this.formGroupSet.get(this.comune);
    let provincia = this.formGroupSet.get(this.provincia);

    provincia.setValidators(requiredDependsOn(nazione, 1, 'value'));
    comune.setValidators(requiredDependsOn(nazione, 1, 'value'));
    citta.setValidators(requiredIfNot(nazione, 1, 'value'));
  }

}
