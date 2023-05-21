/*import { Component, OnInit, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { BaseControlValueAccessorOld } from "../../BaseControlValueAccessorOld";
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-multiselect-field',
  template: `
    <div class="form-group" [class.checkbox]="!dropdown">
      <label
        >{{ translate ? (label | translate) : label }}
        <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip
      ></label>

      <ng-container *ngIf="dropdown">
        <p-multiSelect
          *ngIf="dropdown"
          [(ngModel)]="value"
          [options]="options"
          [optionLabel]="translate ? (optionLabel | translate) : optionLabel"
          [dataKey]="dataKey"
          [disabled]="disabled"
          [style]="{ width: '100%', 'border-color': '#CED4DA' }"
          (onChange)="onChange($event.value)"
        ></p-multiSelect>
      </ng-container>

      <ng-container *ngIf="!dropdown">
        <ng-container *ngFor="let option of options">
          <div class="checkbox-container">
          <p-checkbox
            [name]="name"
            [value]="option"
            [(ngModel)]="value"
            [disabled]="disabled"
            [label]="translate ? (option.label | translate) : option.label"
            (onChange)="changeBind($event.value)"
            (blur)="onTouched()"
          [ngClass]="{'is-invalid': validation === true && errors }">
          ></p-checkbox>
          <app-tooltip *ngIf="option.tooltip" [message]="option.tooltip"></app-tooltip>
          </div>
          <app-text-field
            *ngIf="option.hasDescription && showDescriptionField(option)"
            [prepend]="('DESCRIPTION_TAB.SPECIFIC' | translate | uppercase) + '*'"
            [(ngModel)]="option.description"
            (change)="onChange(value)"
          >
          </app-text-field>

          <div class="pl-4">
            <app-multiselect-field
              *ngIf="option.hasChildren && showChildOptions(option)"
              [label]="option.childLabel"
              [(ngModel)]="option.childValues"
              [name]="option.childName"
              [options]="option.childOptions"
              [parentOption]="option"
              [dropdown]="false"
              [disabled]="disabled || !padreSelezionato(option.value)"
             >
            </app-multiselect-field>
          </div>
        </ng-container>
      </ng-container>
    </div>
  `,
  styles: [
    `
      p-checkbox{
        display: flex;
      }
      .checkbox-container{
        width: 100%; 
        display: flex;
        margin-bottom: 1rem;
      }
    `
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiselectFieldComponent),
      multi: true
    }
  ]
})
export class MultiselectFieldComponent extends BaseControlValueAccessorOld<any[]> {
  @Input() options: any[];
  @Input() optionLabel: string;
  @Input() dataKey: string | number;
  @Input() dropdown = true;
  @Input() parentOption: any = null;

  constructor() {
    super();
  }

  padreSelezionato(value:any){
    let ret=false;
    this.value.forEach(el=>{if(el.value==value){ret=true;}});
    return ret;
  }

  public writeValue(obj: any[]): void {
    if (!this.dropdown && obj) {
      const selectedValues = obj.map((item) => item.value);
      this.value = this.options.filter((item) => {
        if (!this.parentOption) {
          const i = selectedValues.findIndex((it) => it === item.value);
          if (i >= 0) {
            item.childValues = obj[i].childValues;
          }
        }
        return selectedValues.includes(item.value);
      });
    } else {
      this.value = obj;
    }
  }

  showDescriptionField(option: any) {
    if (!this.value) { return false; }
    return this.value.map((item) => item.value).includes(option.value);
  }

  showChildOptions(option: any) {
    return true;
    // return this.value.map(item => item.value).includes(option.value);
  }
  
}*/

import { Component, OnInit, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { BaseControlValueAccessor } from '../../BaseControlValueAccessor';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-multiselect-field',
  template: `
    <div class="form-group" [class.checkbox]="!dropdown">
      <label>
        {{ translate ? (label | translate) : label }}
        <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip>
      </label>
      <ng-container *ngIf="dropdown">
        <p-multiSelect
          *ngIf="dropdown"
          [(ngModel)]="value"
          [options]="options"
          [optionLabel]="translate ? (optionLabel | translate) : optionLabel"
          [dataKey]="dataKey"
          [disabled]="disabled"
          [style]="{ width: '100%', 'border-color': '#CED4DA' }"
          (onChange)="changeBind($event.value)"
          (blur)="onTouched()"
          [ngClass]="{'is-invalid': validation === true && errors }">
        </p-multiSelect>
      </ng-container>

      <ng-container *ngIf="!dropdown">
        <ng-container *ngFor="let option of options">
          <p-checkbox
            [name]="name"
            [value]="option"
            [(ngModel)]="value"
            [disabled]="disabled"
            [label]="translate ? (option.label | translate) : option.label"
            (onChange)="changeBind(value)"
          (blur)="onTouched()">
          </p-checkbox>

          <app-text-field
            *ngIf="option.hasDescription && showDescriptionField(option)"
            [prepend]="('DESCRIPTION_TAB.SPECIFIC' | translate | uppercase) + '*'"
            [(ngModel)]="option.description"
            (change)="onChange(value)"
            [ngClass]="{'is-invalid': validation === true && errors }">
          </app-text-field>

          <div class="pl-4">
            <app-multiselect-field
              *ngIf="option.hasChildren && showChildOptions(option)"
              [label]="option.childLabel"
              [(ngModel)]="option.childValues"
              [name]="option.childName"
              [options]="option.childOptions"
              [parentOption]="option"
              [dropdown]="false"
              [disabled]="disabled || !padreSelezionato(option.value)"
              [ngClass]="{'is-invalid': validation === true && errors }">
            </app-multiselect-field>
          </div>
        </ng-container>
      </ng-container>
      <app-input-error [errors]="errors" [validation]="validation">
      </app-input-error>
    </div>
  `,
  styles: [
    `
    /*  p-checkbox {
        display: flex;
        width: 100%;
        margin-bottom: 1rem;
      }*/
      p-checkbox {
        display: flex;
        width: 100%;
      }
    `
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiselectFieldComponent),
      multi: true
    }
  ]
})
export class MultiselectFieldComponent extends BaseControlValueAccessor<any[]> {
  @Input() options: any[];
  @Input() optionLabel: string;
  @Input() dataKey: string | number;
  @Input() dropdown = true;
  @Input() parentOption: any = null;

  constructor() {
    super();
  }

  padreSelezionato(value:any){
    let ret=false;
    this.value.forEach(el=>{if(el.value==value){ret=true;}});
    return ret;
  }

  public writeValue(obj: any[]): void {
    if (!this.dropdown && obj) {
      const selectedValues = obj.map((item) => item.value);
      this.value = this.options.filter((item) => {
        if (!this.parentOption) {
          const i = selectedValues.findIndex((it) => it === item.value);
          if (i >= 0) {
            item.childValues = obj[i].childValues;
          }
        }
        return selectedValues.includes(item.value);
      });
    } else {
      this.value = obj;
    }
  }

  showDescriptionField(option: any) {
    if (!this.value) { return false; }
    return this.value.map((item) => item.value).includes(option.value);
  }

  showChildOptions(option: any) {
    return true;
    // return this.value.map(item => item.value).includes(option.value);
  }
}

