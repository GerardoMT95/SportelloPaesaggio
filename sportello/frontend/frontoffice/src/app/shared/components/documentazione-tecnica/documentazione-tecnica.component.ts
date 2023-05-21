import { Allegati } from './../../models/models';
import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Fascicolo, TipoContenuto, Allegato, RequestAllegato } from 'src/app/shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { TableConfig } from 'src/app/core/models/header.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectItem } from 'primeng/primeng';
import { CONST } from '../../constants';
import { TranslateService } from '@ngx-translate/core';
import { requiredNotEmpty } from '../../validators/customValidator';


@Component({
  selector: 'app-documentazione-tecnica',
  templateUrl: './documentazione-tecnica.component.html',
  styleUrls: ['./documentazione-tecnica.component.scss']
})
export class DocumentazioneTecnicaComponent implements OnInit {

  documentTableHeaders: TableConfig[] = [];
  @Input() fascicolo: Fascicolo;
  @Input() allegati: Allegati;
  @Input() mainForm: FormGroup;
  @Input() tableData: any[];
  @Input() tipiContenuto: TipoContenuto[];
  @Input() disabledForm:boolean = false;
  @Input("idIntegrazione") idIntegrazione: number;
  @ViewChild('fileUpload', {static:false}) fileUpload: any;

  @Output("actionComplete") actionCompleteEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Input("invalidaOnDelete") invalidaOnDelete:boolean=false;

  documentazioneTecnicaForm: FormGroup;
  formInserimento: FormGroup;
  disabled: boolean = true;
  selectOptionDocTecnica: SelectItem[];

  constructor(private fb: FormBuilder,
    private allegatoService: AllegatoService,
    private loadingService: LoadingService,
    private dialogService: DialogService,
    private translateService:TranslateService) { }

  ngOnInit() {
    this.selectOptionDocTecnica = this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_TECNICA")
      .map(tipoContenuto => {
        return {
          value: tipoContenuto.id,
          label: tipoContenuto.descrizione
        };
      });
    this.buildForm();
    this.addGrigliaAllegatiCaricati();
    this.documentTableHeaders = [
      {
        header: '',
        field: 'status'
      },
      {
        header: 'Tipologia contenuto',
        field: 'tipiContenuto',
      },
      {
        header: 'Nome Contenuto',
        field: 'descrizione'
      },
      {
        header: 'Nome file',
        field: 'nome'
      },
      {
        header: 'Data caricamento',
        field: 'data'
      },
      {
        header: 'Impronta hash',
        field: 'checksum',
        css:'longText'
      }
    ];
    if(!this.idIntegrazione){ //non sono in modalità integrazione....
      this.documentTableHeaders.push({
        header: 'Integrazione',
        field: 'idIntegrazione'
      });
    }
  }

  dummy($event) { }

  buildForm() {
    this.formInserimento = this.fb.group({
      descrizione: [null, Validators.required],
      nomeContenuto: [null, requiredNotEmpty],
    });
    this.documentazioneTecnicaForm = this.fb.group({
      grigliaAllegatiCaricati: this.fb.array([])
    });
    this.mainForm.addControl(
      'documentazioneTecnica',
      this.documentazioneTecnicaForm
    );
  }

  addGrigliaAllegatiCaricati() {
    let allegatiPreesistenti = this.allegati.documentazioneTecnica.grigliaAllegatiCaricati;
    if (allegatiPreesistenti) {
      allegatiPreesistenti.forEach(allegato => {
        this.appendiAllegatoInGriglia('grigliaAllegatiCaricati', allegato);
      });
    }
  }

  appendiAllegatoInGriglia(nomeFormArray, allegato: Allegato) {
    let formArray = (this.documentazioneTecnicaForm.get(nomeFormArray) as FormArray);
    formArray.push(
      this.fb.group({
        id: [allegato.id, Validators.required],
        descrizione: [allegato.descrizione],
        nome: [allegato.nome, Validators.required],
        data: [allegato.data],
        tipiContenuto: [allegato.tipiContenuto],
        idIntegrazione:[allegato.idIntegrazione],
        checksum:[allegato.checksum]
      })
    )
  }

  get getGrigliaAllegatiCaricati() {
    return (this.documentazioneTecnicaForm.get('grigliaAllegatiCaricati') as FormArray);
  }

  eliminaDocumento(id, callback) {
    this.loadingService.emitLoading(true);
    let req = new RequestAllegato();
    req.praticaId = this.fascicolo.id
    req.allegatoId = id;
    req.integrazioneId=this.idIntegrazione ? this.idIntegrazione : null;
    if(this.invalidaOnDelete){
      req.invalidaSezione=true;
    }
    this.allegatoService.eliminaDocumento(req, '/allegati/delete_allegato.pjson')
      .then((response) => {
        let ret = response.payload;
        if (ret != null) {
          callback(id);
          this.actionCompleteEmitter.emit({ action: "DELETE", allegatoId: id, type: "DOC_TECNICA" });
        }
      }).finally(() => this.loadingService.emitLoading(false));
  }


  findIndexFromAllegatoId(allegatiId: string, formArray: FormArray) {
    let ret = -1;
    for (let i = 0; i < formArray.length; i++) {
      if (formArray.at(i).get('id').value == allegatiId) {
        ret = i;
      }
    }
    return ret;
  }

  deleteAttachment(index) {
    let callback = (allegatoId: string) => {
      let indice = this.findIndexFromAllegatoId(allegatoId, this.getGrigliaAllegatiCaricati);
      //se ho altre righe con stessa descrizione, allora la elimino, altrimenti se è l'ultima la lascio nel form
      let tipoContenuto = (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('descrizione').value;
      this.getGrigliaAllegatiCaricati.removeAt(indice);
    };
    this.eliminaDocumento(index, callback);
  }

  doUpload($event) {
    const tipiContenuto = this.formInserimento.value.descrizione;
    const nomeContenuto = this.formInserimento.value.nomeContenuto;
    if (!this.formInserimento.valid || 
      !nomeContenuto || nomeContenuto == "" || !tipiContenuto ||
      !Array.isArray(tipiContenuto) || tipiContenuto.length == 0 ||
      !$event.files || $event.files.length <= 0) {
      this.dialogService.showDialog(true,
        'generic.allRequired',
        'generic.info',
        DialogButtons.CHIUDI,
        (buttonID: string): void => { },
        DialogType.INFORMATION,
        null);
        this.fileUpload.clear();
      return;
    }
    const file: File = $event.files[0];
    let fileTypeOk=this.checkFileType(file);
    if(fileTypeOk){
      this.dialogService.showDialog(true,
        fileTypeOk,
        'generic.warning',
        DialogButtons.CHIUDI,
        (buttonID: string): void => { },
        DialogType.INFORMATION,
        null);
        this.fileUpload.clear();
        return;
    }
    this.caricaDocumentoTecnico({ file: file, fileType: tipiContenuto, nomeContenuto: nomeContenuto },
      (uploadedAttachment: Allegato) => {
        this.appendiAllegatoInGriglia('grigliaAllegatiCaricati', uploadedAttachment);
        this.formInserimento.reset();
        this.fileUpload.clear();
        this.dialogService.showDialog(true,
          'generic.operazione_ok',
          'generic.info',
          DialogButtons.CHIUDI,
          (buttonID: string): void => { },
          DialogType.SUCCESS,
          null);
      });
  }


  caricaDocumentoTecnico($event, callback) {
    if ($event.file) {
      this.loadingService.emitLoading(true);
      const file: File = $event.file;
      const fileType: SelectItem[] = $event.fileType;
      //mi prendo l'id del tipo di file:
      const formData: FormData = new FormData();
      formData.append('file', file);
      let allegatoDoc = new RequestAllegato();
      allegatoDoc.praticaId = this.fascicolo.id;
      allegatoDoc.tipiContenuto = fileType.map(selectItem => selectItem.value);
      allegatoDoc.nomeContenuto = $event.nomeContenuto;
      allegatoDoc.integrazioneId = this.idIntegrazione ? this.idIntegrazione : null;
      formData.append('req', new Blob([JSON.stringify(allegatoDoc)], { type: "application/json" }));
      this.allegatoService.uploadAllegatoDocumento(formData, '/allegati/upload_allegato.pjson')
        .then((response) => {
          let ret = response.payload;
          if (ret != null) {
            callback(response.payload);
            this.actionCompleteEmitter.emit({action: "UPLOAD", file: response.payload, type: "DOC_TECNICA"});
          }
        })
        .finally(() => 
        {
          this.loadingService.emitLoading(false);
          this.fileUpload.clear();
        }
        );
    }
  }

  scaricaDocumento(idAndName) {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/allegati/download.pjson')
      .toPromise()
      .then(data => {
        var blob = new Blob([data.body], { type: data.body.type });
        this.allegatoService.downloadElemento(blob, idAndName.name);
      })
      .catch(error => {
        console.log('download error:', JSON.stringify(error));
        this.loadingService.emitLoading(false);
      })
      .finally(() => {
        this.loadingService.emitLoading(false);
      });
  }

  legendaTipiContenuto(): TipoContenuto[] {
    return this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_TECNICA");
  }

  getFileTypeSelect() {
    return this.selectOptionDocTecnica;
  }

  public checkFileType(file:File):string{
    let mimeOk=CONST.mimeTypeForScansioni;
    mimeOk.push(...CONST.mimeTypeZip);
    if(!mimeOk.includes(file.type)){
        let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:mimeOk});
        message+=" tipo file selezionato: "+file.type;
       return message;
    }
    return null;
 }

}
