import { CustomDialogService } from './../../../core/services/dialog.service';
import { Allegati } from './../../models/models';
import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Fascicolo, TipoContenuto, Allegato, RequestAllegato } from 'src/app/shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { TableConfig } from 'src/app/core/models/header.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectItem } from 'primeng/primeng';


@Component({
  selector: 'app-documentazione-tecnica',
  templateUrl: './documentazione-tecnica.component.html',
  styleUrls: ['./documentazione-tecnica.component.scss']
})
export class DocumentazioneTecnicaComponent implements OnInit
{

  documentTableHeaders: TableConfig[] = [];
  @Input() fascicolo: Fascicolo;
  @Input() allegati: Allegati;
  @Input() mainForm: FormGroup;
  @Input() tableData: any[];
  @Input() tipiContenuto: TipoContenuto[];
  @Input("idIntegrazione") idIntegrazione: number;
  @ViewChild('fileUpload', { static: false }) fileUpload: any;

  @Output("actionComplete") actionCompleteEmitter: EventEmitter<any> = new EventEmitter<any>();

  documentazioneTecnicaForm: FormGroup;
  formInserimento: FormGroup;
  disabled: boolean = true;
  selectOptionDocTecnica: SelectItem[];

  constructor(private fb: FormBuilder,
              private allegatoService: AllegatoService,
              private loadingService: LoadingService,
              private dialogService: CustomDialogService) { }

  ngOnInit()
  {
    this.selectOptionDocTecnica = this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_TECNICA")
      .map(tipoContenuto =>
      {
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
        field: 'checksum'
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

  buildForm()
  {
    this.formInserimento = this.fb.group({
      descrizione: [null, Validators.required],
      nomeContenuto: [null, Validators.required],
    });
    this.documentazioneTecnicaForm = this.fb.group({
      grigliaAllegatiCaricati: this.fb.array([])
    });
    this.mainForm.addControl(
      'documentazioneTecnica',
      this.documentazioneTecnicaForm
    );
  }

  addGrigliaAllegatiCaricati()
  {
    let allegatiPreesistenti = this.allegati.documentazioneTecnica.grigliaAllegatiCaricati;
    if (allegatiPreesistenti)
    {
      allegatiPreesistenti.forEach(allegato =>
      {
        this.appendiAllegatoInGriglia('grigliaAllegatiCaricati', allegato);
      });
    }
  }

  appendiAllegatoInGriglia(nomeFormArray, allegato: Allegato)
  {
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

  get getGrigliaAllegatiCaricati()
  {
    return (this.documentazioneTecnicaForm.get('grigliaAllegatiCaricati') as FormArray);
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
          this.actionCompleteEmitter.emit({ action: "DELETE", allegatoId: id, type: "DOC_TECNICA" });
        }
        this.loadingService.emitLoading(false);
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
      let indice = this.findIndexFromAllegatoId(allegatoId, this.getGrigliaAllegatiCaricati);
      //se ho altre righe con stessa descrizione, allora la elimino, altrimenti se è l'ultima la lascio nel form
      let tipoContenuto = (this.getGrigliaAllegatiCaricati.at(indice) as FormGroup).get('descrizione').value;
      this.getGrigliaAllegatiCaricati.removeAt(indice);
    };
    this.eliminaDocumento(index, callback);
  }

  doUpload($event)
  {
    const tipiContenuto = this.formInserimento.value.descrizione;
    const nomeContenuto = this.formInserimento.value.nomeContenuto;
    if (!this.formInserimento.valid ||
      !nomeContenuto || nomeContenuto == "" || !tipiContenuto ||
      !Array.isArray(tipiContenuto) || tipiContenuto.length == 0 ||
      !$event.files || $event.files.length <= 0)
    {
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
    this.caricaDocumentoTecnico({ file: file, fileType: tipiContenuto, nomeContenuto: nomeContenuto },
      (uploadedAttachment: Allegato) =>
      {
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


  caricaDocumentoTecnico($event, callback)
  {
    if ($event.file)
    {
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
        .then((response) =>
        {
          let ret = response.payload;
          if (ret != null)
          {
            callback(response.payload);
            this.actionCompleteEmitter.emit({ action: "UPLOAD", file: response.payload, type: "DOC_TECNICA" });
          }
          this.loadingService.emitLoading(false)
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
        this.loadingService.emitLoading(false);
      })
      .catch(error =>
      {
        console.log('download error:', JSON.stringify(error));
        this.loadingService.emitLoading(false);
      });
  }

  legendaTipiContenuto(): TipoContenuto[]
  {
    return this.tipiContenuto
      .filter(tipoContenuto => tipoContenuto.sezione == "DOC_TECNICA");
  }

  getFileTypeSelect()
  {
    return this.selectOptionDocTecnica;
  }

}
