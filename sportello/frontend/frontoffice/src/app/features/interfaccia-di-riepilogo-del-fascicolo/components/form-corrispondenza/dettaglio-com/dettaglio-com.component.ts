import { Component, OnInit, Input, Renderer, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe, Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { CONST } from 'src/app/shared/constants';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { TipoSelect } from 'src/app/shared/components/model/model';
import { ComunicazioniService } from 'src/app/shared/services/comunicazioni/comunicazioni.service';
import { downloadFile } from 'src/app/shared/functions/generic.utils';
import { DestinatarioDTO, DettaglioCorrispondenzaDTO, Ricevuta } from '../../../models/corrispondenza.model';

@Component({
  selector: 'app-dettaglio-com',
  templateUrl: './dettaglio-com.component.html',
  styleUrls: ['./dettaglio-com.component.css']
})
export class DettaglioComComponent /* extends AuthComponent decommentare quando sarà fatta la logica */ {

  @Input("dettaglioComunicazione") dettaglio: DettaglioCorrispondenzaDTO;
  @Input("idPratica") idPratica:string;
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
    /* private loadingService: LoadingService,
    private lss: LocalSessionServiceService, */
    private route: ActivatedRoute,
    public router: Router,
    private gestioneIstanzaComunicazioniService: ComunicazioniService,
    private loadingService: LoadingService,
    private location: Location,
    /* private allegatoService:AllegatoService,
    private service : RicercaComunicazioneService, */
    private fb: FormBuilder,
    //private config: DynamicDialogConfig
  ) {
    /* super(router, lss); decommentare quando sarà fatta la logica */

  }

  ngOnInit() {
    //this.comunicazione = this.config.data.comunicazione;

    //console.log('comunicazione: ', this.comunicazione)
    /* this.fromComunicazioneTemplateToDettaglioCom(); */
    console.log("dettaglio: " + this.dettaglio);
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

  /* public fromComunicazioneTemplateToDettaglioCom(): void {
    this.dettaglio.data = this.comunicazione.data;
    this.dettaglio.mittente = this.comunicazione.nome;
    this.dettaglio.mailMittente = this.comunicazione.mittente;
    this.dettaglio.oggetto = this.comunicazione.oggetto;
    this.dettaglio.destinatari = new Array<Destinatari>();
    this.comunicazione.destinatari.forEach(destinatarioDTO => {
      let destinatario: Destinatari = new Destinatari();
      destinatario.indirizzo = destinatarioDTO.email;
      this.dettaglio.destinatari.push(destinatario);
    });
  } */

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

  pageChangedAction(x: number): void {
    this.page = x;
  }

  onShow() {
    this.renderer.setElementClass(document.body, 'modal-open', true);
  }


  classBadge(destinatario: DestinatarioDTO): string {

    if (destinatario.stato == 'ESITO_POSITIVO') {
      return "badge-success";
    } else if (destinatario.stato == 'ESITO_CON_ERRORE') {
      return "badge-danger";
    } else {
      return "badge-inviato";
    }
    /*if (destinatario.ricevute != null && destinatario.ricevute.length > 0) {
      if (destinatario.ricevute[0].errore == "nessuno")
        return "badge-success";
      else if (destinatario.ricevute[0].errore != "nessuno")
        return "badge-danger";
      else
        return "badge-inviato";
    }
    else
      return "badge-inviato";*/
  }

  public getRicevutaAccettazione(dettaglio: DettaglioCorrispondenzaDTO) {
    //prendo la prima nei destinatari...
    let ret = null;
    dettaglio.destinatari.forEach(dest => {
      if(dest.ricevute && dest.ricevute.length>0){
        dest.ricevute.forEach(ric => {
          if (ric.tipoRicevuta == "ACCETTAZIONE" ||
            ric.tipoRicevuta == "NON_ACCETTAZIONE") {
            ret = ric;
          }
        });
      }
    });
    return ret;
  }

  public getRicevutaErroreOconsegna(ricevute: Ricevuta[]) {
    let ret = null;
    ricevute.forEach(ricevuta => {
      if (ricevuta.tipoRicevuta == "AVVENUTA_CONSEGNA" ||
        ricevuta.tipoRicevuta == "PREAVVISO_ERRORE_CONSEGNA" ||
        ricevuta.tipoRicevuta == "RILEVAZIONE_VIRUS" ||
        ricevuta.tipoRicevuta == "ERRORE_CONSEGNA")
        ret = ricevuta;
    }
    );
    return ret;
  }

  public downloadFromAlfresco(idAllegato: string, nomeFile: string): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.downloadAllegato(idAllegato, this.idPratica)
      .subscribe(response => {
        if (response.status == 200)
          downloadFile(response.body, nomeFile);
        this.loadingService.emitLoading(false);
      });
  }

  public downloadRicevuta(idCms: string, nomeFile: string): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.downloadRicevuta(idCms, this.idPratica)
      .subscribe(response => {
        if (response.status == 200)
          downloadFile(response.body, nomeFile);
        this.loadingService.emitLoading(false);
      });
  }

  back(): void {
    this.action.emit(false);
  }

  /**
   * TODO da implementare anche lato be il retrieval del protocollo legato alla mail
   * @param dettaglio 
   */
  getProtocollo(dettaglio: DettaglioCorrispondenzaDTO) {
    return null;
  }
}
