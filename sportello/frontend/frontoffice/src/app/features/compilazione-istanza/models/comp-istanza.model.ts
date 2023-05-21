import { Allegato } from 'src/app/shared/models/models';


export interface LocalizzazioneValues
{
    bpParchiRiserve: boolean;
    bpImmobileAreeInteresse: boolean;
    comuneSelezionato: boolean;
}

export interface InfoAndGeoJson{
    fc:any; //feature collection
    idAllegato:number;
	codeValidazione:number; //0==OK -1=errore validazione
	messaggioValidazione:string;
	allegatoDto?:Allegato;    
}
