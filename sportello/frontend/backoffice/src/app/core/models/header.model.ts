import { SelectOption } from "src/app/shared/components/select-field/select-field.component";

export class TableConfig {
  field?: string;
  header?: string;
  /**
   * optionWithColor(in optionSelect si aspetta value label e in linked il colore) , 
   * currency, boolean, text,text-field,date,date-picker-field, è gestito a destino dai vari component (building-table, generic-table, etc..)
   */
  type?: string; 
  sortDirection?: boolean;
  width?: number;
  eventId?: any;
  orderable?: boolean = true;
  optionSelect?: SelectOption[];
  validators?: any;
  /**
   * utilizzato per accendere altre azioni custom dipendenti dalla riga es. assegnazione utente
   */
  otherActions?:ActionDataButton[];
}

export class ActionDataButton {
  /**
   * nome dell'azione
   */
  nameAction:string;
  /**
   * callback se attivare l'azione sulla riga corrente
   */
  fnEnabled:(rawData:any,col:TableConfig)=>boolean;
  /**
   *es. fa fa-pencil
   */
  btnIcon:string;
  /**
   * es. btn btn-success
   */
  cssButton:string;
}

/**
 * evento lanciato dalla action selezionata
 */
export class EventActionData {
  nameAction:string;
  rowData:any;
}

export class SortHeader {
  field: string = '';
  direction: '-' | '' = '';
}
