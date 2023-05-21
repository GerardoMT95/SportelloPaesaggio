import { Component, Input, OnDestroy, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { CONST } from 'src/shared/constants';
import { TableHeader } from 'src/shared/models/table-header';
import { RegistrationStatus } from '../../model/entity/fascicolo.models';
import { PlainTypeNumericId } from '../../model/plain-type-numeric-id.model';
import { InformazioniDTO } from './../../model/entity/info.models';
import { checkFileSizeTypeFn, downloadFile, saveFileToLocalStorage } from '../../functions/genericFunctions';

@Component({
	selector: 'app-allegati-form',
	templateUrl: './allegati-form.component.html',
	styleUrls: ['./allegati-form.component.css']
})

export class AllegatiFormComponent implements OnInit, OnDestroy {
	@Input() tableHeadersAttachment: TableHeader[];
	@Input() checkFileType = (file:File):string =>{return null;};
	@Input() dettaglioFascicolo: InformazioniDTO;
	@Input() disable: boolean;
	@Input() sezione: string;//provvedimento oppure allegati --> ulteriore documentazione?
	@Output() avviso: EventEmitter<string> = new EventEmitter<string>(); /* per avvisare l'utente di qualcosa */

	uploadForm: FormGroup;
	attachmentsPerFileType: PlainTypeNumericId[] = [];
	fileTypeList: SelectItem[] = [];
	disableButton: boolean;
	attachmentId: number;
	disabled: boolean = true;
	allegatiId: number;

	private unsubscribe$ = new Subject<void>();

	constructor(private allegatiService: AllegatiService,
		private autPaesService: AutorizzazioniPaesaggisticheService,
		private alertService: ShowAlertService,
		private translateService: TranslateService,
		private promptDialogService: ShowPromptDialogService,
		private formBuilder: FormBuilder) { }

	ngOnInit() {
		this.planAttachments();
		this.uploadForm = this.formBuilder.group
			({
				myfile: [''],
				uploadType: ['']
			});
		if (this.dettaglioFascicolo.stato != RegistrationStatus.WORKING) {
			this.disableButton = true;
			this.uploadForm.disable();
		}
		else {
			this.disableButton = false;
			this.uploadForm.enable();
		}
		this.autPaesService.getTipoProcedimento$().subscribe(newval => {
			console.log(newval);
			this.planAttachments();
		});
	}

	onFileSelect(event: any) {
		if (event.files.length > 0) {
			const file = event.files[0];
			let errorValidation=this.checkFileType(file);
			if(errorValidation){
				this.alertService.showAlertDialog(true, null, this.translateService.instant(errorValidation));
				return;
			}
			let tipoFile = this.uploadForm.get("uploadType").value;
			if (tipoFile.value)
				tipoFile = tipoFile.value;
			if (tipoFile) {
				const formData = new FormData();
				formData.append('file', file);
				if (this.sezione === "provvedimento") {
					this.autPaesService.saveProvvedimento(this.dettaglioFascicolo.id.toString(), tipoFile, formData).pipe(takeUntil(this.unsubscribe$)).subscribe(sub => {
						if (sub.codiceEsito === CONST.OK) {
							saveFileToLocalStorage(localStorage, sub.payload, tipoFile, this.dettaglioFascicolo.id.toString(), this.sezione);
							this.planAttachments();
							this.selectFileType();
						}
					});
				}
				else if (this.sezione === "allegati") {
					let infoAllegato={idFascicolo:this.dettaglioFascicolo.id,tipiAllegato:tipoFile}
					formData.append('infoAllegato', new Blob([JSON.stringify(infoAllegato)], { type: "application/json" }));
					this.autPaesService.saveAllegatiMultitipo(formData).pipe(takeUntil(this.unsubscribe$)).subscribe(sub => {
						if (sub.codiceEsito === CONST.OK) {
							saveFileToLocalStorage(localStorage, sub.payload, tipoFile, this.dettaglioFascicolo.id.toString(), this.sezione);
							this.uploadForm.get("uploadType").setValue(null);
							this.selectFileType();
							this.planAttachments();
						}
					});
				}
				else {
					this.autPaesService.saveAllegati(this.dettaglioFascicolo.id.toString(), tipoFile, formData).pipe(takeUntil(this.unsubscribe$)).subscribe(sub => {
						if (sub.codiceEsito === CONST.OK) {
							saveFileToLocalStorage(localStorage, sub.payload, tipoFile, this.dettaglioFascicolo.id.toString(), this.sezione);
							this.planAttachments();
						}
					});
				}
			}
		}
	}

	selectFileType() {
		if (this.sezione == "allegati") {
			if (Array.isArray(this.uploadForm.get('uploadType').value) &&
				this.uploadForm.get('uploadType').value.length > 0) {
				this.disabled = false;
			} else {
				this.disabled = true;
			}
		}
		else if (this.sezione == "provvedimento") {
			//se la lista degli allegati è già piena disabilito l'upload....un file per tipologia
			if (this.uploadForm.get('uploadType').value === null||
				this.attachmentsPerFileType.findIndex(el=>el.id==null)<0) {
				this.disabled = true;
			} else {
				this.disabled = false;
			}
		}else {
			
			if (this.uploadForm.get('uploadType').value === null) {
				this.disabled = true;
			} else {
				this.disabled = false;
			}
		}

	}

	prepareDataForTableAndDropdown(allAttachments) {
		allAttachments.forEach(item => {
			//fill table .. non più, visto che l'upload è ammesso solo via p-fileupload
			if(this.sezione!="allegati"){
				this.attachmentsPerFileType.push
				({
					descrizione: CONST.isPutt()?this.translateService.instant('tipo_allegato_putt.'+item.type):item.nome,
					nome: '',
					id: item.id,
					data: item.data,
					mandatario: item.descrizione === 'mandatario' ? true : false,
					type: item.type
				});
			}
			//fill dropdown
			this.fileTypeList.push
				({
					label: CONST.isPutt()?this.translateService.instant('tipo_allegato_putt.'+item.type):item.nome,
					value: item.type,
					title: item.descrizione
				});
		});
	}

	populatePlanAttachmentsTable(planAttachments) {
		let _this = this;
		//ripulisco la localstorage....
		/*for(let i=0;i<localStorage.length;i++){
			let key=localStorage.key(i);
			if(key.startsWith('ATTACH#' + this.dettaglioFascicolo.id + '#' + this.sezione + '#')){
				localStorage.removeItem(key);
			}
		}*/
		if(this.sezione==="allegati"){
			planAttachments.forEach(item => {
					_this.attachmentsPerFileType.push(item);
			});
		}else{
			planAttachments.forEach(item => {
				_this.attachmentsPerFileType.forEach((element, index) => {
					if (element.type === item.type)
						element.nome ? _this.attachmentsPerFileType.push(item) : (_this.attachmentsPerFileType[index] = item);
				});
			});
		}
		
		_this.attachmentsPerFileType.sort((a, b) => {
			return a.descrizione > b.descrizione ? 1 : -1
		});
	}

	/**
	 * se sezione==allegati ammesso solo l'upload dal p-fileupload
	 */
	uploadFileFromTable(event: any) {
		const file:File = event.file;
		const tipoFile = event.tipoFile;
		const formData = new FormData();
		formData.append('file', file);
		if (this.sezione === "provvedimento") {
			//disattivato check su provvedimento, vogliono caricare anche p7m
			let errorValidation=this.checkFileType(file);
			if (!errorValidation) {
				this.autPaesService.saveProvvedimento(this.dettaglioFascicolo.id.toString(), tipoFile, formData).pipe(takeUntil(this.unsubscribe$)).subscribe(sub => {
					if (sub.codiceEsito === CONST.OK) {
						saveFileToLocalStorage(localStorage, sub.payload, tipoFile, this.dettaglioFascicolo.id.toString(), this.sezione);
						this.planAttachments();
					}
				});
			}
			else {
				this.avviso.emit("Sono consentiti solo file in formato PDF");
			}
		}
		else {
			this.autPaesService.saveAllegati(this.dettaglioFascicolo.id.toString(), tipoFile, formData).pipe(takeUntil(this.unsubscribe$)).subscribe(sub => {
				if (sub.codiceEsito === CONST.OK) {
					saveFileToLocalStorage(localStorage, sub.payload, tipoFile, this.dettaglioFascicolo.id.toString(), this.sezione);
					this.planAttachments();
				}
			});
		}
	}

	/**
	 * cliccato sull'icona e devo chiedere conferma
	 * @param event 
	 */
	deleteFileFromTable(event: any) {
		this.promptDialogService.showDeleteAllegatiDialog(true, this.sezione);
		this.allegatiId = event;
	}

	planAttachments() {
		this.attachmentsPerFileType = [];
		this.fileTypeList = [];
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

	/**
	 * sull'evento di conferma nella dialog
	 * @param event 
	 */
	deleteAllegati(event: any) {
		//eliminaAllegato
		this.autPaesService.eliminaAllegato(this.allegatiId.toString(),this.dettaglioFascicolo.id+'').pipe(takeUntil(this.unsubscribe$)).subscribe(response => {
			this.promptDialogService.showDeleteAllegatiDialog(false, this.sezione);
			if (response.codiceEsito === CONST.OK) {
				this.alertService.showAlertDialog(true, null, this.translateService.instant("ATTACHMENT_DELETE_BUTTON_MESSAGE"));
				let key = 'ATTACH#' + this.dettaglioFascicolo.id + '#' + this.sezione + '#' + this.allegatiId.toString();
				localStorage.removeItem(key);
				this.planAttachments();
				this.selectFileType();
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

	hasItemEndingWith(option:SelectItem[],endString:string){
		return option.findIndex(item=>item.label.lastIndexOf(endString)>0)>=0;
	}

	optionLabelWithStar(option:SelectItem){
		return option.title=="mandatario"?option.label+'*':option.label;
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
