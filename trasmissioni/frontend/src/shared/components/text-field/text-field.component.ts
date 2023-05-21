import { Component, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-text-field',
  template: `
    <div class="form-group">
      <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <input
        *ngIf="!prepend"
        class="form-control"
        type="{{ type }}"
        [name]="name"
        [placeholder]="placeholder"
        [value]="value"
        [disabled]="disabled"
        [maxLength]="maxlength"
        (input)="changeBind($event.target.value)"
        [ngClass]="{'is-invalid': validation === true && errors, 'uppercase': uppercase }">
        <div class="input-group" *ngIf="prepend">
          <div class="input-group-prepend">
            <span class="input-group-text">{{ prepend }}</span>
          </div>
          <input
            class="form-control"
            type="{{ type }}"
            [name]="name"
            [placeholder]="placeholder"
            [value]="value"
            [disabled]="disabled"
            [maxLength]="maxlength"
            (input)="changeBind($event.target.value)"
            [ngClass]="{'is-invalid': validation === true && errors }">
        </div>
        <app-input-error [errors]="errors" [validation]="validation">
        </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextFieldComponent),
      multi: true
    }
  ]
})
export class TextFieldComponent extends BaseControlValueAccessor<string>
{
  @Input() uppercase: boolean = false;
  @Input() prepend: string;
  @Input() type = 'text';
  @Input() maxlength:string = "100"; //default a 100

  @Output("change") onChangeEvt: EventEmitter<string> = new EventEmitter<string>();

  constructor() { super(); }

  public writeValue(value: string): void { this.value = value ? value : ""; }

  public changeBind(value: string): void
  {
    if(this.uppercase)
      value = value.toUpperCase();
    super.changeBind(value);
    this.onChangeEvt.emit(value);
  }

}
