import { CONST } from './../../constants';
import { IButton } from './../../../core/models/dialog.model';
import { CustomDialogService } from './../../../core/services/dialog.service';
import { TableConfig } from './../../../core/models/header.model';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Component, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, SimpleChanges, DoCheck, OnChanges } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BasicFile, DocumentType } from '../../models/allegati.model';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';

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
	@Input("headers") tableHeadersAttachment?: TableConfig[] = CONST.GENERIC_TABLE;
	@Input("readonly") readonly: boolean = false;
	@Input("enableDelete")enableDelete: boolean = false;
	@Input("disableFilteredType")disableFilteredType: boolean = false;
	@Input("downloadEnable") downloadEnable: boolean = true;
	@Input("confirmDelete") confirmDelete:boolean=false;

	@Input() filterFileToUploadFn = (file:File):string =>{return null;};
	@Output("onTableUpload") fileEmitter: EventEmitter<any>;
	@Output("onTableDelete") deleteEmitter: EventEmitter<BasicFile>;
	@Output("onTableDownload") downloadEvent: EventEmitter<BasicFile>;
	@Output("onTableCustomEvent") customEvent:EventEmitter<any>;

	public utilsForm: FormGroup;
	public _type: any;
	private iterableDiffer: IterableDiffer<BasicFile>;
	public myArray: Array<BasicFile> = [];

	constructor(private formBuilder: FormBuilder,
				private translate: TranslateService,
				private alert: CustomDialogService,
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

	public uploadFile(event: any): void
	{
		let type: DocumentType = this.types.find(f => f.type === this._type);
		if (type && event.target.files.length > 0)
		{
			let maxFileSize: number = type.maxSize ? type.maxSize : 99000000;
			const file = event.target.files[0];
			this.utilsForm.reset();
			if (file.size < maxFileSize)
			{
				let result = this.filterFileToUploadFn(file);
				if(result){
					let message = this.translate.instant('result');
					let title = "Attenzione";
					let buttons: IButton[] = [{id: 0, label: "generic.chiudi"}];
					this.alert.showDialog(true, message, title, buttons, null, DialogType.ERROR, null);
				}else{
					this.fileEmitter.emit({ file: file, tipoFile: this._type });
				}
			} 
			else
			{
				let message = this.translate.instant('OVERSIZED_FILE_MESSAGE');
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
		if(this.confirmDelete){
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
		}else{
			this.deleteEmitter.emit(item); 
		}
	}
	
	public isUnique(file: BasicFile): boolean { return this.files.some(f => f === file); }
	public isRequired(type: any): boolean 
	{ 
		return this.types.find(t => t.type === type).required;
	}


	/* get tableContent(): BasicFile[]
	{
		let tableContent: BasicFile[] = [];
		let _this = this;
		let typeColumn = this.tableHeadersAttachment.find(f => f.type === "type").field;
		_this.types.forEach(t =>
		{
			let invalid: boolean = false;
			let temp: BasicFile|BasicFile[] = [];
			if(t.multiple) 
			{
				temp = _this.files.filter((elem: BasicFile) => elem[typeColumn] == t.type);
				invalid = !temp || temp.length === 0;
			}
			else 
			{
				temp = _this.files.find((elem: BasicFile) => elem[typeColumn] == t.type);
				invalid = !temp;
			}
			if (!_this.readonly && invalid)
			{
				temp = <BasicFile>{};
				temp.type = t[typeColumn];
				temp.formatoFile = t.label;
				temp.nomeFile = null;
				temp["required"] = t.required;
				temp["marked"] = true;
			}
			if(temp)
			{
				if(!temp["marked"] && temp instanceof Array)
					tableContent = [...tableContent, ...temp];
				else 
					tableContent = [...tableContent, <BasicFile>temp];
			}
		});
		return tableContent;
	} */

	private tableContentEvaluate(): BasicFile[]
	{
		let tableContent: BasicFile[] = [];
		if(this.types){
		this.types.forEach(t =>
		{
			let temp: BasicFile = this.files.find((elem: BasicFile) => elem.type == t.type);
				if (!this.readonly && !temp)
				{
					temp = new BasicFile();
					temp.type = t.type;
					temp.labelType = t.label;
				}
				if (temp) tableContent.push(temp);
		});
		}else{
			//caso di caricamento senza i tipi predefiniti
			if(this.disableFilteredType){
				tableContent=this.files;
			}
		}
		return tableContent;
	}

	get disableDelete(): boolean { return this.disableButton && !this.enableDelete; }
	public acceptedType(type: DocumentType): string { return type.accept ? type.accept.join(",") : ""; }
	public labelByType(type: any): string { return this.types && this.types.some(s => s.type == type) ? this.types.find(s => s.type == type).label : type; }
}
