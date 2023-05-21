export type LoggedUser = 
{
    /* idEnte: number;
    nomeEnte: string, */

    username: string;
    /* authorities: Array<any>, */
    /* parchi: Array<any>, */
    /* parcoSelected: any, */
    nome: string;
    cognome: string;
    cf: string;
    roles: string[];
    firstName: string;
    lastName: string;

    role: number; //mock
    gruppiRuoli: PMGroup[];

    gruppiRup?: string[];
}

export type UserDTO =
{
    username: string,
    nome: string,
    cognome: string,
    rup: boolean
}

export type PMGroup =
{
    id: string,
    nomeGruppo: string,
    codiceGruppo: string,
    descrizioneGruppo: string,
    idApplicazione: string,
    idGruppoPadre: string,
    idTipo: string,
    nomePadre: string,
    nomeApplicazione: string,
    codiceApplicazione: string,
    nomeRuolo: string,
    nomeTipo: string,
    ruoli: PMRole[],
    idUtentiResponsabili: string[],
    numeroRuoliAssociati: number,
    numeroResponsabili: number,
    additionalProperties: any | null
}

export type PMRole =
{
    id: string,
    nomeRuolo: string,
    descrizioneRuolo: string,
    ruoloPadre: string,
    applicazione: string,
    codiceRuolo: string,
    nomePadre: string,
    nomeApplicazione: string,
    numeroGruppi: number,
    numeroPermessi: number,
    gruppiEntity: any[] | null,
    additionalProperties: any | null
}