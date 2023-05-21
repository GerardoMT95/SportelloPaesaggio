import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/primeng';
import
  {
    Role,
    RoleEnumLabels, UlterioreDocumentazione, ConfigOption
  } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-documentazione-allega',
  templateUrl: './documentazione-allega.component.html',
  styleUrls: ['./documentazione-allega.component.scss']
})
export class DocumentazioneAllegaComponent implements OnInit {
  documentazioniForm: FormGroup;
  documentazioni: UlterioreDocumentazione;

  options: ConfigOption[] = [
    {
      value: Role.Richiedente,
      label: RoleEnumLabels.get(Role.Richiedente)
    },
    {
      value: Role.AmministratoreEnteDelegato,
      label: RoleEnumLabels.get(Role.AmministratoreEnteDelegato)
    },
    {
      value: Role.Soprintendenza,
      label: RoleEnumLabels.get(Role.Soprintendenza)
    },
    {
      value: Role.EnteTerritoriale,
      label: RoleEnumLabels.get(Role.EnteTerritoriale)
    }
  ];

  constructor(
    private formBuilder: FormBuilder,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig
  ) {
    this.documentazioni = this.config.data.documentazioni;
  }

  ngOnInit() {
    this.buildForm();
    if (this.documentazioni) {
      ViewMapper.mapObjectToForm(this.documentazioniForm, this.documentazioni);
    }
  }

  buildForm() {
    this.documentazioniForm = this.formBuilder.group({
      titoloDocumento: [''],
      descrizioneContenuto: [''],
      dataConsivisione: [''],
      destinatarioNotifica: [''],
      visualizzabiliDa: [''],
      inseritoDa: [''],
      anguigiMailPEC: this.formBuilder.array([]),
      allegati: [null]
    });

    this.documentazioniForm.valueChanges.subscribe((val) => console.log(val));
  }

  addAnguigiMailPEC(recepient: string = '') {
    this.getAnguigiMailPEC.push(
      this.formBuilder.group({
        recepient: [recepient]
      })
    );
  }

  get getAnguigiMailPEC() {
    return this.documentazioniForm.get('anguigiMailPEC') as FormArray;
  }

  get shareDate() {
    return this.documentazioniForm.get('dataConsivisione').value;
  }

  upload(event) {
    if (event.files.length >= 0) {
      this.documentazioniForm.get('allegati').patchValue(event.files[0].name);
    }
  }

  addDocument() {
    this.ref.close({
      action: 'ATTACH_DOCUMENT',
      data: this.documentazioniForm.value
    });
  }

  clearDocument() {
    this.documentazioniForm.reset();
    this.ref.close();
  }
}
