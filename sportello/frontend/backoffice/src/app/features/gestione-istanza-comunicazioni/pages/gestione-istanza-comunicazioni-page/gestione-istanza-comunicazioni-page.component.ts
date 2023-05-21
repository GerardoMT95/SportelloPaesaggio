import { Component, OnInit } from '@angular/core';
import { DialogService } from 'primeng/primeng';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DataService } from 'src/app/features/gestione-istanza/services';
import { DialogButtons } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { Allegato } from 'src/app/shared/components/model/model';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo, GroupType, Role, StatoEnum, TemplateDestinatarioDTO, TemplateEmailDestinatariDto } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { TipologicaDTO } from '../../model/tipologica';
import { GestioneIstanzaComunicazioniService } from '../../service/gestione-istanza-comunicazioni.service';
import { DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { FascicoloService } from './../../../../shared/services/pratica/http-fascicolo.service';
import { CorrispondenzaSearch, DestinatarioComunicazioneDTO } from './../../../funzionalita-amministratore-applicazione/models/admin-functions.models';
import { AllegatoCorrispondenza, DettaglioCorrispondenzaDTO, Template, TemplateComunicazione } from './../../model/corrispondenza.models';


@Component({
  selector: 'app-gestione-istanza-comunicazioni-page',
  templateUrl: './gestione-istanza-comunicazioni-page.component.html',
  styleUrls: ['./gestione-istanza-comunicazioni-page.component.scss'],
  providers: [DialogService]
})
export class GestioneIstanzaComunicazioniPageComponent implements OnInit {
  private readonly RICH_INTEGRAZIONE="RICH_INTEGRAZIONE";
  searchObject: any = {};
  comunicazioniTableHeaders: TableConfig[] = [];
  currentUser: LoggedUser;
  //form: FormGroup;
//  userRole;
  templates:TemplateComunicazione[]=[];
  templatesIntegr:TemplateComunicazione[]=[];
  role = Role;
  corrispondenzaSearch:CorrispondenzaSearch=new CorrispondenzaSearch();
  //public inviaComunicazione: boolean = false;
  public statoComponent="RIEPILOGO"; //RIEPILOGO COMP_RICH_INTEGR (caso di editing bozza o nuova richiesta integrazione)  COMPOSIZIONE (caso di editing bozza o nuova comunicazione generica) DETTAGLIO (visualizzazione comunicazione già inviata)
  public comunicazioni: DettaglioCorrispondenzaDTO[] = [];
  public idComunicazione: number | string;
  public numeroTotaleOggetti:number=0;
  public numeroOggettiRestituiti:number=0;
  public dettaglioCorrispondenzaDTO: DettaglioCorrispondenzaDTO;
  public dettaglioOpened:DettaglioCorrispondenzaDTO; //utilizzato nella vista del dettaglio
  public abilitataRichiestaIntegrazione: boolean=false;
  public indirizziMailDefault: DestinatarioComunicazioneDTO[] = [];
  public visibilitaMailDefault: boolean = false; 
  
  constructor(private customDialogService: CustomDialogService,
              private userService: UserService,
              private gestioneIstanzaComunicazioniService: GestioneIstanzaComunicazioniService,
              private loadingService:LoadingService,
              private adminService: AdminFunctionsService,
              private fascicoloService: FascicoloService,
              public data: DataService,
              private dominioService: HttpDominioService) {
    this.currentUser = this.userService.getUser();
    this.corrispondenzaSearch.idPratica=this.data.idPratica;
    this.corrispondenzaSearch.limit=CONST.DEFAULT_PAGE_NUMBER;
    this.corrispondenzaSearch.page=1;
    this.corrispondenzaSearch.idPratica=this.data.fascicolo.id;
    this.corrispondenzaSearch.sortBy='dataInvio';
    this.corrispondenzaSearch.sortType='DESC';
    this.retrieveComunicazioni();
  }

  
  ngOnInit() {
    this.comunicazioniTableHeaders = [
      {
        header: 'Mittente',
        field: 'mittenteEmail'
      },
      {
        header: 'Destinatari',
        field: 'destinatari'
      },
      {
        header: 'Oggetto',
        field: 'oggetto'
      },
      {
        header: 'Riservata',
        field: 'riservata'
      },
      //{
      //  header: 'Interna',
      //  field: 'comunicazioneInterna'
      //},
      {
        header: 'Data invio',
        field: 'dataInvio'
      },
      {
        header: '',
        field: 'displayButton',
        width: 8
      }
    ];
    if(this.userService.groupType === GroupType.EnteTerritoriale)
    {
      this.comunicazioniTableHeaders = this.comunicazioniTableHeaders.filter((item) =>
                                                item.field === 'riservata' ? false : true);
    }
    this.visibilitaRegEDMailDefault(this.fascicolo.enteDelegato);
    /* if (this.userService.role === GroupRole. this.role.EnteTerritoriale) {
      this.comunicazioniTableHeaders = this.comunicazioniTableHeaders.filter((item) =>
        item.field === 'riserva' ? false : true
      );
    } */

    //this.retrieveComunicazioni();
    if (!this.readOnly)
      this.retrieveTemplate();
    this.setAbilitataRichiestaIntegrazione();
  }

  setAbilitataRichiestaIntegrazione(){
    let statiAmmessiIntegrazione= [StatoEnum.PresaInCarica,StatoEnum.InLavorazione,StatoEnum.InTrasmissione];
    this.abilitataRichiestaIntegrazione=
    (this.userService.groupType==GroupType.EnteDelegato || 
      this.userService.groupType==GroupType.Regione) &&
      statiAmmessiIntegrazione.includes(this.fascicolo.attivitaDaEspletare);
  }

  private retrieveTemplate(){
    this.adminService.
              getTemplatDestEmailList("",this.data.fascicolo.id).
              subscribe(_result =>
                {
                  if(_result.codiceEsito === CONST.OK && _result.payload){
                    _result.payload.map((m: TemplateEmailDestinatariDto) =>{
                      let templateTemp:TemplateComunicazione= new TemplateComunicazione()
                          templateTemp.descrizione=m.template.descrizione
                          templateTemp.label=m.template.descrizione
                          templateTemp.value=m.template.codice
                          templateTemp.template= new Template()
                          templateTemp.template.oggetto=m.template.oggetto
                          templateTemp.template.testo=m.template.testo
                          templateTemp.template.riservata=m.template.riservata;
                          templateTemp.template.protocollazione=m.template.protocollazione;
                          templateTemp.template.destinatari=[]
                          m.destinatari.map((d:TemplateDestinatarioDTO)=>{
                            let destinatario:DestinatarioComunicazioneDTO =new DestinatarioComunicazioneDTO()
                            destinatario.email=d.email
                            destinatario.id=d.id
                            destinatario.nome=d.denominazione
                            destinatario.pec = d.pec=="pec" ? true:false
                            destinatario.tipo=d.tipo
                            templateTemp.template.destinatari.push(destinatario)
                          })
                          this.templates.push(templateTemp)
                    })
                    this.loadingService.emitLoading(false)
                  }else{
                    this.loadingService.emitLoading(false)
                  }
                });
  }

  private fetchTemplateIntegrazioneEApriEdit(){
    this.adminService.
    getTemplatDestEmailList(this.RICH_INTEGRAZIONE,this.data.fascicolo.id).
    subscribe(_result =>
      {
        this.loadingService.emitLoading(false);
        if(_result.codiceEsito === CONST.OK && _result.payload){
          _result.payload.map((m: TemplateEmailDestinatariDto) =>{
            let templateTemp:TemplateComunicazione= new TemplateComunicazione()
                templateTemp.descrizione=m.template.descrizione
                templateTemp.label=m.template.descrizione
                templateTemp.value=m.template.codice
                templateTemp.template= new Template()
                templateTemp.template.oggetto=m.template.oggetto
                templateTemp.template.testo=m.template.testo
                templateTemp.template.riservata=m.template.riservata;
                templateTemp.template.protocollazione=m.template.protocollazione;
                templateTemp.template.destinatari=[]
                m.destinatari.map((d:TemplateDestinatarioDTO)=>{
                  let destinatario:DestinatarioComunicazioneDTO =new DestinatarioComunicazioneDTO()
                  destinatario.email=d.email
                  destinatario.id=d.id
                  destinatario.nome=d.denominazione
                  destinatario.pec = d.pec=="pec" ? true:false
                  destinatario.tipo=d.tipo
                  templateTemp.template.destinatari.push(destinatario)
                })
                this.templatesIntegr.push(templateTemp);
                this.inviaComunicazioneTrue(true);
          });
        }else{
          this.loadingService.emitLoading(false)
        }
      });
  }

  public nuovaIntegrazione(): void {
    //come nuova comunicazione ma il template è fisso.
    if(!this.templatesIntegr || this.templatesIntegr.length==0){
      this.fetchTemplateIntegrazioneEApriEdit();
    }else{//già fetchati...
      this.inviaComunicazioneTrue(true);
    }            
  }

  public richiediTemplate(archiviazione: boolean): void
  {
    this.loadingService.emitLoading(true);
    this.fascicoloService.callCreateRequest(this.data.idPratica, archiviazione).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.dettaglioCorrispondenzaDTO = response.payload;
        this.idComunicazione = response.payload.corrispondenza.id;
        this.statoComponent = 'RICHIESTA';
      }
      this.loadingService.emitLoading(false);
    });
  }

  public inviaComunicazioneTrue(forIntegrazione?:boolean): void {
    //this.inviaComunicazione = true;
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.creaBozza(this.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.idComunicazione = response.payload.corrispondenza.id;
          this.dettaglioCorrispondenzaDTO=response.payload;
          if(forIntegrazione){
            this.statoComponent="COMP_RICH_INTEGR";
          }else{
            this.statoComponent="COMPOSIZIONE";
          }
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        //this.inviaComunicazione = false;
        this.statoComponent="RIEPILOGO";
      }
    );
  }

  get fascicolo(): Fascicolo {
    return this.data.fascicolo;
  }

  get comunicazione() {
    if (this.comunicazioni) {
      return this.comunicazioni;
    }
    //return this.comunicazioni;
  }

  insertedByCurrentUser(insertedBy: string, currentUser: string) {
    return insertedBy === currentUser;
  }

  updateQuery(searchQuery?: any) {
    let oldSearch=this.corrispondenzaSearch;
    this.corrispondenzaSearch=new CorrispondenzaSearch();
    this.corrispondenzaSearch.limit=oldSearch.limit;
    this.corrispondenzaSearch.page=oldSearch.page;
    this.corrispondenzaSearch.idPratica=oldSearch.idPratica;
    this.corrispondenzaSearch.sortBy=oldSearch.sortBy;
    this.corrispondenzaSearch.sortType=oldSearch.sortType;
    if(searchQuery){
      Object.keys(searchQuery).forEach(key=>{
        this.corrispondenzaSearch[key]=searchQuery[key];
      });
    }
    this.retrieveComunicazioni();
  }

  public sendData(corrispondenzaDTO: DettaglioCorrispondenzaDTO,withProto:boolean): void {
    if (corrispondenzaDTO.corrispondenza.bozza === true) {
      this.salvaBozza(corrispondenzaDTO);
    }
    else {
      if(corrispondenzaDTO.corrispondenza.codiceTemplate==this.RICH_INTEGRAZIONE && 
        !this.abilitataRichiestaIntegrazione){
        //controllo che nel frattempo sia congruente lo stato della pratica...
        this.errorOnSend("Stato pratica non compatibile, impossibile inviare richiesta di integrazione ");
        return;
      }
      this.sendComunicazione(corrispondenzaDTO,withProto);
    }
  }

  public showPreview(corrispondenzaDTO: DettaglioCorrispondenzaDTO): void {
    this.loadingService.emitLoading(true);
    if(corrispondenzaDTO.corrispondenza.bozza === true){
      this.gestioneIstanzaComunicazioniService.salvaBozza(this.data.idPratica,corrispondenzaDTO).subscribe(
        response => {
          this.loadingService.emitLoading(false);
          if (response.codiceEsito === "OK") {
            this.loadingService.emitLoading(true);
            this.gestioneIstanzaComunicazioniService.anteprimaBozza(this.data.idPratica,corrispondenzaDTO).subscribe(
              resp=>{
                if(resp.status == 200)
                  downloadFile(resp.body, "AnteprimaComunicazione.pdf");
                this.loadingService.emitLoading(false);
              }
            );
          }
        },
        error => {
          this.errorOnSend(error);
        }
      );
    }else{
      this.customDialogService.showDialog(
        true,
        'COMMUNICATIONS.NO_PREVIEW',
        'generic.warning',
        DialogButtons.CHIUDI,
        null,
        DialogType.ERROR,
        null,
        null
      );
    }
  }

  private errorOnSend(message:string){
    this.loadingService.emitLoading(false);
              this.customDialogService.showDialog(
                true,
                message,
                "Attenzione",
                DialogButtons.CHIUDI,
                null,
                DialogType.ERROR,
                null,
                null
              );
  }

  /**
   * viene salvata e poi effettuato il send....
   */
  private sendComunicazione(corrispondenzaDTO: DettaglioCorrispondenzaDTO,withProto:boolean): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.salvaBozza(this.data.idPratica,corrispondenzaDTO).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.loadingService.emitLoading(true);
          this.gestioneIstanzaComunicazioniService.sendComunicazione(this.data.idPratica,response.payload,withProto)
          .subscribe(respSend=>{
            this.loadingService.emitLoading(false);
            if (respSend.codiceEsito === "OK") {
              this.customDialogService.showDialog(
                true,
                'generic.operazioneOk',
                "Successo",
                DialogButtons.CHIUDI,
                null,
                DialogType.SUCCESS,
                null,
                null
              );
              this.statoComponent="RIEPILOGO";
              this.retrieveComunicazioni();
            }else{
              this.errorOnSend("errore durante l'invio della comunicazione");
              this.statoComponent="RIEPILOGO";
            }
          },error=>this.errorOnSend(error));
        }});
  }

  private salvaBozza(corrispondenzaDTO: DettaglioCorrispondenzaDTO): void {
    let dettCorrispondenzaDTO=corrispondenzaDTO;
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.salvaBozza(this.data.idPratica,dettCorrispondenzaDTO).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          this.statoComponent="RIEPILOGO";
          this.retrieveComunicazioni();
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.statoComponent="RIEPILOGO";
      }
    );
  }

  public retrieveComunicazioni(): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazioni(this.corrispondenzaSearch).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.comunicazioni = response.payload.list;
          this.numeroTotaleOggetti=response.numeroTotaleOggetti;
          this.numeroOggettiRestituiti=response.numeroOggettiRestituiti;
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public azioni(container: any): void {
    if (container.azione === "modifica") {
      this.modificaBozza(container.id);
    }
    else if (container.azione === "elimina") {
      this.customDialogService.showDialog(
        true,
        "Sei sicuro di voler eliminare la comunicazione?",
        "Attenzione",
        DialogButtons.CONFERMA_CHIUDI,
        (idbutton: number) => {
          if (idbutton === 1) {
            this.eliminaBozza(container.id);
          }
        },
        DialogType.WARNING,
        null,
        null
      );
    }
    else {
      this.dettaglio(container.id, this.fascicolo.id);
    }
  }

  private dettaglio(idComunicazione: number, idFascicolo: string): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazione(idComunicazione, idFascicolo).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.openDettaglioModal(response.payload);
          console.log(response.payload);
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        console.log(error.message);
      }
    );
  }

  private openDettaglioModal(dettaglioComunicazioneMocked: DettaglioCorrispondenzaDTO): void {
    this.statoComponent="DETTAGLIO";
    this.dettaglioOpened=dettaglioComunicazioneMocked;
    /*const ref = this.dialogService.open(DettaglioComComponent, {
      header: 'Dettaglio',
      width: '90%',
      height: '90%',
      styleClass: 'h-90',
      data: {
        comunicazione: dettaglioComunicazioneMocked
      }
    });*/
  }

  private eliminaBozza(idComunicazione: number): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.eliminaBozza(idComunicazione, this.data.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          /* this.ngOnInit(); */
          this.retrieveComunicazioni();
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  private modificaBozza(idComunicazione: number): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.retrieveComunicazione(idComunicazione, this.data.fascicolo.id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          //response.payload.corrispondenza.oggetto = this.formattaStringa(response.payload.corrispondenza.oggetto);
          this.dettaglioCorrispondenzaDTO = response.payload;
          this.idComunicazione = idComunicazione;
          //this.inviaComunicazione = true;
          if(this.dettaglioCorrispondenzaDTO.corrispondenza.codiceTemplate==this.RICH_INTEGRAZIONE){
            this.statoComponent="COMP_RICH_INTEGR";
          }else{
            this.statoComponent="COMPOSIZIONE";
          }
        }
      },
      error => {
        this.loadingService.emitLoading(false);
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  /**
   * toglie la parte di Fascicolo APPTR-XXXXX usando lo spazio come separatore...e togliendo i primi 2 tokens
   */
  private formattaStringa(oggetto: string): string {
    let oggettoInserito: string = "";
    if (oggetto) {
      let oggettoSplittato: Array<string> = oggetto.split(" ");
      let oggettoSenzaCodiceFascicolo: Array<string> = oggettoSplittato.slice(2, oggettoSplittato.length);
      oggettoInserito = oggettoSenzaCodiceFascicolo.join(" ");
    }
    return oggettoInserito;
  }

  private getAllegato(resp: TipologicaDTO): Allegato
  {
    let allegato = new AllegatoCorrispondenza();
    allegato.id = resp.codice;
    allegato.nome = resp.label;
    allegato.data = new Date();
    return allegato;
  }
  
  public uploadFile(container: any): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.upload(container.file, this.fascicolo.id, container.idComunicazione).subscribe(response =>
      {
        this.loadingService.emitLoading(false);
        if(response.codiceEsito === CONST.OK)
        {
          if(!this.dettaglioCorrispondenzaDTO.allegatiInfo)
            this.dettaglioCorrispondenzaDTO.allegatiInfo = [];
          this.dettaglioCorrispondenzaDTO.allegatiInfo.push(response.payload);
        }
        this.loadingService.emitLoading(false);
      });
  }

  public deleteFile(file: AllegatoCorrispondenza): void {
    this.customDialogService.showDialog(
      true,
      "Sei sicuro di voler eliminare l'allegato?",
      "Attenzione",
      DialogButtons.CONFERMA_CHIUDI,
      (idbutton: number) => {
        if (idbutton === 1) {
          this.eliminaFile(file.id, this.idComunicazione, this.fascicolo.id, this.fascicolo.codicePraticaAppptr);
        }
      },
      DialogType.WARNING,
      null,
      null
    );
  }

  private eliminaFile(id: any, idComunicazione: number | string, idFascicolo: string, codiceFascicole: string): void {
      this.loadingService.emitLoading(true);
      this.gestioneIstanzaComunicazioniService.deleteFile(idFascicolo, id).subscribe(
      response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === "OK") {
          this.customDialogService.showDialog(
            true,
            response.descrizioneEsito,
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          let idxToRemove=this.dettaglioCorrispondenzaDTO.allegatiInfo.findIndex(all=>all.id==id);
          if(idxToRemove>=0){
            this.dettaglioCorrispondenzaDTO.allegatiInfo.splice(idxToRemove,1);
          }
        }
      },
      error => {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public downloadFile(file: AllegatoCorrispondenza): void {
    this.loadingService.emitLoading(true);
    this.gestioneIstanzaComunicazioniService.downloadAllegato(file.id,this.data.idPratica)
    .subscribe(response =>
      {
        if(response.status == 200)
          downloadFile(response.body, file.nome);
        this.loadingService.emitLoading(false);
      });
  }

  chiusoDettaglio($event){
    this.statoComponent="RIEPILOGO";
    this.retrieveComunicazioni();
  }

  chiusoDettaglioView($event){
    this.statoComponent="RIEPILOGO";
  }

  get abilitataRichiestaArchiviazione(): boolean { 
    return ![StatoEnum.Trasmessa, StatoEnum.Archiviata].includes(this.data.fascicolo.attivitaDaEspletare) && 
    [GroupType.EnteDelegato,GroupType.Regione].includes(this.userService.groupType); 
  }
  get abilitataRichiestaAttivazione(): boolean { 
    return this.data.fascicolo.attivitaDaEspletare === StatoEnum.Archiviata && 
    [GroupType.EnteDelegato,GroupType.Regione].includes(this.userService.groupType); 
  }

  public retrieveDestinatariDefault(idEnteDelegato: string): void
   {
    this.loadingService.emitLoading(true)
    this.dominioService.getIndirizziMailDefault(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          this.indirizziMailDefault = response.payload;
          this.indirizziMailDefault.forEach(element => {
            element.tipo = 'TO';
          });
          if(this.indirizziMailDefault.length > 0)
            this.visibilitaMailDefault = true;
          else 
            this.visibilitaMailDefault = false;
          this.loadingService.emitLoading(false);
         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loadingService.emitLoading(false)
       }
     );
  }

  public visibilitaRegEDMailDefault(idEnteDelegato:string) {
    this.loadingService.emitLoading(true)
    this.dominioService.getTipoOrgEnteDelegato(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          if(response.payload == "REG" || response.payload == "ED") 
            this.retrieveDestinatariDefault(idEnteDelegato)
          else 
            this.visibilitaMailDefault = true;
          this.loadingService.emitLoading(false);
         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loadingService.emitLoading(false)
       }
     );

  }

  get dettagliosenzaProtocollo(): boolean { return this.dettaglioCorrispondenzaDTO && ["REQ_ATT", "REQ_ARC"].includes(this.dettaglioCorrispondenzaDTO.corrispondenza.codiceTemplate); }
  get readOnly(): boolean { return this.userService.groupType === GroupType.Amministratore; }
  get isRegione(): boolean { return this.userService.groupType === GroupType.Regione; }
  get isED(): boolean { return this.userService.groupType === GroupType.EnteDelegato; }
}
