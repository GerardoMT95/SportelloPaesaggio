import { Component, forwardRef, Input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-radio-field',
  template: `
    <div class="form-group radio">
      <label *ngIf="label">{{ label|translate }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <div class="radio-group">
        <p-radioButton
          [name]="name"
          [value]="radioValue"
          [(ngModel)]="value"
          [label]="radioLabel"
          [disabled]="disabled"
          (onClick)="changeBind(radioValue)"
          (blur)="onTouched()"
          [ngClass]="{'is-invalid': validation === true && errors }">
        </p-radioButton>
        <app-tooltip *ngIf="radioTooltip" [message]="radioTooltip"></app-tooltip>
      </div>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [
    `label{
      display: block;
    }
    p-radioButton {
      display: flex;
      align-items: flex-start;
    }
    .radio-group {
      display: flex;
    }
  `],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => RadioFieldComponent),
      multi: true
    }
  ]
})

export class RadioFieldComponent extends BaseControlValueAccessor<string> {

  @Input() radioValue: string;
  @Input() radioLabel: string;
  @Input() radioTooltip: string;

  constructor() {
    super();
   }

}
