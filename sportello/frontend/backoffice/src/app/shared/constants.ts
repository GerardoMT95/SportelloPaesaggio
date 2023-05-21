import { Lang } from './components/model/model';
import { environment } from "../../environments/environment";
import { HttpHeaders } from "@angular/common/http";
import { TipoSelect } from "src/app/shared/components/model/model";
import { TableConfig } from '../core/models/header.model';
import { DocumentType } from './models/allegati.model';
import { StatoFascicolo } from './models/registration-status';
import { StatoEnum } from './models/models';
import { SelectItem } from 'primeng/primeng';

 
export class CONST
{
    //definisco una stringa codice applicazione
    public static readonly CODICE_APPLICAZIONE = "PAE_PRES_IST";

    public static MAX_PARTICELLE: number = 400000;

    /*public static readonly typeProcedimento= [
        {
            //"value": "AUT_PAES_ORD",
            "value": 1,
            "description": "AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)"
        },
        {
            //"value": "AUT_PAES_SEMPL_DPR_31_2017",
            "value": 2,
            "description": "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017– art. 90, NTA PPTR PER INTERVENTI E OPERE DI LIEVE ENTITA’ SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL’ARTICOLO 146 C.9 DEL D.LGS 42/2004"
        },
        {
            //"value": "ACCERT_COMPAT_PAES_DLGS_42_2004",
            "value": 3,
            "description": "ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)"
        },
        {
            //"value": "ACCERT_COMPAT_PAES_DPR_31_2017",
            "value": 4,
            "description": "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)"
        },
        {
            //"value": "ACCERT_COMPAT_PAES_DPR_139_2010",
            "value": 5,
            "description": "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)"
        },
        {
            //"value": "AUT_PAES_SEMPL_DPR_139_2010",
            "value": 6,
            "description": "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)"

        }
    ];*/

    public static readonly listaTipologieMockup: SelectItem[] =
    [
        { label: "COMUNE", value: "CO" },
        { label: "PROVINCIA", value: "P" },
        { label: "REGIONE", value: "R" },
        { label: "SITO SIC", value: "SS" },
        { label: "SITO ZPS", value: "SZ" },
        { label: "UFFICIO REGIONALE", value: "UR" },
        { label: "SOPRINTENDENZA", value: "SI" },
        { label: "ASSOCIAZIONE COMUNI", value: "AC" },
        { label: "UNIONE COMUNI", value: "UC" },
        { label: "ALTRO NON SPECIFICATO", value: "ZZ" }
    ];

    public static readonly GENERIC_TABLE: TableConfig[] =
    [
        {
            field: "labelType",
            header: "Tipologia",
            type: "type"
        },
        {
            field: "nome",
            header: "Nome file",
            type: "text"
        },
        {
            field: "checksum",
            header: "Impronta hash",
            type: "text"
        },
        {
            field: "data",
            header: "Data caricamento",
            type: "date"
        }
    ];

    public static readonly SERIAL_KEYS: any = 
    {
        FASCICOLI: "fascicoli",
        ALLEGATI: "allegati",
        COMUNICAZIONI: "colunicazioni",
        ULTERIORE_DOCUMENTAZIONE: "ulteriore_documentazione",
        RELAZIONI: "relazioni",
        VERBALI: "verbali",
        PARERI: "pareri",
        RICHIESTA: "richiesta",
        DESTINATARIO: "destinatario",
        SEDUTE_CL: "seduteCL"
    };

    public static readonly DOCUMENT_CL_TYPE: DocumentType[] =
    [
        {
            label: "Verbale commissione locale",
            multiple: false,
            type: "VERBALE",          
            required: true 
        },
        {
            label: "Scheda tecnica",
            multiple: false,
            type: "SCHEDA_TECNICA",     
            required: true         
        },
        {
            label: "Altro",
            multiple: true,
            type: "ALTRO",     
            required: false         
        }
    ]

    public static readonly mappingTipiProcedimento =
    [
        {
            "value": 1,//"AUT_PAES_ORD",
            "description": "AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)"
        },
        {
            "value": 2,//"AUT_PAES_SEMPL_DPR_31_2017",
            "description": "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017– art. 90, NTA PPTR PER INTERVENTI E OPERE DI LIEVE ENTITA’ SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL’ARTICOLO 146 C.9 DEL D.LGS 42/2004"
        },
        {
            "value": 3,//"ACCERT_COMPAT_PAES_DLGS_42_2004",
            "description": "ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)"
        },
        {
            //"value": "ACCERT_COMPAT_PAES_DPR_31_2017",
            "value": 4,
            "description": "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)"
        },
        {
            //"value": "ACCERT_COMPAT_PAES_DPR_139_2010",
            "value": 5,
            "description": "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)"
        },
        {
            //"value": "AUT_PAES_SEMPL_DPR_139_2010",
            "value": 6,
            "description": "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)"

        }
    ]

    //mock utility-
    public static readonly activitiesToBePerformedList =
    [
        { value: StatoEnum.PresaInCarica, description: 'Presa in carico' },
        { value: StatoEnum.InLavorazione, description: 'In lavorazione' },
        /* {
            value: StatoEnum.RelazioneDaTrasmettere,
            description: 'Relazione da trasmettere alla Soprintendenza'
        },
        {
            value: StatoEnum.InAttesaDiParereSoprintedenza,
            description: 'In attesa di ricezione parere della Soprintendenza'
        },
        {
            value: StatoEnum.ParereSoprintendenzaTrasmesso,
            description: 'Parere della soprintendenza trasmesso'
        }, */
        { value: StatoEnum.InTrasmissione, description: 'In trasmissione' },
        { value: StatoEnum.Trasmessa, description: 'Trasmessa' }
    ];
  
    public static getLabelFromValue(arr: any[], value: any,
        labelKey: string = 'label', labelValue: string = 'value')
    {
        if (!Array.isArray(arr)) { return value; }
        //console.log('cerco ' + value + ' labelKey:' + labelKey + ' labelValue:' + labelValue + ' in {}', arr);
        let el = arr.find(el => { return el[labelValue] == value; });
        //console.log('beccato {}', el);
        let ret: any;
        if (el && el[labelValue])
        {
            ret = el[labelKey];
        }
        return ret;
    }

    //REGEX PER VALIDAZIONE
    public static regexCodFisc = /(?:[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))/;

    public static readonly statoFascicoloStatusAttribute = 
    [
        { enumVal: StatoFascicolo.WORKING, id: 0, label: "inLavorazione", color: "grey", translated: "In Lavorazione" },
        { enumVal: StatoFascicolo.TRANSMITTED, id: 1, label: "trasmesso", color: "#2b86c2", translated: "Trasmesso" },
        { enumVal: StatoFascicolo.CANCELLED, id: 2, label: "annullato", color: "#f00", translated: "Annullato" },
        { enumVal: StatoFascicolo.FINISHED, id: 3, label: "concluso", color: "#5cb85c", translated: "Concluso" },
        { enumVal: StatoFascicolo.SELECTED, id: 4, label: "selezionato", color: "#f5d90a", translated: "In corso" },
        { enumVal: StatoFascicolo.ON_MODIFY, id: 5, label: "inModifica", color: "#d47c02", translated: "In Modifica" },
    ];

    public static readonly statiLabelValue =
    [
        { description: 'In protocollazione', value: StatoEnum.InAttesaDiProtocollazione },
        { description: 'In preistruttoria',  value: StatoEnum.PresaInCarica             },
        { description: 'In lavorazione',     value: StatoEnum.InLavorazione             },
        { description: 'In trasmissione',    value: StatoEnum.InTrasmissione            },
        { description: 'Trasmessa',          value: StatoEnum.Trasmessa                 },
        { description: 'Sospesa',            value: StatoEnum.Sospesa                   },
        { description: 'Archiviata',         value: StatoEnum.Archiviata                }
    ];

    public static readonly ROLES =
    {
        funzionario: "FUN",
        dirigente: "DIRG",
        admin: "ADMIN"
    }

    public static readonly DEVELOPER : boolean = environment.developer;

    public static readonly PAGINAZIONE : TipoSelect[] = [
     {value:5, label:"5"}
    ,{value:10, label:"10"}
    ,{value:25, label:"25"}
    ];
    
    public static readonly TIPO_ALLEGATO={
        "ESITO":"15"
    }

    public static readonly LIMIT_AUTOCOMPLETE:number = 10;
    public static readonly MAX_RESULT_AUTOCOMPLETE:number = 30;
    public static readonly MAX_LENGTH_TEXTAREA:number = 400;
    public static readonly MAX_LENGTH_TEXTAREA_1000: number = 1000;
    public static readonly MAX_LENGTH_INPUT:number = 100;
    public static readonly TODAY: Date = new Date();
    public static readonly MIN_YEAR:number = 1900;
    public static readonly MAX_YEAR:number = new Date().getFullYear() + 30;
    public static readonly NOW_YEAR:number = new Date().getFullYear();
    public static readonly MAX_YEAR_MAGGIORENNI: number = new Date().getFullYear() - 18;
    public static readonly MAX_DATE_MAGGIORENNI=new Date(new Date(CONST.MAX_YEAR_MAGGIORENNI,CONST.TODAY.getMonth(),CONST.TODAY.getDate()));
    public static readonly MAX_SIZE_UPLOAD=environment.maxSizeUpload;
    
    public static readonly DEFAULT_PAGE_NUMBER:number=5;

    public static readonly INACTIVITY_TIME:number=1620000;
    public static readonly REFRESH_TIME:number = 61000;
    public static readonly COUNTDOWN_TIME:number = 20;

    public static readonly OPEN_DIAOLOG_LOGOUT:number=0;
    public static readonly REFRESH_SESSION_TIMEOUT:number=1;

     /*
    public static readonly VIEW_LAYER:string = window.location.protocol +"//testwa.sit.puglia.it/arcgis2/rest/services/Editing/EditingRichConcessioniDemanioFerrovie/MapServer/0";
    public static readonly EDIT_LAYER:string = window.location.protocol +"//testwa.sit.puglia.it/arcgis2/rest/services/Editing/EditingRichConcessioniDemanioFerrovie/FeatureServer/0";
    public static readonly CATASTO_LAYER:string = window.location.protocol +"//testwa.sit.puglia.it/arcgis2/rest/services/Catasto/MapServer";
    public static readonly SFONDO_LAYER:string = window.location.protocol +"//webapps.sit.puglia.it/arcgis/rest/services/Background/TNOInquadramento/MapServer"//window.location.protocol +"//testwa.sit.puglia.it/arcgis2/rest/services/TNOInquadramento/MapServer";//"http://webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer";
    */
    //public static readonly VIEW_LAYER:string    = environment.webgis.urlServices + "/arcgis2/rest/services/Operationals/ConsultaPrivAreeAutocertRR2405/MapServer/0";
    public static readonly VIEW_LAYER:string    = environment.webgis.urlLocale  + "/esriEditing/PresentazioneIstanzaEditing/0"; //in presentazione istanza il layer è sempre quello, cambierà a fine istruttoria
    //public static readonly EDIT_LAYER:string    = environment.webgis.urlLocale + "/arcgis/rest/services/pev/pevLayer/FeatureServer/0";
    public static readonly EDIT_LAYER:string    = environment.webgis.urlLocale  + "/esriEditing/PresentazioneIstanzaEditing/0";
    public static readonly CATASTO_LAYER:string = environment.webgis.urlServices ;
    public static readonly SFONDO_LAYER:string  = environment.webgis.urlOrtofoto + "/arcgis/rest/services/Background/TNOInquadramento/MapServer";


    public static readonly KO:string = 'KO';
    public static readonly OK:string = 'OK';
 
    public static readonly httpOptions = {
        headers: new HttpHeaders({'Content-Type':  'application/json'})
    }
    public static readonly httpOptionsCharset = {
        headers: new HttpHeaders({'Content-Type':  'application/json; charset=utf-8'})
    }
    public static readonly httpOptionsMultipart = {
        headers: new HttpHeaders({'Content-Type':  'multipart/form-data'})
    }
    
    public static readonly mimePDF=['application/pdf'];
    public static readonly mimeTypeForScansioni=['image/png',...CONST.mimePDF,'image/jpeg'];

    public static readonly AVAILABLE_LANGUAGE:Lang[]=[{
        code:'it',
        name:'Italiano'
      },{
        code:'en',
        name:"English"
      }
    ]

    public static readonly IT = {
        firstDayOfWeek: 1,
        dayNames: [ "Domenica","Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato" ],
        dayNamesShort: [ "Dom","Lun","Mar","Mer","Gio","Ven","Sab"],
        dayNamesMin: [ "D","L","M","M","G","V","S" ],
        monthNames: [ "Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre" ],
        monthNamesShort: [ "Gen","Feb","Mar","Apr","Mag","Giu","Lug","Ago","Set","Ott","Nov","Dic" ],
        today: 'Oggi',
        clear: 'Annulla'
    }
    public static readonly EN = {
        firstDayOfWeek: 0,
        dayNames: [ "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday" ],
        dayNamesShort: [ "Sun","Mon","Tue","Wed","Thu","Fri","Sat"],
        dayNamesMin: [ "S","M","T","W","T","F","S" ],
        monthNames: [ "Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre" ],
        monthNamesShort: [ "Gen","Feb","Mar","Apr","Mag","Giu","Lug","Ago","Set","Ott","Nov","Dic" ],
        today: 'Today',
        clear: 'Clear'
    }
    //Sezione configurazione processo di autenticazione 
    public static readonly HOST_BROWSER:string = window.location.protocol +"//"
    + window.location.hostname
    + (!window.location.port || window.location.port == "" || window.location.port == "80" || window.location.port == "443" ? "" : (":" + window.location.port))
    + "/"
    ;

    public static readonly URL_F:string = CONST.HOST_BROWSER + environment.pathName;

    public static readonly URL_LOGGED:string = CONST.HOST_BROWSER
    + environment.pathName
    + "login"
    ;
    public static readonly URL_JOSSO:string = environment.josso.jossoUrl;
    public static readonly PAR_JOSSO:string = "&josso_partnerapp_ctx=/sitpuglia-concessioni-demanio-web&josso_partnerapp_host="
    + CONST.HOST_BROWSER
    + "demanio"
    ;
    public static readonly URL_JOSSO_LOGIN: string = CONST.URL_JOSSO + "signon/login.do?josso_back_to="  + CONST.URL_LOGGED + CONST.PAR_JOSSO;
    public static readonly URL_JOSSO_LOGOUT:string = CONST.URL_JOSSO + "signon/logout.do?josso_back_to=" + CONST.URL_F      + CONST.PAR_JOSSO;
    //Fine configurazione processo di autenticazione
    public static readonly WEB_RESOURCE_BASE_URL:string = environment.baseUrl +environment.projectName ;

    public static readonly ANAGRAFICA_API :string = "anagrafica/api/";
    public static readonly BANDI_API      :string = "bandi/api/";

    public static readonly PATTERN_DECIMALI_VIRGOLA : string = "^[1-9]+([0-9]*)+(\,[0-9]+)?";
    public static readonly PATTERN_CURRENCY_EURO: string = "^\\d+\,\\d{2}$";
    
    public static readonly PATTERN_CF : string = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
    public static readonly PATTERN_NUMERI : string = "^[0-9+]*$";

    public static readonly PHONE_PATTERN: string = '^[0-9 \\+\\-\\.]*$';
    public static readonly PATTERN_CAP : string = "\\d{5}";
    public static readonly PATTERN_MAIL : string = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static readonly MIN_NUMBER : number = 0.01;
    public static readonly MAX_NUMBER : number = 999999999.99;
    public static readonly MAX_100    : number = 100.00;
    public static readonly MAX_50     : number = 50.00;

    public static readonly MAX_1MB: number = 1*1024*1024; 
    public static readonly MAX_5MB: number = 5*1024*1024; 
    public static readonly MAX_10MB: number = 10*1024*1024; 
    public static readonly MAX_50MB: number = 50*1024*1024; 
    public static readonly MAX_100MB: number = 100*1024*1024; 

    public static readonly MAX_LEN_TITOLO_DOCUMENTO: number = 80;
    public static readonly MAX_LEN_DESCRIZIONE_DOCUMENTO: number = 80;  // usato anche per i template
    public static readonly MAX_LEN_DESCRIZIONE_DOCUMENTO_ULT_DOC: number = 200;  
    public static GRUPPO_UTENTE_HEADER: string='GRUPPO-UTENTE';

}