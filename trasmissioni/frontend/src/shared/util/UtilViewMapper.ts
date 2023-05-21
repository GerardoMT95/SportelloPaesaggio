import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { ReturnStatement } from '@angular/compiler';


export class ViewMapper {


    public static mapObjectToForm(formGroup: FormGroup, objectToMap: any, builders?: { [id: string]: () => FormGroup }) {
        if (!objectToMap) return;
        Object.keys(objectToMap).forEach(
            (propertyName) => {
                let control = formGroup.get(propertyName);
                if (!control) {
                    console.log('Non trovo il corrispettivo form control per la property :' + propertyName);
                }
                if (control instanceof FormControl) {
                    (formGroup.get(propertyName) as FormControl).setValue(objectToMap[propertyName]);
                }
                if (control instanceof FormGroup) {
                    this.mapObjectToForm((formGroup.get(propertyName) as FormGroup), objectToMap[propertyName]);
                }
                if (control instanceof FormArray) {
                    const arrValues: any[] = objectToMap[propertyName];
                    arrValues.forEach(
                        (value) => {
                            if (builders && builders[propertyName]) {
                                const fg = builders[propertyName]();
                                (control as FormArray).push(fg);
                                this.mapObjectToForm(fg, objectToMap[propertyName]);
                            }
                        }
                    )
                }



            }
        );

    }

    

}
/**
 * 
 * @param data oggetto le cui chiavi verranno aggiunte in minuscolo es data.COMUNE => data.comune
 */
export function attributesLower(data:any){
    if(typeof(data) === 'object'){
        let keys=Object.keys(data);
        keys.forEach(key=>{data[key.toLowerCase()]=data[key];})
    }
}

