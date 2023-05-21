import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { ProcedureEdilizie } from './../../../../shared/models/models';


@Component({
  selector: 'app-procedure-edilize',
  templateUrl: './procedure-edilize.component.html',
  styleUrls: ['./procedure-edilize.component.scss']
})
export class ProcedureEdilizeComponent extends AbstractTab implements OnInit, OnChanges {
  @Input() tipoDocumentoOptions$: Observable<SelectOption[]>;
  @Input("procedureEdilizie") procedureEdilizieInput: ProcedureEdilizie;

  buildingProceduresRadio: any;
  constructor(private fb: FormBuilder,
              private translateService: TranslateService) { super();}

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
    this.procedureEdilizie.disable();
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.procedureEdilizie) {
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

  public mostraForm(): void {
    let primaCheckbox: boolean = this.mainForm.get("procedureEdilizie").get("tipoStato").value.find(elem => elem.value === 1) ? true : false;
    let secondaCheckbox: boolean = this.mainForm.get("procedureEdilizie").get("tipoStato").value.find(elem => elem.value === 2) ? true : false;
    if (primaCheckbox && !secondaCheckbox) {
      this.procedureEdilizie.get("detagglio").setValidators([Validators.required]);
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
      this.procedureEdilizie.get("espressoData").clearValidators();
      this.procedureEdilizie.get("espressoData").setValue("");
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
    }
    else if (!primaCheckbox && secondaCheckbox) {
      this.procedureEdilizie.get("espressoData").setValidators([Validators.required]);
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
      this.procedureEdilizie.get("detagglio").clearValidators();
      this.procedureEdilizie.get("detagglio").setValue("");
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").clearValidators();
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
      this.procedureEdilizie.get("espressoData").setValue("");
      this.procedureEdilizie.get("espressoData").updateValueAndValidity();
      this.procedureEdilizie.get("detagglio").clearValidators();
      this.procedureEdilizie.get("detagglio").setValue("");
      this.procedureEdilizie.get("detagglio").updateValueAndValidity();
      this.procedureEdilizie.get("pressoData").clearValidators();
      this.procedureEdilizie.get("pressoData").setValue("");
      this.procedureEdilizie.get("pressoData").updateValueAndValidity();
    }
  }

}
