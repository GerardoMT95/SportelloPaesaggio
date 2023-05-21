import { CONST } from './../../../../../../shared/constants';
import { takeUntil } from 'rxjs/operators';
import { Subject, combineLatest } from 'rxjs';
import { AdminService } from './../../../../../services/admin-service/admin.service';
import { PannelloAmministratore } from './../../../../../components/model/model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { formGroupNameProvider } from '@angular/forms/src/directives/reactive_directives/form_group_name';
import { SelectItem } from 'primeng/components/common/selectitem';
import { FormConfigConfigurazioni } from './form-config-configurazioni';
import { PlanFormField } from 'src/shared/models/plan-form.model';
import { FascicoloFormConfig } from 'src/app/components/form-fascicolo/form-plan-config';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { emit } from 'esri/identity/IdentityManager';
import { TipologicaDTO } from 'src/app/components/model/entity/localizzazione.models';


/* export interface ConfigData
{
  //campionamento
  isAttivoCampionamento: boolean;
  campionamentoIntervalloGG: number;
  campionamentoStartSuccessivo: string;
  campionamentoPercentuale: number;
  //comunicazioni
  isAttivoComunicazioni: boolean;
  listaAbilitatiComunicazioni: string[];
  isAttivoOsservazioni: boolean;
  listaAbilitatiOsservazioni: string[];
  isAttivoUltDocum: boolean;
  listaAbilitatiUltDocum: string[];
} */

@Component({
  selector: 'app-admin-configurazioni',
  templateUrl: './admin-configurazioni.component.html',
  styleUrls: ['./admin-configurazioni.component.css']
})
export class AdminConfigurazioniComponent implements OnInit, OnDestroy
{
  public onIcon: string = "fa fa-check";
  public formConfigurazione: FormGroup;
  public formFields: PlanFormField[];
  public data: PannelloAmministratore;
  public groups: SelectItem[] = [];
  public dettCampionamento: boolean;
  public dettComunicazioni: boolean;
  public dettOsservazioni: boolean;
  public dettUltDoc: boolean;
  private unsubscribe: Subject<void> = new Subject<void>();

  campSuccessivo = 
  [
    { label: "a partire dall'ultimo campionamento", value: "PREDEFINITO" },
    { label: "a partire dal primo inserimento successivo", value: "TRASMISSIONE" },
  ]

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
              private router: Router,
              private translateService: TranslateService,
              private service: AdminService){ }

  ngOnInit()
  {
    this.formFields = FormConfigConfigurazioni.formConfig();
    this.prepopulateInfo();
  }

  ngOnDestroy()
  {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  private prepopulateInfo(): void
  {
    let _self = this;
    this.loadingService.emitLoading(true);
    combineLatest(this.service.listaGruppi(), this.service.getPanelInfo())
                  .pipe(takeUntil(this.unsubscribe))
                  .subscribe(([lista, panel]) =>
    {
      if(lista.codiceEsito === CONST.OK && panel.codiceEsito === CONST.OK)
      {
        let formConfigurer: any;
        _self.groups = lista.payload.map(m => { return {label: m.label, value: m.codice}});
        _self.data = panel.payload;
        formConfigurer = _self.data;
        formConfigurer.comunicazioni = _self.mapDataForForm(_self.groups, _self.data.comunicazioni);
        formConfigurer.osservazioni = _self.mapDataForForm(_self.groups, _self.data.osservazioni);
        formConfigurer.documentazione = _self.mapDataForForm(_self.groups, _self.data.documentazione);
        _self.formConfigurazione = _self.formBuilder.group({ ...FascicoloFormConfig.prepareFormBuilder(_self.formFields, formConfigurer) });
        _self.formConfigurazione.patchValue({comunicazioniAttivo : _self.existsAndNotEmpty(_self.formConfigurazione.get("comunicazioni").value),
                                             osservazioniAttivo  : _self.existsAndNotEmpty(_self.formConfigurazione.get("osservazioni").value),
                                             documentazioneAttivo: _self.existsAndNotEmpty(_self.formConfigurazione.get("documentazione").value)});
        _self.evaluateDetails();
        _self.loadingService.emitLoading(false);
      }
    });
  }

  private resetFormData(): void
  {
    this.formConfigurazione.patchValue({ ...this.data,
                                         comunicazioniAttivo : this.existsAndNotEmpty(this.data.comunicazioni),
                                         osservazioniAttivo  : this.existsAndNotEmpty(this.data.osservazioni),
                                         documentazioneAttivo: this.existsAndNotEmpty(this.data.documentazione)});
  }

  public evaluateDetails(): void
  {
    if(this.formConfigurazione)
    {
      this.dettCampionamento = this.formConfigurazione.get("campionamentoAttivo").value;
      this.dettComunicazioni = this.formConfigurazione.get("comunicazioniAttivo").value;
      this.dettOsservazioni = this.formConfigurazione.get("osservazioniAttivo").value;
      this.dettUltDoc = this.formConfigurazione.get("documentazioneAttivo").value;
    }
  }

  private mapDataForForm(list: SelectItem[], values: string[]): SelectItem[]
  {
    let toPutIn: SelectItem[] = [];
    if(list && values)
      toPutIn = list.filter(f => values.includes(f.value));
    return toPutIn;
  }

  /* caricaFormEdati()
  {
    this.loadingService.emitLoading(true);
    this.autPaeSvc.getConfigData()
      .then(res =>
      {
        this.data = res;
        this.formConfigurazione = this.formBuilder.group({
          ...FascicoloFormConfig.prepareFormBuilder(this.formFields, this.data)
        });
        this.attivaDettagliCampionamento();
        this.attivaDettagliOsservazioni();
        this.attivaDettagliComunicazioni();
        this.attivaDettagliUltDoc();
        this.loadingService.emitLoading(false);
      }
      )
      .catch(() =>
      {
        this.loadingService.emitLoading(false);
      });
  } */

  doSalva()
  {
    this.loadingService.emitLoading(true);
    let config: PannelloAmministratore = this.formConfigurazione.getRawValue();
    config.comunicazioni = (<any[]>config.comunicazioni).map(m => m.value);
    config.osservazioni = (<any[]>config.osservazioni).map(m => m.value);
    config.documentazione = (<any[]>config.documentazione).map(m => m.value);
    this.service.saveAdminPanel(config).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK && response.payload)
      {
        this.data = config;
        this.alertData.content = "Salvataggio avvenuto correttamente";
        this.alertData.title = "Successo";
        this.alertData.typ = "success";
        this.alertData.display = true;
      }
    });
    /* this.autPaeSvc.saveConfigData(this.formConfigurazione.getRawValue()).then(res =>
    {
      this.loadingService.emitLoading(false);
      if (res.code == "OK")
      {
        this.alertData.isConfirm = true;
        this.alertData.typ = "info";
        this.alertData.title = "";
        this.alertData.content = this.translateService.instant('generic.operazioneOk');
        this.alertData.extraData = { operazione: 'back' };
        this.alertData.display = true;
      } else
      {
        this.alertData.isConfirm = false;
        this.alertData.typ = "danger";
        this.alertData.title = "errore";
        this.alertData.content = res.messageCode || this.translateService.instant('errro.GENERIC_ERROR');
        this.alertData.extraData = { operazione: 'back' };
        this.alertData.display = true;
      }
    }
    ); */
  }

  /* valida()
  {
    this.formFields.forEach(item =>
    {
      if (item.required)
      {
        this.formConfigurazione.controls[item.field].setValidators(Validators.required);
      }
    });
    if (this.dettCampionamento)
    {
      this.formConfigurazione.controls['campionamentoIntervalloGG'].setValidators(Validators.required);
      this.formConfigurazione.controls['campionamentoStartSuccessivo'].setValidators(Validators.required);
      this.formConfigurazione.controls['campionamentoPercentuale'].setValidators(Validators.required);
    } else
    {
      this.formConfigurazione.controls['campionamentoIntervalloGG'].setValidators([]);
      this.formConfigurazione.controls['campionamentoStartSuccessivo'].setValidators([]);
      this.formConfigurazione.controls['campionamentoPercentuale'].setValidators([]);
    }
    if (this.dettComunicazioni)
    {
      this.formConfigurazione.controls['listaAbilitatiComunicazioni'].setValidators(Validators.required);
    } else
    {
      this.formConfigurazione.controls['listaAbilitatiComunicazioni'].setValidators([]);
    }
    if (this.dettOsservazioni)
    {
      this.formConfigurazione.controls['listaAbilitatiOsservazioni'].setValidators(Validators.required);
    } else
    {
      this.formConfigurazione.controls['listaAbilitatiOsservazioni'].setValidators([]);
    }
    if (this.dettUltDoc)
    {
      this.formConfigurazione.controls['listaAbilitatiUltDocum'].setValidators(Validators.required);
    } else
    {
      this.formConfigurazione.controls['listaAbilitatiUltDocum'].setValidators([]);
    }
    this.formFields.forEach(item =>
    {
      this.formConfigurazione.controls[item.field].updateValueAndValidity();
    });
    this.formConfigurazione.updateValueAndValidity();
  } */


  /* salva()
  {
    this.valida();
    if (this.formConfigurazione.valid)
    {
      this.doSalva();
    } else
    {
      this.alertData.isConfirm = false;
      this.alertData.typ = "danger";
      this.alertData.title = "";
      this.alertData.content = this.translateService.instant('CAMPI_INVALIDI');
      this.alertData.display = true;
    }

  } */

  reset()
  {
    if (this.formConfigurazione.touched)
    {
      this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('fascicolo.abbandonaModifiche');
      this.alertData.content = this.translateService.instant('fascicolo.sicuroAbbandonoModifiche');
      this.alertData.extraData = { operazione: 'resetForm' };
      this.alertData.display = true;
    }
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
    {
      this.alertData.display = false;
    } else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'back':
            this.router.navigate(['/']);
            break;
          case 'resetForm':
            this.resetFormData();
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
    this.loadingService.emitLoading(false);
  }

  private existsAndNotEmpty(array: any[]): boolean { return array && array.length > 0; }


  /* attivaDettagliCampionamento()
  {
    if (this.formConfigurazione.get('isAttivoCampionamento').value)
    {
      this.dettCampionamento = true;
    } else
    {
      this.dettCampionamento = false;
    }
  }
  attivaDettagliOsservazioni()
  {
    if (this.formConfigurazione.get('isAttivoOsservazioni').value)
    {
      this.dettOsservazioni = true;
    } else
    {
      this.dettOsservazioni = false;
    }
  }

  attivaDettagliComunicazioni()
  {
    if (this.formConfigurazione.get('isAttivoComunicazioni').value)
    {
      this.dettComunicazioni = true;
    } else
    {
      this.dettComunicazioni = false;
    }
  }
  attivaDettagliUltDoc()
  {
    if (this.formConfigurazione.get('isAttivoUltDocum').value)
    {
      this.dettUltDoc = true;
    } else
    {
      this.dettUltDoc = false;
    }
  } */

}
