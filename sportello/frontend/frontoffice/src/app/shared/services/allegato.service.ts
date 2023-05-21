import { BaseResponse } from '../components/model/base-response';
import { CONST } from '../constants';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Allegato, RequestAllegato } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class AllegatoService {

  constructor(
    private http: HttpClient
  ) { }

  /**
   * a partire dal blob simula una response di tipo file in modo da far aprire la finestra del
   * salvataggio file al browser
   */
  downloadElemento(blob: Blob, fileName: string,headers?:HttpHeaders): void {
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
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      window.navigator.msSaveOrOpenBlob(blob, filename);
    } else {
      var link = document.createElement("a");
      if (link.download !== undefined) {
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
   * Effettua download di un documento
   * 
   * @param params 
   * @param url 
   */
  private download(params: HttpParams, url: string): Observable<HttpResponse<Blob>> {
    return this.http.get(CONST.WEB_RESOURCE_BASE_URL + url, {
      params: params,
      observe: 'response',
      responseType: 'blob',
    })
  }



  /**
   * 
   * @param file 
   * @param praticaId 
   * @param referenteId 
   * @returns 
   */
  uploadAllegatoDocumentoReferente(file: File, praticaId: string, referenteId: number): Promise<BaseResponse<Allegato>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    let allegatoDocDto = new RequestAllegato();
    allegatoDocDto.praticaId = praticaId;
    allegatoDocDto.referenteId = referenteId;
    formData.append('req', new Blob([JSON.stringify(allegatoDocDto)], { type: "application/json" }));
    return this.uploadAllegatoDocumento(formData, '/allegati/upload_documento_referente.pjson')
  }

  /**
   * Upload allegato
   * 
   * @param input 
   * @param url 
   */
  uploadAllegatoDocumento(input: FormData, url: string): Promise<BaseResponse<Allegato>> {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  getAllegatoDocumentoMetadata(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>> {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  eliminaDocumentoReferente(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>> {
    return this.http.post<BaseResponse<Allegato>>(CONST.WEB_RESOURCE_BASE_URL + url, input)
      .toPromise().then(data => data);
  }

  eliminaDocumento(input: RequestAllegato, url: string): Promise<BaseResponse<Allegato>> {
    return this.eliminaDocumentoReferente(input,url);
  }

  downloadAllegatoFascicolo(idAllegato: string,idPratica:string, url: string): Observable<HttpResponse<Blob>> {
    let params=new HttpParams().set('idAllegato', idAllegato);
    params=params.append('idPratica',idPratica);
    return this.download(params, url);
    //return this.download(new HttpParams().set('idAllegato', idAllegato), url);
  }
  /**
   * Download Allegato
   * 
   * @param id 
   * @param codicePratica 
   * @param url 
   */
  downloadAllegato(id: number, codicePratica: string, url: string): Observable<HttpResponse<Blob>> {
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
  downloadPdfRichiesta(codicePratica: string, url: string): Observable<HttpResponse<Blob>> {
    return this.download(new HttpParams().set('codicePratica', codicePratica), url);
  }

  /**
   * Delete allegato
   * 
   * @param id 
   * @param codicePratica
   * @param url 
   */
  deleteAllegato(id: string, codicePratica: string, url: string): Promise<HttpResponse<BaseResponse<any>>> {
    return this.http.get<BaseResponse<any>>(CONST.WEB_RESOURCE_BASE_URL + url, {
      params: {
        'codicePratica': codicePratica,
        'id': id
      },
      observe: 'response'
    }).toPromise().then(data => data);
  }

  refreshTabellaAllegati(codicePratica: string, idTipoAllegato: string, url: string): Promise<HttpResponse<BaseResponse<any>>> {
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
    return this.download(new HttpParams().set("idAllegato", idAllegato), "/allegati/download.pjson");
  }*/

  public cancellaFile(idAllegato: string, idPratica: string): Observable<BaseResponse<number>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/allegati/delete_allegato.pjson";
    let req = new RequestAllegato();
    req.allegatoId = idAllegato;
    req.praticaId = idPratica;
    return this.http.post<BaseResponse<number>>(url, req);
  }

  public cancellaFileIntegrazione(idAllegato: string, idPratica: string,idIntegrazione:number): Observable<BaseResponse<number>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/allegati/delete_allegato.pjson";
    let req = new RequestAllegato();
    req.allegatoId = idAllegato;
    req.praticaId = idPratica;
    req.integrazioneId = idIntegrazione;
    return this.http.post<BaseResponse<number>>(url, req);
  }

  public downloadManuale(): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams(), "/public/downloadManualeSportello.pjson");
  }

  /**
   * download dei template pubblici
   * @param codiceTemplate 
   * @returns 
   */
  public downloadTemplateByCodice(codiceTemplate:string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams(), `/public/downloadTemplateByCodice/${codiceTemplate}`);
  }

/**
 * 
 * @param input calcola l'hash di applicazione sul file
 * @returns 
 */
 public calcolaHash(input: FormData): Observable<BaseResponse<string>> {
  return this.http.post<BaseResponse<string>>(`${CONST.WEB_RESOURCE_BASE_URL}/allegati/calcola-hash`, input);
}

}
