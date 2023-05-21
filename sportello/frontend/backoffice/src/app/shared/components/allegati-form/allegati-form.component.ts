import { documentTypes } from './../../../features/trasmissione-provvedimento-finale/trasmissione-provvedimento-finale-config';
import { Component, DoCheck, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CONST } from '../../constants';
import { BasicFile, DocumentType } from '../../models/allegati.model';
import { TableConfig } from './../../../core/models/header.model';

@Component({
	selector: 'app-allegati-form',
	templateUrl: './allegati-form.component.html',
	styleUrls: ['./allegati-form.component.css']
})
export class AllegatiFormComponent implements OnInit
{
	@Input("header") tableHeadersAttachment: TableConfig[] = CONST.GENERIC_TABLE;
	@Input("types") documentTypes: Array<DocumentType> = [];
	@Input("files") files: BasicFile[];
	@Input("disabled") disable: boolean = false;
	@Input("readonly") readonly: boolean = false;
	@Input("enableTable") enableTable: boolean = true;
	@Input("enableDelete") enableDelete: boolean = false;

	@Output("onUpload") onUpload: EventEmitter<any>;
	@Output("onDelete") onDelete: EventEmitter<BasicFile>;
	@Output("onDownload") onDownload: EventEmitter<BasicFile>
	@Output("onCustomEvent") onCustomEvent: EventEmitter<any>;

	public disabled: boolean = true;
	public uploadForm: FormGroup;
	public disableButton: boolean;

	public errorType: boolean = false;

	constructor(private formBuilder: FormBuilder) 
	{
		this.onDownload = new EventEmitter<BasicFile>();
		this.onDelete = new EventEmitter<BasicFile>();
		this.onUpload = new EventEmitter<any>()
		this.onCustomEvent = new EventEmitter<any>();
	}

	public ngOnInit(): void 
	{
		this.uploadForm = this.formBuilder.group
		({
			myfile: [null, Validators.required],
			uploadType: [null, Validators.required]
		});
	}

	private validType(documentType: DocumentType): boolean
	{
		return !this.files || documentType.multiple || this.files.every(f => f.type != documentType.type);
	}

	public onFileSelect(event: any): void
	{
		if (!this.errorType && this.uploadForm)
		{
			let file: File = event.files[0];
			this.uploadForm.get("myfile").setValue(file);
			if (this.uploadForm.valid)
			{
				let uploadEvent = { file: file, type: this.uploadType.type };
				this.onUpload.emit(uploadEvent);
				this.uploadForm.reset();
				this.disabled = true;
			}
			else
				console.error("Type must be selected!");
		}
		else
		{
			let errorMessage = "Selezione non valida";
			if(this.uploadType)
				errorMessage = "Per la tipologia '" + this.uploadType.label + "' non è possibile caricare più di un allegato!!";
			console.error(errorMessage);
		}
	}

	public selectFileType(): void 
	{
		if (this.uploadType)
			this.errorType = !this.validType(this.uploadType);
		else
			this.errorType = false;
		this.disabled = this.uploadType === null || this.errorType;
	}

	public uploadFileFromTable(event: any)
	{
		const file = event.file;
		const tipoFile = event.tipoFile;
		let uploadEvent = { file: file, type: tipoFile };
		this.uploadForm.patchValue({ uploadType: null });
		this.disabled = true;
		this.onUpload.emit(uploadEvent);
	}

	public downloadFormTable(event: BasicFile): void { this.onDownload.emit(event); }
	public deleteFileFromTable(event: BasicFile): void 
	{ 
		this.onDelete.emit(event);
		this.uploadForm.patchValue({ uploadType: null }); 
		this.selectFileType();
	}
	public throwsCustomEvent(event: any): void { this.onCustomEvent.emit(event); }
	public acceptedType(type: DocumentType): string { return type.accept ? type.accept.join(",") : ""; }

	get uploadType(): any | null { return this.uploadForm.get('uploadType').value; }
	get selectedType(): DocumentType | null { return this.uploadType ? this.documentTypes.find(t => t.type === this.uploadType) : null; }
	get acceptedMime(): string { return this.selectedType && this.selectedType.accept ? this.selectedType.accept.join(',') : ''; }
	get maxSizeAccepted(): number { return this.selectedType && this.selectedType.maxSize ? this.selectedType.maxSize : 99000000; }
	get emptyDropdown(): boolean { return !this.documentTypes || this.documentTypes.every(t => !t.multiple && this.files.some(s => s.type == t.type)); }
}