import { ComunicazioniTemplate } from 'src/app/shared/models/models';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { MenuItem } from 'primeng/primeng';

export class TestiComunicazioniState {
    testiComunicazione: ComunicazioniTemplate;
    testiComunicazioni: ComunicazioniTemplate[] = [];
    filtered: ComunicazioniTemplate[] = [];
    disabled: boolean;
    breadcrumbs: MenuItem[];
  }
