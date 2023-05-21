import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { Vincolistica } from './../../../../shared/models/models';

@Component({
  selector: 'app-vincolistica',
  templateUrl: './vincolistica.component.html',
  styleUrls: ['./vincolistica.component.scss']
})
export class VincolisticaComponent extends AbstractTab implements OnInit, OnChanges {

  constraintRadio: any
  @Input() casella: { [key: string]: Partial<SelectOption>[] };
  @Input("vincolistica") vincolisticaInput: Vincolistica;

  constructor(private fb: FormBuilder) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.vincolistica && changes.vincolisticaInput && changes.vincolisticaInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.mappaOggettoForm();
    this.vincolistica.disable();
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.vincolistica) {
      ViewMapper.mapObjectToForm(this.vincolistica, this.fascicolo.schedaTecnica.vincolistica);
    }
  }

  get vincolistica() {
    return (this.mainForm.get('vincolistica') as FormGroup);
  }

  buildForm() {
    this.mainForm.addControl('vincolistica', this.fb.group({
      sottopostoATutela: [null, Validators.required],
      specificaVincolo: [[]],
      altriVincolo: [''/* , Validators.required */]
    }));
  }

  get showSpecificaVincolo() {

    const specificaVincolo = this.vincolistica.get('specificaVincolo');
    const sottopostoValue = this.vincolistica.get('sottopostoATutela').value;

    sottopostoValue === true ?
      specificaVincolo.setValidators([/* Validators.required */]) :
      (specificaVincolo.clearValidators(), specificaVincolo.patchValue([]));

    return sottopostoValue === true ? true : false;

  }

  public settaValidators(): void {
    if (this.vincolistica.get('sottopostoATutela').value) {
      this.vincolistica.get('specificaVincolo').setValidators([Validators.required]);
      this.vincolistica.get('specificaVincolo').updateValueAndValidity();
    }
    else {
      this.vincolistica.get('specificaVincolo').clearValidators();
      this.vincolistica.get('specificaVincolo').updateValueAndValidity();
    }
  }

}
