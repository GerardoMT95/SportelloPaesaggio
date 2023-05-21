import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-autocomplete-field',
  template: `
    <div class="form-group">
      <label>{{ label }} <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip></label>
      <p-autoComplete
        [(ngModel)]="value"
        [suggestions]="suggestions"
        (completeMethod)="search($event)"
        (onSelect)="changeBind($event)"
        (blur)="onTouched()"
        appendTo="body"
        class="form-control flex3"
        [minLength]="2"
        [ngClass]="{'is-invalid': validation === true && errors }">
      </p-autoComplete>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  //[style]="{'width':'100%', 'display': 'flex', 'border-color': '#CED4DA'}"
  //[inputStyle]="{'width':'100%', 'display': 'flex', 'border-color': '#CED4DA'}"
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AutocompleteFieldComponent),
      multi: true
    }
  ]
})
export class AutocompleteFieldComponent extends BaseControlValueAccessor<any>
{
  @Input() suggestions: string[];
  @Output() searchChanged: EventEmitter<string> = new EventEmitter<string>();

  constructor()
  {
    super();
  }

  search(event)
  {
    this.searchChanged.emit(event);
  }

}