import { Component, EventEmitter, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { Subject } from 'rxjs';
import { IstanzaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { requiredIfNotEmpty, requiredNotEmpty } from 'src/app/shared/validators/customValidator';
import { RuoloPratica } from 'src/app/shared/models/models';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-richiedente',
  templateUrl: './richiedente.component.html',
  styleUrls: ['./richiedente.component.scss']
})
export class RichiedenteComponent extends AbstractTab implements OnInit,OnDestroy
{
  val1: string;
  prepend: string;
  const=CONST;
  @Input() tipoDocumentoOptions: SelectOption[];
  @Input() ruoloPratica: string;
  @Input() documentoRiconoscimento: Array<any> = [];
  @Input() tipoAziendaOptions: SelectOption[];
  @Input() set disabled(d: boolean) { this.disable = d; if (this.mainForm.get('richiedente') && d) this.mainForm.get('richiedente').disable();  }
  private unsubscribe$=new Subject<void>();

  private disable: boolean = false;

  constructor(private fb: FormBuilder
              ,private istanzaFormService:IstanzaFormService
              ,private userService:UserService
              ) { super() }

  ngOnInit()
  {
    this.buildForm();
    if (this.disable)
    {
      this.mainForm.controls.richiedente.disable();
      this.mainForm.controls.richiedente.clearValidators();
    }
    console.log("ruolo pratica {}",this.ruoloPratica)
    if(this.ruoloPratica && this.ruoloPratica==RuoloPratica.PR && !this.disable){
       this.mainForm.get('richiedente').get('cognome').disable();
       this.mainForm.get('richiedente').get('nome').disable();
       this.mainForm.get('richiedente').get('codiceFiscale').disable();
    }
    ViewMapper.mapObjectToForm(this.richiedente, this.fascicolo.istanza.richiedente);
    if(!this.disable){
      this.richiedente.get('residenteIn').get('cap').setValidators(
        [Validators.pattern('[0-9]{5}'),requiredIfNotEmpty(this.richiedente.get('residenteIn').get('comune'))]);
    }
    this.istanzaFormService.richiedenteFormComplete$.next(true);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  get richiedente() { return (this.mainForm.get('richiedente') as FormGroup); }

  buildForm()
  {
    this.mainForm.addControl("richiedente", this.fb.group({
      id: [null,], //hidden
      nome: ['', requiredNotEmpty],
      cognome: ['', requiredNotEmpty],
      codiceFiscale: ['', [Validators.required, Validators.pattern(CONST.regexCodFisc)]],
      sesso: ['F', Validators.required],
      natoIl: ['', Validators.required],
      natoStato: [null, Validators.required],
      natoCitta: [null],
      natoComune: [null],
      natoProvincia: [null],
      residenteIn: this.fb.group({
        stato: [null, Validators.required],
        citta: [null],
        comune: [null],
        provincia: [null],
        via: ['', requiredNotEmpty],
        n: ['', requiredNotEmpty],
        cap: [null, [Validators.pattern('[0-9]{5}')]]
      }),
      recapitoTelefonico: ['', [requiredNotEmpty,Validators.pattern(CONST.PHONE_PATTERN)]],
      indirizzoEmail: ['', [Validators.required, Validators.email,Validators.pattern(CONST.PATTERN_PEC)]],
      pec: ['', [Validators.required, Validators.email,Validators.pattern(CONST.PATTERN_PEC)]],
      nelCaso: [false],
      inQualitaDi: [''],
      descAltroDitta: [''],
      societa: [''],
      partitaIva: [''],
      societaCodiceFiscale: [''],
      cUo: [''],
      documento: this.fb.group({
        idTipo: [null, Validators.required],
        numero: ['', requiredNotEmpty],
        dataRilascio: ['', Validators.required],
        enteRilascio: ['', requiredNotEmpty],
        dataScadenza: ['', Validators.required]
      }),
      esisteDocumento: [this.fascicolo.istanza.richiedente.documento && 
        this.fascicolo.istanza.richiedente.documento.docAllegato!=null , Validators.requiredTrue]
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
