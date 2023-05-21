import { IstanzaConst } from './../../constants/istanza-const';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { RequestAllegato, Fascicolo, Allegato } from 'src/app/shared/models/models';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-documento-riconoscimento-allegato',
  templateUrl: './documento-riconoscimento-allegato.component.html',
  styleUrls: ['./documento-riconoscimento-allegato.component.css']
})
export class DocumentoRiconoscimentoAllegatoComponent implements OnInit {

    @Input() fascicolo:Fascicolo;
    @Input() praticaId:string;
    @Input() referenteId:number;
    @Input() allegatoDoc:Allegato;
    @Input() disabled: boolean = false;
    @Output() emitTableData: EventEmitter<any> = new EventEmitter<any>();
    tableData: any[];
    documentTableHeaders: TableConfig[] = [];

    constructor(public allegatiService: AllegatoService,
                public loadingService: LoadingService,
                private translateService:TranslateService)
    {
        this.tableData = [
            {
                id: null,
                nome: null,
                descrizione: '',
                data: null,
            }
        ];
        this.documentTableHeaders = IstanzaConst.documentoReferenteCOnfigTable;
    }

    ngOnInit() {
        this.initDocumentoData()
    }
    
    initDocumentoData() {
        if(this.allegatoDoc!=null){
            this.tableData[0] = this.allegatoDoc;
        }
    }

    caricaDocumento($event) {
        this.loadingService.emitLoading(true);
        if ($event.file) {
            const file: File = $event.file;
            const formData: FormData = new FormData();
            formData.append('file', file);
            let allegatoDocDto = new RequestAllegato();
            allegatoDocDto.praticaId = this.praticaId;
            allegatoDocDto.referenteId = this.referenteId;
            formData.append('req', new Blob([JSON.stringify(allegatoDocDto)], { type: "application/json" }));
            this.allegatiService.uploadAllegatoDocumento(formData, '/allegati/upload_documento_referente.pjson')
                //.pipe(takeUntil(this.unsubscribe$))
                .then((response) => {
                    let ret = response.payload;
                    if (ret != null) {
                        this.tableData[0] = response.payload;
                        this.emitTableData.emit(this.tableData);
                    }
                })
                .finally(() => this.loadingService.emitLoading(false));
        }
    }

    scaricaDocumento(idAndName) {
        this.loadingService.emitLoading(true);
        this.allegatiService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/allegati/download.pjson')
            .toPromise()
            .then(data => {
                var blob = new Blob([data.body], { type: data.body.type });
                this.allegatiService.downloadElemento(blob, idAndName.name);
            })
            .catch(error => {
                console.log('download error:', JSON.stringify(error));
                this.loadingService.emitLoading(false);
            })
            .finally(() => {
                this.loadingService.emitLoading(false);
            });
    }
   

    eliminaDocumento(id) {
        this.loadingService.emitLoading(true);
        let req = new RequestAllegato();
        req.praticaId = this.praticaId;
        req.referenteId = this.referenteId;
        this.allegatiService.eliminaDocumentoReferente(req, '/allegati/delete_documento_referente.pjson')
            .then((response) => {
                let ret = response.payload;
                if (ret != null) {
                    this.tableData[0] = {
                        id: null,
                        nome: null,
                        descrizione: '',
                        data: null,
                    };
                    this.emitTableData.emit(this.tableData);
                }
            }).finally(() => this.loadingService.emitLoading(false));
    }

    public checkFileType(file:File):string{
        let mimeOk=CONST.mimeTypeForScansioni;
        if(!mimeOk.includes(file.type)){
            let message=this.translateService.instant('generic.tipoAllegatoInvalido',{mimeTypeOk:mimeOk})
           return message;
        }
        return null;
     }

}
