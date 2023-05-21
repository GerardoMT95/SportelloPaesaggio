import { OrderBy } from 'src/app/features/interfaccia-di-riepilogo-del-fascicolo/models/corrispondenza.model';
import { SelectOption } from '../components/select-field/select-field.component';

export class Fascicolo {
  id?: string; //hidden
  comuniCompetenza: ComuniCompetenza[]; //hidden non nei form. lista di ente_id dei comuni su cui l'ente territoriale ha competenza.
  codicePraticaAppptr?: string;
  enteDelegato?: any;
  oggetto?: string;
  tipoProcedimento?: string;
  dataCreazione?: Date | string;
  dataModifica?: Date | string;
  attivitaDaEspletare?: AttivitaDaEspletareEnum;
  displayButton?: boolean;
  owner:string;
  currentUserOwner?:boolean;
  currentUserRichiedente?:boolean;
  /** TODO: Treba dodati  */
  istanza?: Istanza;
  schedaTecnica?: SchedaTecnica;
  allegati?: Allegati;
  privacyId: number;
  privacyText: string;
  documentiSottoscritti?: Allegato[];

  validatoIstnza: boolean;
  validatoSchedaTecnica: boolean;
  validatoAllegati: boolean;
  validatoRichiedente: boolean;
  tipoSelezioneLocalizzazione?: string;
  hasShape?: boolean;
  dataPresentazione?:Date|string;
  dataTrasmissioneProvvedimentoFinale?:Date|string;
  dataProtocollo?:Date|string;
  numeroProtocollo?:string;
  totPagamenti?:{'OK'?:number,'KO'?:number,'INCORSO'?:number};
  ruoloPratica?: string;
  esoneroBollo?: boolean;
  esoneroOneri?: boolean;

  delegatoPratica?: PraticaDelegato[]; 
  codiceSegreto?: string;
}

export interface PraticaDelegato
{
  capResidenza?: string;
  civicoResidenza?: string;
  codiceFiscale?: string;
  cognome?: string;
  idNazioneNascita?: number;
  idNazioneResidenza?: number;
  idProvinciaNascita?: number;
  provinciaNascita?: string;
  idComuneNascita?: number;
  comuneNascita?: string;
  comuneNascitaEstero?: string;
  idComuneResidenza?: number;
  comuneResidenza?: string;
  idProvinciaResidenza?: number;
  provinciaResidenza?: string;
  comuneResidenzaEstero?: string;
  dataNascita?: Date;
  mail?: string;
  nazioneNascita?: string;
  nazioneResidenza?: string;
  indirizzoResidenza?: string;
  nome?: string;
  pec?: string;
  sesso?: string;
  allegatoDelega?:Allegato;
  allegatoSollevamento?:Allegato;
}

export class ComuniCompetenza {
  enteId?: number;
  descrizione?: string;
  codCat?: string;
  codIstat?: string;
  vincoloArt38?:string;
  vincoloArt100?:string;
}

export class AllegatiDelegato {
  codiceFiscale?: string;
  allegato?:Allegato;
}

export class FascicoloSearch {
  codicePraticaAppptr?: string;
  enteDelegato?: any;
  oggetto?: string;
  tipoProcedimento?: string;
  dataCreazione?: Date | string;
  attivitaDaEspletare?: AttivitaDaEspletareEnum;
  displayButton?: boolean;
  likeCodicePraticaAppptr?: string;
  page?: number = 1;
  limit?: number = 5;
  sortBy?: string;
  sortType?: string;
}

export class FascicoloList {
  count: number;
  list: Fascicolo[];
}

export class Istanza {
  richiedente?: Richiedente;
  dichiarazioni?: Dichiarazioni;
  tecnicoIncaricato?: TecnicoIncaricato;
  altriTitolari?: AltroTitolare[];
  //da completare
  localizzazione?: Localizazione;
  /* Nije konzistentan naziv. Negde pise TecnicoRifirimento  */
  privacyAccettata?: boolean;
  valida?: boolean;

  delegatoPratica?: Array<PraticaDelegato>;
}

export class InformazionePersonale {
  id?: number; //chiave nel DB
  cognome: string;
  nome: string;
  codiceFiscale: string;
  sesso: string;
  natoIl: Date | string;
  nattoStato: string;
  nattoCitta: string;
  documento?: ReferentiDocumentoDTO;
  //tipo: any; /*  enum ili neki drugi tip  */
  //numero: string;
  //rilascitoIl: Date | string;
  //rilascitoDa: string;
  //dataScadenza: Date | string;
  /* TODO: Check if there will be more than one document there is some unclear things in documentation.  */
  //docAllegato?: Allegato;
}

export class Richiedente extends InformazionePersonale {
  residenteIn: IndirizzoCompleto;
  recapitoTelefonico: string;
  indirizzoEmail: string;
  pec: string;
  nelCaso: boolean;
  inQualitaDi: string;
  descAltroDitta: string;
  societa: string;
  partitaIva: string;
  societaCodiceFiscale: string;
  idTipoAzienda:string;
	tipoAzienda:string;
	codiceIpa:string;
  cUO: string;
}

export class IndirizzoCompleto {
  stato?: SelectOption;
  citta?: string;//caso estero
  comune?: SelectOption;
  provincia?: SelectOption;
  via?: string;
  n?: string;
  cap?: string;
}
// TODO: Ovde provertiti da li su sva polja identicna.
export class AltroTitolare extends Richiedente {
  titolaritaInQualitaDi: any; // TODO: Nejasan je skup vrednosti
  descrizioneAltro: string;
}

export class ReferentiDocumentoDTO {
  idTipo: number;
  numero: string;
  dataRilascio: Date;
  enteRilascio: Date;
  dataScadenza: Date;
  docAllegato?: Allegato;
}

export class Dichiarazioni {
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
  //art134?: string[];
  art134?: boolean;
  //tipiDichiarazione?: TipoDichiarazione[];
  /*finiscono in disclaimer_pratica */
  altreOpzioni?: number[]; //tabella disclaimer_pratica
  //nelCasoDichiara?: CasoIntereventoDiVariante;
  allegato: Allegato
}

/*export class TipoDichiarazione {
    valore: any;
    valoriDiTipo: OptionValore[];
}*/

/*export class CasoIntereventoDiVariante {
    numero: string;
    da: string;
    inData: Date;
}*/

export class TecnicoIncaricato extends InformazionePersonale {
  residenteIn: IndirizzoCompleto;
  conStudioIn: IndirizzoCompleto;

  //indirizzoEmail: string;

  iscritoAllOrdine?: string;
  di?: string;
  n?: string;

  recapitoTelefonico?: string;
  fax: string;
  cellulare: string;
  pec: string;
}

export class Localizazione {
  //localizazioniInfo?: LocalizazioneInfo[]; //* */
  localizzazioneComuni: LocalizzazioneIntervento[];
  allegaShapeFile?: Allegato[];
  //ulterioriInformazioni?: UlterioriInformazioni;
}

export class LocalizzazioneIntervento {
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
  particelle?: LocalizazioneInfo[];
  ulterioriInformazioni?: UlterioriInformazioni;
}

export class LocalizazioneInfo {
  id?: number;
  //comune: string;
  sezione?: string;
  livello?: string;
  foglio?: number;
  particella?: number;
  sub?: number;
  oid?:number;
  note?:string;
  codCat?:string;
  descrSezione?: string;
}

export class RequestAllegato {
  praticaId: string;
  referenteId?: number;
  tipiContenuto?:number[];//contiene gli id dei TipoContenuto
  nomeContenuto?:string;//contiene il nome dato dall'utente al file allegato a cui fanno capo più contenuti (gestione a mappa come da modulistica cartacea della documentazione tecnica)
  allegatoId?: string; //avvalorato da FE to BE in caso di richiesta di cancellazione (contiene l'UUID della pk allegato)
  integrazioneId?: number;
  isSigned?:boolean; //indica che il file è firmato digitalmente
  invalidaSezione?:boolean; //true  per invalidare la sezione relativa a cui appartiene in caso di delete
}

/* TODO: DOREDITI TIPOVE DA NE OSTANE ANY  */
export class UlterioriInformazioni {
  //le options contengono tutti i valori selezionabili*/
  bpParchiEReserve?: string[];
  bpParchiEReserveOptions?: SelectOption[];
  ucpPaesaggioRurale?: string[];
  ucpPaesaggioRuraleOptions?: SelectOption[];
  bpImmobileAreePubblico?: string[];
  bpImmobileAreePubblicoOptions?: SelectOption[];
}

export class Allegato {
  id?: string | number;
  descrizione?: string;
  nome?: string;
  data?: Date | string;
  path?: string;
  status?: string; /*  Ovde moze isto da se stavi enumeracija ako nam bude trebalo */
  praticaId?: string;
  size?: number;
  tipiContenuto?:string[];//opzionale, presente solo per gli allegati con tipologia multipla
  idIntegrazione?:number;
  formatoFile?:string;
  checksum?:string;
}


export enum AttivitaDaEspletareEnum {
  COMPILA_DOMANDA = "COMPILA_DOMANDA",
  GENERA_STAMPA_DOMANDA = "GENERA_STAMPA_DOMANDA",
  ALLEGA_DOCUMENTI_SOTTOSCRITTI = "ALLEGA_DOCUMENTI_SOTTOSCRITTI",
  IN_ATTESA_DI_PROTOCOLLAZIONE = "IN_ATTESA_DI_PROTOCOLLAZIONE",
	IN_PREISTRUTTORIA="IN_PREISTRUTTORIA",
	IN_LAVORAZIONE="IN_LAVORAZIONE",
	IN_TRASMISSIONE="IN_TRASMISSIONE",
	SOSPESA="SOSPESA",
	ARCHIVIATA="ARCHIVIATA",
	TRANSMITTED="TRANSMITTED"// come su presentazione e mi serve per il layer_geo pubblico...
};

 export const statiEditingIstanza=[
  AttivitaDaEspletareEnum.COMPILA_DOMANDA,
  AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA,
  AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI,
];

export const statiLavorazione=[
  AttivitaDaEspletareEnum.IN_PREISTRUTTORIA,
  AttivitaDaEspletareEnum.IN_LAVORAZIONE,
  AttivitaDaEspletareEnum.IN_TRASMISSIONE
];

export const AttivitaDaEspletareEnumLabels = new Map([
  [AttivitaDaEspletareEnum.COMPILA_DOMANDA, "Compila Domanda"],
  [AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA, "Genera Stampa Domanda"],
  [AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI, "Allega Documenti Sottoscritti"],
  [AttivitaDaEspletareEnum.IN_ATTESA_DI_PROTOCOLLAZIONE, "In Attesa Di Protocollazione"],
  //[AttivitaDaEspletareEnum.ISTANZA_PRESENTATA, "Instanza Presentata"],
  //[AttivitaDaEspletareEnum.NON_ASSEGNATA, "Instanza Presentata"],
  [AttivitaDaEspletareEnum.IN_PREISTRUTTORIA, "Instanza Presentata"],
  [AttivitaDaEspletareEnum.IN_LAVORAZIONE, "In Lavorazione"],
  //[AttivitaDaEspletareEnum.RELAZIONE_DA_TRASMETTERE, "Instanza Presentata"],
  //[AttivitaDaEspletareEnum.ATTESA_PARERE_SOPRINTENDENZA, "Instanza Presentata"],
  [AttivitaDaEspletareEnum.IN_TRASMISSIONE, "In Trasmissione"],
  [AttivitaDaEspletareEnum.SOSPESA, "Sospesa"],
  [AttivitaDaEspletareEnum.ARCHIVIATA, "Archiviata"],
  [AttivitaDaEspletareEnum.TRANSMITTED, "Trasmessa"],

]);

//*******   Compilation     *******/

export class SchedaTecnica {
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

export class Dichiarazione {
  testo: string; // testo
  labelCb: string; // label checkbox
  accettata: boolean; // accettata oppure no
}

export class DestinazioneUrbanistica {
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
  mostraCoerenza: boolean;//non piu' gestito
  coerenzaData: Date; //non piu' gestito
  coerenzaAtto: string; //contiene direttamente la frase da indicare
}

export class SchedaTecnicaDescrizione {
  descrizione?: string;
  tipoIntervento?: TipologiaDiIntervento;
  caratterizzazioneIntervento?: CaratterizzazioneIntervento;
  qualificazioneIntervento?: ConfigOptionValue[];
}

export class TipologiaDiIntervento {
  tipologiaDiIntervento: string|{value: any, text: string}[];
  inParticolareAgliArtt: string;
  dataApprovazione: Date;
  con: string;
}

export class CaratterizzazioneIntervento 
{
  caratterizzazione?: ConfigOptionValue[];
  durata?: string |{value: any, text: string}[];
}

export class ProcedureEdilizie {
  tipoIntervento: any;
  tipoStato: any;
  motivazione: string;
  pressoData: Date | string;
  espressoData: Date | string;
  detagglio: string | ProcedureEdiliziaDetaglio;
}

export class ProcedureEdiliziaDetaglio {
  presentataPresso?: string;
  data?: Date;
}

export class OptionValore {
  valore: any;
  tipoOption?: TipoOpzione;
  specificaValore?: string; /*additional value */
  valoriOpzione: OptionValore[];
  allegato?: Allegato;
}

export class Destinazione {
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


export class Legittimita {
  tipoLegittimitaUrbanistica: any;   /*  Ovde mozemo isto da stavimo ENUM   */
  legittimitaUrbanstica: string;
  legittimitaInfo: TipologiaDettaglio[];
  tipoLegittimitaPaesaggistica: any; /*  ISTO ENUM moze  */
  dettaglioLegittimitaPaesaggistica: ImposizioneDelVincolo | TipologiaDettaglio[];
  autorizzatoPaesaggisticamenteInfo: TipologiaDettaglio[];
}

export class ImposizioneDelVincolo {
  tipologiaVincolo: string;
  dataIntervento: Date | string;
  dataImposizioneVincolo: Date | string;
}

export class TipologiaDettaglio {
  tipologia: string;
  rilasciatoDa: string;
  noProtocollo: string;
  dataRilascio: Date | string;
  intestinario: string;
}

export class Inquadramento {
  destinazioneUso: string | number | OptionValore;
  contestoPaesaggInterv: string | number | OptionValore;
  morfologiaConPaesag: string | number | OptionValore;
  destinazioneUsoSpecifica: string;
  contestoPaesaggIntervSpecifica: string;
  morfologiaConPaesagSpecifica: string;
}

export class EffetiMitigazione {
  descrizione: string;
  effeti: string;
  misure: string;
  contenutiPercettivi: string;
}

export class EventualiProcedimenti {
  contenzisoAtto: boolean; //Questo in realtà è un boolean!!
  descrizione: string;
}

export class PareriAtti {
  parreriInfo: TipologiaDettaglio[];
  attiInfo: TipologiaDettaglio[];
}

export class PPTR {
  ambitoPaesaggistico: any;
  figura: string;
  art103: boolean;
  art142: boolean;
  descrizione?: string;   //non utilizzato....
  ulteririContestiPaesaggistici: PPTRSelezioni[];
}

export class Vincolistica {
  sottopostoATutela: boolean | string;
  specificaVincolo: any[];
  altriVincolo: string;
}
export class Allegati {
  documentazioneAmministrativa?: DocumentazioneAmministrativa;
  documentazioneTecnica?: DocumentazioneTecnica;
  valida?: boolean;
}

export class DocumentazioneAmministrativa {
  grigliaPagamentoAllegati: Allegato[];
  descrizione: string;
  grigliaAllegatiCaricati: Allegato[];

}

export class DocumentazioneTecnica {
  descrizione: string;
  grigliaAllegatiCaricati: Allegato[];
}
/**  Ovde treba navesti sve tipove ocija. To ce nam sluziti da se naprave opcije koje ce imati slicnu strukturu.
Nismo pravili jer postoji samo jedan nivo dubine svakako.  */
export enum TipoOpzione {
  INTERVENTO_RIQUARDA, TIPO_DI_INTER_DEG_ARTT3
}

export enum TipoRaggruppamento {
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
  type: "group"|"type"|"optiongroup"|"option";
  hastext: boolean;
  dataInizioVal: Date;
  dataFineVal: Date;
  gruppo: string;

}

export interface ConfigOption {
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

/**
 * Gestione del tipo di form control gerachico
 */
export type OptionType = "checkbox"|"radiobutton";

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
export interface TipoContenuto{
  id:number;
  descrizione:string;
  descrizioneEstesa:string;
  sezione:string; //DOC_AMMINISTRATIVA_D, DOC_AMMINISTRATIVA_E,DOC_TECNICA
  checkPagamenti:string; //RO=ricevuta oneri BL=scansione bolli
}

export interface ValidInfoDto
{
  istanza: boolean;
	schedaTecnica: boolean;
  allegati: boolean;
  avviso?:string; //in caso di cambiamenti su dati pibblicati circa i comuni dell'intervento (cambio di BP UCP oppure cambio di strumenti urbanistici)
}

export type StatoIntegrazione = "IN_BOZZA"|"IN_ATTESA"|"COMPLETATA"|"ANNULLATA";
export enum RuoloPratica{
  PR='PR',
  DE='DE'
}

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


export interface WebContentDTO{
  codiceContenuto:string;
  contenuto:string;
  tooltip:string;
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

export interface IpaEnte
{
  codiceUo: string;
  nome: string;
  codiceFiscale: string;
  codiceIpa: string;
}