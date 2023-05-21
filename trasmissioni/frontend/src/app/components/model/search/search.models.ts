import { BaseSearch } from "../entity/fascicolo.models";

export class UlterioreDocumentazioneSearch extends BaseSearch {
  idFascicolo: number;
  titolo: string;
  descrizione: string;
  dataCondivisioneDa: Date;
  dataCondivisioneA: Date;
  visibileA: string[];
  destinatario: string;
  inseritoDa: string;
}

export class CorrispondenzaSearch extends BaseSearch {
  idFascicolo: string;
  comunicazione: boolean;
  mittente: string;
  oggetto: string;
  destinatario: string;
  dataInvioDa: Date;
  dataInvioA: Date;
}

export class RichiestePostTrasmissioneSearch extends BaseSearch {
  id: string;
  idFascicolo: string;
  motivazione: string;
  stato: string;
  tipo: string;
  dateCreated: Date;
  userCreated: string;
  dateUpdated: Date;
  userUpdated: string;
  idCorrispondenza: string;
  // page: number;
  // limit: number;
}

export class AnnotazioniInterneSearch {
  id: string;
  idFascicolo: string;
  idOrganizzazione: string;
  esitoControllo: string;
  note: string;
  dateCreated: Date;
  userCreated: string;
  dateUpdated: Date;
  userUpdated: string;
}

export class VPaesaggioProvvedimentiSearch extends BaseSearch {
  codiceTrasmissione: string;
  idFascicoloPaesaggio: string;
}