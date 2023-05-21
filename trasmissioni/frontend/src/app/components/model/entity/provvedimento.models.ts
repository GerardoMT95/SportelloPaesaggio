import { AllegatoCustomDTO } from './allegato.models';
export class ProvvedimentoTabDTO
{
    esitiPossibili: string[];
    provvedimento: AllegatoCustomDTO;
    provvedimentoPrivato: AllegatoCustomDTO;
    parere: AllegatoCustomDTO;
    parerePrivato: AllegatoCustomDTO;
}