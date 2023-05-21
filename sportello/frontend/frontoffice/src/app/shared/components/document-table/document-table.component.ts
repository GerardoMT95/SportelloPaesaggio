import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { Allegato, Fascicolo } from '../../models/models';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { environment } from 'src/environments/environment';
import { TranslateService } from '@ngx-translate/core';
import { FileSizePipe } from '../../pipes';


@Component({
  selector: 'app-document-table',
  templateUrl: './document-table.component.html',
  styleUrls: ['./document-table.component.scss']
})
export class DocumentTableComponent implements OnInit {
  //@Input() allegaDocumentoSottoscritii: boolean;
  @Input() fascicolo:Fascicolo;
  @Input() tableData: Allegato[];
  @Input() tableHeaders: TableConfig[] = [];
  @Output() emitDeletion: EventEmitter<any> = new EventEmitter<any>();
  @Output() emitDownload: EventEmitter<any> = new EventEmitter<any>();
  @Output() fileUpload: EventEmitter<any> = new EventEmitter<any>();
  @Input() filterFileToUploadFn = (file:File):string =>{return null;};
  @Input() enableUploadFromTable:boolean=true;
  @Input() disabled:boolean=false;
  constructor(
    private dialogService:DialogService,
    private translateService:TranslateService
  ) { }

  ngOnInit() {
    console.log("TABLE DATA", this.tableData);

  }
  download(id,name) {
    this.emitDownload.emit({id:id,name:name});
  }

  deleteFile(index){
    this.dialogService.showDialog(true, 
      'ANNULA.ELEMENTO',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
        (buttonID: string): void => {
          if(buttonID=="1"){
            this.doDeleteFile(index);
          }
        },
        DialogType.WARNING,
        null  );
  }

  doDeleteFile(index) {
    this.emitDeletion.emit(index);
  }


  private alertFileTypeMismatch(message:string){
    this.dialogService.showDialog(true, 
      message,
      'generic.warning',
      DialogButtons.CHIUDI,
        (buttonID: string): void => {          
        },
        DialogType.WARNING,
        null  );
  }

  uploadFile(event, fileType: string) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      let filterCustomMessage = this.filterFileToUploadFn(file);
      if (file.size > environment.maxSizeUpload)
			{
        let fileSizeMax=new FileSizePipe().transform(environment.maxSizeUpload);
        let message=this.translateService.instant('generic.dimensioneAllegatoOversize',{maxUploadSize:fileSizeMax})
        this.alertFileTypeMismatch(message);
        return;
      }
      if(filterCustomMessage){
        this.alertFileTypeMismatch(filterCustomMessage);
        return;
      }
      this.fileUpload.emit({file, fileType});
    }

  }
}
