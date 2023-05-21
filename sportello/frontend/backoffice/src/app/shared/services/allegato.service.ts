import { Allegato } from '../components/model/model';
import { BaseResponse } from '../components/model/base-response';
import { CONST } from '../constants';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestAllegato } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class AllegatoService
{

  constructor(private http: HttpClient) { }

  /**
   * Effettua download di un documento
   * 
   * @param params 
   * @param url 
   */
  /* private download(params:HttpParams, url:string):Observable<HttpResponse<Blob>>{
    return this.http.get(CONST.WEB_RESOURCE_BASE_URL + url,{
      params: params,
      observe: 'response',
      responseType: 'blob',
    })
  } */

  /**
   * Upload allegato
   * 
   * @param input 
   * @param url 
   */
  uploadAllegato(input: FormData, url: string): Promise<BaseResponse<Allegato>>
  {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }


  /**
   * Download Allegato
   * 
   * @param id 
   * @param codicePratica 
   * @param url 
   */
  /* downloadAllegato(id:number, codicePratica:string, url:string):Observable<HttpResponse<Blob>>{
    return this.download(new HttpParams().set('codicePratica', codicePratica).set("id", id.toString()), url);
  } */

  /**
   * Download PDF richiesta
   * 
   * @param codicePratica 
   * @param url 
   */
  /* downloadPdfRichiesta(codicePratica:string, url:string):Observable<HttpResponse<Blob>>{
    return this.download(new HttpParams().set('codicePratica', codicePratica), url);
  } */

  /**
   * Delete allegato
   * 
   * @param id 
   * @param codicePratica
   * @param url 
   */
  /* deleteAllegato(id:string, codicePratica:string, url:string):Promise<HttpResponse<BaseResponse<any>>>{
    return this.http.get<BaseResponse<any>>(CONST.WEB_RESOURCE_BASE_URL + url,{
      params: {
        'codicePratica': codicePratica,
        'id': id
      },
      observe: 'response'}).toPromise().then(data => data);
  } */

  /* refreshTabellaAllegati(codicePratica:string,idTipoAllegato:string, url:string):Promise<HttpResponse<BaseResponse<any>>>{
    return this.http.get<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url,{
      params: {
        'codicePratica': codicePratica,
        'idTipoAllegato': idTipoAllegato
      },
      observe: 'response'})
      .toPromise().then(data => data);
  } */


  //da adattare
  downloadElemento(blob: Blob, fileName: string): void
  {
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
      window.navigator.msSaveOrOpenBlob(blob, fileName);
    } else
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
   * Effettua download di un documento
   * 
   * @param params 
   * @param url 
   */
  private download(params: HttpParams, url: string): Observable<HttpResponse<Blob>>
  {
    return this.http.get(CONST.WEB_RESOURCE_BASE_URL + url, {
      params: params,
      observe: 'response',
      responseType: 'blob',
    })
  }

  /**
   * Upload allegato
   * 
   * @param input 
   * @param url 
   */
  uploadAllegatoDocumento(input: FormData, url: string): Promise<BaseResponse<Allegato>>
  {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  getAllegatoDocumentoMetadata(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>>
  {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  eliminaDocumentoReferente(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>>
  {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  eliminaDocumento(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>>
  {
    return this.eliminaDocumentoReferente(input, url);
  }

  downloadAllegatoFascicolo(idAllegato: string,idPratica:string, url: string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams().set('idAllegato', idAllegato).set('idPratica',idPratica), url);
  }
  /**
   * Download Allegato
   * 
   * @param id 
   * @param codicePratica 
   * @param url 
   */
  downloadAllegato(id: number, codicePratica: string, url: string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams().set('codicePratica', codicePratica).set("id", id.toString()), url);
  }

  downloadDocumentoIntegrazione(idPratica: string, idIntegrazione: number): Observable<HttpResponse<Blob>>
  {
    let url = '/allegati/downloadDocumentoIntegrazione.pjson';
    return this.download(new HttpParams().set('idPratica', idPratica).set("idIntegrazione", idIntegrazione.toString()), url);
  }

  /**
   * Download PDF richiesta
   * 
   * @param codicePratica 
   * @param url 
   */
  downloadPdfRichiesta(codicePratica: string, url: string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams().set('codicePratica', codicePratica), url);
  }

  /**
   * Delete allegato
   * 
   * @param id 
   * @param codicePratica
   * @param url 
   */
  deleteAllegato(id: string, codicePratica: string, url: string): Promise<HttpResponse<BaseResponse<any>>>
  {
    return this.http.get<BaseResponse<any>>(CONST.WEB_RESOURCE_BASE_URL + url, {
      params: {
        'codicePratica': codicePratica,
        'id': id
      },
      observe: 'response'
    }).toPromise().then(data => data);
  }

  refreshTabellaAllegati(codicePratica: string, idTipoAllegato: string, url: string): Promise<HttpResponse<BaseResponse<any>>>
  {
    return this.http.get<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, {
      params: {
        'codicePratica': codicePratica,
        'idTipoAllegato': idTipoAllegato
      },
      observe: 'response'
    })
      .toPromise().then(data => data);
  }


  //-----
  /*public downloadFile(idAllegato: string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams().set("idAllegato", idAllegato), "istruttoria/allegati/download.pjson");
  }*/

  public cancellaFile(idAllegato: string, idPratica: string): Observable<BaseResponse<number>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/allegati/delete_allegato.pjson";
    let req = new RequestAllegato();
    req.allegatoId = idAllegato;
    req.praticaId = idPratica;
    return this.http.post<BaseResponse<number>>(url, req);
  }

  public downloadManuale(): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams(), "/public/downloadManualeIstruttoria.pjson");
  }

/**
 * 
 * @param input calcola l'hash di applicazione sul file
 * @returns 
 */
 public calcolaHash(input: FormData): Observable<BaseResponse<string>> {
  return this.http.post<BaseResponse<string>>(`${CONST.WEB_RESOURCE_BASE_URL}/istruttoria/allegati/calcola-hash`, input);
}

}
