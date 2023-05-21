import { AllegatoCustomDTO } from './allegato.models';
import { TipologicaDTO } from './localizzazione.models';

export type TipoEnte = "AC"|"CN"|"CO"|"E"|"ED"|"P"|"R"|"SI"|"SS"|"SZ"|"UC"|"UR";

export class CorrispondenzaDTO 
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
	type: "CC"|"TO";
	email: string;
	denominazione: string;
	dataRicezione: Date;
	stato: "INVIATA"|"ACCETTATA"|"ERRORE";
}


export class DettaglioCorrispondenzaDTO
{
	corrispondenza: CorrispondenzaDTO;
	//destinatari: DestinatarioDTO[];
	destinatari: DestinatarioComunicazioneDTO[];
	//allegati: TipologicaDTO[]; //in cui codice è id e label il nome del file
	tipologia?: string;
	pec?: boolean = true;
	allegatiInfo:AllegatoCustomDTO[];
}

export interface DestinatarioSearch
{
	nome: string;
	email: string;
	page: number;
	limit: number;
}

/*----- MODEL RICHIESTE POST TRASMISSIONE ----- */
export class RichiestePostTrasmissioneDTO
{
	id?:number;
	idFascicolo?:number;
	motivazione:string;
	stato:string;
	tipo:string; //Modifica Cancellazione
}

export class RichiestePostTrasmissioneDetailDTO
{
	richiestaBase: RichiestePostTrasmissioneDTO;
	allegati:AllegatoCustomDTO[];
}

/*----- MODEL ANNOTAZIONI INTERNE -----*/
export class AnnotazioniInterneDTO {
	id: string;
	idFascicolo: string;
	idOrganizzazione: string;
	esitoControllo: string;
	note: string;
	// dateCreated: Date;
	// userCreated: string;
	// dateUpdated: Date;
	// userUpdated: string;
}

export class AnnotazioniInterneDetailDTO
{
	annotazioneBase: AnnotazioniInterneDTO;
	allegati:AllegatoCustomDTO[];
}