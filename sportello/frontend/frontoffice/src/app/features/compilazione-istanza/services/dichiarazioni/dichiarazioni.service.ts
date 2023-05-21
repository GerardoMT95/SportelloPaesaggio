import { RequestAllegato } from './../../../../shared/models/models';
import { CONST } from 'src/app/shared/constants';
import { BaseResponse } from './../../../../shared/components/model/base-response';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DichiarazioniService {

  private BASE_URL: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati";
  private UPLOAD: string = this.BASE_URL + "/upload_allegato.pjson";
  //private DOWNLOAD: string = this.BASE_URL + "/download.pjson";
  private DELETE: string = this.BASE_URL + "/delete_allegato.pjson";

  constructor(private httpClient: HttpClient) { }

  public uploadFile(container: any): Observable<BaseResponse<any>> {
    let formData: FormData = new FormData();
    formData.append("req", new Blob([JSON.stringify(container.requestAllegato)], { type: "application/json" }));
    formData.append("file", container.file);
    return this.httpClient.post<BaseResponse<any>>(this.UPLOAD, formData);
  }

  /*
  public downloadFile(idAllegato: string): Observable<HttpResponse<Blob>> {
    let params: HttpParams = new HttpParams();
    params = params.append("idAllegato", idAllegato);
    return this.httpClient.get(this.DOWNLOAD, {
      params: params,
      observe: 'response',
      responseType: 'blob',
    });
  }*/

  public deleteFile(requestAllegato: RequestAllegato): Observable<BaseResponse<number>> {
    return this.httpClient.post<BaseResponse<number>>(this.DELETE, requestAllegato);
  }

}
