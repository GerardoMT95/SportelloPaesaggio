import { EnteCss } from './../components/model/model';
import { BaseResponse } from '../components/model/base-response';
import { CONST } from '../constants';
import { LoggedUser } from '../components/model/logged-user';
import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { LocalSessionServiceService } from './local-session-service.service';
import { Observable, ReplaySubject } from 'rxjs';
import { RefreshSession } from './refreshsession.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService
{

  public userObservable: EventEmitter<LoggedUser> = new EventEmitter();
  public logoutObservable: EventEmitter<number> = new EventEmitter();
  private userSubject = new ReplaySubject<LoggedUser>(1);
  private hasUser = false;
  private roles: string[];
  public enteEmitter: EventEmitter<EnteCss> = new EventEmitter<EnteCss>();  

  constructor(
    private lss: LocalSessionServiceService,
    private http: HttpClient,
    private refreshSession: RefreshSession
  ) { }

  public emitUser(): void
  {
    this.userObservable.emit(this.getUser());
  }

  public isLogged(): boolean
  {
    if (!this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
    {
      return false;
    } else
    {
      return true;
    }
  }

  getRolesMenu(): Promise<BaseResponse<string[]>>
  {
    return this.http.get<BaseResponse<string[]>>(CONST.WEB_RESOURCE_BASE_URL + "/logged/user/roles.pjson")
      .toPromise().then(data =>
      {
        if (data.payload != null)
          this.roles = data.payload;
        return data
      });
  }

  public getUser(): LoggedUser
  {
    if (this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
      return this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY);
    return null;
  }

  public getObservableUser(): Observable<LoggedUser>
  {
    if (this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
    {
      this.fetchUser();
    }
    return this.userSubject.asObservable();
  }

  public fetchUser(): void
  {
    let user: LoggedUser = this.getUser();
    // user should contains roles has been granted
    this.hasUser = true;
    this.userSubject.next(user);
    this.userSubject.complete();
  }


  /**
   * Emit for dialog logout event
   */
  public emitDialogSession(): void
  {
    this.logoutObservable.emit(CONST.OPEN_DIAOLOG_LOGOUT);
  }
  /**
   * Emit for refresh session
   */
  public emitRefreshLogoutSession(): void
  {
    this.refreshSession.refresh();
    this.logoutObservable.emit(CONST.REFRESH_SESSION_TIMEOUT);
  }
  /**
   * force logout
   */
  public emitForceLogout(): void
  {
    this.refreshSession.forceLogout();
  }
  /**
 * force logout
 */
  public checkRefresh(): void
  {
    this.refreshSession.checkRefresh();
  }

  public oidcLogout(accesstoken: string): void
  {
    if (!accesstoken)
      return;
    if (CONST.DEVELOPER == false)
    {
      this.http.get(environment.wso2.serverUrl + environment.wso2.logoutUrl
        + "?id_token_hint=" + accesstoken
        + "&post_logout_redirect_uri=" + window.location.origin + "/"
        , { responseType: 'text' }
      ).toPromise()
        .then(data =>
        {
          this.http.post(environment.wso2.serverUrl + environment.wso2.logoutUrl + "?consent=approve"
            , { responseType: 'text' }
          ).toPromise();
        });
    }
  }

  public filteredRole(roles: string[])
  {
    return roles.filter(role => Object.keys(environment.roles).indexOf(role) >= 0);
  }

  public getEnteCss(idEnte:number): Observable<BaseResponse<EnteCss>> {
    return this.http.get<BaseResponse<EnteCss>>(environment.linkFunzioniTrasverali + "/find"
                                               ,{params:{"applicazione":"PAE_PRES_IST","id":idEnte + ""}}
                                               );
  }
}
