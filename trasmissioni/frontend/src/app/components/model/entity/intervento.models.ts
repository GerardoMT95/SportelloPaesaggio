export class TipiEQualificazioniDTO
{
    id: number;
    stile: string;
    zona: number;
    label: string;
    ordine: number;
    raggruppamento: string;
    dependesOn?: number;
}

export class InterventoTabDTO
{
    lista: TipiEQualificazioniDTO[];
}