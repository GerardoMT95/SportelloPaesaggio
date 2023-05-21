import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { HierarchicalOption } from '../../models/models';

export class HierarchicalFieldValidators
{
    public static required(struct: HierarchicalOption): ValidatorFn
    {
        return (control: AbstractControl): ValidationErrors|null =>
        {
            let validation: ValidationErrors|null = null;
            if(control.value != null)
            {
                let temp = struct;
                while (temp != null)
                {
                    let key: number = null;
                    let flag: boolean = false;
                    temp.options.forEach(item =>
                    {
                        let index = control.value.map(m => m.value).indexOf(item.value);
                        if (index != -1)
                        {
                            if (item.hasText === true && (!control.value[index].text || control.value[index].text === ""))
                                validation = { textRequired: validation && validation.textRequired ? [...validation.textRequired, control.value[index].value] : [control.value[index].value] }
                            key = key ? key : item.key;
                            flag = true;
                        }
                    });
                    if (!flag && temp.options.length > 1)
                    {
                        validation = { valueRequired: temp.options };
                        temp = null;
                    }
                    if (key != null && temp.children.dependency === key)
                        temp = temp.children;
                    else
                        temp = null;
                }
            }
            return validation;
        }
    }
}