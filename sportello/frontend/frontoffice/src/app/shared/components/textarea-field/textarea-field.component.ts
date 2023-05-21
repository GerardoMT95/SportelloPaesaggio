import { Component, Input, OnInit, forwardRef } from '@angular/core';

import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { CONST } from '../../constants';

@Component({
  selector: 'app-textarea-field',
  template: `
    <div class="form-group">
      <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <textarea
        class="form-control" type="number" [rows]="rows"
        [name]="name" [placeholder]="placeholder"
        [value]="value" [disabled]="disabled"
        [maxLength]="maxlength"
        (input)="changeBind($event.target.value)" 
        [ngClass]="{'is-invalid': validation === true && errors }">
      </textarea>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextareaFieldComponent),
      multi: true
    }
  ]
})

export class TextareaFieldComponent extends BaseControlValueAccessor<string> {

  @Input() rows = '5';
  @Input() maxlength = CONST.MAX_LENGTH_TEXTAREA;
  constructor()
  {
    super();
  }

  //metodi di utility
  public changeBind(value: string): void
  {
    this.value = value;
    super._callTouchAndChange();
  }
}
