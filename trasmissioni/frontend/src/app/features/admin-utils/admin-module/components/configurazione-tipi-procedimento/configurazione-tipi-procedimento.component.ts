import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ErrorService } from 'src/app/services/error.service';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { AuthComponent } from 'src/app/components/generic/AuthComponent';
import { CONST } from 'src/shared/constants';
import { SelectItem } from 'primeng/primeng';
import { TipoProcedimentoDTO } from 'src/app/components/model/entity/tipoProcedimento.models';

@Component({
  selector: 'app-configurazione-tipi-procedimento',
  templateUrl: './configurazione-tipi-procedimento.component.html',
  styleUrls: ['./configurazione-tipi-procedimento.component.css']
})
export class ConfigurazioneTipiProcedimento extends AuthComponent {

  // form con i campi di configurazione scheduling
  public form: FormGroup;

  public submitted:boolean;
  //DIALOG DI CONFERMA SALVATAGGIO
  public displayConfirm: boolean = false;
  public contentConfirm: string;
  public titleConfirm: string;
  public typConfirm: string = 'info';

  public CONST: CONST = CONST;
  public MIN_YEAR: number = 1970;
  public MAX_YEAR: number = new Date().getFullYear() + 100;
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
  public tipiProcedimento:SelectItem[];

  constructor(private router          : Router
             ,private lss             : LocalSessionServiceService
             ,private loadingService  : LoadingService
             ,private translateService: TranslateService
             ,private service         : AdminService
             ,private errorService    : ErrorService
             ,private fb              : FormBuilder
  ) {
    super(router, lss);
  }

  ngOnInit() {
    this.submitted=false;
    this.buildForm();
    this.getTipiProcedimento();
  }
  /**
   * Build form
   */
  private buildForm(): void {
    this.form = this.fb.group({
                              tipoProcedimento  :[null, [Validators.required]]
                              ,dataInizioValidita      :[null, [Validators.required]]
                              ,dataFineValidita        :[null, [Validators.required]]
    })
  }

/**
   * Recupero tutti i tipi procedimento
   */
  public getTipiProcedimento() {
    this.service.getTipiProcedimentoWithAttributes().then(response => {
      if (response.codiceEsito == CONST.OK) {
        const ret: SelectItem[] = [];
        response.payload.forEach(element =>
        {
          ret.push({ label: element.descrizione, value: element });
        });
        this.tipiProcedimento=ret;
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

  public popolaValidita(event: any){
    //console.log("POPOLA VALIDITA': "+JSON.stringify(event));
    this.form.controls.dataInizioValidita.setValue(new Date(event.value.inizioValidita));
    this.form.controls.dataFineValidita.setValue(new Date(event.value.fineValidita));
  }


  /**
 * Open dialog confirm reset
 */
public resetForm() :void {

  this.displayReset=true;
  this.contentReset=this.translateService.instant('tipoProcedimento.reset.content');
  this.titleReset=this.translateService.instant('tipoProcedimento.reset.title');
}
  /**
   * Reset i valori della form ai valori di partenza
   */
  public callbackReset(event:any): void {

    this.displayReset=false;
    if(event.isConfirm){
      this.submitted=false;
      this.buildForm();
      this.getTipiProcedimento();
    }
  }

  /**
   * Display salva button
   */
  public confirmSave(): void {
    if (this.form.controls.dataInizioValidita.value>this.form.controls.dataFineValidita.value){
      this.openWarning(this.translateService.instant('tipoProcedimento.esito.titleWarning'),
                        this.translateService.instant('tipoProcedimento.errore.inizioMinoreDiFine'));
    }else {
      this.submitted=true;
      this.displayConfirm = true;
      this.contentConfirm = this.translateService.instant('tipoProcedimento.confirm.content');
      this.titleConfirm = this.translateService.instant('tipoProcedimento.confirm.title');
    }
  }

  /**
   * Callback confirm save
   */
  public callbackConfirm(event: any): void {
    this.displayConfirm = false;
    if(event.isConfirm){
        this.salvaConfigurazione();
    }
  }

  public openWarning(title:string, content:string){
    this.contentEsito=content;
    this.titleEsito=title;
    this.typEsito = 'warning';
    this.displayEsito=true
  }

  /**
   * Aggiornamento parametri di configurazione
   */
  public salvaConfigurazione() {
    //Creazione bean request per salvataggio nuovo periodo di validità
    this.loadingService.emitLoading(true);
    let newConfig : TipoProcedimentoDTO=this.form.controls.tipoProcedimento.value;
    newConfig.inizioValidita=this.form.controls.dataInizioValidita.value;
    newConfig.fineValidita=this.form.controls.dataFineValidita.value;
  this.service.updateTipoProcedimento(newConfig).then(
    response => {
      if (response.codiceEsito == CONST.OK) {
       this.openEsito();
      } else {
        this.errorService.emitError(response.descrizioneEsito);
      }
      this.loadingService.emitLoading(false);
    }).catch(error => {
      this.errorService.emitError(error.statusText);
      this.loadingService.emitLoading(false);
    });
  }

  /**
   * Apertura dialog esito ok 
   */
  private openEsito(){    
    this.contentEsito=this.translateService.instant('tipoProcedimento.esito.content');
    this.titleEsito=this.translateService.instant('tipoProcedimento.esito.title');
    this.typEsito = 'success';
    this.displayEsito=true
  }

/**
   * Chiusura dialog esito ok 
   */
  public closeEsito(){
    this.displayEsito=false;
  }

}
