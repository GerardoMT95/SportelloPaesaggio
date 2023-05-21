import { updateAllFormValidity } from 'src/app/shared/functions/generic.utils';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { Component, Input, OnChanges, OnInit, SimpleChanges, SimpleChange } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors, FormControl, AbstractControl } from '@angular/forms';
import { TipoRaggruppamento } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { HierarchicalFieldValidators } from './../../../../shared/components/hierarchical-field/hierarchical-field-validators';
import { CaratterizzazioneIntervento, ConfigOptionValue, HierarchicalOption, SchedaTecnicaDescrizione, TipologiaDiIntervento } from './../../../../shared/models/models';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { CONST } from 'src/app/shared/constants';
import { requiredAllOrNone } from 'src/app/shared/validators/customValidator';


@Component({
  selector: 'app-descrizione',
  templateUrl: './descrizione.component.html',
  styleUrls: ['./descrizione.component.scss']
})
export class DescrizioneComponent extends AbstractTab implements OnInit, OnChanges
{
  @Input("descrizione") description: SchedaTecnicaDescrizione;
  @Input("options") options: HierarchicalOption[];
  @Input("tipoProcedimento") tipoProcedimento: number;
  public currentDate:Date = CONST.TODAY;

  private ready: boolean = false;
  constructor(private fb: FormBuilder,
        private stFormService:SchedaTecnicaFormService) { super(); }


  ngOnInit()
  {
    this.buildForm();
    this.init();
    this.stFormService.descrizioneFormComplete$.next(true);
  }

  ngOnChanges(changes: SimpleChanges): void
  {
    /*if (this.mainForm && this.fascicolo)
    {
      this.init();
    }*/
      
  }

  private init(): void
  {
    if (this.description)
    {
      const formInit = this.fromSchedaTecnicaToForm(this.description);
      ViewMapper.mapObjectToForm(this.descrizione, formInit);
    }
    if (this.descrizione && this.options && this.options.length > 0 && !this.ready)
    {
      (this.descrizione.controls.tipoIntervento as FormGroup).controls.tipologiaDiIntervento.setValidators(HierarchicalFieldValidators.required(this.tipoInt));
      (this.descrizione.controls.caratterizzazioneIntervento as FormGroup).controls.caratterizzazione.setValidators(HierarchicalFieldValidators.required(this.carInt));
      (this.descrizione.controls.caratterizzazioneIntervento as FormGroup).controls.durata.setValidators(HierarchicalFieldValidators.required(this.carIntTP));
      this.descrizione.controls.qualificazioneIntervento.setValidators(HierarchicalFieldValidators.required(this.qualificazioni));
      this.ready = true;
    } 
  }

  private buildForm()
  {
    this.mainForm.addControl('descrizione', this.fb.group
    ({
        descrizione: [''],
        tipoIntervento: this.fb.group
        ({
          tipologiaDiIntervento: [[]],
          inParticolareAgliArtt: [''],
          dataApprovazione: [null],
          con: ['']
        },
        { validators: [requiredAllOrNone(['inParticolareAgliArtt','dataApprovazione','con'])] }
        ),
        caratterizzazioneIntervento: this.fb.group
        ({
          caratterizzazione: [[]],
          durata: [[]]
        }),
        qualificazioneIntervento: [[]]
    }));
  }

  private fromSchedaTecnicaToForm(descrizione: SchedaTecnicaDescrizione): any
  {
    let tipoInt: TipologiaDiIntervento = descrizione.tipoIntervento;
    let carInt: CaratterizzazioneIntervento = descrizione.caratterizzazioneIntervento;
    let qual: ConfigOptionValue[] = descrizione.qualificazioneIntervento;
    const form =
    {
      descrizione: descrizione.descrizione,
      tipoIntervento: 
      {
        tipologiaDiIntervento: tipoInt && tipoInt.tipologiaDiIntervento ? [{ value: tipoInt.tipologiaDiIntervento, text: null}] : [],
        inParticolareAgliArtt: tipoInt && tipoInt.inParticolareAgliArtt ? descrizione.tipoIntervento.inParticolareAgliArtt : '',
        dataApprovazione: tipoInt ? descrizione.tipoIntervento.dataApprovazione : '',
        con: tipoInt ? descrizione.tipoIntervento.con : ''
      },
      caratterizzazioneIntervento: 
      {
        caratterizzazione: carInt && carInt.caratterizzazione ? carInt.caratterizzazione : [],
        durata: carInt && carInt.durata ? [{value: carInt.durata, text: null}] : []
      },
      qualificazioneIntervento: qual ? qual : []
    }

    return form;
  }

  public updateValidity(): void { updateAllFormValidity(this.descrizione); }


  get descrizione(): FormGroup { return this.mainForm.get('descrizione') as FormGroup; }
  get tipoInt(): HierarchicalOption { return this.options ? this.options.find(o => o.name === TipoRaggruppamento.TIPO_INT) : null; }
  get carInt(): HierarchicalOption { return this.options ? this.options.find(o => o.name === TipoRaggruppamento.CAR_INT) : null; }
  get carIntTP(): HierarchicalOption { return this.options ? this.options.find(o => o.name === TipoRaggruppamento.CAR_INT_TP) : null; }
  get qualificazioni(): HierarchicalOption 
  { 
    let others: string[] = 
    [
      TipoRaggruppamento.TIPO_INT,
      TipoRaggruppamento.CAR_INT, 
      TipoRaggruppamento.CAR_INT_TP
    ];
    return this.options ? this.options.find(o => !others.includes(o.name)) : null; 
  }
  get labelQualificazione(): string
  {
    let label = "DESCRIPTION_TAB.";
    switch(this.tipoProcedimento.toString())
    {
    case '1':
      label += "THE_INTERVENTIONS_AND/OR_PROJECT_WORKS_ARE_QUALIFIABLE";
      break;
    case '2':
      label += "TYPE_OF_WORK";
      break;
    case '4':
      label +="THE_INTERVENTION_IS_BETWEEN"
      break;
    case '3':
      label +="RICADONO_3";
      break;
    default:
      label = "";
      break;
    }
    return label;
  }


  get tipoIntControls(): {[key: string]: AbstractControl} { return (this.descrizione.controls.tipoIntervento as FormGroup).controls; }
  get carIntControls(): {[key: string]: AbstractControl} { return (this.descrizione.controls.caratterizzazioneIntervento as FormGroup).controls }
  
  get errorTipoIntervento(): ValidationErrors { return this.descrizione.controls.tipoIntervento.errors }
  get errorTipoInt(): ValidationErrors { return this.tipoIntControls.tipologiaDiIntervento.errors }
  get errorCarInt(): ValidationErrors { return this.carIntControls.caratterizzazione.errors }
  get errorCarIntTP(): ValidationErrors { return this.carIntControls.durata.errors }
  get errorQual(): ValidationErrors { return this.descrizione.controls.qualificazioneIntervento.errors }


  mergeError(err1,err2){
    let ret={...err1,...err2};
    if(!err1 && !err2) return null;
    return ret;
  }
}
