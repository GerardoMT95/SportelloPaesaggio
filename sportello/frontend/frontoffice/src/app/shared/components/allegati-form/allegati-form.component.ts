import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BasicFile, DocumentType } from '../../models/allegati.model';
import { TableConfig } from './../../../core/models/header.model';
import { isNullOrUndefined } from 'util';
import { CONST } from '../../constants';

@Component({
	selector: 'app-allegati-form',
	templateUrl: './allegati-form.component.html',
	styleUrls: ['./allegati-form.component.css']
})
export class AllegatiFormComponent<T extends BasicFile> implements OnInit 
{
	@Input("header") tableHeadersAttachment: TableConfig[];
	@Input("types") documntTypes: DocumentType[];
	@Input("files") files: T[];
	@Input("disabled") disable: boolean = false;
	@Input("readonly") readonly: boolean = false;
	@Input("enableTable") enableTable: boolean = true;
	@Input("enableDelete") enableDelete: boolean = false;

	@Output("onUpload") onUpload: EventEmitter<any>;
	@Output("onDelete") onDelete: EventEmitter<T>;
	@Output("onDownload") onDownload: EventEmitter<T>
	@Output("onCustomEvent") onCustomEvent: EventEmitter<any>;

	public disabled: boolean = true;
	public uploadForm: FormGroup;
	public disableButton: boolean;

	constructor(private formBuilder: FormBuilder) 
	{
		this.onDownload = new EventEmitter<T>();
		this.onDelete = new EventEmitter<T>();
		this.onUpload = new EventEmitter<any>()
		this.onCustomEvent = new EventEmitter<any>();
	}

	ngOnInit() 
	{
		if (isNullOrUndefined(this.tableHeadersAttachment))
			this.tableHeadersAttachment = CONST.GENERIC_TABLE;
		this.uploadForm = this.formBuilder.group
		({
			myfile: [null, Validators.required],
			uploadType: [null, Validators.required]
		});
	}

	get uploadType(): any | null { return this.uploadForm.get('uploadType').value; }
	get selectedType(): DocumentType|null { return this.uploadType ? this.documntTypes.find(t => t.type === this.uploadType) : null; }
	get acceptedMime(): string { return this.selectedType && this.selectedType.accept ? this.selectedType.accept.join(',') : ''; }
	get maxSizeAccepted(): number { return this.selectedType && this.selectedType.maxSize ? this.selectedType.maxSize : 99000000; }

	public onFileSelect(event: any): void
	{
		let file: File = event.files[0];
		this.uploadForm.get("myfile").setValue(file);
		if(this.uploadForm.valid)
		{
			let uploadEvent = { file: file, type: this.uploadForm.get("uploadType").value.type }; 
			this.onUpload.emit(uploadEvent);
			this.uploadForm.reset();
		}
		else
			console.error("Type must be selected!");
	}

	public selectFileType(): void { this.disabled = this.uploadForm.get('uploadType').value === null }

	public uploadFileFromTable(event: any)
	{
		const file = event.file;
		const tipoFile = event.tipoFile;
		let uploadEvent = { file: file, type: tipoFile };
		this.onUpload.emit(uploadEvent);
	}

	public downloadFormTable(event: T): void { this.onDownload.emit(event); }
	public deleteFileFromTable(event: T): void { this.onDelete.emit(event); }
	public throwsCustomEvent(event: any): void { this.onCustomEvent.emit(event); }
	public acceptedType(type: DocumentType): string { return type.accept ? type.accept.join(",") : ""; }
}
