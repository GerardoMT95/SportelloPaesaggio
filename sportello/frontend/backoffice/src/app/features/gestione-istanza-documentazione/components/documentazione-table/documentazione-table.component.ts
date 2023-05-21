import { LoggedUser } from './../../../../core/models/user.models';
import { Fascicolo, User, UlterioreDocumentazione } from 'src/app/shared/models/models';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { StatoEnum } from './../../../../shared/models/models';
import { TableConfig } from 'src/app/core/models/header.model';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { HttpAllegatoService } from 'src/app/shared/services/http-allegato.service';
import { DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { IButton } from 'src/app/core/models/dialog.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';

@Component({
  selector: 'app-documentazione-table',
  templateUrl: './documentazione-table.component.html',
  styleUrls: ['./documentazione-table.component.scss']
})
export class DocumentazioneTableComponent implements OnInit {
  @Input() meta: { [key: string]: SelectOption[] };
  @Input() fascioloTableHeaders: TableConfig[] = [];
  @Input() tableData: UlterioreDocumentazione[];
  @Input() tableHeader: TableConfig[];
  @Input() currentUser: LoggedUser;
  @Input() idFascicolo: string;
  @Output() deleteChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() navigateChange: EventEmitter<any> = new EventEmitter<any>();


  //attivitaDaEspletareEnum = AttivitaDaEspletareEnum;
  statoEnum = StatoEnum;

  constructor(private loading: LoadingService,
              private allegatiService: AllegatoService,
              private dialog: CustomDialogService) {}

  ngOnInit() {
    console.log('tableData', this.tableData);
  }

  delete(id: number) {
    this.deleteChange.emit(id);
  }

  navigate(id: number, azione: string) {
    let container = {
      idUlterioreDocumentazione: id,
      azione: azione
    }
    this.navigateChange.emit(container);
  }

  public download(id: string, titolo: string): void
  {
    this.loading.emitLoading(true)
    //this.allegatiService.callDownloadAllegato(id+'')
    this.allegatiService.downloadAllegatoFascicolo(id+'',this.idFascicolo, '/istruttoria/allegati/download.pjson')
    .subscribe(result =>
    {
      if(result.status == 200)
      {
        downloadFile(result.body, titolo)
        this.loading.emitLoading(false)
      }
      else
      {
        let title: string = "Errore"
        let message: string = "Errore durante il download dell'allegato"
        let button: IButton[] = [{ id: 0, label: "generic.chiudi" }]
        this.dialog.showDialog(true, message, title, button, null, DialogType.ERROR)
        this.loading.emitLoading(false)
      }
    })
  }

}
