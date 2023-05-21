import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn, FormControl } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { BasicFile } from 'src/app/shared/models/allegati.model';
import { HttpDichiarazioniSettingsService } from 'src/app/shared/services/dichiarazioni/http-dichiarazioni-settings.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { HierarchicalFieldValidators } from './../../../../shared/components/hierarchical-field/hierarchical-field-validators';
import { CONST } from './../../../../shared/constants';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { HierarchicalOption } from './../../../../shared/models/models';
import { IstanzaConst } from './../../constants/istanza-const';
import { LocalizzazioneService } from './../../services/localizzazione/localizzazione.service';


@Component({
  selector: 'app-dichiarazioni',
  templateUrl: './dichiarazioni.component.html',
  styleUrls: ['./dichiarazioni.component.scss']
})
export class DichiarazioniComponent extends AbstractTab implements OnInit, OnDestroy
{
  @Input() files: BasicFile[] = [];
  @Input() titolaritaInQualitaDiOptions: SelectOption[];
  @Input() disclaimer: any[];

  @Output() onUpload: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDownload: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDelete: EventEmitter<any> = new EventEmitter<any>();

  unsubscribe$ = new Subject<void>();
  dichiarazioni: FormGroup;
  types: DocumentType[] = [
    {
      type: "DICHIARAZIONI_ASSENSO",
      label: "Dichiarazione d'assenso",
      multiple: false,
      required: true,
      accept: null,
      maxSize: null,
    }
  ];
  headers=[
    {
        field: "type",
        header: "Tipologia",
        type: "type"
    },
    {
        field: "nome",
        header: "Nome file",
        type: "text"
    },
    {
        field: "data",
        header: "Data caricamento",
        type: "date"
    }
];

  public disclaimer2: any[] = IstanzaConst.disclaimer;
  public art134opt: SelectItem = IstanzaConst.art134;
  public art136opt: HierarchicalOption = IstanzaConst.art136;
  public art142opt: HierarchicalOption = IstanzaConst.art142;

  constructor(private fb: FormBuilder,
              private httpDichiarazioniSettingsService: HttpDichiarazioniSettingsService,
              private httpDominioService: HttpDominioService,
              private loc: LocalizzazioneService) { super(); }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit() 
  {
    if (this.fascicolo.tipoProcedimento == '2')
    {
      this.httpDominioService.findUlterioriContestiPaesaggisticiHierarchical2(parseInt(this.fascicolo.tipoProcedimento), 'art_142').subscribe(result =>
      {
        if (result.codiceEsito === CONST.OK && result.payload)
        {
          this.art142opt.children = result.payload;
          this.art142opt.children.options.forEach((item, index, array) =>
          {
            if (item.value === 'PARCHI_E_RISERVE')
              array[index].disabled = true;
          });
          this.buildForm();
          //this.watchChanges();
          ViewMapper.mapObjectToForm(this.dichiarazioni, this.fascicolo.istanza.dichiarazioni);
          if(this.dichiarazioni.controls.art142.value)
          {
            let value = this.fascicolo.istanza.dichiarazioni.art142.map(m => { return { value: m, text: null } });
            this.dichiarazioni.controls.art142.setValue(value);
            //this.dichiarazioni.controls.art142.setValue(value.filter(f => f.value != '142'));
          }
          if (this.dichiarazioni.controls.art136.value)
            this.dichiarazioni.controls.art136.setValue(this.fascicolo.istanza.dichiarazioni.art136.map(m => { return { value: m, text: null } }));
          this.checkInitialState();
          this.startWatchLocalizzazione();
          this.dichiarazioni.disable();
        }
      });
    }
    else
    {
      this.buildForm();
      //this.watchChanges();
      ViewMapper.mapObjectToForm(this.dichiarazioni, this.fascicolo.istanza.dichiarazioni);
      this.checkInitialState();
      this.dichiarazioni.disable();
    }
  }

  buildForm()
  {
    this.dichiarazioni = this.fb.group({
      //useDiAvereTitolo: [true],
      titolarita: [null, Validators.required],
      diAvereTitoloAltroSpec: [null],
      diAvereTitoloRappSpec: [null],
      titolaritaEsclusivaIntervento: [null, Validators.required],
      inCasoDiVariante: [false],
      numero: [null],
      da: [null],
      inData: [null],
      art142: [[], HierarchicalFieldValidators.required(this.art142opt)],
      art136: [[], HierarchicalFieldValidators.required(this.art136opt)],
      art134: [[]],
      //altreOpzioni: [[],this.tutteSelezionateValidator(this.disclaimer)],//devono essere tutte obbligatorie
      //altreOpzioni: [[], [Validators.required, Validators.minLength(this.disclaimer.length)]],
      altreOpzioni: [[],],//devono essere tutte obbligatorie NON attivare validator che mandano in loop il controllo
    });
    if (this.fascicolo.tipoProcedimento === '2')
    {
      this.dichiarazioni.get('inCasoDiVariante').valueChanges.subscribe(
        val =>
        {
          const fg: FormGroup = this.dichiarazioni as FormGroup;
          if (!val)
          {
            fg.get('numero').disable();
            fg.get('da').disable();
            fg.get('inData').disable();
          } else
          {
            fg.get('numero').enable();
            fg.get('numero').setValidators(Validators.required);
            fg.get('da').enable();
            fg.get('da').setValidators(Validators.required);
            fg.get('inData').enable();
            fg.get('inData').setValidators(Validators.required);
          }
        }
      )
    }

    this.dichiarazioni.get('diAvereTitoloRappSpec').disable();
    this.dichiarazioni.get('diAvereTitoloAltroSpec').disable();
    this.dichiarazioni.get('titolarita').valueChanges.subscribe(el => this.gestisciSpecifica(el));
    this.mainForm.addControl('dichiarazioni', this.dichiarazioni);
  }

  gestisciSpecifica(nuovoValore: number)
  {
    let selectConSpecifica =
      this.titolaritaInQualitaDiOptions.find(el => el.value == nuovoValore);
    if (!selectConSpecifica)
    {
      return;
    }
    this.dichiarazioni.get('diAvereTitoloRappSpec').setValidators(null);
    this.dichiarazioni.get('diAvereTitoloRappSpec').disable();
    this.dichiarazioni.get('diAvereTitoloRappSpec').updateValueAndValidity();
    this.dichiarazioni.get('diAvereTitoloAltroSpec').setValidators(null);
    this.dichiarazioni.get('diAvereTitoloAltroSpec').disable();
    this.dichiarazioni.get('diAvereTitoloAltroSpec').updateValueAndValidity();
    if (selectConSpecifica && selectConSpecifica.linked != '' &&
      this.dichiarazioni.get(selectConSpecifica.linked))
    {
      this.dichiarazioni.get(selectConSpecifica.linked).enable();
      //this.dichiarazioni.get(selectConSpecifica.linked).setValue(null);
      this.dichiarazioni.get(selectConSpecifica.linked).setValidators(Validators.required);
      this.dichiarazioni.get(selectConSpecifica.linked).updateValueAndValidity();
    } else
    {
      this.dichiarazioni.get('diAvereTitoloAltroSpec').setValue(null);
      this.dichiarazioni.get('diAvereTitoloRappSpec').setValue(null);
    }
  }

  watchChanges()
  {
    this.dichiarazioni.get('titolaritaEsclusivaIntervento').valueChanges.subscribe(val =>
    {
      if (val === 'S')
        this.mainForm.get('altriTitolari').disable();
      else
        this.mainForm.get('altriTitolari').enable();
    });
  }

  checkInitialState()
  {
    if (this.dichiarazioni.get('titolaritaEsclusivaIntervento').value === 'S')
    {
      this.mainForm.get('altriTitolari').disable();
    }
    this.gestisciSpecifica(this.dichiarazioni.get('titolarita').value);
    //this.spegniFiglie(this.dichiarazioni.get('interventaNecesitaArt146').value);
  }

  private startWatchLocalizzazione(): void
  {
    this.loc.localizzazioneObs.pipe(takeUntil(this.unsubscribe$)).subscribe(next =>
    {
      if (next.comuneSelezionato === true)
      {
        /* let tmp = this.dichiarazioni.controls.art136.value;
        let index = tmp ? tmp.map(m => m.value).indexOf('art136') : -1;
        if (index != -1)
          tmp.splice(index);
        this.dichiarazioni.controls.art136.setValue(tmp);
        this.art136opt.options[0].disabled = true; */
        if (next.bpParchiRiserve === true)
        {
          let tmp = this.dichiarazioni.controls.art142;
          if (!tmp.value || !tmp.value.map(m => m.value).includes('142'))
            tmp.setValue([{ value: '142', text: null }]);
          this.art142opt.options[0].disabled = true;
        }
        if (next.bpImmobileAreeInteresse === true)
        {
          let tmp = this.dichiarazioni.controls.art136;
          if (!tmp.value || !tmp.value.map(m => m.value).includes('136'))
            tmp.setValue([{ value: '136', text: null }]);
          this.art136opt.options[0].disabled = true;
        }
      }
    });
  }

  public uploadFile(event: any): void { this.onUpload.emit(event); }
  public downloadFile(event: any): void { this.onDownload.emit(event); }
  public deleteFile(): void { this.onDelete.emit(); }

  public attivaVariante(event: any): void {
    if (event) {
      this.dichiarazioni.get("numero").setValidators([Validators.required]);
      this.dichiarazioni.get("numero").updateValueAndValidity();
      this.dichiarazioni.get("da").setValidators([Validators.required]);
      this.dichiarazioni.get("da").updateValueAndValidity();
      this.dichiarazioni.get("inData").setValidators([Validators.required]);
      this.dichiarazioni.get("inData").updateValueAndValidity();
    }
    else {
      this.dichiarazioni.get("numero").clearValidators();
      this.dichiarazioni.get("numero").setValue(null);
      this.dichiarazioni.get("numero").updateValueAndValidity();
      this.dichiarazioni.get("da").clearValidators();
      this.dichiarazioni.get("da").setValue(null);
      this.dichiarazioni.get("da").updateValueAndValidity();
      this.dichiarazioni.get("inData").clearValidators();
      this.dichiarazioni.get("inData").setValue(null);
      this.dichiarazioni.get("inData").updateValueAndValidity();
    }
  }

  tutteSelezionateValidator(disclaimer:SelectOption[]):ValidatorFn{
    let validatorFn=
    (control: FormControl): ValidationErrors | null =>{
      const checked = control.value;
      let numChecked=0;
      if(checked && Array.isArray(checked) && checked.length>0){
        for(let i=0;i<checked.length;i++){
          disclaimer.findIndex(el=>el.value==checked[i])>=0?numChecked++:null;
        }
        if(numChecked>=disclaimer.length){
          return null;
        }
      }
      return  { 'notAllChecked': true };
    }
    return validatorFn;
  }

  

}