import { TipoAllegato } from './entity/allegato.models';
export class PlainTypeNumericId 
{
  id?: number;
  nome?: string;
  descrizione?: string;
  data?: Date;
  mandatario?: boolean;
  type?: TipoAllegato;
}
