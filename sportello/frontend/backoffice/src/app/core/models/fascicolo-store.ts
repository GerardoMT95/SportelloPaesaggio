import { MenuItem } from 'primeng/primeng';
import { TemplateComunicazione } from 'src/app/features/gestione-istanza-comunicazioni/model/corrispondenza.models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { Fascicolo } from 'src/app/shared/models/models';
import { Ente, RupGruppoMocked, SedutaDiCommissione } from './../../shared/models/models';

export class FascicoloState 
{
    meta: { [key: string]: SelectOption[]};
    casella: { [key: string]: Partial<SelectOption[]>};
    pptr: any;
    fascicolo: Fascicolo;
    fascicoli: Fascicolo[] = [];
    filtered: Fascicolo[] = [];
    disabled: boolean;
    breadcrumbs: MenuItem[];
    files?: { [key: string]: File };
    serial: { [key: string]: number };
    rup: RupGruppoMocked[];
    rubricaEnte: Ente[];
    rubricaIstituzionale: Ente[];
    seduteDiCommissione: SedutaDiCommissione[];
    /**
     * @todo predisposizione di FascicoloState ma da implementare
     */
    templates: TemplateComunicazione[];
}
