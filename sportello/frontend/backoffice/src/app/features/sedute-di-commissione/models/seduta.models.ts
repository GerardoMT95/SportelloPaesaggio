import { OrderBy } from 'src/app/shared/models/search.models';
import { SedutaDiCommissione } from '../../../shared/models/models';
export type EventType = "REVOCATION"|"ATTACH"|"MODIFY"|"VIEW_TO_EXAMINED"|"VIEW_EXAMINED"|"VIEW";
export interface OperationEventType
{
    selected: SedutaDiCommissione;
    operation: EventType;
}

export class BasicSearch extends OrderBy
{
    constructor()
    {
        super();
    }
}

export class FascicoloInput
{
    codiceFascicolo: string;
    stato: "ESAMINATO"|"DA_ESAMINARE"|null;
}

export class ExtraDataFileCL
{
    fascicoli: string[];
    type: "VERBALE"|"SCHEDA_TECNICA";
    idSeduta: number;
}