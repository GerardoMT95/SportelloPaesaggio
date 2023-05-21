import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { copyOf } from 'src/app/core/functions/generic.utils';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { IButton } from './../../../../core/models/dialog.model';
import { GroupType} from './../../../../shared/models/models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { UserService } from './../../../../shared/services/user.service';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';

@Component({
  selector: 'app-gestione-istanza-passa-page',
  templateUrl: './gestione-istanza-passa-page.component.html',
  styleUrls: ['./gestione-istanza-passa-page.component.scss']
})
export class GestioneIstanzaPassaPageComponent implements OnInit
{
  public statoEnum = StatoEnum;
  public reqVCL: boolean = false;

  constructor(private router: Router,
              private dialogService: CustomDialogService,
              private fascicoloService: FascicoloService,
              private loading: LoadingService,
              private sharedData: DataService,
              private user: UserService) { }

  public ngOnInit(): void
  {
    this.loading.emitLoading(true);
    this.fascicoloService.callReqVCL(this.fascicolo.id).subscribe(response =>
    {
      if(response.codiceEsito == CONST.OK)
        this.reqVCL = response.payload;
      this.loading.emitLoading(false);
    });
  }
  
  public passaInLavorazione(): void
  {
    let title: string = 'PASSA_TAB.PASSA_TRANSMISSION_TITLE';
    let message: string = 'PASSA_TAB.PASSA_ELABORAZIONE_MESSAGE';
    let buttons: IButton[] = DialogButtons.CONFERMA_CHIUDI;
    this.dialogService.showDialog(true, message, title, buttons, (buttonID: number) =>
    {
      if (buttonID === ButtonType.OK_BUTTON)
      {
        this.loading.emitLoading(true);
        this.fascicoloService.callFascicoloInLavorazione(this.fascicolo.id).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK && response.payload === true)
          {
            let fascicolo = copyOf(this.sharedData.fascicolo);
            fascicolo.attivitaDaEspletare = this.statoEnum.InLavorazione;
            fascicolo.dataInizioLavorazione = new Date();
            this.sharedData.fascicolo = fascicolo;
            this.router.navigate(['gestione-istanze', this.sharedData.codicePratica, 'istanza-presentata']);
          }
          this.loading.emitLoading(false);
        });
      }
    }, DialogType.INFORMATION);
  }

  public passaInTrasmissione(): void
  {
    let title = "Attenzione";
    let message = "Verbale commissione locale, relazione ente e parere soprintendenza sono obbligatori. " +
                  "Verifica che queste informazioni siano state correttamente inserite";
    let dialogType: DialogType = DialogType.ERROR;
    let callback = (buttonId: any) => {};
    let buttons = DialogButtons.CHIUDI; 
    if(this.praticaValida())
    {
      title = 'PASSA_TAB.PASSA_TRANSMISSION_TITLE';
      message = 'PASSA_TAB.PASSA_ELABORAZIONE_TRANSMISSION_MESSAGE';
      dialogType = DialogType.INFORMATION;
      buttons = DialogButtons.CONFERMA_CHIUDI;
      callback = (buttonId: any) =>
      {
        if(buttonId == ButtonType.OK_BUTTON)
        {
          this.loading.emitLoading(true);
          this.fascicoloService.callFascicoloDaTrasmettere(this.fascicolo.id).subscribe(response =>
          {
            if (response.codiceEsito === CONST.OK && response.payload === true)
            {
              let fascicolo = copyOf(this.sharedData.fascicolo);
              fascicolo.attivitaDaEspletare = this.statoEnum.InTrasmissione;
              this.sharedData.fascicolo = fascicolo;
              this.router.navigate(['gestione-istanze', this.sharedData.codicePratica, 'trasmissione-provvedimento-finale']);
            }
            this.loading.emitLoading(false);
          });
        }
      } 
      callback.bind(this);
    }
    this.dialogService.showDialog(true, message, title, buttons, callback, dialogType);
  }


  private praticaValida(): boolean
  {
    console.log(this.fascicolo);
    console.log(this.reqVCL);
    let valid = true;
    if(this.fascicolo.attivitaDaEspletare === StatoEnum.InLavorazione)
    {
      // valid = (this.fascicolo.statoSedutaCommissione === "VERBALE_SEDUTA_ALLEGATO" //Verbale obbligatorio per tutte le tipologie tranne REGIONE
      //             || this.user.groupType === GroupType.Regione
      //             || !this.reqVCL) &&
      //         (this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_CON_AVVIO" //Relazione ente obbligatoria
      //             || this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_SENZA_AVVIO") &&
      //         (this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_SOPRINTENDENZA" //Parere soprintendenza obbligatoria
      //             || this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_ENTE");
      valid = (this.user.groupType == GroupType.Regione && (this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_CON_AVVIO"
                   || this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_SENZA_AVVIO")) ||
                   (this.user.groupType != GroupType.Regione && (this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_CON_AVVIO"
                   || this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_SENZA_AVVIO") &&
                   (this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_SOPRINTENDENZA" || this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_ENTE")
                   && (!this.reqVCL || this.fascicolo.statoSedutaCommissione === "VERBALE_SEDUTA_ALLEGATO"));

      /* switch(this.fascicolo.tipoProcedimento.toString())
      {
        case TipoProcedimento.AUT_PAES_ORD:
          valid = (this.fascicolo.statoSedutaCommissione === "VERBALE_SEDUTA_ALLEGATO" //Verbale obbligatorio per tutte le tipologie tranne REGIONE
                      || this.user.groupType === GroupType.Regione) &&
                   (this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_CON_AVVIO" //Relazione ente obbligatoria
                      || this.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_SENZA_AVVIO"
                      || this.user.groupType === GroupType.Regione) &&
                   (this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_SOPRINTENDENZA" //Parere soprintendenza obbligatoria
                      || this.fascicolo.statoParereSoprintendenza === "PARERE_INSERITO_ENTE"
                      || this.user.groupType === GroupType.Regione);
          break;
        default:
          valid = true; //DUMMY, finchè non abbiamo risposto più precise è tutto, al più, opzionale
      } */
    }
    return valid;
  }

  get fascicolo(): Fascicolo { return this.sharedData.fascicolo; }
  get disabled(): boolean { return !this.praticaValida(); }
  
}
