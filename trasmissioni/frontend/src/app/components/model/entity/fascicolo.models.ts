import { StatoFascicolo } from './../model';
import { SelectOption } from 'src/shared/components/select-field/select-field.component';
import { ValidatorFn } from '@angular/forms';
export enum TipoProcedimento
{
    AUT_PAES_ORD,
    AUT_PAES_SEMPL_DPR_139_2010,
    AUT_PAES_SEMPL_DPR_31_2017,
    ACCERT_COMPAT_PAES_DLGS_42_2004,
    ACCERT_COMPAT_PAES_DPR_31_2017,
    ACCERT_COMPAT_PAES_DPR_139_2010
}

export enum RegistrationStatus
{
    WORKING="WORKING",
	TRANSMITTED="TRANSMITTED",
	CANCELLED="CANCELLED",
	SELECTED="SELECTED",
	FINISHED="FINISHED",
	ON_MODIFY="ON_MODIFY"
}

export enum EsitoAutorizzazione
{
    AUTORIZZATO, 
	NON_AUTORIZZATO, 
	AUT_CON_PRESCRIZ
}

export enum TipoIntervento
{
    INTERVENTI_NON_EDILIZI,
	MANUTENZIONE_RESTAURO,
	NUOVA_COSTRUZIONE,
	RISTR_EDILIZIA,
	RISTR_URBANISTICA
}

export enum QualificaDPCM_2005
{
    QualificaDPCM_2005_01,
	QualificaDPCM_2005_02,
	QualificaDPCM_2005_03
}

export class FascicoloPublicDto{
    id: number; //ha valore sono per le trasmissioni e non per le righe provenienti dallo sportello
    codice: string;
    ufficio: string; //codice organizzazione (es. ED_6)
    orgCreazione?:number; //es 6 organizzazione owner (Ente Delegato o REGione)
    stato: RegistrationStatus;
    oggettoIntervento: string;
    codiceInternoEnte: string;
    numeroInternoEnte: string;
    protocollo: string;
    dataProtocollo: Date;
    tipoProcedimento: TipoProcedimento;
    rup: string;
    esito: EsitoAutorizzazione;
    numeroProvvedimento: string;
    dataRilascioAutorizzazione: Date;
    esitoNumeroProvvedimento?: string;
    esitoDataRilascioAutorizzazione?: Date;
    esitoVerifica?: string;
    comuni:string[]; //nomi in chiaro dei comuni su cui è localizzato l'intervento 
    applicazione?:string; //applicazione di provenienza dell'autorizzazione (autpae o pae_pres_ist)
    groupCanAccess?:string; //gruppo a cui appartiene l'utente con cui si puo' accedere in dettaglio alla pratica
}

export class FascicoloDTO extends FascicoloPublicDto
{
    sanatoria: boolean;
    note: string;
    tipologiaIntervento: TipoIntervento;
    tipoInterventoTempo: string;
    interventoDettaglio: string; //anche per pareri.....
    interventoSpecifica: string;
    istanzaRinnovo: string;
    qualDPCM_2005: QualificaDPCM_2005;
    dataCreazione: Date;
    dataUltimaModifica: Date;
    dataTrasmissione: Date;
    dataCampionamento: Date;
    dataVerifica: Date;
    usernameUtenteCreazione: string;
    usernameUtenteUltimaModifica: string;
    avviso: string;
    usernameUtenteTrasmissione: string;
    modificabileFino: Date;
    statoTrasmissione: boolean;
}

export class BaseSearch
{
    //paginazione
    limit: number = 5;
    page: number = 1;
    //ordinamento
    colonna: string;
    direzione: string;
}

export class TemplateDocSearch extends BaseSearch{
    nome: string
}

export class FascicoloSearch extends BaseSearch
{
    codice: string;
    tipologiaIntervento: TipoIntervento;
    tipoProcedimento: TipoProcedimento
    dataProtocolloDa: Date | string;
    dataProtocolloA: Date | string;
    ufficio: string;
    esito: EsitoAutorizzazione;
    stato: StatoFascicolo;
    statoRegistrazione: RegistrationStatus;
    esitoVerifica: string;
    codiceInternoEnte: string;
    protocollo: string;
    numeroInternoEnte: string;
    dataRilascioAutorizzazioneDa: Date | string;
    dataRilascioAutorizzazioneA: Date | string;
    comuneIntervento: string;

    constructor() { super(); }
}

export class AcceleratoriDTO
{
    nWorking?: number;
    nTrasmitted: number;
    nCancelled: number;
    nSelected: number;
    nFinished: number;
    nOnModify: number;

    public static getStatus(key: string): string
    {
        let stato: string;
        switch(key)
        {
            case "nWorking":
                stato = "WORKING";
                break;
            case "nTrasmitted":
                stato = "TRANSMITTED";
                break;
            case "nCancelled":
                stato = "CANCELLED"
                break;
            case "nSelected":
                stato = "SELECTED";
                break;
            case "nFinished":
                stato = "FINISHED";
                break;
            case "nOnModify":
                stato = "ON_MODIFY";
                break;
            default: 
                stato = null;
                break;
        }
        return stato;
    } 
}

export class TableConfig {
    field?: string;
    header?: string;
    type?: string; //hidden, date-picker, text-field
    sortDirection?: boolean;
    width?: number;
    optionSelect?:SelectOption[];
    innerHtml?:string;
    validators?:ValidatorFn[];
  }


