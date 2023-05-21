import { Lang } from './components/model/model';
import { environment } from "../../environments/environment";
import { HttpHeaders } from "@angular/common/http";
import { TipoSelect } from "src/app/shared/components/model/model";
import { TableConfig } from '../core/models/header.model';
 
export class CONST{
    public static regexCodFisc=/(?:[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))/;

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
    public static readonly MAX_LENGTH_INPUT:number = 100;
    public static readonly MIN_YEAR:number = 1900;
    public static readonly MAX_YEAR:number = new Date().getFullYear() + 30;
    public static readonly NOW_YEAR:number = new Date().getFullYear();
    public static readonly TODAY= new Date();
    public static readonly MAX_YEAR_MAGGIORENNI: number = new Date().getFullYear() - 18;
    public static readonly MAX_DATE_MAGGIORENNI=new Date(new Date(CONST.MAX_YEAR_MAGGIORENNI,CONST.TODAY.getMonth(),CONST.TODAY.getDate()));
    public static readonly MAX_SIZE_UPLOAD=environment.maxSizeUpload;
    public static readonly DEFAULT_PAGE_NUMBER:number=10;

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
    public static readonly CATASTO_LAYER:string = environment.webgis.urlServices;
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

    public static readonly GENERIC_TABLE: TableConfig[] =
    [
        {
            field: "labelType",
            header: "Tipologia",
            type: "type"
        },
        {
            field: "name",
            header: "Nome file",
            type: "text"
        },
        {
            field: "checksum",
            header: "Impronta hash",
            type: "text"
        },
        {
            field: "uploadDate",
            header: "Data caricamento",
            type: "date"
        }
    ];

    public static readonly REDIRECT_PAGE:string = "/login";
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
    public static readonly WEB_RESOURCE_BASE_URL:string = environment.baseUrl+environment.projectName;

    public static readonly ANAGRAFICA_API :string = "anagrafica/api/";
    public static readonly BANDI_API      :string = "bandi/api/";

    public static readonly PATTERN_DECIMALI_VIRGOLA: string = "^[1-9]+([0-9]*)+(\,[0-9]+)?";
    public static readonly PATTERN_CURRENCY_EURO: string = "^\\d+\,\\d{2}$";
    public static readonly PATTERN_CF: string = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
    public static readonly PATTERN_NUMERI: string = "^[0-9+]*$";
    public static readonly PHONE_PATTERN: string = '^[0-9 \\+\\-\\.]*$';
    public static readonly PATTERN_CAP: string = "\\d{5}";
    public static readonly PATTERN_PROVINCIA: string = "[A-Z]{2}";
    public static readonly PATTERN_MAIL: string = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; 
    public static readonly PATTERN_PEC: string = '^\\s*?(.+)@(.+?)\\s*$';
    public static readonly MIN_NUMBER : number = 0.01;
    public static readonly MAX_NUMBER : number = 999999999.99;
    public static readonly MAX_100    : number = 100.00;
    public static readonly MAX_50     : number = 50.00;
    public static MAX_PARTICELLE: number=400000;
    public static readonly mimePDF=['application/pdf'];
    public static readonly mimeTypeForScansioni=['image/png',...CONST.mimePDF,'image/jpeg'];
    public static readonly mimeTypeP7M=['application/pkcs7-mime','application/x-pkcs7-mime','application/pkcs7'];
    public static readonly mimeTypeZip=['application/zip','application/x-zip-compressed','application/x-7z-compressed',
    'application/vnd.rar','application/x-tar'];

    public static readonly MAX_1MB: number = 1*1024*1024; 
    public static readonly MAX_5MB: number = 5*1024*1024; 
    public static readonly MAX_10MB: number = 10*1024*1024; 
    public static readonly MAX_50MB: number = 50*1024*1024; 
    public static readonly MAX_100MB: number = 100*1024*1024; 
    public static readonly MAX_LEN_CAP:     number = 5;
    public static readonly MAX_LEN_NCIVICO:     number = 30;
    static KEY_WEB_CONTENT_NUOVO_FASCICOLO: string="NUOVO_FASCICOLO";
    public static RANGE_CURRENT_YEAR_PLUS_10: string=CONST.NOW_YEAR+":"+(CONST.NOW_YEAR+10);
    public static RANGE_10_TO_CURRENT: string=(CONST.NOW_YEAR-10)+":"+CONST.NOW_YEAR;
}