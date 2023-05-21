import { Component, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-password-field',
  template: `
    <div class="form-group">
      <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
        <div class="input-group">
          <input
            class="form-control"
            type="{{ type }}"
            [name]="name"
            [placeholder]="placeholder"
            [value]="value"
            [disabled]="disabled"
            [maxLength]="maxlength"
            (input)="changeBind($event.target.value)"
            (blur)="onTouched()"
            [ngClass]="{'is-invalid': validation === true && errors }">
            <div class="input-group-append">
            <span (click)="switchPassword()" class="input-group-text" *ngIf="type !='password'"><i class ='fa fa-eye-slash'></i></span>
             <span (click)="switchPassword()" style="cursor:pointer;" class="input-group-text" *ngIf="type == 'password'"><i class ='fa fa-eye'></i></span>
          </div>
        </div>
        <app-input-error [errors]="errors" [validation]="validation">
        </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PasswordFieldComponent),
      multi: true
    }
  ]
})
export class PasswordFieldComponent extends BaseControlValueAccessor<string>
{
  @Input() maxlength:string = "100"; //default a 100
  type='password';
  @Output("change") onChangeEvt: EventEmitter<string> = new EventEmitter<string>();

  constructor() { super(); }

  public writeValue(value: string): void { this.value = value ? value : ""; }

  public changeBind(value: string): void
  {
    super.changeBind(value);
    this.onChangeEvt.emit(value);
  }

  switchPassword():void{
    if(this.type=='password'){
      this.type='text';
    }else{
      this.type='password';
    }
    
  }

}
