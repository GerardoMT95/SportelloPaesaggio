import { ComuneService } from 'src/app/services/comune.service';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, AbstractControl, FormBuilder } from '@angular/forms';
import { CONST } from 'src/shared/constants';
import { SelectItem } from 'primeng/components/common/selectitem';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { requiredDependsOn } from '../../validators/customValidator';
import { InformazioniDTO } from '../../model/entity/info.models';
import { Projects } from '../../model/enum';
import { environment } from 'src/environments/environment';
import { RegistrationStatus } from '../../model/entity/fascicolo.models';

@Component({
  selector: 'app-form-richiedente',
  templateUrl: './form-richiedente.component.html',
  styleUrls: ['./form-richiedente.component.css']
})
export class FormRichiedenteComponent implements OnInit
{
  
  @Input() disableAndNotEditable:boolean = false;
  @Input() praticaId:string;
  @Input() fascicoloFormRichiedente: FormGroup;
  @Input() submitted: boolean;
  @Input() cfErrorRichiedente: boolean; //true se cf errato
  @Input() disable: boolean; //disabilita tutti i control
  @Input() dettaglioFascicolo: InformazioniDTO;
  public activeIds: string[] = ['responsabile'];//default aperto

  public maxLengthInput: number = CONST.MAX_LENGTH_INPUT;
  public MAX_YEAR = CONST.NOW_YEAR;
  public const=CONST;
  public it: any = CONST.IT;
  public tipi_ditta: SelectItem[];
  public comuneAutocomplete: SelectItem[];
  public comuneEnteAutocomplete: SelectItem[];
  public provinciaAutocomplete: SelectItem[];
  public nazionalitaAutocomplete: SelectItem[];
  public nazionalitaNascitaTitolare: boolean = false;
  public nazionalitaResidenzaTitolare: boolean = false;
  public rsEnum=RegistrationStatus;


  public constructor(private autPaesSvc: AutorizzazioniPaesaggisticheService,
                     private comuneSvc: ComuneService,
                     private fb: FormBuilder){};

  ngOnInit() 
  {
    this.autPaesSvc.getTipiRuoloInDitta().subscribe(res => this.tipi_ditta = res);
    this.activeExtraField();
    let dittaControl: AbstractControl = this.fascicoloFormRichiedente.get("ditta");
    let qualitaDiControl: AbstractControl = this.fascicoloFormRichiedente.get("dittaInQualitaDi");

    this.fascicoloFormRichiedente.get("dittaSocieta").setValidators(requiredDependsOn(dittaControl, true));
    this.fascicoloFormRichiedente.get("dittaCodiceFiscale").setValidators(requiredDependsOn(dittaControl, true));
    this.fascicoloFormRichiedente.get("dittaPartitaIva").setValidators(requiredDependsOn(dittaControl, true));
    qualitaDiControl.setValidators(requiredDependsOn(dittaControl, true));
    this.fascicoloFormRichiedente.get("dittaQualitaAltro").setValidators(requiredDependsOn(qualitaDiControl, "ALTRO"));

    //valuto quali input devo mostrare per luogo nascita
    if (this.fascicoloFormRichiedente.get("nazionalitaNascitaName").value &&
        this.fascicoloFormRichiedente.get("nazionalitaNascitaName").value.value === "IT")
    {
      this.nazionalitaNascitaTitolare = true;
      this.fascicoloFormRichiedente['controls']["provinciaNascitaName"].enable();
      this.fascicoloFormRichiedente['controls']["comuneNascitaName"].disable();
      this.fascicoloFormRichiedente['controls']["comuneNascitaEstero"].disable();
      if (this.fascicoloFormRichiedente.get("provinciaNascitaName").value &&
          this.fascicoloFormRichiedente.get("provinciaNascitaName").value.label)
      {
        this.fascicoloFormRichiedente['controls']['comuneNascitaName'].enable();
      }
      else
      {
        this.fascicoloFormRichiedente['controls']['comuneNascitaName'].disable();
      }
    }
    //valuto quali input devo mostrare per luogo residenza
    if (this.fascicoloFormRichiedente.get("nazionalitaResidenzaName").value &&
        this.fascicoloFormRichiedente.get("nazionalitaResidenzaName").value.value === "IT")
    {
      this.nazionalitaResidenzaTitolare = true;
      this.fascicoloFormRichiedente['controls']["provinciaResidenzaName"].enable();
      this.fascicoloFormRichiedente['controls']["comuneResidenzaName"].disable();
      this.fascicoloFormRichiedente['controls']["comuneResidenzaEstero"].disable();
      if (this.fascicoloFormRichiedente.get("provinciaResidenzaName").value &&
        this.fascicoloFormRichiedente.get("provinciaResidenzaName").value.label)
      {
        this.fascicoloFormRichiedente['controls']['comuneResidenzaName'].enable();
      }
      else
      {
        this.fascicoloFormRichiedente['controls']['comuneResidenzaName'].disable();
      }
    }

    if(this.dettaglioFascicolo.stato==this.rsEnum.TRANSMITTED){
      this.fascicoloFormRichiedente.disable();
    }
  }

  public uppercase(name: string)
  {
    let value = this.fascicoloFormRichiedente['controls'][name].value.toUpperCase();
    this.fascicoloFormRichiedente['controls'][name].setValue(value);
  }

  activeAltroDitta(val: any): void
  {
    if (!this.disable)
    {
      this.fascicoloFormRichiedente['controls'].dittaQualitaAltro.setValue('');
      this.fascicoloFormRichiedente.updateValueAndValidity();
      if (val.value == 'ALTRO')
        this.fascicoloFormRichiedente['controls'].dittaQualitaAltro.enable();
      else
        this.fascicoloFormRichiedente['controls'].dittaQualitaAltro.disable();
    }
  }

  public activeExtraField(): void
  {
    if (!this.disable || !this.disableAndNotEditable)
    {
      if (this.fascicoloFormRichiedente['controls'].ditta.value)
      {
        this.fascicoloFormRichiedente['controls'].dittaInQualitaDi.enable();
        this.fascicoloFormRichiedente['controls'].dittaSocieta.enable();
        this.fascicoloFormRichiedente['controls'].dittaCodiceFiscale.enable();
        this.fascicoloFormRichiedente['controls'].dittaPartitaIva.enable();
      } else
      {
        this.fascicoloFormRichiedente['controls'].dittaInQualitaDi.disable();
        this.fascicoloFormRichiedente['controls'].dittaInQualitaDi.setValue('');
        this.fascicoloFormRichiedente['controls'].dittaQualitaAltro.disable();
        this.fascicoloFormRichiedente['controls'].dittaQualitaAltro.setValue('');
        this.fascicoloFormRichiedente['controls'].dittaSocieta.disable();
        this.fascicoloFormRichiedente['controls'].dittaSocieta.setValue('');
        this.fascicoloFormRichiedente['controls'].dittaCodiceFiscale.disable();
        this.fascicoloFormRichiedente['controls'].dittaCodiceFiscale.setValue('');
        this.fascicoloFormRichiedente['controls'].dittaPartitaIva.disable();
        this.fascicoloFormRichiedente['controls'].dittaPartitaIva.setValue('');
      }
    }
  }

  onBlur(inputFocus: any): void
  {
    if (inputFocus.el != null && inputFocus.el.nativeElement.nodeName == "P-AUTOCOMPLETE")
      inputFocus.el.nativeElement.children[0].focus();
    else
      inputFocus.focus();
  }


  public onClear(idselect: string, parent?: string)
  {
    if (parent)
    {
      this.fascicoloFormRichiedente['controls'][idselect].disable();
      this.fascicoloFormRichiedente['controls'][idselect].setValue('');
    } else
    {
      this.fascicoloFormRichiedente.controls[idselect].disable();
      this.fascicoloFormRichiedente.controls[idselect].setValue('');
    }
  }

  public search(event: any, idselect: string, parent: string)
  {
    let parentId = null;
    if (idselect)
      parentId = this.fascicoloFormRichiedente['controls'][idselect].value;
    let id: number = null;
    let label: string = null;
    if (parentId && parentId.label)
    {
      label = parentId.label;
      id = parentId.value;
    }
    else
      id = parentId;
    this.comuneSvc.autocompleteComuni(event.query, label, id, 50).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload)
      {
        this.comuneAutocomplete = result.payload;
      }
    });
  }

  public searchProv(event: any)
  {
    this.comuneSvc.autocompleteProvice(event.query, 50).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload)
      {
        this.provinciaAutocomplete = result.payload;
      }
    });
  }

  public searchNaz(event: any)
  {
    this.comuneSvc.autocompleteNazioni(event.query, 50).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK && result.payload)
      {
        this.nazionalitaAutocomplete = result.payload;
      }
    });
  }

  public setNazionalita(event: any, idcontrollo: string, provincia: string, comune: string, comuneEstero: string): void
  {
    this.fascicoloFormRichiedente['controls'][comune].setValue('')
    this.fascicoloFormRichiedente['controls'][provincia].setValue('');
    if (event.value == "IT")
    {
      this[idcontrollo] = true;
      this.fascicoloFormRichiedente['controls'][comune].disable();
      this.fascicoloFormRichiedente['controls'][provincia].enable();
      this.fascicoloFormRichiedente['controls'][comuneEstero].disable();
    } else
    {
      this[idcontrollo] = false;
      this.fascicoloFormRichiedente['controls'][comuneEstero].enable();
      this.fascicoloFormRichiedente['controls'][provincia].disable();
      this.fascicoloFormRichiedente['controls'][comune].disable();
    }

  }

  public abilitaComune(event: any, idselect: string, parent?: string): void
  {
    if (parent)
      this.fascicoloFormRichiedente['controls'][idselect].enable();
    else
      this.fascicoloFormRichiedente.controls[idselect].enable();
  }

  get pareri(): boolean { return this.const.isPareri(); }

}
