import { ControlValueAccessor } from '@angular/forms';
import { Input } from '@angular/core';

export class BaseControlValueAccessorOld<T> implements ControlValueAccessor {
  @Input() name = '';
  @Input() label = '';
  @Input() tooltip = '';
  @Input() placeholder = '';
  @Input() translate = true;
  public disabled = false;
  @Input() smallLabel = '';

  /**
   * Call when value has changed programatically
   */
  public value: T = null;
  public onChange(newVal: T) {}
  public onTouched(_?: any) {}

  /**
   * Model -> View changes
   */
  public writeValue(obj: T): void {
    this.value = obj;
  }
  public registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  public registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  public setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
