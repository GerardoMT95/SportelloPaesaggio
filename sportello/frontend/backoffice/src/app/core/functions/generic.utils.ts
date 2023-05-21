import { FileSizePipe } from './../../shared/pipes/filesize/filesize.pipe';
import { FormGroup, FormArray } from '@angular/forms';
import { GroupType, GroupRole } from './../../shared/models/models';
import { BaseResponse } from '../models/basic.models';
import { CONST } from 'src/app/shared/constants';
import { HttpParams } from '@angular/common/http';

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
 * @description metodo di utili che permette di creare un oggetto complesso con i valori di un secondo oggetto passato in input al metodo
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
        {
            leftArg = [];
            rightArg.forEach((rightItem, index) =>
            {
                leftArg[index] = {}
                copyObj(leftArg[index], rightItem, excluding);
            })
        }
        else
        {
            leftArg = {};
            copyObj(leftArg, rightArg, excluding);
        }
    }
    return leftArg;
}

/**
 * @description Metodo che permette il download efffettivo del file
 * @param blob file
 * @param fileName nome da assegnare al file
 */
export function downloadFile(blob: Blob, fileName: string): void
{
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
        window.navigator.msSaveOrOpenBlob(blob, fileName);
    }
    else
    {
        var link = document.createElement("a");
        if (link.download !== undefined)
        {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", fileName);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
} 

/**
 * @description Ottieni il tipo gruppo sulla base di un codice passati in input
 * @param code Codice del gruppo di cui si vuole ricavare il tipo
 */
export function getGroupType(code: string): GroupType|null
{
    let typeString = code.split("_")[0];
    switch(typeString)
    {
        case GroupType.Regione:
            return GroupType.Regione;
        case GroupType.Richiedente:
            return GroupType.Richiedente;
        case GroupType.Soprintendenza:
            return GroupType.Soprintendenza;
        case GroupType.UtentePubblico:
            return GroupType.UtentePubblico;
        case GroupType.EnteDelegato:
            return GroupType.EnteDelegato;
        case GroupType.EnteTerritoriale:
            return GroupType.EnteTerritoriale;
        case GroupType.CommissioneLocale:
            return GroupType.CommissioneLocale;
        case GroupType.Amministratore:
            return GroupType.Amministratore;
        default: return null;
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
    console.log("form after update value and validity: ", form);
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

export function toHttpParams(object: any): HttpParams
{
    let params: HttpParams = new HttpParams();
    Object.keys(object).forEach(key =>
    {
        if (typeof (object[key]) == 'boolean')
            params = params.append(key, object[key] ? 'true' : 'false');
        else if(object[key])
        {
            if (object[key] instanceof Array)
            {
                object[key].forEach((o: any) =>
                {
                    params = params.append(key, o.toString());
                });
            }
            else if (object[key] instanceof Date)
                params = params.append(key, object[key].toDateString());
            else
                params = params.append(key, object[key].toString());
        }
        
    });
    return params;
}

export function fromGroupToGroupType(codiceGruppo: string): GroupType
{
    let type: GroupType = null;
    if (codiceGruppo)
    {
        if (codiceGruppo !== "ADMIN" && codiceGruppo != "RICHIEDENTI")
        {
            let startsWith: string = codiceGruppo.split("_")[0];
            switch (startsWith)
            {
                case "ED":
                    type = GroupType.EnteDelegato;
                    break;
                case "CL":
                    type = GroupType.CommissioneLocale;
                    break;
                case "REG":
                    type = GroupType.Regione
                    break;
                case "SBAP":
                    type = GroupType.Soprintendenza
                    break;
                case "EPA":
                    type = GroupType.EnteParco;
                    break;
                case "ETI":
                    type = GroupType.EnteTerritoriale;
                    break;
                case "ADMIN":
                    type = GroupType.Amministratore;
                    break;
            }
        }
        else type = codiceGruppo === "RICHIEDENTI" ? GroupType.Richiedente : GroupType.Amministratore;
    }
    return type;
}

export function fromGroupToRole(codiceGruppo: string): GroupRole
{
    let type: GroupRole = null;
    if (codiceGruppo)
    {
        if (codiceGruppo !== "ADMIN")
        {
            let endWith: string = codiceGruppo.split("_")[codiceGruppo.split("_").length - 1];
            switch (endWith)
            {
                case "F":
                    type = GroupRole.F;
                    break;
                case "D":
                    type = GroupRole.D
                    break;
                case "A":
                    type = GroupRole.A
                    break;
            }
        }
    }
    return type;
}

export function fromGroupToidOrganizzazione(codiceGruppo: string): number
{
    let idOrganizzazione:number=0;
    if (codiceGruppo)
    {
        if (codiceGruppo !== GroupType.Amministratore && codiceGruppo !== GroupType.Richiedente)
        {
            let parts: string[] = codiceGruppo.split("_");
            if(parts.length==3 && Number.parseInt(parts[1])!=NaN){
                idOrganizzazione=Number.parseInt(parts[1]);
            }
        }
    }
    return idOrganizzazione;
}

export function checkFileSizeTypeFn(type: string, size: number): (file: File) => string
{
    let fnRet = (file: File): string =>
    {
        if (type && file.type != type)
        {
            return "Errore: tipo non supportato. Ammessi: " + type;
        }
        if (file.size > size)
        {
            let pipeTr = new FileSizePipe();
            return "Errore: il file supera dimensione massima ammessa di " + pipeTr.transform(size);
        }
        return null;
    };
    return fnRet;
}