import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { AllegatoService } from 'src/app/services/allegato.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { CONST } from 'src/shared/constants';
import { PlanFormField } from 'src/shared/models/plan-form.model';
import { TableHeader } from 'src/shared/models/table-header';
import { FascicoloFormConfig } from '../form-fascicolo/form-plan-config';
import { checkFileSizeTypeFn, downloadFile } from '../functions/genericFunctions';
import { PlaceholderDoc, TemplateDocDTO, TipoSezione } from '../model/entity/admin.models';
import { TemplateDocSearch } from '../model/entity/fascicolo.models';
import { PlainTypeNumericId } from '../model/plain-type-numeric-id.model';

@Component({
  selector: 'app-modifica-template-documentazioni',
  templateUrl: './modifica-template-documentazioni.component.html',
  styleUrls: ['./modifica-template-documentazioni.component.css']
})
export class ModificaTemplateDocumentazioniComponent implements OnInit {

  public rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;
  public pages: SelectItem[] = CONST.PAGINAZIONE;
  public page: number = 1;
  public dettaglio: boolean = false;
  public dettaglioTemplate: TemplateDocDTO;
  public itemInEdit: TemplateDocDTO;
  public formDettaglio: FormGroup;
  public const = CONST;

  public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
    };

  public disable: boolean = false;
  public attachmentsPerFileType: Map<string, PlainTypeNumericId[]> = new Map<string, PlainTypeNumericId[]>();
  public tableHeadersAttachment: TableHeader[];
  private unsubscribe$ = new Subject<void>();
  public itemsTotal: number = 0;
  public filter: TemplateDocSearch;

  public data: TemplateDocDTO[];
  public allegatoId: any;
  protected checkFileSizeType=checkFileSizeTypeFn('image/png',100*1024);

  constructor(public allegatiService: AllegatoService,
    public loadingService: LoadingService,
    private promptDialogService: ShowPromptDialogService,
    private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private adminService: AdminService,
    private autPaesService: AutorizzazioniPaesaggisticheService) { }

  ngOnInit() {
    this.filter = new TemplateDocSearch();
    this.getTemplatePaginated();
    // console.log("Stampo data -> " + JSON.stringify(this.data));
    this.tableHeadersAttachment = FascicoloFormConfig.allegatiUploadTableHeaders();
  }


  private getTemplatePaginated(){
    this.filter.limit = this.rowsOnPage;
    this.filter.page = this.page;
    this.filter.direzione = "ASC";
    this.filter.colonna = "nome";
    this.adminService.getAllDocPaginated(this.filter).subscribe(res => {
      if(res.codiceEsito === CONST.OK){
        // console.log("Stampo la res della paginazione -> "+ JSON.stringify(res.payload.list));
        this.data = res.payload.list;
       this.itemsTotal = res.payload.count;
      }
    });
  }

  /**
 * @description Cambia il numero di righe per pagina della tabella
 * @param rowsOnPage 
 */
  public changeRows(rowsOnPage: number): void {
    this.rowsOnPage = rowsOnPage;
    this.getTemplatePaginated();
    // this.page = 1;
    //richiama ricerca
  }


  public openDettaglio(dataInput) {
     this.getInfo(dataInput.nome);
    //  this.dettaglio = true;
  }

/* Preleviamo le info per il template */
  private getInfo(nomeTemplate: string){
     this.adminService.getInfoDoc(nomeTemplate).subscribe(res =>{
      if(res.codiceEsito === CONST.OK){
        // console.log("Stampiamo la risposta dell'info -> "+ JSON.stringify(res.payload));
        this.itemInEdit = res.payload;
        this.buildFormDettaglio();
        this.dettaglio = true;
      }
    });
  }


  buildFormDettaglio() {
    this.itemInEdit.sezioni.forEach(el => {
      if(el.allegato){
        let allegato = [];
          allegato.push({
          descrizione: el.allegato.descrizione,
          nome: el.allegato.nome,
          id: el.allegato.id,
          data: el.allegato.dataCaricamento,
          mandatario: true,
          type: el.allegato.tipo
        });
        this.attachmentsPerFileType.set(el.nome, allegato);
      }
      else{
        let allegatoArray = [];
        allegatoArray.push({
          descrizione: el.nome,
          nome: '',
          id: null,
          data: null,
          mandatario: true,
          type: el.nome
        });
        this.attachmentsPerFileType.set(el.nome, allegatoArray)
      }
    });

    let formField: PlanFormField[] = [];
    let defaultData = {};
    this.itemInEdit.sezioni.forEach(el => {
      if (el.tipoSezione != TipoSezione.IMAGE) {
        formField.push({ field: el.nome, label: null, validator: [Validators.required,Validators.maxLength(this.const.MAX_LEN_TEXT_TEMPLATE_DOCUMENT)] });
        defaultData[el.nome] = el.valore;
      }
    });
    this.formDettaglio = this.formBuilder.group({
      ...FascicoloFormConfig.prepareFormBuilder(formField, defaultData)
    });
  }

  caricaDocumento($event: { file: any, tipoFile: string }) {
    this.loadingService.emitLoading(true);
    if ($event.file) {
      const file: File = $event.file;
      const formData: FormData = new FormData();
      let sezioneInUpload = this.itemInEdit.sezioni.find(sezione => sezione.nome == $event.tipoFile);
      formData.append('file', file);
      formData.append("sezione", new Blob([JSON.stringify(sezioneInUpload)], { type: 'application/json' }));
      this.allegatiService.uploadAllegatoDocumento(formData, '/templateDoc/carica')
        .then((response) => {
          let ret = response.payload;
          sezioneInUpload.allegato = ret;
          let allegato: any = {
            descrizione: ret.descrizione,
            nome: ret.nome,
            id: ret.id,
            data: ret.dataCaricamento,
            mandatario: true,
            type: sezioneInUpload.nome
          };
          this.attachmentsPerFileType.set(sezioneInUpload.nome, [allegato]);
        })
    }
  }

  /**
 * cliccato sull'icona e devo chiedere conferma
 * in event viene passato il number idAllegato
 * @param event 
 */
  deleteFileFromTable(event: any) {
    this.promptDialogService.showDeleteAllegatiDialog(true, 'adminTemplateDoc');
    this.allegatoId = event;
  }

  deleteImage($event) {
    let _this=this;
    this.adminService.eliminaImage(this.allegatoId + "").pipe(takeUntil(this.unsubscribe$)).subscribe(response => {
      this.promptDialogService.showDeleteAllegatiDialog(false, 'adminTemplateDoc');
      if (response.codiceEsito === CONST.OK) {
        _this.alertData =
        {
          title: 'Informazioni',
          content: 'Immagine cancellata correttamente',
          typ: "info",
          isConfirm: false,
          extraData: {},
          display: true
        };
        //cerco in sezioni la sezione che contiene .allegato.id=this.allegatoId
        let sezione = _this.itemInEdit.sezioni.find(sezione => sezione.allegato && sezione.allegato.id == this.allegatoId);
        if (sezione) {
          let newAllegatoValues = []
          newAllegatoValues.push({
            descrizione: sezione.nome,
            nome: '',
            id: null,
            data: null,
            mandatario: true,
            type: sezione.nome
          });
          _this.attachmentsPerFileType.set(sezione.nome, newAllegatoValues);
        }
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


  /**
 * Open dialog confirm save
 */
  public openSalva() {
    this.formDettaglio.updateValueAndValidity();
    if (this.formDettaglio.valid) {
      this.alertData =
      {
        title: this.translateService.instant('templateDocumentazioni.dialog.salva.title'),
        content: this.translateService.instant('templateDocumentazioni.dialog.salva.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "save" },
        display: true
      };
    }else{
      this.alertData =
      {
        title: this.translateService.instant('templateDocumentazioni.dialog.invalid.title'),
        content: this.translateService.instant('templateDocumentazioni.dialog.invalid.content'),
        typ: "danger",
        isConfirm: false,
        extraData: null,
        display: true
      };
    }
  }


  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    }
    else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        let template: any = this.formDettaglio.getRawValue();
        console.log("Stampo il template --> " + JSON.stringify(template));
        switch (event.extraData.operazione) {
          case 'save':
            //riverso i valori dal form sull'oggetto in edit...
            this.itemInEdit.sezioni.forEach(el => {
              if (template[el.nome]) {
                el.valore = template[el.nome];
              }
            });
            this.onAction(this.itemInEdit);
            break;
          case 'resetTemplate':
            this.resettaTemplate(this.itemInEdit.nome);
            break;
          case 'goBack':
            this.dettaglio = false
            // console.log("Stampo il dettaglio --> "+ this.dettaglio);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }


  public onAction(event: TemplateDocDTO) {
    console.log("event ", event);
    if (event) {
      //chiamata al BE per l'update
      this.adminService.saveTemplateDoc(event).subscribe(response => {
        if (response.codiceEsito === CONST.OK) {
          this.alertData =
          {
            title: "Successo",
            content: "Template salvato con successo",
            typ: "success",
            display: true,
            extraData: null,
            isConfirm: false
          };
          let index = this.data.map(m => m.nome).indexOf(event.nome);
          this.data[index] = event;
          // this.dettaglio = false;
        }
      });
    }
    else
      this.dettaglio = null;
  }

  public resetTemplate(): void {
    this.alertData =
    {
      content: "Sei sicuro di voler ritornare al template originale? tutte le modifiche effettuate saranno perse.",
      title: "Attenzione",
      typ: "warning",
      extraData: { operazione: "resetTemplate" },
      isConfirm: true,
      display: true
    }
  }


  private resettaTemplate(nome: string): void {
    this.loadingService.emitLoading(true);
    this.adminService.resetTemplateDoc(nome).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        this.dettaglioTemplate = response.payload;
        this.getInfo(nome);
      }
      this.loadingService.emitLoading(false);
    });
  }

  public back(): void {
    if(this.formDettaglio.dirty){
      this.alertData =
      {
        title: this.translateService.instant('templateComunicazioni.dialog.back.title'),
        content: this.translateService.instant('templateComunicazioni.dialog.back.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "goBack" },
        display: true
      };  
    }else{
      this.dettaglio=false;
      return;
    }
    
  }


  /*public checkFileSizeType(file:File):string{
    return  checkFileSizeTypeFn('image/png',100*1024);
    if(file.type!='image/png'){
       return "Errore: tipo non supportato.."
    }
    if(file.size>=100*1024){
      return "Errore: file supera dimensione..";
    }
      return null;
    }*/

    public pageChangedAction(pageNumber: number): void
    {
      this.page = pageNumber;
      this.getTemplatePaginated();
      //richiama ricerca
    }


    public downloadAnteprima(){
      this.adminService.getPreview(this.itemInEdit.nome).subscribe(res => {
        if(res.ok){
          downloadFile(res.body, this.itemInEdit.nome+".pdf");
        }
      });
    }


      /**
   * Open dialog info editor
   */
  public openInfo(placeholders: PlaceholderDoc[]): void
  {
    var placeholderString: string = "";
    placeholders.forEach(placeholder => {
     placeholderString = placeholderString.concat("<b>{",placeholder.codice,"}</b>: ", placeholder.info,"<br/>");
    });
    this.alertData =
    {
      title: "Lista dei placeholder ammessi",
      content: placeholderString ,
      typ: "info",
      isConfirm: false,
      extraData: { operazione: "openInfo" },
      display: true
    };
  }

  download(row: PlainTypeNumericId)
	{
		let obsDownload$=this.adminService.downloadImage(row.id.toString());
		obsDownload$.subscribe(result =>
		{
			if (result.ok)
			{
				downloadFile(result.body, row.nome);
			}
		});
	}

}
