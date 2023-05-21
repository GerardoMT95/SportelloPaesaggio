import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { DestinazioneUrbanistica } from './../../../../shared/models/models';

@Component({
  selector: 'app-destinazione-urbanistica',
  templateUrl: './destinazione-urbanistica.component.html',
  styleUrls: ['./destinazione-urbanistica.component.scss'] 
})
export class DestinazioneUrbanisticaComponent extends AbstractTab implements OnInit, OnChanges {
  @Input("destinazioneUrbanistica") destinazioneUrbanistica: DestinazioneUrbanistica[];

  constructor(private fb: FormBuilder) { super(); }

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
    this.mainForm.get("destinazione").disable();
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
          const group = 
          this.buildGroup(destinazione.comune, destinazione.comuneId, 
            true//la checkbox verrà mostrata sempre.... e la rendiamo obbligatoria(this.fascicolo.schedaTecnica.destinazione[i] ? this.fascicolo.schedaTecnica.destinazione[i].mostraCoerenza : null)
            );
          this.destinazione.push(group);
          if (this.fascicolo.schedaTecnica) {
            ViewMapper.mapObjectToForm(group, this.fascicolo.schedaTecnica.destinazione[i]);
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
    return this.fb.group({
      titolo: [comune || ''/* , Validators.required */],
      strumentoUrbanistico: ['', Validators.required],
      approvatoData: ['', Validators.required],
      approvatoCon: ['', Validators.required],
      // deliberazioneApprovazione: ['', Validators.required],
      destinazioneUrbanistica: ['', Validators.required],
      ulterioriTutele: [''/* , Validators.required */],
      confermaCoerenza: [null, mostraCheckbox ? Validators.required : null],
      strumentoInAdozione: ['', Validators.required],
      conformitaStrumentoUrbanistico: [null, Validators.required],
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
  }
}
