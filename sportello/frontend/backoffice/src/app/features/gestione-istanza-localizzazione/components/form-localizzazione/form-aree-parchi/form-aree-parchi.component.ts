import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable, Subscription } from 'rxjs';
/* import { requiredIfHaveOptions } from 'src/app/components/validators/customValidator'; */
/* import { GlobalService } from 'src/app/services/global.service'; */
/* import { AutorizzazioniPaesaggisticheService } from './../../../../services/autorizzazioni-paesaggistiche.service'; */

@Component({
  selector: 'app-form-aree-parchi',
  templateUrl: './form-aree-parchi.component.html',
  styleUrls: ['./form-aree-parchi.component.css']
})

export class FormAreeParchiComponent implements OnInit
{
  @Input() fascicoloFormLocalizzazione: FormGroup;
  @Input() submitted: boolean;
  @Input() disable: boolean; //disabilita tutti i control
  @Input() codiceFascicolo: string;
  @Input() id: string;//id fascicolo
  @Input() cambioParticelle$: Observable<{ comuni: string[], province: string[] }>;

  public bpParchiRiserve: SelectItem[];
  public bpPaesaggioRurale: SelectItem[];
  public bpImmobiliAreeInteresse: SelectItem[];

  lastEntiSet: Set<string> = null;
  subscriptionParicelle: Subscription;
  init: boolean;

  constructor(/* private globalService: GlobalService, */
    /* private autPaesSvc: AutorizzazioniPaesaggisticheService */) { }

  ngOnInit() 
  {
    this.subscriptionParicelle = this.cambioParticelle$.subscribe(data =>
    {
      this.refreshSelect(data);
      this.fascicoloFormLocalizzazione.markAsTouched();
    });

  }

  get form() { return this.fascicoloFormLocalizzazione.controls; }

  /**
   * 
   * @param a set
   * @param b set
   */
  isSetsEqual<T>(a: Set<T>, b: Set<T>)
  {
    if (a && !b) return false;
    if (!a && b) return false;
    if (!a && !b) return true;
    return a.size === b.size && [...Array.from(a)].every(value => b.has(value));
  }

  /**
   * controlla se ci sono stati cambiamenti sensibili al cambio set di valori nelle select
   * @param data formarray value della particella
   */
  refreshSelect(data: { comuni: string[], province: string[] })
  {
    if (data)
    {
      let controls = this.fascicoloFormLocalizzazione.controls;
      let enti: Set<string> = data.province ? new Set<string>(data.province) : new Set();
      if (data.comuni)
        data.comuni.forEach(c => enti.add(c));
      if (data && !this.isSetsEqual(this.lastEntiSet, enti))
      {
        this.lastEntiSet = enti;
        let _this = this;
        /* this.autPaesSvc.getDropdownsParchi(data.province, data.comuni).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK && response.payload)
          {
            _this.bpParchiRiserve = response.payload.parchi.map(m => <SelectItem>{ label: m.label.replace("'", "\'"), value: m.codice });
            _this.bpPaesaggioRurale = response.payload.paesaggiRurali.map(m => <SelectItem>{ label: m.label.replace("'", "\'"), value: m.codice });
            _this.bpImmobiliAreeInteresse = response.payload.immobiliAreeInteresse.map(m => <SelectItem>{ label: m.label.replace("'", "\'"), value: m.codice });

            controls.bpParchiRiserve.setValidators(requiredIfHaveOptions(_this.bpParchiRiserve));
            controls.bpPaesaggioRurale.setValidators(requiredIfHaveOptions(_this.bpPaesaggioRurale));
            controls.bpImmobiliAreeInteresse.setValidators(requiredIfHaveOptions(_this.bpImmobiliAreeInteresse));

            //verifico che i valori settati siano congruenti alle nuove options, preoccupandomi
            //di eliminare quelli non coerenti
            if (controls.bpParchiRiserve.value)
            {
              controls.bpParchiRiserve.value.forEach((p, index) =>
              {
                if (!_this.bpParchiRiserve.map(m => m.value).includes(p))
                  controls.bpParchiRiserve.value.splice(index, 1);
              });
            }

            if (controls.bpPaesaggioRurale.value)
            {
              controls.bpPaesaggioRurale.value.forEach((p, index) =>
              {
                if (!this.bpPaesaggioRurale.map(m => m.value).includes(p))
                  controls.bpPaesaggioRurale.value.splice(index, 1);
              });
            }

            if (this.fascicoloFormLocalizzazione.controls.bpImmobiliAreeInteresse.value)
            {
              this.fascicoloFormLocalizzazione.controls.bpImmobiliAreeInteresse.value.forEach((p, index) =>
              {
                if (!this.bpImmobiliAreeInteresse.map(m => m.value).includes(p))
                  this.fascicoloFormLocalizzazione.controls.bpImmobiliAreeInteresse.value.splice(index, 1);
              });
            }
            controls.bpParchiRiserve.updateValueAndValidity();
            controls.bpPaesaggioRurale.updateValueAndValidity();
            controls.bpImmobiliAreeInteresse.updateValueAndValidity();
          }
        }); */
      }
    }
  }
}
