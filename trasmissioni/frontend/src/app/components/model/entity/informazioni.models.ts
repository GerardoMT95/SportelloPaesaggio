import { LocalizzazioneTabDTO } from './localizzazione.models';
import { RichiedenteDTO } from './richiedente.models';
import { FascicoloDTO } from './fascicolo.models';

export class InformazioniDTO extends FascicoloDTO 
{
    richiedente: RichiedenteDTO;
	intervento: number[];  // lista degli id delle opzioni selezionate nel tab "Intervento"
	interventoSelezionati: number[];
	localizzazione: LocalizzazioneTabDTO;
}

export class LineaTemporaleDTO
{
	dataCreazione: Date;
	dataTrasmissione: Date;
	dataCampionamento: Date;
	dataVerifica: Date;
	richiedenteNome: string;
	richiedenteCognome: string;
	ente: string;
	tipoProcedimento: string;
	protocollo: string;
}