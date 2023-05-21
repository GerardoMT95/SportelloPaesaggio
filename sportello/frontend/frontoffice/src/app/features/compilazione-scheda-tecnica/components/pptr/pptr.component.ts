import { updateAllFormValidity } from 'src/app/shared/functions/generic.utils';
import { PPTR } from './../../../../shared/models/models';
import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ConfigOption, Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';


@Component({
  selector: 'app-pptr',
  templateUrl: './pptr.component.html',
  styleUrls: ['./pptr.component.scss']
})
export class PptrComponent extends AbstractTab implements OnInit, OnChanges
{
  @Input("pptr") value: PPTR
  @Input("pptrTable") pptrTable: ConfigOption[];
  vincoliArt38=[];

  constructor(private fb: FormBuilder,
    private stFormService:SchedaTecnicaFormService) { super(); }

  ngOnInit(): void { 
    //costruzione array vincoli art.38
    if(this.fascicolo && 
      this.fascicolo.istanza && 
      this.fascicolo.istanza.localizzazione &&
      this.fascicolo.istanza.localizzazione.localizzazioneComuni && 
      this.fascicolo.istanza.localizzazione.localizzazioneComuni.length>0){
      this.fascicolo.istanza.localizzazione.localizzazioneComuni.forEach(comune=>{
        let comComp=this.fascicolo.comuniCompetenza.find(el=>el.enteId==comune.comuneId);
        if(comComp){
          this.vincoliArt38.push(comComp.vincoloArt38);
        }
      });
    }
    this.buildForm();
    this.stFormService.pptrFormComplete$.next(true);
   }

  ngOnChanges(changes: SimpleChanges): void
  {
    //this.populateDataForm(); 
  }

  private populateDataForm(){
    if(this.pptr && this.pptrTable)
    {
      if(this.value)
      {
        let ucp = this.value.ulteririContestiPaesaggistici;
        ViewMapper.mapObjectToForm(this.pptr, this.value);
        this.pptr.controls.ulteririContestiPaesaggistici.setValue(ucp ? ucp.map(m => { return { value: m.codice, text: m.specifica } }) : []);
      }      
      this.pptr.controls.ulteririContestiPaesaggistici.setValidators(this.onlyTextRequired(this.pptrTable));
    }
  }

  public updateValidity(): void { updateAllFormValidity(this.pptr); }
  get pptr() { return this.mainForm.get('pptr') as FormGroup; }

  buildForm()
  {
    this.mainForm.addControl('pptr',
      this.fb.group
      ({
        ambitoPaesaggistico: ['', Validators.required],
        figura: ['', Validators.required],
        art103: [''],
        art142: [''],
        descrizione: [''],
        ulteririContestiPaesaggistici: [[]]
      }));
      this.populateDataForm();
  }

  private onlyTextRequired(options: ConfigOption[]): ValidatorFn
  {  
    return (control: AbstractControl): ValidationErrors | null =>
    {
      let myMap: {[key: string]: boolean} = this.transformToMap(options);
      let errors: ValidationErrors | null = null;
      if (control && control.value)
      {
        control.value.forEach(value =>
        {
          if (myMap[value.value] === true && (!value.text || value.text === ""))
            errors = { textRequired: errors && errors.textRequired ? [...errors.textRequired, value.value] : [value.value] };
        });
      }
      return errors;
    }
  }
  private transformToMap(options: ConfigOption[]): {[key: string]: boolean}
  {
    let map: {[key: string]: boolean} = {};
    this.transorm(map, options);
    return map;
  }
  private transorm(map: any, options: ConfigOption[]): void
  {
    if(options)
    {
      options.forEach(option =>
      {
        map[option.value.toString()] = option.hasDescription;
        this.transorm(map, option.childOptions);
      });      
    }
  }
}
