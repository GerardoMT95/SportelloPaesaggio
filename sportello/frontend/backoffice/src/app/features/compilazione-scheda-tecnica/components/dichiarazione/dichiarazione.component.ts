import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { Dichiarazione } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-dichiarazione',
  templateUrl: './dichiarazione.component.html',
  styleUrls: ['./dichiarazione.component.scss']
})

export class DichiarazioneComponent extends AbstractTab implements OnInit, OnChanges {

  @Input("dichiarazione") dichiarazioneInput: Dichiarazione;

  constructor(private fb: FormBuilder) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.dichiarazione && changes.dichiarazioneInput && changes.dichiarazioneInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.dichiarazione.disable();
    this.mappaOggettoForm();
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.dichiarazione) {
      ViewMapper.mapObjectToForm(this.dichiarazione, this.fascicolo.schedaTecnica.dichiarazione);
    }
  }

  get dichiarazione() {
    return (this.mainForm.get('dichiarazione') as FormGroup);
  }

  buildForm() {
    this.mainForm.addControl('dichiarazione', this.fb.group({
      accettata: [null, Validators.requiredTrue],
      testo: [""],
      labelCb: [""],
    }));
  }
}
