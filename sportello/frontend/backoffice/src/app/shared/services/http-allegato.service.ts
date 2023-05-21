import { CONST } from 'src/app/shared/constants';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { SelectOption } from '../components/select-field/select-field.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseResponse } from 'src/app/core/models/basic.models';

@Injectable({
  providedIn: 'root'
})
export class HttpAllegatoService
{
  private BASE_URL     = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/allegati";
  private URL_DOWNLOAD = this.BASE_URL               + "/download.pjson";
  private URL_DELETE   = this.BASE_URL               + "/delete";

  constructor(private http: HttpClient) { }

  getTipoDocumento(): Observable<SelectOption[]>
  {
    return this.http.get<SelectOption[]>('http://localhost:8080/tipo').pipe(map((response: SelectOption[]) =>
    {
      return response;
    }));
  }

  /**
   * @description Realizzo la chiamata al BE per il download di uno specifico file
   * @param path id alfresco per scaricare il file
   */
  /*public callDownloadAllegato(idAllegato: string): Observable<HttpResponse<Blob>>
  {
    let url: string = this.URL_DOWNLOAD;
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: new HttpParams().set('idAllegato', idAllegato) });
  }
  */
  /**
   * @description Realizzo la chiamata al BE per la cancellazione di uno specifico file
   * @param path 
   */
  public callDeleteAllegato(path: string): Observable<BaseResponse<boolean>>
  {
    let url: string = this.URL_DELETE;
    let params = { params: new HttpParams().set('path', path)};
    return this.http.delete<BaseResponse<boolean>>(url, params);
  }
}
