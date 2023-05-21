import { CustomDialogService } from './../../../core/services/dialog.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { Allegato, Fascicolo } from '../../models/models';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';


@Component({
  selector: 'app-document-table',
  templateUrl: './document-table.component.html',
  styleUrls: ['./document-table.component.scss']
})
export class DocumentTableComponent implements OnInit
{
  //@Input() allegaDocumentoSottoscritii: boolean;
  @Input() fascicolo: Fascicolo; //attualmente non utilizzato....
  @Input() tableData: Allegato[];
  @Input() tableHeaders: TableConfig[] = [];
  @Output() emitDeletion: EventEmitter<any> = new EventEmitter<any>();
  @Output() emitDownload: EventEmitter<any> = new EventEmitter<any>();
  @Output() fileUpload: EventEmitter<any> = new EventEmitter<any>();
  @Input() enableUploadFromTable: boolean = true;
  //@Input() disableButton: boolean; //rinominato in disable come per presentazioneIstanza
  @Input() disabled:boolean=false;
  
  
  constructor(private dialogService: CustomDialogService) { }

  ngOnInit()
  {
    console.log("TABLE DATA", this.tableData);

  }
  download(id, name)
  {
    this.emitDownload.emit({ id: id, name: name });
  }

  deleteFile(index)
  {
    this.dialogService.showDialog(true,
      'ANNULA.ELEMENTO',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
          this.doDeleteFile(index);
        }
      },
      DialogType.WARNING,
      null);
  }

  doDeleteFile(index)
  {
    this.emitDeletion.emit(index);
  }

  uploadFile(event, fileType: string)
  {
    if (event.target.files.length > 0)
    {
      const file = event.target.files[0];
      this.fileUpload.emit({ file, fileType });
    }

  }
}