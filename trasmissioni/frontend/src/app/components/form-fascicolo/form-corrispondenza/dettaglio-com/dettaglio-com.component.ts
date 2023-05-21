import { Location } from '@angular/common';
//import { Destinatari } from './../components/model/dettaglioComunicazione';
import { Component, EventEmitter, Input, Output, Renderer } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { downloadFile } from 'src/app/components/functions/genericFunctions';
import { AuthComponent } from 'src/app/components/generic/AuthComponent';
import { Destinatari, DettaglioCom } from 'src/app/components/model/dettaglioComunicazione';
import { TipoSelect } from 'src/app/components/model/model';
import { AllegatoService } from 'src/app/services/allegato.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { RicercaComunicazioneService } from 'src/app/services/ricerca-comunicazione.service';
import { CONST } from 'src/shared/constants';

@Component({
  selector: 'app-dettaglio-com',
  templateUrl: './dettaglio-com.component.html',
  styleUrls: ['./dettaglio-com.component.css']
})
export class DettaglioComComponent extends AuthComponent
{

  //@Input("visible") visible:boolean= true;
  //@Input("dettaglioComunicazione") dettaglioCom : DettaglioComunicazione
  @Input("dettaglioComunicazione") dettaglio: DettaglioCom;
  //dettaglio : DettaglioCom;

  @Input() idFascicolo: string;

  @Output("action") action = new EventEmitter<boolean>();
  type: string = "info";
  title: string = "Dettaglio Comunicazione";
  form: FormGroup;
  //@ViewChild("mf") mfDataTable = DataTable;
  //data : Allegato[];
  // TABELLA ALLEGATI
  rowsOnPage: number = CONST.DEFAULT_PAGE_NUMBER;

  @Input() itemsTotal: number;
  //itemsTotal : number = 0;
  page: number = 1;
  sortField: string;
  sortOrder: number;
  pages: TipoSelect[] = CONST.PAGINAZIONE;

  constructor(private renderer: Renderer,
    private loadingService: LoadingService,
    private lss: LocalSessionServiceService,
    private route: ActivatedRoute,
    public router: Router,
    private location: Location,
    private allegatoService: AllegatoService,
    private service: RicercaComunicazioneService,
    private fb: FormBuilder,
    private autpaeSvc: AutorizzazioniPaesaggisticheService)
  {
    super(router, lss);

  }

  ngOnInit()
  {
    console.log('dettaglio {}', this.dettaglio)
    // this.dettaglio=this.service.getDettaglioCom();
    /*  const id = this.route.snapshot.paramMap.get('id');
     const idDestinatario = this.route.snapshot.paramMap.get('idDestinatario');
     this.service.getDettaglio(id, idDestinatario).then(item =>{
       this.dettaglio=item.payload
       //this.data= this.dettaglio.allegati;
       this.itemsTotal= this.dettaglio.allegati.length;

       this.buildForm();
     });*/

  }

  /* public buildForm(){
     let dp : DatePipe = new  DatePipe('it');
     let data =dp.transform(this.dettaglio.data, 'dd/MM/yyyy HH:mm');
 
     this.form=this.fb.group({
       codiceComunicazione: [this.dettaglio.codiceComunicazione,null],
       data: [data,null],
       numeroprotocollo: [this.dettaglio.numeroProtocollo,null],
       mittente: [this.dettaglio.mittente,null],
       mailMittente:[this.dettaglio.mailMittente,null],
       oggetto:[this.dettaglio.oggetto,null],
       tipologia: [this.dettaglio.tipologia,null],
       pec:[this.dettaglio.pec,null],
     })
   }*/

  pageChangedAction(x: number): void
  {
    this.page = x;
  }

  onShow() { this.renderer.setElementClass(document.body, 'modal-open', true); }


  public classBadge(destinatario: Destinatari): string
  {
    let styleClass: string = "badge-inviato";
    if(destinatario && destinatario.ricevute && destinatario.ricevute[0])
    {
      switch(destinatario.ricevute[0].tipoRicevuta)
      {
        case "AVVENUTA_CONSEGNA":
          styleClass = "badge-success"
          break;
        case "NON_ACCETTAZIONE":
        case "PREAVVISO_ERRORE_CONSEGNA":
        case "ERRORE_CONSEGNA":
        case "RILEVAZIONE_VIRUS":
          styleClass = "badge-danger"
          break;
        case "ACCETTAZIONE":
        case "PRESA_IN_CARICO":
        case "POSTA_CERTIFICATA":
        case null:
          styleClass = "badge-inviato"
          break;
      } 
    }
    return styleClass;
  }

  public downloadAllegato(idAllegato: string, nomeAllegato: string): void
  {
    this.autpaeSvc.downloadAllegatoFascicolo(this.idFascicolo,idAllegato).subscribe(
      response =>
      {
        if (response.ok)
        {
          downloadFile(response.body, nomeAllegato);
        }
      },
      error =>
      {
        console.log(error.message);
      }
    );
  }

  public downloadEML(idRicevuta: string, tipo: string): void
  {
      this.autpaeSvc.downloadRicevutaEml(this.idFascicolo,idRicevuta,tipo).subscribe(
      response =>
      {
        if (response.ok)
          downloadFile(response.body, tipo+'.eml');
      },
      error => console.log(error.message)
    );
  }

  public downloadFromAlfresco(idCms: string, nomeFile: string): void
  {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadDoc(idCms, 'dettaglio/ricevuta/download.pjson').subscribe(data =>
    {
      var blob = new Blob([data.body], { type: data.body.type });
      this.downloadElemento(blob, nomeFile);
    }, error =>
    {
      console.log('download error:', JSON.stringify(error));
      this.loadingService.emitLoading(false);
    }, () =>
    {
      console.log('Completed file download.');
      this.loadingService.emitLoading(false);
    });
  }

  public downloadElemento(blob: Blob, fileName: string): void
  {
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
      window.navigator.msSaveOrOpenBlob(blob, fileName);
    } else
    {
      var link = document.createElement("a");
      if (link.download !== undefined)
      {
        var url = URL.createObjectURL(blob);
        link.setAttribute("href", url);
        link.setAttribute("download", fileName);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
    this.loadingService.emitLoading(false);
  }

  public notEmpty(obj: string): boolean 
  { 
    return obj && obj != "" 
  }

  back(): void
  {
    console.log("CHIUDI");
    this.action.emit(false);
    //this.location.back();
  }
}
