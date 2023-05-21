import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from 'src/app/shared/constants';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { ErrorService } from 'src/app/shared/services/error.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DiogeneApplicationConfigbean } from '../../models/admin-functions.models';

@Component({
  selector: 'app-configurazione-diogene',
  templateUrl: './configurazione-diogene.component.html',
  styleUrls: ['./configurazione-diogene.component.scss']
})
export class ConfigurazioneDiogeneComponent implements OnInit {


  public environmentOptions=
  [{
    label:"SVILUPPO",
    value:"SVILUPPO"
  },
  {
    label:"PRODUZIONE",
    value:"PRODUZIONE"
  }]

  // form con i campi di configurazione scheduling
  public form: FormGroup;

  //DIALOG DI CONFERMA SALVATAGGIO O INDIETRO
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

  //DIALOG DI ALERT VALIDAZIONE
  public displayInvalid: boolean = false;
  public contentInvalid: string;
  public titleInvalid: string;
  public typInvalid: string = 'danger';

  //BEAN CON PARAMETRI DI CONFIGURAZIONE
  public configurazione: DiogeneApplicationConfigbean;
  public disabled=false;
  public submitted=false;
  public isAutpae=true;


  /**
   * alert data generica
   */
   alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
    labelOk: "YES_BUTTON",
    labelChiudi: "NO_BUTTON"
  };

  constructor(private loadingService: LoadingService
    , private translateService: TranslateService
    , private service: AdminFunctionsService
    , private errorService: ErrorService
    , private fb: FormBuilder
    , private router: Router
  ) {
    this.buildForm();

  }

  ngOnInit() {
    this.getConfigurazione();
  }
  /**
   * Build form
   */
  private buildForm(): void {

    this.form = this.fb.group({
      codiceApplicazione: ["", [Validators.required]]
      ,gatewayUsername: ["", [Validators.required]]
      , gatewayPassword: ["", [Validators.required]]
      , gatewayConsumerKey: ["", [Validators.required]]
      , gatewayConsumerSecret: ["", [Validators.required]]
      , diogeneUsername: ["", [Validators.required]]
      , diogenePassword: ["", [Validators.required]]
      , diogeneConsumerKey: ["", [Validators.required]]
      , diogeneConsumerSecret: ["", [Validators.required]]
      , idTitolario: ["", [Validators.required]]
      , idVoceTitolario: ["", [Validators.required]]
      , idCartellaRoot: ["", [Validators.required]]
      , nomeCartellaRoot: ["", [Validators.required]]
      , environment: ["", [Validators.required]]
      , idAoo: ["", []]
      , enabled: [false, [Validators.required]]
    });
  }

  /**
     * Recupero parametri di configurazione scheduling
     */
  public getConfigurazione() {
    this.loadingService.emitLoading(true);
    this.service.getDiogeneConfiguration().subscribe(response => {
      if (response.codiceEsito == CONST.OK) {
        this.loadingService.emitLoading(false)
        this.configurazione = response.payload;
        this.setValue();
      } 
    });
  }

  /**
  * set value from bean to form
  */
  private setValue(): void {
    this.form.patchValue(this.configurazione);
  }

  /**
 * Open dialog confirm reset
 */
  public resetForm(): void {
    this.displayReset = true;
    this.contentReset = this.translateService.instant('diogene.reset.content');
    this.titleReset = this.translateService.instant('diogene.reset.title');
  }
  /**
   * Reset i valori della form ai valori di partenza
   */
  public callbackReset(event: any): void {

    this.displayReset = false;
    if (event.isConfirm){
      this.setValue();
      this.submitted=false;
    }
  }

  /**
   * Display salva button
   */
  public confirmSave(): void {
    this.submitted=true;
    if(this.form.invalid){
      this.openInvalid();
    }else{
      this.displayConfirm = true;
      this.contentConfirm = this.translateService.instant('diogene.confirm.content');
      this.titleConfirm = this.translateService.instant('diogene.confirm.title');
    }
    
  }

  /**
   * Callback confirm save
   */
  public callbackConfirm(event: any): void {
    this.displayConfirm = false;
    if (event.isConfirm){
      this.salvaConfigurazione();
    }
  }

  /**
   * Aggiornamento parametri di configurazione
   */
  public salvaConfigurazione() {
    this.loadingService.emitLoading(true);
    let newConfig: DiogeneApplicationConfigbean = this.form.getRawValue();
    this.service.upsertDiogeneConfiguration(newConfig).subscribe(
      response => {
        if (response.codiceEsito == CONST.OK) {
          this.openEsito();
          this.configurazione = newConfig;
          this.form.markAsPristine();
          this.form.markAsUntouched();
        } else {
          this.errorService.emitError(response.descrizioneEsito);
        }
        this.loadingService.emitLoading(false);
      });
  }

  /**
   * Apertura dialog esito ok 
   */
  private openEsito() {
    this.contentEsito = this.translateService.instant('diogene.esito.content');
    this.titleEsito = this.translateService.instant('diogene.esito.title');
    this.displayEsito = true
  }

  /**
     * Chiusura dialog esito ok 
     */
  public closeEsito() {
    this.displayEsito = false;
  }


  /**
   * chiusura dialog invalid
   */
  public closeInvalid() {
    this.displayInvalid = false;
  }

  /**
   * apertura dialog invalid
   */
  private openInvalid() {
    this.contentInvalid = this.translateService.instant('generic.campiInvalidi');
    this.titleInvalid = this.translateService.instant('dialog.warning');
    this.displayInvalid = true
  }

  public back() {
    if(this.form.dirty){
      this.alertData.typ = "warning";
      this.alertData.isConfirm = true;
      this.alertData.content = this.translateService.instant('diogene.warningDatiNonSalvati');
      this.alertData.labelChiudi = 'generic.no';
      this.alertData.extraData ={ operazione: 'back'};
      this.alertData.display=true;
    }else{
      this.doBack();
    }
  }

  public callBack(event:any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'back':
            this.doBack();
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  private doBack(){
    this.router.navigate(['gestione-istanze']);
  }

}
