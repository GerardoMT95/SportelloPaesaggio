import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { Component, OnInit, Input, ChangeDetectionStrategy, OnChanges, SimpleChanges } from '@angular/core';
import { Fascicolo, Dichiarazione } from 'src/app/shared/models/models';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';

@Component({
  selector: 'app-dichiarazione',
  templateUrl: './dichiarazione.component.html',
  styleUrls: ['./dichiarazione.component.scss']
})

export class DichiarazioneComponent extends AbstractTab implements OnInit, OnChanges {

  @Input("dichiarazione") dichiarazioneInput: Dichiarazione;

  constructor(private fb: FormBuilder,
    private stFormService:SchedaTecnicaFormService) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.dichiarazione && changes.dichiarazioneInput && changes.dichiarazioneInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.mappaOggettoForm();
    this.stFormService.dichiarazioneFormComplete$.next(true);
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
