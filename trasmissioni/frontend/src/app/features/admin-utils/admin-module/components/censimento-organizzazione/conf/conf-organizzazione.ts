import { TipoEnte } from 'src/app/components/model/entity/corrispondenza.models';
import { Paging } from './../../../../../../../shared/components/rows-number-handler/rows-number-handler.component';

export type TipoOrganizzazione = 'ADMIN'|'REG'|'ETI'|'SBAP'|'EPA'|'ED'|'CL';
export type TipoOrganizzazioneSpecifica = 'Regione'|'Provincia'|'Comune'|'Unione'|'Soprintendenza'|'Commissione Locale'|'Associazione'|
                                          'REGIONE'|'PROVINCIA'|'COMUNE'|'UNIONE'|'SOPRINTENDENZA'|'COMMISSIONE_LOCALE'|'ASSOCIAZIONE';
export interface EnteSummary {denominazione: string, idEnte: any, idOrganizzazioneCompetenze: number, dataTermine: Date, tipoEnte: string};

export interface OrganizzaizoneBean
{
    id                          : number;
    enteId                      : number;
    tipoOrganizzazione          : TipoOrganizzazione;
    tipoOrganizzazioneSpecifica : TipoOrganizzazioneSpecifica;
    codiceCivilia               : string;
    riferimentoOrganizzazione   : number;
    dataCreazione               : Date;
    dataTermine                 : Date;
    denominazione               : string;
    enabled                     : boolean;        
    dataScadenzaAbilitazione    : Date;       
}

export interface OrganizzaizoneSearchBean extends Paging
{
    enteId                     : number;
    tipoOrganizzazione         : TipoOrganizzazione;
    tipoOrganizzazioneSpecifica: TipoOrganizzazioneSpecifica;
    dataCreazioneDa            : Date;
    dataCreazioneA             : Date;
    dataTermineDa              : Date;
    dataTermineA               : Date;
    denominazione              : string;
    enabled                    : boolean;    
    
    sortBy                     : string;
	sortType                   : "ASC"|"DESC";
}

export interface OrganizzazioneExtendedBean extends OrganizzaizoneBean
{
    enti                     : Array<EnteSummary>;
}

export interface OrganizzazioneAbilitazioneBean
{
    applicazioneId              : number;
    paesaggioOrganizzazioneId   : number;
    dataCreazione               : Date;
    dataTermine                 : Date;
}

