import { EnteSummary } from 'src/app/features/admin-utils/admin-module/components/censimento-organizzazione/conf/conf-organizzazione';
import { LoadingService } from './../../../../../../services/loading.service';
import { CONST } from './../../../../../../../shared/constants';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { copyOf } from 'src/app/components/functions/genericFunctions';
import { TableHeader } from 'src/shared/models/table-header';
import { ConstConfOrganizzazioni } from './../conf/conf-organizzazione-const';
import { SelectItem } from 'primeng/primeng';
import { OrganizzazioneExtendedBean, TipoOrganizzazione, OrganizzazioneAbilitazioneBean, TipoOrganizzazioneSpecifica } from './../conf/conf-organizzazione';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { requiredDependsOn, validDate } from 'src/app/components/validators/customValidator';
import { SimpleFormControlData } from 'src/app/components/model/model';

@Component({
  selector: 'app-form-creazione-organizzazione',
  templateUrl: './form-creazione-organizzazione.component.html',
  styleUrls: ['./form-creazione-organizzazione.component.scss']
})
export class FormCreazioneOrganizzazioneComponent implements OnInit
{
  @Input('readonly') disable: boolean = false;
  @Input('init') set init(o: OrganizzazioneExtendedBean)
  { 
    this.isModifica = (o != null);
    if(o)
    {
      this.buildForm(); 
      this.tipoOrganizzazioneChange(o.tipoOrganizzazione, () => 
      {
        Object.keys(this.form.controls).forEach(k => this.form.controls[k].enable());
        this.form.patchValue({...o});
        if(o.tipoOrganizzazione == "SBAP")
        {
          this.tipoOrgOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSbap);
          this.tipoOrgSOptions = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecSbap);
        }
        else
        {
          this.tipoOrgOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniEtiEd); 
          if(o.tipoOrganizzazioneSpecifica.toLocaleLowerCase() == 'regione')
          {
            this.tipoOrgSOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecEtiEdREG); 
            this.isRegione = true;
            this.recordRegione = {label: 'Regione Puglia', value: o.enteId};
          }
          else
          {
            this.tipoOrgSOptions = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecEtiEd);
          }
        }
        this.valutaForm();
        console.log("FORM: ", this.form.getRawValue());
      });
    }
  }

  
  @Output('complete') complete: EventEmitter<OrganizzazioneExtendedBean> = new EventEmitter();
  @Output('close') close: EventEmitter<void> = new EventEmitter();

  public submitted: boolean = false;
  public form: FormGroup;

  public enti: Array<EnteSummary> = [];
  public isRegione: boolean = false;

  public tipoOrgOptions : Array<SelectItem> = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioni);
  public tipoOrgSOptions: Array<SelectItem> = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpec);
  public entiAssOptions : Array<SelectItem> = [];
  public entiOptions    : Array<SelectItem> = [];
  public columnsEnti    : Array<TableHeader> = ConstConfOrganizzazioni.headerEnteTable;
  public formConf       : Array<SimpleFormControlData> = copyOf(ConstConfOrganizzazioni.formConf);

  public mindate: Date = null;
  public maxdate: Date = new Date();

  public openAssociaEnte: boolean = false;
  public initEnte: {idEnte: number, dataTermine: Date, index: number} = null;
  public errorMessage: string = null;

  public entiList: Array<SelectItem> = [];
  public isModifica: boolean = false;
  private recordRegione: SelectItem = null;

  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;

  //oggetto utilizzato per l'alert
  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private formBuilder: FormBuilder,
              private loadingService: LoadingService,
              private adminService: AdminService) { }
  
  public ngOnInit(): void 
  { 
    this.buildForm(); 
    this.valutaForm();
    this.popolaDropdownEntiAssociazione();
  }

  private buildForm(): void
  {
    //se non ricreo il form le dropdown non sentono il patch dei valori al cambiamento del'Input init
    //if(!this.form)
   // {
      this.form = this.formBuilder.group({
        id                         : [null],
        enteId                     : [null, Validators.required],
        tipoOrganizzazione         : [null, Validators.required],
        tipoOrganizzazioneSpecifica: [null, Validators.maxLength(100)],
        codiceCivilia              : [null, [Validators.maxLength(20)]],
        riferimentoOrganizzazione  : [null],
        dataCreazione              : [null],
        dataTermine                : [null],
        denominazione              : [null, [Validators.required, Validators.maxLength(200)]],
        enabled                    : [null],
        enti                       : [null, Validators.required],
        destinatariDefault         : [null]
      });
      this.form.controls.dataCreazione.setValidators([Validators.required, validDate(null, this.form.controls.dataTermine)]);
      this.form.controls.dataTermine.setValidators([Validators.required, validDate(this.form.controls.datadataCreazioneTermine)]);
      let tipoOrganizzazioneControl=this.form.controls.tipoOrganizzazione;
      this.form.controls.codiceCivilia.setValidators(requiredDependsOn(tipoOrganizzazioneControl, "ED"));

      /* if(this.isModifica) 
      {
        this.form.controls.enteId.disable();
        this.form.controls.tipoOrganizzazione.disable();
        this.form.controls.tipoOrganizzazioneSpecifica.disable();
      } */
    //}
  }

  public done(): void
  {
    this.submitted = true;
    if(/* this.validEnte() &&  */this.form.valid)
    {
      this.submitted = false;
      let o: OrganizzazioneExtendedBean = this.form.getRawValue();
      this.complete.emit(o);
    }
  }

  public indietro(): void
  {
    this.close.emit();
  }

  public evaluateConstraints(date: Date|string, calledBy: string): void
  {
    let d = date ? new Date(date) : null;
    switch(calledBy)
    {
      case 'dataCreazione': this.mindate = d; break;
      case 'dataTermine': this.maxdate = d; break;
      default: console.debug("Nessuna operazione prevista per " + calledBy);        
    }
    if(calledBy == 'dataTermine') this.formConf[1].maxdate = d;
  }

  public openEdit(init?: any, index?: number): void
  {
    this.initEnte = init;
    if(init) this.initEnte.index = index;
    this.formConf[0].readonly = init != null;
    this.openAssociaEnte = true;
    this.errorMessage = null;
  }

  public associaEnte(event: any): void
  {
    let array = [];
    if(this.form.controls.enti.value)
      array = this.form.controls.enti.value;
    if(this.initEnte != null && this.initEnte.index != null)
    {
      array[this.initEnte.index] = event;
      this.form.controls.enti.setValue(array);
      this.openAssociaEnte = false;
    }
    else if(!array.some(v => v.idEnte == event.idEnte))
    {
      array = [...array, event];
      this.form.controls.enti.setValue(array);
      this.openAssociaEnte = false;
    } 
    else
    {
      this.errorMessage = "Errore, comune già associato all'organizzazione";
    }
  }

  public popolaDropdownEntiAssociazione(tipoOrg?: TipoOrganizzazione): void
  {
    this.loadingService.emitLoading(false);
    this.adminService.dropdownOrganizzazioneEntiAssociazione(tipoOrg).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
      {
        let dropdown = response.payload.map(m => {return {label: m.denominazione, value: m.idEnte}});
        this.enti = response.payload;
        this.entiAssOptions = dropdown;
        if(this.isRegione)
          this.entiAssOptions.unshift(this.recordRegione);
      }
      this.loadingService.emitLoading(false);
    });
  }

  public tipoOrganizzazioneChange(tipoOrg: TipoOrganizzazione, callback?: () => void): void
  {
    if(tipoOrg != null)
    {
      this.loadingService.emitLoading(true);
      this.adminService.dropdownOrganizzazioneEntiTerritori(tipoOrg).subscribe(response =>
      {
        if(response.codiceEsito == CONST.OK)
        {
          let dropdown = response.payload.map(m => {return {label: m.denominazione, value: m.idEnte}});
          this.entiList = dropdown;
          this.formConf[0].options = dropdown;
          this.valutaForm();
          if(callback) callback();
        }
        this.loadingService.emitLoading(false);
      });
    }
  }

  public deleteTerritorio(row: EnteSummary, index: number): void
  {
    if(row && row.idOrganizzazioneCompetenze == null)
    {
      let array: Array<any> = this.form.controls.enti.value;
      array.splice(index, 1);
      this.form.controls.enti.setValue(array);
    }
  }

  public chooseEnte(val: any): void
  {
    if(val)
    {
      let o: EnteSummary = this.enti.filter(p => p.idEnte == val)[0];
      if(o.tipoEnte.toLowerCase() == "soprintendenza")
      {
        this.tipoOrgSOptions = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecSbap);
        this.tipoOrgOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSbap);
        this.form.patchValue({
          tipoOrganizzazione         : 'SBAP',
          tipoOrganizzazioneSpecifica: 'Soprintendenza'
        });
        this.tipoOrganizzazioneChange('SBAP');
      }
      else if(o.tipoEnte.toLowerCase() == "ente_parco") //se sceglie un ente parco puo' solo essere un ETI
      {
        this.tipoOrgSOptions = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecParco);
        this.tipoOrgOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniEti);
        this.form.patchValue({
          tipoOrganizzazione         : 'ETI',
          tipoOrganizzazioneSpecifica: 'Unione'
        });
        this.tipoOrganizzazioneChange('ETI');
      }
      else
      {
        this.tipoOrgSOptions = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecEtiEd);
        this.tipoOrgOptions  = copyOf(ConstConfOrganizzazioni.selectTipiOrganizzazioniEtiEd);
        this.form.patchValue({
          tipoOrganizzazione         : null,
          tipoOrganizzazioneSpecifica: null
        });
      }
      this.form.patchValue({ denominazione: o.denominazione });
      this.valutaForm();
    }
  }

  callbackAlert(event: any) 
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso) 
    {
      this.alertData.display = false;
    } 
    else if (event.isConfirm) 
    {
      if (event.extraData && event.extraData.operazione) 
      {
        switch (event.extraData.operazione) 
        {
          case 'TEST':
            //TODO
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  /* private validEnte(): boolean
  {
    let violations: Array<string> = [];
    let d: Date = this.form.controls.dataTermine.value;
    if(d != null) d = new Date(d);
    let array: Array<EnteSummary> = [];
    if(this.form.controls.enti.value)
      array = this.form.controls.enti.value;
    for(let e of array)
    {
      let tmpD = e.dataTermine ? new Date(e.dataTermine) : null;
      if(tmpD && e.dataTermine.getTime() > d.getTime())
      {
        let denominazione = this.enti.filter(p => p.idEnte === e.idEnte)[0].denominazione;
        violations.push(denominazione);
      }
    }
    if(violations.length > 0)
    {
      let msg = "";
      violations.forEach(v => msg = msg+="<li>" + v + "</li>");
      this.alertData =
      {
        display: true,
        title: "Attenzione",
        content: "Data scadenza competenza non valida per i territori: " + msg,
        typ: "danger",
        extraData: null,
        isConfirm: false,
      };
    }
    return violations.length == 0;
  } */

  private valutaForm(): void
  {
    Object.keys(this.form.controls).forEach(k => this.form.controls[k].enable());
    if(this.isRegione)
      this.form.disable();
    else
    {
      if(this.isModifica) 
      {
        this.form.controls.enteId.disable();
        this.form.controls.tipoOrganizzazione.disable();
        this.form.controls.tipoOrganizzazioneSpecifica.disable();
      }
      else
      {
        if(!this.form.controls.enteId.value)
        {
          this.form.controls.tipoOrganizzazione.disable();
          this.form.controls.tipoOrganizzazioneSpecifica.disable();
          this.form.controls.codiceCivilia.disable();
          this.form.controls.riferimentoOrganizzazione.disable();
          this.form.controls.dataCreazione.disable();
          this.form.controls.dataTermine.disable();
          this.form.controls.denominazione.disable();
          this.form.controls.enabled.disable();
          this.form.controls.enti.disable();
        }
        else 
        {
          if(!this.form.controls.tipoOrganizzazione.value)
          {
            this.form.controls.tipoOrganizzazioneSpecifica.disable();
            this.form.controls.enti.disable();
          }
        }
      }
    }
  }

}
