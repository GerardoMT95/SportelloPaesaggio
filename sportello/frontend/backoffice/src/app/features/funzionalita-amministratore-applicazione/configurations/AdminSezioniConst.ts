export type SezioneType = 'DICHIARAZIONE'|'DISCLAIMER'|'TARIFFE'|'CONFERENZA_SERVIZI'|null;

export type SezioniConf = {sezioni: string, value: SezioneType, icon: string,path:string};
export class AdminSezioniConst
{
    public static readonly sezioniConfigurazioneTipologia: Array<SezioniConf> = [
        { sezioni: 'TAB_TITLE.STATEMENT_TAB'      , value: 'DICHIARAZIONE'              , icon: 'arrow-right',path:"dichiarazione" },
        { sezioni: 'configurazioneProcedimento.sezioni.disclaimers'      , value: 'DISCLAIMER' , icon: 'arrow-right',path:"disclaimer" }
    ];
}

export class AdminEnteSezioniConst
{
    public static readonly sezioniConfigurazioneTipologia: Array<SezioniConf> = [
        { sezioni: 'configurazioneProcedimento.sezioni.tariffe', value: 'TARIFFE', icon: 'arrow-right', path: 'tariffe' },
        { sezioni: 'configurazioneProcedimento.sezioni.configurazioneConferenzaDeiServizi', value: 'CONFERENZA_SERVIZI' , icon: 'arrow-right',path:"conferenza-servizi" },
    ];
}

