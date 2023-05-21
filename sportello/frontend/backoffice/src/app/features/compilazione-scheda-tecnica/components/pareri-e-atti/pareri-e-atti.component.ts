import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { PareriAtti } from './../../../../shared/models/models';
import { Component, Input, OnInit, ChangeDetectionStrategy, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { TableConfig } from 'src/app/core/models/header.model';
import { Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-pareri-e-atti',
  templateUrl: './pareri-e-atti.component.html',
  styleUrls: ['./pareri-e-atti.component.scss']
})
export class PareriEAttiComponent extends AbstractTab implements OnInit, OnChanges {
  parreriTableHeaders: TableConfig[] = [];
  parreriForm: FormGroup;

  @Input("pareriEAtti") pareriEAtti: PareriAtti;

  constructor(private fb: FormBuilder) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.parreriForm && changes.pareriEAtti && changes.pareriEAtti.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.buildForm();
    this.parreriForm.disable();
    this.mainForm.get("parreriEAtti").disable();
    this.pareriInfo.disable();
    this.attiInfo.disable();
    this.parreriTableHeaders = [
      {
        header: 'PARERI_E_ATI.TYPE',
        field: 'tipologia',
        type: 'text-field'
      },
      {
        header: 'PARERI_E_ATI.RELEASED_BY',
        field: 'rialisciatoDa',
        type: 'text-field'
      },
      {
        header: 'PARERI_E_ATI.PROTOCOL',
        field: 'noProtocollo',
        type: 'text-field'
      },
      {
        header: 'PARERI_E_ATI.RELEASE_DATE',
        field: 'dataRilascio',
        type: 'date-picker-field'
      },
      {
        header: 'PARERI_E_ATI.ACCOUNTHOLDER',
        field: 'intestinario',
        type: 'text-field'
      }
    ];
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.parreriEAtti) {
      let builders = {
        parreriInfo: this.buildParreriForm.bind(this),
        attiInfo: this.buildParreriForm.bind(this)
      };
      ViewMapper.mapObjectToForm(this.parreriEAtti, this.fascicolo.schedaTecnica.parreriEAtti, builders);
    } else {
      /* this.addLegittimitaInfoInfo();
      this.addAutorizzatoPaesaggisticamenteInfo(); */
    }
  }

 buildForm() {
    this.parreriForm = this.fb.group({
      parreriInfo: this.fb.array([]),
      attiInfo: this.fb.array([])
    });
    //this.addPareriInfo();
    this.mainForm.addControl('parreriEAtti', this.parreriForm);
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.parreriEAtti &&
      this.fascicolo.schedaTecnica.parreriEAtti.parreriInfo &&
      this.fascicolo.schedaTecnica.parreriEAtti.parreriInfo.length === 0)this.addPareriInfo();

    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.parreriEAtti &&
      this.fascicolo.schedaTecnica.parreriEAtti.attiInfo &&
      this.fascicolo.schedaTecnica.parreriEAtti.attiInfo.length === 0)this.addPareriInfo();

  }

  addPareriInfo() {
    this.pareriInfo.push(
      this.buildParreriForm()
    );
  }

  addAttiInfo() {
    this.attiInfo.push(
      this.buildParreriForm()
    );
  }

  buildParreriForm() {
    return this.fb.group({
      tipologia: ['', Validators.required],
      rialisciatoDa: ['', Validators.required],
      noProtocollo: ['', Validators.required],
      dataRilascio: ['', Validators.required],
      intestinario: ['', Validators.required]
    }, {disabled: true});
  }
  
  get pareriInfo() {
    return this.parreriForm.get('parreriInfo') as FormArray;
  }

  get attiInfo() {
    return this.parreriForm.get('attiInfo') as FormArray;
  }

  get parreriEAtti() {
    return this.mainForm.get('parreriEAtti') as FormGroup;
  }

}
