import { HttpHeaders, HttpParams } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { faWindowRestore } from '@fortawesome/free-regular-svg-icons';
import { FileSizePipe } from 'src/app/pipe/filesize/filesize.pipe';
import { GroupRole, GroupType } from '../model/enum';
import { TemplateDestinatarioDTO } from './../model/entity/admin.models';
import { AllegatoCustomDTO } from './../model/entity/allegato.models';
import { DestinatarioComunicazioneDTO } from './../model/entity/corrispondenza.models';
import { TipoLocalizzazione } from './../model/entity/localizzazione.models';
export function saveFileToLocalStorage(localStorage: Storage, file: AllegatoCustomDTO, tipoFile:string[]|string, idFascicolo: string, sezione: string): void
{
    let objAttach = {};
    let key = 'ATTACH#' + idFascicolo + '#' + sezione + '#' + file.id;
    objAttach['id'] = file.id;
    objAttach['idCms'] = file.idCms,
    objAttach['descrizione'] = tipoFile;
    objAttach['data'] = (new Date(file.dataCaricamento)).toISOString();
    objAttach['nome'] = file.nome;
    objAttach['size'] = file.dimensione;
    objAttach['type'] = file.mimeType;
    objAttach['tipoAllegato'] = tipoFile;
    objAttach['checksum'] = file.checksum;
    localStorage.setItem(key, JSON.stringify(objAttach));
}

/**
 * 
 * @param blob 
 * @param fileName 
 * @param headers  se riesce a fetchare il filename da content-disposition: xxxx;filename=nomefile.ext 
 *                  altrimenti usa fileName
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

export function fromStringToTipoLocalizzazione(arg: string): TipoLocalizzazione
{
    switch(arg)
    {
        case "PTC": return TipoLocalizzazione.PTC;
        case "R": return TipoLocalizzazione.R;
        case "P": return TipoLocalizzazione.P;
        case "CO": return TipoLocalizzazione.CO;
        default: return null;
    }
}

export function logInvalidControls(form: FormGroup, subform?: string)
{
    let formEffettivo: FormGroup = subform ? form.controls[subform] as FormGroup : form;
    Object.keys(formEffettivo.controls).forEach(key =>
    {
        let elem = formEffettivo.controls[key];
        if (elem && elem.invalid)
        {
            if (elem["controls"])
            {
                logInvalidControls(elem as FormGroup);
            }
            else
            {
                console.log(key, " è invalido: ", elem);
            }
        }

    });
}

export function swipe(array: any[], leftIndex: number, rightIndex: number): void
{
    let tmp: any = array[leftIndex];
    array[leftIndex] = array[rightIndex];
    array[rightIndex] = tmp;
}

export function fromGroupToGroupType(codiceGruppo: string): GroupType
{
    let type: GroupType = null;
    if(codiceGruppo)
    {
        if(codiceGruppo !== "ADMIN")
        {
            let startsWith: string = codiceGruppo.split("_")[0];
            switch (startsWith)
            {
                case "ED":
                    type = GroupType.ED;
                    break;
                case "REG":
                    type = GroupType.REG
                    break;
                case "SBAP":
                    type = GroupType.SBAP
                    break;
                case "EPA":
                    type = GroupType.EPA;
                    break;
                case "ETI":
                    type = GroupType.ETI;
                    break;
                case "ADMIN":
                    type = GroupType.ADMIN;
                    break;
            }
        }
        else type = GroupType.ADMIN;
    }
    return type;
}

export function fromGroupToRole(codiceGruppo: string): GroupRole
{
    let type: GroupRole = null;
    if(codiceGruppo)
    {
        if(codiceGruppo !== "ADMIN")
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

/**
 * @description Trasforma un array di TemplateDestinatarioDTO in un array di DestinatarioComunicazioneDTO
 * preoccupandosi di veriticalizzare pec e mail
 * @param value 
 */
/* export function translateTemplateDestToDestCom(value: TemplateDestinatarioDTO[]): DestinatarioComunicazioneDTO[]
{
    let array: DestinatarioComunicazioneDTO[] = [];
    if(value)
    {
        value.forEach(v =>
        {
            if(v.email && v.email != "")
            {
                let tmp: DestinatarioComunicazioneDTO = {nome: v.nome, email: v.email, pec: false, tipo: v.tipo};
                array.push(tmp);
            }
            if (v.pec && v.pec != "")
            {
                let tmp: DestinatarioComunicazioneDTO = { nome: v.nome, email: v.pec, pec: true, tipo: v.tipo };
                array.push(tmp);
            }
        });
    }
    return array;
} */

/**
 * @description Trasforma un array di DestinatarioIstituzionale in un array di DestinatarioComunicazioneDTO
 * preoccupandosi di veriticalizzare pec e mail
 * @param value 
 */
/* export function translateDestIstToDestCom(value: DestinatarioIstituzionaleDto[]): DestinatarioComunicazioneDTO[]
{
    let array: DestinatarioComunicazioneDTO[] = [];
    if (value)
    {
        value.forEach(v =>
        {
            if (v.email && v.email != "")
            {
                let tmp: DestinatarioComunicazioneDTO = { nome: v.nome, email: v.email, isPec: false, tipo: v.tipo };
                array.push(tmp);
            }
            if (v.pec && v.pec != "")
            {
                let tmp: DestinatarioComunicazioneDTO = { nome: v.nome, email: v.pec, isPec: true, tipo: v.tipo };
                array.push(tmp);
            }
        });
    }
    return array;
} */

export function toHttpParams(object: any): HttpParams
{
    let params: HttpParams = new HttpParams();
    Object.keys(object).forEach(key =>
    {
        if(object[key])
         params = params.append(key, object[key].toString());
    });
    return params;
}

function copyObj(leftArg: any, rightArg: any, excluding?: string[]): void
{
    if (rightArg && leftArg)
    {
        Object.keys(rightArg).forEach(key =>
        {
            if (!excluding || !excluding.includes(key))
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
        if (rightArg instanceof Array)
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
        if (!fn || fn(rightItem))
        {
            leftArg[index] = {}
            copyObj(leftArg[index], rightItem, excluding);
        }
    });
    return leftArg;
}


/**
 * 
 * @param type 
 * @param size 
 * @param extensions estensioni ammesse
 * @returns 
 */
export function checkFileSizeTypeFn(type:string,size:number,extensions?:string[]):(file:File)=>string{
    let fnRet=(file:File):string=>{
        if(type && file.type!=type){
           return "Errore: tipo non supportato. Ammessi: " +type;
        }
        if(extensions){
            let extensionOk=false;
            extensions.forEach(es=>{
                if(file.name.toLowerCase().endsWith(es)){
                    extensionOk=true;
            }});
            if(!extensionOk){
                return "Il file non ha estensione tra le ammesse: "+extensions;
            }
        }
        if(file.size>size){
            let pipeTr=new FileSizePipe();
          return "Errore: il file supera dimensione massima ammessa di "+pipeTr.transform(size);
        }
        return null;
    };
    return fnRet;
}