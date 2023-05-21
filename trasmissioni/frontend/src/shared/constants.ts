import { HttpHeaders } from "@angular/common/http";
import { SelectItem } from 'primeng/components/common/selectitem';
import { DettaglioTemplate, Ente } from 'src/app/components/model/entity/admin.models';
import { TipoAllegato } from 'src/app/components/model/entity/allegato.models';
import { DestinatarioComunicazioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { Projects } from 'src/app/components/model/enum';
import { TipoSelect } from "src/app/components/model/model";
import { PlainTypeNumericId } from 'src/app/components/model/plain-type-numeric-id.model';
import { DestinatariComunicazione } from 'src/app/components/model/recipient.model';
import { environment } from "../environments/environment";
import { TipoTemplate } from './../app/components/model/entity/admin.models';
import { Lang } from './../app/components/model/model';
import { RegistrationStatus, StatoFascicolo } from './models/registration-status';

export class CONST {

  public static readonly mockupTemplates: DettaglioTemplate[] =
  [
    {
      codice: TipoTemplate.INVIO_TRASMISSIONE,
      nome: "TRASMISSIONE PROVVEDIMENTO",
      descrizione: "Mail comunicazione di conferma ricezione del provvedimento finale",
      oggetto: "Provvedimento finale",
      testo: "<h3>PROVVEDIMENTO FINALE</h3><br/>{nome} alleghiamo di seguito il provvedimento finale del fascicolo con codice {codice}. Buona giornata.",
      destinatari: []
    }
  ]

  public static readonly mockupRubrica: Ente[] =
    [
      {
        id: 1,
        denominazione: 'Comune di Test',
        mail: "test@gmail.com",
        pec: "test@pec.test.it",
        tipologia: "CO"
      }
    ]

    /**
     * Tipo Ente CO (comune), P (provincia), E (ente), CN (consorzio), R (regione), SS(SITO SIC), 
     * SZ(SITO ZPS)  UR(Ufficio Regione)  
     * SI(Soprintendenza ) AC(Associazione comuni) UC(Unione comuni) ZZ(Altro non specificato)
     */
  public static readonly listaTipologieMockup: SelectItem[] =
  [
    { label: "COMUNE", value: "CO" },
    { label: "PROVINCIA", value: "P" },
    { label: "REGIONE", value: "R" },
    { label: "SITO SIC", value: "SS" },
    { label: "SITO ZPS", value: "SZ" },
    { label: "UFFICIO REGIONALE", value: "UR" },
    { label: "SOPRINTENDENZA", value: "SI" },
    { label: "ASSOCIAZIONE COMUNI", value: "AC"},
    { label: "UNIONE COMUNI", value: "UC"},
    { label: "ALTRO NON SPECIFICATO", value: "ZZ"}
  ];

  public static readonly tipoDocumento: SelectItem[] =[
    {label: "Carta di identità", value: 'CARTA_DI_IDENTITA'},
    {label: "Patente", value: 'PATENTE'},
    {label: "Passaporto", value: 'PASSAPORTO'}
  ]

  public static readonly entiTemplate: SelectItem[] =
    [
      { label: "Regione Puglia", value: "REG_" },
      { label: "Ente delegato", value: "ED_" },
      { label: "Soprintendenza", value: "SBAP_" },
      /* { label: "Ente parco", value: "EPA_" } */
      { label: "Enti territorialmente interessati", value: "ETI_" }
    ]

  public static readonly LIMIT_AUTOCOMPLETE: number = 10;
  public static readonly MAX_RESULT_AUTOCOMPLETE: number = 30;
  public static readonly MAX_LENGTH_TEXTAREA: number = 400;
  public static readonly MAX_LENGTH_TEXTAREA_1000: number = 1000;
  public static readonly MAX_LENGTH_INPUT: number = 100;
  public static readonly MIN_YEAR: number = 1900;
  public static readonly MAX_YEAR: number = new Date().getFullYear() + 30;
  public static readonly NOW_YEAR: number = new Date().getFullYear();
  public static readonly TODAY = new Date();
  public static readonly MAX_YEAR_MAGGIORENNI: number = new Date().getFullYear() - 18;
  public static readonly MAX_DATE_MAGGIORENNI= new Date(CONST.MAX_YEAR_MAGGIORENNI,CONST.TODAY.getMonth(),CONST.TODAY.getDate());
  public static readonly NOW_DATE = new Date();
  public static MAX_PARTICELLE: number = 400000;

  public static readonly DEVELOPER: boolean = environment.developer;
  public static PAE_PRES_IST: string="pae_pres_ist";
  

  public static labelMapping(){
    if(this.isPutt())
    return {
      1: "fascicolo.interventoSectionPutt.titoloTipo",
      "a": "fascicolo.interventoSectionPutt.opereDiProgetto",
      2: "fascicolo.interventoSectionPutt.tipologiaIntervento",
      "b": "",
      4: "fascicolo.interventoSectionPutt.tipologiaProvvedimento",
      "e": "",
      "j": "fascicolo.interventoSectionPutt.qualificazioneSemplificata",
    }
    else
    return   {
      1: "fascicolo.interventoSection.titoloTipo",
      "a": "fascicolo.interventoSection.opereDiProgetto",
      2: "fascicolo.interventoSection.caratterizzazioneIntervento",
      "b": "fascicolo.interventoSection.riguarda",
      "c": "fascicolo.interventoSection.carattere",
      4: "fascicolo.interventoSection.qualificazioneIntervento",
      "d": "fascicolo.interventoSection.fraseQualificazione2005",
      "e": "fascicolo.interventoSection.fraseQualificazione31_2017",
      "f": "fascicolo.interventoSection.fraseQualificazione42_2004",
      "g": "fascicolo.interventoSection.fraseQualificazione",
      "h": "fascicolo.interventoSection.fraseQualificazioneIntervento",
      "i": "fascicolo.interventoSection.fraseQualificazione139_2010"
    }
  } 
    

  public static readonly footerMapping =
    {
      "a": "fascicolo.interventoSection.legenda4",
      "d": "fascicolo.interventoSection.legenda3",
    }

  public static readonly PAGINAZIONE: TipoSelect[] = [
    { value: 5, label: "5" }
    , { value: 10, label: "10" }
    , { value: 25, label: "25" }
  ];

  public static readonly TIPO_ALLEGATO = {
    "ESITO": "15"
  }

  public static readonly ADMIN = "ADMIN";

  public static readonly MAX_SIZE_UPLOAD = environment.maxSizeUpload;
  public static readonly DEFAULT_PAGE_NUMBER: number = 5;

  public static readonly INACTIVITY_TIME: number = 1620000;
  public static readonly REFRESH_TIME: number = 61000;
  public static readonly COUNTDOWN_TIME: number = 20;

  public static readonly OPEN_DIAOLOG_LOGOUT: number = 0;
  public static readonly REFRESH_SESSION_TIMEOUT: number = 1;

  //public static readonly VIEW_LAYER:string    = environment.webgis.urlServices + "/arcgis2/rest/services/Operationals/ConsultaPrivAreeAutocertRR2405/MapServer/0";
  public static readonly VIEW_LAYER:string    = environment.webgis.urlLocale  + "/public/esriPublished/"+environment.project+"Published/0";
    //public static readonly EDIT_LAYER:string    = environment.webgis.urlLocale + "/arcgis/rest/services/pev/pevLayer/FeatureServer/0";
  public static readonly EDIT_LAYER:string    = environment.webgis.urlLocale  + "/esriEditing/"+environment.project+"Editing/0";
  public static readonly CATASTO_LAYER:string = environment.webgis.urlServices ;
  public static readonly SFONDO_LAYER:string  = environment.webgis.urlOrtofoto + "/arcgis/rest/services/Background/TNOInquadramento/MapServer";

  /**per compatibilita con orp nela sezione corrispondenza */
  public static readonly ROLE_ADMIN: string = 'Admin';
  public static readonly ROLE_REGIONE: string = 'ORP_REG';
  public static readonly UTENTEREGIONE: string = "Regione"


  public static readonly KO: string = 'KO';
  public static readonly OK: string = 'OK';

  public static readonly httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }
  public static readonly httpOptionsCharset = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' })
  }
  public static readonly httpOptionsMultipart = {
    headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
  }

  public static readonly httpOptionsMultipartValue = {
    headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data-value' })
  }

  public static readonly AVAILABLE_LANGUAGE: Lang[] = [{
    code: 'it',
    name: 'Italiano'
  }, {
    code: 'en',
    name: "English"
  }
  ]

  public static readonly IT = {
    firstDayOfWeek: 1,
    dayNames: ["Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"],
    dayNamesShort: ["Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"],
    dayNamesMin: ["D", "L", "M", "M", "G", "V", "S"],
    monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
    monthNamesShort: ["Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"],
    today: 'Oggi',
    clear: 'Annulla'
  }

  public static readonly EN = {
    firstDayOfWeek: 0,
    dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
    dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    dayNamesMin: ["S", "M", "T", "W", "T", "F", "S"],
    monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
    monthNamesShort: ["Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"],
    today: 'Today',
    clear: 'Clear'
  }
  //Sezione configurazione processo di autenticazione 
  public static readonly HOST_BROWSER: string = window.location.protocol + "//"
    + window.location.hostname
    + (!window.location.port || window.location.port == "" || window.location.port == "80" || window.location.port == "443" ? "" : (":" + window.location.port))
    + "/"
    ;

  public static readonly URL_F: string = CONST.HOST_BROWSER + environment.pathName;

  public static readonly URL_LOGGED: string = CONST.HOST_BROWSER
    + environment.pathName
    + "login"
    ;
  public static readonly URL_JOSSO: string = environment.josso.jossoUrl;
  public static readonly PAR_JOSSO: string = "&josso_partnerapp_ctx=/sitpuglia-concessioni-demanio-web&josso_partnerapp_host="
    + CONST.HOST_BROWSER
    + "demanio"
    ;
  public static readonly URL_JOSSO_LOGIN: string = CONST.URL_JOSSO + "signon/login.do?josso_back_to=" + CONST.URL_LOGGED + CONST.PAR_JOSSO;
  public static readonly URL_JOSSO_LOGOUT: string = CONST.URL_JOSSO + "signon/logout.do?josso_back_to=" + CONST.URL_F + CONST.PAR_JOSSO;
  //Fine configurazione processo di autenticazione
  public static readonly WEB_RESOURCE_BASE_URL: string =
    environment.baseUrl + environment.projectName;

  public static readonly ANAGRAFICA_API: string = "anagrafica/api/";
  public static readonly BANDI_API: string = "bandi/api/";

  public static readonly PATTERN_DECIMALI_VIRGOLA: string = "^[1-9]+([0-9]*)+(\,[0-9]+)?";
  public static readonly PATTERN_CF: string = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
  public static readonly PATTERN_NUMERI: string = "^[0-9+]*$";
  public static readonly PHONE_PATTERN: string = '^[0-9 \\+\\-\\.]*$';
  public static readonly PATTERN_CAP: string = "\\d{5}";
  public static readonly PATTERN_PROVINCIA: string = "[a-zA-Z]{2}";
  public static readonly PATTERN_MAIL: string = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

  public static readonly MIN_NUMBER: number = 0.01;
  public static readonly MAX_NUMBER: number = 999999999.99;
  public static readonly MAX_100: number = 100.00;
  public static readonly MAX_50: number = 50.00;

  /**
   * MOCK DATA
   */
  public static readonly WEB_RESOURCE_PROFILE_MANAGER: string = 'http://puglialab.eng.it/pm'

  public static TipoInterventoBase=[
    { value: 'MANUTENZIONE_RESTAURO', label: "Manutenzione, restauro e risanamento conservativo che alterano lo stato dei luoghi e l'aspetto esteriore dell'edificio (art. 3 DPR 380/01)" },
    { value: 'NUOVA_COSTRUZIONE', label: "Nuova costruzione (art. 3 DPR 380/01)" },
    { value: 'RISTR_EDILIZIA', label: "Ristrutturazione edilizia (art. 3 DPR 380/01)" },
    ];

  public static TipoIntervento = [
    { value: 'INTERVENTI_NON_EDILIZI', label: "Interventi e/o opere non di edilizia" },
    ...CONST.TipoInterventoBase,
    { value: 'RISTR_URBANISTICA', label: "Ristrutturazione urbanistica (art. 3 DPR 380/01)" }];

  public static TipoInterventoPutt = [
      { value: 'INTERVENTI_IMPEGNO_TERRITORIALE', label: "Interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)" },
      { value: 'INTERVENTI_NON_EDILIZI', label: "Interventi e/o opere non di edilizia" },
      ...CONST.TipoInterventoBase];

  public static TipoInterventoPareri=[
    {value:'PIANI_URBANISTICI',label:'piani urbanistici esecutivi (L.R. 20/2001)'},
    {value:'PIANI_PARTICOLAREGGIATI',label:'piani Particolareggiati di Esecuzione, (L. 1150/1942 ess. mm. e ii)'},
    {value:'PIANI_EDILIZIA',label:'piani per l’Edilizia Economica e Popolare, (L. 167/1962)'},
    {value:'PIANI_LOTTIZZAZIONE',label:'piani di Lottizzazione convenzionata, (L. 765/1967 e ss. mm. e ii.)'},
    {value:'PIANI_INSEDIAMENTI',label:'piani per gli Insediamenti Produttivi, (L. 865/1971)'},
    {value:'PIANI_RECUPERO',label:'piani di Recupero(L. 457/1978)'},
    {value:'PIANI_ALTRO',label:'altro'}
  ] ;   

  public static CaratterizzazioniIntervento = [
    { label: "Rimessa in ripristino", value: 'C01' },
    { label: "Demolizione", value: 'C02' },
    { label: "Nuovi insediamenti in area urbana", value: 'C03' },
    { label: "Nuovi insediamenti rurali", value: 'C04' },
    { label: "Interventi su manufatti rurali in pietra a secco", value: 'C05' },
    { label: "Interventi su manufatti rurali non in pietra a secco", value: 'C06' },
    { label: "Nuovi insediamenti industriali e commerciali", value: 'C07' },
    { label: "Interventi su insediamenti industriali e commerciali", value: 'C08' },
    { label: "Recinzioni", value: 'C09' },
    { label: "Impianti per la produzione di energia rinnovabile", value: 'C10' },
    { label: "Linee telefoniche o elettriche", value: 'C11' },
    { label: "Infrastrutture primarie (viarie, acqua, gas, ecc.)", value: 'C12' },
    { label: "Miglioramenti fondiari", value: 'C13' },
    { label: "Altro", value: 'C14' },];

  public static DurataIntervento = [
    { label: "Temporaneo", value: 'T' },
    { label: "Permanente", value: 'F' },
  ];

  public static CompatibilitaDPR31_2017 = [
    { label: "non ricadono tra gli interventi di lieve entità di cui all'allegato B al DPR 31/2017", value: 'N' },
    { label: "ricadono tra gli interventi di lieve entità di cui all'allegato B al DPR 31/2017 in quanto:", value: 'S' },//okkio all'ordine
  ];

  public static CompatibilitaDPR31_2017BIS = [
    { label: "Le opere rientrano tra gli interventi di lieve entità di cui all’allegato B al d.P.R. 31/2017:", value: 'S' },
  ];

  public static CompatibilitaDPR139_2010 = [
    { label: "Non ricade tra gli interventi di lieve entità come definiti dal DPR 139/2010", value: 'N' },
    { label: "Ricade tra gli interventi di lieve entità come definiti dal DPR 139/2010", value: 'S' },
  ];

  public static MotiviQualificazioneDPR31_2017 = [
    { value: 'B01', label: "B.1. incrementi di volume non superiori al 10 per cento della volumetria della costruzione originaria e comunque non superiore a 100 mc, eseguiti nel rispetto delle caratteristiche architettoniche, morfotipologiche, dei materiali e delle finiture esistenti. Ogni ulteriore incremento sullo stesso immobile da eseguirsi nei 5 anni successivi all’ultimazione lavori è sottoposto a procedimento autorizzatorio ordinario." },
    { value: 'B02', label: "B.2. realizzazione o modifica di aperture esterne o finestre a tetto riguardanti beni vincolati ai sensi del Codice, art. 136, comma 1, lettere a), b) e C) limitatamente, per quest’ultima, agli immobili di interesse storico-architettonico o storico-testimoniale, ivi compresa l’edilizia rurale tradizionale, isolati o ricompresi nei centri o nuclei storici, purchè tali interventi siano eseguiti nel rispetto delle caratteristiche architettoniche, morfo-tiologiche, dei materiali e delle finiture esistenti." },
    { value: 'B03', label: "B.3. interventi sui prospetti, diversi da quelli di cui alla voce B.2., comportanti alterazione dell’aspetto esteriore degli edifici mediante modifica delle caratteristiche architettoniche, morfotipologiche, dei materiali o delle finiture esistenti, quali modifica delle facciate mediante realizzazione o riconfigurazione di aperture esterne, ivi comprese vetrine e dispositivi di protezione delle attività economiche, o di manufatti quali cornicioni, ringhiere, parapetti, interventi sulle finiture esterne, con rifacimento di intonaci, tinteggiature o rivestimenti esterni, modificativi di quelli preesistenti; realizzazione, modifica o chiusura di balconi o terrazze; realizzazione o modifica sostanziale di scale esterne." },
    { value: 'B04', label: "B.4. interventi sulle coperture, diversi da quelli di cui alla voce B.2., comportanti alterazione dell’aspetto esteriore degli edifici mediante modifica delle caratteristiche architettoniche, morfotipologiche, dei materiali o delle finiture esistenti, quali rifacimento del manto del tetto con materiali diversi; modifiche alle coperture finalizzate all’installazione di impianti tecnologici; modifiche alla inclinazione o alla configurazione delle falde; realizzazione di lastrici o terrazze a tasca; inserimento di canne fumarie o comignoli; realizzazione di finestre a tetto, lucernari, abbaini o elementi consimili." },
    { value: 'B05', label: "B.5. Interventi di adeguamento alla normativa antisismica ovvero finalizzati al contenimento dei consumi energetici degli edifici, laddove comportanti innovazione nelle caratteristiche morfotipologiche, ovvero nei materiali di finitura o di rivestimento preesistenti." },
    { value: 'B06', label: "B.6. interventi necessari per il superamento di barriere architettoniche, laddove comportanti la realizzazione di rampe per il superamento di dislivelli superiori a 60 cm, ovvero la realizzazione di ascensori esterni o di manufatti consimili che alterino la sagoma dell’edificio e siano visibili dallo spazio pubblico." },
    { value: 'B07', label: "B.7. installazione di impianti tecnologici esterni a servizio di singoli edifici, quali condizionatori e impianti di climatizzazione dotati di unità esterna, caldaie, parabole, antenne su prospetti prospicenti la pubblica via o in posizioni comunque visibili dallo spazio pubblico, o laddove si tratti di impianti non integrati nella configurazione esterna degli edifici oppure qualora tali installazioni riguardino beni vincolati ai sensi del Codice, art. 136, comma 1, lettera a), b) e c) limitatamente, per quest’ultima, agli immobili di interesse storico-architettonico o storico-testimoniale, ivi compresa l’edilizia rurale tradizionale, isolati o ricompresi nei centri o nuclei storici." },
    { value: 'B08', label: "B.8. installazione di pannelli solari (termici o fotovoltaici) a servizio di singoli edifici, purchè integrati nella configurazione delle coperture, o posti in aderenza ai tetti degli edifici con la stessa inclinazione e lo stesso orientamento della falda degli edifici ricadenti fra quelli di cui art. 136, comma 1, lettera a), b) e c), del decreto legislativo 22 gennaio 2004, n. 42; installazione di pannelli solari (termici o fotovoltaici) a servizio di singoli edifici su coperture piane in posizioni visibili dagli spazi pubblici esterni." },
    { value: 'B09', label: "B.9. installazione di microgeneratori eolici con altezza complessiva non superiore a ml 1,50 e diametro non superiore a ml 1,00, qualora tali interventi interessino i beni vincolati ai sensi del Codice, art. 136, comma 1, lettera a), b) e c) limitatamente, per quest’ultima, agli immobili di interesse storico-architettonico o storico-testimoniale, ivi compresa l’edilizia rurale tradizionale, isolati o ricompresi nei centri o nuclei storici." },
    { value: 'B10', label: "B.10. installazione di cabine per impianti tecnologici a rate o colonnine modulari ovvero sostituzione delle medesime con altre diverse per tipologia, dimensioni e localizzazione." },
    { value: 'B11', label: "B.11. interventi puntuali di adeguamento della viabilità esistente, quali sistemazioni di rotatorie, riconfigurazione di incroci stradali, realizzazione di banchine, pensiline, marciapiedi e percorsi ciclabili, manufatti necessari per la sicurezza della circolazione, realizzazione di parcheggi a raso con fondo drenante o che assicuri adeguata permeabilità del suolo." },
    { value: 'B12', label: "B.12. interventi sistematici di arredo urbano comportanti l’installazione di manufatti e componenti, compresi gli impianti di pubblica illuminazione." },
    { value: 'B13', label: "B.13. opere di urbanizzazione primaria previste in piani attuativi già valutati ai fini paesaggistici, ove non siano oggetto di accordi di collaborazione tra il Ministero, le regioni e gli enti locali o di specifica disciplina contenuta nel piano paesaggistico approvato ai sensi dell’art. 143 del Codice." },
    { value: 'B14', label: "B.14. interventi di cui alla voce A.12 dell’Allegato «A», da eseguirsi nelle aree di pertinenza degli edifici, ove si tratti di beni vincolati ai sensi dell’art. 136, comma 1, lettera b) del Codice." },
    { value: 'B15', label: "B.15. interventi di demolizione senza ricostruzione di edifici, e manufatti edilizi in genere, privi di interesse architettonico, storico o testimoniale" },
    { value: 'B16', label: "B.16. realizzazione di autorimesse, collocate fuori terra ovvero parzialmente interrate, con volume emergente fuori terra non superiore a 50 mc, compresi i percorsi di accesso e le eventuali rampe" },
    { value: 'B17', label: "B.17. realizzazione di tettoie, porticati, chioschi da giardino di natura permanente e manufatti consimili aperti su più lati, aventi una superficie non superiore a 30 mq o di manufatti accessori o volumi tecnici con volume emergente fuori terra non superiore a 30 mc" },
    { value: 'B18', label: "B.18. interventi sistematici di configurazione delle aree di pertinenza di edifici esistenti, diversi da quelli di cui alla voce B.14, quali: nuove pavimentazioni, accessi pedonali e carrabili, modellazioni del suolo incidenti sulla morfologia del terreno, realizzazione di rampe, opere fisse di arredo, modifiche degli assetti vegetazionali" },
    { value: 'B19', label: "B.19. installazione di tettoie aperte di servizio a capannoni destinati ad attività produttive, o di collegamento tra i capannoni stessi, entro il limite del 10 per cento della superficie coperta preesistente" },
    { value: 'B20', label: "B.20. impianti tecnici esterni al servizio di edifici esistenti a destinazione produttiva, quali strutture per lo stoccaggio dei prodotti ovvero per la canalizzazione dei fluidi o dei fumi mediante tubazioni esterne" },
    { value: 'B21', label: "B.21. realizzazione di cancelli, recinzioni, muri di cinta o di contenimento del terreno, inserimento di elementi antintrusione sui cancelli, le recinzioni e sui muri di cinta, interventi di manutenzione, sostituzione o adeguamento dei medesimi manufatti, se eseguiti con caratteristiche morfotipologiche, materiali o finiture diversi da quelle preesistenti e, comunque, ove interessino beni vincolati ai sensi del Codice, art. 136, comma 1, lettere a), b) e c) limitatamente, per quest'ultima, agli immobili di interesse storico-architettonico o storico-testimoniale, ivi compresa l'edilizia rurale tradizionale, isolati o ricompresi nei centri o nuclei storici" },
    { value: 'B22', label: "B.22. taglio, senza sostituzione, di alberi, ferma l'autorizzazione degli uffici competenti, ove prevista; sostituzione o messa a dimora di alberi e arbusti nelle aree, pubbliche o private, vincolate ai sensi dell'art. 136, comma 1, lettere a) e b) del Codice, ferma l'autorizzazione degli uffici competenti, ove prevista" },
    { value: 'B23', label: "B.23. realizzazione di opere accessorie in soprasuolo correlate alla realizzazione di reti di distribuzione locale di servizi di pubblico interesse o di fognatura, o ad interventi di allaccio alle infrastrutture a rete" },
    { value: 'B24', label: "B.24. posa in opera di manufatti parzialmente o completamente interrati quali serbatoi e cisterne,ove comportanti la modifica permanente della morfologia del terreno o degli assetti vegetazionali, comprese le opere di recinzione o sistemazione correlate; posa in opera in soprasuolo dei medesimi manufatti, con dimensioni non superiori a 15 mc, e relative opere di recinzione o sistemazione" },
    { value: 'B25', label: "B.25. occupazione temporanea di suolo privato, pubblico, o di uso pubblico, mediante installazione di strutture o di manufatti semplicemente ancorati al suolo senza opere murarie o di fondazione per manifestazioni, spettacoli, eventi, o per esposizioni e vendita di merci, per un periodo superiore a 120 e non superiore a 180 giorni nell'anno solare" },
    { value: 'B26', label: "B.26. verande e strutture in genere poste all'esterno (dehors), tali da configurare spazi chiusi funzionali ad attività economiche quali esercizi di somministrazione di alimenti e bevande, attività commerciali, turistico-ricettive, sportive o del tempo libero; installazione di manufatti amovibili o di facile rimozione, consistenti in opere di carattere non stagionale e a servizio della balneazione, quali, ad esempio, chioschi, servizi igienici e cabine; prima collocazione ed installazione dei predetti manufatti amovibili o di facile rimozione aventi carattere stagionale" },
    { value: 'B27', label: "B.27. manufatti in soprasuolo correlati alla realizzazione di pozzi ed opere di presa e prelievo dafalda per uso domestico" },
    { value: 'B28', label: "B.28. realizzazione di ponticelli di attraversamento di corsi d'acqua, o tombinamento parziale dei medesimi, limitatamente al tratto necessario per dare accesso ad edifici esistenti o a fondi agricoli interclusi; riapertura di tratti tombinati di corsi d'acqua" },
    { value: 'B29', label: "B.29. manufatti per ricovero attrezzi agricoli, realizzati con opere murarie o di fondazione, con superficie non superiore a dieci metri quadrati" },
    { value: 'B30', label: "B.30. realizzazione di nuove strutture relative all'esercizio dell'attività ittica con superficie non superiore a 30 mq" },
    { value: 'B31', label: "B.31. interventi di adeguamento della viabilità vicinale e poderale eseguiti nel rispetto della normativa di settore" },
    { value: 'B32', label: "B.32. interventi di ripristino delle attività agricole e pastorali nelle aree rurali invase da formazioni di vegetazione arbustiva o arborea, previo accertamento del preesistente uso agricolo o pastorale da parte delle autorità competenti, ove eseguiti in assenza di piano paesaggistico regionale che individui tali aree" },
    { value: 'B33', label: "B.33. interventi di diradamento boschivo con inserimento di colture agricole di radura" },
    { value: 'B34', label: "B.34. riduzione di superfici boscate in aree di pertinenza di immobili esistenti, per superfici non superiori a 2.000 mq, purché preventivamente assentita dalle amministrazioni competenti" },
    { value: 'B35', label: "B.35. interventi di realizzazione o adeguamento della viabilità forestale in assenza di piani o strumenti di gestione forestale approvati dalla Regione previo parere favorevole del Soprintendente per la parte inerente la realizzazione o adeguamento della viabilità forestale" },
    { value: 'B36', label: "B.36. posa in opera di cartelli e altri mezzi pubblicitari non temporanei di cui all'art. 153, comma 1, del Codice, di dimensioni inferiori a 18 mq, ivi compresi le insegne e i mezzi pubblicitari a messaggio o luminosità variabile, nonché l'installazione di insegne fuori dagli spazi vetrina o da altre collocazioni consimili a ciò preordinate" },
    { value: 'B37', label: "B.37. installazione di linee elettriche e telefoniche su palo a servizio di singole utenze di altezza non superiore, rispettivamente, a metri 10 e a metri 6,30" },
    { value: 'B38', label: "B.38. installazione di impianti delle reti di comunicazione elettronica o di impianti radioelettrici, diversi da quelli di cui all'art. 6, comma 4, del decreto-legge 12 settembre 2014, n. 133, convertito con modificazioni, dalla legge 11 novembre 2014, n. 164, che comportino la realizzazione di supporti di antenne non superiori a 6 metri se collocati su edifici esistenti, e/o la realizzazione di sopralzi di infrastrutture esistenti come pali o tralicci, non superiori a 6 metri, e/o la realizzazione di apparati di telecomunicazioni a servizio delle antenne, costituenti volumi tecnici, tali comunque da non superare l'altezza di metri 3 se collocati su edifici esistenti e di metri 4 se posati direttamente a terra" },
    { value: 'B39', label: "B.39. interventi di modifica di manufatti di difesa dalle acque delle sponde dei corsi d'acqua e dei laghi per adeguamento funzionale" },
    { value: 'B40', label: "B.40. interventi sistematici di ingegneria naturalistica diretti alla regimazione delle acque, alla conservazione del suolo o alla difesa dei versanti da frane e slavine" },
    { value: 'B41', label: "B.41. interventi di demolizione e ricostruzione di edifici e manufatti, ivi compresi gli impianti tecnologici, con volumetria, sagoma ed area di sedime corrispondenti a quelle preesistenti, diversi dagli interventi necessitati di ricostruzione di edifici e manufatti in tutto o in parte crollati o demoliti in conseguenza di calamità naturali o catastrofi. Sono esclusi dal procedimento semplificato gli interventi di demolizione e ricostruzione che interessino i beni di cui all'art. 136, comma 1, lettere a) e b) del Codice" },
    { value: 'B42', label: "B.42. interventi di ripascimento circoscritti di tratti di arenile in erosione, manutenzione di dune artificiali in funzione antierosiva, ripristino di opere di difesa esistenti sulla costa" }
  ];

  public static MotiviQualificazioneDLGS42_2004 = [
    { value: 'DLGS42_2004_A', label: 'a) lavori, realizzati in assenza o difformità dall\'autorizzazione paesaggistica, che non abbiano determinato creazione di superfici utili o volumi ovvero aumento di quelli legittimamente realizzati;' },
    { value: 'DLGS42_2004_B', label: 'b) impiego di materiali in difformità dall\'autorizzazione paesaggistica;' },
    { value: 'DLGS42_2004_C', label: 'c) lavori comunque configurabili quali interventi di manutenzione ordinaria o straordinaria ai sensi dell\'articolo 3 del d.P.R. 6 giugno 2001, n. 380.' }
  ];

  public static IstanzaRinnovo = [
    { value: 'ISTANZA_RINNOVO', label: 'Istanza di rinnovo di autorizzazione paesaggistica anche rilasciata ai sensi dell’art 146 del codice scaduta da non più di un anno e relative ad interventi in tutto o in parte non eseguiti, per progetti conformi a quanto in precedenza autorizzato e alle specifiche prescrizioni di tutela eventualmente sopravvenute.' }
  ];

  public static MotiviQualificazioneDPR139_2010 = [
    { value: 'DPR_139_2010_01', label: "1. Incremento di volume non superiore al 10 per cento della volumetria della costruzione originaria e comunque non superiore a 100mc;" },
    { value: 'DPR_139_2010_02', label: "2. Interventi di demolizione e ricostruzione con il rispetto di volumetria e sagomature preesistenti;" },
    { value: 'DPR_139_2010_03', label: "3. Interventi di demolizione senza ricostruzione o demolizioni di superfetazioni;" },
    {
      value: 'DPR_139_2010_04', label: "4. Interventi sui prospetti degli edifici esistenti, quali:\n" +
        "- aperture di porte e finestre o modifica delle aperture esistenti per dimensione e posizione; - interventi sulle finiture esterne, con rifacimento di intonaci, tinteggiature o rivestimenti esterni, - modificativi di quelli preesistenti; - realizzazione o modifica di balconi o terrazze; - inserimento o modifica di cornicioni, ringhiere, parapetti; chiusura di terrazze o di balconi già chiusi su tre lati mediante installazione di infissi; - realizzazione, modifica o sostituzione di scale esterne\n" +
        "(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);"
    },
    {
      value: 'DPR_139_2010_05', label: "5. interventi sulle coperture degli edifici esistenti, quali:\n" +
        "- rifacimento del manto del tetto e delle lattonerie con materiale diverso; - modifiche indispensabili per l'installazione di impianti tecnologici; - modifiche alla inclinazione o alla configurazione delle falde; - realizzazione di lastrici solari o terrazze a tasca di piccole dimensioni; - inserimento di canne fumarie o comignoli; - realizzazione o modifica di finestre a tetto e lucernari; - realizzazione di abbaini o elementi consimili\n" +
        "(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);"
    },
    { value: 'DPR_139_2010_06', label: "6. modifiche che si rendono necessarie per l'adeguamento alla normativa antisismica ovvero per il contenimento dei consumi energetici degli edifici;" },
    { value: 'DPR_139_2010_07', label: "7. realizzazione o modifica di autorimesse pertinenziali, collocate fuori terra ovvero parzialmente o totalmente interrate, con volume non superiore a 50 mc, compresi percorsi di accesso ed eventuali rampe. Ogni successivo intervento di realizzazione o modifica di autorimesse pertinenziale allo stesso immobile è sottoposto a procedura autorizzatoria ordinaria;" },
    { value: 'DPR_139_2010_08', label: "8. realizzazione di tettoie, porticati, chioschi da giardino e manufatti consimili aperti su piÃ¹ lati, aventi una superficie non superiore a 30 mq;" },
    { value: 'DPR_139_2010_09', label: "9. realizzazione di manufatti accessori o volumi tecnici di piccole dimensioni (volume non superiore a 10 mc);" },
    { value: 'DPR_139_2010_10', label: "10. interventi necessari al superamento delle barriere architettoniche, anche comportanti modifica dei prospetti o delle pertinenze esterne degli edifici, ovvero realizzazione o modifica di volumi tecnici. Sono fatte salve le procedure semplificate ai sensi delle leggi speciali di settore(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_11', label: "11. realizzazione o modifica di cancelli, recinzioni, o muri di contenimento del terreno (la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_12', label: "12. interventi di modifica di muri di cinta esistenti senza incrementi di altezza;" },
    { value: 'DPR_139_2010_13', label: "13. interventi sistematici nelle aree di pertinenza di edifici esistenti, quali: pavimentazioni, accessi pedonali e carrabili di larghezza non superiore a 4 m, modellazioni del suolo, rampe o arredi fissi (la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_14', label: "14. realizzazione di monumenti ed edicole funerarie all'interno delle zone cimiteriali;" },
    { value: 'DPR_139_2010_15', label: "15. posa in opera di cartelli e altri mezzi pubblicitari non temporanei di cui all'art. 153, comma 1 del Codice, di dimensioni inferiori a 18 mq, ivi comprese le insegne per le attività commerciali o pubblici esercizi(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_16', label: "16. collocazione di tende da sole sulle facciate degli edifici per locali destinati ad attività commerciali e pubblici esercizi;" },
    { value: 'DPR_139_2010_17', label: "17. interventi puntuali di adeguamento della viabilità esistente, quali: adeguamento di rotatorie, riconfigurazione di incroci stradali, realizzazione di banchine e marciapiedi, manufatti necessari per la sicurezza della circolazione, nonchÃ© quelli relativi alla realizzazione di parcheggi a raso a condizione che assicurino la permeabilità del suolo, sistemazione e arredo di aree verdi;" },
    { value: 'DPR_139_2010_18', label: "18. interventi di allaccio alle infrastrutture a rete, ove comportanti la realizzazione di opere in soprasuolo;" },
    { value: 'DPR_139_2010_19', label: "19. linee elettriche e telefoniche su palo a servizio di singole utenze di altezza non superiore, rispettivamente, a metri 10 e a metri 6,30;" },
    { value: 'DPR_139_2010_20', label: "20. adeguamento di cabine elettriche o del gas, ovvero sostituzione delle medesime con altre di tipologia e dimensioni analoghe;" },
    { value: 'DPR_139_2010_21', label: "21. interventi sistematici di arredo urbano comportanti l'installazione di manufatti e componenti, compresi gli impianti di pubblica illuminazione;" },
    { value: 'DPR_139_2010_22', label: "22. installazione di impianti tecnologici esterni per uso domestico autonomo, quali condizionatori e impianti di climatizzazione dotati di unità esterna, caldaie, parabole, antenne(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_23', label: "23. parabole satellitari condominiali e impianti di condizionamento esterni centralizzati, nonchÃ© impianti per l'accesso alle reti di comunicazione elettronica di piccole dimensioni con superficie non superiore ad 1 mq o volume non superiore ad 1 mc(la presente voce non si applica agli immobili soggetti a tutela ai sensi dell'articolo 136, comma 1, lettere a), b) e c), del Codice);" },
    { value: 'DPR_139_2010_24', label: "24. Installazione di impianti di radiocomunicazioni elettroniche mobili, di cui all'articolo 87 del decreto legislativo 1Â° agosto 2003, n. 259, che comportino la realizzazione di supporti di antenne non superiori a 6 metri se collocati su edifici esistenti, e/o la realizzazione di sopralzi di infrastrutture esistenti come pali o tralicci, non superiori a 6 metri, e/o la realizzazione di apparati di telecomunicazioni a servizio delle antenne, costituenti volumi tecnici, tali comunque da non superare l'altezza di metri 3 se collocati su edifici esistenti e di metri 4 se posati direttamente a terra;" },
    { value: 'DPR_139_2010_25', label: "25. installazione in soprasuolo di serbatoi di GPL di dimensione non superiore a 13 mc, e opere di recinzione e sistemazione correlate;" },
    { value: 'DPR_139_2010_26', label: "26. impianti tecnici esterni al servizio di edifici esistenti a destinazione produttiva, quali sistemi per la canalizzazione dei fluidi mediante tubazioni esterne, lo stoccaggio dei prodotti e canne fumarie;" },
    { value: 'DPR_139_2010_27', label: "27. posa in opera di manufatti completamente interrati (serbatoi, cisterne etc.), che comportino la modifica della morfologia del terreno, comprese opere di recinzione o sistemazione correlate;" },
    { value: 'DPR_139_2010_28', label: "28. pannelli solari, termici e fotovoltaici fino ad una superficie di 25 mq (la presente voce non si applica nelle zone territoriali omogenee \"A\" di cui all'articolo 2 del decreto ministeriale n. 1444 del 1968, e ad esse assimilabili, e nelle aree vincolate ai sensi dell'articolo 136, comma 1, lettere b) e c), del Codice), ferme restando le diverse e piÃ¹ favorevoli previsioni del decreto legislativo 30 maggio 2008, n. 115, recante \"Attuazione della direttiva 2006/32/CE relativa all'efficienza degli usi finali dell'energia e i servizi energetici e abrogazione della direttiva 93/76/CEE\", e dell'articolo 1, comma 289, della legge 24 dicembre 2007, n. 244, recante \"Disposizioni per la formazione del bilancio annuale e pluriennale dello Stato(legge finanziaria 2008)\";" },
    { value: 'DPR_139_2010_29', label: "29. nuovi pozzi, opere di presa e prelievo da falda per uso domestico, preventivamente assentiti dalle Amministrazioni competenti, comportanti la realizzazione di manufatti in soprasuolo;" },
    { value: 'DPR_139_2010_30', label: "30. tombinamento parziale di corsi d'acqua per tratti fino a 4 m ed esclusivamente per dare accesso ad abitazioni esistenti e/o a fondi agricoli interclusi, nonchÃ© la riapertura di tratti tombinati di corsi d'acqua;" },
    { value: 'DPR_139_2010_31', label: "31. interventi di ripascimento localizzato di tratti di arenile in erosione, manutenzione di dune artificiali in funzione antierosiva, ripristino di opere di difesa esistenti sulla costa;" },
    { value: 'DPR_139_2010_32', label: "32. ripristino e adeguamento funzionale di manufatti di difesa dalle acque delle sponde dei corsi d'acqua e dei laghi;" },
    { value: 'DPR_139_2010_33', label: "33. taglio selettivo di vegetazione ripariale presente sulle sponde o sulle isole fluviali;" },
    { value: 'DPR_139_2010_34', label: "34. riduzione di superfici boscate in aree di pertinenza di immobili esistenti, per superfici non superiori a 100 mq, preventivamente assentita dalle amministrazioni competenti;" },
    { value: 'DPR_139_2010_35', label: "35. ripristino di prati stabili, prati pascolo, coltivazioni agrarie tipiche, mediante riduzione di aree boscate di recente formazione per superfici non superiori a 5000 mq, preventivamente assentiti dalle amministrazioni competenti;" },
    { value: 'DPR_139_2010_36', label: "36. taglio di alberi isolati o in gruppi, ove ricompresi nelle aree di cui all'articolo 136, comma 1, lettere c) e d), del Codice, preventivamente assentito dalle amministrazioni competenti;" },
    { value: 'DPR_139_2010_37', label: "37. manufatti realizzati in legno per ricovero attrezzi agricoli, con superficie non superiore a 10 mq;" },
    { value: 'DPR_139_2010_38', label: "38. occupazione temporanea di suolo privato, pubblico, o di uso pubblico, con strutture mobili, chioschi e simili, per un periodo superiore a 120 giorni;" },
    { value: 'DPR_139_2010_39', label: "39. strutture stagionali non permanenti collegate ad attività turistiche, sportive o del tempo libero, da considerare come attrezzature amovibili." }
  ];

  public static MotiviQualificazioneDPCM_2005 = [
    { value: 'DPCM_2005_01', label: "ordinari;" },
    { value: 'DPCM_2005_02', label: "di grande impegno territoriale a carattere areale (1);" },
    { value: 'DPCM_2005_03', label: "di grande impegno territoriale a carattere lineare o a rete (2)" }
  ];
  public static readonly TipiProcedimento: any[] = [
    { label: "AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)", value: 'AUT_PAES_ORD', project: Projects.AUTPAE },
    { label: "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010– art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)", value: 'AUT_PAES_SEMPL_DPR_139_2010', project: Projects.AUTPAE },
    { label: "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017– art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)", value: 'AUT_PAES_SEMPL_DPR_31_2017', project: Projects.AUTPAE },
    { label: "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA", value: 'ACCERT_COMPAT_PAES_DLGS_42_2004', project: Projects.AUTPAE },
    { label: "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)", value: 'ACCERT_COMPAT_PAES_DPR_31_2017', project: Projects.AUTPAE },
    { label: "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)", value: 'ACCERT_COMPAT_PAES_DPR_139_2010', project: Projects.AUTPAE },
    { label: "PARERE DI COMPATIBILITÀ PAESAGGISTICA art. 96.1 lett.d NTA PPTR", value: 'PARERE_COMP_96D', project: Projects.PARERI }, //solo per applicazione pareri
    { label: "Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT", value: 'PUTT_X', project: Projects.PUTT }, //solo per applicazione putt
    { label: "Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT e dell’art. 146 D.Lgs 42/04", value: 'PUTT_DLGS_42_2004', project: Projects.PUTT } //solo per applicazione putt
  ];

  /*public static readonly UfficiCompetenza = [
    { name: "UFFICIO COMUNE BITONTO", id: 1 },
    { name: "UFFICIO COMUNE ADELFIA", id: 2 },
    { name: "UFFICIO COMUNE ACQUAVIVA", id: 3 },
    { name: "UFFICIO COMUNE CASSANO", id: 4 },
  ];*/

  public static readonly ImmobiliAreeInteresse = [
    { id: 1, codice: "PII001", denominazione: "PALAZZO FIZZAROTTI 1", label: "LABEL IMMOBILI AREE INTERESSE 1", ente: "11" },
    { id: 2, codice: "PII002", denominazione: "TEATRO ZZZZZ 2", label: "LABEL IMMOBILI AREE INTERESSE 2", ente: "69" },
    { id: 3, codice: "PII003", denominazione: "TEATRO ZZZZZ 3", label: "LABEL IMMOBILI AREE INTERESSE 3", ente: "69" },
    { id: 4, codice: "PII004", denominazione: "ZONA TEATRO ZZZZZ TRULLI 4", label: "LABEL IMMOBILI AREE INTERESSE 4", ente: "11" },
    { id: 5, codice: "PII005", denominazione: "ZONA TEATRO ZZZZZ TRULLI 5", label: "LABEL IMMOBILI AREE INTERESSE 5", ente: "11" },
    { id: 6, codice: "PII006", denominazione: "ZONA TEATRO DEI TRULLI 6", label: "LABEL IMMOBILI AREE INTERESSE 6", ente: "140" }
  ];

  public static readonly PaesaggioRurale = [
    { id: 1, codice: "PAR001", denominazione: "ZONA DAUNIA ", label: "LABEL PAE001", ente: "11" },
    { id: 2, codice: "PAR002", denominazione: "ZONA TIPICA DEI MOSAICI 2", label: "LABEL PAESAGGIO RURALE001", ente: "11" },
    { id: 3, codice: "PAR003", denominazione: "ZONA TIPICA DEI MOSAICI 3", label: "LABEL PAESAGGIO RURALE002", ente: "140" },
    { id: 4, codice: "PAR004", denominazione: "ZONA TIPICA DEI MOSAICI 4", label: "LABEL PAESAGGIO RURALE003", ente: "69" },
    { id: 5, codice: "PAR005", denominazione: "ZONA TIPICA DEI MOSAICI 5", label: "LABEL PAESAGGIO RURALE004", ente: "11" },
    { id: 6, codice: "PAR006", denominazione: "ZONA TIPICA DEI MOSAICI 6", label: "LABEL PAESAGGIO RURALE005", ente: "69" }
  ];
  public static readonly ParchiRiserve = [
    { id: 1, codice: "PAE001", denominazione: "ZONA TIPICA DEI TRULLI 1", label: "LABEL PAERIS001", ente: "11" },
    { id: 2, codice: "PAE002", denominazione: "ZONA TIPICA DEI TRULLI 2", label: "LABEL PAERIS002", ente: "140" },
    { id: 3, codice: "PAE003", denominazione: "ZONA TIPICA DEI TRULLI 3", label: "LABEL PAERIS003", ente: "11" },
    { id: 4, codice: "PAE004", denominazione: "ZONA TIPICA DEI TRULLI 4", label: "LABEL PAERIS004", ente: "11" },
    { id: 5, codice: "PAE005", denominazione: "ZONA TIPICA DEI TRULLI 5", label: "LABEL PAERIS005", ente: "140" },
    { id: 6, codice: "PAE006", denominazione: "ZONA TIPICA DEI TRULLI 6", label: "LABEL PAERIS006", ente: "69" }
  ];


  public static readonly StatoFascicoloStatusAttribute = [
    { enumVal: StatoFascicolo.WORKING, id: 0, label: "inLavorazione", color: "grey", translated: "In Lavorazione", lettera: 'L' },
    { enumVal: StatoFascicolo.TRANSMITTED, id: 1, label: "trasmesso", color: "#2b86c2", translated: "Trasmesso", lettera: 'T' },
    { enumVal: StatoFascicolo.CANCELLED, id: 2, label: "annullato", color: "#f00", translated: "Annullato", lettera: 'A' },
    { enumVal: StatoFascicolo.FINISHED, id: 3, label: "concluso", color: "#5cb85c", translated: "Concluso", lettera: 'C' },
    { enumVal: StatoFascicolo.SELECTED, id: 4, label: "selezionato", color: "#f5d90a", translated: "In corso", lettera: 'I' },
    { enumVal: StatoFascicolo.ON_MODIFY, id: 5, label: "inModifica", color: "#d47c02", translated: "In Modifica", lettera: 'M' },
  ];

  public static readonly RegistrationStatusAttribute2 = [
    { enumVal: RegistrationStatus.CANCELLED, id: 0, label: "annullato", color: "grey", translated: "Annullato" },
    { enumVal: RegistrationStatus.SELECTED, id: 3, label: "inCorso", color: "#5cb85c", translated: "In corso" },
    { enumVal: RegistrationStatus.ON_MODIFY, id: 2, label: "inModifica", color: "#f00", translated: "In Modifica" },
    { enumVal: RegistrationStatus.FINISHED, id: 1, label: "concluso", color: "#2b86c2", translated: "Concluso" },
  ];

  public static readonly EsitoAutorizzazione: any[] =
    [
      { value: 'AUTORIZZATO', label: "Autorizzato" },
      { value: 'NON_AUTORIZZATO', label: "Non autorizzato" },
      { value: 'AUT_CON_PRESCRIZ', label: "Autorizzato con Prescrizioni" }
    ];

  public static readonly EsitoAutorizzazionePublic: any[] =
    [
      { value: 'AUTORIZZATO', label: "Autorizzato" },
      { value: 'AUT_CON_PRESCRIZ', label: "Autorizzato con Prescrizioni" }
    ];

  public static readonly StatoRegistrazione: any[] =
    [
      { value: 'SELECTED', label: "In corso" },
      { value: 'FINISHED', label: "Conclusa" },
      { value: 'CANCELLED', label: "Annulata" },
      { value: 'ON_MODIFY', label: "In modifica" }
    ];

  public static readonly EsitiVerifica =
    [
      { value: "SAMPLING_PENDING", label: "In attesa di selezione" },
      { value: "CHECK_IN_PROGRESS", label: "Verifica in corso" },
      { value: "POSITIVE", label: "Verifica Positiva" },
      { value: "NEGATIVE", label: "Verifica negativa" },
      { value: "NOT_SELECTED", label: "Non selezionata" },
      { value: "CANCELLED", label: "Annullata" },
      { value: "ON_MODIFY", label: "In modifica" },
      { value: "NOT_SAMPLED", label: "Non campionata" }
    ]

  public static readonly DittaQualitaDi = [
    { value: 'RAPPRES_LEGALE', label: "Rappresentante Legale" },
    { value: 'TECNICO_INCARICATO', label: "Tecnico Incaricato" },
    { value: 'ALTRO', label: "Altro" }
  ]



  public static readonly TipiRuoloInDitta =
    [
      { label: "Titolare", value: "T" },
      { label: "Rappresentante", value: "R" },
      { label: "Amministratore", value: "A" },
      { label: "dipendente", value: "D" },
      { label: "Altro", value: "Z" }
    ];

  public static readonly MockComuni =
    [
      { label: "Bari", value: "11" },
      { label: "Barletta", value: "69" },
      { label: "Mola di Bari", value: "29" },
      { label: "Sammichele di Bari", value: "40" },
      { label: "Brindisi", value: "48" },
      { label: "Lecce", value: "140" },
      { label: "Sannicandro di Bari", value: "41" },
      { label: "altro", value: "33" }
    ];

  public static readonly MockComuniUser = [{ label: "Bari", value: "A662" }];


  public static AllegatiProvvedimento(tipoProcedimento: string): PlainTypeNumericId[] {
    let ret = [
      { id: null, nome: "Provvedimento Finale (Pubblico)", descrizione: "mandatario", data: null, type: TipoAllegato.PROVVEDIMENTO_FINALE },
      { id: null, nome: "Provvedimento Finale (Riservato)", descrizione: "opzionale", data: null, type: TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO }
    ];
    if (['AUT_PAES_ORD',
      'AUT_PAES_SEMPL_DPR_139_2010',
      'AUT_PAES_SEMPL_DPR_31_2017',
      'ACCERT_COMPAT_PAES_DLGS_42_2004',
      'PUTT_DLGS_42_2004'].indexOf(tipoProcedimento) >= 0) {
      ret.push({ id: null, nome: "Parere MIBAC (Pubblico)", descrizione: "mandatario", data: null, type: TipoAllegato.PARERE_MIBAC });
      ret.push({ id: null, nome: "Parere MIBAC (Riservato)", descrizione: "opzionale", data: null, type: TipoAllegato.PARERE_MIBAC_PRIVATO });
    }
    return ret;
  }

  private static readonly ELABORATI_DI_PROGETTO = 'Elaborati di progetto';
  private static tipiAllegati: PlainTypeNumericId[] =
    [
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ISTANZA, nome: 'Istanza' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.VERBALE, nome: 'Verbale Conferenza Servizi' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PARERE, nome: 'Parere/Verbale commissioni paesaggio' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PREAVVISO, nome: 'Preavviso di diniego' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ALTRI_PARERI, nome: 'Altri pareri acquisiti dall\'Ente' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ELABORATI, nome: CONST.ELABORATI_DI_PROGETTO },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PARERI_SCHEDA, nome: 'Scheda conoscitiva del manufatto e del contesto rurale (rif. Capitolo 2 dell\'elaborato dei PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell\'Edilizia e dei Beni Rurali) **' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.SCHEDA_PROGETTO, nome: 'Scheda di progetto (rif. Capitolo 3 dell\'elaborato dei PPTR 4.4.6 -  Linee guida per il recupero, la manutenzione e il riuso dell\'Edilizia e dei Beni Rurali) **' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.INTEGRAZIONI, nome: 'Integrazioni documentali' },
    ];

    public static tipiAllegatiResponsabile: PlainTypeNumericId[] = [
      { id: null, data: null, descrizione: "mandatario", type: TipoAllegato.DOCUMENTO_RICONOSCIMENTO, nome: 'Documento di riconoscimento' },
    ];
    
    private static tipiAllegatiPutt: PlainTypeNumericId[] =
    [
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ATTESTAZIONE_CONFORMITA, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PREAVVISO, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.RICHIESTA_SOPRINTENDENZA, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PROPOSTA_PROVVEDIMENTO, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.DOCUMENTAZIONE_FOTOGRAFICA, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.PARERE, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.RETTIFICA_AUTORIZZAZIONE, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.VERBALE, nome: '' },
      { id: null, data: null, descrizione: "mandatario", type: TipoAllegato.ISTANZA, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ALTRI_PARERI, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ELABORATI, nome: '' },
      { id: null, data: null, descrizione: "opzionale", type: TipoAllegato.ALTRO, nome: '' }
    ];  
  private static allegatiPutt(tipoProcedimento:string):PlainTypeNumericId[]{
    //qui gestisco eventuali obbligatorietà / filtri per tipo procedimento
    return this.tipiAllegatiPutt;
  }

  public static AllegatiAllegati(tipoProcedimento: string): PlainTypeNumericId[] {
    let ret: PlainTypeNumericId[] = [];
    if(this.isPutt()){
      return this.allegatiPutt(tipoProcedimento);
    }
    //filtro i tipi allegato... per pareri:[ISTANZA,ELABORATI,INTEGRAZIONI]
    //per autpae tutto tranne INTEGRAZIONI
    ret = this.tipiAllegati.filter(
      el => this.isPareri() ?
        [TipoAllegato.ISTANZA, TipoAllegato.ELABORATI, TipoAllegato.INTEGRAZIONI].findIndex(tipo => tipo == el.type) >= 0 :
        [TipoAllegato.INTEGRAZIONI].findIndex(tipo => tipo == el.type) < 0);
    ret.forEach(el => el.nome == CONST.ELABORATI_DI_PROGETTO ? el.descrizione = "opzionale" : null);
    if (['ACCERT_COMPAT_PAES_DPR_139_2010',
      'ACCERT_COMPAT_PAES_DLGS_42_2004',
      'ACCERT_COMPAT_PAES_DPR_31_2017'
      ].indexOf(tipoProcedimento) >= 0) {
      ret.forEach(el => el.nome == CONST.ELABORATI_DI_PROGETTO ? el.descrizione = "mandatario" : null);
    }
    if (['PARERE_COMP_96D'].indexOf(tipoProcedimento) >= 0) {
      ret.forEach(el => {
        if ([TipoAllegato.ELABORATI, TipoAllegato.ISTANZA].findIndex(tt => el.type == tt) >= 0)
         { el.descrizione = "mandatario"; 
           }
      });
    }
    return ret;
  }



  public static readonly DestinatariComunicaz: DestinatariComunicazione[] =
    [
      { denominazione: 'REGIONE PUGLIA', mailAddress: 'regionepuglia@pectestmail.it', to: true },
      { denominazione: 'COMUNE INTERESSATO', mailAddress: 'comuneinteressato@pectestmail.it', to: false },
      { denominazione: 'MARIO ROSSI', mailAddress: 'mariorossi@pectestmail.it', to: false },
    ];



  public static getLabelFromValue(arr: any[], value: any,
    labelKey: string = 'label', labelValue: string = 'value') {
    if (!Array.isArray(arr)) { return value; }
    //console.log('cerco ' + value + ' labelKey:' + labelKey + ' labelValue:' + labelValue + ' in {}', arr);
    let el = arr.find(el => { return el[labelValue] == value; });
    //console.log('beccato {}', el);
    let ret: any;
    if (el && el[labelValue]) {
      ret = el[labelKey];
    }
    return ret;
  }

  public static readonly roles =
    [
      'admin',
      'ap',
      'funz',
      'viewer'
    ];

  public static isAutpae(): boolean {
      return environment.project == Projects.AUTPAE;
  }

  public static isPareri(): boolean {
    return environment.project == Projects.PARERI;
  }

  public static isPutt(): boolean {
    return environment.project == Projects.PUTT;
  }
 
  public static readonly MAX_1MB: number = 1*1024*1024; 
  public static readonly MAX_5MB: number = 5*1024*1024; 
  public static readonly MAX_10MB: number = 10*1024*1024; 
  public static readonly MAX_50MB: number = 50*1024*1024; 
  public static readonly MAX_100MB: number = 100*1024*1024; 
  public static readonly MAX_LEN_AUTOCOMPLETE_CODICE:     number = 18;
  public static readonly MAX_LEN_AUTOCOMPLETE_COMUNE:     number = 35; // San Valentino in Abruzzo Citeriore
  public static readonly MAX_LEN_CODICE_INTERNO_ENTE:     number = 40;
  public static readonly MAX_LEN_NUMERO_INTERNO_ENTE:     number = 40;
  public static readonly MAX_LEN_PROTOCOLLO_INTERNO_ENTE: number = 40;
  public static readonly MAX_LEN_OGGETTO_INTERVENTO:      number = 500;
  public static readonly MAX_LEN_NOTE:                    number = 500;
  public static readonly MAX_LEN_NOTE_PARTICELLA:         number = 200;
  public static readonly MAX_LEN_NOME:                    number = 40;
  public static readonly MAX_LEN_COGNOME:                 number = 40;
  public static readonly MAX_LEN_NAZIONE:                 number = 50;
  public static readonly MAX_LEN_PROVINCIA:               number = 30;
  public static readonly MAX_LEN_CITTA:                   number = 50; // Straniere
  public static readonly MAX_LEN_VIA:                     number = 50;
  public static readonly MAX_LEN_CIVICO:                  number = 20;
  public static readonly MAX_LEN_PIANO:                   number = 20;
  public static readonly MAX_LEN_INTERNO:                 number = 20;
  public static readonly MAX_LEN_EMAIL_PEC:               number = 80;
  public static readonly MAX_LEN_TELEFONO:                number = 30;
  public static readonly MAX_LEN_FAX:                     number = 30;
  public static readonly MAX_LEN_PARTITA_IVA:             number = 11;
  public static readonly MAX_LEN_RUOLO_DITTA:             number = 40;
  public static readonly MAX_LEN_DESCRIZIONE_DITTA:       number = 60;
  public static readonly MAX_LEN_INTERVENTO_ALTRO:        number = 100;
  public static readonly MAX_LEN_DELIBERA_NUM:            number = 40;
  public static readonly MAX_LEN_DELIBERA_OGGETTO:        number = 500;
  public static readonly MAX_LEN_DELIBERE_PRECEDENTI:     number = 500;
  public static readonly MAX_LEN_DESTINAZIONE_USO:        number = 500;
  public static readonly MAX_LEN_SEZIONE:                 number = 20;
  public static readonly MAX_LEN_FOGLIO:                  number = 20;
  public static readonly MAX_LEN_PARTICELLA:              number = 20;
  public static readonly MAX_LEN_PROVVEDIMENTO:           number = 50;
  public static readonly MAX_LEN_OGGETTO_EMAIL:           number = 200;
  public static readonly MAX_LEN_DESTINATARIO_EMAIL:      number = 80;  // denominazione
  public static readonly MAX_LEN_TITOLO_DOCUMENTO:        number = 80;
  public static readonly MAX_LEN_DESCRIZIONE_DOCUMENTO:   number = 80;  // usato anche per i template
  public static readonly MAX_LEN_TEXT_TEMPLATE_DOCUMENT:  number = 400;  // GENERICO: usato per tutti i campi (plain)Text dei template documentazioni
  public static readonly MAX_LEN_DENOMINAZIONE_ENTE:      number = 60;
  
  public static readonly mimePDF=['application/pdf'];
  public static readonly mimeTypeForScansioni=['image/png',...CONST.mimePDF,'image/jpeg'];
  public static readonly mimeTypeZip=['application/zip','application/x-zip-compressed','application/x-7z-compressed',
  'application/vnd.rar','application/x-tar'];
  public static GRUPPO_UTENTE_HEADER: string='GRUPPO-UTENTE';


}
