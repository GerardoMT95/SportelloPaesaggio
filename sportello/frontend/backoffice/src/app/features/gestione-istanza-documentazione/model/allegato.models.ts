/*export enum TipoAllegato
{
	PROVVEDIMENTO_FINALE = "PROVVEDIMENTO_FINALE",
	PARERE_MIBAC = "PARERE_MIBAC",
	ISTANZA = "ISTANZA",
	VERBALE = "VERBALE",
	PARERE = "PARERE",
	PREAVVISO = "PREAVVISO",
	ALTRI_PARERI = "ALTRI_PARERI",
	ELABORATI = "ELABORATI",
	PARERI_SCHEDA = "PARERI_SCHEDA",
	SCHEDA_PROGETTO = "SCHEDA_PROGETTO"
}
*/ //non utilizzato

export class AllegatiDTO  //stessa su BE
{
	id: string; //e' di tipo uuid
	nome: string;  //nomeFile su BE
	formatoFile: string;
	priticaId: string;
	descrizione: string;
	idIntegrazione: number;
	deleted: boolean;
	size: number;
	type: string;
	tipiContenuto?: string[];
	data: Date;
	path: string
}

export class AllegatoCustomDTO {
	id: number;
	nome: string;
	tipo: string;
	idCms: string;
	dataCaricamento: Date;
	obbligatorio: boolean;
	dimensione: number;
	multiplo: boolean;
	mimeType: string;
	descrizione?: string;
	titolo?: string;
	utenteInserimento: string;
}

export class AllegatoUlterioreDocumentazione extends AllegatoCustomDTO {
	idFascicolo: number;
	allegato?: File;
	notifica: string[];
	enti: string[];
	direzione: string;
}