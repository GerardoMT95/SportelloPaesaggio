import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { TableConfig } from "src/app/components/model/entity/fascicolo.models";
import { AllegatiService } from "src/app/services/allegati.service";
import { LoadingService } from "src/app/services/loading.service";
import { Location } from '@angular/common'

import { environment } from "src/environments/environment";
import { CONST } from "src/shared/constants";
import { FileUploadComponent } from "../file-upload/file-upload.component";
import { PlainTypeNumericId } from "src/app/components/model/plain-type-numeric-id.model";


@Component({
  selector: "app-verifica-impronta-hash",
  templateUrl: "./verifica-impronta-hash.component.html",
  styleUrls: ["./verifica-impronta-hash.component.css"],
})
export class VerificaImprontaHashComponent implements OnInit {
  documentTableHeaders: TableConfig[] = [];
  tableData = new Array<any>();
  files = new Array<File>();
  mimeTypeForScansioni = CONST.mimeTypeForScansioni.join();
  maxSize = environment.maxSizeUpload;
  areFilesChanged = false;
  @ViewChild("fileUpload") fileUpload: FileUploadComponent;

  public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
    };


  constructor(
    private loadingSvc: LoadingService,
    private router: Router,
    private translateService: TranslateService,
    private allegatiSvc: AllegatiService,
    private location:Location
  ) {}

  ngOnInit() {
    this.translateService
      .get(["generic.nomeDocumento", "VERIFY_HASH.TABLE_HEADER_HASH"])
      .subscribe((response) => {
        this.documentTableHeaders = [
          {
            header: response["generic.nomeDocumento"],
            field: "nomeDocumento",
          },
          {
            header: response["VERIFY_HASH.TABLE_HEADER_HASH"],
            field: "hash",
          },
        ];
      });
  }


  onSelect(event: File[]): void {
    this.areFilesChanged = true;
    this.files = event;
  }

  onAllega(): void {
    // Ricreo la tabella solo se i file sono cambiati
    if (this.areFilesChanged) {
      this.tableData.splice(0, this.tableData.length);
      let i = 0;
      this.files.forEach((element) => {
        this.tableData.push({
          id: i++,
          nomeDocumento: element.name,
          hash: null,
          file: element,
        });
      });
      this.areFilesChanged = false;
      this.fileUpload.clearUpload();
    }
  }

  generateHashCode() {
    this.loadingSvc.emitLoading(true);
    this.tableData.forEach((el) => {
      if (el.hash === null) {
        const formData: FormData = new FormData();
        formData.append("file", el.file);
        this.allegatiSvc
          .calcolaHash(formData)
          .subscribe((response) => {
            el.hash = response.payload;
          });
      }
    });
    this.loadingSvc.emitLoading(false);
  }

  deleteAll(): void {
    this.tableData.splice(0, this.tableData.length);
    this.areFilesChanged = true;
  }

  onRemoveFileFromTable(index: any): void {
    this.alertData.isConfirm = true;
    this.alertData.typ = "danger";
    this.alertData.title = this.translateService.instant('dialog.warning');
    this.alertData.content = this.translateService.instant('DELETE_PROMPT_BUTTON_MESSAGE');
    this.alertData.extraData = { operazione: 'deleteAllegato',index:index};
    this.alertData.display = true;
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'deleteAllegato':
            this.doRemoveFileFromTable(event.extraData.index)
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  doRemoveFileFromTable(index: any): void {
    this.tableData.splice(index, 1);
    for (index; index < this.tableData.length; index++)
      this.tableData[index].id--;
    this.areFilesChanged = true;
  }

  onRemoveFileFromUpload(event: File[]): void {
    this.files = event;
    this.areFilesChanged = true;
  }

  goBack(): void {
    this.location.back();    
  }

  dummyDownload(row: PlainTypeNumericId)
	{
	}
}
