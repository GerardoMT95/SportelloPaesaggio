import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { EventualiProcedimenti } from './../../../../shared/models/models';
import { Component, OnInit, Input, ChangeDetectionStrategy, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { Fascicolo } from 'src/app/shared/models/models';

@Component({
  selector: 'app-eventuali-procedimenti',
  templateUrl: './eventuali-procedimenti.component.html',
  styleUrls: ['./eventuali-procedimenti.component.scss']
})
export class EventualiProcedimentiComponent extends AbstractTab implements OnInit, OnChanges {

  @Input("eventualiProcedimenti") eventualiProcedimentiInput: EventualiProcedimenti;

  eventualiRadio: any[];
  constructor(private fb: FormBuilder) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.eventualiProcedimenti && changes.eventualiProcedimentiInput && changes.eventualiProcedimentiInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.mappaOggettoForm();
    this.eventualiProcedimenti.disable();
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.eventualiProcedimenti) {
      ViewMapper.mapObjectToForm(this.eventualiProcedimenti, this.fascicolo.schedaTecnica.eventualiProcedimenti);
    }
  }

  get eventualiProcedimenti() {
    return (this.mainForm.get('eventualiProcedimenti') as FormGroup);
  }

  buildForm() {
    this.mainForm.addControl('eventualiProcedimenti', this.fb.group({
      contenzisoAtto: [null, Validators.required],
      descrizione: ['']
    }));
  }

  get showDescrizione() {

    const procedamentiDescrizione = this.eventualiProcedimenti.get('descrizione');
    const contenzisoAttoValue = this.eventualiProcedimenti.get('contenzisoAtto').value;

    contenzisoAttoValue === true ?
      procedamentiDescrizione.setValidators([Validators.required]) :
      (procedamentiDescrizione.clearValidators(), procedamentiDescrizione.patchValue([]));

    return contenzisoAttoValue === true ? true : false;

  }
}
