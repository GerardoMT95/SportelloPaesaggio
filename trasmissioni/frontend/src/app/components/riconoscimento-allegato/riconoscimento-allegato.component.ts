import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Fascicolo, DettaglioFascicolo } from '../model/fascicolo.models';
import { TableConfig, RegistrationStatus } from '../model/entity/fascicolo.models';
import { AllegatoService } from 'src/app/services/allegato.service';
import { LoadingService } from 'src/app/services/loading.service';
import { RequestAllegato } from '../model/model';
import { TipoAllegato } from '../model/entity/allegato.models';
import { InformazioniDTO } from '../model/entity/info.models';
import { PlainTypeNumericId } from '../model/plain-type-numeric-id.model';
import { TableHeader } from 'src/shared/models/table-header';
import { FascicoloFormConfig } from '../form-fascicolo/form-plan-config';
import { TranslateService } from '@ngx-translate/core';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { CONST } from 'src/shared/constants';
import { takeUntil } from 'rxjs/operators';
import { Subject, combineLatest } from 'rxjs';
import { checkFileSizeTypeFn, downloadFile, saveFileToLocalStorage } from '../functions/genericFunctions';

@Component({
  selector: 'app-riconoscimento-allegato',
  templateUrl: './riconoscimento-allegato.component.html',
  styleUrls: ['./riconoscimento-allegato.component.css']
})
export class RiconoscimentoAllegatoComponent implements OnInit, OnDestroy {

  @Input() dettaglioFascicolo: InformazioniDTO;
  @Input() disable:boolean;
  @Output() emitTableData: EventEmitter<any> = new EventEmitter<any>();
  tableData: any[];
  documentTableHeaders: TableConfig[] = [];


  disableButton: boolean;
  attachmentsPerFileType: PlainTypeNumericId[] = [];
  tableHeadersAttachment: TableHeader[];
  allegatiId: number;
  public sezione: string = "responsabile";
  private unsubscribe$ = new Subject<void>();
  public fnCheckFileTypeSize=checkFileSizeTypeFn('application/pdf',CONST.MAX_1MB);


  constructor(public allegatiService: AllegatoService,
              public loadingService: LoadingService,
              private translateSvc: TranslateService,
              private autPaesService: AutorizzazioniPaesaggisticheService,
              private promptDialogService: ShowPromptDialogService,
              private alertService: ShowAlertService)
{
this.tableData = [
{
    id: null,
    nome: null,
    descrizione: '',
    data: null,
}
];
// this.documentTableHeaders = IstanzaConst.documentoReferenteCOnfigTable;
}

  ngOnInit() {

    this.tableHeadersAttachment=FascicoloFormConfig.allegatiUploadTableHeaders();
    this.planAttachments();

    
    if (this.dettaglioFascicolo.stato === RegistrationStatus.WORKING) {
      this.disableButton = true;
		}
		else {
			this.disableButton = false;
		}
  }

  initDocumentoData() {
    let req = new RequestAllegato();
    // req.praticaId = this.praticaId;
    // req.referenteId = this.referenteId;
    this.allegatiService.getAllegatoDocumentoMetadata(req, '/allegati/get_documento_referente.pjson')
        .then((response) => {
            let ret = response.payload;
            if (ret != null) {
                this.tableData[0] = ret;
                this.emitTableData.emit(this.tableData);
            }
        });
}

caricaDocumento($event) {
  this.loadingService.emitLoading(true);
  if ($event.file) {
      const file: File = $event.file;
      const formData: FormData = new FormData();
      formData.append('file', file);

      formData.append("idFascicolo",this.dettaglioFascicolo.id+"");
      // formData.append("tipoAllegato",TipoAllegato.DOCUMENTO_RICONOSCIMENTO);
      this.allegatiService.uploadAllegatoDocumento(formData, '/richiedente/carica')
          .then((response) => {
              let ret = response.payload;
              saveFileToLocalStorage(localStorage, ret, TipoAllegato.DOCUMENTO_RICONOSCIMENTO, this.dettaglioFascicolo.id.toString(), this.sezione);
              this.planAttachments();
          })
           //.finally(() => this.loadingService.emitLoading(false));
  }
}

// scaricaDocumento(idAndName) {
//   this.loadingService.emitLoading(true);
//   this.allegatiService.downloadAllegatoFascicolo(idAndName.id, '/allegati/download.pjson')
//       .toPromise()
//       .then(data => {
//           var blob = new Blob([data.body], { type: data.body.type });
//           this.allegatiService.downloadElemento(blob, idAndName.name);
//       })
//       .catch(error => {
//           console.log('download error:', JSON.stringify(error));
//           this.loadingService.emitLoading(false);
//       })
//     //   .finally(() => {
//     //       this.loadingService.emitLoading(false);
//     //   });
// }

// eliminaDocumento(id) {
//   this.loadingService.emitLoading(true);
//   let req = new RequestAllegato();

//   this.allegatiService.eliminaDocumentoReferente(req, '/allegati/elimina')
//       .then((response) => {
//           let ret = response.payload;
//           if (ret != null) {
//               this.tableData[0] = {
//                   id: null,
//                   nome: null,
//                   descrizione: '',
//                   data: null,
//               };
//               this.emitTableData.emit(this.tableData);
//           }
//       })
//        .finally(() => this.loadingService.emitLoading(false));
// }

	/**
	 * cliccato sull'icona e devo chiedere conferma
	 * @param event 
	 */
	deleteFileFromTable(event: any) {
		this.promptDialogService.showDeleteAllegatiDialog(true, this.sezione);
		this.allegatiId = event;
  }


  prepareDataForTableAndDropdown(allAttachments) {
		allAttachments.forEach(item => {
			//fill table .. non più, visto che l'upload è ammesso solo via p-fileupload
				this.attachmentsPerFileType.push
				({
					descrizione: item.nome,
					nome: '',
					id: item.id,
					data: item.data,
					mandatario: item.descrizione === 'mandatario' ? true : false,
					type: item.type
				});
		
		});
  }
  

  populatePlanAttachmentsTable(planAttachments) {
		let _this = this;

			planAttachments.forEach(item => {
				_this.attachmentsPerFileType.forEach((element, index) => {
					if (element.type === item.type)
						element.nome ? _this.attachmentsPerFileType.push(item) : (_this.attachmentsPerFileType[index] = item);
				});
			});
	}
  
  
	planAttachments() {
		this.attachmentsPerFileType = [];
		combineLatest(this.autPaesService.getAttachments("", this.sezione, this.autPaesService.getLastTipoProcedimento()),
			this.autPaesService.getAttachments(this.dettaglioFascicolo.id.toString(), this.sezione, this.autPaesService.getLastTipoProcedimento()))
			.pipe(takeUntil(this.unsubscribe$)).subscribe
			(
				([allAttachments, planAttachments]) => {
					this.prepareDataForTableAndDropdown(allAttachments);
					this.populatePlanAttachmentsTable(planAttachments);
				}
      );
	}


deleteAllegati(event: any) {
  //eliminaAllegato
  this.autPaesService.eliminaAllegato(this.allegatiId.toString(),this.dettaglioFascicolo.id+'').pipe(takeUntil(this.unsubscribe$)).subscribe(response => {
    this.promptDialogService.showDeleteAllegatiDialog(false, this.sezione);
    this.alertService.showAlertDialog(true, null, this.translateSvc.instant("ATTACHMENT_DELETE_BUTTON_MESSAGE"));
    // if (response.codiceEsito === CONST.OK) {
    //   let key = 'ATTACH#' + this.dettaglioFascicolo.id + '#' + this.sezione + '#' + this.allegatiId.toString();
    //   localStorage.removeItem(key);
    // }
    if (response.codiceEsito === CONST.OK) {
      let key = 'ATTACH#' + this.dettaglioFascicolo.id + '#' + this.sezione + '#' + this.allegatiId.toString();
      localStorage.removeItem(key);
      this.planAttachments();
    }
    else {
      console.error("Errore nella cancellazione");
    }
  });
}

ngOnDestroy() {
  this.unsubscribe$.next();
  this.unsubscribe$.complete();
}

download(row: PlainTypeNumericId)
	{
		let obsDownload$=this.autPaesService.downloadAllegatoFascicolo(this.dettaglioFascicolo.id+'',row.id.toString());
		obsDownload$.subscribe(result =>
		{
			if (result.ok)
			{
				downloadFile(result.body, row.nome);
			}
		});
	}

}
