import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { ReturnStatement } from '@angular/compiler';


export class ViewMapper {


    public static mapObjectToForm(formGroup: FormGroup, objectToMap: any, builders?: { [id: string]: Function}) {
        if (!objectToMap) return;
        Object.keys(objectToMap).forEach(
            (propertyName) =>{
                let control = formGroup.get(propertyName);
                if (!control) {
                    //console.log('Ne postoji form controla za :' + propertyName);
                }
                if (control instanceof FormControl) {
                        (formGroup.get(propertyName) as FormControl).setValue(objectToMap[propertyName]);     
                }
                if (control instanceof FormGroup) {
                    this.mapObjectToForm((formGroup.get(propertyName) as FormGroup), objectToMap[propertyName]);     
            } 
                // if (control instanceof FormArray) {
                //     const arrValues: any[] = objectToMap[propertyName];
                //     arrValues.forEach(
                //         (value) => {
                //             if (builders && builders[propertyName]){
                //                 const fg = builders[propertyName]();
                //                 (control as FormArray).push(fg);
                //                 this.mapObjectToForm(fg, objectToMap[propertyName]);
                //             }
                //         }
                //     )
                // }
              
               

            }
        );

    }

}