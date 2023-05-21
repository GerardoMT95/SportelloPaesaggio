export type LoggedUser = {
  //idEnte: number;
  //nomeEnte: string,
  username: string;
  //authorities: Array<any>,
  //parchi: Array<any>,
  //parcoSelected:any,
  //nome: string;
  //cognome: string;
  //cf: string;
  roles: string[];
  firstName: string;
  lastName: string;
  emailAddress?: string;
  mobile?: string;
  phone?: string;
  fiscalCode: string;
  otherFields?:any; // mappa con chiave GruppoRuoli
  //gruppiRuoli: GruppiRuoli[] // non piu' utilizzato
  
    /*
    dataNascita: Date;
    birthPlace: string;
    comuneResidenza: string;
    emailAddress: string;
    
    fiscalCode: string;
    
    mobile: string;
    phone: string;
    provinciaResidenza: string;
    residenza: string;
    sessionId: string;
    username: string */
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
      additionalProperties: any|null
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
      gruppiEntity: any[]|null,
      additionalProperties: any| null
  }