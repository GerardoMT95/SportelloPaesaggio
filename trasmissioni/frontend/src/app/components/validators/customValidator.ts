import { AbstractControl, FormArray, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * @description Metodo per effettuare la validazione del tipo date. Sia il limite minimo che il limite massimo
 * sono parametri opzionali, se non viene passato nessuno dei due è come se venisse fatto un nullValidator
 * 
 * @param minDate, limite minimo per la data da validare.
 * @param maxDate, limite massimo per la data da validare.
 */
export function validDate(min?: string | Date | AbstractControl, max?: string | Date | AbstractControl ): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors | null =>
    {
        let minDate: Date = min ? min instanceof Date ? min : min instanceof AbstractControl ? new Date(min.value) : new Date(min) : undefined;
        let maxDate: Date = max ? max instanceof Date ? max : max instanceof AbstractControl ? max.value ? new Date(max.value) : null : new Date(max) : undefined;
        let validator: ValidationErrors = null;
        if (control.value && control.value != '')
        {
            let myDate: Date = new Date(control.value);
            if (maxDate && minDate && (myDate > minDate || myDate < maxDate))
                validator = { dateError: true };
            else if (!maxDate && minDate && myDate < minDate)
                validator = { dateError: true };
            else if(maxDate && !minDate && myDate > maxDate)
                validator = { dateError: true };
        }
        return validator;
    }
}

/**
 * @description Metodo di validazione che fa l'esatto opposto del required, ovvero
 * richiede la mancata esistenza di un campo.
 * 
 * @param control 
 */
export function shouldNotExist(control: AbstractControl): ValidationErrors|null
{
    let validator: ValidationErrors|null = null;
    if(control.value && control.value != '')
        validator = {shouldNotExist: true};
    return validator;
}

/**
 * @description Validatore utile a controllare che uno o più caratteri non siano
 * presenti nell'input
 * 
 * @param substring sottostringa vietata nel test
 */
export function shouldNotInclude(substring: string): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let validator: ValidationErrors|null = null;
        if (control.value && (control.value as string).includes(substring))
            validator = {containsForbidden: true};
        return validator;
    }
}

/**
 * @description Verifica che non ci siano duplicati fra le proprietà
 * 
 * @param fieldSet Form Array in cui si vuole controllare l'univocità
 * @param field Nome del campo del form array che deve essere univoco
 */
export function noValuesDuplicate(fieldSet: FormArray, field: string): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors | null =>
    {
        let validator: ValidationErrors | null = null;
        fieldSet = (control.parent.parent as FormArray);
        if(fieldSet && control)
        {
            if (fieldSet.controls.filter(f => (f as FormGroup).controls[field].value && control.value && (f as FormGroup).controls[field].value.toLowerCase() === control.value.toLowerCase()).length > 1)
            {
                validator = {duplicate: true};
            }
        }   
        return validator;
    }
}

/**
 * @description Verifica, in un form array che sia stato inserito almeno un elemento del campo specificato 
 * @param fieldName nome del campo da controllare
 */
export function atLastOne(fieldName: string): ValidatorFn
{   return (control: FormArray): ValidationErrors|null =>
    {
        let validator: ValidationErrors | null = null;
        if(control && control.controls)
        {
            let index: number = 0, length: number = control.controls.length;
            validator = { noOne: true };
            while(validator && index < length)
            {
                if (control.controls[index].get(fieldName).value && control.controls[index].get(fieldName).value.trim() != "")  validator = null;
                else index++;
            }        
        }
        return validator;
    }
}

/**
 * @description Serve a valutare l'effettiva correttezza dei vallori all'interno del dropdown
 * @param invalidValue segnala quando il menu a tendina assume un valore non voluto
 */
export function dropdownValid(invalidValue: string): ValidatorFn
{
    return (control:AbstractControl): ValidationErrors|null =>
    {
        let validator: ValidationErrors | null = null;
        if(control)
        {
            if(!control.value || control.value.value === invalidValue)
                        validator = {invalidValue: true};
        }
        return validator;
    }
}

/**
 * @description Validazione destinata a verificare il contenuto di un'autocomplete non sia vuoto
 * @param control 
 */
export function autocompleteNonEmpty(control: AbstractControl): ValidationErrors | null
{
    let validator: ValidationErrors | null = null;
    /* console.log('control è undefined: ', !control.value);
    console.log('label è undefined: ', (!control.value.label || control.value.label === ''));
    console.log('value è undefined: ', (!control.value.label || control.value.value === '')); */
    if (!control.value || ((!control.value.label || control.value.label === '') || (!control.value.label || control.value.value === '')))
        validator = { required: true };
    return validator;
}

/**
 * @description Verifica che la stringa sia vuota
 * @param control 
 */
export function notEmptyString(control: AbstractControl): ValidationErrors | null
{
    let validator: ValidationErrors | null = null;
    if (!control.value || control.value === '')
        validator = { shouldExist: true };
    return validator;
}

/**
 * @description Validazione destinata alle dropdown, questa validazione considera il campo required solo
 * se la lista di opzioni non è vuota
 * @param options lista di opzioni fra cui scegliere
 */
export function requiredIfHaveOptions(options: any[]): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let result: ValidationErrors|null = null;
        if(options && options.length > 0 && (!control.value || control.value.length === 0))
            result = {required: true};
        return result;
    }
}

/**
 * @description Verifica che il contenutoo dell'input sia lungo esattamento quanto il parametro passato
 * in input al validatore
 * @param length Lunghezza attesa per il valore
 */
export function requiredLength(length: number): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let result: ValidationErrors|null = null;
        if(control.value && control.value.length != length)
            result = {requiredLength: length};
        return result;
    }
}

/**
 * @description Validatore che rende required un determinato input solamente quando un altro abstractControl
 * (passato in input al metodo) ha un determinato valore. 
 * @param dependency input al quale è legato il required
 * @param expectedValue valore che rende required l'abstractControl a cui è associato questo validator
 */
export function requiredDependsOn(dependency: AbstractControl, expectedValue: any): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let response: ValidationErrors|null = null;
        if (dependency && dependency.value &&
            (((!(dependency.value instanceof Array) && dependency.value === expectedValue)) || dependency.value.includes(expectedValue)) &&
            (!control || !control.value || control.value === "" || control.value.length === 0))
            response = {required: true};
        return response;
    }
}

/**
 * @description Verifica che dalla data del form control a quella attuale siano passati un minimo di anni
 * @param minAge - età minima
 */
export function minAge(minAge: number): ValidatorFn
{
    return (control: AbstractControl): ValidationErrors|null =>
    {
        let errors: ValidationErrors|null = null;
        if(control && control.value && control.value != "")
        {
            let now: Date = new Date();
            let birthday: Date = new Date(control.value);
            if(now.getFullYear() - birthday.getFullYear() === minAge)
            {
                if(now.getMonth() < birthday.getMonth())
                    errors = { minAge: minAge };
                else if(now.getMonth() === birthday.getMonth() &&
                        now.getDate() < birthday.getDate())
                    errors = { minAge: minAge };
            }
            else if(now.getFullYear() - birthday.getFullYear() < minAge)
                errors = { minAge: minAge };
        }
        return errors;
    }
}

