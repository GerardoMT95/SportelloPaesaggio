import { Component, OnDestroy, OnInit } from '@angular/core';
import {  FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { VPaesaggioTipoCaricaDocumentoDTO } from 'src/app/components/model/admin-function/admin-function-model';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { ErrorService } from 'src/app/services/error.service';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-configurazione-sportello-ambiente',
  templateUrl: './configurazione-sportello-ambiente.component.html',
  styleUrls: ['./configurazione-sportello-ambiente.component.css']
})
export class ConfigurazioneSportelloAmbienteComponent implements OnInit,OnDestroy {

  opzioniSelezionabili:SelectItem[];
  selected:SelectItem[];
  loaded:boolean;
  helpSelezione:string;

  private unsubscribe: Subject<void> = new Subject<void>();
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

  form: FormGroup;

  constructor(private loadingService: LoadingService
    , private translateService: TranslateService
    , private service: AdminService
    , private errorService: ErrorService
    , private router: Router
    , private fb: FormBuilder
  ) {
  }

  ngOnDestroy()
  {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  ngOnInit() {
    this.buildForm();
    this.caricaDati()
  }


  private caricaDati(){
    this.loadingService.emitLoading(true);
    combineLatest(this.service.getTipiDocSportello(), 
                  this.service.optionsTipiDocSportello())
    .pipe(takeUntil(this.unsubscribe))
    .subscribe(([selezioni, optionResponse]) =>{
      this.loadingService.emitLoading(false);
      this.opzioniSelezionabili=optionResponse.payload.map(vPaeTipo=>this.toSelectItem(vPaeTipo));
      this.selected=optionResponse.payload
      .filter(tipo=>selezioni.payload.indexOf(tipo.id)>=0)
      .map(el=>this.toSelectItem(el));
      this.resettaForm();
      this.loaded=true;
    });
  }

  private toSelectItem(item:VPaesaggioTipoCaricaDocumentoDTO){
    return {
      value:item,
      label:item.descrizione + " - " + item.nome
    };
  }

  private resettaForm(){
    this.form.patchValue({tipoCaricaDocumento:this.selected});
  }

  private buildForm(){
    this.form=this.fb.group({
      tipoCaricaDocumento:[]
    });
  }

  confirmSave(){
    this.alertData.content ="generic.confermaSalva";
    this.alertData.typ = "info";
    this.alertData.isConfirm=true;
    this.alertData.display=true;
    this.alertData.title="generic.info"
    this.alertData.extraData={ operazione: "save"};
  }

  salva(){
    let formData=this.form.getRawValue();
    let tipiSelezionatiObj:any=formData.tipoCaricaDocumento;
    console.log("tipiSelezionatiObj:",tipiSelezionatiObj);
    if(!tipiSelezionatiObj){
      tipiSelezionatiObj=[];
    }
    let tipiSelezionati=tipiSelezionatiObj.map(vtipo=>vtipo.value.id);
    console.log("tipiSelezionati:",tipiSelezionati);
    this.service.upsertTipiDocSportello(tipiSelezionati).subscribe(response=>{
      if(response.payload!=1){
        this.alertData.title="generic.errore";
        this.alertData.content="generic.error";
        this.alertData.isConfirm=false;
        this.alertData.extraData={};
        this.alertData.display=true;
      }else{
        this.alertData.title="generic.successo";
        this.alertData.content="generic.operazioneOk";
        this.alertData.isConfirm=false;
        this.alertData.extraData={};
        this.alertData.display=true;
        this.selected=tipiSelezionatiObj;
        this.form.markAsUntouched();
      }
    });
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
     if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'save':
            this.salva();
            break;
          case 'confirmBack':  
            this.doBack();
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  private doBack(){
    this.router.navigate(['/private/fascicolo']);
  }

  back(){
    if(this.form.touched){
      this.alertData.title="generic.info";
      this.alertData.content="generic.indietroTouched";
      this.alertData.isConfirm=true;
      this.alertData.extraData={operazione:"confirmBack"};
      this.alertData.display=true;   
    }else{
      this.doBack();
    }
  }

  resetForm(){
    if(this.form.touched){
      this.resettaForm();
    }
  }

}
