import { HttpResponse } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BaseResponse } from './../../../shared/components/model/base-response';
import { CambioOwnershipRequest, CambioOwnershipResponse, Subentro } from './../model/cambio-ownership-model';
import { CONST } from './../../../shared/constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PraticaDelegato } from 'src/app/shared/models/models';

@Injectable({
  providedIn: 'root'
})
export class CambioOwnershipService {

  private static readonly URL_BASE : string = CONST.WEB_RESOURCE_BASE_URL + "/cambio-ownership";
  private static readonly URL_RICERCA : string = CambioOwnershipService.URL_BASE + "/search";
  private static readonly URL_TRASMISSIONE : string = CambioOwnershipService.URL_BASE + "/trasmetti";
  private static readonly URL_SAVE : string = CambioOwnershipService.URL_BASE + "/save";
  private static readonly URL_UPLOAD_DELEGA : string = CambioOwnershipService.URL_BASE + "/uploadDelega";
  private static readonly URL_UPLOAD_SOL_INCARICO : string = CambioOwnershipService.URL_BASE + "/uploadSollevamento";
  private static readonly URL_DELETE_DELEGA : string = CambioOwnershipService.URL_BASE + "/deleteDelega";
  private static readonly URL_DELETE_SOL_INCARICO : string = CambioOwnershipService.URL_BASE + "/deleteSollevamento";
  private static readonly URL_DOWNLOAD_DELEGA : string = CambioOwnershipService.URL_BASE + "/downloadDelega";
  private static readonly URL_DOWNLOAD_SOL_INCARICO : string = CambioOwnershipService.URL_BASE + "/downloadSollevamento";
  private static readonly URL_SOLLEVA_INCARICO : string = CambioOwnershipService.URL_BASE + "/solleva-incarico";

  constructor(private http: HttpClient) { }

  public ricercaCambio(dto: CambioOwnershipRequest):  Observable<BaseResponse<CambioOwnershipResponse>>{
    return this.http.post<BaseResponse<CambioOwnershipResponse>>(CambioOwnershipService.URL_RICERCA, dto);
  }
  public trasmetti(dto: CambioOwnershipRequest):  Observable<BaseResponse<string>>{
    return this.http.post<BaseResponse<string>>(CambioOwnershipService.URL_TRASMISSIONE, dto);
  }
  
  public save(dto: PraticaDelegato, codicePratica:string):  Observable<BaseResponse<boolean>>{
    let params : HttpParams = new HttpParams();
    params = params.set("codicePratica", codicePratica);
    return this.http.post<BaseResponse<boolean>>(CambioOwnershipService.URL_SAVE, dto, {params:params});
  }

  public uploadDelega(formData : FormData):  Observable<BaseResponse<string>>{
    return this.http.post<BaseResponse<string>>(CambioOwnershipService.URL_UPLOAD_DELEGA, formData);
  }

  public uploadSollevamento(formData : FormData):  Observable<BaseResponse<string>>{
    return this.http.post<BaseResponse<string>>(CambioOwnershipService.URL_UPLOAD_SOL_INCARICO, formData);
  }

  public deleteDelega(codicePratica:string):  Observable<BaseResponse<boolean>>{
    let params : HttpParams = new HttpParams();
    params = params.set("codicePratica", codicePratica);
    return this.http.delete<BaseResponse<boolean>>(CambioOwnershipService.URL_DELETE_DELEGA, {params:params});
  }
  
  public deleteSollevamento(codicePratica:string):  Observable<BaseResponse<boolean>>{
    let params : HttpParams = new HttpParams();
    params = params.set("codicePratica", codicePratica);
    return this.http.delete<BaseResponse<boolean>>(CambioOwnershipService.URL_DELETE_SOL_INCARICO, {params:params});
  }

  public downloadDelega(codicePratica:string):Observable<HttpResponse<Blob>>{
    let params : HttpParams = new HttpParams();
    params = params.set("codicePratica", codicePratica);
    return this.http.get(CambioOwnershipService.URL_DOWNLOAD_DELEGA,{
      params: params,
      observe: 'response',
      responseType: 'blob',
    })
  }
  public downloadSollevamento(codicePratica:string):Observable<HttpResponse<Blob>>{
    let params : HttpParams = new HttpParams();
    params = params.set("codicePratica", codicePratica);
    return this.http.get(CambioOwnershipService.URL_DOWNLOAD_SOL_INCARICO,{
      params: params,
      observe: 'response',
      responseType: 'blob',
    })
  }
  public sollevaIncarico(idPratica : String):  Observable<BaseResponse<boolean>>{
    return this.http.get<BaseResponse<boolean>>(CambioOwnershipService.URL_SOLLEVA_INCARICO + "/" + idPratica);
  }
}
