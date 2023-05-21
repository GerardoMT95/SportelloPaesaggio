import { Destinatari } from "./destinatari.model";
import { PlainTypeNumericId } from "./plain-type-numeric-id.model";

export class Correspondence {
    mittente?: string;
    soggetto?: string;
    dataGenerata?: Date;
    pianoId?: number;
    testo?: string;
    emailAllegatiIds?: PlainTypeNumericId[];
    destinatari?: Destinatari;
}