import { Component, Input, OnInit, forwardRef, Output, EventEmitter } from '@angular/core';

import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-checkbox-field',
  template: `
    <div class="form-group checkbox">
      <label *ngIf="label">{{ label }} </label>
      <div class="dflex">
       <p-checkbox
        *ngIf="!isSingle"
        [name]="name"
        [value]="simpleCheckboxValue?simpleCheckboxValue:checkboxValue"
        [(ngModel)]="value"
        [label]="checkboxLabel"
        [disabled]="disabled"
        (onChange)="changeBind(value)"
        [ngStyle]="{'width': 'fit-content'}"
        (click)="change()"
        [ngClass]="{'is-invalid': validation === true && errors }">
       </p-checkbox>
       <p-checkbox
        *ngIf="isSingle"
        [name]="name"
        [(ngModel)]="value"
        [label]="checkboxLabel"
        [disabled]="disabled"
        binary="true"
        (onChange)="changeBind(value)"
        (click)="change()"
        [ngClass]="{'is-invalid': validation === true && errors }">
       </p-checkbox>
       <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip>
      </div>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [`
    p-checkbox {
      display: flex;
      align-items: flex-start;
    }
  `],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CheckboxFieldComponent),
      multi: true
    }
  ]
})

export class CheckboxFieldComponent extends BaseControlValueAccessor<string> {

  @Input() checkboxValue: any[];
  @Input() simpleCheckboxValue?: any;//nel caso in cui voglio il modello con un valore di tipo string o int
  @Input() checkboxLabel: string;
  @Input() isSingle = false;
  @Input() disabled = false;
  @Output("on_change") on_change: EventEmitter<string> = new EventEmitter<string>();
  constructor() {
    super();
  }

  // funzione per prendere il valore della checkbox senza doverla mettere in un form
  public change(): void {
    this.on_change.emit(this.value);
  }

}
