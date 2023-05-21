import { LocalizzazioneIntervento } from './../fascicolo.models';
import { AllegatoCustomDTO } from './allegato.models';
export enum TipoLocalizzazione
{
    R = "R", //regione
    P = "P", //provincia
    CO = "CO", //comune
    PTC = "PTC" //Particella
}

export type TipologicaDTO =
{
    codice: string,
    label: string
}

/**vecchio modello */
export class LocalizzazioneTabDTO
{
    //unused
    particelle: Particella[];
    parchi: TipologicaDTO[];
    paesaggiRurali: TipologicaDTO[];
    immobiliAreeInteresse: TipologicaDTO[];

    //new
    geoAllegati: AllegatoCustomDTO[];
    localizzazioneComuni: LocalizzazioneIntervento[];
}

/**vecchio modello */
export class Particella
{
    id: string;
    ente: string;
    foglio: string;
    sezione: string;
    particella: string;
    sub: string;
    codiceEnte: string;
    tipoLocalizzazione: TipoLocalizzazione;
}

export interface InfoAndGeoJson{
    fc:any; //feature collection
    idAllegato:number;
	codeValidazione:number; //0==OK -1=errore validazione
	messaggioValidazione:string;
	allegatoDto?:AllegatoCustomDTO;    
}
