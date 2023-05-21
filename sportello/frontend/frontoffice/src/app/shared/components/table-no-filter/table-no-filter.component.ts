import { Component, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, SimpleChanges, OnChanges, DoCheck } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/core/services/dialog.service';
import { environment } from 'src/environments/environment';
import { BasicFile, DocumentType } from '../../models/allegati.model';
import { FileSizePipe } from '../../pipes';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { IButton } from './../../../core/models/dialog.model';
import { TableConfig } from './../../../core/models/header.model';
import { CONST } from './../../constants';

@Component({
	selector: 'app-table-no-filter',
	templateUrl: './table-no-filter.component.html',
	styleUrls: ['./table-no-filter.component.scss']
})
export class TableNoFilterComponent implements OnInit, OnChanges, DoCheck
{
	@Input("disabled") disableButton: boolean;
	@Input("types") types: DocumentType[];
	@Input("files") files: BasicFile[];
	@Input("headers") tableHeadersAttachment: TableConfig[] = CONST.GENERIC_TABLE;
	@Input("readonly") readonly: boolean = false;
	@Input("enableDelete")enableDelete: boolean = false;
	@Input("disableFilteredType")disableFilteredType: boolean = false;
	@Input("downloadEnable") downloadEnable: boolean = true;

	@Output("onTableUpload") fileEmitter: EventEmitter<any>;
	@Output("onTableDelete") deleteEmitter: EventEmitter<BasicFile>;
	@Output("onTableDownload") downloadEvent: EventEmitter<BasicFile>;
	@Output("onTableCustomEvent") customEvent:EventEmitter<any>;

	private iterableDiffer: IterableDiffer<BasicFile>;
	public myArray: Array<BasicFile> = [];
	public utilsForm: FormGroup;
	public _type: any;

	constructor(private formBuilder: FormBuilder,
				private translate: TranslateService,
				private alert: DialogService,
				private differs: IterableDiffers) 
	{
		this.iterableDiffer = differs.find([]).create(null);
		this.fileEmitter = new EventEmitter<any>();
		this.deleteEmitter = new EventEmitter<BasicFile>();
		this.downloadEvent = new EventEmitter<BasicFile>();
		this.customEvent = new EventEmitter<any>();
	}
	

	ngOnInit(): void 
	{
		this.utilsForm = this.formBuilder.group({ hiddenUpload: new FormControl(null) });
		if(this.readonly === true)
			this.disableButton = true;
	}

	ngOnChanges(changes: SimpleChanges): void { if (changes.files) this.myArray = this.tableContentEvaluate(); }

	ngDoCheck(): void 
	{
		let changes = this.iterableDiffer.diff(this.files);
		if (changes)
		{
			this.myArray = this.tableContentEvaluate();
		}
	}

	public prova()
	{
		console.log("ciao");
	}

	public uploadFile(event: any): void
	{
		let type: DocumentType = this.types.find(f => f.type === this._type);

		// console.log("Stampo accept -> ",JSON.stringify(type.accept[0])) 

		if (type && event.target.files.length > 0)
		{
			let maxFileSize: number = type.maxSize ? type.maxSize : environment.maxSizeUpload;
			const file = event.target.files[0];
			this.utilsForm.reset();
			/* this.utilsForm.get("hiddenUpload").setValue(null); */
			if (file.size < maxFileSize)
			{
				if(type.accept!=null&&type.accept!=undefined){
					let checkType:boolean = true;
					for(let typeOfFile of type.accept){
						if(file.type===typeOfFile){
							this.fileEmitter.emit({ file: file, tipoFile: this._type });
							checkType = false;
							break;
						}
					}
					if(checkType){
						let message = "Il tipo del file scelto non è consentito:"+file.type+" !";
						let title = "Attenzione";
						let buttons: IButton[] = [{id: 0, label: "generic.chiudi"}];
						this.alert.showDialog(true, message, title, buttons, null, DialogType.ERROR, null);
					}
				}else{
					this.fileEmitter.emit({ file: file, tipoFile: this._type });
				}
			} 
			else
			{
				let filesize=new FileSizePipe().transform(environment.maxSizeUpload)
				let message = this.translate.instant('generic.dimensioneAllegatoOversize',{maxUploadSize:filesize});
				let title = "Attenzione";
				let buttons: IButton[] = [{id: 0, label: "generic.chiudi"}];
				this.alert.showDialog(true, message, title, buttons, null, DialogType.ERROR, null);
			}
		}
		this._type = null;
	}

	public customEventMethod(file: BasicFile, eventId: any)
	{
		let eventResponse = { file: file, eventId: eventId };
		this.customEvent.emit(eventResponse);
	}

	public downloadFile(item: BasicFile): void { this.downloadEvent.emit(item); }
	public deleteFile(item: BasicFile): void { 
		this.alert.showDialog(true, 
			'ANNULA.ELEMENTO',
			'ANNULA.TITLE',
			DialogButtons.OK_CANCEL,
			  (buttonID: string): void => {
				if(buttonID=="1"){
				  this.deleteEmitter.emit(item);
				}
			  },
			  DialogType.WARNING,
			  null  );
		//this.deleteEmitter.emit(item); 
	}
	public isUnique(file: BasicFile): boolean { return this.files.some(f => f === file); }
	public isRequired(type: any): boolean { return this.types.find(t => t.type === type).required; }

	private tableContentEvaluate(): BasicFile[]
	{
		let tableContent: BasicFile[] = [];
		if(this.types){
			this.types.forEach(t =>
				{
					let temp: BasicFile = this.files.find((elem:BasicFile) =>  elem.type == t.type);
					if(!this.readonly && !temp)
					{
						temp = new BasicFile();
						temp.type = t.type;
						temp.labelType = t.label;
					}
					if(temp) tableContent.push(temp);
				});
		}else{
			//caso di caricamento senza i tipi predefiniti
			if(this.disableFilteredType){
				tableContent=this.files;
			}
		}
		return tableContent;
	}

	get disableDelete(): boolean 
	{
		return this.disableButton && !this.enableDelete;
	}

	public acceptedType(type: DocumentType): string { 
		return type.accept ? type.accept.join(",") : ""; 
	}

	public evaluatePrinting(row: DocumentType): boolean 
	{ 
		return !this.readonly || 
			   (this.files.length > 0 &&
				this.types.some(t => this.files.some(f => f.type == t.type))); 
	}

	public comileRow(row: DocumentType)
	{
		let temp: BasicFile = this.files.find((elem: BasicFile) => elem.type == row.type);
		if (!this.readonly && !temp)
		{
			/* temp = { name: null, nome: null, type: row.type, labelType: row.label, 
					 uploadDate: null, data: null, formatoFile: null, idCms: null}; */
			temp = new BasicFile();
			temp.type = row.type;
			temp.labelType = row.label;
			temp.name = null;
		}
		return temp;
	}

}
