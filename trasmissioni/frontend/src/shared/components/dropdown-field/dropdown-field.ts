import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';

@Component({
  selector: 'app-dropdown-field',
  template: `
    <div class="form-group">
        
        <label *ngIf="label" class="mr-3">{{ label }} 
          <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip>
        </label>
        <p-dropdown [placeholder]="placeholder ? placeholder : '-- Nessun valore inserito --'" [filter]="filter" class="dflex"
            [options]="options" [optionLabel]="optionLabel" [autoWidth]="false" 
            [group]="group" [(ngModel)]="val" [showClear]="true" [disabled]="disabled" 
            [readonly]="disabled" [style]="{ width: '100%' }" (onChange)="changeBind($event.value)">
        </p-dropdown>
        <app-input-error [errors]="errors" [validation]="validation">
        </app-input-error>
    </div>
  `,
  styles: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DropdownFieldComponent),
      multi: true
    }
  ]
})

export class DropdownFieldComponent extends BaseControlValueAccessor<any> 
{
  @Input("options") set _(o: Array<any>) 
  {
    this.options = o;
    this.writeValue(this.value);
  }
  @Input("optionLabel") optionLabel: string="description";
  @Input("key") key: string = null; 
  @Input("filter") filter: boolean = false;
  @Input("group") group: boolean = false;

  @Output("change") change: EventEmitter<any>

  public options: Array<any> = [];
  public val: any = null;

  constructor()
  {
    super();
    this.change = new EventEmitter<any>();
  }

  public writeValue(value: any): void
  {
    let assegna = value;
    if(value != null && this.key && this.options)
      assegna = this.options.filter(p => p[this.key] == value)[0];
    this.val = assegna; 
    super.writeValue(value);
  } 

  public changeBind(value: any): void
  {
    let v = value;
    if(value && this.key != null)
        v = value[this.key];
    super.changeBind(v);
    this.change.emit({value: v, item: value});
  }

}

