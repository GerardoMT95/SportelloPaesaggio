import { TableConfig } from './../../../core/models/header.model';
import { CONST } from './../../../shared/constants';
import { DocumentType } from './../../../shared/models/allegati.model';
import { DateOptions, InputType, SearchFields } from './../../../shared/models/form-search.configurations.models';

export class SeduteDiCommissioneConfig
{
    private static _searchConf: SearchFields[] =
    [
        {
            label: "Data",
            formControlName: "dataSeduta",
            type: InputType.dateRange,
            className: 'form-control',
            extra: <DateOptions>{
                format: "dd/mm/yy",
                locale: CONST.IT,
                maxDate: CONST.TODAY,
                yearNavigator: true,
                yearRange: "1900:" + CONST.TODAY.getFullYear(),
                monthNavigator: false
            }
        },
        {
            label: "Codice fascicolo",
            className: "form-control",
            formControlName: "codicePratica",
            type: InputType.text,
            extra: {}
        }
    ];

    private static _tableHeaders: TableConfig[] =
    [
        {
            header: "",
            width: 3,
            field: "stato",
            orderable: false
        },
        {
            header: "COMMISSIONE_LOCALE.THEAD.NAME",
            field: "nomeSeduta"
        },
        {
            header: "COMMISSIONE_LOCALE.THEAD.DATE",
            field: "dataSeduta"
        },
        {
            header: "COMMISSIONE_LOCALE.THEAD.HOUR",
            field: "hour",
            orderable: false
        },
        {
            header: "COMMISSIONE_LOCALE.THEAD.TO_BE_EXAMINED",
            field: "fascioliDaEsaminare",
            orderable: false
        },
        {
            header: "COMMISSIONE_LOCALE.THEAD.EXAMINED",
            field: "fascicoliEsaminati",
            orderable: false
        },
        {
            header: "",
            field: "displayButton",
            width: 18,
            orderable: false
        }
    ];

    private static _fileTableHeaders: TableConfig[] =
    [
        {
            header: "fascicolo.tipoFile",
            field: "type",
            type: "type"
        },
        {
            header: "fascicolo.tableAllegati.nomeFile",
            field: "nome",
            type: "text"
        },
        {
            header: "fascicolo.tableAllegati.dataCaricamento",
            field: "data",
            type: "date"
        },
        {
            header: "fascicolo.verb_com_loc.dettaglio",
            field: "fascicoliCount",
            type: "counter",
            eventId: "DETAILS"
        },
    ]

    private static _tableHeaderFascioli: TableConfig[] =
    [
        {
            header: "fascicolo.codiceFascicolo",
            field: "codiceFascicolo"
        },
        {
            header: "fascicolo.oggetto",
            field: "oggetto"
        },
    ]

    static get searchConfiguration(): SearchFields[] { return this._searchConf; }
    static get tableHeaders(): TableConfig[] { return this._tableHeaders; }
    static get tableHeadersDetails(): TableConfig[] { return this._tableHeaderFascioli; }
    static get documentoClTypes(): DocumentType[] { return CONST.DOCUMENT_CL_TYPE; }
    static get fileTableHeaders(): TableConfig[] { return this._fileTableHeaders; }
}