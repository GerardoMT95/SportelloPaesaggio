import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { PaginatedBaseResponse } from 'src/app/shared/components/model/paginated-base-response';
import { CONST } from 'src/app/shared/constants';
import { UlterioreDocumentazione } from 'src/app/shared/models/models';

@Injectable({
  providedIn: 'root'
})
export class GestioneIstanzaDocumentazioneService {

  constructor(private httpClient: HttpClient) { }

  /* public newAttachment(obj: AllegatoUlterioreDocumentazione): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/documentazione/new-attachment";
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, obj);
  } */

  public retrieveDocumentazioni(idFascicolo: string): Observable<BaseResponse<UlterioreDocumentazione[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/search`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idFascicolo", idFascicolo);
    return this.httpClient.get<BaseResponse<UlterioreDocumentazione[]>>(url, {params: parameters});
  }

  public creaBozza(idFascicolo: string): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/crea-bozza`;
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, idFascicolo);
  }

  public modificaDocumentazione(idFascicolo: string, idDocumentazione: number): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/modifica-documentazione`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idFascicolo", idFascicolo);
    parameters = parameters.append("idDocumentazione", idDocumentazione.toString());
    return this.httpClient.get<BaseResponse<UlterioreDocumentazione>>(url, {params: parameters});
  }

  public eliminaDocumentazione(idFascicolo: string, idDocumentazione: number): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/elimina-documentazione`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idFascicolo", idFascicolo);
    parameters = parameters.append("idDocumentazione", idDocumentazione.toString());
    return this.httpClient.delete<BaseResponse<UlterioreDocumentazione>>(url, {params: parameters});
  }

  public salvaBozza(obj: UlterioreDocumentazione): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/salva-documentazione`;
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, obj);
  } 

  public uploadFile(body: any): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/upload-file`;
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, body);
  }

  public deleteFile(body: any): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/delete-file`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idFascicolo", body.idFascicolo);
    parameters = parameters.append("idDocumentazione", body.container.idDocumentazione.toString());
    parameters = parameters.append("idAllegato", body.container.idAllegato.toString());
    parameters = parameters.append("codiceFascicolo", body.codiceFascicolo.toString());
    return this.httpClient.delete<BaseResponse<UlterioreDocumentazione>>(url, {params: parameters});
  }

  public inviaDocumentazione(obj: FormData): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/invia-documentazione`;
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, obj);
  }

  public inviaDocumentazioneMulti(obj: FormData): Observable<BaseResponse<UlterioreDocumentazione>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/invia-documentazione-multi`;
    return this.httpClient.post<BaseResponse<UlterioreDocumentazione>>(url, obj);
  }

  public ricerca(container: any): Observable<BaseResponse<PaginatedBaseResponse<UlterioreDocumentazione>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/documentazione/search`;
    return this.httpClient.post<BaseResponse<PaginatedBaseResponse<UlterioreDocumentazione>>>(url, container);
  }

}
