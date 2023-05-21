import { InputType, SearchFields } from '../../../shared/models/form-search.configurations.models';
import { TableConfig } from './../../../core/models/header.model';

export class AssegnaFascicoloConfiguration
{
    private static nCol = 1;
    private static searchConf: SearchFields[] =
    [
        {
            label: "fascicolo.codiceFascicolo",
            labelClass: "",
            className: "uppercase",
            formControlName: "codice",
            type: InputType.autocomplete,
            extra: 
            {
                forceSelection: false,
                uppercase: true
            }
        }
    ];

    private static notAssignedTH: TableConfig[] =
    [
        {
            field: "stato",
            header: "",
            width: 3
        },
        {
            field: "codice",
            header: "fascicolo.codiceFascicolo",
            width: 8
        },
        {
            field: "comuni",
            header: "fascicolo.comune",
            width: 15
        },
        {
            field: "oggettoIntervento",
            header: "fascicolo.oggetto",
        },
        {
            field: "tipoProcedimento",
            header: "fascicolo.tipoProcedimento",
            width: 30
        },
        {
            field: "stato",
            header: "fascicolo.stato",
            width: 12
        },
        {
            field: "displayButton",
            header: "",
            width: 4
        }
    ];

    private static assignedTH: TableConfig[] =
    [
        {
            field: "stato",
            header: "",
            width: 3,
            orderable: false
        },
        {
            field: "codice",
            header: "fascicolo.codiceFascicolo",
            orderable: true
        },
        {
            field: "comuni",
            header: "fascicolo.comune",
            orderable: false
        },
        /* {
            field: "oggetto",
            header: "fascicolo.oggetto",
        }, */
        {
            field: "ultimaOperazione",
            header: "fascicolo.dataUltimaAssegnazione",
            orderable: true
        },
        {
            field: "stato",
            header: "fascicolo.stato",
            orderable: false
        },
        {
            field: "denominazioneFunzionario",
            header: "fascicolo.funzionario",
            orderable: true
        },
        {
            field: "denominazioneRup",
            header: "fascicolo.rup",
            orderable: true
        },
        {
            field: "riassegnazioni",
            header: "fascicolo.numeroAssegnazione",
            width: 3,
            orderable: false
        },
        {
            field: "displayButton",
            header: "",
            width: 10,
            orderable: false
        }
    ];

    private static assegnazioneHeader: TableConfig[] =
    [
        {
            field: "denominazioneFunzionario",
            header: "fascicolo.assegnazione.funzionari",
        },
        {
            field: "denominazioneRup",
            header: "fascicolo.rup",
        },
        /* {
            field: "dataAssegnazione",
            header: "fascicolo.assegnazione.dataAssegnazioneFunzionario",
        },
        {
            field: "dataAssegnazione",
            header: "fascicolo.assegnazione.dataAssegnazioneRup",
        }, */
        {
            field: "tipoOperazione",
            header: "fascicolo.assegnazione.tipoOperazione"
        },
        {
            field: "dataOperazione",
            header: "fascicolo.assegnazione.dataAssegnazione",
        },
        /* {
            field: "dataFineAssegnazione",
            header: "fascicolo.assegnazione.dataFineAssegnazione",
        } */
    ];

    /**
     * @description restituisce il json di configurazione per il component di ricerca in cui vengono definiti
     * gli input presenti e le proprietà che ciascuno di essi deve avere
     */
    static get configuration(): SearchFields[] { return this.searchConf; }
    /**
     * @description resituisce il numero di colonne desiderate per il component di ricerca
     */
    static get nColumns(): number { return this.nCol; }
    /**
     * @description definisce i campi che saranno mostrati nella tabella dei fascicoli Non Assegnati
     */
    static get tableHeadersNA(): TableConfig[] { return this.notAssignedTH; }
    /**
     * @description definisce i campi che saranno mostrati nella tabella dei fascicoli già Assegnati
     */
    static get tableHeadersA(): TableConfig[] { return this.assignedTH; }
    /**
     * @description definisce i campi che saranno mostrati nella tabella dello Storico delle Assegnazioni
     */
    static get tableHeadersSA(): TableConfig[] { return this.assegnazioneHeader; }
}