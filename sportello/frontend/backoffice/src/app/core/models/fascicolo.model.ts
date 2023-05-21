import { FormArray } from '@angular/forms';
import { StatoEnum } from 'src/app/shared/models/models';
export class Fascicolo 
{
  codiceFascicolo?: string;
  oggetto?: string;
  tipoProcedimento?: string;
  attivitaDeEspletare?: string;
  displayButton?: boolean;
  allegati: any;
  stato: StatoEnum;
  funzionari: string[];
  rup: string;
}

export class Particella
{
  id:string;
  type:string;
  ente:string;
  foglio:string;
  sezione:string;
  particella:string;
  sub:string;
  codiceEnte:string;
}

export class Localizzazione 
{
  public particelle: FormArray;
  public tipoSelezioneLocalizzazione: string;
  public localizzazioneComuni: string[];
  public localizzazioneProvince: string[];
  public bpImmobiliAreeInteresse: string[];
  public bpPaesaggioRurale: string[];
  public bpParchiRiserve: string[];
}
