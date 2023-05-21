import { UserService } from './../../../../../shared/services/user.service';
import { Router } from '@angular/router';
import { ButtonType, DialogButtons, DialogType } from './../../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../../shared/constants';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { updateAllFormValidity } from 'src/app/core/functions/generic.utils';
import { requiredDependsOn } from 'src/app/shared/validators/customValidator';
import { DataConf } from '../../configurations/data-conf';
import { CustomDialogService } from './../../../../../core/services/dialog.service';
import { AdminFunctionsService } from './../../../../../shared/services/admin/admin-functions.service';
import { LoadingService } from './../../../../../shared/services/loading.service';
import { Configurazione, SimpleFormControlData } from './../../models/ente-admin.models';
import { GroupType } from 'src/app/shared/models/models';
import { Button } from 'primeng/primeng';

@Component({
  selector: 'app-servizi-integrazione-page',
  templateUrl: './servizi-integrazione-page.component.html',
  styleUrls: ['./servizi-integrazione-page.component.scss']
})
export class ServiziIntegrazionePageComponent implements OnInit
{
  public configurazione: Configurazione;
  public form: FormGroup;
  public validation: boolean = false;
  public types = CONST.mappingTipiProcedimento;
  public configurations: {[key: string]: Array<SimpleFormControlData>} = DataConf.formInputs;

  constructor(private service: AdminFunctionsService,
    private loading: LoadingService,
    private dialog: CustomDialogService,
    private fb: FormBuilder,
    private router: Router,
    private user: UserService) 
  {

  }

  ngOnInit()
  {
    this.form = this.buildForm();
    this.findConfiguration();
    if (this.isRegione)
      this.form.patchValue({ sistemaProtocollazione: true });
      
  }

  private findConfiguration(): void
  {
    this.loading.emitLoading(true);
    this.service.findServiziIntegrazione().subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.configurazione = response.payload;
        this.form.patchValue(this.configurazione);
        this.form.patchValue({ pagamentoCommissione: this.configurazione.pagamentoCommissione+"" });
      }
      this.loading.emitLoading(false);
    });
  }

  private buildForm(): FormGroup
  {
    let form = this.fb.group({
      sistemaProtocollazione: [false, Validators.required],
      protocollazioneEndpoint: [null],
      protocollazioneAdministration: [null],
      protocollazioneAoo: [null],
      protocollazioneRegister: [null],
      protocollazioneUser: [null],
      protocollazionePassword: [null],
      protocollazioneHashAlgorithm: [null],
      protocollazioneIndirizzoPostale: [null],
      protocollazioneIndirizzoTelematico: [null],
      protocollazioneTipoIndirizzoTelematico: [null],
      protocollazioneAooDenominazione: [null],
      protocollazioneDenominazione: [null],
      protocollazioneNumeroRegistrazione: [null],
      protocollazioneDataRegistrazione: [null],
      sistemaPagamento: [false, Validators.required],
      pagamentoCodEnte: [null],
      pagamentoTipologia: [null],
      pagamentoTipoRiscossione: [null],
      pagamentoEndPoint: [null],
      pagamentoPassword: [null],
      pagamentoCommissione: [null],
      pagamentoRegexIud: [null],
      bilancio:[null],
      sistemaPec: [false, Validators.required],
      pecIndirizzo: [null],
      pecNome: [null],
      pecUsername: [null],
      pecPassword: [null],
      pecHostIn: [null],
      pecHostOut: [null],
      pecProtocolloIn: [false],
      pecTipoProtocolloOut: [null],
      pecTipoProtocolloIn: [null],
      pecSslIn: [false],
      pecSslOut: [false],
      pecTlsOut: [false],
      pecPortaSslIn: [null],
      pecPortaSslOut: [null],
      pecPortaTlsOut: [null],
      pecStartTlsIn: [false],
      pecStartTlsOut: [false],
      pecAutenticazioneIn: [false],
      pecAutenticazioneOut: [false],
      pubblicazioneStatoPratica: [null],
      tipoPraticaSeduta: [null],
      canCreateConferenza:[false],
      indirizziMailDefault: [null],
      mailDefault:[null]
    });
    //insesirisco validazioni
    Object.keys(DataConf.formInputs).forEach(key =>
    {
      for (let item of DataConf.formInputs[key])
      {
        if (item.type != 'category')
        {
          if(item.required){
            let validators: any[] = item.validators && item.validators.length > 0 ? item.validators : [];
            validators.push(requiredDependsOn(form.get(key), true, item.type === "dropdown" ? "value" : null));
            form.get(item.formControlName).setValidators(validators);
          }
          if(item.validators && item.validators.length > 0){
            let validators: any[] = item.validators && item.validators.length > 0 ? item.validators : [];
            form.get(item.formControlName).setValidators(validators);
          }
        }
        else
        {
          for (let subItem of item.children)
          {
            if (subItem.required)
            {
              let validators: any[] = subItem.validators && subItem.validators.length > 0 ? subItem.validators : [];
              validators.push(requiredDependsOn(form.get(key), true/* , subItem.type == "dropdown" ? "value" : null */));
              form.get(subItem.formControlName).setValidators(validators);
            }
          }
        }
      }
    });
    return form;
  }

  private doSalva(){
    this.validation = false;
      //Se è regione settiamo di default il valore a false
      if(this.user.groupType !== GroupType.Regione) {
        this.form.patchValue({ canCreateConferenza: false });
      }
      let configuration: Configurazione = this.normalizeObj(this.form.getRawValue());
      this.loading.emitLoading(true);
      this.service.saveServiziIntegrazione(configuration).subscribe(response =>
      {
        if (response.codiceEsito === CONST.OK && response.payload)
        {
          this.configurazione = response.payload;
          let title = "Successo";
          let message = "Configurazione salvata correttamente";
          this.dialog.showDialog(true, message, title, DialogButtons.CHIUDI, (btn)=>{
            if(btn==ButtonType.CLOSE_BUTTON){
              this.findConfiguration();
            }
          }, DialogType.SUCCESS);
          
        }
        this.loading.emitLoading(false);
      });
  }

  public salva(): void
  {
    if(this.form.controls['indirizziMailDefault'].value) {
    var mailValid: boolean = true;
    var verificaMail : string[] = this.form.controls['indirizziMailDefault'].value.split(",");
    verificaMail.forEach(element => {
      let rgx = new RegExp(CONST.PATTERN_MAIL);
        if(!rgx.test(element) && mailValid) 
            mailValid = false;
    });
    if(!mailValid) {
      let titleError = "Errore";
      let messageError = "Formattazione non corretta! Controllare di non aver aggiunto spazi e che la mail sia valida";
      this.dialog.showDialog(true, messageError, titleError, DialogButtons.CHIUDI, null, DialogType.ERROR);
      return;
    }
    }
    this.validation = true;
    updateAllFormValidity(this.form);
    if (this.form.valid)
    {
      let titleConferma = "Conferma";
      let messageConferma = "Confermi la modifica delle configurazioni?";
      this.dialog.showDialog(true, messageConferma, titleConferma, DialogButtons.CONFERMA_CHIUDI, (btn)=>{
        if(btn==ButtonType.OK_BUTTON){
          this.doSalva();
        }
      }, DialogType.INFORMATION);
    }
    /* else
    {
      printFormErrors(this.mainForm, "mainForm");
    } */
  }

  public indietro(): void { this.router.navigate(['gestione-istanze']); }

  /**
   * Se la configurazione viene settata su "manuale" elimina tutti i settaggi
   * @param key 
   */
  public toggleButton(key: string): void
  {
    if (DataConf.formInputs[key])
    {
      let formKeys: string[] = DataConf.formInputs[key].filter(f => f.formControlName).map(m => m.formControlName);
      formKeys.forEach(key => this.form.get(key).setValue(null));
    }
  }

  private normalizeObj(obj: any): any 
  {
    Object.keys(obj).forEach(key =>
      obj[key] = obj[key] && obj[key].value ? obj[key].value : obj[key]);
    return obj;
  }
  public getChildren(key: string): SimpleFormControlData[] { return this.configurations[key]; }

  get pubblicazioneState(): string
  {
    let label: string;
    switch (this.form.get("pubblicazioneStatoPratica").value)
    {
      case true:
        label = "FUNZIONI_ADMIN.VISIBILE"
        break;
      case false:
        label = "FUNZIONI_ADMIN.NON-VISIBILE";
        break;
      default:
        label = "FUNZIONI_ADMIN.DEFAULT"
    }
    return label;
  }

  get isRegione(): boolean { return this.user.groupType === GroupType.Regione; }
  get isED(): boolean { return this.user.groupType === GroupType.EnteDelegato; }

}
