import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/primeng';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

import { ComunicazioniTemplate } from './../../../../shared/models/models';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-invia-comunicazione',
  templateUrl: './invia-comunicazione.component.html',
  styleUrls: ['./invia-comunicazione.component.scss']
})
export class InviaComunicazioneComponent implements OnInit {
  comunicazioneForm: FormGroup;
  codiceFascicolo: number;
  comunicazioni: ComunicazioniTemplate;
  constructor(
    private ref: DynamicDialogRef,
    private formBuilder: FormBuilder,
    public fascicoloStore: FascicoloStore,
    private config: DynamicDialogConfig
  ) {}

  ngOnInit() {
    this.codiceFascicolo = this.config.data.codiceFascicolo;
    this.comunicazioni = this.config.data.comunicazioni;
    this.buildComunicazioneForm();
    if (this.comunicazioni) {
      ViewMapper.mapObjectToForm(this.comunicazioneForm, this.comunicazioni);
      this.patchAggiungiDestinatari();
    } else {
      this.addAggiungiDestinario();
    }
  }
  get tipoComunicazione() {
    return this.metadata.tipoComunicazione;
  }
  get metadata() {
    return this.fascicoloStore.state.meta;
  }
  buildComunicazioneForm() {
    this.comunicazioneForm = this.formBuilder.group({
      codiceFascicolo: [{ value: this.codiceFascicolo, disabled: true }],
      descrizione: [''],
      mittente: [''],
      aggiungiDestinario: this.formBuilder.array([]),
      oggetto: [''],
      testoTemplate: [''],
      data: [''],
      allegati: [null]
    });

    this.comunicazioneForm.valueChanges.subscribe((val) => console.log(val));
  }
  addAggiungiDestinario(destinatario?) {
    this.getAnguigiDestinario.push(
      this.formBuilder.group({
        recepient: [destinatario ? destinatario.recepient : '']
      })
    );
  }
  get getAnguigiDestinario() {
    return this.comunicazioneForm.get('aggiungiDestinario') as FormArray;
  }

  upload(event) {
    if (event.files.length >= 0) {
      this.comunicazioneForm.get('allegati').patchValue(event.files[0].name);
    }
  }

  addCommunication() {
    this.ref.close({
      action: 'ADD_COMMUNICATION',
      data: this.comunicazioneForm.value
    });
  }
  addInvia() {
    this.ref.close({
      action: 'SEND_MAIL',
      data: this.comunicazioneForm.value
    });
  }

  clearDocument() {
    this.comunicazioneForm.reset();
    this.ref.close();
  }

  patchAggiungiDestinatari() {
    console.log('DESTINTARII', this.comunicazioni.destinatari);
     this.comunicazioni.destinatari.forEach((element) => {
        this.addAggiungiDestinario(element);
      });
  }

}
