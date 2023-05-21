import { BFile } from './../models/allegati.model';
import { Allegato } from './../models/models';
import { AttivitaDaEspletareEnum } from 'src/app/shared/models/models';
import { FormArray, FormGroup } from '@angular/forms';
import { HttpHeaders, HttpParams } from '@angular/common/http';
import { CONST } from '../constants';
import { BaseResponse } from '../components/model/base-response';

function copyObj(leftArg: any, rightArg: any, excluding?: string[]): void
{
    if(rightArg && leftArg)
    {
        Object.keys(rightArg).forEach(key =>
        {
            if(!excluding || !excluding.includes(key))
            {
                leftArg[key] = rightArg[key];
            }
        })
    }
}

/**
 * @description Metodo di utili che permette di creare un oggetto complesso con i valori di un secondo oggetto passato in input al metodo
 * @param rightArg elemento da cui verranno copiati i dati
 * @param excluding eventuali key da escludere
 * @returns nuovo oggetto ottenuto dalla copia di rightArg
 */
export function copyOf(rightArg: any, excluding?: string[]): any
{
    let leftArg: any;
    if (rightArg)
    {
        if(rightArg instanceof Array)
            leftArg = copyOfArray(rightArg, excluding);
        else
        {
            leftArg = {};
            copyObj(leftArg, rightArg, excluding);
        }
    }
    return leftArg;
}


/**
 * @description Metodo che permette di effettuare la copia parziale di un array, è possibile definire
 * l'array di partenza, eventuali campi da escludere durante la copia ed un metodo che indica in quali condizioni
 * non deve essere copiato l'elemento iesimo
 * @param rightArg Array da copiare
 * @param excluding Campi che non si desidera vengano copiati
 * @param fn Funzione che stabilisce se l'iesimo record vada copiato o meno
 * @returns Array copia
 */
export function copyOfArray(rightArg: any[], excluding?: string[], fn?: (arg: any) => boolean): any[]
{
    let leftArg: any[] = [];
    rightArg.forEach((rightItem, index) =>
    {
        if(!fn || fn(rightItem))
        {
            leftArg[index] = {}
            copyObj(leftArg[index], rightItem, excluding);
        }
    });
    return leftArg;
}

/**
 * @description Metodo che permette il download effettivo del file
 * @param blob file
 * @param fileName nome da assegnare al file
 */
export function downloadFile(blob: Blob, fileName: string,headers?:HttpHeaders): void
{
    //retrieval del nome dall'header...
    let filename = fileName;
    if (headers && headers.get('Content-Disposition')) {
        let contentDisposition = headers.get('Content-Disposition');
        let parts = contentDisposition.split(';');
        if (contentDisposition && parts.length > 1) {
            let partF = parts[1].split('filename');
            if (partF && partF.length > 1) {
                let partEqual = partF[1].split('=');
                if (partEqual && partEqual.length > 1)
                    filename = partEqual[1].trim();
            }
        }
    }
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
        window.navigator.msSaveOrOpenBlob(blob, filename);
    }
    else
    {
        var link = document.createElement("a");
        if (link.download !== undefined)
        {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
} 

/**
 * @description Metodo che permette di fare un update value and validity di ogni elemento che lo costituisce
 * @param form 
 */
export function updateAllFormValidity(form: FormGroup|FormArray)
{
    if(form)
    {
        if(form instanceof FormArray)
        {
            let items = form.controls;
            for(let item of items)
                updateAllFormValidity(<FormGroup|FormArray>item);
        }
        else
        {
            let controls = form.controls;
            Object.keys(controls).forEach(key =>
            {
                if(controls[key])
                {
                    if(controls[key] instanceof FormGroup || controls[key] instanceof FormGroup)
                        updateAllFormValidity(<FormGroup|FormArray>controls[key]);
                    else
                        controls[key].updateValueAndValidity();
                }
            });
        }
    }
    //console.log("form after update value and validity: ", form);
}

export function printFormErrors(form: FormGroup, formName: string): void
{
    if(form)
    {
        Object.keys(form.controls).forEach(key =>
        {
            if (form.get(key).errors)
                console.log("Form group: ",formName," - Campo ", key, " Errato: ", form.get(key).errors);
            if (form.get(key) instanceof FormGroup){
                printFormErrors(form.get(key) as FormGroup, key);
            }else if ((form.get(key) instanceof FormArray) && (form.get(key) as FormArray).length>0){
                for (let index = 0; index < (form.get(key) as FormArray).length; index++) {
                    let fArrayElement=(form.get(key) as FormArray).at(index);
                    if(fArrayElement instanceof FormGroup){
                        printFormErrors(fArrayElement as FormGroup, key+'_'+index);    
                    }
                    
                }
            }
                
        });
    }
    
}

export function clearAllValidators(form: FormGroup): void
{
    if(form)
    {
        form.clearValidators();
        form.clearAsyncValidators();
        Object.keys(form).forEach(formControlName =>
        {
            if(form.controls[formControlName] instanceof FormGroup)
                clearAllValidators(form.controls[formControlName] as FormGroup);
            else if ((form.get(formControlName) instanceof FormArray) && (form.get(formControlName) as FormArray).length > 0)
            {
                for (let index = 0; index < (form.get(formControlName) as FormArray).length; index++)
                {
                    let fArrayElement = (form.get(formControlName) as FormArray).at(index);
                    if (fArrayElement instanceof FormGroup)
                        clearAllValidators(fArrayElement as FormGroup);
                }
            }
        })
    }
}

export function isInIstruttoria(stato: AttivitaDaEspletareEnum): boolean
{
    let inIstruttoria = ![AttivitaDaEspletareEnum.COMPILA_DOMANDA, 
                          AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA, 
                          AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI].includes(stato);
    return inIstruttoria;
}

export function toHttpParams(object: any): HttpParams
{
    let params: HttpParams = new HttpParams();
    Object.keys(object).forEach(key =>
    {
        if (object[key])
            params = params.append(key, object[key].toString());
    });
    return params;
}

export function okresponse(payload: any, totalItems?: number): BaseResponse<any>
{
    return {
        payload,
        codiceEsito: CONST.OK,
        descrizioneEsito: "ok",
        numeroOggettiRestituiti: payload instanceof Array ? payload.length : 1,
        numeroTotaleOggetti: totalItems ? totalItems : payload instanceof Array ? payload.length : 1
    };
}
