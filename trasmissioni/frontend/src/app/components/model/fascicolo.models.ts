import { SelectOption } from "src/shared/components/select-field/select-field.component";
import { AllegatoCustomDTO } from "./entity/allegato.models";

export interface globalResponse<T>{
    code:string;
    message:string;
    partialSize:number;
    payload:any;
    /*payload:{
        list:T;
        count:number;
    };*/
    size:number;
}

export class Fascicolo{
    id:string;
    codice:string;
    descrizione:string;
    //idTipologiaIntervento:number;
    tipologiaIntervento:string;
    comune:string;
    responsabileProcedimento:string;
    numeroProvvedimento:string;
    dataProvvedimento:string;
    richiedente:string;
    esitoProvvedimento:string;
    //idEsitoProvvedimento:number;
    urlProvvedimentoFinale?:string;
    codeStatoFascicolo?:string; 
    esitoVerifica?:string;
    statoRegistrazione?:string; //in teoria e' calcolato da codeStatoFascicolo e  esitoVerifica
}

export class Particella{
    id:string;
    type:string;
    ente:string;
    foglio:string;
    sezione:string;
    particella:string;
    sub:string;
    codiceEnte:string;
}

/*export class Localizzazione {
    particelle?: Particella[];
    bpImmobiliAreeInteresse: string[];
    bpPaesaggioRurale: string[];
    bpParchiRiserve: string[];
}*/

export class Richiedente{
    cognome:string;
    nome:string;
    codiceFiscale:string;
    sesso:string;
    dataNascita:Date;
    nazionalitaNascitaName:string;
    provinciaNascitaName:string;
    comuneNascitaName:string;
    comuneNascitaEstero:string;
    comuneResidenzaEstero:string;
    nazionalitaResidenzaName:string;
    provinciaResidenzaName:string;
    comuneResidenzaName:string;
    viaResidenza:string;
    numeroResidenza:string;
    cap:string;
    telefono:string;
    mail:string;
    pec:string;
    ditta:string; //checkbox
    nomeDitta:string;
    dittaQualitaDi:string;
    dittaQualitaAltro:string;
    dittaCf:string;
    dittaPartitaIva:string;
}

export class Intervento{
    idTipoIntervento: string;
    //tipologiaIntervento: string; //???
    caratterizzazione:string[];
    rimessaInRipristinoDettaglio:string;
    altroSpecificare:string;
    idTipoInterventoTempo:string; //temporaneo T o permanente F
    idQualificazioneDPR31_2017:string; //ricadono DPR 31/2017 SI NO
    idQualificazioneDPR139_2010:string; //ricadono DPR 31/2017 SI NO
    qualDPR31_2017:string[];
    istanzaRinnovo?:string; 
    qualDLGS42_2004?:string[];
    qualDPR139_2010?:string[];
    qualDPCM_2005?:string; //singola selezione
}

export class Provvedimento{
    numeroProvvedimento: string;
    dataProvvedimento: string;
    idEsitoProvvedimento: string;
    responsabileProcedimento: string; //rup
}

export class DettaglioFascicolo {
    id: string;   
    codice: string;
    ufficioComune: string;
    tipoProcedimento: string;
    isInSanatoria = false;
    oggettoIntervento: string;
    codiceInternoEnte: string;
    numeroInternoEnte: string;
    protocolloInternoEnte: string;
    dataProtocolloInternoEnte: Date;
    note: string;
    //richiedente
    richiedente:Richiedente;
    //intervento
    intervento:Intervento;
    //localizzazione
    localizzazione?: Localizzazione;

    comune: string;
    //provvedimento
    provvedimento:Provvedimento;
    
    urlProvvedimentoFinale?: string;
    codeStatoFascicolo?: string;
    //nomeStatoFascicolo?: string;
    dataCreazione:Date|string;
    dataTrasmissione?:Date|string;
    userNameCreazione:string;
    //sezione verifica campionamento
    noteVerifica?:string;
    esitoVerifica?:string;
    dataVerifica?:string;
    comuniCompetenza: ComuniCompetenza[]; //hidden non nei form. lista di ente_id dei comuni su cui l'ente territoriale ha competenza.
 }

 export interface Allegato
 {
    id: number;
    nomeFile:string;
    dataArrivo:string;
    descrizione:string;
}

export class ComuniCompetenza {
    enteId?: number;
    descrizione?: string;
    codCat?: string;
    codIstat?: string;
  }  

 /**nuovi */
export class Localizzazione {
    localizzazioneComuni: LocalizzazioneIntervento[];
    geoAllegati: AllegatoCustomDTO[];
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
    sezione?: string;
    livello?: string;
    foglio?: number;
    particella?: number;
    sub?: number;
    oid?:number;
    note?:string;
    codCat?:string;
    descrSezione?:string;
}

export class UlterioriInformazioni {
    //le options contengono tutti i valori selezionabili*/
    bpParchiEReserve?: string[];
    bpParchiEReserveOptions?: SelectOption[];
    ucpPaesaggioRurale?: string[];
    ucpPaesaggioRuraleOptions?: SelectOption[];
    bpImmobileAreePubblico?: string[];
    bpImmobileAreePubblicoOptions?: SelectOption[];
}

export class ValidationBeanDto {
    richiedenteError:SelectOption[];
	fascicoloError:SelectOption[];
	interventoError:SelectOption[];
	localizzazioneError:SelectOption[];
	provvedimentoError:SelectOption[];
	allegatiError:SelectOption[];
    valid:boolean;
}
