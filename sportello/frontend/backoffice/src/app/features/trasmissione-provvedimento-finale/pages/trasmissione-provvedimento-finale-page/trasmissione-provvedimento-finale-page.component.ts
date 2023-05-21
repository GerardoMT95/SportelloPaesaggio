import { state } from '@angular/animations';
import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { all } from 'esri/renderers/smartMapping/symbology/support/colorRamps';
import { downloadFile, copyOf } from 'src/app/core/functions/generic.utils';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { GestioneIstanzaComunicazioniService } from 'src/app/features/gestione-istanza-comunicazioni/service/gestione-istanza-comunicazioni.service';
import { CONST } from 'src/app/shared/constants';
import { Allegato, Fascicolo, ProvvedimentoFinale } from 'src/app/shared/models/models';
import { ProvvedimentoFinaleService } from 'src/app/shared/services/provvedimento-finale/provvedimento-finale.service';
import { Ente, StatoEnum } from './../../../../shared/models/models';
import { HttpAllegatoService } from './../../../../shared/services/http-allegato.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { UserService } from './../../../../shared/services/user.service';
import { DestinatarioComunicazioneDTO } from './../../../funzionalita-amministratore-applicazione/models/admin-functions.models';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { documentTypes, FILE_TABLE } from './../../trasmissione-provvedimento-finale-config';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { TranslateService } from '@ngx-translate/core';
import { IButton } from 'src/app/core/models/dialog.model';

@Component({
  selector: 'app-trasmissione-provvedimento-finale-page',
  templateUrl: './trasmissione-provvedimento-finale-page.component.html',
  styleUrls: ['./trasmissione-provvedimento-finale-page.component.scss']
})
export class TrasmissioneProvvedimentoFinalePageComponent implements OnInit {
  public form: FormGroup;
  //public documentTableHeaders: TableConfig[];
  public types = documentTypes;
  public switchOnDestinatario = false;
  public statoEnum = StatoEnum;
  public provvedimento: ProvvedimentoFinale = new ProvvedimentoFinale();
  public validation: boolean = false; // indica se ho premuto sul tasto "continua" oppure no
  public displayRubrica: boolean = false;
  public displayRubricaEnte: boolean = false;
  public displayRubricaIstituzionale: boolean = false;
  public displayNuovoDestinatario: boolean = false;

  public enti: Ente[] = [];
  public entiIstituzionali: Ente[] = [];
  constructor(private formBuilder: FormBuilder,
    private dialogService: CustomDialogService,
    private userService: UserService,
    private service: ProvvedimentoFinaleService,
    private comunicazioneService: GestioneIstanzaComunicazioniService,
    //private allegatiService: HttpAllegatoService,
    private allegatoService: AllegatoService,
    private shared: DataService,
    private loading: LoadingService,
    private translate: TranslateService) {
    this.loading.emitLoading(true);
  }

  ngOnInit() {
    this.loading.emitLoading(true);
    this.service.find(this.shared.idPratica).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        this.provvedimento = response.payload;
        this.buildForm();
      }
      this.loading.emitLoading(false);
    });
  }

  public disableEdit(): boolean {
    if (this.form.disabled || this.fascicolo.attivitaDaEspletare === this.statoEnum.Trasmessa) {
      return true;
    }
  }

  buildForm() {
    let provvedimento = this.provvedimento ? this.provvedimento : new ProvvedimentoFinale();
    this.form = this.formBuilder.group
      ({
        id: [provvedimento.id],
        idPratica: [provvedimento.idPratica],
        numeroProvvedimento: [provvedimento.numeroProvvedimento, Validators.required],
        dataRilascioAutorizzazione: [provvedimento.dataRilascioAutorizzazione, Validators.required],
        esitoProvvedimento: [provvedimento.esitoProvvedimento, Validators.required],
        rup: [provvedimento.rup, Validators.required],
        draft: [provvedimento.draft, Validators.required],
        idCorrispondenza: [provvedimento.idCorrispondenza]
      });

    if (this.disabled)
      this.form.disable();
  }

  public save(callback?: () => void): void {
    this.loading.emitLoading(true);
    this.service.update(this.form.getRawValue()).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        this.provvedimento = { ...this.provvedimento, ...response.payload };
        this.form.patchValue({ ...this.provvedimento });
        this.loading.emitLoading(false);
        if (callback)
          callback();
      }
      else
        this.loading.emitLoading(false);
    });
  }

  public cancel(): void { this.form.reset(); }

  public upload(event: any): void {
    this.loading.emitLoading(true);
    this.service.upload(event.file, event.type, this.shared.idPratica).subscribe(response => {
      if (response.codiceEsito === CONST.OK) {
        if (!this.allegati || this.allegati.length === 0)
          this.provvedimento.allegati = [];
        this.provvedimento.allegati.push(response.payload);
      }
      this.loading.emitLoading(false);
    });
  }

  public delete(event: Allegato): void {
    let title = "Attenzione";
    let message = "Confermi la cancellazione dell'allegato '" + event.nome + "'?";
    this.dialogService.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, (buttonId) => {
      if (buttonId == 1) {
        this.loading.emitLoading(true);
        this.service.remove(event.id as string).subscribe(response => {
          if (response.codiceEsito === CONST.OK) {
            let index = this.provvedimento.allegati.map(m => m.id).indexOf(event.id);
            if (index != -1)
              this.provvedimento.allegati.splice(index, 1);
          }
          this.loading.emitLoading(false);
        });
      }
    }, DialogType.WARNING);
  }

  public download(event: Allegato): void {
    this.loading.emitLoading(true);
    this.allegatoService.downloadAllegatoFascicolo(event.id as string, this.fascicolo.id, '/istruttoria/allegati/download.pjson').subscribe(
      //this.allegatiService.callDownloadAllegato(event.id as string).subscribe(
      response => {
        if (response.status == 200)
          downloadFile(response.body, event.nome);
        this.loading.emitLoading(false);
      });
  }

  public continue(): void {
    this.validation = true;
    if (this.form.valid && this.allegati.length > 0) {
      this.validation = false;
      this.save(() => { this.switchOnDestinatario = true; });
    }
  }

  public back(): void { this.switchOnDestinatario = false; }

  public addDestinatario(event: DestinatarioComunicazioneDTO): void {
    event.tipo = "TO";
    if (!this.provvedimento.ulterioriDestinatari)
      this.provvedimento.ulterioriDestinatari = [];
    this.provvedimento.ulterioriDestinatari.push(event);
  }

  public removeDestinatario(event: DestinatarioComunicazioneDTO): void {
    let index = this.provvedimento.ulterioriDestinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.provvedimento.ulterioriDestinatari.splice(index, 1);
  }

  public cambiaTipologia(event: DestinatarioComunicazioneDTO): void {
    let index = this.provvedimento.ulterioriDestinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.provvedimento.ulterioriDestinatari[index].tipo = event.tipo;
  }

  public saveDestinatari(): void {
    this.loading.emitLoading(true);
    this.service.saveDestinatari(this.destinatari, this.shared.idPratica).subscribe(response => {
      if (response.codiceEsito === CONST.OK)
        this.provvedimento.ulterioriDestinatari = response.payload;
      this.loading.emitLoading(false);
    });
  }

  public cancelDestinatari(): void { this.provvedimento.ulterioriDestinatari = []; }

  public trasmetti(): void {
    let title = "Conferma trasmissione";
    let message = "Confermi la trasmissione del provvedimento finale per la pratica " + this.shared.codicePratica + "? Confermando non si potranno più modificare le informazioni inserite";
    this.dialogService.showDialog(true, message, title, DialogButtons.OK_CANCEL, (buttonId) => {
      if (buttonId == 1) {
        this.loading.emitLoading(true);
        this.service.trasmettiProvvedimento(this.destinatari, this.shared.idPratica).subscribe(response => {
          if (response.codiceEsito === CONST.OK) {
            let fascicolo = copyOf(this.shared.fascicolo);
            fascicolo.attivitaDaEspletare = StatoEnum.Trasmessa;
            fascicolo.dataTrasmissioneProvvedimentoFinale = new Date();
            this.shared.fascicolo = fascicolo;
            this.switchOnDestinatario = false;
            this.form.disable();
            let title: string = this.translate.instant("generic.successo");
            let message: string = this.translate.instant("generic.operazioneOk");
            let buttons: IButton[] = DialogButtons.CHIUDI
            this.dialogService.showDialog(true, message, title, buttons, (buttonId: number) => {
            }, DialogType.SUCCESS);
          }
          this.loading.emitLoading(false);
        });
      }
    }, DialogType.INFORMATION);
  }

  public generaPDF(): void {
    this.loading.emitLoading(true);
    this.service.saveDestinatari(this.destinatari, this.shared.idPratica).toPromise()
      .then(
        response => {
          this.loading.emitLoading(false);
          if (response.codiceEsito === CONST.OK)
            this.provvedimento.ulterioriDestinatari = response.payload;
          this.loading.emitLoading(true);
          return this.service.generaPDF(this.fascicolo.id).toPromise();
        }).then(respPdf => {
          this.loading.emitLoading(false);
          if (respPdf.status === 200)
            downloadFile(respPdf.body, "ricevuta_trasmissione_" + this.fascicolo.codicePraticaAppptr + ".pdf");
        });
    /*this.loading.emitLoading(true);
    this.service.generaPDF(this.fascicolo.id).subscribe(response =>
    {
      if(response.status === 200)
        downloadFile(response.body, "ricevuta_trasmissione_"+this.fascicolo.codicePraticaAppptr+".pdf");
      this.loading.emitLoading(false);
    });*/
  }

  public getRicevutaTrasmissione() {
    this.loading.emitLoading(true);
    this.service.getRicevutaTrasmissione(this.fascicolo.id).toPromise().then(
      respPdf => {
        this.loading.emitLoading(false);
        if (respPdf.status === 200)
          downloadFile(respPdf.body, "ricevuta_trasmissione_" + this.fascicolo.codicePraticaAppptr + ".pdf");
      }
    );
  }

  get fascicolo(): Fascicolo { return this.shared.fascicolo; }
  get allegati(): Array<Allegato> { return this.provvedimento && this.provvedimento.allegati ? this.provvedimento.allegati : []; }
  get destinatariFissi(): Array<DestinatarioComunicazioneDTO> { return this.provvedimento && this.provvedimento.destinatariFissi ? this.provvedimento.destinatariFissi : []; }
  get destinatari(): Array<DestinatarioComunicazioneDTO> { return this.provvedimento && this.provvedimento.ulterioriDestinatari ? this.provvedimento.ulterioriDestinatari : []; }
  get headers() { return FILE_TABLE; }
  get dataValid(): boolean { return this.provvedimento && this.form ? this.form.valid && this.allegati.length > 0 && this.allegati.some(s => s.type == '951') : false; }
  get disabled(): boolean { return this.shared.status === StatoEnum.Trasmessa; }

}
