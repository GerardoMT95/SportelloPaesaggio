import { AdminService } from './../../../../../services/admin-service/admin.service';
import { CONST } from './../../../../../../shared/constants';
import { FascicoloDTO } from './../../../../../components/model/entity/fascicolo.models';
import { LoadingService } from './../../../../../services/loading.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-fascicolo',
  templateUrl: './admin-fascicolo.component.html',
  styleUrls: ['./admin-fascicolo.component.css']
})
export class AdminFascicoloComponent implements OnInit {

  public colonneTabella: any[] =
    [
      { header: "Codice Fascicolo" },
      { header: "Oggetto" },
      { header: "Tipologia Procedimento" },
      { header: "Comune" },
      { header: "Responsabile Procedimento" },
      { header: "Numero Provvedimento" },
      { header: "Data Provvedimento" },
      { header: "Esito" },
    ];
  public fascicoliDaAnnullare: Array<FascicoloDTO> = [];

  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  public const = CONST;
  public codiceDaCercare: string;
  public results: Array<string> = [];

  constructor(private adminService: AdminService,
    private loadingService: LoadingService) { }

  ngOnInit() {

  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'confermaAnnullamento':
            this.confermaAnnullamento(event.extraData.idFascicolo);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  public annulla(idFascicolo: number): void {
    this.alertData = {
      display: true,
      title: "adminFascicolo.dialogAnnulla.title",
      content: "adminFascicolo.dialogAnnulla.message",
      typ: "info",
      extraData: { idFascicolo: idFascicolo, operazione: "confermaAnnullamento" },
      isConfirm: true,
    };
  }

  public confermaAnnullamento(idFasciclo: number): void {
    this.loadingService.emitLoading(true);
    this.adminService.annullaFascicolo(idFasciclo).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.alertData = {
            display: true,
            title: "adminFascicolo.dialogAnnullaEsito.title",
            content: "adminFascicolo.dialogAnnullaEsito.message",
            typ: "success",
            extraData: null,
            isConfirm: false,
          };
          this.fascicoliDaAnnullare = [];
          this.loadingService.emitLoading(false);
        }
      },
    );
  }

  public ricerca(): void {
    if (this.codiceDaCercare) {
      this.adminService.getFascicoloDaAnnullare(this.codiceDaCercare).subscribe(
        response => {
          if (response.codiceEsito === "OK" && response.payload) {
            this.fascicoliDaAnnullare = [];
            this.fascicoliDaAnnullare.push(response.payload);
          }
        },
        error => {
          this.alertData = {
            display: true,
            title: "Errore",
            content: error.message,
            typ: "danger",
            extraData: null,
            isConfirm: false,
          };
        }
      );
    }
    else {
      this.fascicoliDaAnnullare = [];
    }
  }

  public reset(event: any): void {
    this.codiceDaCercare = null;
    this.results = [];
    this.fascicoliDaAnnullare = [];
  }

  getTipoProcedimento(tipoProcedimento: string): string {
    let label=tipoProcedimento;
    CONST.TipiProcedimento.forEach(el=>{
      if (el.value==tipoProcedimento){
        label=el.label;
      }
    });
    return label;
    /*switch (tipoProcedimento) {
      case CONST.TipiProcedimento[0].value: return CONST.TipiProcedimento[0].label;
      case CONST.TipiProcedimento[1].value: return CONST.TipiProcedimento[1].label;
      case CONST.TipiProcedimento[2].value: return CONST.TipiProcedimento[2].label;
      case CONST.TipiProcedimento[3].value: return CONST.TipiProcedimento[3].label;
      case CONST.TipiProcedimento[4].value: return CONST.TipiProcedimento[4].label;
      case CONST.TipiProcedimento[5].value: return CONST.TipiProcedimento[5].label;
      case CONST.TipiProcedimento[6].value: return CONST.TipiProcedimento[6].label;
      default: break;
    }*/
  }

  getEsitoAutorizzazione(esito: string): string {
    switch (esito) {
      case CONST.EsitoAutorizzazione[0].value: return CONST.EsitoAutorizzazione[0].label;
      case CONST.EsitoAutorizzazione[1].value: return CONST.EsitoAutorizzazione[1].label;
      case CONST.EsitoAutorizzazione[2].value: return CONST.EsitoAutorizzazione[2].label;
      default: break;
    }
  }

  public autocompleteCodice(event: any): void {
    this.adminService.autocompleteCodice(event.query,true).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.results = response.payload;
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: error.message,
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
      }
    );
  }

}
