import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { Inquadramento } from './../../../../shared/models/models';
import { ChangeDetectionStrategy, Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';

@Component({
  selector: 'app-inquadramento',
  templateUrl: './inquadramento.component.html',
  styleUrls: ['./inquadramento.component.scss']
})
export class InquadramentoComponent extends AbstractTab implements OnInit, OnChanges {
  @Input("inquadramento") inquadramentoInput: Inquadramento;

  constructor(private fb: FormBuilder,
    private stFormService:SchedaTecnicaFormService) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.inquadramento && changes.inquadramentoInput && changes.inquadramentoInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.mappaOggettoForm();
    this.stFormService.inquadramentoFormComplete$.next(true);
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.inquadramento) {
      ViewMapper.mapObjectToForm(this.inquadramento, this.fascicolo.schedaTecnica.inquadramento);
    }
  }

  get inquadramento() {
    return this.mainForm.get('inquadramento') as FormGroup;
  }

  buildForm(){
    this.mainForm.addControl('inquadramento', this.fb.group({
      destinazioneUso: [null, Validators.required],
      contestoPaesaggInterv: [null, Validators.required],
      morfologiaConPaesag: [null, Validators.required],
      destinazioneUsoSpecifica: ['', Validators.required],
      contestoPaesaggIntervSpecifica: ['', Validators.required],
      morfologiaConPaesagSpecifica: ['', Validators.required]
    }));
}

showFormControl(subordinate, superior, condition) {
  const subordinateControl = this.inquadramento.get(subordinate);
  const superiorValue = this.inquadramento.get(superior).value;

  return superiorValue === condition ?
    (subordinateControl.setValidators([Validators.required]), true) :
    (subordinateControl.clearValidators(), subordinateControl.patchValue(null), false);
}

}
