import { Allegato, Fascicolo, Role } from 'src/app/shared/models/models';
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { TableConfig } from 'src/app/core/models/header.model';
import { TranslateService } from '@ngx-translate/core';
import { documentTableHeaders } from '../../gestione-istanza-relazione-ente-config';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';

@Component({
  selector: 'app-allegati',
  templateUrl: './allegati.component.html',
  styleUrls: ['./allegati.component.scss']
})
export class AllegatiComponent implements OnInit {
  @Input() fascicolo: Fascicolo;
  @Input() tableData: Allegato[];
  @Input() form: FormGroup;
  @Input() userRole;
  role = Role;
  @Input() documentTypeOptions: SelectOption[];
  @Output() emitDeletion: EventEmitter<any> = new EventEmitter<any>();
  @Output() emitDownload: EventEmitter<any> = new EventEmitter<any>();
  @Output() emitUploadFromTable: EventEmitter<any> = new EventEmitter<any>();
  @Output() emitUploadOnFileSelect: EventEmitter<any> = new EventEmitter<any>();
  documentTableHeaders: TableConfig[];
  allegatiPagamentoData: Allegato[];

  constructor(private translateService: TranslateService) {}

  ngOnInit() {
    this.documentTableHeaders = documentTableHeaders;
  }

  deleteFile(event) {
    this.emitDeletion.emit(event);
  }
  uploadFile(event) {
    this.emitUploadFromTable.emit(event);
  }

  onFileSelect(event) {
    this.emitUploadOnFileSelect.emit(event);
  }

}
