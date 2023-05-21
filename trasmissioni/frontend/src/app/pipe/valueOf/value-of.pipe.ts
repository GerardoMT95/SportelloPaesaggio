import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'valueOf'
})
export class ValueOfPipe implements PipeTransform
{
  transform(value: any, list?: any[], valueKey?: string, displayKey?: string): string 
  {
    let display: string = value;
    if(list && list instanceof Array)
    {
      if (!valueKey)
        valueKey = "value";
      if (!displayKey)
        displayKey = "label";

      let data = list.find(f => f[valueKey] === value);
      if (data)
      {
        display = data[displayKey];
      }
    }
    return display;
  }

}
