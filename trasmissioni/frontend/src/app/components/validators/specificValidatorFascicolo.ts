import { ValidatorFn, AbstractControl, ValidationErrors, FormGroup } from '@angular/forms';
import { TipiEQualificazioniDTO } from './../model/entity/intervento.models';

/**
 * Validazione custom fatta per gli elementi del tab intervento
 * 
 * @param entrySet Lista di elementi fra cui è possibile effettuare una scelta nel tab intervento
 * @param dependesOn valore per cui almeno un elemento del sottogruppo diviene required
 */
export function sottoGruppoInterventoRequired(entrySet: TipiEQualificazioniDTO[], form: FormGroup, dependesOn: number): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let result: ValidationErrors|null = null;
        if(entrySet && (!control.value || control.value === "" || control.value.length === 0))
        {
            let tmp = entrySet.filter(p => p.zona === dependesOn);
            if(tmp)
            {
                tmp.forEach(f =>
                {
                    if (!result && form.controls[f.raggruppamento].value && 
                        ((form.controls[f.raggruppamento].value instanceof Array && form.controls[f.raggruppamento].value.map(m => m.toString()).includes(f.id.toString())) ||
                        form.controls[f.raggruppamento].value.toString() === f.id.toString())) // && form.controls[f.raggruppamento].value != ""
                    {
                        result = { required: f.raggruppamento };
                    } 
                });
            }            
        }
        return result;
    }
}