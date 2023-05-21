import { Component, OnInit, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-number-field',
  template: `
    <div class="form-group">
      <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <input
        class="form-control"
        type="number"
        [id]="name"
        [name]="name"
        [placeholder]="placeholder"
        [value]="value"
        [disabled]="disabled"
        (input)="changeBind($event.target.value)"
        
        [ngClass]="{'is-invalid': validation === true && errors }">
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => NumberFieldComponent),
      multi: true
    }
  ]
})

export class NumberFieldComponent extends BaseControlValueAccessor<number> {

  constructor() {
    super();
   }

}
