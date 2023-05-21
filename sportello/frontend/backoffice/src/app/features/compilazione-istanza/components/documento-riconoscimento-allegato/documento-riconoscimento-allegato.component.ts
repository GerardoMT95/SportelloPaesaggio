import { IstanzaConst } from './../../constants/istanza-const';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { RequestAllegato, Fascicolo } from 'src/app/shared/models/models';

@Component({
    selector: 'app-documento-riconoscimento-allegato',
    templateUrl: './documento-riconoscimento-allegato.component.html',
    styleUrls: ['./documento-riconoscimento-allegato.component.css']
})
export class DocumentoRiconoscimentoAllegatoComponent implements OnInit
{

    @Input() fascicolo: Fascicolo;
    @Input() praticaId: string;
    @Input() referenteId: number;
    @Output() emitTableData: EventEmitter<any> = new EventEmitter<any>();
    tableData: any[];
    documentTableHeaders: TableConfig[] = [];

    constructor(public allegatiService: AllegatoService,
        public loadingService: LoadingService)
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

    ngOnInit()
    {
        this.initDocumentoData()
    }

    initDocumentoData()
    {
        let req = new RequestAllegato();
        req.praticaId = this.praticaId;
        req.referenteId = this.referenteId;
        this.allegatiService.getAllegatoDocumentoMetadata(req, '/istruttoria/allegati/get_documento_referente.pjson')
            .then((response) =>
            {
                let ret = response.payload;
                if (ret != null)
                {
                    this.tableData[0] = ret;
                    this.emitTableData.emit(this.tableData);
                }
            });
    }

    /* caricaDocumento($event) {
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
    } */

    scaricaDocumento(idAndName)
    {
        this.loadingService.emitLoading(true);
        this.allegatiService.downloadAllegatoFascicolo(idAndName.id,this.fascicolo.id, '/istruttoria/allegati/download.pjson')
            .toPromise()
            .then(data =>
            {
                var blob = new Blob([data.body], { type: data.body.type });
                this.allegatiService.downloadElemento(blob, idAndName.name);
                this.loadingService.emitLoading(false);
            })
            .catch(error =>
            {
                console.log('download error:', JSON.stringify(error));
                this.loadingService.emitLoading(false);
            })
        /* .finally(() => {
            this.loadingService.emitLoading(false);
        }); */
    }

    /*downloadElemento(blob: Blob, fileName: string): void {
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(blob, fileName);
        } else {
            var link = document.createElement("a");
            if (link.download !== undefined) {
                var url = URL.createObjectURL(blob);
                link.setAttribute("href", url);
                link.setAttribute("download", fileName);
                link.style.visibility = 'hidden';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    }*/

    eliminaDocumento(id)
    {
        this.loadingService.emitLoading(true);
        let req = new RequestAllegato();
        req.praticaId = this.praticaId;
        req.referenteId = this.referenteId;
        this.allegatiService.eliminaDocumentoReferente(req, '/allegati/delete_documento_referente.pjson')
            .then((response) =>
            {
                let ret = response.payload;
                if (ret != null)
                {
                    this.tableData[0] = {
                        id: null,
                        nome: null,
                        descrizione: '',
                        data: null,
                    };
                    this.emitTableData.emit(this.tableData);
                    this.loadingService.emitLoading(false)
                }
            })/* .finally(() => this.loadingService.emitLoading(false)); */
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
                    this.loadingService.emitLoading(false);
                    let ret = response.payload;
                    if (ret != null) {
                        this.tableData[0] = response.payload;
                        this.emitTableData.emit(this.tableData);
                    }
                })
                .catch(() => this.loadingService.emitLoading(false));
        }
    }
}
