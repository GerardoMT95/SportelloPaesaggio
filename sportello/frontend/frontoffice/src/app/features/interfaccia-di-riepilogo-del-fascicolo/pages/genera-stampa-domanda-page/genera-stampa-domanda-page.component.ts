import { HttpResponse } from '@angular/common/http';
import { CommentStmt } from '@angular/compiler';
import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { paths } from 'src/app/app-routing.module';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { ButtonType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { downloadFile } from 'src/app/shared/functions/generic.utils';
import { DocumentType } from 'src/app/shared/models/allegati.model';
import { Allegato, AttivitaDaEspletareEnum, Fascicolo } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { DialogButtons } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../shared/constants';
import { BFile } from './../../../../shared/models/allegati.model';
import { RequestAllegato } from './../../../../shared/models/models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { DichiarazioniService } from './../../../compilazione-istanza/services/dichiarazioni/dichiarazioni.service';

@Component({
  selector: 'app-genera-stampa-domanda-page',
  templateUrl: './genera-stampa-domanda-page.component.html',
  styleUrls: ['./genera-stampa-domanda-page.component.scss'],
})
export class GeneraStampaDomandaPageComponent implements OnInit
{
  generatedDocuments: Allegato[];
  codiceFascicolo: string;
  subscribedDocuments: Allegato[] = [];
  fcIsSigned:FormControl=new FormControl();
  

  public domande: any[] =
  [
    { "descrizione": "ISTANZA", "nome": "Istanza", },
    { "descrizione": "SCHEDA", "nome": "Dichiarazione tecnica" }
  ];

  fascicoloLoaded$=new EventEmitter<boolean>();
  public files: BFile[] = [];
  public types: DocumentType[] = [
    { label: "Istanza", type: 700, required: true, multiple: false, accept: [...CONST.mimePDF] },
    { label: "Dichiarazione tecnica", type: 701, required: true, multiple: false, accept: [...CONST.mimePDF] },
  ]

  public fascicolo: Fascicolo;

  constructor(private route: ActivatedRoute,
              private store: FascicoloStore,
              private router: Router,
              private dialogService: DialogService,
              private loading: LoadingService,
              private service: FascicoloService,
              private allegatoService: AllegatoService,
              private allegati: DichiarazioniService,
              private translateService:TranslateService)
  {
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit()
  {
    this.store.setBreadcrumbs([{ label: 'Genera Stampa' }]);
    this.loadFascicolo();
    this.generatedDocuments = [
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

  public loadFascicolo(): void 
  {
    this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codiceFascicolo;
    this.loading.emitLoading(true);
    this.service.getFascicolo(this.fascicolo).then(result =>
    {
      this.loading.emitLoading(false);
      if (result.codiceEsito === CONST.OK)
      {
        this.fascicolo = result.payload;   
        this.loading.emitLoading(true);
        this.service.getMetadatiDichiarazioni(this.fascicolo.id).subscribe(dResponse =>
        {
          if(dResponse.codiceEsito === CONST.OK)
          {
            if(dResponse.payload.length > 0)
              this.uploadFilesGridVisibility();
            this.files = dResponse.payload.map(m => 
            {
              m.labelType = m.type == 700 ? "Istanza" : "Dichiarazione tecnica"
              return m;
            });
            this.refreshCheckBoxIsSigned();
            this.loading.emitLoading(false);
            if (this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.COMPILA_DOMANDA)     
            {
              this.loading.emitLoadingCustomMessage("Preparazione documenti in corso...<br>L'operazione potrebbe richiedere<br>alcuni secondi...");
              this.service.passaAGeneraStampaDomanda(this.fascicolo.id).subscribe(resp =>
              {
                this.loading.emitLoadingCustomMessage(null);
                if (resp.codiceEsito === CONST.OK)
                  {
                    this.fascicolo.attivitaDaEspletare = AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA;
                  }
                else{
                  // se va in errore devo tornare alla pagina del fascicolo.
                  this.router.navigate([paths.details(this.fascicolo.codicePraticaAppptr)]); 
                }
              },
              error=>{this.loading.emitLoadingCustomMessage(null)});
            }
          }
          else
            this.loading.emitLoading(false);
        });
      }
    }).finally(()=>this.fascicoloLoaded$.emit(true));
  }


  refreshCheckBoxIsSigned() {
    
    if(this.files && this.files.length>0 && this.files.some(file=>CONST.mimePDF.includes(file.formatoFile))){
      if(this.files.some(file=>file.isSigned == true))
        this.fcIsSigned.setValue(true)
      this.fcIsSigned.disable();
    }else{
      this.fcIsSigned.enable();
    }
  }

  public uploadFilesGridVisibility(): void
  {
    if(this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA)
    {
      this.loading.emitLoading(true);
      this.service.passaAllegaDocumentiGenerati(this.fascicolo.id).subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
          this.fascicolo.attivitaDaEspletare = AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI;
        this.loading.emitLoading(false);
      });
      
    }
    //TODO
    //this.store.editFascicolo(this.fascicolo);
  }

  public indietro(): void 
  {
    let title: string = "Attenzione";
    let message: string = "Conferma per riportare la pratica in stato di 'in compilazione'";
    this.dialogService.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, this.executeIndietro.bind(this), DialogType.WARNING);
  }

  public indietroAListaFascicoli(): void 
  {
    this.router.navigate([paths.list()]);
  }

  private executeIndietro(infoButton:number): void
  {
    if(infoButton == ButtonType.OK_BUTTON){
      this.loading.emitLoading(true);
      this.service.backToCompilazioneIstanza(this.fascicolo.id).subscribe(response =>
      {
        this.loading.emitLoading(false);
        if(response.codiceEsito === CONST.OK && response.payload)
          this.router.navigate([paths.details(this.fascicolo.codicePraticaAppptr)]); 
      });
    }
  }

  public subscribedDocumentsUploaded(event: any): void
  {
    this.subscribedDocuments = event.filter(item => item.data);
    this.fascicolo.documentiSottoscritti = event.filter(item => item.data);
    //TODO Salvataggio del documento uploadato
  }

  public handleUpload(event: any): void
  {
    let file: File = event.file;
    let filename: string = (event.tipoFile == 700 ? "Istanza_" : "Dichiarazione_Tecnica_") + this.fascicolo.codicePraticaAppptr + ""; 
    let requestAllegato:RequestAllegato = 
    { praticaId: this.fascicolo.id, tipiContenuto: [event.tipoFile],
       nomeContenuto: filename, referenteId: null, allegatoId: null };
    let container = { requestAllegato, file };
    //carico il file indicando anche se è firmato o meno
    if(this.fcIsSigned.value){
      requestAllegato.isSigned=true;
    } else {
      requestAllegato.isSigned = false;
    }

    if(this.fcIsSigned.value && !CONST.mimePDF.includes(file.type)){
      //il file deve essere firmato...
      let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:CONST.mimePDF});
      this.dialogService.showDialog(true, message, "generic.warning", DialogButtons.CHIUDI, null, DialogType.ERROR);
      return;
    }
    if(!this.fcIsSigned.value && !CONST.mimePDF.includes(file.type)){
        //il file deve NON essere firmato...
      let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:CONST.mimePDF});
      this.dialogService.showDialog(true, message, "generic.warning", DialogButtons.CHIUDI, null, DialogType.ERROR);
      return;
    }
    this.loading.emitLoading(true);
    
    this.allegati.uploadFile(container).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if(response.codiceEsito === CONST.OK)
      {
        let metadata: BFile = new BFile();
        metadata.id = response.payload.id;
        metadata.name = file.name;
        metadata.type = event.tipoFile;
        metadata.checksum = response.payload.checksum;
        metadata.labelType = event.tipoFile == 700 ? "Istanza" : "Dichiarazione tecnica";
        metadata.uploadDate = new Date();
        metadata.formatoFile = file.type;
        this.files.push(metadata);
        this.refreshCheckBoxIsSigned();
      }

    });
  }

  public delete(event: BFile): void
  {
    this.loading.emitLoading(true);
    let req: RequestAllegato = new RequestAllegato();
    req.allegatoId = event.id;
    req.praticaId = this.fascicolo.id;
    this.allegati.deleteFile(req).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        let index = this.files.map(m => m.id).indexOf(event.id);
        if(index != -1)
          this.files.splice(index, 1);
        this.refreshCheckBoxIsSigned();
      }
      this.loading.emitLoading(false);
    });
  }

  public handleDownload(type: "ISTANZA"|"SCHEDA"): void
  {
    let filename = (type === "ISTANZA" ? "Istanza_" : "Dichiarazione_Tecnica_") + this.fascicolo.codicePraticaAppptr + ".pdf";
    let codice: string = this.fascicolo.codicePraticaAppptr;
    let endpointCall: Observable<HttpResponse<Blob>>;
    endpointCall = type === "ISTANZA" ? this.service.downloadDomandaIstanza(codice) :
      this.service.downloadDomandaSchedaTecnica(codice);
    this.loading.emitLoading(true);
    endpointCall.subscribe(response =>
    {
      this.loading.emitLoading(false);
      if (response.ok)
        downloadFile(response.body, filename);
    });
  }

  public download(idAndName)
  {
    this.loading.emitLoading(true);
    this.allegatoService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/allegati/download.pjson')
      .toPromise()
      .then(data =>
      {
        var blob = new Blob([data.body], { type: data.body.type });
        this.allegatoService.downloadElemento(blob, idAndName.name);
      })
      .catch(error =>
      {
        console.log('download error:', JSON.stringify(error));
        this.loading.emitLoading(false);
      })
      .finally(() =>
      {
        this.loading.emitLoading(false);
      });
  }

  

  private dialogPresentazioneCompletata(){
    this.dialogService.showDialog(true, 
      'GENERATE_STAMPA.PRESENTAZIONE_OK', 'INFORMATION_TITLE', DialogButtons.CHIUDI, (buttonID: number) =>{},DialogType.SUCCESS,
      paths.details(this.fascicolo.codicePraticaAppptr));
  }

  public finalize(): void
  {
    if (this.files.length === 2)
    {
      let message = 'GENERATE_STAMPA.CONFIRMATION_PRESENTATA_ISTANZA';
      let title = 'INFORMATION_TITLE';
      this.dialogService.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, (buttonID: number) =>
      {
        if (buttonID === ButtonType.OK_BUTTON)
        {
          this.loading.emitLoading(true);
          this.service.passaAInProtocollazione(this.fascicolo.id, this.fascicolo.codicePraticaAppptr).subscribe(response =>
          {
            this.loading.emitLoading(false);
            if(response.codiceEsito === CONST.OK)
            {
              this.fascicolo.attivitaDaEspletare = AttivitaDaEspletareEnum.IN_PREISTRUTTORIA;
              //this.router.navigate([paths.details(this.fascicolo.codicePraticaAppptr)]);
              this.dialogPresentazioneCompletata();
            }
            
          });
        }
      },DialogType.INFORMATION);
    } 
    else
    {
      let message = 'GENERATE_STAMPA.DIFFERENCE_IN_FILES';
      let title = 'INFORMATION_TITLE';
      this.dialogService.showDialog(true, message, title, DialogButtons.CHIUDI, null, DialogType.ERROR);
    }
  }

  get showAllega()
  {
    return this.fascicolo.attivitaDaEspletare !== AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI &&
           this.fascicolo.attivitaDaEspletare !== AttivitaDaEspletareEnum.IN_PREISTRUTTORIA;
  }

  get showPresentata(): boolean { return this.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI; }
  get disablePresentata(): boolean { return this.files.length !== 2; }
}
