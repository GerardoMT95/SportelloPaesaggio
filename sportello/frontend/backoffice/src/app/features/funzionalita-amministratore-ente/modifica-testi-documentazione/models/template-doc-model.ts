import { BaseSearch } from "src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models";
/* GM gestione template documentazione*/

import { AllegatiDTO } from "src/app/features/gestione-istanza-documentazione/model/allegato.models";
import { PlainTypeStringId } from "src/app/shared/components/model/logged-user";


export interface TemplateDocDTO{
    idOrganizzazione:number;
    nome: string;
    descrizione:string;
    sezioni:TemplateDocSezioneDTO[];
}

export interface TemplateDocSezioneDTO{
    idOrganizzazione:number;
    nome: string;
    tipoSezione: TipoSezione;
    valore: string;
    idAllegato: string;
    placeholders?: string[];
    allegato?: AllegatiDTO;
    placeholdersInfo?: PlainTypeStringId[];
    
}

export enum TipoSezione{
    HTML = "HTML",
    TEXT = "TEXT",
    IMAGE = "IMAGE"
}

export class TemplateDocSearch extends BaseSearch{
    nome: string
}
