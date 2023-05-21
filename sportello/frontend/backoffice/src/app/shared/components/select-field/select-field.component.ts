import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

export interface SelectOption {
  value: string | number;
  description: string;
  linked?:string;
  label?: string;
}

@Component({
  selector: 'app-select-field',
  template: `
    <div class="form-group">
      <label *ngIf="label" class="mr-3">{{ label }} 
        <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <select class="form-control" [id]="name" [name]="name" [value]="value" (onBlur)="onTouched()"
        [disabled]="disabled" (change)="changeBind($event.target.value)" 
        [ngClass]="{ 'is-invalid': validation === true && errors }">
        <!--<option [value]="null" selected disabled hidden> {{placeholder!=''?placeholder:('generic.selezionaunavoce'|translate)}}</option>-->
        <option selected disabled hidden> {{placeholder!=''?placeholder:('generic.selezionaunavoce'|translate)}}</option>
        <option [value]="option.value" *ngFor="let option of options"> {{ option[optionLabel] }} </option>
      </select>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectFieldComponent),
      multi: true
    }
  ]
})

export class SelectFieldComponent extends BaseControlValueAccessor<string | number> 
{
  @Input("options") options: SelectOption[] = [];
  @Input("optionLabel") optionLabel: string ="description";
  @Input("filter") filter: boolean = false;
  @Input("group") group: boolean = false;
  @Output("change") change: EventEmitter<any>
  constructor()
  {
    super();
    this.change = new EventEmitter<any>();
  }

  public changeBind(value: any): void
  {
    super.changeBind(value);
    this.change.emit({value: value});
  }

}

