//import { SpawnSyncOptionsWithStringEncoding } from 'child_process';
import { BasicFile } from 'src/app/shared/models/allegati.model';
import { Allegato } from 'src/app/shared/models/models';

export type TipoEnte = "AC" | "CN" | "CO" | "E" | "ED" | "P" | "R" | "SI" | "SS" | "SZ" | "UC" | "UR";

/*export type DettaglioCom = 
{
    codiceComunicazione: string;
    data:string;
    numeroProtocollo: string;
    mittente:string;
    mailMittente:string;
    oggetto:string;
    tipologia:string;
    pec:boolean;
    testo: string;
    //allegati:Allegato[];
    allegati: Allegato[];
    //idCms:string;
    ricevutaAccetazione: Ricevuta;
    destinatari: Destinatari[];
}*/

export type AllegatoRicevuta = {
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

/*export type Allegato=
{
    id:string;
    dimensione:number;
    idCmis:string;
    nomeOriginale:string;
    nrProtocollo:string;
}*/

export class TemplateComunicazione {
	public label: string;
	public value: any;
	public descrizione: string;
	public template: Template;
}

export class Template 
{
	public destinatari: DestinatarioComunicazioneDTO[];
	public oggetto: string;
	public testo: string;
	public riservata: boolean;
	public protocollazione:string;
}

export type Ricevuta=
{
    data:string;
    tipoRicevuta: string;
    errore:string;
    descrizioneErrore:string;
    idCmsEml:string;
    idCmsDatiCert:string;
    idCmsSmime:string;
}



export type Destinatari=
{
    tipoDestinatario:string;
    denominazione:string;
    nominativo:string;
    idDestinatario:string;
    indirizzo:string;
    ricevute:Ricevuta[];
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

export abstract class Paging
{
    page: number;
    limit: number;

    constructor(page?: number, limit?: number)
    {
        this.page = page ? page : 0;
        this.limit = limit ? limit : 0;
    }
}

export abstract class  OrderBy extends Paging
{
    sortBy: string;
    sortType: "ASC"|"DESC"| null;

    constructor(column?: string, direction?: "ASC"|"DESC", page?: number, limit?: number)
    {
        super(page, limit);
        this.sortBy = column ? column : null;
        this.sortType = direction ? direction : null;
    }
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

/*export class ElencoComunicazione
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
}*/

/*export interface DettaglioComunicazione
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
}*/

export class CorrispondenzaDTO
{
    id: number;
    idEmlCmis: string;
    messageId: string;
    mittenteEmail: string;
    mittenteDenominazione: string;
    mittenteUsername: string;
    mittenteEnte: string;
    dataInvio: Date;
    oggetto: string;
    testo: string;
    stato: boolean;
    comunicazione: boolean;
    bozza: boolean;
    riservata?: boolean;
    codiceTemplate?: string;
	comunicazioneInterna: boolean;
	idOrganizzazioneOwner?: number;
    tipoOrganizzazioneOwner?: string;
	visibilita?: string;
	protocollo?: string;
	dataProtocollo?: Date|string;
}

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

/* export class DestinatarioIstituzionaleDto extends DestinatarioComunicazioneDTO
{
	pec: string;
} */

export class DestinatarioDTO
{
    id: number;
    type: "CC" | "TO";
    email: string;
    denominazione: string;
    dataRicezione: Date;
    stato: "INVIATA" | "ESITO_POSITIVO" | "ESITO_CON_ERRORE";
}


export class DettaglioCorrispondenzaDTO
{
    corrispondenza: CorrispondenzaDTO;
    //destinatari: DestinatarioDTO[];
    destinatari: DestinatarioComunicazioneDTO[];
    allegati: TipologicaDTO[]; //in cui codice è id e label il nome del file
    tipologia?: string;
    allegatiInfo: Allegato[];
    pec?: boolean = true;
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

//pari al BE
export interface TemplateEmailDTO{
    idOrganizzazione:number; //chiave primaria
    codice: string; //chiave primaria
  	oggetto: string;
	  testo: string;
	  descrizione: string;
    nome: string;
    placeholders?:string ; //lista di placeholders separati da ,
    readonlyOggetto?:string;
    readonlyTesto?:string;
    visibilita?:string;
    sezione?:string;
    tipiProcedimentoAmmessi?:string;
    protocollazione?:string;
    cancellabile?:boolean;
    riservata?:boolean;
}
export interface TemplateDestinatarioDTO
{
  id: number;
  idOrganizzazione:number;
	codiceTemplate:string;
	email: string;
	pec: string;
	denominazione: string;
	tipo: 'TO'|'CC';
}
export class PlainTypeStringId
{
  value?: string;
  description?: string;
}

export interface TemplateEmailDestinatariDto
{
    template:TemplateEmailDTO;
    destinatari?:TemplateDestinatarioDTO[];
    placeholders?:PlainTypeStringId[];
}

export class RespDettaglio{
	list: DettaglioCorrispondenzaDTO[];
	count: number;
}

export class AllegatoCorrispondenza extends BasicFile
{

}