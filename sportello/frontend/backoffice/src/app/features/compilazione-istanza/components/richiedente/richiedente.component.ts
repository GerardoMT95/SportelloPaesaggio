import { customEmailValidator } from 'src/app/shared/validators/customValidator';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';


@Component({
  selector: 'app-richiedente',
  templateUrl: './richiedente.component.html',
  styleUrls: ['./richiedente.component.scss']
})
export class RichiedenteComponent extends AbstractTab implements OnInit
{
  val1: string;
  prepend: string;
  const=CONST;
  //tableData: any[];
  //documentTableHeaders: TableConfig[] = [];


  @Input() tipoDocumentoOptions: SelectOption[];
  @Input() documentoRiconoscimento: Array<any> = [];

  constructor(private fb: FormBuilder,
              private allegatiService: AllegatoService,
              private loadingService: LoadingService) { super() }

  ngOnInit()
  {
    this.buildForm();
    ViewMapper.mapObjectToForm(this.richiedente, this.fascicolo.istanza.richiedente);
    this.richiedente.disable();
  }

  get richiedente() { return (this.mainForm.get('richiedente') as FormGroup); }

  buildForm()
  {
    this.mainForm.addControl("richiedente", this.fb.group({
      id: [null,], //hidden
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      codiceFiscale: ['', [Validators.required, Validators.pattern(CONST.regexCodFisc)]],
      sesso: ['F', Validators.required],
      natoIl: ['', Validators.required],
      natoStato: [null, Validators.required],
      natoCitta: [null, Validators.required],
      natoComune: [null, Validators.required],
      natoProvincia: [null, Validators.required],
      residenteIn: this.fb.group({
        stato: [null, Validators.required],
        citta: [null, Validators.required],
        comune: [null, Validators.required],
        provincia: [null, Validators.required],
        via: ['', Validators.required],
        n: ['', Validators.required],
        cap: ['', Validators.required]
      }),
      recapitoTelefonico: ['', Validators.required],
      indirizzoEmail: ['', [Validators.required, customEmailValidator]],
      pec: ['', Validators.required],
      nelCaso: [false],
      inQualitaDi: [null],
      descAltroDitta: [''],
      societa: [''],
      partitaIva: [''],
      societaCodiceFiscale: [''],
      documento: this.fb.group({
        idTipo: [null, Validators.required],
        numero: ['', Validators.required],
        dataRilascio: ['', Validators.required],
        enteRilascio: ['', Validators.required],
        dataScadenza: ['', Validators.required]
      }),
      esisteDocumento: [false, Validators.requiredTrue]
      ,idTipoAzienda: ["", null]
      ,tipoAzienda: ["", null]
      ,codiceIpa: ["", null]
    }));
  }

  showFormControl(subordinate, superior, condition)
  {
    const subordinateControl = this.richiedente.get(subordinate);
    const superiorValue = this.richiedente.get(superior).value;
    //console.log(superiorValue + '===' + condition);
    return superiorValue == condition ?
      (subordinateControl.setValidators([Validators.required]), true) :
      (subordinateControl.clearValidators(), subordinateControl.patchValue(''), false);
  }

  public validazioneTabella(event: Array<any>): void {
    let documento: any = event[0];
    if (documento.id) {
      this.richiedente.get("esisteDocumento").setValue(true);
    }
    else {
      this.richiedente.get("esisteDocumento").setValue(false);
    }
  }

}
