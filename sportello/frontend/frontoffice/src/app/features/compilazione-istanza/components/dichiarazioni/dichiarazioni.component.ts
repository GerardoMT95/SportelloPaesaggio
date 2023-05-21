import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn, FormControl, AbstractControl, FormArray } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractTab } from 'src/app/core/pages/abstract-tab';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { BasicFile } from 'src/app/shared/models/allegati.model';
import { HttpDichiarazioniSettingsService } from 'src/app/shared/services/http-dichiarazioni-settings.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { HierarchicalFieldValidators } from './../../../../shared/components/hierarchical-field/hierarchical-field-validators';
import { CONST } from './../../../../shared/constants';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { ConfigOptionValue, HierarchicalOption } from './../../../../shared/models/models';
import { IstanzaConst } from './../../constants/istanza-const';
import { LocalizzazioneService } from './../../services/localizzazione/localizzazione.service';
import { IstanzaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';

@Component({
  selector: 'app-dichiarazioni',
  templateUrl: './dichiarazioni.component.html',
  styleUrls: ['./dichiarazioni.component.scss']
})
export class DichiarazioniComponent extends AbstractTab implements OnInit, OnDestroy,OnChanges
{
  @Input() files: BasicFile[];
  @Input() titolaritaInQualitaDiOptions: SelectOption[];
  @Input() disclaimer: any[];

  @Output() onUpload: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDownload: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDelete: EventEmitter<any> = new EventEmitter<any>();

  public formloaded=false;
  const=CONST;
  unsubscribe$ = new Subject<void>();
  dichiarazioni: FormGroup;
  types: DocumentType[] = [
    {
      type: "DICHIARAZIONI_ASSENSO",
      label: "Dichiarazione d'assenso",
      multiple: false,
      required: true,
      accept: CONST.mimeTypeForScansioni,
      maxSize: null,
    }
  ];

  public art134opt: SelectItem = IstanzaConst.art134;
  public art136opt: HierarchicalOption = IstanzaConst.art136;
  public art142opt: HierarchicalOption = IstanzaConst.art142;
  public disclaimerInner: any[];

  constructor(private fb: FormBuilder,
              private httpDichiarazioniSettingsService: HttpDichiarazioniSettingsService,
              private httpDominioService: HttpDominioService,
              private loc: LocalizzazioneService,
              private istanzaFormService:IstanzaFormService) { super(); }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }


  remap142e136() {
    if(!this.dichiarazioni || !this.dichiarazioni.controls || !this.dichiarazioni.controls.art142)
      return;
    if (this.dichiarazioni.controls.art142.value) {
      let value = this.fascicolo.istanza.dichiarazioni.art142.map(m => { return { value: m, text: null } });
      this.dichiarazioni.controls.art142.setValue(value);
      //this.dichiarazioni.controls.art142.setValue(value.filter(f => f.value != '142'));
    }
    if (this.dichiarazioni.controls.art136.value)
      this.dichiarazioni.controls.art136.setValue(this.fascicolo.istanza.dichiarazioni.art136.map(m => { return { value: m, text: null } }));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mainForm && this.fascicolo && changes && changes['fascicolo']) {
      this.remap142e136();
    }

  }

  private ordineAsIs142(codice:string):number{
    let order={
      TERRITORI_COSTIERI:1,
      TERRITORI_LAGHI:2,
      ACQUE_PUBBLICHE:3,
      PARCHI_E_RISERVE:4,
      BOSCHI:5,
      ZONE_USI_CIVICI:6,
      ZONE_UMIDE_RAMSAR:7,
      ZONE_INT_ARCH:8
    };
    if(order[codice]){
      return order[codice];
    }
    return 9999;
  }

  ngOnInit() 
  {
    this.disclaimerInner=this.disclaimer;
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
              {
                array[index].disabled = true;
                array[index].tooltip='DICHIARAZIONI_TAB.TOOLTIP2';  
              }
              //le ordino come sull'AS-IS
              item.orderIndex=this.ordineAsIs142(item.value);
          });
          this.buildForm();
          this.watchChanges();
          ViewMapper.mapObjectToForm(this.dichiarazioni, this.fascicolo.istanza.dichiarazioni);
          this.remap142e136();
          this.checkInitialState();
          this.startWatchLocalizzazione();
          this.istanzaFormService.dichiarazioniFormComplete$.next(true);
          this.formloaded=true;
        }
      });
    }
    else
    {
      this.buildForm();
      this.watchChanges();
      ViewMapper.mapObjectToForm(this.dichiarazioni, this.fascicolo.istanza.dichiarazioni);
      this.checkInitialState();
      this.istanzaFormService.dichiarazioniFormComplete$.next(true);
      this.formloaded=true;
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
      art134: [false],
      //altreOpzioni: [[],this.tutteSelezionateValidator(this.disclaimer)],//devono essere tutte obbligatorie
      //altreOpzioni: [[], [Validators.required, Validators.minLength(this.disclaimer.length)]],
      altreOpzioni: [[],this.tutteSelezionateValidator],//devono essere tutte obbligatorie 
    });
    /*if (this.fascicolo.tipoProcedimento === '2')
    {
      this.dichiarazioni.get('inCasoDiVariante').valueChanges.subscribe(
        val =>
        {
          const fg: FormGroup = this.dichiarazioni as FormGroup;
          if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA') return;
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
    }*/
    if(this.fascicolo.attivitaDaEspletare==='COMPILA_DOMANDA'){
      this.dichiarazioni.get('diAvereTitoloRappSpec').disable();
      this.dichiarazioni.get('diAvereTitoloAltroSpec').disable();
      this.dichiarazioni.get('titolarita').valueChanges.subscribe(el => this.gestisciSpecifica(el));
    }
    this.mainForm.addControl('dichiarazioni', this.dichiarazioni);
  }

  gestisciSpecifica(nuovoValore: number)
  {
    if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA') return;
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
    if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA') return;

    this.dichiarazioni.get('titolaritaEsclusivaIntervento').valueChanges.subscribe(val =>
    {
      if (val === 'S')
        this.mainForm.get('altriTitolari').disable();
      else
      {
        this.mainForm.get('altriTitolari').enable();
      }
        
    });
  }

  checkInitialState()
  {
    if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA') return;
    if (this.dichiarazioni.get('titolaritaEsclusivaIntervento').value === 'S')
    {
      this.mainForm.get('altriTitolari').disable();
    }
    this.gestisciSpecifica(this.dichiarazioni.get('titolarita').value);
    //this.spegniFiglie(this.dichiarazioni.get('interventaNecesitaArt146').value);
    this.attivaVariante(this.dichiarazioni.get('inCasoDiVariante').value);
    this.aggiornaCheckBox142();
    this.art136opt.options[0].disabled = true;//sempre disabled la madre...
  }

  private aggiornaCheckBox142(){
    //se in localizzazione hanno check-ato BP-parchi e riserve, accendo la checkbox
    let hasParchi:boolean=false;
    let hasPaesaggioRurale:boolean=false;
    let fmLoc=this.mainForm.get('localizzazione') as FormGroup;
    if(!fmLoc) return;
    let fArrComuni=fmLoc.get('localizzazioneComuni') as FormArray;
    if(!fArrComuni) return;
    let comuniFormRawValue=fArrComuni.getRawValue();
    if(comuniFormRawValue.length>0){
      comuniFormRawValue.forEach(comuneIntervento=>{
          if(comuneIntervento.ulterioriInformazioni && 
            comuneIntervento.ulterioriInformazioni.bpParchiEReserve &&
            comuneIntervento.ulterioriInformazioni.bpParchiEReserve.length>0){
              hasParchi=true;
            }
          if(comuneIntervento.ulterioriInformazioni && 
              comuneIntervento.ulterioriInformazioni.ucpPaesaggioRurale &&
              comuneIntervento.ulterioriInformazioni.ucpPaesaggioRurale.length>0){
                hasPaesaggioRurale=true;
              }
        });
      }
       let oldValue=this.dichiarazioni.get('art142').value as ConfigOptionValue[];
        if(!oldValue){
          oldValue=[];
        }
        let idx=oldValue.findIndex(el=>el.value=='PARCHI_E_RISERVE')
        if(hasParchi && idx<0){
            oldValue.push({value:'PARCHI_E_RISERVE',text:null});
            this.dichiarazioni.get('art142').setValue(oldValue);
            this.dichiarazioni.get('art142').updateValueAndValidity();
        }else if(!hasParchi && idx>=0){
          //rimuovo
          oldValue.splice(idx,1);
          if(oldValue.length==1 && oldValue.some(el=>el.value=='142')){
            oldValue.splice(0,1);
          }
          this.dichiarazioni.get('art142').setValue(oldValue);
          this.dichiarazioni.get('art142').updateValueAndValidity();
        }
  }

  private startWatchLocalizzazione(): void
  {
    if(this.fascicolo.attivitaDaEspletare!=='COMPILA_DOMANDA') return;
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
        if (next.bpParchiRiserve === false){
          this.art142opt.options[0].disabled = false;
        }
        if (next.bpImmobileAreeInteresse === true)
        {
          let tmp = this.dichiarazioni.controls.art136;
          if (!tmp.value || !tmp.value.map(m => m.value).includes('136'))
            tmp.setValue([{ value: '136', text: null }]);
          this.art136opt.options[0].disabled = true;
        }
        if (next.bpImmobileAreeInteresse === false){
          let tmp = this.dichiarazioni.controls.art136;
          tmp.setValue(null); //ripulisco tutto...
        }
      }
      this.aggiornaCheckBox142();
    });
  }

  public uploadFile(event: any): void { 
    //wrappo l'evento cosi' come se lo aspetta... event.files[0]
    this.onUpload.emit({files:[event['file']]}); 
  }
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
      this.dichiarazioni.get("numero").enable();
      this.dichiarazioni.get("da").enable();
      this.dichiarazioni.get("inData").enable();
    }
    else {
      this.dichiarazioni.get("numero").clearValidators();
      this.dichiarazioni.get("numero").setValue(null);
      this.dichiarazioni.get("numero").updateValueAndValidity();
      this.dichiarazioni.get("numero").disable();
      this.dichiarazioni.get("da").clearValidators();
      this.dichiarazioni.get("da").setValue(null);
      this.dichiarazioni.get("da").updateValueAndValidity();
      this.dichiarazioni.get("da").disable();
      this.dichiarazioni.get("inData").clearValidators();
      this.dichiarazioni.get("inData").setValue(null);
      this.dichiarazioni.get("inData").updateValueAndValidity();
      this.dichiarazioni.get("inData").disable();
    }
  }

  private tutteSelezionateValidator:ValidatorFn=
      (control: AbstractControl): ValidationErrors | null =>{
      const checked = control.value;
      let numChecked=0;
      if(checked && Array.isArray(checked) && checked.length>0){
        for(let i=0;i<checked.length;i++){
          this.disclaimer.findIndex(el=>el.value==checked[i])>=0?numChecked++:null;
        }
        if(numChecked>=this.disclaimer.length){
          return null;
        }
      }
      return  { 'notAllChecked': true };
    }
    
  

  

}