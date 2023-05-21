import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { Legittimita } from './../../../../shared/models/models';
import { ChangeDetectionStrategy, Component, Input, OnInit, DoCheck, OnChanges, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Fascicolo } from 'src/app/shared/models/models';
import { TableConfig } from 'src/app/core/models/header.model';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { requiredDependsOn, validDate } from 'src/app/shared/validators/customValidator';
import { SchedaTecnicaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-legittimita-urbanistica',
  templateUrl: './legittimita-urbanistica.component.html',
  styleUrls: ['./legittimita-urbanistica.component.scss']
})
export class LegittimitaUrbanisticaComponent extends AbstractTab implements OnInit, OnChanges {
  @Input("legittimita") legittimitaInput: Legittimita;

  buildingTableHeaders: TableConfig[] = [];
  urbanLegitimacyRadio: any[];
  urbanLandscapeRadio: any[];
  public currentDate: Date = CONST.TODAY;
  public const=CONST;

  constructor(private fb: FormBuilder,private stFormService:SchedaTecnicaFormService) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.legittimita && changes.legittimitaInput && changes.legittimitaInput.currentValue) {
      this.mappaOggettoForm();
    }
  }

  ngOnInit() {
    this.urbanLegitimacyRadio = [{ label: 'privo di titolo edilizio', value: 1 }, { label: 'dotato di titolo edilizio', value: 2 }];
    this.urbanLandscapeRadio = [
      { label: "realizzato prima dell'imposizione del vincolo  ", value: 1 },
      { label: 'autorizzato paesaggisticamente', value: 2 }
    ];

    this.buildingTableHeaders = [
    { 
      header: 'LEGITTIMITA_TABLE.TIPOLOGY', 
      field: 'tipologia', 
      type: 'text-field',
      validators:[Validators.required]
    },
    {
      header: 'LEGITTIMITA_TABLE.RELEASED_BY',
      field: 'rialisciatoDa',
      type: 'text-field',
      validators:[Validators.required]
    },
    {
      header: 'LEGITTIMITA_TABLE.PROTOCOL_NUMBER',
      field: 'noProtocollo',
      type: 'text-field',
      validators:[Validators.required]
    },
    {
      header: 'LEGITTIMITA_TABLE.RELEASE_DATE',
      field: 'dataRilascio',
      type: 'date-picker-field',
      validators:[Validators.required]
    },
    {
      header: 'LEGITTIMITA_TABLE.HOLDER',
      field: 'intestinario',
      type: 'text-field',
      validators:[Validators.required]
    }
    ];

    this.buildForm();
    this.mappaOggettoForm();
    this.initValueAndValidators();
    this.stFormService.leggittimitaUrbanisticaFormComplete$.next(true);
  }

  private initValueAndValidators(){
    let bool=this.showFormControl('dettaglioLegittimitaPaesaggistica', 'tipoLegittimitaPaesaggistica', 1)
    if(bool){
      let dettaglioPaesaggio = this.legittimita.get("dettaglioLegittimitaPaesaggistica"); 
      let legittimitaPaesag = this.legittimita.get("tipoLegittimitaPaesaggistica");
      let dataInterventoValue=this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento");
      dettaglioPaesaggio.get("dataImposizioneVincolo").setValidators(
        [requiredDependsOn(legittimitaPaesag, 1),
          validDate(dataInterventoValue)]);
        }
  }

  get legittimita() {
    return this.mainForm.get('legittimita') as FormGroup;
  }

  get legittimitaInfo() {
    return this.legittimita.get('legittimitaInfo') as FormArray;
  }

  get autorizzatoPaesaggisticamenteInfo() {
    return this.legittimita.get('autorizzatoPaesaggisticamenteInfo') as FormArray;
  }

  private mappaOggettoForm(): void {
    if (this.fascicolo.schedaTecnica && this.fascicolo.schedaTecnica.legittimita) {
      let builders = {
        autorizzatoPaesaggisticamenteInfo: this.buildLegittimitaForm.bind(this)
      };
      ViewMapper.mapObjectToForm(this.legittimita, this.fascicolo.schedaTecnica.legittimita, builders);
    } else {
      /* this.addLegittimitaInfoInfo();
      this.addAutorizzatoPaesaggisticamenteInfo(); */
    }
  }

  buildForm() {
    this.mainForm.addControl('legittimita',
      this.fb.group({
        tipoLegittimitaUrbanistica: [null, Validators.required],
        legittimitaUrbanstica: [''],
        legittimitaInfo: this.fb.array([]),
        dettaglioLegittimitaPaesaggistica: this.fb.group({
          tipologiaVincolo: [''],
          dataIntervento: [''],
          dataImposizioneVincolo: ['']
        }),
        tipoLegittimitaPaesaggistica: [null, Validators.required],
        legitimitaInfo: this.fb.array([]),
        autorizzatoPaesaggisticamenteInfo: this.fb.array([])
      })
    );
    //cross validation
    let legittimitaPaesag = this.legittimita.get("tipoLegittimitaPaesaggistica");
    let legittimitaUrb = this.legittimita.get("tipoLegittimitaUrbanistica");
    let dettaglioPaesaggio = this.legittimita.get("dettaglioLegittimitaPaesaggistica"); 
    dettaglioPaesaggio.get("tipologiaVincolo").setValidators(requiredDependsOn(legittimitaPaesag, 1));
    dettaglioPaesaggio.get("dataIntervento").setValidators(requiredDependsOn(legittimitaPaesag, 1));
    dettaglioPaesaggio.get("dataImposizioneVincolo").setValidators(requiredDependsOn(legittimitaPaesag, 1));
    this.legittimita.get("autorizzatoPaesaggisticamenteInfo").setValidators(requiredDependsOn(legittimitaPaesag, 2));
    this.legittimita.get("legittimitaUrbanstica").setValidators(requiredDependsOn(legittimitaUrb, 1));
    this.legittimita.get("legittimitaInfo").setValidators(requiredDependsOn(legittimitaUrb, 2));
  }
  get showSpecificaLegitimita() {

    const specificaVincolo = this.legittimita.get('specificaVincolo');
    const sottopostoValue = this.legittimita.get('sottopostoATutela').value;

    sottopostoValue === 2 ?
      specificaVincolo.setValidators([Validators.required]) :
      (specificaVincolo.clearValidators(), specificaVincolo.patchValue([]));

    return sottopostoValue === 2 ? true : false;

  }

  showFormControl(subordinate, superior, condition) 
  {
    //const subordinateControl = this.legittimita.get(subordinate);
    const superiorValue = this.legittimita.get(superior).value;
    return superiorValue === condition;
    /* return superiorValue === condition ?
      (subordinateControl.setValidators([Validators.required]), true) :
      (subordinateControl.clearValidators(), subordinateControl.patchValue([]), false); */
  }

  addLegittimitaInfoInfo() {
    this.legittimitaInfo.push(
      this.buildLegittimitaForm()
    );
  }

  addAutorizzatoPaesaggisticamenteInfo() {
    this.autorizzatoPaesaggisticamenteInfo.push(
      this.buildLegittimitaForm()
    );
  }

  buildLegittimitaForm() {
    return this.fb.group({
      tipologia: ['', Validators.required],
      rialisciatoDa: ['', Validators.required],
      noProtocollo: ['', Validators.required],
      dataRilascio: ['', Validators.required],
      intestinario: ['', Validators.required]
    });
  }

  public spegniValidators(): void {
    if (this.legittimita.get("tipoLegittimitaPaesaggistica").value === 2) {
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("tipologiaVincolo").clearValidators();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("tipologiaVincolo").updateValueAndValidity();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento").clearValidators();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento").updateValueAndValidity();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataImposizioneVincolo").clearValidators();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataImposizioneVincolo").updateValueAndValidity();
    }
    else if (this.legittimita.get("tipoLegittimitaPaesaggistica").value === 1) {
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("tipologiaVincolo").setValidators([Validators.required]);
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("tipologiaVincolo").updateValueAndValidity();
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento").setValidators([Validators.required]);
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento").updateValueAndValidity();
      let dataInterventoValue=this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataIntervento");
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataImposizioneVincolo").setValidators([Validators.required,validDate(dataInterventoValue)]);
      this.legittimita.get("dettaglioLegittimitaPaesaggistica").get("dataImposizioneVincolo").updateValueAndValidity();
    }
  }

}
