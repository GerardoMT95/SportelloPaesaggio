import { HttpHeaders, HttpParams } from '@angular/common/http';
import { CONST } from 'src/app/shared/constants';
import { TemplateDestinatarioDTO } from 'src/app/shared/models/models';
import { DestinatarioComunicazioneDTO } from '../../funzionalita-amministratore-applicazione/models/admin-functions.models';
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
 * Metodo per mappare un oggetto (contenente possibilmente i filtri di ricerca da applicare)
 * in un httpParams in modo generico.
 * @param filters Filtri di ricerca da inserire nell'httpParams
 * @param fnMap mappa chiave -> funzione da usare per mappare il valore
 */
export function populateHttpParams(filters: any, fnMap?: {[key: string]: (value: any) => any}): HttpParams
{
    let httpParams: HttpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
        //se ho predisposto un metodo per mappare l'oggetto questo viene usato
        //altrimenti il valore viene inserimento così com'è
        let value: any = filters[key];
        if (fnMap && fnMap[key]) 
            value = fnMap[key](filters[key]);
        if(value || value instanceof Boolean)
        {
            if(value instanceof Array)
                value.forEach(element => httpParams = httpParams.append(key, element.toString()));
            else if(value instanceof Date)
                httpParams = httpParams.append(key, value.toDateString());
            else
                httpParams = httpParams.append(key, value.toString());
        }  
    });
    return httpParams;
}

export function translateTypeProcedimento(id: number): string
{
    let typeProcedimento = CONST.mappingTipiProcedimento;
    
    if(id <= 6)
        return typeProcedimento[id - 1].description;
    else 
        return null;
}

/**
 * @description Trasforma un array di TemplateDestinatarioDTO in un array di DestinatarioComunicazioneDTO
 * preoccupandosi di veriticalizzare pec e mail
 * @param value 
 */
export function translateTemplateDestToDestCom(value: TemplateDestinatarioDTO[]): DestinatarioComunicazioneDTO[]
{
    let array: DestinatarioComunicazioneDTO[] = [];
    if(value)
    {
        value.forEach(v =>
        {
            if(v.email && v.email != "")
            {
                let tmp: DestinatarioComunicazioneDTO = {nome: v.denominazione, email: v.email, pec: false, tipo: v.tipo};
                array.push(tmp);
            }
            if (v.pec && v.pec != "")
            {
                let tmp: DestinatarioComunicazioneDTO = { nome: v.denominazione, email: v.pec, pec: true, tipo: v.tipo };
                array.push(tmp);
            }
        });
    }
    return array;
}