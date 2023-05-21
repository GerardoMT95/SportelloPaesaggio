import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ConfigurazioneProtocollo } from 'src/app/components/model/model';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ErrorService } from 'src/app/services/error.service';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { AuthComponent } from 'src/app/components/generic/AuthComponent';
import { CONST } from 'src/shared/constants';

@Component({
  selector: 'app-configurazione-protocollo',
  templateUrl: './configurazione-protocollo.component.html',
  styleUrls: ['./configurazione-protocollo.component.css']
})
export class ConfigurazioneProtocolloComponent extends AuthComponent {

  // form con i campi di configurazione scheduling
  public form: FormGroup;

  //DIALOG DI CONFERMA SALVATAGGIO
  public displayConfirm: boolean = false;
  public contentConfirm: string;
  public titleConfirm: string;
  public typConfirm: string = 'info';

  public CONST: CONST = CONST;
  public MIN_YEAR: number = CONST.MIN_YEAR;
  public MAX_YEAR: number = CONST.MAX_YEAR;
  public it: any = CONST.IT;
  //DIALOG DI CONFERMA RESET
  public displayReset: boolean = false;
  public contentReset: string;
  public titleReset: string;
  public typReset: string = 'info';

  //DIALOG DI ESITO SALVATAGGIO
  public displayEsito: boolean = false;
  public contentEsito: string;
  public titleEsito: string;
  public typEsito: string = 'success';

  //BEAN CON PARAMETRI DI CONFIGURAZIONE
  public configurazione: ConfigurazioneProtocollo;

  //EYE PASSWORD
  public tipo:string = 'password';
  public classEye:boolean = true;

  constructor(private router          : Router
             ,private lss             : LocalSessionServiceService
             ,private loadingService  : LoadingService
             ,private translateService: TranslateService
             ,private service         : AdminService
             ,private errorService    : ErrorService
             ,private fb              : FormBuilder
  ) {
    super(router, lss);
    this.buildForm();
    
  }

  ngOnInit() {
    this.getConfigurazione()
  }
  /**
   * Build form
   */
  private buildForm(): void {
    this.form = this.fb.group({algoritmoImpronta           :[""  , [Validators.required]]
	                            ,endpoint                    :[""  , [Validators.required]]
	                            ,amministrazione             :[""  , [Validators.required]]
	                            // ,mittente                    :[""  , [Validators.required]]
	                            ,aoo                         :[""  , [Validators.required]]
	                            ,registro                    :[""  , [Validators.required]]
	                            ,utente                      :[""  , [Validators.required]]
                              ,pwd                         :[""  , [Validators.required]]
                              ,numeroRegistrazione         :[""  , [Validators.required]]
                              ,tipoIndirizzoTelematico     :[""  , [Validators.required]]
                              ,valoreIndirizzoTelematico   :[""  , [Validators.required]]
                              ,indirizzoPostale            :[""  , [Validators.required]]
                              ,denominazioneAOO            :[""  , [Validators.required]]
                              ,dataRegistrazione           :[null, [Validators.required]]
                              // ,unitaOrganizzativa          :[""  , [Validators.required]]
                              ,amministrazioneDenominazione:[""  , [Validators.required]]
                              ,mittaoocodiceaoo:[null  ]
    })
  }

/**
   * Recupero parametri di configurazione scheduling
   */
  public getConfigurazione() {
    this.service.getCongifurazioneProtocollo().then(response => {
      if (response.codiceEsito == CONST.OK) {
        this.configurazione=response.payload;
        this.setValue();
        this.loadingService.emitLoading(false)
      } else {
        this.errorService.emitError(response.descrizioneEsito);
        this.loadingService.emitLoading(false)
      }
    }).catch(error => {
      this.errorService.emitError(error.statusText);
      this.loadingService.emitLoading(false);
    });
  }

 /**
 * set value from bean to form
 */
 private setValue(): void {  
    this.form.controls.algoritmoImpronta           .setValue(this.configurazione.algoritmoImpronta           );
    this.form.controls.endpoint                    .setValue(this.configurazione.endpoint                    );
    this.form.controls.amministrazione             .setValue(this.configurazione.amministrazione             );
    // this.form.controls.mittente                    .setValue(this.configurazione.mittente                    );
    this.form.controls.aoo                         .setValue(this.configurazione.aoo                         );
    this.form.controls.registro                    .setValue(this.configurazione.registro                    );
    this.form.controls.utente                      .setValue(this.configurazione.utente                      );
    this.form.controls.pwd                         .setValue(this.configurazione.pwd                         );
    this.form.controls.numeroRegistrazione         .setValue(this.configurazione.numeroRegistrazione         );
    this.form.controls.tipoIndirizzoTelematico     .setValue(this.configurazione.tipoIndirizzoTelematico     );
    this.form.controls.valoreIndirizzoTelematico   .setValue(this.configurazione.valoreIndirizzoTelematico   );
    this.form.controls.indirizzoPostale            .setValue(this.configurazione.indirizzoPostale            );
    this.form.controls.denominazioneAOO            .setValue(this.configurazione.denominazioneAOO            );
    // this.form.controls.unitaOrganizzativa          .setValue(this.configurazione.unitaOrganizzativa          );
    this.form.controls.amministrazioneDenominazione.setValue(this.configurazione.amministrazioneDenominazione);
    this.form.controls.dataRegistrazione           .setValue(new Date(this.configurazione.dataRegistrazione));
    this.form.controls.mittaoocodiceaoo           .setValue(this.configurazione.mittAooCodiceAoo);
  }

  /**
 * Open dialog confirm reset
 */
public resetForm() :void {

  this.displayReset=true;
  this.contentReset=this.translateService.instant('protocollo.reset.content');
  this.titleReset=this.translateService.instant('protocollo.reset.title');
}
  /**
   * Reset i valori della form ai valori di partenza
   */
  public callbackReset(event:any): void {

    this.displayReset=false;
    if(event.isConfirm)
      this.setValue();
  }

  /**
   * Display salva button
   */
  public confirmSave(): void {
    this.displayConfirm = true;
    this.contentConfirm = this.translateService.instant('protocollo.confirm.content');
    this.titleConfirm = this.translateService.instant('protocollo.confirm.title');
  }

  /**
   * Callback confirm save
   */
  public callbackConfirm(event: any): void {
    this.displayConfirm = false;
    if(event.isConfirm)
    this.salvaConfigurazione();
  }

  /**
   * Aggiornamento parametri di configurazione
   */
  public salvaConfigurazione() {
    this.loadingService.emitLoading(true);
    let newConfig : ConfigurazioneProtocollo={algoritmoImpronta           :this.form.controls.algoritmoImpronta           .value
                                             ,endpoint                    :this.form.controls.endpoint                    .value
                                             ,amministrazione             :this.form.controls.amministrazione             .value
                                            //  ,mittente                    :this.form.controls.mittente                    .value
                                             ,aoo                         :this.form.controls.aoo                         .value
                                             ,registro                    :this.form.controls.registro                    .value
                                             ,utente                      :this.form.controls.utente                      .value
                                             ,pwd                         :this.form.controls.pwd                         .value
                                             ,numeroRegistrazione         :this.form.controls.numeroRegistrazione         .value
                                             ,tipoIndirizzoTelematico     :this.form.controls.tipoIndirizzoTelematico     .value
                                             ,valoreIndirizzoTelematico   :this.form.controls.valoreIndirizzoTelematico   .value
                                             ,indirizzoPostale            :this.form.controls.indirizzoPostale            .value
                                             ,denominazioneAOO            :this.form.controls.denominazioneAOO            .value
                                             ,dataRegistrazione           :this.form.controls.dataRegistrazione           .value
                                            //  ,unitaOrganizzativa          :this.form.controls.unitaOrganizzativa          .value
                                             ,amministrazioneDenominazione:this.form.controls.amministrazioneDenominazione.value
                                             ,mittAooCodiceAoo            :this.form.controls.mittaoocodiceaoo.value
    };
  this.service.salvaConfigurazioneProtocollo(newConfig).then(
    response => {
      if (response.codiceEsito == CONST.OK) {
        //console.log("Salvataggio Configurazione Scheduling code OK");
       this.openEsito();
       this.configurazione=newConfig;
      } else {
        //console.log("Salvataggio Configurazione Scheduling code KO");
        this.errorService.emitError(response.descrizioneEsito);
      }
      this.loadingService.emitLoading(false);
    }).catch(error => {
      //console.log("Salvataggio Configurazione Scheduling ");
      this.errorService.emitError(error.statusText);
      this.loadingService.emitLoading(false);
    });
  }

  /**
   * Apertura dialog esito ok 
   */
  private openEsito(){    
    this.contentEsito=this.translateService.instant('protocollo.esito.content');
    this.titleEsito=this.translateService.instant('protocollo.esito.title');
    this.displayEsito=true
  }

/**
   * Chiusura dialog esito ok 
   */
  public closeEsito(){
    this.displayEsito=false;
  }

  /**
   * Switch per l'eye password
   */
  switchPassword():void{
    this.classEye = !this.classEye;
    this.tipo = this.classEye ? 'password' : 'text';
  }

}
