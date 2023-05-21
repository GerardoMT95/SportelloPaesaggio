import { UserService } from './../../../../shared/services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/primeng';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DataService } from 'src/app/features/gestione-istanza/services';

@Component({
  selector: 'app-comunicazione-modal',
  templateUrl: './comunicazione-modal.component.html',
  styleUrls: ['./comunicazione-modal.component.scss']
})
export class ComunicazioneModalComponent implements OnInit
{
  relazioneEnteForm: FormGroup;
  tipoComunicazioneList: SelectOption[];
  elementIndex: number;
  codiceFascicolo: number;
  comunicazioneForm: FormGroup;
  mode: string;
  hasProtocollazione:boolean=false;
  constructor(
    private data: DataService,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit()
  {
    this.hasProtocollazione=this.data.hasProtocollazione;
    this.tipoComunicazioneList = this.config.data.metadata.tipoComunicazione;

    this.relazioneEnteForm = this.config.data.relazioneEnteForm;

    this.elementIndex = this.config.data.elementIndex;
    this.codiceFascicolo = this.config.data.codiceFascicolo;
    this.mode = this.config.data.mode;
    this.buildComunicazioneForm();
    if (this.elementIndex >= 0)
    {
      ViewMapper.mapObjectToForm(
        this.comunicazioneForm,
        this.getComunicazioneAsFormGroup(this.elementIndex).value
      );
      this.patchAggiungiDestinatari();
    } else
    {
      this.addAggiungiDestinario();
    }

    if (this.mode === 'VIEW')
    {
      this.comunicazioneForm.disable();
    }
  }

  buildComunicazioneForm()
  {
    this.comunicazioneForm = this.formBuilder.group({
      codiceFascicolo: [{ value: this.codiceFascicolo, disabled: true }],
      descrizione: [''],
      mittente: [''],
      aggiungiDestinario: this.formBuilder.array([]),
      oggetto: [''],
      testoTemplate: [''],
      data: ['']
    });
  }

  addAggiungiDestinario(recepient: string = '')
  {
    this.getAggiungiDestinario.push(
      this.formBuilder.group({
        recepient: [recepient]
      })
    );
  }

  get getAggiungiDestinario()
  {
    return this.comunicazioneForm.get('aggiungiDestinario') as FormArray;
  }

  getComunicazioneAsFormGroup(index: number)
  {
    return (this.relazioneEnteForm.get('comunicazioni') as FormArray).at(index) as FormGroup;
  }

  cancel()
  {
    this.comunicazioneForm.reset();
    setTimeout(() =>
    {
      this.buildComunicazioneForm();
      if (this.elementIndex !== undefined)
      {
        ViewMapper.mapObjectToForm(
          this.comunicazioneForm,
          this.getComunicazioneAsFormGroup(this.elementIndex).value
        );
      }
      this.patchAggiungiDestinatari();
    }, 0);
  }

  saveDraft()
  {
    this.patchDataAndMittente();
    if (this.elementIndex === undefined)
    {
      console.log('CODE', this.elementIndex);
      (this.relazioneEnteForm.get('comunicazioni') as FormArray).push(this.comunicazioneForm);
    } else if (this.elementIndex !== undefined)
    {
      ViewMapper.mapObjectToForm(
        this.getComunicazioneAsFormGroup(this.elementIndex),
        this.comunicazioneForm.value
      );
      this.patchAggiungiDestinariToMainForm();
    }

    this.ref.close({
      action: 'SAVE_DRAFT'
    });

  }

  sendMailAndProtocol()
  {
    this.ref.close({
      data: this.comunicazioneForm.value,
      action: 'SEND_MAIL_PROTOCOL'
    });
  }

  sendMailWithoutProtocol()
  {
    this.ref.close({
      data: this.comunicazioneForm.value,
      action: 'SEND_MAIL_WITHOUT_PROTOCOL'
    });
  }

  patchDataAndMittente()
  {
    this.comunicazioneForm.get('data').patchValue(new Date().toLocaleString());
    this.comunicazioneForm.get('mittente').patchValue((this.userService.getUser().nome + " " + this.userService.getUser().cognome));
    this.comunicazioneForm.updateValueAndValidity();
  }

  patchAggiungiDestinatari()
  {
    if (this.elementIndex !== undefined)
    {
      const destinario = (this.getComunicazioneAsFormGroup(this.elementIndex).get(
        'aggiungiDestinario'
      ) as FormArray).value;

      destinario.forEach((element) =>
      {
        this.addAggiungiDestinario(element.recepient);
      });
    }
  }

  patchAggiungiDestinariToMainForm()
  {
    this.clearDestinario();
    const communicatione = (this.comunicazioneForm.get('aggiungiDestinario') as FormArray).value;
    const destinario = (this.getComunicazioneAsFormGroup(this.elementIndex).get('aggiungiDestinario') as FormArray);

    communicatione.forEach((element) =>
    {
      destinario.push(
        this.formBuilder.group({
          recepient: [element.recepient]
        })
      );
    });
  }

  clearDestinario()
  {
    (this.getComunicazioneAsFormGroup(this.elementIndex).get(
      'aggiungiDestinario'
    ) as FormArray).clear();
  }

}
