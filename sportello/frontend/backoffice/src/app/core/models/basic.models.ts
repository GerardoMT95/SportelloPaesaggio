export class BaseResponse<T>
{
    descrizioneEsito: string;
    codiceEsito: string;
    numeroTotaleOggetti: number;
    numeroOggettiRestituiti: number;
    payload: T;

    constructor(descrizioneEsito?: string, codiceEsito?: string, numeroTotaleOggetti?: number, numeroOggettiRestituiti?: number, payload?: T)
    {
        this.descrizioneEsito = descrizioneEsito ? descrizioneEsito : null;
        this.codiceEsito = codiceEsito ? codiceEsito : null;
        this.numeroOggettiRestituiti = numeroOggettiRestituiti && payload ? numeroOggettiRestituiti : 0;
        this.numeroTotaleOggetti = numeroTotaleOggetti ? numeroTotaleOggetti : 0;
        this.payload = payload ? payload : null;
    }
}

export class PaginatedList<T>
{
    list: T[];
    count: number;

    constructor(list?: T[], count?: number)
    {
        this.list = list ? list : [];
        this.count = count ? count : 0;
    }
}