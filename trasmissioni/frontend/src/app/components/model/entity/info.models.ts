import { AllegatoCustomDTO } from './allegato.models';
import { FascicoloDTO, TipoProcedimento } from './fascicolo.models';
import { InterventoTabDTO } from './intervento.models';
import { LocalizzazioneTabDTO } from './localizzazione.models';
import { ProvvedimentoTabDTO } from './provvedimento.models';
import { RichiedenteDTO } from './richiedente.models';


export type UfficiPossibili =
{
    codice: string,
    label: string
}
/**
 * DTO da cui ricevo dati e struttura degli elementi da mostrare
 * nella pagina di dettaglio del fascicolo
 */
export class InformazioniDTO extends FascicoloDTO
{
    allegati: AllegatoCustomDTO[];
    intervento: InterventoTabDTO;
    localizzazione: LocalizzazioneTabDTO;
    provvedimento: ProvvedimentoTabDTO;
    richiedente: RichiedenteDTO;
    tipiProcedimento: TipoProcedimento[];
    uffici: UfficiPossibili[];
    interventoSelezionati?: number[]; //anche per pareri....
    //solo per pareri.... assiene ad 
    dataDelibera?:Date; //utilizzata anche in putt
    deliberaNum?:string;
    oggettoDelibera?:string;
    infoDeliberePrecedenti?:string;
    descrizioneIntervento?:string;
    //vedi o non vedi i tab ulteriore doc e com?
    ultDocEnabled: boolean;
    comunicazioniEnabled: boolean;
    //......................
}