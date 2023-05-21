import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { Fascicolo, Allegato, AttivitaDaEspletareEnumLabels, AttivitaDaEspletareEnum } from 'src/app/shared/models/models';
import { DichiarazioniService } from 'src/app/features/compilazione-istanza/services/dichiarazioni/dichiarazioni.service';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { downloadFile } from 'src/app/shared/functions/generic.utils';

@Component({
  selector: 'app-allega-documenti-sottoscritti',
  templateUrl: './allega-documenti-sottoscritti.component.html',
  styleUrls: ['./allega-documenti-sottoscritti.component.scss']
})
export class AllegaDocumentiSottoscrittiComponent implements OnInit {
  @Input() fascicolo: Fascicolo;
  @Input() tableData: Allegato[];
  @Input() inIstruttoria:boolean=false;
  @Output() documentiUploaded: EventEmitter<any> = new EventEmitter<any>();
  fraseDinamica:string='ATTACH_SIGNED_DOCUMENTS.SUBSCRIPTION';

  documentTableHeaders: TableConfig[] = [];
  public attivitaDaEspletareLabel:string = AttivitaDaEspletareEnumLabels.get(AttivitaDaEspletareEnum.IN_PREISTRUTTORIA);

  public disabled: boolean = false;


  constructor(private dichiarazioniService:DichiarazioniService,
    private allegatoSvc:AllegatoService ) {}

  ngOnInit() {
    console.log("Stampo attivitaDaEspletareLabel -> "+ this.attivitaDaEspletareLabel);
    this.initTable();
    this.setTableData();
    this.documentTableHeaders = [
      {
        header: 'Descrizione',
        field: 'descrizione'
      },
      {
        header: 'Data',
        field: 'data'
      },
      {
        header: 'Impronta hash',
        field: 'checksum'
      }
    ];
    if(this.fascicolo.numeroProtocollo){
      this.fraseDinamica='ATTACH_SIGNED_DOCUMENTS.REGISTERED_INSTANCE';
    }else if(this.fascicolo.attivitaDaEspletare==AttivitaDaEspletareEnum.IN_ATTESA_DI_PROTOCOLLAZIONE){
      this.fraseDinamica='ATTACH_SIGNED_DOCUMENTS.NOT_REGISTERED_INSTANCE';
    }
  }

  setTableData(){
    if (this.fascicolo.documentiSottoscritti) {
      this.fascicolo.documentiSottoscritti.forEach((item, i) => {
        this.tableData[i] = item;
      });
      this.disabled = true;
    }
  }

  uploadFile(event) {

    const fileType = event.fileType;
    this.tableData.forEach((item, index) => {
      if (item.descrizione === fileType) {
        this.tableData[index].data = new Date().toLocaleString();
      }
    });

    this.documentiUploaded.emit(this.tableData);
  }

  initTable() {
    this.tableData = [
      {
        descrizione: 'istanza_' + this.fascicolo.codicePraticaAppptr,
        data: null
      },
      {
        descrizione: 'dichiarazione_tecnica_' + this.fascicolo.codicePraticaAppptr,
        data: null
      }
    ];
  }

  deleteFile(event: number) {
    this.tableData[event].data = null;
    this.documentiUploaded.emit(this.tableData);
  }

  download(row: any) {
    this.allegatoSvc.downloadAllegatoFascicolo(row.id + "",this.fascicolo.id, '/allegati/download.pjson').subscribe(result => {
      if (result.ok) {
        downloadFile(result.body, row.name,result.headers);
      }
    });
  }
  
}
