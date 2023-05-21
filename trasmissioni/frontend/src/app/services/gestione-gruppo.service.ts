import { PlainTypeStringId, GruppiRuoliDTO } from './../../shared/models/plain-type-string-id.model';
import { CONST } from './../../shared/constants';
import { BaseResponse } from './../components/model/base-response';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GestioneGruppoService {

  constructor(private http: HttpClient) { }


  public readGruppi(): Observable<BaseResponse<GruppiRuoliDTO[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/autorita-procedurale-nuovo";
    return this.http.get<BaseResponse<GruppiRuoliDTO[]>>(url);
  }

  public controllaGruppo(gruppoScelto: string): Observable<BaseResponse<any>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/checkGruppo";
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.append('codiceGruppo', gruppoScelto);
    return this.http.get<BaseResponse<any>>(url, { params: httpParams });
  }

}
