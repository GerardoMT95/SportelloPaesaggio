import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { IButton } from 'src/app/core/models/dialog.model';
import { TableConfig } from 'src/app/core/models/header.model';
import { DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { PlainTypeStringId } from 'src/app/shared/components/model/logged-user';
import { CONST } from 'src/app/shared/constants';
import { TableHeader } from 'src/app/shared/models/table-header';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { HttpAllegatoService } from 'src/app/shared/services/http-allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { TemplateDocDTO, TemplateDocSearch, TipoSezione } from '../../models/template-doc-model';

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
  //public attachmentsPerFileType: Map<string, PlainTypeStringId[]> = new Map<string, PlainTypeStringId[]>();
  public attachmentsPerFileType= {} ;//conterrà nome sezione /array di destinatari
  public tableHeadersAttachment: TableConfig[];
  private unsubscribe$ = new Subject<void>();
  public itemsTotal: number = 0;
  public filter: TemplateDocSearch;

  public data: TemplateDocDTO[];
  public allegatoId: any;

  tipoLogo=[
    {
        label: "Logo template",
        multiple: false,
        type: "Logo",          
        required: true 
    }];

  constructor(
    public loadingService: LoadingService,
    private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private adminService: AdminFunctionsService,
    private allegatiService: HttpAllegatoService) { }

  ngOnInit() {
    this.filter = new TemplateDocSearch();
    this.getTemplatePaginated();

    this.tableHeadersAttachment = [
      {
          field: "type",
          header: "Tipologia",
          type: "type"
      },
      {
          field: "nome",
          header: "Nome file",
          type: "text"
      },
      {
          field: "data",
          header: "Data caricamento",
          type: "date"
      }
  ];
  }


  private getTemplatePaginated() {
    this.filter.limit = this.rowsOnPage;
    this.filter.page = this.page;
    this.filter.direzione = "ASC";
    this.filter.colonna = "nome";
    this.loadingService.emitLoading(true);
    this.adminService.getAllDocPaginated(this.filter).subscribe(res => {
      if (res.codiceEsito === CONST.OK) {
        this.data = res.payload.list;
        this.itemsTotal = res.payload.count;
        this.loadingService.emitLoading(false);
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
  private getInfo(nomeTemplate: string) {
    this.loadingService.emitLoading(true);
    this.adminService.getInfoDoc(nomeTemplate).subscribe(res => {
      this.loadingService.emitLoading(false);
      if (res.codiceEsito === CONST.OK) {
        // console.log("Stampiamo la risposta dell'info -> "+ JSON.stringify(res.payload));
        this.itemInEdit = res.payload;
        this.buildFormDettaglio();
        this.dettaglio = true;
      }
    });
  }


  buildFormDettaglio() {
    this.itemInEdit.sezioni.forEach(el => {
      if (el.allegato) {
        let allegato = [];
        allegato.push({
          descrizione: el.allegato.descrizione,
          nome: el.allegato.nome,
          id: el.allegato.id,
          data: el.allegato.data,
          mandatario: true,
          type: el.allegato.descrizione
        });
        //this.attachmentsPerFileType.set(el.nome, allegato);
        this.attachmentsPerFileType[el.nome]=allegato;
      }
      else {
        let allegatoArray = [];
        allegatoArray.push({
          descrizione: el.nome,
          nome: '',
          id: null,
          data: null,
          mandatario: true,
          type: el.nome
        });
        this.attachmentsPerFileType[el.nome]=allegatoArray;
        //this.attachmentsPerFileType.set(el.nome, allegatoArray)
      }
    });

    let formDef = {};
    this.itemInEdit.sezioni.forEach(el => {
      if (el.tipoSezione != TipoSezione.IMAGE) {
        formDef[el.nome] = [el.valore, [Validators.required]];
      }
    });
    this.formDettaglio = this.formBuilder.group(formDef);
  }

  caricaDocumento($event: { file: any, fileType: string }) {
    if ($event.file) {
      const file: File = $event.file;
      const formData: FormData = new FormData();
      let sezioneInUpload = this.itemInEdit.sezioni.find(sezione => sezione.nome == $event.fileType);
      formData.append('file', file);
      formData.append("sezione", new Blob([JSON.stringify(sezioneInUpload)], { type: 'application/json' }));
      this.loadingService.emitLoading(true);
      this.adminService.uploadLogo(formData)
        .then((response) => {
          this.loadingService.emitLoading(false);
          let ret = response.payload;
          sezioneInUpload.allegato = ret;
          let allegato: any = {
            descrizione: ret.descrizione,
            nome: ret.nome,
            id: ret.id,
            data: ret.data,
            mandatario: true,
            type: sezioneInUpload.nome
          };
          //this.attachmentsPerFileType.set(sezioneInUpload.nome, [allegato]);
          this.attachmentsPerFileType[sezioneInUpload.nome]=[allegato];
        })
    }
  }

  deleteImage(allegatoId:string) {
    this.loadingService.emitLoading(true);
    //let _this=this;
    this.adminService.eliminaImage(allegatoId).pipe(takeUntil(this.unsubscribe$)).subscribe(response => {
      //this.promptDialogService.showDeleteAllegatiDialog(false, 'adminTemplateDoc');
      this.loadingService.emitLoading(false);
      if (response.codiceEsito === CONST.OK) {
        //cerco in sezioni la sezione che contiene .allegato.id=this.allegatoId
        let sezione = this.itemInEdit.sezioni.find(sezione => sezione.allegato && sezione.allegato.id+"" == allegatoId);
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
          //this.attachmentsPerFileType.set(sezione.nome, newAllegatoValues);
          this.attachmentsPerFileType[sezione.nome]= newAllegatoValues;
        }
        this.alertData =
        {
          title: 'generic.info',
          content: 'generic.operazioneOk',
          typ: "info",
          isConfirm: false,
          extraData: {},
          display: true
        };
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
    if (event) {
      //chiamata al BE per l'update
      this.loadingService.emitLoading(true);
      this.adminService.saveTemplateDoc(event).subscribe(response => {
        this.loadingService.emitLoading(false);
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
    this.alertData =
    {
      title: this.translateService.instant('templateComunicazioni.dialog.back.title'),
      content: this.translateService.instant('templateComunicazioni.dialog.back.content'),
      typ: "info",
      isConfirm: true,
      extraData: { operazione: "goBack" },
      display: true
    };
  }


  public checkFileSizeType(file: File): string {
    if (file.type != 'image/png') {
      return "Errorre: tipo non supportato.."
    }
    console.log(file);
    if (file.size >= 100 * 1024) {
      return "Errore: file supera dimensione..";
    }
    return null;
  }

  public pageChangedAction(pageNumber: number): void {
    this.page = pageNumber;
    this.getTemplatePaginated();
  }


  public downloadAnteprima() {
    this.loadingService.emitLoading(true);
    this.adminService.getPreview(this.itemInEdit.nome).subscribe(res => {
      this.loadingService.emitLoading(false);
      if (res.ok) {
        downloadFile(res.body, this.itemInEdit.nome + ".pdf");
      }
    });
  }

  /**
* Open dialog info editor
*/
  public openInfo(placeholders: PlainTypeStringId[]): void {
    var placeholderString: string = "";
    placeholders.forEach(placeholder => {
      placeholderString = placeholderString.concat("<b>{", placeholder.value, "}</b>: ", placeholder.description, "<br/>");
    });
    this.alertData =
    {
      title: "templateDocumentazioni.dialog.placeholder.ammessi",
      content: placeholderString,
      typ: "info",
      isConfirm: false,
      extraData: {},
      display: true
    };
  }

  public download(event: any): void {
    this.loadingService.emitLoading(true);
    this.adminService.callDownloadLogo(event.id).subscribe(result => {
      this.loadingService.emitLoading(false);
      if (result.status == 200) {
        downloadFile(result.body, event.name);
      }
      else {
        this.alertData =
        {
          title: "generic.error",
          content: "Errore durante il download dell'allegato",
          typ: "error",
          isConfirm: false,
          extraData: {},
          display: true
        };
      }
    });
  }

}
