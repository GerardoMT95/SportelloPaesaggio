import { AllegatoCustomDTO } from 'src/app/features/gestione-istanza-documentazione/model/allegato.models';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { PlainTypeStringId } from '../components/model/logged-user';
import { SelectOption } from '../components/select-field/select-field.component';
import { DestinatarioDTO, DettaglioCorrispondenzaDTO } from './../../features/gestione-istanza-comunicazioni/model/corrispondenza.models';
import { BasicFile } from './allegati.model';
import { OrderBy } from './search.models';

export class Fascicolo
{
  id: string; 
  comuniCompetenza: ComuniCompetenza[]; //hidden non nei form. lista di ente_id dei comuni su cui l'ente territoriale ha competenza.
  codicePraticaAppptr?: string;
  enteDelegato?: any;
  oggetto?: string;
  tipoProcedimento?: TipoProcedimento;
  dataCreazione?: Date | string;
  attivitaDaEspletare?: StatoEnum;
  /* attivitaDaEspletare?: AttivitaDaEspletareEnum; */
  displayButton?: boolean;
  comune?: string;
  /** TODO: Treba dodati  */
  istanza?: Istanza;
  schedaTecnica?: SchedaTecnica;
  allegati?: Allegati;
  documentiSottoscritti?: Allegato[];
  relazioneEnte?: RelazioneEnte;
  
  /* comunicazioni: DettaglioComunicazioniMocked[]; */
  ulterioreDocumentazione?: UlterioreDocumentazione[];
  //trasmissioneProvvedimentoFinale: TrasmissioneProvedimentoFinale;
  dataStato?: StoricoStato[]; //solo fe
  rup: boolean;
  //assegnazione fascicolo
  /* rup: string; //corrisponderà all'username del rup assegnato
  funzionari: string[]; //corrisponderà alla lista di funzionari che possono visualizzare/modificare la pratica
  funzionariSop: string[]; */

  numeroProtocollo: string;
  dataProtocollo: Date;

  /* soprintendenza?: string;
  entiTerritoriali?: string[];
  commissioneLocale?: string; */

  dataPresentazione: Date; //da vedere se corrisponde a data creazione
  dataTrasmissioneVerbale: Date;
  dataInizioLavorazione: Date;
  dataTrasmissioneRelazione: Date;
  dataTrasmissioneParere: Date;
  dataTrasmissioneProvvedimentoFinale: Date;

  /* assegnazioni?: Assegnazione[];
  assegnazioniSop?: Assegnazione[];

  richieste?: RichiestaModificaStato[]; */

  tipoSelezioneLocalizzazione?: string;
  hasShape?: boolean;

  statoSedutaCommissione?: StatoSeduta;
  statoRelazioneEnte?: StatoRelazione;
  statoParereSoprintendenza?: StatoParere;

  storicoASR: Array<StoricoASR>;
  denominazioneRup?:string; 
	usernameRup?:string;
	denominazioneFunzionario?:string; 
	usernameFunzionario?:string;
  showVCL?: boolean;
  descrComuniIntervento?:string[]; //popolato solo in caso di search per popolare il datatable della dashboard (istanze-table.component)
  esoneroBollo?: boolean;
  esoneroOneri?: boolean;
  descrEnteDelegato?: string; //popolato solo nell'elenco istanze
  denominazioneAssegnatarioOrganizzazione?:string; 
	usernameAssegnatarioOrganizzazione?:string;
  ruoloPratica?: string;
  codiceTrasmissione?: string;
}

export type StatoSeduta    = "VERBALE_NON_PREVISTO"|"VERBALE_SEDUTA_ASSENTE"|"VERBALE_SEDUTA_ALLEGATO";
export type StatoParere    = "PARERE_NON_PREVISTO" | "PARERE_NON_ALLEGATO" | "PARERE_IN_BOZZA_ENTE" | "PARERE_IN_BOZZA_SOPRINTENDENZA" | "PARERE_INSERITO_SOPRINTENDENZA" | "PARERE_INSERITO_ENTE";
export type StatoRelazione = "RELAZIONE_NON_PREVISTA" | "RELAZIONE_NON_TRASMESSA" | "RELAZIONE_TRASMESSA_SENZA_AVVIO" | "RELAZIONE_TRASMESSA_CON_AVVIO";
export class ComuniCompetenza
{
  enteId?: number;
  descrizione?: string;
  codCat?: string;
  codIstat?: string;
  vincoloArt38?:string;
  vincoloArt100?:string;
}

export type TipoRichiesta = "ATTIVAZIONE" | "SOSPENSIONE" | "ARCHIVIAZIONE";

export class Assegnazione
{
  codiceFascicolo: string;
  dataAssegnazione: Date | string;
  dataFineAssegnazione?: Date | string | null;
  rup: User;
  funzionari: User[];
}

export class Assegnamento
{
  idFascicolo: string;
  usernameFunzionario: string;
  denominazioneFunzionario: string;
  usernameRup: string;
  denominazioneRup: string;
} 

export class StoricoAssegnazioni extends Assegnamento
{
  id: number;
  idOrganizzazione: number;
  tipoOperazione: string;
  dataOperazione: Date;
}

export type TipoAssegnamento = "MANUALE"|"LOCALIZZAZIONE"|"TIPO_PROCEDIMENTO";

export class ValoriAssegnamentoDTO
{
  idOrganizzazione: number;
  fase: string;
  idComuneTipoProcedimento: string;
  denominazioneComuneProcedimento: string; 
  tipoAssegnamento: TipoAssegnamento;
  usernameFunzionario: string;
  denominazioneFunzionario: string;
  usernameRup: string;
  denominazioneRup: string;
}

export class ConfigurazioneAssegnamento
{
  idOrganizzazione: number;
  fase: string;
  rup: boolean;
  criterioAssegnamento: TipoAssegnamento;
  valoriAssegnati: ValoriAssegnamentoDTO[];
}

export class TabellaAssegnamentoFascicolo extends Assegnamento
{
  id: string;
  stato: StatoEnum;
  codice: string;
  tipoProcedimento: TipoProcedimento;
  oggettoIntervento: string;

  comuni: string[];

  riassegnazioni: number;
  ultimaAssegnazione: Date;
  ultimaOperazione: Date;

  storico: StoricoAssegnazioni;
}

export class StoricoASR
{
  id: number;
	idPratica: string;
	codicePratica: string;
	type: TipoRichiesta;
	draft: boolean;
	note: string;
	data: Date;
	usernameUtenteCreazione: string;
  statoPratica: StatoEnum;
  allegati?: Array<Allegato>
}

export class SedutaDiCommissione
{
  //dto base
  public id: number;
  public idEnteDelegato: number;
  public nomeSeduta: string;
  public dataSeduta: Date;
  public usernameUtenteCreazione: string;
  public dataCreazione: Date;
  public pubblica: boolean;
  public stato: "IN_BOZZA"|"PUBBLICATA"|"CONCLUSA";

  //varie estensioni
  public nFascicoli?: number;
  public nFascicoliEsaminati?: number;
  public pratiche?: Array<string>
  public praticheDetails: Array<Fascicolo>
  public allegati?: Array<FileCommissioneLocale>
}

export class StoricoStato
{
  public stato: string;
  public data: string | Date;
}

export class Istanza
{
  richiedente?: Richiedente;
  dichiarazioni?: Dichiarazioni;
  altriTitolari?: AltroTitolare[];
  localizzazione?: localizzazione;
  /* Nije konzistentan naziv. Negde pise TecnicoRifirimento  */
  tecnicoIncaricato?: TecnicoIncaricato;
  privacyAccettata?: boolean;
  valida?: boolean;
  delegatoPratica: PraticaDelegato[];
}

export interface PraticaDelegato
{
  id?: string;
  indice?: number;
  cognome?: string;
  nome?: string;
  codiceFiscale?: string;
  sesso?: string;
  dataNascita?: Date;
  idNazioneNascita?: number;
  nazioneNascita?: string;
  idComuneNascita?: number;
  comuneNascita?: string;
  comuneNascitaEstero?: string;
  idNazioneResidenza?: number;
  nazioneResidenza?: string;
  idComuneResidenza?: number;
  comuneResidenza?: string;
  comuneResidenzaEstero?: string;
  indirizzoResidenza?: string;
  civicoResidenza?: string;
  capResidenza?: string;
  pec?: string;
  mail?: string;
  dateCreate?: Date;
  delgatoCorrente?: boolean;
  provinciaNascita?: string;
  idProvinciaNascita?: number;
  provinciaResidenza?: string;
  idProvinciaResidenza?: number;
  allegatoDelega?:Allegato;
  allegatoSollevamento?:Allegato;
}

export class InformazionePersonale
{
  id?: number; //chiave nel DB
  cognome: string;
  nome: string;
  codiceFiscale: string;
  sesso: string;

  natoIl: Date | string;
  natoStato: SelectOption;
  natoCitta: string; //caso estero
  natoComune: SelectOption;
  natoProvincia: SelectOption;

  documento: RiconoSciementoAllegato;
  /* tipo: any; */ /*  enum ili neki drugi tip  */
  /* numero: string;
  rilascitoIl: Date | string;
  rilascitoDa: string;
  dataScadenza: Date | string; */
  /* TODO: Check if there will be more than one document there is some unclear things in documentation.  */
  allegato?: Allegato;
}

export class Richiedente extends InformazionePersonale
{
  residenteIn: IndirizzoCompleto;
  recapitoTelefonico: string;
  indirizzoEmail: string;
  pec: string;

  /* TODO: Provetiti sa njima da li je ovo obavezno samo ako se izabere checkbox jer
    u tom slucaju i nije obezno  */
  nelCaso: boolean;
  inQualitaDi: string;
  societa: string;
  partitaIva: string;
  societaCodiceFiscale: string;
}

export class IndirizzoCompleto
{
  stato?: string;
  citta?: string;

  via?: string;
  n?: string;
  cap?: string;
}
// TODO: Ovde provertiti da li su sva polja identicna.
export class AltroTitolare extends Richiedente
{
  titolaritaInQualitaDi: any; // TODO: Nejasan je skup vrednosti
  descrizioneAltro: string;
}

export class RiconoSciementoAllegato
{
  tipo: string; /*  enum ili neki drugi tip  */
  numero: string;
  rilascitoIl: Date;
  rilascitoDa: Date;
  dataScadenza: Date;
  /* TODO: Check if there will be more than one document there is some unclear things in documentation.  */
  allegato: Allegato;
}

export class Dichiarazioni
{
  /*finiscono in tabella referente*/
  titolarita?: number;  //presa da ruolo_referente 4=proprietario esclusivo    
  diAvereTitoloRappSpec?: string;
  diAvereTitoloAltroSpec?: string;
  titolaritaEsclusivaIntervento: string; //'S'i 'N'o
  /*finiscono in altre_dichiarazioni_rich tabella*/
  inCasoDiVariante?: boolean; // su DB is_caso_di_variante
  numero?: string;
  da?: string;
  inData?: Date;
  //interventaNecesitaArt146: number[]; //contiene tra 136 142 134
  art136?: string[]; //A B C D 
  art142?: string[]; //questo dovrebbe essere popolato dalle selezioni in pptr e localizzazione
  art134?: string[];
  //tipiDichiarazione?: TipoDichiarazione[];
  /*finiscono in disclaimer_pratica */
  altreOpzioni?: number[]; //tabella disclaimer_pratica
  //nelCasoDichiara?: CasoIntereventoDiVariante;
  allegato: Allegato
}

export class TipoDichiarazione
{
  valore: any;
  valoriDiTipo: OptionValore[];
}

export class CasoIntereventoDiVariante
{
  numero: string;
  da: string;
  inData: Date;
}

export class TecnicoIncaricato extends InformazionePersonale
{
  residenteIn: IndirizzoCompleto;
  conStudioIn: IndirizzoCompleto;

  indirizzoEmail: string;

  iscritoAllOrdine?: string;
  di?: string;
  n?: string;

  recapitoTelefonico?: string;
  fax: string;
  cellulare: string;
  pec: string;
}

/* export class localizzazione
{
  localizazioniInfo?: localizzazioneInfo[]; 
  allegaShapeFile?: Allegato[];
  ulterioriInformazioni?: UlterioriInformazioni;
} */

export class localizzazione
{
  localizzazioneComuni: LocalizzazioneIntervento[];
  allegaShapeFile?: Allegato[];
}

export class LocalizzazioneIntervento
{
  comuneId: number;
  comune?: string;
  indirizzo?: string;
  numCivico?: string;
  piano?: string;
  interno?: string;
  destUsoAtt?: string;
  destUsoProg?: string;
  siglaProvincia?: string;
  dataRiferimentoCatastale?: Date | string;
  strade?: boolean;
  particelle?: localizzazioneInfo[];
  ulterioriInformazioni?: UlterioriInformazioni;
}

export class localizzazioneInfo
{
  id?: number;
  //comune: string;
  sezione?: string;
  livello?: string;
  foglio?: number;
  particella?: number;
  sub?: number;
  oid?: number;
  note?: string;
  codCat?: string;
  descrSezione?: string;
}
/* TODO: DOREDITI TIPOVE DA NE OSTANE ANY  */
export class UlterioriInformazioni
{
  bpParchiEReserve: any; /* U pitanju je combobox  */
  ucpPaesaggioRurale: any;
  bpImmobileAreePubblico: any[];
}

/* export class Allegato
{
  id?: string | number;
  descrizione?: string;
  nome?: string;
  data?: Date | string;
  path?: string;
  status?: string;
} */

export class Allegato
{
  id?: string | number;
  descrizione?: string;
  nome?: string;
  data?: Date | string;
  path?: string;
  status?: string; /*  Ovde moze isto da se stavi enumeracija ako nam bude trebalo */
  praticaId?: string;
  size?: number;
  tipiContenuto?: string[];//opzionale, presente solo per gli allegati con tipologia multipla
  type?: string;
  idIntegrazione?:number;
  checksum?: string;
}


export class DocumentoAllegato extends BasicFile
{
  //da cancellare
  public codiceFascicolo: string;
  public status?: string;
  public description?: string;
  public extension: string;
}

export class DocumentoRelazione extends DocumentoAllegato
{
  public idRelazione: number;
}

export class DocumentoParere extends DocumentoAllegato
{
  public idParere: number;

  constructor()
  {
    super();
    this.type = "PARERE_MIBAC";
    this.formatoFile = "Parere Mibac";
  }
}

/***  PREBACITI ENUM U POSEBAN FAJL  ****/

export enum AttivitaDaEspletareEnum
{
  CompilaDemanda = 'Compila Demanda',
  GeneraStampaDomanda = 'Genera Stampa Domanda',
  AllegaDocumentiSottoscritti = 'Allega Documenti Sottoscritti',
  InAttesaDiProtocollazione = 'In Attesa Di Protocollazione',
  IstanzaPresentata = 'Istanza Presentata'
} //fa fede StatoEnum

/**
 * ATTENZIONE: 
 * - Lo stato Presa in carico per il comportamento che ha e per cui è stato programmato corrisponde
 *   pari pari allo stato di preistruttoria.
 * - Lo stato "NonAssegnata", "Protocollazione", "Sospesa" e "Archiviata" sono stati aggiunti in un secondo momento
 * - Gli stati "InLavorazione", "RelazioneDaTrasmettere", "InAttesaDiRicezioneParereDellaSoprintedenza" e 
 *   "ParereRicevutoDallaSoprintendenza" corrispondono allo stato di "In lavorazione"
 */
export enum StatoEnum 
{
  compilaDomanda = 'COMPILA_DOMANDA',
  GeneraStampaDomanda = 'GENERA_STAMPA_DOMANDA',
  AllegaDocumentiSottoscritti = 'ALLEGA_DOCUMENTI_SOTTOSCRITTI',
  InAttesaDiProtocollazione = 'IN_ATTESA_DI_PROTOCOLLAZIONE',
  //inProtocollazione = 'IN_PROTOCOLLAZIONE',
  PresaInCarica = 'IN_PREISTRUTTORIA',
  InLavorazione = 'IN_LAVORAZIONE',
  InTrasmissione = 'IN_TRASMISSIONE',
  Trasmessa = 'TRANSMITTED',
  Sospesa = 'SOSPESA',
  Archiviata = 'ARCHIVIATA'
}

/* export enum TipoProcedimento
{
  AUT_PAES_ORD = "AUT_PAES_ORD",
  AUT_PAES_SEMPL_DPR_31_2017 = "AUT_PAES_SEMPL_DPR_31_2017",
  ACCERT_COMPAT_PAES_DLGS_42_2004 = "ACCERT_COMPAT_PAES_DLGS_42_2004",
  ACCERT_COMPAT_PAES_DPR_139_2010 = "ACCERT_COMPAT_PAES_DPR_139_2010"
} */

export enum TipoProcedimento
{
  AUT_PAES_ORD = '1',
  AUT_PAES_SEMPL_DPR_31_2017 = '2',
  ACCERT_COMPAT_PAES_DLGS_42_2004 = '3',
  ACCERT_COMPAT_PAES_DPR_139_2010 = '4'
}

export class StatoGroup
{
  label?: string;
  color?: string;
  timeLinePosition?: string;
}

/*export const StatoLegend = new Map([
  [AttivitaDaEspletareEnum.CompilaDemanda, 'Compila Demanda'],
  [AttivitaDaEspletareEnum.IstanzaPresentata, 'Instanza Presentata']
]);

export const AttivitaDaEspletareEnumLabels = new Map([
  [AttivitaDaEspletareEnum.CompilaDemanda, 'Compila Demanda'],
  [AttivitaDaEspletareEnum.IstanzaPresentata, 'Instanza Presentata']
]);*/

//*******   Compilation     *******/

/* export class SchedaTecnica
{
  descrizione?: SchedaTecnicaDescrizione;
  destinazione?: DestinazioneUrbanistica[];
  legittimita?: Legittimita;
  procedureEdilizie?: ProcedureEdilizie;
  inquadramento?: Inquadramento;
  effetiMitigazione?: EffetiMitigazione;
  eventualiProcedimenti?: EventualiProcedimenti;
  parreriEAtti?: PareriAtti;
  pptr?: PPTR;
  vincolistica?: Vincolistica;
  dichiara?: any | boolean;
  valida?: boolean;
} */

export class SchedaTecnica
{
  idPratica: string;
  descrizione?: SchedaTecnicaDescrizione;
  destinazione?: DestinazioneUrbanistica[];
  legittimita?: Legittimita;
  procedureEdilizie?: ProcedureEdilizie;
  inquadramento?: Inquadramento;
  effetiMitigazione?: EffetiMitigazione;
  eventualiProcedimenti?: EventualiProcedimenti;
  parreriEAtti?: PareriAtti;
  pptr?: PPTR;
  vincolistica?: Vincolistica;
  //dichiara?: any | boolean; // non serve a niente
  dichiarazione: Dichiarazione;
  valida?: boolean;
}

export class Dichiarazione
{
  testo: string; // testo
  labelCb: string; // label checkbox
  accettata: boolean; // accettata oppure no
}

export class DestinazioneUrbanistica
{
  titolo: string;
  strumentoUrbanistico: string | number;
  approvatoData: Date | string;
  deliberazioneApprovazione: string;
  destinazioneUrbanistica: string;
  ulterioriTutele: string;
  confermaCoerenza: boolean;
  strumentoInAdozione: string;
  conformitaStrumentoUrbanistico: boolean;
  //aggiunti dopo
  approvatoCon: string;
  adottatoData: Date;
  adottatoCon: string;
  destinazioneUrbanisticaAdottato: string;
  ulterioriTuteleAdottato: string;
  comuneId: number;
  mostraCoerenza: boolean;
  coerenzaData: Date;
  coerenzaAtto: string;
}

/* export class SchedaTecnicaDescrizione
{
  descrizione?: string;
  tipologiaDiIntervento?: TipologiaDiIntervento;
  caratterizzazioneIntervento?: CaratterizzazioneIntervento;
  qualificazioneIntervento?: OptionValore; //
  tipologiaDiInterventoDegliArtt3?: OptionValore[];
} */

export class SchedaTecnicaDescrizione
{
  descrizione?: string;
  tipoIntervento?: TipologiaDiIntervento;
  caratterizzazioneIntervento?: CaratterizzazioneIntervento;
  qualificazioneIntervento?: ConfigOptionValue[];
}

export class TipologiaDiIntervento
{
  tipologiaDiIntervento: string | { value: any, text: string }[];
  inParticolareAgliArtt: string;
  dataApprovazione: Date;
  con: string;
}

export class CaratterizzazioneIntervento 
{
  caratterizzazione?: ConfigOptionValue[];
  durata?: string | { value: any, text: string }[];
}

export class ProcedureEdilizie
{
  tipoIntervento: any;
  tipoStato: any;
  motivazione: string;
  pressoData: Date | string;
  espressoData: Date | string;
  detagglio: string | ProcedureEdiliziaDetaglio;
}

export class ProcedureEdiliziaDetaglio
{
  presentataPresso?: string;
  data?: Date;
}

export class OptionValore
{
  valore: any;
  tipoOption?: TipoOpzione;
  specificaValore?: string; /*additional value */
  valoriOpzione: OptionValore[];
  allegato?: Allegato;
}

export class Destinazione
{
  strumentoUrbanisticoGenSeq: any;
  approvatoInData: Date;
  approvatoInCon: string;
  destinazioneUrbanisticaVigente: string;
  ulterioriTuteleVigente: string;
  strumententoUrbanistico: boolean;
  strumentoInAdozione: any;
  adottatoINData: Date;
  adottatoINCon: string;
  destinazioneUrbanisticaAdottato: string;
  ulterioriTuteleAdottato: string;
  urbanInstrumentoConferme: boolean;
}

export class Legittimita
{
  tipoLegittimitaUrbanistica: any;   /*  Ovde mozemo isto da stavimo ENUM   */
  legittimitaUrbanstica: string;
  legittimitaInfo: TipologiaDettaglio[];
  tipoLegittimitaPaesaggistica: any; /*  ISTO ENUM moze  */
  dettaglioLegittimitaPaesaggistica: ImposizioneDelVincolo | TipologiaDettaglio[];
  autorizzatoPaesaggisticamenteInfo: TipologiaDettaglio[];
}

export class ImposizioneDelVincolo
{
  tipologiaVincolo: string;
  dataIntervento: Date | string;
  dataImposizioneVincolo: Date | string;
}

export class TipologiaDettaglio
{
  tipologia: string;
  rilasciatoDa: string;
  noProtocollo: string;
  dataRilascio: Date | string;
  intestinario: string;
}

export class Inquadramento
{
  destinazioneUso: string | number | OptionValore;
  contestoPaesaggInterv: string | number | OptionValore;
  morfologiaConPaesag: string | number | OptionValore;
  destinazioneUsoSpecifica: string;
  contestoPaesaggIntervSpecifica: string;
  morfologiaConPaesagSpecifica: string;
}

export class EffetiMitigazione
{
  descrizione: string;
  effeti: string;
  misure: string;
  contenutiPercettivi: string;
}

export class EventualiProcedimenti
{
  contenzisoAtto: any;
  descrizione: string;
}

export class PareriAtti
{
  parreriInfo: TipologiaDettaglio[];
  attiInfo: TipologiaDettaglio[];
}

export class PPTR
{
  ambitoPaesaggistico: any;
  figura: string;
  art103: boolean;
  art142: boolean;
  descrizione?: string; /*  Da li ide sve ili samo ako je druga opcija izabrana  */
  ulteririContestiPaesaggistici: any[];
}

export class Vincolistica
{
  sottopostoATutela: boolean | string;
  specificaVincolo: any[] | string;
  altriVincolo: string;
}
export class Allegati
{
  documentazioneAmministrativa?: DocumentazioneAmministrativa;
  documentazioneTecnica?: DocumentazioneTecnica;
  valida?: boolean;
}

export enum TipologiaEnte
{
  RegionePuglia = 'Regione Puglia',
  ProvinciaDiFoggia = 'Provincia di Foggia',
  Comuni = 'Comuni'
}
export class IndirizziEnti
{
  id: number | string;
  denominazione: string;
  tipologia: TipologiaEnte;
  pec: string;
  mail: string;
}
export class DocumentazioneAmministrativa
{
  grigliaPagamentoAllegati: Allegato[];
  descrizione: string;
  grigliaAllegatiCaricati: Allegato[];
}

//pari al BE
export interface TemplateEmailDestinatariDto
{
    template:TemplateEmailDTO;
    destinatari?:TemplateDestinatarioDTO[];
    placeholders?:PlainTypeStringId[];
}

export interface TemplateEmailDTO{
    idOrganizzazione:number; //chiave primaria
    codice: string; //chiave primaria
  	oggetto: string;
	  testo: string;
	  descrizione: string;
    nome: string;
    placeholders?:string ; //lista di placeholders separati da ,
    readonlyOggetto?:string;
    readonlyTesto?:string;
    visibilita?:string;
    sezione?:string;
    tipiProcedimentoAmmessi?:string;
    protocollazione?:string;
    cancellabile?:boolean;
    riservata?:boolean;
}


export interface TemplateDestinatarioDTO
{
  id: number;
  idOrganizzazione:number;
	codiceTemplate:string;
	email: string;
	pec: string;
	denominazione: string;
	tipo: 'TO'|'CC';
}


export class ComunicazioniTemplate
{
  id: string;
  nome?: string;
  oggetto: string;
  mittente?: string;
  riserva?: string;
  data?: string;
  descrizione?: string;
  testoTemplate?: string;
  a?: string;
  destinatari?: DestinatarioDTO[];
  allegati?: Allegato[];
  isDraft?: boolean;
}
export class DocumentazioneTecnica
{
  descrizione: string;
  grigliaAllegatiCaricati: Allegato[];
}
/**  Ovde treba navesti sve tipove ocija. To ce nam sluziti da se naprave opcije koje ce imati slicnu strukturu.
Nismo pravili jer postoji samo jedan nivo dubine svakako.  */
export enum TipoOpzione
{
  INTERVENTO_RIQUARDA,
  TIPPO_DI_INTER_DEG_ARTT3
}

export class User 
{
  name?: string;

  nome?: string;
  cognome?: string;
  username: string;

  password: string;
  role?: Role;
}

export class RelazioneEnte
{
  idRelazioneEnte?: number;
  idPratica?: string;
  numeroProtocolloEnte: string;
  nominativoIstruttore: string;
  dettaglioRelazione?: string;
  notaInterna: string;
  tipoAllegato: string;
  allegatiRelazione: DocumentoRelazione[];
  comunicazioneAllegate?: RelazioneComunicazione[]; 
  //questi campi ci sono ma verranno totalmente ignorati e, succesivamente, eliminati
  grigliaAllegati?: Allegato[];
  dettaglioCorrispondenza: DettaglioCorrispondenzaDTO;//ComunicazioniTemplate[];

  sent: boolean = false;
}

export class RelazioneComunicazione
{
  idRelazione: number;
  idComunicazione: number;
  tipoInvio: "WITH_PROT" | "WITHOUT_PROT";
}

export enum Role
{
  Richiedente = 1,
  GestoreRegione = 2,
  Soprintendenza = 3,
  EnteTerritoriale = 4,
  UtentePubblico = 5,
  Amministratore = 6,
  AmministratoreEnteDelegato = 7,
  AmministratoreSoprintendenza = 8,
  AmministratoreEnteTerritoriale = 9
}

export enum GroupType 
{
  Richiedente = "RICHIEDENTI",
  Regione = "REG",
  Soprintendenza = "SBAP",
  EnteTerritoriale = "ETI",
  EnteDelegato = "ED",
  EnteParco = "EPA",
  UtentePubblico = "PUB", //utile solo a livello di mockup, forse da rimuovere totalmente assieme agli tenti mockati
  Amministratore = "ADMIN",
  CommissioneLocale = "CL"
}

export enum GroupRole
{
  F = "Funzionario",
  D = "Dirigente",
  A = "Amministratore"
}

export enum CommonRole
{
  Amministratore = "ADMIN",
  Dirigente = "DIRG",
  Funzionario = "FUN"
}

export enum TipoAllegatoRelazione
{
  relazioneTecnicaIlustrativa = 'Relazione Tecnica Ilustrativa',
  altro = 'Altro'
}
export const RoleEnumLabels = new Map([
  [Role.Richiedente, 'Richiedente'],
  [Role.GestoreRegione, 'Gestore Regione'],
  [Role.Soprintendenza, 'Soprintendenza'],
  [Role.EnteTerritoriale, 'Ente Territoriale'],
  [Role.UtentePubblico, 'Utente Pubblico'],
  [Role.Amministratore, 'Amministratore'],
  [Role.AmministratoreEnteDelegato, 'Amministratore Ente Delegato'],
  [Role.AmministratoreSoprintendenza, 'Amministratore Soprintendenza'],
  [Role.AmministratoreEnteTerritoriale, 'Amministratore Ente Territoriale']
]);

export class UlterioreDocumentazione
{
  id: number;
  idFascicolo: string;
  bozza: boolean;
  titoloDocumento?: string;
  descrizioneContenuto?: string;
  data?: Date ;
  destinatarioNotifica?: string;
  notificaA: DestinatarioComunicazioneDTO[];
  direzione?: string;
  visibileA?: string;
  inseritoDa?: string;
  ruolo?: string;
  dataCondivisioneDa?: Date | string;
  dataCondivisioneA?: Date | string;
  anguigiMailPEC?: string;
  allega: string;
  visualizzabiliDa: any[];
  allegati: AllegatoDocumentazione[];
  files: File[]
}

export interface UlterioreDocumentazioneBean
{
  idFascicolo: number;
  allegati: AllegatoCustomDTO[];
  notifica: DestinatarioComunicazioneDTO[];
  enti: string[];
  direzione: boolean;
}

export class AllegatoDocumentazione extends BasicFile {
  lastModified: Date;
}

export class Destinatario
{
  id?: number;
  ente: string;
  mailPec: string;
}

export class ProvvedimentoFinale
{
  id: number;
  idPratica: string;
  numeroProvvedimento: string;
  dataRilascioAutorizzazione: Date;
  esitoProvvedimento: EsitoParere;
  rup: boolean;
  draft: boolean;
  idCorrispondenza: number;
  allegati?: Array<Allegato>;
  destinatariFissi?: Array<DestinatarioComunicazioneDTO>;
  ulterioriDestinatari?: Array<DestinatarioComunicazioneDTO>;

  constructor()
  {
    this.draft = true;
  }
}

export class Ente {
  id: number;
  denominazione: string;
  mail: string;
}

export interface BaseMap
{
  thumbnail: string;
  name: string;
  url: string;
}

export const basemap: BaseMap[] = [
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2006.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2006/ImageServer",
    name: "Ortofoto 2006"
  },
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2010.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2010/ImageServer",
    name: "Ortofoto 2010"
  },
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2011.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2011/ImageServer",
    name: "Ortofoto 2011"
  },
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2013/ImageServer",
    name: "Ortofoto 2013"
  },
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2016/ImageServer",
    name: "Ortofoto 2016"
  },
  {
    thumbnail: "http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/trasp.png",
    url: "http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/SfondoTrasp/MapServer",
    name: "Trasparente"
  }
];

export class LineaTemporaleDTO
{
  dataPresentazione: Date;
  dataInizioLavorazione: Date;
  dataTrasmissioneVerbale: Date;
  dataTrasmissioneRelazione: Date;
  dataTrasmissioneParere: Date;
  dataTrasmissioneProvvedimento: Date;
}

export class Counters
{
  n_non_assegnata: number;
  n_in_attesa_di_protocollazione: number;
  n_in_preistruttoria: number;
  n_in_lavorazione: number;
  n_in_trasmissione: number;
  n_transmitted: number;
  n_sospesa: number;
  n_archiviata: number;

  constructor()
  {
    this.n_non_assegnata = 0;
    this.n_in_attesa_di_protocollazione = 0;
    this.n_in_preistruttoria = 0;
    this.n_in_lavorazione = 0;
    this.n_in_trasmissione = 0;
    this.n_transmitted = 0;
    this.n_sospesa = 0;
    this.n_archiviata = 0;
  }
}

export interface SimplePratica
{
  oggetto: string;
  codicePraticaAppptr: string;
}

export type TipoAllegatoCL = "VERBALE"|"SCHEDA_TECNICA"|"ALTRO";

export class FileCommissioneLocale extends BasicFile
{
  idSeduta: number;
  praticheAssociate: Array<string>;
  tipoAllegato: TipoAllegatoCL;
}

export class SimpleFileCommissioneLocale extends BasicFile
{
  pratiche: Array<SimplePratica>;
  tipoAllegato: TipoAllegatoCL;
  idSeduta: number;
}

export class VerbaleCommissioneLocaleMocked extends FileCommissioneLocale
{
}

export class RupGruppoMocked
{
  codiceGruppo: string;
  username: string[]
}

export enum TipoRaggruppamento
{
  TIPO_INT = "TIPO_INT",
  CAR_INT = "CAR_INT",
  CAR_INT_TP = "CAR_INT_TP",
  DPCM_12_2005 = "DPCM_12_2005",
  QUAL = "QUAL",
  DPR_31_2017 = "DPR_31_2017",
  DLGS_42_2004 = "DLGS_42_2004"
}

export class TipiEQualificazioni 
{
  codice: string;
  stile: OptionType;
  raggruppamento: TipoRaggruppamento;
  label: string;
  ordine: number;
  key: number;
  dependency: number;
  hastext: boolean;
}

export class UlterioriContestiPaesaggistici
{
  codice: string;
  label: string;
  art1: string;
  definizione: string;
  disposizioni: string;
  art2: string;
  type: "group" | "type" | "optiongroup" | "option";
  hastext: boolean;
  dataInizioVal: Date;
  dataFineVal: Date;
  gruppo: string;

}

export interface ConfigOption
{
  value: string | number,
  label: string,
  hasDescription?: boolean,
  description?: string,
  tooltip?: string,
  hasChildren?: boolean,
  childLabel?: string,
  childName?: string,
  childValues?: any[],
  childOptions?: ConfigOption[];
  name?: string;
  extraData?: any
}

export type EsitoParere = "AUTORIZZATO"|"NON_AUTORIZZATO"|"AUT_CON_PRESCRIZ";

export class ParereSoprintendenza
{
  id: number;
	idPratica: string;
	numeroProtocollo: string;
	nominativoIstruttore: string;
  esitoParere: EsitoParere;
  note: string;
  dettaglioPrescrizione: string;
	usernameUtenteCreazione: string;
  dataInserimento: Date;
  allegati?: Array<Allegato>;
  comunicazione?: DettaglioCorrispondenzaDTO;

  constructor() { }
}

/**
 * Gestione del tipo di form control gerachico
 */
export type OptionType = "checkbox" | "radiobutton";

export interface OptionNode
{
  label: string;
  value: string;
  key: number;
  hasText: boolean;
  orderIndex: number;
  tooltip?: string;
  disabled?: boolean
}
export interface HierarchicalOption
{
  dependency: number;
  options: OptionNode[];
  children?: HierarchicalOption;
  childStyle?: string;
  name: string;
  type: OptionType;
}

export interface ConfigOptionValue
{
  value: string;
  text?: string;
}

export interface PPTRSelezioni
{
  codice: string;
  specifica?: string;
}

/**
 * corrisponde alla tabella BE tipo_contenuto
 */
export interface TipoContenuto
{
  id: number;
  descrizione: string;
  descrizioneEstesa: string;
  sezione: string; //DOC_AMMINISTRATIVA_D, DOC_AMMINISTRATIVA_E,DOC_TECNICA
}

export interface ValidazioneResponse
{
  istanza: boolean;
  schedaTecnica: boolean;
  allegati: boolean;
}

export type StatoIntegrazione = "IN_BOZZA" | "IN_ATTESA" | "COMPLETATA" | "ANNULLATA";

export class IntegrazioneDocumentale
{
  id: number;
  stato: StatoIntegrazione;
  dataCreazione?: Date;
  dataCaricamento?: Date;
  richiestaIntegrazone: boolean = false;
  usernameUtenteCreazione?: string;
  descrizione: string;
  note?: string;
  praticaId: string;

  allegati?: Allegati
  documentoIntegrazione?: Allegato;
}

export class RequestAllegato
{
  praticaId: string;
  referenteId?: number;
  tipiContenuto?: number[];//contiene gli id dei TipoContenuto
  nomeContenuto?: string;//contiene il nome dato dall'utente al file allegato a cui fanno capo più contenuti (gestione a mappa come da modulistica cartacea della documentazione tecnica)
  allegatoId?: string; //avvalorato da FE to BE in caso di richiesta di cancellazione (contiene l'UUID della pk allegato)
  integrazioneId?: number;
}

export interface ContainerInitSearch
{
  richiedenti: Array<PlainTypeStringId>;
  funzionari : Array<PlainTypeStringId>;
  comuni     : Array<PlainTypeStringId>;
  enti       : Array<PlainTypeStringId>;
}

export class PagamentiMypayDTO{
  iud:string;
  praticaId:string;
  causale:string;
  retUrl:string;
  dataInserimento?:Date|string;
  dataModifica?:Date|string;
  importo:number;
  esito:'OK'|'KO'|'INCORSO';
  error?:string;
  urlToPay:string;
}

export class PagamentiMypaySearch extends OrderBy
{
    praticaId: string;
}


/**
 * corrispondente common.utente_attribute
 */
 export interface UtenteAttributeDTO{
  //utenteId:number;
  //applicazioneId:number;
  pec:string;
  mail:string;
  birthDate?:Date;
  birthPlace?:string;
  phoneNumber?:string;
  mobileNumber?:string;
  lastRichiestaAbilitazione?:number;
}


/**
 * bean per i messaggi configurabili
 */
export interface WebContentDTO{
  codiceContenuto:string;
  tooltip:string;
  contenuto:string;
}

export class AvviaPagamentoRequestBean {
  importoDiProgetto?: number;
  dataScadenza?: Date;
}

export class PagamentoDto {
  importoProgetto?: number;
  importoPagamento?: number;
  dataScadenza?: Date;
	urlPdf?: string;
	urlMyPay?: string;
	pagato?: boolean;
	causale?: string;
}

export interface AllegatoUD
{
  titolo: string;
  descrizione: string;
  allegato: File;
}

export interface SelectItemDto {
  label: string;
  value: string;
  parent?: string;
}

export interface CdsSaveConfigurazione{
	attivita?:SelectItemDto[];
	azioni?:SelectItemDto[];
	tipo?:SelectItemDto[];
}

export interface CdsGetConfigurazione extends CdsSaveConfigurazione{
	attivitaSelezionabili?:SelectItemDto[];
	azioniSelezionabili?:SelectItemDto[];
	tipoSelezionabili?:SelectItemDto[];
}
