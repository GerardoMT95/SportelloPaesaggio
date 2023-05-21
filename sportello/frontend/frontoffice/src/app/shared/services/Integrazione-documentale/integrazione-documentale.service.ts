import { SortObj } from './../../models/response-object.model';
import { Observable } from 'rxjs';
import { IntegrazioneDocumentale, Allegati, StatoIntegrazione } from './../../models/models';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { CONST } from './../../constants';
import { Injectable } from '@angular/core';
import { BaseResponse } from '../../components/model/base-response';

@Injectable({
  providedIn: 'root'
})
export class IntegrazioneDocumentaleService
{
  private URL_BASE          = CONST.WEB_RESOURCE_BASE_URL + "/IntegrazioneDocumentale";
  private URL_SAVE          = this.URL_BASE + "/save";
  private URL_UPDATE        = this.URL_BASE + "/update";
  private URL_DELETE        = this.URL_BASE + "/delete";
  private URL_SEARCH        = this.URL_BASE + "/search";  
  private URL_FIND          = this.URL_BASE + "/find";
  private URL_ATTACHMENTS   = this.URL_BASE + "/findAttachments";
  private URL_UPDATE_STATUS = this.URL_BASE + "/updateStatus";
  private URL_DOWNLOAD      = this.URL_BASE + "/download";

  constructor(private http: HttpClient) { }

  public save(entity: IntegrazioneDocumentale): Observable<BaseResponse<IntegrazioneDocumentale>> { return this.http.post<BaseResponse<IntegrazioneDocumentale>>(this.URL_SAVE, entity); }
  public update(entity: IntegrazioneDocumentale): Observable<BaseResponse<number>> {return this.http.put<BaseResponse<number>>(this.URL_UPDATE, entity); }
  
  public delete(idPratica?: string, idIntegrazione?: number): Observable<BaseResponse<number>> 
  { 
    let httpParams: HttpParams = new HttpParams();
    if(idPratica && idPratica != "")
      httpParams = httpParams.append("idPratica", idPratica);
    if(idIntegrazione)
      httpParams = httpParams.append("idIntegrazione", idIntegrazione.toString());
    return this.http.delete<BaseResponse<number>>(this.URL_DELETE, {params: httpParams}); 
  }
  
  public search(idPratica: string, sortBy: string, sortOrder: "ASC" | "DESC"): Observable<BaseResponse<IntegrazioneDocumentale[]>>
  {
    let httpParams = new HttpParams().set("praticaId", idPratica).set("sortBy", sortBy).set("sortType", sortOrder);
    return this.http.get<BaseResponse<IntegrazioneDocumentale[]>>(this.URL_SEARCH, {params: httpParams});
  }

  public findAttachments(idPratica: string, idIntegrazione: number): Observable<BaseResponse<Allegati>>
  {
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.append("idPratica", idPratica);
    httpParams = httpParams.append("idIntegrazione", idIntegrazione.toString());
    return this.http.get<BaseResponse<Allegati>>(this.URL_ATTACHMENTS, { params: httpParams }); 
  }

  public find(idIntegrazione: number): Observable<BaseResponse<IntegrazioneDocumentale>>
  {
    let params = { params: new HttpParams().set("idIntegrazione", idIntegrazione.toString()) };
    return this.http.get<BaseResponse<IntegrazioneDocumentale>>(this.URL_FIND, params);
  }

  public updateStatus(idIntegrazione: number,idPratica:string, stato: StatoIntegrazione): Observable<BaseResponse<number>> 
  {
    let params = {
      params: new HttpParams()
      .set("idIntegrazione", idIntegrazione.toString())
      .set("stato", stato)
      .set("idPratica", idPratica)
  }; 
    return this.http.put<BaseResponse<number>>(this.URL_UPDATE_STATUS, null, params);
  }

  public downloadDocumentoIntegrazione(idIntegrazione: number, idPratica: string, codicePratica: string): Observable<HttpResponse<Blob>>
  {
    let params: HttpParams = new HttpParams().set("idIntegrazione", idIntegrazione.toString())
                                             .set("idPratica", idPratica)
                                             .set("codicePratica", codicePratica);
    return this.http.get(this.URL_DOWNLOAD, {
      params: params,
      observe: 'response',
      responseType: 'blob',
    });
  }
}
