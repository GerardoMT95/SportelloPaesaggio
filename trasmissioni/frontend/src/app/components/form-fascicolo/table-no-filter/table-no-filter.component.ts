import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subject } from 'rxjs';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { TableHeader } from 'src/shared/models/table-header';
import { checkFileSizeTypeFn, downloadFile } from '../../functions/genericFunctions';
import { PlainTypeNumericId } from '../../model/plain-type-numeric-id.model';
import { ArrayDataSource } from '@angular/cdk/collections';
import { CONST } from 'src/shared/constants';

@Component({
	selector: 'app-table-no-filter',
	templateUrl: './table-no-filter.component.html',
	styleUrls: ['./table-no-filter.component.scss']
})
export class TableNoFilterComponent implements OnInit, OnDestroy 
{
	@Input() disableButton: boolean;
	@Input() attachmentsPerFileType: PlainTypeNumericId[];
	@Input() tableHeadersAttachment: TableHeader[] = [];
	@Input() filterFileToUploadFn:(file:File)=>string = checkFileSizeTypeFn(null,CONST.MAX_5MB);
	@Output() fileEmitter: EventEmitter<any> = new EventEmitter();
	@Output() deleteEmitter: EventEmitter<number> = new EventEmitter();
	@Output() downloadEmitter: EventEmitter<any> = new EventEmitter();
	//attiva la prima colonna
	@Input() colonnaStatus: boolean=true;
	//attiva la azione download
	@Input() downloadEnable: boolean=true;
	@Input() idFascicolo: any=null; //in alcuni casi è pieno
	private unsubscribe$ = new Subject<void>();
	displayAlert: boolean = false;
	alertMessage: string = '';
	utilsForm: FormGroup;

	public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
    };




	constructor(private allegatiService: AllegatiService,
		//private alertService: ShowAlertService, 
				private autSvc: AutorizzazioniPaesaggisticheService,
				private translateService: TranslateService,
				private formBuilder: FormBuilder) { }

	ngOnInit() 
	{
		this.utilsForm = this.formBuilder.group({ hiddenUpload: new FormControl(null)});
	}

	download(row: PlainTypeNumericId)
	{
		this.downloadEmitter.emit(row);
		/*let obsDownload$=this.autSvc.downloadAllegato(row.id.toString());
		if(this.idFascicolo!=null){
			obsDownload$=this.autSvc.downloadAllegatoFascicolo(this.idFascicolo,row.id.toString());
		}
		obsDownload$.subscribe(result =>
		{
			if (result.ok)
			{
				downloadFile(result.body, row.nome);
			}
		});*/
	}

	uploadFile(event, tipoFile: string)
	{
		if (event.target.files.length > 0)
		{
			const file = event.target.files[0];
			this.utilsForm.reset();
			let result = this.filterFileToUploadFn(file);
			if(result){
			  this.alertMessage = result;
			  this.errorFile();
			}else{
				this.fileEmitter.emit({ file, tipoFile });
				}
		}
	}

	deleteFile(allegatiId: number)
	{
		this.deleteEmitter.emit(allegatiId);
	}

	isUnique(descrizione)
	{
		const item = this.attachmentsPerFileType.filter(item => item.descrizione === descrizione);
		return item.length > 1 ? false : true;
	}

	ngOnDestroy()
	{
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

	isMultitipo(colonna:any){
		return Array.isArray(colonna) && colonna.length>0;
	}


	  /**
 * Open dialog confirm save
 */
public errorFile() {

      this.alertData =
      {
        title: this.translateService.instant('Errore'),
        content: this.translateService.instant(this.alertMessage),
        typ: "Errore",
        isConfirm: true,
        extraData: { operazione: "errorFile" },
        display: true
      };
    
  }



	callbackAlert(event: any) {
		// console.log("Evento callback -> ",JSON.stringify(event));
		this.alertData.isConfirm = false;
		if (event.isChiuso) {
		  this.alertData.display = false;
		}
		else {
		  if (event.extraData && event.extraData.operazione) {
			switch (event.extraData.operazione) {
				case 'Errore': 
				this.alertData.display = true;
				break;
		  }
		  this.alertData.display = false;
		}
	  }
	}
}
