import { BasicFile } from 'src/app/shared/models/allegati.model';
import { Allegato, TemplateDestinatarioDTO } from 'src/app/shared/models/models';
import { DestinatarioComunicazioneDTO } from './../../funzionalita-amministratore-applicazione/models/admin-functions.models';
import { TipologicaDTO } from './tipologica';

export class CorrispondenzaDTO 
{
	id: number;
	idFascicolo: string;
	destinatari: TipologicaDTO[];
	oggetto: string;
	mittente: MittenteDTO;
	dataInvio: Date;
	testo: string;
	bozza: boolean;
	denominazione: string;
	allegati: AllegatoCorrispondenza[];
	mittenteEmail: string;
	mittenteDenominazione: string;
	mittenteEnte: string;
	riservata: boolean;
	codiceTemplate?: string;
	comunicazioneInterna: boolean;
	idOrganizzazioneOwner?: number;
    tipoOrganizzazioneOwner?: string;
	visibilita?: string;
	protocollo?: string;
	dataProtocollo?: Date|string;
}

export class MittenteDTO
{
	mittenteEmail: string;
	mittenteDenominazione: string;
	mittenteEnte: string;
}

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

export class RespDettaglio{
	list: DettaglioCorrispondenzaDTO[];
	count: number;
}
export class DettaglioCorrispondenzaDTO
{
	corrispondenza: CorrispondenzaDTO;
	destinatari: DestinatarioComunicazioneDTO[];
	allegati: AllegatoCorrispondenza[];
	allegatiInfo: Array<Allegato>;
	pec?: boolean = true;
}

export class DettaglioComunicazioniMocked extends DettaglioCorrispondenzaDTO
{
	sezione: string = null;
}

export class AllegatoCorrispondenza extends BasicFile
{

}

export class CorrispondenzaParereDTO extends CorrispondenzaDTO
{
	idParere?: number;
}

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