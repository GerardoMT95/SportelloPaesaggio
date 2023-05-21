import { DestinatarioComunicazioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
export enum TipoAllegato
{
	PROVVEDIMENTO_FINALE = "PROVVEDIMENTO_FINALE",
	PARERE_MIBAC = "PARERE_MIBAC",
	PROVVEDIMENTO_FINALE_PRIVATO = "PROVVEDIMENTO_FINALE_PRIVATO",
	PARERE_MIBAC_PRIVATO = "PARERE_MIBAC_PRIVATO",
	ISTANZA = "ISTANZA",
	VERBALE = "VERBALE",
	PARERE = "PARERE",
	PREAVVISO = "PREAVVISO",
	ALTRI_PARERI = "ALTRI_PARERI",
	ELABORATI = "ELABORATI",
	PARERI_SCHEDA = "PARERI_SCHEDA",
	SCHEDA_PROGETTO = "SCHEDA_PROGETTO",
	DOCUMENTO_RICONOSCIMENTO = "DOCUMENTO_RICONOSCIMENTO",
	INTEGRAZIONI ="INTEGRAZIONI",
	//putt aggiunte...
	ATTESTAZIONE_CONFORMITA ="ATTESTAZIONE_CONFORMITA",
	RICHIESTA_SOPRINTENDENZA="RICHIESTA_SOPRINTENDENZA",
	DOCUMENTAZIONE_FOTOGRAFICA="DOCUMENTAZIONE_FOTOGRAFICA",
	RETTIFICA_AUTORIZZAZIONE="RETTIFICA_AUTORIZZAZIONE",
	PROPOSTA_PROVVEDIMENTO="PROPOSTA_PROVVEDIMENTO",
	ALTRO="ALTRO",
	COMUNICAZIONE_SOPRINTENDENZA_ESITO_NON_COMPLETATO="COMUNICAZIONE_SOPRINTENDENZA_ESITO_NON_COMPLETATO"
}

export class AllegatoDTO 
{
    id: number;
	nome: string;
	descrizione: string;
	mimeType: string;
	dataCaricamento: Date;
	contenuto: string;
	checksum: string;
	deleted: boolean;
	dimensione: number;
	inputProtocolNumber: string;
	inputProtocolDate: Date;
	outputProtocolNumber: string;
	outputProtocolDate: Date;
}

export class AllegatoCustomDTO
{
	id: number;
	nome: string;
	tipo: string;
	tipi?: string[];
	idCms: string;
	dataCaricamento: Date;
	obbligatorio: boolean;
	dimensione: number;
	multiplo: boolean;
	mimeType: string;
	descrizione?: string;
	titolo?: string;
	utenteInserimento: string;
	checksum?: string;
	isUrl?: boolean;
}

export class AllegatoUlterioreDocumentazione extends AllegatoCustomDTO
{
	idFascicolo: number;
	allegato?: File;
	notifica: DestinatarioComunicazioneDTO[];
	enti: string[];
	direzione: boolean;
}

export interface UlterioreDocumentazione 
{
	idFascicolo: number;
	allegati: AllegatoCustomDTO[];
	notifica: DestinatarioComunicazioneDTO[];
	enti: string[];
	direzione: boolean;
}

export interface AllegatoUD
{
	titolo: string;
	descrizione: string;
	allegato: File;
}

/**
 * allegato utilizzato per lo shape file per il nuovo pannello localizzazione
 */
export class Allegato {
	id?: string | number;
	descrizione?: string;
	nome?: string;
	data?: Date | string;
	path?: string;
	status?: string; 
	praticaId?: string;
	size?: number;
	tipiContenuto?:string[];//opzionale, presente solo per gli allegati con tipologia multipla
  }