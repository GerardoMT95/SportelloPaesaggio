import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { Allegato, Fascicolo, RequestAllegato, TipoContenuto } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { CONST } from '../../constants';
import { SportelloConfigBean } from '../model/model';
import { Allegati } from './../../models/models';

@Component({
  selector: 'app-documentazione-amministrativa',
  templateUrl: './documentazione-amministrativa.component.html',
  styleUrls: ['./documentazione-amministrativa.component.scss']
})
export class DocumentazioneAmministrativaComponent implements OnInit
{
  @Input() mainForm: FormGroup;
  @Input() fascicolo: Fascicolo;
  @Input() allegati: Allegati;
  @Input() tableData: any[];
  @Input() tipiContenuto: TipoContenuto[];
  @Input("idIntegrazione") idIntegrazione = null;
  @ViewChild('fileUpload', { static: false }) fileUpload: any;
  @Input() disabledForm:boolean = false;
  @Output("actionComplete") actionCompleteEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Input("enteConPagamenti") hasPagamenti:boolean=false;
  //se a true chiama il delete allegato in modo da settare a false il flag di validità della sezione allegati
  @Input("invalidaOnDelete") invalidaOnDelete:boolean=false;
  @Input() sportelloConfig:SportelloConfigBean;
  //@Input() meta: { [key: string]: SelectOption[] };
  documentazioneAmministrativaForm: FormGroup;
  disabled: boolean = true;
  //allegatiPagamentoData: Allegato[];
  documentTableHeaders: TableConfig[];
  selectOptionDocAmmE: SelectOption[];

  constructor(private fb: FormBuilder,
              private translateService: TranslateService,
              private loadingService: LoadingService,
              private allegatoService: AllegatoService,
              private dialogService: DialogService) { }

  ngOnInit()
  {
    this.selectOptionDocAmmE = this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_AMMINISTRATIVA_E")
      .map(tipoContenuto =>
      {
        return {
          value: tipoContenuto.id,
          description: tipoContenuto.descrizione
        };
      });
    this.buildForm();
    //carico gli schemi (righe senza i dati dei file ma con le tipologie ammesse)
    this.addGrigliaPagamentoAllegati();
    this.addGrigliaAllegatiCaricati();
    //carico i dati dei file presenti dal fascicolo.allegati
    this.documentTableHeaders = [
      {
        header: '',
        field: 'status'
      },
      {
        header: 'Tipo documento',
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
        css:'longText',
      }
    ];
    if(!this.idIntegrazione){ //non sono in modalità integrazione....
      this.documentTableHeaders.push({
        header: 'Integrazione',
        field: 'idIntegrazione'
      });
    }
  }

  selectFileType()
  {
    this.disabled = this.documentazioneAmministrativaForm.get('descrizione').value === null;
  }

  buildForm()
  {
    this.documentazioneAmministrativaForm = this.fb.group({
      grigliaPagamentoAllegati: this.fb.array([]),//tipologie di allegati
      descrizione: [this.allegati ? this.allegati.documentazioneAmministrativa.descrizione : ''],
      grigliaAllegatiCaricati: this.fb.array([]) //allegati già presenti con tipologia
    });
    this.mainForm.addControl(
      'documentazioneAmministrativa',
      this.documentazioneAmministrativaForm
    );
  }


  addGrigliaPagamentoAllegati()
  {
    let tipiDocAmministrativaD = this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_AMMINISTRATIVA_D")
      .map(tipoContenuto =>
      {
        return {
          value: tipoContenuto.id,
          description: tipoContenuto.descrizione
        };
      });
    tipiDocAmministrativaD.forEach((item, i) =>
    {
      this.getGrigliaPagamentoAllegati.push(
        this.fb.group({
          id: [null],
          descrizione: [item.description],
          nome: ['', Validators.required],
          data: [null],
          idIntegrazione:[null],
          checksum:[null]
        })
      );
    });
    let allegatiPreesistenti = this.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati;
    if (allegatiPreesistenti)
    {
      allegatiPreesistenti.forEach(allegato =>
      {
        this.appendiAllegatoInGriglia('grigliaPagamentoAllegati', allegato);
      })
    }
  }

  get getGrigliaPagamentoAllegati()
  {
    return (this.documentazioneAmministrativaForm.get(
      'grigliaPagamentoAllegati'
    ) as FormArray);
  }

  
  addGrigliaAllegatiCaricati()
  {
    this.selectOptionDocAmmE.forEach((item, i) =>
    {
      //pusho lo schema e poi i dati
      this.getGrigliaAllegatiCaricati.push(
        this.fb.group({
          id: [null],
          descrizione: [item.description],
          nome: ['', Validators.required],
          data: [null],
          idIntegrazione:[null],
          checksum:[null]
        })
      );
    });
    let allegatiPreesistenti = this.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati;
    if (allegatiPreesistenti)
    {
      allegatiPreesistenti.forEach(allegato =>
      {
        this.appendiAllegatoInGriglia('grigliaAllegatiCaricati', allegato);
      })
    }
  }

  get getGrigliaAllegatiCaricati()
  {
    return (this.documentazioneAmministrativaForm.get('grigliaAllegatiCaricati') as FormArray);
  }

  /**
   * caricamento dal file uploader con selezione della tipologia dalla select
   */
  onFileSelect(event)
  {
    if (event.files.length > 0)
    {
      const file: File = event.files[0];
      const idDescription = this.documentazioneAmministrativaForm.value.descrizione;
      const description = this.tipiContenuto.find(el => el.id == idDescription).descrizione;
      if (!description)
      {
        this.dialogService.showDialog(true,
          'TAB_ALLEGATI.TIPO_CONTENUTO_NON_TROVATO',
          'generic.info',
          DialogButtons.CHIUDI,
          (buttonID: string): void => { },
          DialogType.INFORMATION,
          null);
        return;
      }
      this.caricaDocumentoAmministrativo({ file: file, fileType: description },
        (uploadedAttachment: Allegato) =>
        {
          this.appendiAllegatoInGriglia('grigliaAllegatiCaricati', uploadedAttachment);
          this.fileUpload.clear();
          this.documentazioneAmministrativaForm.get('descrizione').setValue(null);
          this.dialogService.showDialog(true,
            'generic.operazione_ok',
            'generic.info',
            DialogButtons.CHIUDI,
            (buttonID: string): void => { },
            DialogType.SUCCESS,
            null);
        });
    }
  }

  appendiAllegatoInGriglia(nomeFormArray, allegato: Allegato)
  {
    let inseritoInTabella: boolean = false;
    let formArray = (this.documentazioneAmministrativaForm.get(nomeFormArray) as FormArray);
    (this.documentazioneAmministrativaForm.get(nomeFormArray).value).forEach((item, index) =>
    {
      if (item.descrizione === allegato.descrizione && item.id == null)
      {
        (formArray.at(index) as FormGroup).patchValue(allegato);
        inseritoInTabella = true;
      }
    });
    if (!inseritoInTabella)
    {
      formArray.push(
        this.fb.group({
          id: [allegato.id, Validators.required],
          descrizione: [allegato.descrizione],
          nome: [allegato.nome, Validators.required],
          data: [allegato.data],
          idIntegrazione: [allegato.idIntegrazione],
          checksum: [allegato.checksum]
        })
      )
    }
  }

  /**
   * caricamento direttamente dalla riga della tabella 
   * (non devo aggiungere righe, ma avvalorare con i dati del file caricato)
   */
  uploadFileFromTable(event)
  {
    const file = event.file;
    const fileType = event.fileType;
    let sezioneDelFIle = this.tipiContenuto.find(el => el.descrizione == fileType).sezione;
    let grigliaDiCompetenza='grigliaAllegatiCaricati';
    if (sezioneDelFIle == "DOC_AMMINISTRATIVA_D"){
      grigliaDiCompetenza='grigliaPagamentoAllegati';
    }
    this.caricaDocumentoAmministrativo(event,
        (uploadedAttachment) =>
        {
          (this.documentazioneAmministrativaForm.get(grigliaDiCompetenza).value).forEach((item, index) =>
          {
            if (item.descrizione === fileType)
            {
              ((this.documentazioneAmministrativaForm.get(grigliaDiCompetenza) as FormArray).at(index) as FormGroup)
              .patchValue(uploadedAttachment);
            }
          });
        });
  }

  eliminaDocumento(id, callback)
  {
    this.loadingService.emitLoading(true);
    let req = new RequestAllegato();
    req.praticaId = this.fascicolo.id
    req.allegatoId = id;
    req.integrazioneId=this.idIntegrazione ? this.idIntegrazione : null;
    if(this.invalidaOnDelete){
      req.invalidaSezione=true;
    }
    this.allegatoService.eliminaDocumento(req, '/allegati/delete_allegato.pjson')
      .then((response) =>
      {
        let ret = response.payload;
        if (ret != null)
        {
          callback(id);
        }
      }).finally(() => this.loadingService.emitLoading(false));
  }


  findIndexFromAllegatoId(allegatiId: string, formArray: FormArray)
  {
    let ret = -1;
    for (let i = 0; i < formArray.length; i++)
    {
      if (formArray.at(i).get('id').value == allegatiId)
      {
        ret = i;
      }
    }
    return ret;
  }

  deleteAttachmentFromGriglia(index,grigliaAttachmentFormArray:FormArray)
  {
    let callback = (allegatoId: string) =>
    {
      let esisteAltro: boolean = false;
      let indice = this.findIndexFromAllegatoId(allegatoId, grigliaAttachmentFormArray);
      //se ho altre righe con stessa descrizione, allora la elimino, altrimenti se è l'ultima la lascio nel form
      let tipoContenuto = (grigliaAttachmentFormArray.at(indice) as FormGroup).get('descrizione').value;
      let type = this.tipiContenuto.find(el => el.descrizione == tipoContenuto).sezione;
      for (let i = 0; i < grigliaAttachmentFormArray.length; i++)
      {
        if ((grigliaAttachmentFormArray.at(i) as FormGroup).get('descrizione').value == tipoContenuto && i != indice)
        {
          esisteAltro = true;
        }
      }
      (grigliaAttachmentFormArray.at(indice) as FormGroup).get('id').patchValue(null);
      (grigliaAttachmentFormArray.at(indice) as FormGroup).get('nome').patchValue('');
      (grigliaAttachmentFormArray.at(indice) as FormGroup).get('data').patchValue(null);
      (grigliaAttachmentFormArray.at(indice) as FormGroup).get('checksum').patchValue(null);
      if (esisteAltro)
      {
        grigliaAttachmentFormArray.removeAt(indice);
      } 
      this.actionCompleteEmitter.emit({ action: "DELETE", allegatoId, type });
    };
    this.eliminaDocumento(index, callback);
  }

  deleteAttachment(index)
  {
    this.deleteAttachmentFromGriglia(index,this.getGrigliaAllegatiCaricati)
  }

  deletePaymentAttachment(index: number)
  {
    this.deleteAttachmentFromGriglia(index,this.getGrigliaPagamentoAllegati)
  }

  
  /********* NUOVI METODI */
  caricaDocumentoAmministrativo($event, callback)
  {
    if ($event.file)
    {
      const file: File = $event.file;
      const fileType: string = $event.fileType;
      //mi prendo l'id del tipo di file:
      let idTipoContenuto = this.tipiContenuto.find(el => el.descrizione == fileType).id;
      if (!idTipoContenuto)
      {
        this.dialogService.showDialog(true,
          'TAB_ALLEGATI.TIPO_CONTENUTO_NON_TROVATO',
          'generic.info',
          DialogButtons.CHIUDI,
          (buttonID: string): void => { },
          DialogType.INFORMATION,
          null);
        return;
      }
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
      let type = this.tipiContenuto.find(el => el.descrizione == fileType).sezione;
      const formData: FormData = new FormData();
      formData.append('file', file);
      let allegatoDoc = new RequestAllegato();
      allegatoDoc.praticaId = this.fascicolo.id;
      allegatoDoc.tipiContenuto = [];
      allegatoDoc.tipiContenuto.push(idTipoContenuto);
      allegatoDoc.integrazioneId = this.idIntegrazione ? this.idIntegrazione : null;
      formData.append('req', new Blob([JSON.stringify(allegatoDoc)], { type: "application/json" }));
      this.loadingService.emitLoading(true);
      this.allegatoService.uploadAllegatoDocumento(formData, '/allegati/upload_allegato.pjson')
        //.pipe(takeUntil(this.unsubscribe$))
        .then((response) =>
        {
          let ret = response.payload;
          if (ret != null)
          {
            callback(response.payload);
            this.actionCompleteEmitter.emit({ action: "UPLOAD", file: response.payload, type });
          }
        })
        .finally(() => this.loadingService.emitLoading(false));
    }
  }

  scaricaDocumento(idAndName)
  {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/allegati/download.pjson')
      .toPromise()
      .then(data =>
      {
        var blob = new Blob([data.body], { type: data.body.type });
        this.allegatoService.downloadElemento(blob, idAndName.name);
      })
      .catch(error =>
      {
        console.log('download error:', JSON.stringify(error));
        this.loadingService.emitLoading(false);
      })
      .finally(() =>
      {
        this.loadingService.emitLoading(false);
      });
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
