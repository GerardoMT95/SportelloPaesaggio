import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { EffetiMitigazione } from './../../../../shared/models/models';
import { Component, OnInit, Input, ChangeDetectionStrategy, OnChanges, SimpleChanges } from '@angular/core';
import { FormControlName, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-effetti-cons-mitigazione',
  templateUrl: './effetti-cons-mitigazione.component.html',
  styleUrls: ['./effetti-cons-mitigazione.component.scss']
})
export class EffettiConsMitigazioneComponent extends AbstractTab implements OnInit, OnChanges {

  @Input("effettiMitigazione") effettiMitigazione: EffetiMitigazione;

  constructor(private fb: FormBuilder) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.effetiMitigazione && changes.effettiMitigazione && changes.effettiMitigazione.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.effetiMitigazione.disable();
    this.mappaOggettoForm();
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.effetiMitigazione) {
      ViewMapper.mapObjectToForm(this.effetiMitigazione, this.fascicolo.schedaTecnica.effetiMitigazione);
    }
  }

  get effetiMitigazione() {
    return (this.mainForm.get('effetiMitigazione') as FormGroup);
  }

  buildForm() {
    this.mainForm.addControl('effetiMitigazione', this.fb.group({
      descrizione: ['', Validators.required],
      effeti: ['', Validators.required],
      misure: ['', Validators.required],
      contenutiPercettivi: ['', Validators.required]
    }));
  }
}
