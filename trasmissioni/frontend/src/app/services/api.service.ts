import { HttpClient, HttpResponse } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CONST } from 'src/shared/constants';
import { LoggedUser } from '../components/model/logged-user';
import { Messaggio } from '../components/model/messaggio';
import { BaseResponse } from './../components/model/base-response';
import { LocalSessionServiceService } from './local-session-service.service';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient,
              private router: Router,
              @Inject(LocalSessionServiceService) private lss) { }


  performLogin(token: string): Observable<HttpResponse<BaseResponse<LoggedUser>>> 
  {
    /* /logged/user/info.pjson */
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }

  /**
   * MOCK-ato su profilemanager 
   * a regime va cambiato
   */
  performOauthLogin(): Observable<HttpResponse<BaseResponse<LoggedUser>>> {
    //return this.httpClient.get<any>(CONST.WEB_RESOURCE_PROFILE_MANAGER + '/logged/user/info');
    //return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
    /* /logged/user/info.pjson */
    //return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/auth/logged/user/info');
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }

  /**non utilizzato */
  getLoggedUser() {
    return this.httpClient.get(CONST.WEB_RESOURCE_BASE_URL + '/logged/user/info.pjson');
  }

  getPageView(): Observable<BaseResponse<Array<Messaggio>>> {
    return this.httpClient.get<BaseResponse<Array<Messaggio>>>(CONST.WEB_RESOURCE_BASE_URL + '/rest/protected/recuperamsgs');
  }

  performLogout(): Observable<HttpResponse<any>> {
    return this.httpClient.get<any>(CONST.WEB_RESOURCE_BASE_URL + '/logout',
      {
        observe: 'response'
      }
    );
  }
}