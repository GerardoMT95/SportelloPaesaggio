import { CustomDialogService } from './../../../core/services/dialog.service';
import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { Allegato, Fascicolo, RequestAllegato, TipoContenuto } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { Allegati } from './../../models/models';
import { SportelloConfigBean } from '../model/model';

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

  @Output("actionComplete") actionCompleteEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Input("enteConPagamenti") hasPagamenti:boolean=false;
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
    private dialogService: CustomDialogService) { }

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
        field: 'checksum'
      },
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
    /* 
        if (this.documentazioneAmministrativaForm.get('descrizione').value === null)
        {
          this.disabled = true;
        } else
        {
          this.disabled = false;
        } */
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

  /*getGrigliaPagamentoAllegatiFields(index: number, field: string) {
    if (field === 'nome') {
      return this.fascicolo.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati[index].nome;
    } else {
      return this.fascicolo.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati[index].data;
    }
  }*/

  addGrigliaPagamentoAllegati()
  {
    let tipiDocAmministrativaE = this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_AMMINISTRATIVA_D")
      .map(tipoContenuto =>
      {
        return {
          value: tipoContenuto.id,
          description: tipoContenuto.descrizione
        };
      });
    tipiDocAmministrativaE.forEach((item, i) =>
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
    //this.fascicolo.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati;
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

  /*getGrigliaAllegatiCaricatiFields(index: number, field: string) {
    if (field === 'nome') {
      return this.fascicolo.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati[index].nome;
    } else {
      return this.fascicolo.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati[index].data;
    }
  }*/

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
        //(formArray.at(index) as FormGroup).get('nome').markAsDirty();
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
          idIntegrazione:[allegato.idIntegrazione],
          checksum:[allegato.checksum]
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
    if (sezioneDelFIle == "DOC_AMMINISTRATIVA_D" /*ricevuta oneri*/)
    {
      //effettuo l'upload e poi allineo il form con i dati dell'upload
      this.caricaDocumentoAmministrativo(event,
        (uploadedAttachment) =>
        {
          (this.getGrigliaPagamentoAllegati.at(0) as FormGroup).patchValue(uploadedAttachment)
        });
    } else
    {//altro documento della sezione DOC AMMINISTRATIVA E
      this.caricaDocumentoAmministrativo(event,
        (uploadedAttachment) =>
        {
          (this.documentazioneAmministrativaForm.get('grigliaAllegatiCaricati').value).forEach((item, index) =>
          {
            if (item.descrizione === fileType)
            {
              (this.getGrigliaAllegatiCaricati.at(index) as FormGroup).patchValue(uploadedAttachment);
              //(this.getGrigliaAllegatiCaricati.at(index) as FormGroup).get('nome').markAsDirty();
            }
          });
        });
    }
  }

  eliminaDocumento(id, callback)
  {
    this.loadingService.emitLoading(true);
    let req = new RequestAllegato();
    req.praticaId = this.fascicolo.id
    req.allegatoId = id;
    req.integrazioneId=this.idIntegrazione ? this.idIntegrazione : null;
    this.allegatoService.eliminaDocumento(req, '/allegati/delete_allegato.pjson')
      .then((response) =>
      {
        let ret = response.payload;
        if (ret != null)
        {
          callback(id);
        }
        this.loadingService.emitLoading(false)
      });
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

  deleteAttachment(index)
  {
    let callback = (allegatoId: string) =>
    {
      let esisteAltro: boolean = false;
      let indice = this.findIndexFromAllegatoId(allegatoId, this.getGrigliaAllegatiCaricati);
      //se ho altre righe con stessa descrizione, allora la elimino, altrimenti se è l'ultima la lascio nel form
      let tipoContenuto = (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('descrizione').value;
      let type = this.tipiContenuto.find(el => el.descrizione == tipoContenuto).sezione;
      console.log("tipo contenuto in queta merda ", tipoContenuto);
      for (let i = 0; i < this.getGrigliaAllegatiCaricati.length; i++)
      {
        if ((this.getGrigliaAllegatiCaricati.at(i) as FormGroup).get('descrizione').value == tipoContenuto && i != indice)
        {
          esisteAltro = true;
        }
      }
      (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('id').patchValue(null);
      (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('nome').patchValue('');
      //(this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('nome').markAsDirty();
      (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('data').patchValue(null);
      if (esisteAltro)
        this.getGrigliaAllegatiCaricati.removeAt(indice);
      this.actionCompleteEmitter.emit({ action: "DELETE", allegatoId, type });
    };
    this.eliminaDocumento(index, callback);
  }

  deletePaymentAttachment(index: number)
  {
    let callback = (allegatoId: string) =>
    {
      let indice = this.findIndexFromAllegatoId(allegatoId, this.getGrigliaPagamentoAllegati);
      if (indice > 0)
      {//riga aggiuntiva...
        this.getGrigliaPagamentoAllegati.removeAt(indice);
      } else
      {
        (this.getGrigliaPagamentoAllegati.at(indice) as FormGroup).get('id').patchValue(null);
        (this.getGrigliaPagamentoAllegati.at(indice) as FormGroup).get('nome').patchValue('');
        //(this.getGrigliaPagamentoAllegati.at(indice) as FormGroup).get('nome').markAsDirty();
        (this.getGrigliaPagamentoAllegati.at(indice) as FormGroup).get('data').patchValue(null);
      }
    };
    this.eliminaDocumento(index, callback);
  }

  /*getFileTypeSelect() {
    switch (this.fascicolo.tipoProcedimento) {
        case '1': {
          return this.meta.descrizioneAmministrativaArt146;
        }
        case '2': {
          return this.meta.descrizioneAmministrativaArt90;
        }
        case '3': {
          return this.meta.descrizioneAmministrativaArt167;
        }
        case '4': {
          return this.meta.descrizioneAmministrativaArt91;
        }
    }
  }*/

  /********* NUOVI METODI */
  caricaDocumentoAmministrativo($event, callback)
  {
    if ($event.file)
    {
      this.loadingService.emitLoading(true);
      const file: File = $event.file;
      const fileType: string = $event.fileType;
      //mi prendo l'id del tipo di file:
      let idTipoContenuto = this.tipiContenuto.find(el => el.descrizione == fileType).id;
      if (!idTipoContenuto)
      {
        this.loadingService.emitLoading(false);
        this.dialogService.showDialog(true,
          'TAB_ALLEGATI.TIPO_CONTENUTO_NON_TROVATO',
          'generic.info',
          DialogButtons.CHIUDI,
          (buttonID: string): void => { },
          DialogType.INFORMATION,
          null);
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
      this.allegatoService.uploadAllegatoDocumento(formData, '/allegati/upload_allegato.pjson')
        //.pipe(takeUntil(this.unsubscribe$))
        .then((response) =>
        {
          let ret = response.payload;
          if (ret != null)
          {
            callback(response.payload);
            this.actionCompleteEmitter.emit({ action: "UPLOAD", file: response.payload, type });
            this.loadingService.emitLoading(false)
          }
        });
    }
  }

  scaricaDocumento(idAndName)
  {
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/istruttoria/allegati/download.pjson')
      .toPromise()
      .then(data =>
      {
        var blob = new Blob([data.body], { type: data.body.type });
        this.allegatoService.downloadElemento(blob, idAndName.name);
        this.loadingService.emitLoading(false)
      })
      .catch(error =>
      {
        console.log('download error:', JSON.stringify(error));
        this.loadingService.emitLoading(false);
      });
  }

  get filesA(): Array<Allegato> 
  { 
    let allegati = [];
    if (this.allegati && this.allegati.documentazioneAmministrativa)
      allegati = this.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati
    return allegati;
  }

  get filesB(): Array<Allegato> 
  {
    let allegati = [];
    if (this.allegati && this.allegati.documentazioneAmministrativa)
      allegati = this.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati;
    return allegati;
  }


}
