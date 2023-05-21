import { Component, OnInit, forwardRef, Input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';
import { CONST } from '../../constants';

@Component({
  selector: 'app-date-picker-field',
  template: `
  <div class="form-group">
    <label *ngIf="label">{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
    <div class="input-group flex-nowrap" >
        <div *ngIf="prepend" class="input-group-prepend" >
          <span class="input-group-text">{{ prepend }}</span>
        </div>
      <p-calendar
      [(ngModel)]="value"
      [disabled]="disabled"
      appendTo="body"
      yearNavigator="true"
      [yearRange]="yearRange"
      [maxDate]="maxDate"
      [minDate]="minDate"
      [defaultDate]="defaultDate"
      [inputStyleClass]="'form-control'"
      class="form-control nop"
      (onSelect)="changeBind($event)"
      (onClearClick)="changeBind(null)"
      [locale]="it"
s      [ngClass]="{'is-invalid': validation === true && errors }"
      dateFormat="{{'generic.dateFormatCalendar'|translate}}"
      showButtonBar="true"></p-calendar>
      <div class="input-group-append">
        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
      </div>
    </div>  
    <app-input-error [errors]="errors" [validation]="validation"></app-input-error>
  </div> 
  `,
  //[style]="{'width':'100%', 'display': 'flex', 'border-color': '#CED4DA'}"
  //[inputStyle]="{'width':'100%', 'display': 'flex', 'border': '1px solid', 'border-color': '#CED4DA', 'border-top-left-radius': '3px', 'border-bottom-left-radius': '3px'}"
  styles: [`
    .flex-1 {
      flex: 1;
    }
  `],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DatePickerFieldComponent),
      multi: true
    }
  ]
})
export class DatePickerFieldComponent extends BaseControlValueAccessor<Date> {

  @Input() prepend: string;
  @Input() yearRange:string="2000:2030";
  @Input() maxDate:Date=null;
  @Input() minDate:Date=null;
  @Input() defaultDate:Date=null;
  public it: any = CONST.IT;


  constructor() {
    super();
   }

   public writeValue(obj: any): void {
    if (typeof obj === 'string') {
      this.value = obj && obj.length > 0? new Date(obj): null;
    } else {
      this.value = obj;
    }      
  }

}
