import { BaseResponse } from '../components/model/base-response';
//import { environment } from 'src/environments/environment';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalSessionServiceService } from './local-session-service.service';
import { CONST } from 'src/app/shared/constants';
import { Messaggio } from '../components/model/messaggio';
import { LoggedUser } from '../components/model/logged-user';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient,
    private router: Router,
    @Inject(LocalSessionServiceService) private lss) { }


  performLogin(token: string): Observable<HttpResponse<BaseResponse<LoggedUser>>> {
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }
  performOauthLogin(): Observable<HttpResponse<BaseResponse<LoggedUser>>> {
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }
  getLoggedUser() {
    return this.httpClient.get(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }

  getPageView(): Observable<BaseResponse<Array<Messaggio>>> {
    return this.httpClient.get<BaseResponse<Array<Messaggio>>>(CONST.WEB_RESOURCE_BASE_URL + 'rest/protected/recuperamsgs');
  }

  performLogout(): Observable<HttpResponse<any>> {
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logout',
      {
        observe: 'response'
      }
    );
  }
}