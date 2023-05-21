import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { CONST } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class AccettaPrivacyService {

  constructor(private http: HttpClient) { }


  public userHaveToAccept(): Observable<BaseResponse<boolean>>{
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/privacyUserHaveToAccept.pjson");
  }
  
  public testoPrivacy(): Observable<BaseResponse<string>>{
    return this.http.get<BaseResponse<string>>(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/testo-privacy.pjson");
  }
  
  public accettaPrivacy(): Observable<BaseResponse<string>>{
    return this.http.get<BaseResponse<string>>(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/privacyAccept.pjson");
  }
}
