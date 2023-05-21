import { Component, forwardRef, Input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';


@Component({
  selector: 'app-textarea-field',
  template: `
    <div class="form-group">
      <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <textarea
        class="form-control" type="number" [rows]="rows"
        [name]="name" [placeholder]="placeholder"
        [value]="value" [disabled]="disabled"
        (input)="changeBind($event.target.value)" (blur)="onTouched()"
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
