import { ControlValueAccessor, ValidationErrors } from '@angular/forms';
import { Input } from '@angular/core';

export class BaseControlValueAccessor<T> implements ControlValueAccessor
{
  @Input("name") name = '';
  @Input("label") label = '';
  @Input("tooltip") tooltip = '';
  @Input("placeholder") placeholder = '';
  @Input("translate") translate = true;
  @Input("errors") errors: ValidationErrors = null;
  @Input("validation") validation: boolean = false;

  value: T = null;
  disabled: boolean = false;
  onChange: Function = (args: T) => {}; 
  onTouched: Function = (_?: any) => {};

  constructor() { }

  //Override dei metodi descritti nell'interfaccia ControlValueAccessor
  public writeValue(value: T): void { this.value = value; }
  public registerOnChange(fn: any): void { this.onChange = fn; }
  public registerOnTouched(fn: any): void { this.onTouched = fn; }
  public setDisabledState?(isDisabled: boolean): void { this.disabled = isDisabled; }

  //metodi di utility
  public changeBind(value: T): void
  {
    this.value = value;
    this._callTouchAndChange();
  }
  protected _callTouchAndChange(): void
  {
    if (this.onChange)
      this.onChange(this.value);
    if (this.onTouched)
      this.onTouched();
  }

  public onTouchedDate (obj: any): void {
    this.onChange(this.value);

  }
}
