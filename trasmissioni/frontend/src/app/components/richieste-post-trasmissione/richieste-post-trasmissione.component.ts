import { Component, Input, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  NgForm,
  Validators,
} from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { AutorizzazioniPaesaggisticheService } from "src/app/services/autorizzazioni-paesaggistiche.service";
import { LoadingService } from "src/app/services/loading.service";
import { ShowAlertService } from "src/app/services/show-alert.service";
import { OrderByAndPaging } from "src/shared/components/data-table/data-table.component";
import { CONST } from "src/shared/constants";
import {
  checkFileSizeTypeFn,
  downloadFile,
} from "../functions/genericFunctions";
import { AllegatoCustomDTO } from "../model/entity/allegato.models";
import {
  DettaglioCorrispondenzaDTO,
  RichiestePostTrasmissioneDTO,
} from "../model/entity/corrispondenza.models";
import { TableConfig } from "../model/entity/fascicolo.models";
import { Colonna } from "../model/model";
import { RichiestePostTrasmissioneSearch } from "../model/search/search.models";

export enum StatoRichiestaPostTrasmissione
{
    Trasmesso = "Trasmesso",
    Bozza = "Bozza",
}

@Component({
  selector: "app-richieste-post-trasmissione",
  templateUrl: "./richieste-post-trasmissione.component.html",
  styleUrls: ["./richieste-post-trasmissione.component.scss"],
})
export class RichiestePostTrasmissioneComponent implements OnInit {
  // @Input("init") toEdit: DettaglioCorrispondenzaDTO;
  @Input() idFascicolo: number;

  idRichiesta: number;
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  public display: boolean = false;
  public detail: boolean = false;
  public richiesteData: RichiestePostTrasmissioneDTO[] = [];
  public richiesta: RichiestePostTrasmissioneDTO =
    new RichiestePostTrasmissioneDTO();
  public searchRichiesta: RichiestePostTrasmissioneSearch =
    new RichiestePostTrasmissioneSearch();

  public disableEdit: boolean = false;
  public disableButtons: boolean = false;

  public comForm: FormGroup;
  public inviato: boolean = false;
  public fileArray: any[] = [];
  public disableEditAllegato: boolean;

  public itemsTotal: number = 0; //totale record ricerca

  public pagSearch: OrderByAndPaging = {
    page: 1,
    rows: 5,
    field: null,
    sortField: null,
    sortOrder: "ASC",
  };

  intestazioneTabella: TableConfig[] = [
    {
      field: "dateCreated",
      header: "richiestePostTrasmissione.table.dataCreazione",
      type: "datetime",
    },
    { field: "tipo", header: "richiestePostTrasmissione.table.tipo" },
    {
      field: "motivazione",
      header: "richiestePostTrasmissione.table.motivazione",
    },
    { field: "stato", header: "richiestePostTrasmissione.table.stato" },
  ];

  constructor(
    private loadingService: LoadingService,
    private formBuilder: FormBuilder,
    private autpaeSvc: AutorizzazioniPaesaggisticheService,
    private showAlertDialog: ShowAlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.caricamentoDati();
    this.buildForm();
  }

  public buildForm(): void {
    this.comForm = this.formBuilder.group({
      motivazione: new FormControl(null),
      allegati: new FormControl([]),
    });
  }
  private caricamentoDati() {
    //carico tabella
    this.loadingService.emitLoading(true);

    this.searchRichiesta.idFascicolo = String(this.idFascicolo);
    this.searchRichiesta.page = this.pagSearch.page;
    this.searchRichiesta.limit = this.pagSearch.rows;
    this.autpaeSvc
      .getRichiestePostTrasmissione(this.searchRichiesta)
      .subscribe((result) => {
        if (result.codiceEsito === CONST.OK && result.payload) {
          this.richiesteData = result.payload.list;
          this.itemsTotal = result.payload.count;
        } else {
          this.richiesteData = [];
        }

        this.loadingService.emitLoading(false);
        this.display = true;
      });
  }

  nuovaModifica() {
    //crea nuova richiesta di modifica
    this.richiesta.tipo = "Modifica";
    this.autpaeSvc
      .createRichiestePostTrasmissione(
        String(this.idFascicolo),
        this.richiesta.tipo
      )
      .subscribe((result) => {
        this.idRichiesta = result.payload.id;
        this.richiesteData.push(result.payload);
        this.apriDettaglio();
      });

    //call BE per creazione nuova richiesta e fetch dell DTO (con idRIchiesta)
  }

  nuovaCancellazione() {
    //crea nuova richiesta di cancellazione
    this.richiesta.tipo = "Cancellazione";
    this.autpaeSvc
      .createRichiestePostTrasmissione(
        String(this.idFascicolo),
        this.richiesta.tipo
      )
      .subscribe((result) => {
        this.idRichiesta = result.payload.id;
        this.richiesteData.push(result.payload);
        this.apriDettaglio();
      });
  }

  private apriDettaglio() {
    this.comForm.controls["motivazione"].enable();
    this.disableButtons = false;
    this.detail = true;
  }

  modificaBozza(item: any) {
    if (item != undefined) {
      this.idRichiesta = item.id;
      this.autpaeSvc
        .dettaglioRichiestePostTrasmissione(
          String(item.idFascicolo),
          String(item.id)
        )
        .subscribe((result) => {
          this.richiesta = result.payload.richiestaBase;
          if (result.payload.allegati !== null) {
            this.fileArray = result.payload.allegati;
          } else {
            this.fileArray = [];
          }
          this.comForm.patchValue({
            motivazione: this.richiesta.motivazione,
          });
          if (this.checkStatoRichiesta(this.richiesta)) {
            //this.toEdit = true;
            this.comForm.controls["motivazione"].enable();
            this.disableButtons = false;
          } else {
            this.disableButtons = true;
            this.comForm.controls["motivazione"].disable();
          }
          this.detail = !this.detail;
        });
    }
  }

  eliminaBozza(item: any) {
    if (item != undefined) {
      this.richiesta.idFascicolo = item.idFascicolo;
      this.richiesta.id = item.id;

      //cancella
      this.alertData = {
        display: true,
        title: "Attenzione",
        content:
          "La cancellazione di questo bozza sarà irreversibile, proseguire?",
        typ: "warning",
        extraData: { operazione: "deleteBozza", id: item.id },
        isConfirm: true,
      };
    }
  }

  checkStatoRichiesta(richiesta: RichiestePostTrasmissioneDTO) {
    return richiesta.stato == StatoRichiestaPostTrasmissione.Bozza ? true : false;
  }

  cambioPagina(event: { limit?: number; page: number }) {
    if (event.limit) {
      this.pagSearch.rows = event.limit;
    }
    if (event.page) {
      this.pagSearch.page = event.page;
    }
    this.caricamentoDati();
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case "validato":
            //this.doValidate(event.extraData.codice)
            break;
          case "deleteBozza":
            this.autpaeSvc
              .deleteRichiestePostTrasmissione(
                String(this.richiesta.idFascicolo),
                String(this.richiesta.id)
              )
              .subscribe((result) => {
                this.alertData.display = true;
                this.alertData.title = "Esito Operazione";

                if (result["payload"]) {
                  //Esito Operazione
                  this.alertData.content =
                    "Cancellazione avvenuta con successo";
                  this.alertData.typ = "success";
                  this.caricamentoDati();
                } else {
                  this.alertData.content =
                    "Errore nella cancellazione della richiesta!";
                  this.alertData.typ = "danger";
                }
                this.alertData.extraData = null;
                this.alertData.isConfirm = false;
              });
            break;
          case "deleteAllegato":
            const id = this.alertData.extraData.id;
            const index = this.alertData.extraData.index;
            this.autpaeSvc
              .eliminaAllegatoRicheistaPostTramissione(
                id,
                this.richiesta.idFascicolo,
                this.richiesta.id
              )
              .subscribe((result) => {
                this.alertData.display = true;
                this.alertData.title = "Esito Operazione";

                if (result) {
                  //Esito Operazione
                  this.alertData.content =
                    "Cancellazione avvenuta con successo";
                  this.alertData.typ = "success";
                  this.eliminaElemento(null, this.fileArray, index);
                } else {
                  this.alertData.content =
                    "Errore nella cancellazione dell'allegato!";
                  this.alertData.typ = "danger";
                }
                this.alertData.extraData = null;
                this.alertData.isConfirm = false;
              });
            break;
          case "inviaBozza":
            this.sendData(StatoRichiestaPostTrasmissione.Trasmesso);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  checksumFile(file: any) {
    if (file && file.checksum) {
      return file.checksum;
    } else {
      return "";
    }
  }

  descrizioneFile(file: any) {
    if (file && file.descrizione) {
      return file.descrizione;
    } else {
      return "Ulteriore documentazione";
    }
  }

  private mapToFileArrayElem(allegato: AllegatoCustomDTO) {
    return {
      name: allegato.nome,
      id: allegato.id,
      descrizione: allegato.descrizione,
      checksum: allegato.checksum,
      isUrl: allegato.isUrl,
    };
  }

  private upload(file: File, idFascicolo: number, idRichiesta: number): void {
    this.autpaeSvc
      .caricaAllegatoRicheistaPostTramissione(file, idFascicolo, idRichiesta)
      .subscribe((result) => {
        if (result.codiceEsito === CONST.OK) {
          this.fileArray.push(result.payload);
          //this.fileArray.push({ name: result.payload.nome, file: file,checksum:result.payload.checksum });
          this.comForm.controls.allegati.setValue(this.fileArray);
        }
        //this.fileArray.push({ name: result.payload.nome, id: result.payload.id,descrizione:result.payload.descrizione,checksum:result.payload.checksum });
        // this.fileArray.push(result.payload);
        // this.comForm.controls.allegati.setValue(this.fileArray);
      });
  }

  public allegaFile(event: any): void {
    if (event.files) {
      let file = event.files[0];
      let fileCheck = checkFileSizeTypeFn(null, CONST.MAX_5MB);
      let errorValidation = fileCheck(file);
      if (errorValidation) {
        this.showAlertDialog.showAlertDialog(
          true,
          null,
          this.translateService.instant(errorValidation)
        );
        return;
      }
      // if (this.toEdit)
      // {
      this.upload(file, this.idFascicolo, this.idRichiesta);
      // }
      // else
      // {
      //   let arr = this.comForm.controls.allegati.value;
      //   arr.push(file);
      //   this.comForm.controls.allegati.setValue(arr);
      //   this.fileArray.push({ nome: file.name, file: file });
      // }
    }
  }

  public cancellaAllegato(index: number): void {
    if (this.fileArray[index].id) {
      //cancella
      this.alertData = {
        display: true,
        title: "Attenzione",
        content:
          "La cancellazione di questo allegato sarà irreversibile, proseguo?",
        typ: "warning",
        extraData: {
          operazione: "deleteAllegato",
          id: this.fileArray[index].id,
          index: index,
        },
        isConfirm: true,
      };
    } else {
      if (this.comForm.controls.allegati) {
        let fileIndex = this.comForm.controls.allegati.value.indexOf(
          this.fileArray[index].file
        );
        if (fileIndex >= 0)
          this.comForm.controls.allegati.value.splice(fileIndex);
        this.fileArray.splice(index);
        if (!this.comForm.controls.allegati.value)
          this.comForm.controls.allegati.setValue([]);
      }
    }
  }

  trasmetti(){
    this.formToBean(StatoRichiestaPostTrasmissione.Trasmesso);
      if (this.richiesta.motivazione.length < 20) {
        this.alertData = {
          display: true,
          title: "Errore",
          content: "La motivazione deve essere lunga almeno 20 caratteri.",
          typ: "danger",
          extraData: "",
          isConfirm: false,
        };
        return;
      }
      this.alertData = {
        display: true,
        title: "Conferma operazione",
        content: "Confermi l'invio della richiesta?",
        typ: "info",
        extraData: { operazione: "inviaBozza"},
        isConfirm: true,
      };
  }

  formToBean(stato:string){
    this.richiesta.id = this.idRichiesta;
    this.richiesta.idFascicolo = this.idFascicolo;
    this.richiesta.motivazione = this.comForm.controls.motivazione.value;
    this.richiesta.stato = stato;
  }

  sendData(stato: string) {
    //Salva Bozza o Invia Bozza
    this.formToBean(stato);
    // Aggiorno la richiesta indipendentemente dal fatto che l'utente abbia cliccato su "Salva come bozza" o su "Invia"
    this.autpaeSvc
      .updateRichiestePostTrasmissione(this.richiesta)
      .subscribe((result) => {
        if (result.codiceEsito === CONST.OK && result.payload) {
          // Quando la modifica è terminata, se l'utente ha cliccato su "Invia"
          if (stato == StatoRichiestaPostTrasmissione.Trasmesso) {
            // Trasmetto la richiesta, con i dati aggiornati
            this.autpaeSvc
              .trasmettiRichiestePostTrasmissione(
                String(this.richiesta.idFascicolo),
                String(this.richiesta.id)
              )
              .subscribe((result) => {
                if (result.codiceEsito === CONST.OK && result.payload) {
                  this.alertData = {
                    display: true,
                    title: "Operazione completata",
                    content: "Richiesta trasmessa con successo.",
                    typ: "success",
                    extraData: "",
                    isConfirm: false,
                  };
                  this.cancel();
                }
              });
          } else {
            // Se l'utente non ha cliccato su "Invia", torno indietro.
            this.cancel();
          }
        }
      });
  }

  cancel() {
    this.comForm.patchValue({
      motivazione: null,
    });
    this.fileArray = [];
    this.detail = !this.detail;
    this.pagSearch.rows = 5;
    this.pagSearch.page = 1;
    this.caricamentoDati();
  }

  public download(id: number, filename: string): void {
    this.autpaeSvc.downloadAllegatoFascicolo(this.idFascicolo+'',id.toString()).subscribe((result) => {
      if (result.ok) downloadFile(result.body, filename);
    });
  }

  /*---- Elimina un elemento dell'array ----*/
  eliminaElemento(item: any, array: any[], index: number) {
    if (item != null) {
      array.forEach((e) => {
        if (e.id == item.id) item = e;
      });
      index = array.indexOf(item, 0);
    }

    array.splice(index, 1);
    array.slice();
  }
}
