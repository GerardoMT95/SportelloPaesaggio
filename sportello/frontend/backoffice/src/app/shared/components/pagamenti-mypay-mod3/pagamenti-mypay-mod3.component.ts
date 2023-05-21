import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { CONST } from '../../constants';
import { AvviaPagamentoRequestBean, PagamentoDto } from '../../models/models';
import { LoadingService } from '../../services/loading.service';
import { PagoPaService } from '../../services/pago-pa.service';
import { ButtonType, DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-pagamenti-mypay-mod3',
  templateUrl: './pagamenti-mypay-mod3.component.html',
  styleUrls: ['./pagamenti-mypay-mod3.component.scss']
})
export class PagamentiMypayMod3Component implements OnInit {
  @Input() praticaId:string;
  @Input() disabled:boolean=false;
  @Input() hasPagamenti:boolean=false;
  formInputPag:FormGroup;
  public accendiValidazione=false;

  constructor(private fb: FormBuilder, 
    private pagoPaService: PagoPaService, 
    private loadingSvc: LoadingService,
    private dialogService:CustomDialogService
    ) { }

    
  
    dataScadenzaDisabilitata: boolean=false;
    pagato: boolean=false;
  
    
    const = CONST;
    infoPagamento:PagamentoDto=null;
  
   
     get pagoPa() { return (this.formInputPag as FormGroup); }
  
    ngOnInit() {
      this.loadingSvc.emitLoading(true);
      this.buildForm();
      this.loadingSvc.emitLoading(false);
      this.getPagamento(); 
    }
  
    public buildForm() {
      this.formInputPag=this.fb.group({
        importo: [null, [Validators.required,Validators.pattern(CONST.PATTERN_NUMERI)]], 
        dataScadenza: [null,Validators.required]  
      });  
      if(this.disabled){
        this.formInputPag.disable();
      }
    }
  
    get dataScadenza() {
      return this.formInputPag.get("dataScadenza");
    }
    get importoProgetto() {
      return this.formInputPag.get("importo");
    }
    avviaPagamento() {
      /*
      if(this.formInputPag.invalid){
        this.accendiValidazione=true;
        this.errorDialog('generic.warning','generic.campiInvalidi');
        return;
      }
      let value = this.formInputPag.getRawValue();
      let generaPagamento = new AvviaPagamentoRequestBean();
      generaPagamento.importoDiProgetto = value.importo;
      generaPagamento.dataScadenza = value.dataScadenza;
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.avvioPagamento(this.praticaId, generaPagamento).subscribe(response => {
        this.loadingSvc.emitLoading(false);
        if(response.codiceEsito==CONST.OK){
          this.successDialog("generic.info","generic.operazione_ok");
          this.infoPagamento = response.payload;
          if(this.infoPagamento !== null && this.infoPagamento.urlMyPay == null) {
            this.getMyPay();
          }
          if(this.infoPagamento!=null){
            this.formInputPag.patchValue({
              dataScadenza: this.infoPagamento.dataScadenza,
              importo:this.infoPagamento.importoProgetto
            });
          }
          if(!this.dataScadenza.disabled)
            this.dataScadenza.disable();
          if(!this.importoProgetto.disabled)
            this.importoProgetto.disable();  
        }
      });
      */
    }
  
    annullaPagamento(){
      /*
      this.dialogService.showDialog(true,
        "generic.confermiEliminazione",
        'generic.warning',
        DialogButtons.OK_CANCEL,
        (buttonID: number): void => {
          if(buttonID==ButtonType.OK_BUTTON)
          this.doAnnullaPagamento();
         },
        DialogType.WARNING,
        null);
        */
    }

    doAnnullaPagamento() {
      /*
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.annullaPagamento(this.praticaId).subscribe(response =>{
        this.loadingSvc.emitLoading(false);
        if(response.codiceEsito==CONST.OK){
          this.successDialog("generic.info" , "generic.operazione_ok");
          this.infoPagamento=null;
          this.getPagamento();
          this.reset();
          if(this.dataScadenza.disabled)
            this.dataScadenza.enable();
          if(this.importoProgetto.disabled)
            this.importoProgetto.enable();  
        }
      });
      */
    }
  
    private getPagamento() {
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.getPagamento(this.praticaId).subscribe(response =>{
        this.loadingSvc.emitLoading(false);
        this.infoPagamento = response.payload;
        if(this.infoPagamento) {
          this.dataScadenza.disable();
          this.importoProgetto.disable();
          this.formInputPag.patchValue({
            dataScadenza: this.infoPagamento.dataScadenza,
            importo:this.infoPagamento.importoProgetto
          });
          this.loadingSvc.emitLoading(true);
          this.pagoPaService.verificaPagamento(this.praticaId).subscribe(response => {
            this.loadingSvc.emitLoading(false);
            if(response.payload != null)
              this.pagato = response.payload;
          });
        }
        if(this.infoPagamento != null && this.infoPagamento.urlMyPay == null) {
          this.getMyPay();
        }
      });
    }
  
    private reset() {
      this.formInputPag.patchValue({
        importo: null,
        dataScadenza: null
      })
    }
    private getMyPay() {
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.getUrlMyPay(this.praticaId).subscribe(response => {
        this.loadingSvc.emitLoading(false);
        this.infoPagamento.urlMyPay = response.payload;
      })
    }

    downloadRicevuta() {
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.downloadRicevuta(this.praticaId).subscribe((response) => {
        this.loadingSvc.emitLoading(false);
        downloadFile(response.body, "Ricevuta Pagamento");
      })
    }

    private errorDialog(titolo,messaggio){
      this.dialogService.showDialog(true,
        messaggio,
        titolo,
        DialogButtons.CHIUDI,
        (buttonID: string): void => { },
        DialogType.WARNING,
        null);
    }

    private successDialog(titolo,messaggio){
      this.dialogService.showDialog(true,
        messaggio,
        titolo,
        DialogButtons.CHIUDI,
        (buttonID: string): void => { },
        DialogType.SUCCESS,
        null);
    }

}
