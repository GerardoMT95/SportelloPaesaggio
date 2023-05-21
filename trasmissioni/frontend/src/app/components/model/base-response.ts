
export type BaseResponse<T> = {
    descrizioneEsito:string;
    codiceEsito:string;
    numeroTotaleOggetti:number;
    numeroOggettiRestituiti:number;
    payload:T;
}

export type BaseRestResponse<T> = {
    codiceEsito: string;    
    numeroTotaleOggetti: number;
    numeroOggettiRestituiti: number;
    descrizioneEsito: string;
    payload : T;
}

