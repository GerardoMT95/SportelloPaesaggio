import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
  selector: 'app-tecnico-incaricato',
  templateUrl: './tecnico-incaricato.component.html',
  styleUrls: ['./tecnico-incaricato.component.scss']
})
export class TecnicoIncaricatoComponent implements OnInit
{

  @Input() tipoDocumentoOptions: SelectOption[];
  @Input() fascicolo: Fascicolo;
  @Input() mainForm: FormGroup;
  @Input() validation: boolean;
  const = CONST;
  constructor(private fb: FormBuilder,
              private allegatiService: AllegatoService,
              private loadingService: LoadingService) { }


  ngOnInit()
  {
    this.buildForm();
    ViewMapper.mapObjectToForm((this.mainForm.get("tecnicoIncaricato") as FormGroup), this.fascicolo.istanza.tecnicoIncaricato);
    this.mainForm.get("tecnicoIncaricato").disable();
  }

  buildForm()
  {
    this.mainForm.addControl("tecnicoIncaricato", this.fb.group({
      id: [null],//hidden
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      codiceFiscale: ['', [Validators.required, Validators.pattern(CONST.regexCodFisc)]],
      sesso: ['', Validators.required],
      natoIl: ['', Validators.required],
      natoStato: ['', Validators.required],
      natoCitta: ['', Validators.required],
      natoComune: ['', Validators.required],
      natoProvincia: ['', Validators.required],
      residenteIn: this.fb.group({
        stato: [''],
        citta: [''],
        comune: [''],
        provincia: [''],
        via: [''],
        n: [''],
        cap: ['']
      }),
      conStudioIn: this.fb.group({
        stato: [''],
        citta: [''],
        comune: [''],
        provincia: [''],
        via: [''],
        n: [''],
        cap: ['']
      }),
      recapitoTelefonico: [''],
      // indirizzoEmail: ['', Validators.required],
      pec: ['', Validators.required],
      iscritoAllOrdine: [''],
      di: [''],
      n: [''],
      fax: [''],
      cellulare: [''],
      documento: this.fb.group({
        idTipo: ['', Validators.required],
        numero: ['', Validators.required],
        dataRilascio: ['', Validators.required],
        enteRilascio: ['', Validators.required],
        dataScadenza: ['', Validators.required]
      }),
      esisteDocumento: [false, Validators.requiredTrue]
    }));
  }

  public validazioneTabella(event: Array<any>): void
  {
    let documento: any = event[0];
    if (documento.id)
    {
      this.mainForm.get("tecnicoIncaricato").get("esisteDocumento").setValue(true);
    }
    else
    {
      this.mainForm.get("tecnicoIncaricato").get("esisteDocumento").setValue(false);
    }
  }

}
