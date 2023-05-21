import { SelectItem } from 'primeng/primeng';
import { GroupType, GroupRole } from './enum';
export interface Lang {
    code: string;
    name: string;
}

export interface Menu {
    name: string;
    link: string;
    icon?: string;
    target?: string;
    roles?: string;
}
export interface PrimeNgMenu {
    label: string;
    items: PrimeNgMenuItems[];
}
export interface PrimeNgMenuItems {
    label: string;
    icon: string;
    routerLink: string;
    allowedRoles: string[];
    grant?: AllowedGroup|AllowedGroup[]|'*'|'ALL'|'all';
    subMenu?:PrimeNgMenuItems[];
    show?:boolean;
}

export interface AllowedGroup
{
    group: GroupType;
    roles?: GroupRole[];
}

//************************ SEZIONE PER LE MAIL
export interface Role{
    id:string;
    name:string;
    codice:string;
}

//Bean response
export interface ResponseBean<T> {
    code: string;
    message: string;
    partialSize: number;
    payload: T;
    size: number;
}

export interface Utente{
    loginame : string
    mail: string
    pec : string
    tipoEnte : string
    codEnte: string
    denEnte : string
    id: string
    statoInvio?: number
}

export interface UtenteIn {
    email?: string
    tipoUtente?: string
    codiceEnte?: string
}

export interface TabellaComunicazione {
    idComunicazione: string
    idEmlCmis: string
    data:Date
    nrProtocollo:string
    oggetto: string
    testo: string
    annoComunicazione: string
    denominazioneTipo: string
    nome:string
    mittente:Utente
    destinatario:Utente
    codiceComunicazione:string
    
}

export interface RicercaComunicazione {
    from?: UtenteIn
    to?: UtenteIn
    dateFrom?: Date
    dateTo?: Date
    numeroProtocollo?: number
    tipoComunicazione?: string[]
    page?: number
    rowsOnPage?: number
    sortBy?: string
    sortOrder?: string
}

export interface NuovaTabellaComunicazione {
    idComunicazione: string
    idEmlCmis: string
    data:Date
    nrProtocollo:string
    oggetto: string
    testo: string
    annoComunicazione: string
    denominazioneTipo: string
    nome:string
    mittente:Utente
    destinatari:Utente[]
    codiceComunicazione:string
}

export interface Ricevuta {
    idCms:string,
    tipoRicevuta:string,
    oggetto:string,
    data:Date
    allegati: AllegatoRicevuta[]

}

export type AllegatoRicevuta={
    id:string;
    dimensione:number;
    idCmis:string;
    nomeOriginale:string;
    nrProtocollo:string;
}
export class FromTo {
    from: string;
    to: string;
    constructor(from: string, to: string) {
        this.from = from;
        this.to = to;
    }
}


export class ElencoComunicazione {
    elencoFromRegione;
    elencoToRegione;
    constructor() {
        this.elencoFromRegione = [new FromTo("Regione", "Province"),
        new FromTo("Regione", "ARO"),
        new FromTo("Regione", "Comuni"),
        new FromTo("Regione", "Impianti"),
        new FromTo("Regione", "Gestori"),
        new FromTo("Regione", "ARPA")]
    }
}

export interface DettaglioComunicazione {
    id: string
    mittente: string
    destinatario: string
    data: Date
    tipologia: string;
    numeroProtocollo?: number
    pecEmail: string
    allegati: Allegato[]
    ricevute: Ricevuta[]
    mailMittente: string;
    mailDestinatario: string;
    pec: boolean;
    codiceComunicazione: string;
    statoInvio:number;
    errore:string;
    descrizioneErrore: string;
}

export interface Colonna{
    field: string
    header: string
}

export interface AutocompleteResponse{
    label: string
    value: any
}
export interface AutocompleteRequest {
    filter: string;
    limit: number;
}

export class RequestAllegato 
{
  praticaId: string;
  referenteId?: number;
  tipiContenuto?:number[];
  nomeContenuto?:string;
  allegatoId?: string; 
}

//************************ FINE SEZIONE PER LE MAIL */

export interface TipoSelect {
    label: string
    value: any
}


export interface SicZps {
    id: number;
    codice: string;
    denominazione: string;
    label: string;
}

export interface TipoAllegato {
    label: string
    items: TipoSelect[]
}

export interface ParticellaRequest {
    id: string;
    type?: string;
    ente: string;
    foglio: string;
    sezione: string;
    particella: string;
    sub: string;
    codiceEnte: string;
}

export type OptionType = "checkbox"|"radiobutton";

export interface OptionNode
{
  label: string;
  value: string;
  key: number;
  hasText: boolean;
  orderIndex: number;
  tooltip?: string;
  disabled?: boolean
}

export interface HierarchicalOption
{
  dependency: number;
  options: OptionNode[];
  children?: HierarchicalOption;
  childStyle?: string;
  name: string;
  type: OptionType;
}

export interface Allegato {
    idAllegatiRichiesta?: number
    idAllegato?: number
    pathAlfresco?: string
    idAlfresco?: string
    idTipo?: string
    numero?: string
    dataRilascio?: Date
    dataScadenza?: Date
    enteRilascio?: string
    fileName?: string;
    descrizione?: string;
}

export interface BaseMap {
    thumbnail: string;
    name: string;
    url: string;
}

export const basemap: BaseMap[] = [
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2006.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2006/ImageServer",
        name: "Ortofoto 2006"
    },
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2010.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2010/ImageServer",
        name: "Ortofoto 2010"
    },
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2011.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2011/ImageServer",
        name: "Ortofoto 2011"
    },
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2013/ImageServer",
        name: "Ortofoto 2013"
    },
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2016/ImageServer",
        name: "Ortofoto 2016"
    },
    {
        thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/trasp.png",
        url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/SfondoTrasp/MapServer",
        name: "Trasparente"
    }
];

export class defaultSelectModel {
    value: number;
    label: string;
}

//Base bean for paginated request
export interface PaginatedRequest {
    page: number;
    rowsOnPage: number;
    myFilter?: string;
    sortBy: string;
    sortOrder: string;
    sortOrderSql?: string;
}
export interface Lang {
    code: string;
    name: string;
}

export interface Idname {
    id: number;
    name: string;
}
export interface TipoIntervento extends Idname { };
export interface StatoFascicolo extends Idname { };
export interface EsitoProvvedimento extends Idname { };
export interface UfficioCompetenza extends Idname { };

export interface SchemaRicerca
{
    registrazione: boolean;
    verifica: boolean;
}

export enum PevRole {
    FUNZ_REG_PEV,
    AMM_PEV,
    OP_ENTE_PEV
}

export interface ConfigurazioneCampionamento
{
    campionamentoAttivo: boolean;
    intervalloCampionamento: number;
    tipoCampionamentoSuccessivo: "PREDEFINITO"|"TRASMISSIONE";
    percentualeIstanze: number;
    giorniNotificaCampionamento: string;
    giorniNotificaVerifica: string;
    intervalloVerifica: number;
    esitoPubblico: boolean;
    applicaInCorso: boolean;
}

export interface PannelloAmministratore extends ConfigurazioneCampionamento
{
    comunicazioni: string[];
    documentazione: string[];
    osservazioni: string[];
    statoRegistrazione: boolean;
    esitoVerifica: boolean;
    comunicazioniAttivo: boolean;
    osservazioniAttivo: boolean;
    documentazioneAttivo: boolean;
} 

//Configurazione Protocllo
export interface ConfigurazioneProtocollo {
	algoritmoImpronta           :string;
	endpoint                    :string;
	amministrazione             :string;
	mittente?                   :string;
	aoo                         :string;
	registro                    :string;
	utente                      :string;
    pwd                         :string;
    numeroRegistrazione         :string;
	tipoIndirizzoTelematico     :string;
	valoreIndirizzoTelematico   :string;
	indirizzoPostale            :string;
	denominazioneAOO            :string;	
    dataRegistrazione           :Date;
    unitaOrganizzativa?         :string;
    amministrazioneDenominazione:string;
    mittAooCodiceAoo?           :string;
}

/**
 * corrispondente common.utente_attribute
 */
export interface UtenteAttributeDTO{
    //utenteId:number;
    //applicazioneId:number;
    pec:string;
    mail:string;
    birthDate?:Date;
    birthPlace?:string;
    phoneNumber?:string;
    mobileNumber?:string;
    lastRichiestaAbilitazione?:number;
}

export interface SimpleFormControlData
{
    formControlName?: string;
    type: "text"|"password"|"number"|"email"|"url"|"checkbox"|"dropdown"|"category"|"date"|'hidden';
    label: string;
    required?: boolean;
    children?: SimpleFormControlData[];
    validators?: any[];
    options?: SelectItem[];
    filter?: boolean;
    readonly?: boolean;
    mindate?: Date;
    maxdate?: Date;
    yearRange?: string;
    change?: (_: any) => any;
}

export interface DiogeneApplicationConfigbean {
	codiceApplicazione:string;
	gatewayUsername:string;
	gatewayPassword:string;
	gatewayConsumerKey:string;
	gatewayConsumerSecret:string;
	diogeneUsername:string;
	diogenePassword:string;
	diogeneConsumerKey:string;
	diogeneConsumerSecret:string;
	idTitolario:string;
	idVoceTitolario:string;
	idCartellaRoot:string;
	nomeCartellaRoot:string;
	environment:string; //SVILUPPO o PRODUZIONE riferiti ad ambiente DIOGENE
	idAoo:string;
	enabled:boolean; //default a true
}


export interface EnteCss{
    id:number;
    nome:string;
    footer:string;
    link:string;
    hasLogo:boolean;
  }