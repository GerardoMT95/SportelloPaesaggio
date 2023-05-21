import { Component, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-editor-field',
  template: `
    <div class="form-group">
      <label>{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <p-editor
        [(ngModel)]="value"
        [readonly]="disabled"
        [style]="{'width':'100%', 'display': 'flex', 'border-color': '#CED4DA', 'height':'200px'}"
        [scrollingContainer]="'.ui-editor-content'"
        (onTextChange)="changeBind($event.htmlValue)"
        (blur)="onTouched()"
        [ngClass]="{'is-invalid': validation === true && errors }">
      </p-editor>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => EditorFieldComponent),
      multi: true
    }
  ]
})
export class EditorFieldComponent extends BaseControlValueAccessor<Date> {

  constructor() {
    super();
   }

}
