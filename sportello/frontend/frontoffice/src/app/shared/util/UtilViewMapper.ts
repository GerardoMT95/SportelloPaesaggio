import { FormArray, FormControl, FormGroup } from '@angular/forms';


export class ViewMapper
{
    /**
     * 
     * @param formGroup 
     * @param objectToMap 
     * @param builders 
     */
    public static mapObjectToForm(formGroup: FormGroup, objectToMap: any, builders?: { [id: string]: () => FormGroup })
    {
        if (!objectToMap) return;
        Object.keys(objectToMap).forEach(
            (propertyName) =>
            {
                let control = formGroup.get(propertyName);
                if (!control)
                {
//                    console.log('Non trovo il corrispettivo form control per la property :' + propertyName);
                }
                if (control instanceof FormControl)
                {
                    let valueToSet=objectToMap[propertyName];
                    (formGroup.get(propertyName) as FormControl).setValue(valueToSet);
                }
                if (control instanceof FormGroup)
                {
//                    console.log("Chiamo me stesso per ", propertyName);
                    this.mapObjectToForm((formGroup.get(propertyName) as FormGroup), objectToMap[propertyName]);
                }
                if (control instanceof FormArray)
                {
                    const arrValues: any[] = objectToMap[propertyName];
                    if(arrValues)
                    {
                        arrValues.forEach(
                            (value) =>
                            {
                                if (builders && builders[propertyName])
                                {
                                    const fg = builders[propertyName]();
                                    (control as FormArray).push(fg);
//                                    console.log("Chiamo me stesso per ", propertyName);
                                    this.mapObjectToForm(fg, objectToMap[propertyName]);
                                }
                            }
                        )
                    }
                }
            }
        );

    }


}

