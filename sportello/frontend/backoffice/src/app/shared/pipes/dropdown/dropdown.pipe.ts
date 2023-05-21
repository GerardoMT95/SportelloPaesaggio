import { SelectItem } from 'primeng/components/common/selectitem';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dropdown'
})
export class DropdownPipe implements PipeTransform
{
  transform(items: any[], label?: string, value?: string, isDisabled?: (item: any) => boolean): SelectItem[]
  {
    let options: SelectItem[] = [];

    if(items)
    {
      items.forEach(item =>
        options.push(this.converti(item, label, value, isDisabled)));
    }

    return options;
  }

  private converti(item: any, labelKey: string, valueKey: string, isDisabled?: (item: any) => boolean): SelectItem
  {
    let simpleItem: boolean = item instanceof String || typeof(item) === "string" ? true : false;
    let label: string; 
    let value: any; 
    
    if (simpleItem)
    {
      label = item;
      value = item;
    }
    else
    {
      label = labelKey ? item[labelKey] : item["label"];
      value = valueKey ? item[valueKey] : item["value"];
    }
    let disabled: boolean = false;
    if(isDisabled)
      disabled = isDisabled(item);

    const option: SelectItem = {label, value, disabled};
    return option;
  }
}
