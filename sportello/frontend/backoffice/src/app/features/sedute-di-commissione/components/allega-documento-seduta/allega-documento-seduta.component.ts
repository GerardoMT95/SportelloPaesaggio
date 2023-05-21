import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { ExtraDataFileCL } from './../../models/seduta.models';
import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { TableConfig } from './../../../../core/models/header.model';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { Fascicolo, FileCommissioneLocale, SedutaDiCommissione } from './../../../../shared/models/models';
import { SeduteDiCommissioneConfig } from './../../configuration/seduteDICommissione.config';

@Component({
  selector: 'app-allega-documento-seduta',
  templateUrl: './allega-documento-seduta.component.html',
  styleUrls: ['./allega-documento-seduta.component.scss']
})
export class AllegaDocumentoSedutaComponent
{
  @Input("seduta") seduta: SedutaDiCommissione;
  @Input("disableButtons") disableButtons: boolean = true;

  @Output("uploadFile") uploadEvent: EventEmitter<any> = new EventEmitter<any>()
  @Output("deleteFile") deleteFile: EventEmitter<FileCommissioneLocale> = new EventEmitter<FileCommissioneLocale>();
  @Output("downloadFile") downloadFile: EventEmitter<FileCommissioneLocale> = new EventEmitter<FileCommissioneLocale>();
  @Output("showDetails") showDetailsEvent: EventEmitter<FileCommissioneLocale> = new EventEmitter<FileCommissioneLocale>();
  @Output("complete")complete: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output("cancel")cancel: EventEmitter<void> = new EventEmitter<void>();

  private selected: any[] = [];

  get _selected(){
    return this.selected;
  }
  set _selected(val:any[]){
    this.selected=val;
  }

  constructor(private dialog: CustomDialogService) { }
  
  public onUpload(event: any): void
  {
    let file = event.file;
    let metadata: ExtraDataFileCL = new ExtraDataFileCL();
    metadata.idSeduta = this.seduta.id;
    metadata.type = event.type;
    metadata.fascicoli = this.selected.map(m => m.value ? m.value : m);
    this.uploadEvent.emit({file, metadata});
  }

  public delete(event: FileCommissioneLocale): void { this.deleteFile.emit(event); }
  public download(event: FileCommissioneLocale): void { this.downloadFile.emit(event); }
  public showDetails(event: any): void { this.showDetailsEvent.emit(event.file); }
  public chiudi(): void { this.cancel.emit(); }
  public concludiSeduta(): void 
  {
    let requiredFiles = ["VERBALE", "SCHEDA_TECNICA"];
    let invalidTitle = "generic.warning";
    let invalidMessage: string;
    if (!requiredFiles.every(req => this.seduta.allegati.some(a => a.tipoAllegato === req)))
    {
      invalidMessage = "fascicolo.verb_com_loc.missingFile";
    } 
    else if (this.seduta.allegati.some(a => !a.praticheAssociate || a.praticheAssociate.length === 0))
    {
      invalidMessage = "fascicolo.verb_com_loc.invalidFiles";
    }

    if(invalidMessage)
      this.dialog.showDialog(true, invalidMessage, invalidTitle, DialogButtons.CHIUDI, null, DialogType.ERROR);
    else
      this.complete.emit(true); 
  }

  get docs(): FileCommissioneLocale[] { return this.seduta.allegati ? this.seduta.allegati.map(m => { return { ...m, type: m.tipoAllegato, fascicoliCount: m.praticheAssociate ? m.praticheAssociate.length : null } }) : []; }
  get fascicoli(): Array<Fascicolo> { return this.seduta.praticheDetails; }
  get fileTableHeaders(): TableConfig[] { return SeduteDiCommissioneConfig.fileTableHeaders; }
  get types(): DocumentType[] { return SeduteDiCommissioneConfig.documentoClTypes; }

}
