import { Fascicolo } from 'src/app/shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { MenuItem } from 'primeng/primeng';

export class FascicoloState {
    meta: { [key: string]: SelectOption[]};
    casella: { [key: string]: Partial<SelectOption[]>};
    pptr: any;
    fascicolo: Fascicolo;
    fascicoli: Fascicolo[] = [];
    filtered: Fascicolo[] = [];
    disabled: boolean;
    breadcrumbs: MenuItem[];
}
