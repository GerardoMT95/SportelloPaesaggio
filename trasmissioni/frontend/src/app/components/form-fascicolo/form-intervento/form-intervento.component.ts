import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { environment } from 'src/environments/environment';
import { CONST } from 'src/shared/constants';
import { sottoGruppoInterventoRequired } from '../../validators/specificValidatorFascicolo';
import { TipiEQualificazioniDTO } from './../../model/entity/intervento.models';


@Component({
  selector: 'app-form-intervento',
  templateUrl: './form-intervento.component.html',
  styleUrls: ['./form-intervento.component.css']
})

export class FormInterventoComponent implements OnInit
{
  @Input() fascicoloFormIntervento: FormGroup;
  @Input() tipoProcedimento: string;
  @Input() submitted: boolean;
  @Input() disable: boolean = false; //disabilita tutti i control
  @Input() listaIntervento: TipiEQualificazioniDTO[];
  @Input() listaIntervento$: Observable<TipiEQualificazioniDTO[]>;
  public CONST = CONST;
  public env = environment;


  /* @Input() inputs: TipiEQualificazioniDTO[]; */
  public groups: { [key: string]: { [key: string]: TipiEQualificazioniDTO[] } } = {};
  public labels: any = CONST.labelMapping();
  public legends: any = CONST.footerMapping;
  public index1: string[] = [];
  public index2: Map<string, string[]> = new Map<string, string[]>();
  public disabilitati: string[] = [];
  private inputs: TipiEQualificazioniDTO[];


  public maxLength: number = CONST.MAX_LENGTH_TEXTAREA;
  enableRimessaInRipristinoDettaglio: boolean = true;
  enableAltroSpecificare: boolean = true;

  constructor(private autPaeSvc: AutorizzazioniPaesaggisticheService) { }

  ngOnInit() 
  {
    let fascisolo = this.fascicoloFormIntervento;

    this.listaIntervento$.subscribe(listaIntervento =>
    {
      this.dividiInput(listaIntervento);

      let raggruppamenti = [];
      for(let elem of listaIntervento)
      {
        if (!raggruppamenti.includes(elem.raggruppamento))
          raggruppamenti.push(elem.raggruppamento);
      }
      for(let r of raggruppamenti)
      {
        if(this.inputs.filter(f => f.raggruppamento === r).some(s => !s.dependesOn || s.dependesOn === 0))
          this.fascicoloFormIntervento.get(r).setValidators([Validators.required]);
      }
      this.fascicoloFormIntervento.updateValueAndValidity();
      
      /*if (fascisolo['controls']['a'].value &&
        !fascisolo['controls']['a'].value.includes('172'))
        {
          fascisolo['controls']['interventoDettaglio'].disable();  
        }else if(fascisolo['controls']['a'].value &&
          fascisolo['controls']['a'].value.includes('172')){
          fascisolo['controls']['interventoDettaglio'].setValidators(Validators.required);  
          fascisolo['controls']['interventoDettaglio'].updateValueAndValidity();  
      }*/

      Object.keys(this.fascicoloFormIntervento.controls).forEach(key =>
      {
        this.fascicoloFormIntervento.controls[key].updateValueAndValidity();
      });


      if (this.disable)
      {
        this.fascicoloFormIntervento.disable();
      }

      if (!fascisolo['controls']['b'].value && CONST.isAutpae())
      {
        fascisolo['controls']['interventoDettaglio'].disable();
        fascisolo['controls']['interventoSpecifica'].disable();
      }
      if (fascisolo['controls']['b'].value &&
        !fascisolo['controls']['b'].value.includes('6'))
        fascisolo['controls']['interventoDettaglio'].disable();
      if (fascisolo['controls']['b'].value &&
        !fascisolo['controls']['b'].value.includes('19'))
        fascisolo['controls']['interventoSpecifica'].disable();
      if (fascisolo['controls']['a'].value &&
        !fascisolo['controls']['a'].value.includes('172') && CONST.isPareri())
      {
        fascisolo['controls']['interventoDettaglio'].disable();
      }
    });
    this.autPaeSvc.getTipoProcedimento$().subscribe(newval => {
      if(CONST.isPutt() && fascisolo['controls']['j']){
        fascisolo['controls']['j'].setValue(null); //al cambio di tipo procedimento il raggruppamento j deve essere ripulito.
      }
		});

  }

  get form() { return this.fascicoloFormIntervento.controls; }

  public dividiInput(inputs: TipiEQualificazioniDTO[]): void
  {
    let _this = this;
    _this.groups = {};
    _this.index1 = [];
    _this.disabilitati = [];
    _this.index2.clear();
    _this.inputs = inputs;
    if (inputs)
    {
      inputs.forEach(input =>
      {
        let zona: number;
        if (input.zona >= 100)
        {
          if (input.zona > 10000)
          {
            input.dependesOn = input.zona / 100;
            zona = input.zona / 10000;
            _this.fascicoloFormIntervento.get(input.raggruppamento).setValidators(sottoGruppoInterventoRequired(inputs, _this.fascicoloFormIntervento, input.dependesOn));
          }
          else
            zona = input.zona / 100;

          if (input.zona >= 10000 && !_this.dipendenzaSoddisfatta(input.dependesOn, inputs))
            _this.disabilitati.push(input.id.toString());
        }
        else
          zona = input.zona === 3 ? 2 : input.zona;
        if (!_this.groups[zona])
        {
          _this.groups[zona] = {};
          _this.index1.push(zona.toString());
        }
        _this.groups[zona][input.raggruppamento] ? _this.groups[zona][input.raggruppamento].push(input)
          : _this.groups[zona][input.raggruppamento] = [input];
        if (_this.index2.has(zona.toString()))
        {
          if (!_this.index2.get(zona.toString()).includes(input.raggruppamento))
            _this.index2.get(zona.toString()).push(input.raggruppamento);
        }
        else
          _this.index2.set(zona.toString(), [input.raggruppamento]);
      });
      for (let i of this.index1)
      {
        this.index2.get(i).forEach(array =>
        {
          this.groups[i][array] = this.groups[i][array].sort((arg1, arg2) => arg1.ordine - arg2.ordine);
        });
      }
    }
  }

  private dipendenzaSoddisfatta(dependesOn: number, inputs: TipiEQualificazioniDTO[]): boolean
  {
    let soddisfatto: boolean = false;
    let inputCheAttivano = inputs.filter(f => f.zona === dependesOn).map(m => m.id.toString());
    Object.keys(this.fascicoloFormIntervento['controls']).forEach(keys =>
    {
      let completecheck = this.fascicoloFormIntervento['controls'][keys].value &&
        ((this.fascicoloFormIntervento['controls'][keys].value instanceof Array &&
          this.fascicoloFormIntervento['controls'][keys].value.some(p => inputCheAttivano.includes(p))) ||
          (inputCheAttivano.includes(this.fascicoloFormIntervento['controls'][keys].value.toString())))
      if ((keys != "interventoDettaglio" && keys != "interventoSpecifica") && completecheck)
        soddisfatto = true;
    });
    return soddisfatto;
  }

  attivaInput(index: string, key: string, zona: number)
  {
    if (key === 'b')
    {
      if (this.fascicoloFormIntervento['controls']['b'].value.includes("6"))
      {
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].enable()
      }
      else
      {
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].disable();
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].patchValue("");
      }

      if (this.fascicoloFormIntervento['controls']['b'].value.includes("19"))
      {
        this.fascicoloFormIntervento['controls']['interventoSpecifica'].enable()
      }
      else
      {
        this.fascicoloFormIntervento['controls']['interventoSpecifica'].disable();
        this.fascicoloFormIntervento['controls']['interventoSpecifica'].patchValue("");
      }

    }
    //pareri....
    if (key === 'a' && CONST.isPareri())
    {
      if (this.fascicoloFormIntervento['controls']['a'].value.includes("172"))
      {
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].enable()
        /*this.fascicoloFormIntervento['controls']['interventoDettaglio'].setValidators(Validators.required);
        if(this.submitted){
          this.fascicoloFormIntervento['controls']['interventoDettaglio'].updateValueAndValidity();
        }*/
      }
      else
      {
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].disable();
        this.fascicoloFormIntervento['controls']['interventoDettaglio'].patchValue("");
        /*this.fascicoloFormIntervento['controls']['interventoDettaglio'].clearValidators();
        if(this.submitted){
          this.fascicoloFormIntervento['controls']['interventoDettaglio'].updateValueAndValidity();
        }*/
      }
    }
    this.checkDisableInput();
  }

  private checkDisableInput(): void
  {
    let _this = this;
    let temp = _this.inputs.filter(p => p.zona > 10);
    if (temp)
    {
      temp.forEach(t =>
      {
        let dependesOn = t.zona / 100;
        _this.fascicoloFormIntervento['controls'][t.raggruppamento].updateValueAndValidity();
        if (t.zona >= 10000)
        {
          if (_this.dipendenzaSoddisfatta(dependesOn, this.inputs))
          {
            //rimuovo il segnalino "disabilitato"
            let i = _this.disabilitati.indexOf(t.id.toString());
            if (i >= 0)
              _this.disabilitati.splice(i, 1);
          }
          else
          {
            //rimuovo il valore se presente
            if (_this.fascicoloFormIntervento['controls'][t.raggruppamento].value instanceof Array)
            {
              _this.fascicoloFormIntervento['controls'][t.raggruppamento].setValue("");
              /* let i = _this.fascicoloFormIntervento['controls'][t.raggruppamento].value.indexOf(t.id.toString());
              if (i >= 0)
                _this.fascicoloFormIntervento['controls'][t.raggruppamento].value.splice(i, 1); */
            }
            if (!_this.disabilitati)
              _this.disabilitati = [];
            if (!_this.disabilitati.includes(t.id.toString()))
              _this.disabilitati.push(t.id.toString());
          }
        }
      });
    }
  }
}
