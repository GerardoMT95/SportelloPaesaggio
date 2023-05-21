import { ActionEvent } from './../../models/integrazione.models';
import { DocumentType } from 'src/app/shared/models/allegati.model';
import { BFile } from './../../../../shared/models/allegati.model';
import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { Fascicolo, IntegrazioneDocumentale } from 'src/app/shared/models/models';
import { DialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { FormControl } from '@angular/forms';
import { CONST } from 'src/app/shared/constants';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-documento-riepilogo-integrazione',
  templateUrl: './documento-riepilogo-integrazione.component.html',
  styleUrls: ['./documento-riepilogo-integrazione.component.scss']
})
export class DocumentoRiepilogoIntegrazioneComponent implements OnChanges
{
  @Input("fascicolo") fascicolo: Fascicolo;
  @Input("documentoIntegrazione") documentoIntegrazione: BFile;
  @Input() integrazione: IntegrazioneDocumentale;
  
  @Output("action") actionEmitter: EventEmitter<ActionEvent> = new EventEmitter<ActionEvent>();
  @Output("download") downloadEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output("upload") uploadEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output("delete") deleteEmitter: EventEmitter<any> = new EventEmitter<any>();
  
  fcIsP7M:FormControl=new FormControl();
  
  public domande: any[] = [{ "descrizione": "INT_DOC", "nome": "Integrazione_Documentale" }];
  public types: DocumentType[] = [{ label: "Integrazione documentale", type: 702, required: true, multiple: false, accept: [...CONST.mimePDF,...CONST.mimeTypeP7M] }];

  constructor(private serviceDialog:DialogService
              ,private translateService:TranslateService) { }


  ngOnChanges(changes: SimpleChanges): void {
    if(changes && changes.documentoIntegrazione){
      this.refreshCheckBoxP7M();
    }
  }

  public completa(): void
  {
    let _this=this;
    if(this.files.length === 1){
      this.serviceDialog.showDialog(
        true,
        'integrazione.confermaTrasmetti',
        'generic.info',
        DialogButtons.CONFERMA_CHIUDI,
        (bottone:ButtonType)=>{
          if(bottone==ButtonType.OK_BUTTON){
            _this.actionEmitter.emit("COMPLETA");
          }
        }
      );
    }
  }
  
  refreshCheckBoxP7M() {
    if(this.files && this.files.some(file=>CONST.mimeTypeP7M.includes(file.formatoFile))){
      this.fcIsP7M.setValue(true)
      this.fcIsP7M.disable();
    }else if(this.files && this.files.length>0) {
      this.fcIsP7M.disable();
    }else{
      this.fcIsP7M.enable();
    }
  }

  public indietro(): void { this.actionEmitter.emit("INDIETRO"); }
  public downloadDocumento(): void { this.actionEmitter.emit("DOWNLOAD"); }
  public tornaBozza(): void { this.actionEmitter.emit("TORNA_IN_BOZZA"); }
  public download(event: any): void { this.downloadEmitter.emit(event); }
  public upload(evt: any): void { 
    let event=evt;
    if(this.fcIsP7M.value){
      event.p7m=true;
    }
    if(this.fcIsP7M.value && !CONST.mimeTypeP7M.includes(event.file.type)){
      //il file deve essere firmato...
      let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:CONST.mimeTypeP7M});
      this.serviceDialog.showDialog(true, message, "generic.warning", DialogButtons.CHIUDI, null, DialogType.ERROR);
      return;
    }
    if(!this.fcIsP7M.value && CONST.mimeTypeP7M.includes(event.file.type)){
        //il file deve NON essere firmato...
      let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:CONST.mimePDF});
      this.serviceDialog.showDialog(true, message, "generic.warning", DialogButtons.CHIUDI, null, DialogType.ERROR);
      return;
    }
    this.uploadEmitter.emit(event); 
  }
  public delete(event: any): void { this.deleteEmitter.emit(event); }

  public get files(): BFile[] { return this.documentoIntegrazione ? [this.documentoIntegrazione] : []; }

}
