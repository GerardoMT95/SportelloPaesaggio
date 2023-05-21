import { DeprecatedCurrencyPipe } from '@angular/common';
import { Input, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { CONST } from '../../constants';
import { PagamentiMypayDTO, PagamentiMypaySearch } from '../../models/models';
import { LoadingService } from '../../services/loading.service';
import { FascicoloService } from '../../services/pratica/http-fascicolo.service';
import { ButtonType, DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { GenericTableComponent } from '../generic-table/generic-table.component';
@Component({
  selector: 'app-pagamenti-mypay',
  templateUrl: './pagamenti-mypay.component.html',
  styleUrls: ['./pagamenti-mypay.component.scss']
})

/**
 * Sostituito da PagamentiMypayMod3
 */
export class PagamentiMypayComponent implements OnInit {
  @Input("praticaId") praticaId:string;
  @Input("codicePratica") codicePratica:string;
  @Input("disabled") disabled:boolean=true; //non si puo' più pagare
  @Input("limiteMaxPag") maxImporto:number=0; //se totOK+totIncorso<MaxImporto.. attivo il pagamento 0 per nessun limite...
  @Input('totPagamenti') objTotali:{'OK'?:number,'KO'?:number,'INCORSO'?:number};
  formInput:FormGroup;
  accendiValidazione:boolean=false;
  pagSearch=new PagamentiMypaySearch();
  pagamentiData: PagamentiMypayDTO[]=[];
  recTotali:number=0;
  

  intestazioneTabella: TableConfig[] = [
    { field: 'importo', header: 'PAGAMENTI.IMPORTO',type:'currency' },
    { field: 'causale', header: 'PAGAMENTI.CAUSALE'},
    { field: 'stato', header: 'PAGAMENTI.STATUS' , type:'optionWithColor',
    optionSelect:[
      {value:'OK',description:'Ok',linked:'#07b536'},
      {value:'KO',description: 'Ko',linked:'#dc3545'},
      {value:'INCORSO',description:'In corso',linked:'#eeb448'}
    ]
     },
    { field: 'dataInserimento', header: 'PAGAMENTI.DATA', type: 'datetime' }
  ];
  mapTotali:{label:string,color:string,importo:number}[]=[];
  

  constructor(private fb:FormBuilder,
              private dialogService:CustomDialogService,
              private fascicoloService:FascicoloService,
              private loadingService:LoadingService) { }

  ngOnInit() {
    this.formInput=this.fb.group({
        causale:['',Validators.required],
        importo:[null,[Validators.required,Validators.pattern(CONST.PATTERN_CURRENCY_EURO)]]
    });
    //carico i pagamenti effettuati
    this.pagSearch.limit=5
    this.pagSearch.page=1;
    this.pagSearch.praticaId=this.praticaId;
    this.pagSearch.sortBy='dataInserimento';
    this.pagSearch.sortType='DESC';
    this.refreshPagePagamenti(true);
    this.mapTotali=this.mappaTotali(this.objTotali);
  }

  /*refreshTotali(){
    this.loadingService.emitLoading(true);
    this.fascicoloService.totPagamentiPerStato(this.praticaId).subscribe(resp=>{
      this.loadingService.emitLoading(false);
      if(resp.payload){
        this.objTotali=resp.payload;
        this.mapTotali=this.mappaTotali(resp.payload);
      }
    });
  }*/

  refreshPagePagamenti(refreshTotali?:boolean){
    this.loadingService.emitLoading(true);
    this.fascicoloService.searchPagamenti(this.pagSearch).subscribe(resp=>{
        this.loadingService.emitLoading(false);
        if(resp.payload){
          this.pagamentiData=resp.payload.list;
          this.recTotali=resp.payload.count;
          if(refreshTotali && this.pagamentiData && this.pagamentiData.length>0){
            //li aggiorno solo se ci sono record... non ha senso aggiornarli...
            //this.refreshTotali();
          }
        }
    });
  }

  cambioPagina(event:{limit?:number,page:number}){
    if (event.limit) {
      this.pagSearch.limit = event.limit;
    }
    if (event.page) {
      this.pagSearch.page = event.page;
    }
    this.refreshPagePagamenti();
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

  
  paga(){
    this.accendiValidazione=true;
    if(!this.formInput.valid){
        this.errorDialog('generic.warning','generic.campiInvalidi');
        return;
    }
    let formValue=this.formInput.getRawValue();
    formValue.importo=(formValue.importo as string).replace(',','.');
    if(this.maxImporto && this.maxImporto>0){
      //verifico che con questo pagamento non supera l'importo ammesso...
      let importoAPagare=Number.parseFloat(formValue.importo);
      let totNew=
        importoAPagare+
          (this.objTotali['OK']?this.objTotali['OK']:0)+
          (this.objTotali['INCORSO']?this.objTotali['INCORSO']:0);
      if(totNew>this.maxImporto){
        let messaggio="L'importo totale comprensivo dei pagamenti in corso " +totNew+" € supera il limite massimo ammesso di "+this.maxImporto + " €";
        this.errorDialog('generic.warning',messaggio);
        return;
      }
    }
    //chiamo il webservice e se ok 
    //rimando alla pagina di cassa di mypay
    this.dialogService.showDialog(true,
      "Verrai rediretto alla pagina di MyPay per effettuare l'autenticazione e il pagamento",
      'generic.info',
      [{ id: ButtonType.OK_BUTTON, label: 'BUTTONS.PROSEGUI' }],
      (buttonID: string): void => {
        this.avviaPagamento(formValue)
       },
      DialogType.INFORMATION,
      null);
    
  }

  avviaPagamento(formValue){
    let retUrl=window.location.href;//questa pagina
    this.accendiValidazione=false;
    this.formInput.reset();
    this.loadingService.emitLoading(true);
    this.fascicoloService.effettuaPagamento(this.praticaId,
      this.codicePratica + ' '+formValue.causale,
      formValue.importo,retUrl)
    .subscribe(ret=>
      {
        this.loadingService.emitLoading(false);
        if(ret.codiceEsito==CONST.OK && ret.payload && ret.payload.urlToPay){
          //redirect on page of mypay
          window.location.href=ret.payload.urlToPay;
        }
      });
  }


  mappaTotali(mappaTotali:{'OK'?:number,'KO'?:number,'INCORSO'?:number}):{label:string,color:string,importo:number}[]{
    let ret=[];
    let header=this.intestazioneTabella.find(el=>el.field=='stato');
    if(mappaTotali){
      Object.keys(mappaTotali).forEach(keyStato=>{
        ret.push({...GenericTableComponent.optionWithColor(keyStato,header,this.intestazioneTabella),importo:mappaTotali[keyStato]});
      });
    }
    return ret;
  }

}
