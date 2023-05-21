import { FormBuilder } from "@angular/forms";

/**
 * configurazione campo form
 */
export class PlanFormField {
  field: string;
  label: string;
  required?: boolean;
  validator?: any;
  children?: any;
  isDate?: boolean;
  nestedForm?:PlanFormField[];
  formBuilder?:FormBuilder;

  constructor(field?: string, label?: string)
  {
    this.field = field ? field : null;
    this.label = label ? label : null;
    this.required = false;
    this.validator = null;
    this.children = null;
    this.isDate = false;
  }
}