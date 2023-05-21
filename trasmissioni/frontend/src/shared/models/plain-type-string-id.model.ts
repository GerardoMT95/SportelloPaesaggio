export class PlainTypeStringId {
    id?: string;
    nome?: string;
}

export class GruppiRuoliDTO {
    gruppo: string;
    ruoli: Array<PlainTypeStringId>;
}