import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { ValidatorFn } from '@angular/forms';

export class TableConfig {
  field?: string;
  header?: string;
  type?: string; //hidden, date-picker, text-field datetime date optionWithColor(in optionSelect si aspetta value label e in linked il colore) , currency
  sortDirection?: boolean;
  width?: number;
  optionSelect?:SelectOption[];
  innerHtml?:string;
  css?:string;
  validators?: ValidatorFn[];
}

export class SortHeader {
  field: string = '';
  direction: '-' | '' = '';
}
