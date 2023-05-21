import { ChangeDetectionStrategy, Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { IstanzaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { requiredIfNotEmpty, requiredNotEmpty } from 'src/app/shared/validators/customValidator';



@Component({
  selector: 'app-altro-titolare',
  templateUrl: './altro-titolare.component.html',
  styleUrls: ['./altro-titolare.component.scss']
})
export class AltroTitolareComponent implements OnInit, OnDestroy
{
  checked: boolean = false;
  genders: string[] = ['F', 'M'];
  //documentTableHeaders: TableConfig[] = [];
  //tableData: any[]
  const=CONST;

  @Input() fascicolo: Fascicolo;

  @Input() titolaritaInQualitaDiAltroTit: SelectOption[];
  @Input() tipoDocumentoOptions: SelectOption[];
  @Input() validation: boolean;
  @Input() disabled=false;
  altroTitolareForm: FormGroup;
  altriTitolariFormArr: FormArray;
  @Input() mainForm: FormGroup;
  private unsubscribe$ = new Subject<void>();
  @Input() tipoAziendaOptions: SelectOption[];

  constructor(private fb: FormBuilder,
              public loadingService: LoadingService,
              private dialogService: DialogService,
              private fascicoloService: FascicoloService,
              private istanzaFormService:IstanzaFormService)
  {
  }
  
  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit()
  {
    this.buildForm();
    this.istanzaFormService.altriTitolariFormComplete$.next(true);
  }

  elimina(index: number)
  {
    this.dialogService.showDialog(true,
      'ANNULA.CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
          this.doElimina(index);
        }
      },
      DialogType.WARNING,
      null);
  }

  doElimina(index: number)
  {
    this.loadingService.emitLoading(true);
    let formGroup = this.altriTitolariFormArr.at(index) as FormGroup;
    let id = formGroup.get('id').value;
    this.fascicoloService
      .eliminaTitolare({ value: id, description: this.fascicolo.codicePraticaAppptr })
      .then(ret =>
      {
        if (ret.payload > 0)
        {
          this.altriTitolariFormArr.removeAt(index);
        }
      })
      .finally(
        () =>
        {
          this.loadingService.emitLoading(false);
          this.dialogService.showDialog(true,
            'ANNULA.SUCCESSO',
            'ANNULA.TITLE',
            DialogButtons.CHIUDI,
            (buttonID: string): void => { },
            DialogType.SUCCESS,
            null);
        });
  }

  addOtherOwner()
  {
    this.loadingService.emitLoading(true);
    this.fascicoloService.creaTitolare(this.fascicolo)
      .then(
        ret =>
        {
          if(!this.fascicolo.istanza.altriTitolari){
            this.fascicolo.istanza.altriTitolari=[];
          }
          this.fascicolo.istanza.altriTitolari.push(ret.payload);
          this.altriTitolariFormArr.push(this.buildFormAltroTitolare(ret.payload.id));
          

        }
      )
      .finally(() => this.loadingService.emitLoading(false));
  }

  /*proprietarioEsclusivo()
  {
    let formValue = this.mainForm.value;
    let ret = false;
    try
    {
      //console.log('form value={}',formValue);
      if (formValue.dichiarazioni.titolarita == 4) //4=proprietario esclusivo
      {
        this.altroTitolareForm.reset()
        ret = true;
      }
    } catch (error) { }
    return ret;
  }*/


  buildForm()
  {
    this.altriTitolariFormArr = this.mainForm.get(
      'altriTitolari'
    ) as FormArray;
    if (this.fascicolo.istanza.altriTitolari && this.fascicolo.istanza.altriTitolari.length > 0)
    {
      this.fascicolo.istanza.altriTitolari.forEach(
        altroTitolare =>
        {
          const newAltrTitolare = this.buildFormAltroTitolare();
          newAltrTitolare.get('esisteDocumento').setValue(altroTitolare.documento.docAllegato!=null && altroTitolare.documento.docAllegato.id!=null);
          this.altriTitolariFormArr.push(newAltrTitolare);
          ViewMapper.mapObjectToForm(newAltrTitolare, altroTitolare);
          this.showFormControl('descrizioneAltro', 'titolaritaInQualitaDi', newAltrTitolare);
          newAltrTitolare.updateValueAndValidity();
        }
      );
    }
  }


  buildFormAltroTitolare(id?: number)
  {
    let formTitolare = this.fb.group({
      id: [id],//hidden
      titolaritaInQualitaDi: [null, Validators.required],
      descrizioneAltro: ['', requiredNotEmpty],
      nome: ['', requiredNotEmpty],
      cognome: ['', requiredNotEmpty],
      codiceFiscale: ['', [Validators.required, Validators.pattern(CONST.regexCodFisc)]],
      sesso: ['', Validators.required],
      natoIl: ['', Validators.required],
      natoStato: [null, Validators.required],
      natoCitta: [null],
      natoComune: [null],
      natoProvincia: [null],
      residenteIn: this.fb.group({
        stato: [null,Validators.required],
        citta: [null],
        comune: [null],
        provincia: [null],
        via: ['', requiredNotEmpty],
        n: ['', requiredNotEmpty],
        cap: ['', Validators.pattern('[0-9]{5}')]
      }),
      recapitoTelefonico: ['', [Validators.required,Validators.pattern(CONST.PHONE_PATTERN)]],
      pec: ['', [Validators.required, Validators.email,Validators.pattern(CONST.PATTERN_PEC)]],
      nelCaso: [false],
      inQualitaDi: [''/* , Validators.required */],
      descAltroDitta: [''/* , Validators.required */],
      societa: [''/* , Validators.required */],
      partitaIva: [''/* , Validators.required */],
      cUo: [''],
      societaCodiceFiscale: [''/* , Validators.required */],
      documento: this.fb.group({
        idTipo: [null, Validators.required],
        numero: ['', requiredNotEmpty],
        dataRilascio: ['', Validators.required],
        enteRilascio: ['', requiredNotEmpty],
        dataScadenza: ['', Validators.required]
      }),
      esisteDocumento: [false, Validators.requiredTrue]
      ,idTipoAzienda: ["", null]
      ,tipoAzienda: ["", null]
      ,codiceIpa: ["", null]
    });
    /*
    formTitolare.get('descrizioneAltro').disable();
    formTitolare.get('titolaritaInQualitaDi').valueChanges.subscribe(el=>
      {
       if(el=='4'){     //TO-DO non sincronizza la view
          formTitolare.get('descrizioneAltro').enable();
          formTitolare.get('descrizioneAltro').setValidators([Validators.required]);
        }
          else{
            formTitolare.get('descrizioneAltro').patchValue('');
            formTitolare.get('descrizioneAltro').clearValidators();
            formTitolare.get('descrizioneAltro').updateValueAndValidity();
            formTitolare.get('descrizioneAltro').disable();
          }
          });*/

    formTitolare.get('residenteIn').get('cap').setValidators(
        [Validators.pattern('[0-9]{5}'),requiredIfNotEmpty(formTitolare.get('residenteIn').get('comune'))]);
    return formTitolare;
  }

  showFormControl(subordinate, superior, form: FormGroup)
  {
    let valueConSpecifica = this.titolaritaInQualitaDiAltroTit.find(el => el.linked == "specificare").value;
    const subordinateControl = form.get(subordinate);
    const superiorValue = form.get(superior).value;
    //console.log(superiorValue + '===' + condition);
    //TO-DO 
    return superiorValue == valueConSpecifica ?
      (subordinateControl.setValidators([Validators.required]), true) :
      (subordinateControl.clearValidators(), subordinateControl.patchValue(''), false);
  }

  public validazioneTabella(event: Array<any>, indice: number): void
  {
    let documento: any = event[0];
    if (documento.id)
      this.altriTitolariFormArr.at(indice).get("esisteDocumento").setValue(true);
    else
      this.altriTitolariFormArr.at(indice).get("esisteDocumento").setValue(false);
  }

}
