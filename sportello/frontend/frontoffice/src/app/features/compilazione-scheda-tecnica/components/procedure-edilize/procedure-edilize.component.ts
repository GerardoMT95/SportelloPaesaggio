import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { TranslateService } from '@ngx-translate/core';
import { ChangeDetectionStrategy, Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Fascicolo } from 'src/app/shared/models/models';
import { Observable } from 'rxjs';
import { ProcedureEdilizie } from './../../../../shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-procedure-edilize',
  templateUrl: './procedure-edilize.component.html',
  styleUrls: ['./procedure-edilize.component.scss']
})
export class ProcedureEdilizeComponent extends AbstractTab implements OnInit, OnChanges {
  @Input() tipoDocumentoOptions$: Observable<SelectOption[]>;
  @Input("procedureEdilizie") procedureEdilizieInput: ProcedureEdilizie;
  public currentDate:Date = CONST.TODAY;

  buildingProceduresRadio: any;
  constructor(private fb: FormBuilder,
              private translateService: TranslateService,
              private stFormService:SchedaTecnicaFormService) { super();}

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.procedureEdilizie && changes.procedureEdilizieInput && changes.procedureEdilizieInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  options: { value: string | number; label: string; hasDescription?: boolean; description?: string }[] = [
    {
      value: 1,
      label: this.translateService.instant('BUILDING_PROCEDURES_TAB.BUILDING_PROCEDURE_CHECKBOX')
    },
    {
      value: 2,
      label: this.translateService.instant('BUILDING_PROCEDURES_TAB.URBAN_OPINION_CHECKBOX')
    }
  ];

  ngOnInit() {
    this.buildingProceduresRadio = [
      { label: 'non soggetto a procedura edilizia', value: 1 },
      { label: 'soggetto a procedura edilizia', value: 2 }
    ];

    this.buildForm();
    this.mappaOggettoForm();
    this.mostraForm(true);
    this.stFormService.procedureEdilizieFormComplete$.next(true);
  }

  /**
   * mi arriva da fe senza label ed è gestito nelle checkbox come oggetto value/label
   * es. {value:1,label=""} se inizializzo così il formControl della checkbox, non riconosce 
   * il valore iniziale e quindi non accende sulla view la corrispondente casella
   * sono costretto a ricostruire con 
   * {value:1,label="la pratica è stata presentata presso"}
   */
  private ricostruisciTipoStato(){
    let tipoStatoSalvato:any[]=this.fascicolo.schedaTecnica.procedureEdilizie.tipoStato;
      if(tipoStatoSalvato && tipoStatoSalvato.length>0){
        let tipoStatoRicostruito=[];
        tipoStatoSalvato.forEach(el=>{
          this.options.forEach(opzione=>{
            if(el.value==opzione.value){
                tipoStatoRicostruito.push(opzione);
            }
          });
        });
        this.fascicolo.schedaTecnica.procedureEdilizie.tipoStato=tipoStatoRicostruito;
      }
  }
  
  private mappaOggettoForm(): void {
    //le 
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.procedureEdilizie) {
      this.ricostruisciTipoStato();
      ViewMapper.mapObjectToForm(this.procedureEdilizie, this.fascicolo.schedaTecnica.procedureEdilizie);
    }
  }

  get procedureEdilizie() {
    return this.mainForm.get('procedureEdilizie') as FormGroup;
  }
  buildForm() {
    this.mainForm.addControl(
      'procedureEdilizie',
      this.fb.group({
        tipoIntervento: ['', Validators.required],
        tipoStato: [[]],
        motivazione: [''],
        pressoData: [''],
        espressoData: [''],
        detagglio: ['']
      })
    );
  }

  showFormControl(subordinate, superior, condition) {
    const subordinateControl = this.procedureEdilizie.get(subordinate);
    const superiorValue = this.procedureEdilizie.get(superior).value;

    return superiorValue === condition ?
      (subordinateControl.setValidators([Validators.required]), true) :
      (subordinateControl.clearValidators(), subordinateControl.patchValue([]), false);
  }

  public mostraForm(noUpdateValue?:boolean): void {
    let primaCheckbox: boolean = this.mainForm.get("procedureEdilizie").get("tipoStato").value.find(elem => elem.value === 1) ? true : false;
    let secondaCheckbox: boolean = this.mainForm.get("procedureEdilizie").get("tipoStato").value.find(elem => elem.value === 2) ? true : false;
    if (primaCheckbox && !secondaCheckbox) {
      this.procedureEdilizie.get("detagglio").setValidators([Validators.required]);
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
      this.procedureEdilizie.get("espressoData").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("espressoData").setValue("");
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
    }
    else if (!primaCheckbox && secondaCheckbox) {
      this.procedureEdilizie.get("espressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
      this.procedureEdilizie.get("detagglio").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("detagglio").setValue("");
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("pressoData").setValue("");
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
    }
    else if (primaCheckbox && secondaCheckbox) {
      this.procedureEdilizie.get("detagglio").setValidators([Validators.required]);
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
      this.procedureEdilizie.get("espressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
    }
    else {
      this.procedureEdilizie.get("espressoData").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("espressoData").setValue("");
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
      this.procedureEdilizie.get("detagglio").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("detagglio").setValue("");
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").clearValidators();
      if(!noUpdateValue)
        this.procedureEdilizie.get("pressoData").setValue("");
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
    }
  }

}
