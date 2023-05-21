import { IndirizziEnti } from 'src/app/shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { MenuItem } from 'primeng/primeng';

export class IndirizziEntiState {
    indirizziEnte: IndirizziEnti;
    indirizziEnti: IndirizziEnti[] = [];
    filtered: IndirizziEnti[] = [];
    disabled: boolean;
    breadcrumbs: MenuItem[];
  }
