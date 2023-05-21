import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators, ValidationErrors, FormControl } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DestinazioneUrbanistica } from './../../../../shared/models/models';
import { requiredDependsOn } from 'src/app/shared/validators/customValidator';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-destinazione-urbanistica',
  templateUrl: './destinazione-urbanistica.component.html',
  styleUrls: ['./destinazione-urbanistica.component.scss'] 
})
export class DestinazioneUrbanisticaComponent extends AbstractTab implements OnInit, OnChanges {
  @Input("destinazioneUrbanistica") destinazioneUrbanistica: DestinazioneUrbanistica[];
  public currentDate:Date = CONST.TODAY;
  
  constructor(private fb: FormBuilder,
    private stFormService:SchedaTecnicaFormService) { super(); }

    //non dovrebbe servire piu' forse quando c'era il pannello localizzzazione anche in scheda tecnica
  ngOnChanges(changes: SimpleChanges): void {
   /* if (this.mainForm && this.destinazione && changes.destinazioneUrbanistica && changes.destinazioneUrbanistica.currentValue) {
      this.mappaOggettoForm();
    }*/
  }

  /* ngDoCheck(): void {
    //Called every time that the input properties of a component or a directive are checked. Use it to extend change detection by performing a custom check.
    //Add 'implements DoCheck' to the class.
    if (this.mainForm && this.destinazione && this.fascicolo && this.fascicolo.schedaTecnica && this.fascicolo.istanza)
      this.mappaOggettoForm();
  } */

  ngOnInit() {
    this.buildForm();
    this.mappaOggettoForm();
    this.stFormService.destinazioneUrbanisticaFormComplete$.next(true);
    /* if (this.fascicolo.istanza) {
      if (this.fascicolo.istanza.localizazione) {
        this.fascicolo.istanza.localizazione.localizzazioneComuni.forEach((destinazione, i) => {
          const group = this.buildGroup(destinazione.comune);
          this.destinazione.push(group);
          if (this.fascicolo.schedaTecnica) {
            ViewMapper.mapObjectToForm(group, this.fascicolo.schedaTecnica.destinazione[i]);
          }
        });
      }
    } */
    // ViewMapper.mapObjectToForm(this.destinazione, this.fascicolo.schedaTecnica.destinazione);
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.istanza) {
      if (this.fascicolo.istanza.localizzazione) {
        this.destinazione.clear();
        this.fascicolo.istanza.localizzazione.localizzazioneComuni.forEach((destinazione, i) => {
          const group = this.buildGroup(destinazione.comune, 
            destinazione.comuneId, 
            true
            );
          let idxComuneCompetenza=this.fascicolo.comuniCompetenza.findIndex(el=>el.enteId==destinazione.comuneId);
          if(idxComuneCompetenza>=0){
            group.get('coerenzaAtto').patchValue(this.fascicolo.comuniCompetenza[idxComuneCompetenza].vincoloArt100);  
          }
          
          this.destinazione.push(group);
          if (this.fascicolo.schedaTecnica) {
            ViewMapper.mapObjectToForm(group, this.fascicolo.schedaTecnica.destinazione[i]);
            if(group.get('strumentoInAdozione').value==1){
              group.get('adottatoData').disable();
              group.get('adottatoCon').disable();
            }
          }
        });
      }
    }
  }

  get destinazione() {
    return this.mainForm.get('destinazione') as FormArray;
  }

  buildForm() {
    this.mainForm.addControl('destinazione', this.fb.array([]));
  }

  pushGroup(group: FormGroup) {
    this.destinazione.push(group);
  }

  buildGroup(comune?: string, comuneId?: number, mostraCheckbox?: boolean) {
    let fb=this.fb.group({
      titolo: [comune || ''/* , Validators.required */],
      strumentoUrbanistico: ['', Validators.required],
      approvatoData: ['', Validators.required],
      approvatoCon: ['', Validators.required],
      // deliberazioneApprovazione: ['', Validators.required],
      destinazioneUrbanistica: ['', Validators.required],
      ulterioriTutele: [''/* , Validators.required */],
      confermaCoerenza: [null],
      strumentoInAdozione: ['', Validators.required],
      conformitaStrumentoUrbanistico: [null],
      adottatoData: ['', Validators.required],
      adottatoCon: ['', Validators.required],
      destinazioneUrbanisticaAdottato: ['', Validators.required],
      ulterioriTuteleAdottato: [''/* , Validators.required */],
      coerenzaData: [""],
      coerenzaAtto: [""],
      /* servono a me per alcune logiche FE --> */
      comuneId: [comuneId || null],
      mostraCoerenza : [false],
      /* <-- */
    });
    let strumentoInAdozioneCtl=fb.get('strumentoInAdozione') as FormControl;
    fb.get('adottatoData').setValidators([requiredDependsOn(strumentoInAdozioneCtl, 2),requiredDependsOn(strumentoInAdozioneCtl, 3)]);
    fb.get('adottatoCon').setValidators([requiredDependsOn(strumentoInAdozioneCtl, 2),requiredDependsOn(strumentoInAdozioneCtl, 3)]);
    return fb;
  }

  public attivaValidatori(event:any,index:number){
    this.destinazione.at(index).get('adottatoData').updateValueAndValidity();
    this.destinazione.at(index).get('adottatoCon').updateValueAndValidity();
    let val=this.destinazione.at(index).get('strumentoInAdozione').value;
    if(val==1){
      this.destinazione.at(index).get('adottatoData').disable();
      this.destinazione.at(index).get('adottatoCon').disable();
      this.destinazione.at(index).get('adottatoData').patchValue(null);
      this.destinazione.at(index).get('adottatoCon').patchValue(null);
    }else{
      this.destinazione.at(index).get('adottatoData').enable();
      this.destinazione.at(index).get('adottatoCon').enable();
    }
  }

}
