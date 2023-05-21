import { downloadFile } from 'src/app/shared/functions/generic.utils';
import { ButtonType, DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { IButton } from './../../../../core/models/dialog.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Fascicolo, RequestAllegato } from 'src/app/shared/models/models';
import { DialogService } from './../../../../core/services/dialog.service';
import { CONST } from './../../../../shared/constants';
import { BFile } from './../../../../shared/models/allegati.model';
import { Allegati, Allegato, IntegrazioneDocumentale, StatoIntegrazione } from './../../../../shared/models/models';
import { AllegatoService } from './../../../../shared/services/allegato.service';
import { FascicoloService } from './../../../../shared/services/http-fascicolo.service';
import { IntegrazioneDocumentaleService } from './../../../../shared/services/Integrazione-documentale/integrazione-documentale.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { ActionEvent } from './../../models/integrazione.models';

@Component({
  selector: 'app-dettaglio-integrazione-page',
  templateUrl: './dettaglio-integrazione-page.component.html',
  styleUrls: ['./dettaglio-integrazione-page.component.scss']
})
export class DettaglioIntegrazionePageComponent implements OnInit
{
  //INPUT PASSATI DA PATH
  private codicePratica: string;
  public idIntegrazione: number;
  //DATI OTTENUTI DA BE
  public fascicolo: Fascicolo;
  public allegati: Allegati;
  public integrazione: IntegrazioneDocumentale;
  public documentoIntegrazione: BFile;
  //UTILITY
  private unsubscribe$: Subject<void> = new Subject<void>();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: IntegrazioneDocumentaleService,
              private fascicoloService: FascicoloService,
              private allegatoService: AllegatoService,
              private dialog: DialogService,
              private loading: LoadingService)
  { 
    this.codicePratica = this.route.snapshot.paramMap.get("id");
    this.idIntegrazione = parseInt(this.route.snapshot.paramMap.get("idIntegrazione"));
  }

  ngOnInit()
  {
    this.loadData();
  }

  private loadData(): void
  {
    this.fascicolo = new Fascicolo();
    this.fascicolo.codicePraticaAppptr = this.codicePratica;
    this.loading.emitLoading(true);
    this.fascicoloService.getFascicolo(this.fascicolo).then(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.fascicolo = response.payload;
        this.service.find(this.idIntegrazione).subscribe(response_int =>
        {
          if(response_int.codiceEsito === CONST.OK)
          {
            this.allegati = response_int.payload.allegati;
            this.integrazione = response_int.payload;
            this.documentoIntegrazione = this.toBFILE(this.integrazione.documentoIntegrazione);
            console.log(this.documentoIntegrazione);
          }
          this.loading.emitLoading(false);
        });
      }
      else
        this.loading.emitLoading(false);
    });
  }

  public actionResolver(event: ActionEvent): void
  {
    switch(event)
    {
      case "INDIETRO":
        this.indietro();
        break;
      case "PROSEGUI":
        this.prosegui();
        break;
      case "DOWNLOAD":
        this.downloadDocumentoIntegrazione();
        break;
      case "COMPLETA":
        this.completa();
        break;
      case "TORNA_IN_BOZZA":
          this.tornaInBozza();
          break;  
    }
  }

  private tornaInBozza(){
    this.cambiaStatoCall("IN_BOZZA",this.indietro.bind(this));
  }
  private indietro(): void { this.router.navigate(['gestione-istanze', this.codicePratica, 'fascicolo']); }
  private prosegui(): void
  {
    if(this.allegati)
      this.cambiaStatoCall("IN_ATTESA");
  }
  private completa(): void
  {
    if(this.documentoIntegrazione)
      this.cambiaStatoCall("COMPLETATA", this.indietro.bind(this));
  }

  private cambiaStatoCall(stato: StatoIntegrazione, callback?: () => void): void
  {
    this.loading.emitLoading(true);
    this.service.updateStatus(this.idIntegrazione,this.fascicolo.id, stato).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if (response.codiceEsito === CONST.OK && response.payload === 1)
      {
        this.integrazione.stato = stato;
        this.dialog.showDialog(
          true,
          'generic.operazioneOk',
          'generic.info',
          DialogButtons.CHIUDI,
          (bottone)=>{
            if(callback) callback();
          }
        );
      }      
    });
  }

  public uploadDocumentoIntegrazione(container:{file:File,isSigned?:boolean}): void
  {
    if(!this.documentoIntegrazione)
    { 
      this.loading.emitLoading(true);   
      const formData: FormData = new FormData();
      formData.append('file', container.file);
      let documentoIntegrazione = new RequestAllegato();
      documentoIntegrazione.praticaId = this.fascicolo.id;
      documentoIntegrazione.tipiContenuto = [702];
      documentoIntegrazione.integrazioneId = this.idIntegrazione;
      if(container.isSigned){
        documentoIntegrazione.isSigned=container.isSigned
      }
      formData.append('req', new Blob([JSON.stringify(documentoIntegrazione)], { type: "application/json" }));
      this.allegatoService.uploadAllegatoDocumento(formData, '/allegati/upload_allegato.pjson').then(response =>
      {
        this.loading.emitLoading(false);
        if(response.codiceEsito === CONST.OK)
        {
          this.integrazione.documentoIntegrazione = response.payload;
          this.documentoIntegrazione = this.toBFILE(response.payload);
        }
      });
    }
  }

  public downloadDocumentoCompilato(event: any): void
  {
    this.loading.emitLoading(true);
     //this.allegatoService.downloadFile(event.id).subscribe(response =>
     this.allegatoService.downloadAllegatoFascicolo(event.id,this.fascicolo.id, '/allegati/download.pjson').subscribe(response =>  
    {
      this.loading.emitLoading(false);
      if (response.ok)
        downloadFile(response.body, "Integrazione_documentale_" + new Date().toLocaleDateString().replace('/', ''),response.headers);
    });
  }

  private downloadDocumentoIntegrazione(): void
  {
    this.loading.emitLoading(true);
    this.service.downloadDocumentoIntegrazione(this.idIntegrazione, this.fascicolo.id, this.codicePratica).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if(response.ok)
        downloadFile(response.body, "Integrazione_documentale_" + new Date().toLocaleDateString().replace('/', ''),response.headers);
    });
  }

  public cancellaDocumentoIntegrazione(container: any): void
  {
    this.dialog.showDialog(true, 'ANNULA.ELEMENTO', "generic.warning", DialogButtons.OK_CANCEL, 
    (button:ButtonType) =>
    {
      if(button!=ButtonType.OK_BUTTON) 
          return;
      this.loading.emitLoading(true);
      this.allegatoService.cancellaFileIntegrazione(container.id, this.fascicolo.id,this.integrazione.id).subscribe(response =>
      {
        this.loading.emitLoading(false);
        if (response.codiceEsito === CONST.OK)
          this.documentoIntegrazione = null;
      });
    }, DialogType.WARNING);
  }

  private toBFILE(allegato: Allegato): BFile
  {
    let tmp = null;
    if(allegato)
    {
      tmp = 
      {
        labelType: "Documento integrazione",
        name: allegato.nome,
        uploadDate: <Date>allegato.data,
        type: 702,
        checksum:allegato.checksum,
        id: allegato.id,
        path: allegato.path,
        size: allegato.size
      };
    }
    return tmp; 
  }
}
