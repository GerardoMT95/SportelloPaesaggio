/* export type LoggedUser = {
    birthDate: Date;
    birthPlace: string;
    comuneResidenza: string;
    emailAddress: string;
    firstName: string;
    fiscalCode: string;
    lastName: string;
    mobile: string;
    phone: string;
    provinciaResidenza: string;
    residenza: string;
    roles: string[];
    sessionId: string;
    username: string
  } */

export type LoggedUser = {
  idEnte: number;
  nomeEnte: string,
  username: string;
  authorities: Array<any>,
  parchi: Array<any>,
  parcoSelected: any,
  nome: string;
  cognome: string;
  cf: string;
  roles: string[];
  firstName: string;
  lastName: string;
  emailAddress?: string;
  mobile?: string;
  phone?: string;
  pec?: string;
  gruppiRuoli: GruppiRuoli[]
}

export type GruppiRuoli =
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
  ruoli: PMRoles[],
  idUtentiResponsabili: string[],
  numeroRuoliAssociati: number,
  numeroResponsabili: number,
  additionalProperties: any | null
}

export type PMRoles =
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

export class PlainTypeStringId
{
  value?: any;
  description?: string;
}

export class PlainFunzRup extends PlainTypeStringId
{
  rup?: boolean;
}

export class GruppiRuoliDTO
{
  gruppo: string;
  ruoli: Array<PlainTypeStringId>;
}

//Bean response
export interface ResponseBean<T>
{
  code: string;
  message: string;
  partialSize: number;
  payload: T;
  size: number;
}
export class UtenteAttributeBean
{
  username: string;
  nome: string;
  cognome: string;
  mail: string;
  pec: string;
	phoneNumber: string;
}