import { AllegatoCustomDTO } from './allegato.models';
import { DestinatarioComunicazioneDTO } from './corrispondenza.models';
import { BaseSearch } from './fascicolo.models';
//Row tabella template comunicazioni
export interface TemplateComunicazioni
{
    codice: TipoTemplate;
	oggetto: string;
	testo: string;
	descrizione: string;
    nome: string;
    placeholders?: PlaceholderDoc[];
}

/* GM */

//Dettaglio template comunicazioni
export interface DettaglioTemplate extends TemplateComunicazioni
{
    destinatari: TemplateDestinatarioDTO[];
    destinatariAutomatici?: string[];
}

/* export interface TemplateDestinatarioDTO
{
    id: number;
	codiceTemplate: TipoTemplate;
	email: string;
	pec: string;
    denominazione: string;
	tipo: 'TO'|'CC';
} */

export interface TemplateDestinatarioDTO extends DestinatarioComunicazioneDTO
{
    codiceTemplate: TipoTemplate;
}

//Bean for tabella enti
export interface Ente
{
    id: number;
    tipologia: string;
    denominazione: string;
    mail: string;
    pec: string;
}

export enum TipoTemplate
{
    INVIO_TRASMISSIONE="INVIO_TRASMISSIONE",
    INVIO_COMUNICAZIONI="INVIO_COMUNICAZIONI",
    INVIO_ULTERIORE_DOCUMENTAZIONE="INVIO_ULTERIORE_DOCUMENTAZIONE",
    ESITO_CAMPIONAMENTO="ESITO_CAMPIONAMENTO",
    ESITO_VERIFICA="ESITO_VERIFICA",
    MODIFICA_POST_TRASMISSIONE="MODIFICA_POST_TRASMISSIONE",
    ELIMINAZIONE_POST_TRASMISSIONE="ELIMINAZIONE_POST_TRASMISSIONE",
    NOTIFICA_PRE_CAMPIONAMENTO="NOTIFICA_PRE_CAMPIONAMENTO",
    NOTIFICA_PRE_VERIFICA="NOTIFICA_PRE_VERIFICA"
}

/*
    INVIO_COMUNICAZIONI				 (getDestinatariInvioComunicazioni()			, PlaceHolder.getSetComunicazioni()),
	INVIO_TRASMISSIONE				 (getDestinatariInvioTrasmissione()				, PlaceHolder.getSetTrasmissione()),
	INVIO_RITRASMISSIONE			 (getDestinatariInvioTrasmissione()				, PlaceHolder.getSetTrasmissione()),
	INVIO_ULTERIORE_DOCUMENTAZIONE	 (getDestinatariInvioUlterioreDocumentazione()  , PlaceHolder.getSetUlterioreDocumentazione()),
	ESITO_CAMPIONAMENTO				 (getDestinatariEsitoCampionamento()			, PlaceHolder.getSetEsitoCampionamento()),
	ESITO_VERIFICA					 (getDestinatariEsitoVerifica()					, PlaceHolder.getSetEsitoVerifica()),
	MODIFICA_POST_TRASMISSIONE		 (getDestinatariModificaPostTrasmissione()		, PlaceHolder.getSetModificaPostTrasmissione()),
	REVOCA_MODIFICA_POST_TRASMISSIONE(getDestinatariRevocaModificaPostTrasmissione(), PlaceHolder.getSetRevocaModificaPostTrasmissione()),
	ELIMINAZIONE_POST_TRASMISSIONE	 (getDestinatariEliminazionePostTrasmissione()  , PlaceHolder.getSetEliminazionePostTrasmissione()),
	NOTIFICA_PRE_CAMPIONAMENTO		 (getDestinatariNotificaPreCampionamento()		, PlaceHolder.getSetNotificaPreCampionamento()),
	NOTIFICA_PRE_VERIFICA			 (getDestinatariNotificaPreVerifica()			, PlaceHolder.getSetNotificaPreVerifica()),
	ASSEGNAMENTO_FASCICOLO			 (getDestinatariAssegnamentoFascicolo()			, PlaceHolder.getSetAssegnamentoFascicolo()),
	DISASSEGNAMENTO_FASCICOLO
 */

export class EnteSearch extends BaseSearch
{
    public tipologia: string;
    public denominazione: string;
}


/* GM gestione template documentazione*/

export interface PlaceholderDoc{
    codice: string;
    info: string;
}

export interface TemplateDocDTO{
    nome: string;
    descrizione:string;
    sezioni:TemplateDocSezioneDTO[];
}

export interface TemplateDocSezioneDTO{
    nome: string;
    tipoSezione: TipoSezione;
    valore: string;
    placeholders?: PlaceholderDoc[];
    allegato?: AllegatoCustomDTO;
}

export enum TipoSezione{
    HTML = "HTML",
    TEXT = "TEXT",
    IMAGE = "IMAGE"
}
