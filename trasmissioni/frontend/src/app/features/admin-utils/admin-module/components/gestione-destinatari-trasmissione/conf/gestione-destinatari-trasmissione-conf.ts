import { TipoOrganizzazione } from 'src/app/features/admin-utils/admin-module/components/censimento-organizzazione/conf/conf-organizzazione';
import { Paging } from 'src/shared/components/rows-number-handler/rows-number-handler.component';
import { TipoOrganizzazioneSpecifica } from './../../censimento-organizzazione/conf/conf-organizzazione';

export interface EmailOrganizzazioneBaseBean
{
	id               			: number;
	email            			: string;
	denominazione    			: string;
	pec              			: boolean;
	organizzazioneId 			: number;
	enteId           			: number;
}

export interface EmailOrganizzazioneBean extends EmailOrganizzazioneBaseBean
{
	denominazioneEnte			: string;
	denominazioneOrganizzazione	: string;
	tipoOrganizzazione			: TipoOrganizzazioneSpecifica;
}

export interface EmailOrganizzazioneSearch extends Paging
{
	id              			: number;
	email           			: string;
	denominazione   			: string;
	pec             			: boolean;
	organizzazioneId			: number;
	enteId          			: number;
	denominazioneEnte			: string;
	denominazioneOrganizzazione	: string;
	tipoOrganizzazione			: Array<TipoOrganizzazioneSpecifica>;
	organizzazione				: TipoOrganizzazione;

	sortBy                     : string;
	sortType                   : "ASC"|"DESC";
}