import { Component, forwardRef, Input } from '@angular/core';
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
        <p-header *ngIf="limitato">
                  <span class="ql-formats">
                      <button class="ql-bold" aria-label="Bold"></button>
                      <button class="ql-italic" aria-label="Italic"></button>
                      <button class="ql-underline" aria-label="Underline"></button>
                      <button class="ql-sup" aria-label="Sup"></button>
                  </span>
        </p-header> 
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
  /**
   * se a true abilita solo i tag che sono trattati nelle caselle di testo Jasper con markup (html)
   */
  @Input() limitato:boolean=false;

  constructor() {
    super();
   }

}
