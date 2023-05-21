import { validDate } from 'src/app/components/validators/customValidator';
import { OrganizzaizoneSearchBean, TipoOrganizzazione } from './conf-organizzazione';
import { Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { TableHeader } from 'src/shared/models/table-header';
import { CONST } from './../../../../../../../shared/constants';
import { DateOptions, InputType, SearchFields } from './../../../../../../components/model/form-search/formSearch.models';
import { SimpleFormControlData } from './../../../../../../components/model/model';

export type CircleData = {color: string, status: string};

export class ConstConfOrganizzazioni
{
    public static readonly selectTipiOrganizzazioni: Array<SelectItem> = [
        //{label:"Amministratori"                   , value: "ADMIN"},
        //{label:"Regione"                          , value: "REG"},
        {label:"Ente territorialmente interessato", value: "ETI"},
        {label:"Soprintendenza"                   , value: "SBAP"},
        //{label:"Ente Parco"                       , value: "EPA"},
        {label:"Ente delegato"                    , value: "ED"},
        //{label:"Commissione Locale"               , value: "CL"},
    ];

    public static readonly selectTipiOrganizzazioniEtiEd: Array<SelectItem> = [
        {label:"Ente territorialmente interessato", value: "ETI"},
        {label:"Ente delegato"                    , value: "ED"}
    ];

    public static readonly selectTipiOrganizzazioniEti: Array<SelectItem> = [
        {label:"Ente territorialmente interessato", value: "ETI"},
    ];

    public static readonly selectTipiOrganizzazioniSbap: Array<SelectItem> = [
        {label:"Soprintendenza"                   , value: "SBAP"},
    ];

    public static readonly selectTipiOrganizzazioniSpec: Array<SelectItem> = [
        {label:"Provincia"          , value: "Provincia"},
        {label:"Comune"             , value: "Comune"},
        {label:"Unione"             , value: "Unione"},
        {label:"Soprintendenza"     , value: "Soprintendenza"},
        {label:"Regione"            , value: "Regione"}
    ];
    
    public static readonly selectTipiOrganizzazioniSpecParco: Array<SelectItem> = [
        {label:"Unione"             , value: "Unione"},
    ];

    public static readonly selectTipiOrganizzazioniSpecEtiEd: Array<SelectItem> = [
        //{label:"Regione"            , value: "Regione"},
        {label:"Provincia"          , value: "Provincia"},
        {label:"Comune"             , value: "Comune"},
        {label:"Unione"             , value: "Unione"},
        //{label:"Soprintendenza"     , value: "Soprintendenza"},
        //{label:"Commissione Locale" , value: "Commissione Locale"},
        //{label:"Associazione"       , value: "Associazione"},
    ];

    public static readonly selectTipiOrganizzazioniSpecEtiEdREG: Array<SelectItem> = [
        ...ConstConfOrganizzazioni.selectTipiOrganizzazioniSpecEtiEd,
        {label:"Regione"            , value: "Regione"}
    ];

    public static readonly selectTipiOrganizzazioniSpecSbap: Array<SelectItem> = [
        {label:"Soprintendenza"     , value: "Soprintendenza"},
    ];

    public static readonly headerEnteTable: Array<TableHeader> = [
        {field: 'denominazione', header: 'Denominazione ente'},
        {field: 'dataTermine'  , header: 'Data scadenza autorizzazione'}
    ];

    public static readonly headerDestinataridefault: Array<TableHeader> = [
        {field: 'email' , header: 'Email'},
        {field: 'pec'   , header: 'pec'}
    ]; 

    public static readonly columnsOrg: Array<TableHeader> = [
        {field: 'denominazione', header: 'Denominazione', sortableField: true},
        {field: 'tipoOrganizzazione', header: 'Tipo organizzazione', sortableField: true},
        {field: 'tipoOrganizzazioneSpecifica', header: 'Tipo organizzazione specifica', sortableField: true},
        {field: 'dataCreazione', header: 'Data creazione', sortableField: true},
        {field: 'dataTermine', header: 'Data scadenza', sortableField: true}
    ];

    public static readonly statusTab: {[key: string]: CircleData} = {
        "true": {color: '#00cc00', status: 'Attivo'},
        "false": {color: '#cc0000', status: 'Disattivo'}
    };

    public static readonly attivaConf: Array<SimpleFormControlData> = [
        {
            formControlName: 'paesaggioOrganizzazioneId',
            type: 'hidden',
            label: null,
            required: false,
            validators: [],
            options: []
        },
        {
            formControlName: 'dataTermine',
            type: 'date',
            label: 'Data fine validità',
            required: true,
            validators: [Validators.required, validDate(new Date())],
            options: []
        },
    ];

    public static readonly formConf: Array<SimpleFormControlData> = [
        {
            formControlName: 'idEnte',
            type: 'dropdown',
            label: 'Ente',
            required: true,
            validators: [Validators.required],
            options: []
        },
        {
            formControlName: 'dataTermine',
            type: 'date',
            label: 'Data fine validità',
            required: true,
            validators: [Validators.required/* , validDate(new Date()) */],
            options: [],
            yearRange: '2000:9999'
        },
        {
            formControlName: 'idOrganizzazioneCompetenze',
            type: 'hidden',
            label: null,
            required: false,
            validators: [],
            options: []
        },
    ];


    public static readonly confBean: OrganizzaizoneSearchBean = {
        denominazione: null,
        enteId: null,
        tipoOrganizzazione: null,
        tipoOrganizzazioneSpecifica: null,
        dataCreazioneDa: null,
        dataCreazioneA: null,
        dataTermineDa: null,
        dataTermineA: null,
        enabled: null,
        page: 1,
        limit: 5,
        sortBy: 'denominazione',
        sortType: 'ASC'
    }


    public static readonly searchConfiguration: Array<SearchFields> = [
        {
            label: "Denominazione",
            formControlName: "denominazione",
            className: "form-control",
            type: InputType.text,
            extra: {
                maxlength: 200
            }
        },
        /* {
            label: "Attiva paesaggio",
            formControlName: "enabled",
            className: "form-control",
            type: InputType.radio,
            extra: {
                valueLabel: 'value',
                optionLabel: 'label',
                options: [{label: 'Nessuno', value: null}, {label: 'Abilitato', value: true}, {label: 'Non abilitato', value: false}]
            }
        }, */
        {
            label: "Tipo organizzazione",
            formControlName: "tipoOrganizzazione",
            className: "dflex",
            type: InputType.select,
            extra: {
                options: ConstConfOrganizzazioni.selectTipiOrganizzazioni,
                optionLabel: "label",
                defaultLabel: '-- Seleziona una tipologia --'
            }
        },
        {
            label: "Tipo organizzazione specifica",
            formControlName: "tipoOrganizzazioneSpecifica",
            className: "dflex",
            type: InputType.select,
            extra: {
                options: ConstConfOrganizzazioni.selectTipiOrganizzazioniSpec,
                optionLabel: "label",
                defaultLabel: '-- Seleziona una tipologia specifica --'
            }
        },
        {
            label: "Data Creazione",
            formControlName: "dataCreazione",
            className: "form-control",
            type: InputType.dateRange,
            extra:<DateOptions> {
                format: "dd/mm/yy",
                locale: CONST.IT,
                maxDate: null,
                minDate: null,
                monthNavigator: true,
                yearNavigator: true,
                yearRange: "1950:" + (CONST.NOW_YEAR + 50)
            }
        },
        {
            label: "Data fine validità",
            formControlName: "dataTermine",
            className: "form-control",
            type: InputType.dateRange,
            extra:<DateOptions> {
                format: "dd/mm/yy",
                locale: CONST.IT,
                maxDate: null,
                minDate: null,
                monthNavigator: true,
                yearNavigator: true,
                yearRange: "1950:" + (CONST.NOW_YEAR + 50)
            }
        },
   ];

   public static readonly TipoOrganizzazioneTablePrint: {[key: string]: string} = 
   {
       "SBAP": "Soprintendenza",
       "ED"  : "Ente delegato",
       "ETI" : "Ente territorialmente interessato" 
   }
}