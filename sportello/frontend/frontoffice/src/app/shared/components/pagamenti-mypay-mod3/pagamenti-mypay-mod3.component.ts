import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DialogService } from 'src/app/core/services/dialog.service';
import { CONST } from '../../constants';
import { downloadFile } from '../../functions/generic.utils';
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
  @Input() giorniScadenzaPagamento:number=0;

  formInputPag:FormGroup;
  public accendiValidazione=false;

  constructor(private fb: FormBuilder, 
    private route: ActivatedRoute, 
    private pagoPaService: PagoPaService, 
    private loadingSvc: LoadingService,
    private dialogService:DialogService
    ) { }

    
  
    dataScadenzaDisabilitata: boolean=false;
    pagato: boolean=false;
    dataScadenzaCalcolata:Date;
  
    
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
      //--- Set della scadenza in base ai parametri ricevuti
      console.log("Build Form");
      if(this.giorniScadenzaPagamento>0){
        this.dataScadenzaCalcolata=new Date();
        console.log("Giorni Scadenza:"+this.giorniScadenzaPagamento)
        this.dataScadenzaCalcolata.setDate(this.dataScadenzaCalcolata.getDate()+this.giorniScadenzaPagamento);
      }else {
        this.dataScadenzaCalcolata=null;
      }
      //---------
      console.log("Creo forminputpage con datascadenzacalcolata=" +this.dataScadenzaCalcolata);
      this.formInputPag=this.fb.group({
        importo: [null, [Validators.required,Validators.pattern(CONST.PATTERN_NUMERI)]], 
        //Si setta prima a null e poi dopo getpagamento si setta con la data di scadenza calcolata
        //questo per evitare che se esiste un pagamento generato si veda prima la data di scadenza calcolata sotto al loading
        //prima della fine di getPagamento
        dataScadenza: [{value:null, disabled: this.dataScadenzaCalcolata!=null},Validators.required]  
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
    }
  
    annullaPagamento(){
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
    }

    doAnnullaPagamento() {
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.annullaPagamento(this.praticaId).subscribe(response =>{
        this.loadingSvc.emitLoading(false);
        if(response.codiceEsito==CONST.OK){
          this.successDialog("generic.info" , "generic.operazione_ok");
          this.infoPagamento=null;
          this.getPagamento();
          this.reset();
          //non deve esserci una data di scadenza calcolata per poter abilitare il relativo campo
          if(this.dataScadenza.disabled && this.dataScadenzaCalcolata==null)
            this.dataScadenza.enable();
          if(this.importoProgetto.disabled)
            this.importoProgetto.enable();  
        }
      })
    }
  
    private getPagamento() {
      this.loadingSvc.emitLoading(true);
      this.pagoPaService.getPagamento(this.praticaId).subscribe(response =>{
        this.loadingSvc.emitLoading(false);
        this.infoPagamento = response.payload;
        if(this.infoPagamento) {
          this.pagato=this.infoPagamento.pagato;
          this.dataScadenza.disable();
          this.importoProgetto.disable();
          this.formInputPag.patchValue({
            dataScadenza: this.infoPagamento.dataScadenza,
            importo:this.infoPagamento.importoProgetto
          });
          if(!this.infoPagamento.pagato){
            this.loadingSvc.emitLoading(true);
            this.pagoPaService.verificaPagamento(this.praticaId).subscribe(response => {
              this.loadingSvc.emitLoading(false);
              if(response.payload != null)
                this.pagato = response.payload;
            });
          }
        }else { //se non c'è un pagamento generato si chiama reset per settare la data di scadenza ad oggi+30gg
          this.reset();
        }
        if(this.infoPagamento != null && this.infoPagamento.urlMyPay == null && !this.infoPagamento.pagato) {
          this.getMyPay();
        }
      });
    }
  
    private reset() {
      this.formInputPag.patchValue({
        importo: null,
        dataScadenza: this.dataScadenzaCalcolata
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
