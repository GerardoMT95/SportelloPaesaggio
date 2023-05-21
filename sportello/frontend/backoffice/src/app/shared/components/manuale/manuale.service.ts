import { HttpClient, HttpParams, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CONST } from "../../constants";
import { BaseResponse } from "../model/base-response";
import { DownloadManualeBean } from "../model/model";

@Injectable({
  providedIn: 'root'
})
export class ManualeService
{
  constructor(private http: HttpClient) { }

  public getDownloadUrl(): Observable<BaseResponse<DownloadManualeBean[]>>
  {
    return this.http.get<BaseResponse<DownloadManualeBean[]>>(CONST.WEB_RESOURCE_BASE_URL + '/downloadManuale/get-download');
  }

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

  public downloadManuale(url: string): Observable<HttpResponse<Blob>>
  {
    return this.download(new HttpParams(), "/downloadManuale/" + url);
  }
}
