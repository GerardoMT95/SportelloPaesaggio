import { BaseResponse } from './../../../../shared/components/model/base-response';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { CONST } from './../../../../shared/constants';
import { CambioOwnershipRequest, CambioOwnershipResponse } from './../../model/cambio-ownership-model';
import { LoadingService } from './../../../../shared/services/loading.service';
import { CambioOwnershipService } from './../../services/cambio-ownership.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { DelegatoComponent } from 'src/app/shared/components/delegato/delegato.component';
import { Fascicolo, PraticaDelegato } from 'src/app/shared/models/models';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { DialogService } from 'src/app/core/services/dialog.service';

@Component({
  selector: 'app-cambio-ownership',
  templateUrl: './cambio-ownership.component.html',
  styleUrls: ['./cambio-ownership.component.css']
})
export class CambioOwnershipComponent implements OnInit {


  public validazione: boolean;
  public form: FormGroup;

  @ViewChild("delegatoTab", { static: false })
  public delegatoTab: DelegatoComponent;

  public isConfirm: boolean = false;
  public display: boolean = false;
  public title: string = "";
  public content: string = "";
  public typ: string = "";
  public extraData: any;
  public acceptedFiles: string = CONST.mimePDF.join();
  public invalidFileType: string = "";
  public checkSuperato: boolean = false;
  public needSollevamentoIncarico: boolean = false;
  public activeIndex: number = 0;
  public fascicoloDettaglio: Fascicolo = new Fascicolo();
  public fileNameDelega: string;
  public fileNameSollevamento: string;
  public hashDelega: string;
  public hashSollevamento: string;
  public delegato: PraticaDelegato;

  constructor(private router: Router
    , private translateService: TranslateService
    , private loadingService: LoadingService
    , private formBuilder: FormBuilder
    , private service: CambioOwnershipService
    , private allegatoService: AllegatoService
    , private dialog: DialogService

  ) {
    this.translateService.get("generic.INCORRECT_FILE_TYPE_PDF").subscribe(response => {
      this.invalidFileType = response;
    })
   }

  ngOnInit() {
    this.buildForm();
    this.loadingService.emitLoading(false);
  }

  private buildForm(): void {
    this.form = this.formBuilder.group({
      codicePratica: [null, Validators.required]
      , codiceFiscaleProponente: [null, Validators.required]
      , codiceSegreto: [null, Validators.required]
    });
  }


  public ricerca(): void {
    this.validazione = true;
    if (this.form.valid) {
      this.loadingService.emitLoading(true);
      this.service.ricercaCambio(this.getRequest())
        .subscribe(response => {
          if (response && CONST.OK == response.codiceEsito && response.payload && response.payload.esito) {
            this.activeIndex = 1;
            this.checkResponse(response);
          } else {
            this.isConfirm = false;
            this.typ = 'warning'
            this.title = this.translateService.instant("cambioOwnership.titleNoData");
            this.content = this.translateService.instant("cambioOwnership.contentNoData");
            this.display = true;
          }
          this.loadingService.emitLoading(false);
        })
    }
  }

  private checkResponse(response: BaseResponse<CambioOwnershipResponse>): void {
    this.isConfirm = false;
    this.typ = 'success'
    this.title = this.translateService.instant("generic.success");
    this.content = this.translateService.instant("cambioOwnership.praticaTrovata");
    this.display = true;
    this.checkSuperato = true;
    this.fascicoloDettaglio.delegatoPratica = [response.payload.subentro];
    this.needSollevamentoIncarico = response.payload.sollevamentoIncarico;
    this.fileNameDelega = response.payload.subentro.fileNameModulo
    this.fileNameSollevamento = response.payload.subentro.fileNameSollevamento;
    this.hashDelega = response.payload.subentro.hashModulo;
    this.hashSollevamento = response.payload.subentro.hashSollevamento;
  }


  private conferma(): void {
    this.loadingService.emitLoading(true);
    this.service.trasmetti(this.getRequest())
      .subscribe(response => {
        if (response && CONST.OK == response.codiceEsito) {
          if (response.payload && response.payload.length) {
            this.extraData = response.payload;
            this.isConfirm = false;
            this.typ = 'success'
            this.title = this.translateService.instant("cambioOwnership.titleTrasmetti");
            this.content = this.translateService.instant("cambioOwnership.contentTrasmetti");
          }
          this.display = true;
        }
        this.loadingService.emitLoading(false);
      })
  }

  private getRequest(): CambioOwnershipRequest {
    return {
      codicePratica: this.form.controls.codicePratica.value
      , codiceFiscaleProponente: this.form.controls.codiceFiscaleProponente.value
      , codiceSegreto: this.form.controls.codiceSegreto.value
    };
  }

  private getDelegato(): PraticaDelegato {
    return this.delegatoTab.getFormBean()[0];
  }


  public callback(event: any): void {
    if (event.isConfirm) {
      let extraData = this.extraData;
      this.extraData = null;
      if (extraData == "trasmetti") {
        this.conferma();
      }
      if (extraData == "deleteDelega") {
        this.confirmDeleteDelega();
      }
      if (extraData == "deleteSollevamento") {
        this.confirmDeleteSollevamento();
      }
    }
    if (this.extraData) {
      this.router.navigateByUrl("gestione-istanze/" + this.extraData + "/fascicolo");
    }
    this.display = false;
  }

  public resetForm(): void {
    this.validazione = false;
    if (this.checkSuperato)
      this.form.removeControl("delegatoPratica");
    this.form.controls.codicePratica.setValue(null);
    this.form.controls.codiceFiscaleProponente.setValue(null);
    this.form.controls.codiceSegreto.setValue(null);
    this.checkSuperato = false;
    this.fileNameDelega = null;
    this.fileNameSollevamento = null;
    this.activeIndex = 0;
  }

  public onUpload(files: File[]): void {
    let file: File = files[0];
    console.log(files);
    this.loadingService.emitLoading(true);
    let formData: FormData = new FormData();
    formData.set("file", file);
    formData.set("codicePratica", this.getRequest().codicePratica);
    this.service.uploadDelega(formData)
      .subscribe(result => {
        if (CONST.OK === result.codiceEsito) {
          if(result.descrizioneEsito != "INVALID_FORMAT"){
            this.fileNameDelega = file.name;
            this.hashDelega = result.payload;
          } else {
            this.dialog.showDialog(true, 'generic.INCORRECT_FILE_TYPE_PDF', 'generic.errore',
            DialogButtons.CHIUDI,
            (buttonID: string): void =>
            {
            },
            DialogType.ERROR,
            null, {});
          }
        }
        this.loadingService.emitLoading(false);
      });
  }

  public onUploadSollevamento(files: File[]): void {
    let file: File = files[0];
    this.loadingService.emitLoading(true);
    let formData: FormData = new FormData();
    formData.set("file", file);
    formData.set("codicePratica", this.getRequest().codicePratica);
    this.service.uploadSollevamento(formData)
      .subscribe(result => {
        if (CONST.OK === result.codiceEsito) {
          if(result.descrizioneEsito != "INVALID_FORMAT"){
            this.fileNameSollevamento = file.name;
            this.hashSollevamento = result.payload;
          } else {
            this.dialog.showDialog(true, 'generic.INCORRECT_FILE_TYPE_PDF', 'generic.errore',
            DialogButtons.CHIUDI,
            (buttonID: string): void =>
            {
            },
            DialogType.ERROR,
            null, {});
          }
        }
        this.loadingService.emitLoading(false);
      });
  }


  public save(isTrasmetti: boolean = false): void {
    this.loadingService.emitLoading(true);
    this.service.save(this.getDelegato(), this.getRequest().codicePratica)
      .subscribe(result => {
        if (result.codiceEsito == CONST.OK) {
          if (isTrasmetti) {
            this.dialogTramsetti();
          } else {
            this.isConfirm = false;
            this.typ = 'success'
            this.title = this.translateService.instant("cambioOwnership.titleSave");
            this.content = this.translateService.instant("cambioOwnership.contentSave");
            this.display = true;
          }
        }
        this.loadingService.emitLoading(false);
      });
  }

  public trasmetti(): void {
    this.save(true);
  }
  private dialogTramsetti(): void {
    this.isConfirm = true;
    this.extraData = "trasmetti";
    this.typ = 'info'
    this.title = this.translateService.instant("cambioOwnership.titleConfirm");
    this.content = this.translateService.instant("cambioOwnership.contentConfirm");
    this.display = true;
  }

  public downloadTemplateDelega(): void {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadTemplateByCodice('DELEGA_CMIS').subscribe(response => {
      this.loadingService.emitLoading(false);
      this.allegatoService.downloadElemento(response.body, "Delega.pdf");
    });
  }

  public downloadTemplateSollevamentoIncarico(): void {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadTemplateByCodice('SOLLEVAMENTO_INCARICO_CMIS').subscribe(response => {
      this.loadingService.emitLoading(false);
      this.allegatoService.downloadElemento(response.body, "Sollevamento incarico.pdf");
    });
  }

  public deleteDelega(): void {
    this.isConfirm = true;
    this.extraData = "deleteDelega";
    this.typ = 'info'
    this.title = this.translateService.instant("cambioOwnership.titleDeleteConfirm");
    this.content = this.translateService.instant("cambioOwnership.contentDeleteConfirm");
    this.display = true;
  }
  public deleteSollevamento(): void {
    this.isConfirm = true;
    this.extraData = "deleteSollevamento";
    this.typ = 'info'
    this.title = this.translateService.instant("cambioOwnership.titleDeleteConfirm");
    this.content = this.translateService.instant("cambioOwnership.contentDeleteConfirm");
    this.display = true;
  }

  private confirmDeleteDelega(): void {
    this.loadingService.emitLoading(true);
    this.service.deleteDelega(this.getRequest().codicePratica)
      .subscribe(result => {
        if (result.codiceEsito == CONST.OK) {
          this.fileNameDelega = null;
          this.hashDelega = null;
        }
        this.loadingService.emitLoading(false);
      });
  }
  private confirmDeleteSollevamento(): void {
    this.loadingService.emitLoading(true);
    this.service.deleteSollevamento(this.getRequest().codicePratica)
      .subscribe(result => {
        if (result.codiceEsito == CONST.OK) {
          this.fileNameSollevamento = null;
          this.hashSollevamento = null;
        }
        this.loadingService.emitLoading(false);
      });
  }

  public downloadDelega(): void {
    this.loadingService.emitLoading(true);
    this.service.downloadDelega(this.getRequest().codicePratica).subscribe(response => {
      this.loadingService.emitLoading(false);
      this.allegatoService.downloadElemento(response.body, this.fileNameDelega);
    });
  }

  public downloadSollevamentoIncarico(): void {
    this.loadingService.emitLoading(true);
    this.service.downloadSollevamento(this.getRequest().codicePratica).subscribe(response => {
      this.loadingService.emitLoading(false);
      this.allegatoService.downloadElemento(response.body, this.fileNameSollevamento);
    });
  }
}
