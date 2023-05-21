import { Component, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR, ValidationErrors } from '@angular/forms';
import { BaseControlValueAccessor } from 'src/app/shared/BaseControlValueAccessor';
import { isNullOrUndefined } from 'util';
import { ConfigOption, ConfigOptionValue } from './../../../../shared/models/models';

@Component({
  selector: 'app-pptr-contesti-paesaggistici',
  templateUrl: './pptr-contesti-paesaggistici.component.html',
  styleUrls: ['./pptr-contesti-paesaggistici.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PptrContestiPaesaggisticiComponent),
      multi: true
    }
  ]
})
export class PptrContestiPaesaggisticiComponent extends BaseControlValueAccessor<ConfigOptionValue[]>
{
  @Input("data") data: ConfigOption[];
  @Input("disabled") disabled:boolean=false;
  @Output("change") change: EventEmitter<ConfigOptionValue[]> = new EventEmitter<ConfigOptionValue[]>();

  public dummyValues: string[] = [];
  public dummyText: {[key: string]: string} = {};

  constructor() { super(); }

  public writeValue(value: ConfigOptionValue[]): void
  {
    if (!isNullOrUndefined(value))
    {
      this.value = value;
      this.dummyValues = [];
      this.value.forEach(v =>
      {
        this.dummyValues.push(v.value);
        this.dummyText[v.value] = v.text ? v.text : "";
      })
    }
  }

  protected myChangeBindSelected(): void
  {
    this.value.splice(0);
    this.dummyValues.forEach(code => this.value.push({value: code, text: this.dummyText[code] ? this.dummyText[code] : null}));
    this.checkTexts();
    this.change.emit(this.value);
    super._callTouchAndChange();
  }

  protected myChangeBindText(codice: string): void
  {
    this.value.forEach((item, index, array) =>
    {
      if(item.value === codice)
        this.value[index].text = this.dummyText[codice];
    });
    this.change.emit(this.value);
    super._callTouchAndChange();
  }

  protected checkSelection(code: string): boolean { return this.value.some(v => v.value === code); }

  private checkTexts(): void
  {
    Object.keys(this.dummyText).forEach(key =>
    {
      if(!this.value.some(v => v.value === key))
        this.dummyText[key] = "";
    });
  }

  protected getErrorForTextInput(codice: string): ValidationErrors|null
  {
    let errors: ValidationErrors|null = null;
    let textErrors: string[] = this.errors ? this.errors.textRequired : [];
    if (textErrors.includes(codice))
      errors = { textRequired: true };
    return errors;
  }
  
}
