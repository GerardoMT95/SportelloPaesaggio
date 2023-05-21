import { PlainTypeStringId } from './../../../shared/components/model/logged-user';
import { OrderBy } from "src/app/shared/models/search.models";

export type TipoEnte = "AC" | "CN" | "CO" | "E" | "ED" | "P" | "R" | "SI" | "SS" | "SZ" | "UC" | "UR";

export type DettaglioCom =
    {
        codiceComunicazione: string;
        data: string;
        numeroProtocollo: string;
        mittente: string;
        mailMittente: string;
        oggetto: string;
        tipologia: string;
        pec: boolean;
        testo: string;
        //allegati:Allegato[];
        allegati: Allegato[];
        //idCms:string;
        ricevutaAccetazione: Ricevuta;
        destinatari: Destinatari[];
    }

export type AllegatoRicevuta =
    {
        id: string;
        dimensione: number;
        idCmis: string;
        nomeOriginale: string;
        nrProtocollo: string;
    }
export class FromTo
{
    from: string;
    to: string;
    constructor(from: string, to: string)
    {
        this.from = from;
        this.to = to;
    }
}

export type Allegato =
    {
        id: string;
        dimensione: number;
        idCmis: string;
        nomeOriginale: string;
        nrProtocollo: string;
    }

export type Ricevuta =
    {
        data: string;
        tipoRicevuta: string;
        errore: string;
        descrizioneErrore: string;
        idCmsEml: string;
        idCmsDatiCert: string;
        idCmsSmime: string;
    }

export type Destinatari =
    {
        tipoDestinatario: string;
        denominazione: string;
        nominativo: string;
        idDestinatario: string;
        indirizzo: string;
        ricevute: Ricevuta[];
    }

export interface NuovaTabellaComunicazione
{
    idComunicazione: string
    idEmlCmis: string
    data: Date
    nrProtocollo: string
    oggetto: string
    testo: string
    annoComunicazione: string
    denominazioneTipo: string
    nome: string
    mittente: Utente
    destinatari: Utente[]
    codiceComunicazione: string
}

export interface Utente
{
    loginame: string
    mail: string
    pec: string
    tipoEnte: string
    codEnte: string
    denEnte: string
    id: string
    statoInvio?: number
}

export interface UtenteIn
{
    email?: string
    tipoUtente?: string
    codiceEnte?: string
}

export interface Colonna
{
    field: string
    header: string
}

export class BaseSearch
{
    //paginazione
    limit: number;
    page: number;
    //ordinamento
    colonna: string;
    direzione: string;
}

export class CorrispondenzaSearch extends OrderBy
{
    idPratica: string;
    comunicazione: boolean;
    mittente: string;
    oggetto: string;
    destinatario: string;
    dataInvioDa:Date | string;
    dataInvioA: Date | string;
}

export interface TabellaComunicazione
{
    idComunicazione: string
    idEmlCmis: string
    data: Date
    nrProtocollo: string
    oggetto: string
    testo: string
    annoComunicazione: string
    denominazioneTipo: string
    nome: string
    mittente: Utente
    destinatario: Utente
    codiceComunicazione: string

}

export interface RicercaComunicazione
{
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

export class ElencoComunicazione
{
    elencoFromRegione;
    elencoToRegione;
    constructor()
    {
        this.elencoFromRegione = [new FromTo("Regione", "Province"),
        new FromTo("Regione", "ARO"),
        new FromTo("Regione", "Comuni"),
        new FromTo("Regione", "Impianti"),
        new FromTo("Regione", "Gestori"),
        new FromTo("Regione", "ARPA")]
    }
}

export interface DettaglioComunicazione
{
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
    statoInvio: number;
    errore: string;
    descrizioneErrore: string;
}

/*export class CorrispondenzaDTO
{
    id: number;
    idEmlCmis?: string;
    messageId?: string;
    mittenteEmail?: string;
    mittenteDenominazione?: string;
    mittenteUsername?: string;
    mittenteEnte?: string;
    dataInvio?: Date;
    oggetto: string;
    text: string;
    stato?: boolean;
    comunicazione: boolean;
    bozza: boolean;

}*/

export class CorrispondenzaDTO_OLD
{
    id: number;
    idFascicoli: number[];
    destinatari: DestinatarioComunicazioneDTO[];
    oggetto: string;
    mittente: MittenteDTO;
    dataInvio: Date;
    testo: string;
    bozza: boolean;
    denominazione: string;

    mittenteEmail: string;
    mittenteDenominazione: string;
    mittenteEnte: string;
}

export class MittenteDTO
{
    mittenteEmail: string;
    mittenteDenominazione: string;
    mittenteEnte: string;
}

export class DestinatarioComunicazioneDTO 
{
    id?: number;
    email: string;
    pec?: boolean;
    nome: string;
    tipo: "CC" | "TO";
    stato?: "INVIATA"|"ESITO_POSITIVO"|"ESITO_CON_ERRORE";
    ricevute?:Ricevuta[];
    mailDefault?:boolean;
}
//forse si puo' togliere...
export class DestinatarioDTO
{
	id?: number;
	type: "CC"|"TO";
	email: string;
	denominazione?: string;
	dataRicezione?: Date;
	stato?: "INVIATA"|"ESITO_POSITIVO"|"ESITO_CON_ERRORE";
	tipoComunicazione?: string;
}

export class DestinatarioIstituzionaleDto
{
    id: number;
    email: string;
    pec: string;
    nome: string;
    tipoEnte: TipoEnte;
}

export interface RubricaIstituzionaleSearch
{
    tipoEnte: TipoEnte;
    nome: string;
    page: number;
    limit: number;
    colonna: string;
    direzione: string;
}

export class SezioneCatastaleSearch extends BaseSearch{
    nome: string
}


export interface TipologicaDTO
{
    codice: string;
    label: string;
}

export interface DestinatarioSearch
{
    nome: string;
    email: string;
    page: number;
    limit: number;
}

export interface Container
{
    enti : Array<PlainTypeStringId>;
    users: Array<PlainTypeStringId>;
}

export class CommissioneLocaleOrganizzazione
{
    public id: number;
    public denominazione: string;
    public dataCreazione: Date;
    public dataTermine: Date;
    public email?: string;
    public organizzazioniAssociate: Array<number>;
    public entiLabelValue: Array<PlainTypeStringId>;
    public users: Array<PlainTypeStringId>;
}

export class EntiCommissioni
{
    nomeEnte: string;
    nomeCommissione: string;
    dataInizioVal: Date;
    dataFineVal: Date;
}

export class CLSearch
{
   public denominazione: string;
   public username: string;
   public dataValiditaDa: Date;
   public dataValiditaA: Date;
   public colonna: string;
   public direzione: "ASC"|"DESC";
   public pagina: number;
   public limite: number;

   constructor()
   {
       this.colonna     = "data_creazione";
       this.direzione   = "DESC";
       this.pagina      = 1;
       this.limite      = 5;
   }
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


export interface TipoProcedimento
{
	id: string;
    codice:string;
    descrizione: string;
    sanatoria?:boolean;
    abilitatoPresentazione?:boolean;
}

export interface DichiarazioneDTO
{
	id:string;
    testo:string;
    labelCb:string;
    idProcedimento:string;
}


export interface DisclaimerDTO{
    id:string;
	testo:string;
	tipoProcedimento:number;
	tipoReferente:string;
	ordine:number;
	required:boolean;
}


export interface TariffaDTO{
    id?:string;
    idOrganizzazione?:string;
    idTipoProcedimento?:string;
    sogliaMinima?:number
    sogliaMassima?:number;
    deleteEccedente?:boolean;
    percentuale?:number;
    cifraDaAggiungere?:number;
    percentualeFinale?:number;
    startDate?:Date;
    endDate?:Date;
    createDate?:Date;
    createUser?:string;
}
