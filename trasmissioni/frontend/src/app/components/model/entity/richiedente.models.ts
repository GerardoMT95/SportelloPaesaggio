import { AllegatoCustomDTO } from "./allegato.models";

export class RichiedenteDTO
{
    id: number;
    nome: string;
    cognome: string;
    codiceFiscale: string;
    sesso: String;
    dataNascita: Date;
    statoNascita: string;
    provinciaNascita: string;
    comuneNascita: string;
    statoResidenza: string;
    provinciaResidenza: string;
    comuneResidenza: string;
    viaResidenza: string;
    numeroResidenza: string;
    cap: string;
    pec: string;
    email: string;
    telefono: string;
    dittaSocieta: string;
    /* dittaAltro: string; */
    dittaCodiceFiscale: string;
    dittaPartitaIva: string;
    dittaInQualitaDi: string;
    responsabile?: Responsabile;
}


export class Responsabile{
    cognome: string;
    nome: string;
    inQualitaDi: string;
    servizioSettoreUfficio:string;
    pec: string;
    mail: string;
    telefono: string;
    riconoscimentoTipo: string;
    riconoscimentoNumero: string;
    riconoscimentoDataRilascio: Date;
    riconoscimentoRilasciatoDa: string;
    riconoscimentoDataScadenza: Date;
    documentoRiconoscimento?:AllegatoCustomDTO;
}