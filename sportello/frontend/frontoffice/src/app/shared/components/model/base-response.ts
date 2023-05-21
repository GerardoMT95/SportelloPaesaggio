
export type BaseResponse<T> = {
    descrizioneEsito:string;
    codiceEsito:string;
    numeroTotaleOggetti:number;
    numeroOggettiRestituiti:number;
    payload:T;
}
