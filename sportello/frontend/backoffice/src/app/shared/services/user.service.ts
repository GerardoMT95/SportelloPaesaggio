import { BaseResponse } from 'src/app/core/models/basic.models';
import { EnteCss } from './../components/model/model';
import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { fromGroupToGroupType, fromGroupToidOrganizzazione, fromGroupToRole } from 'src/app/core/functions/generic.utils';
import { environment } from 'src/environments/environment';
import { GroupRole, GroupType } from '../models/models';
import { CONST } from './../../shared/constants';
import { GruppiRuoli, LoggedUser, GruppiRuoliDTO } from './../components/model/logged-user';
import { LocalSessionServiceService } from './local-session-service.service';
import { RefreshSession } from './refreshsession.service';

const wgk  = LocalSessionServiceService.WORKING_GROUP_KEY;
const rKey = LocalSessionServiceService.RUP_KEY;

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
  public groupChanged: Subject<string> = new Subject<string>();


  constructor(private lss: LocalSessionServiceService,
              private http: HttpClient,
              private refreshSession: RefreshSession) { }

  public emitUser(): void { this.userObservable.emit(this.getUser()); }

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

  /*getRolesMenu(): Promise<BaseResponse<string[]>> {
    return this.http.get<BaseResponse<string[]>>(CONST.WEB_RESOURCE_BASE_URL + "/logged/user/roles.pjson")
      .toPromise().then(data => {
        if (data.payload != null)
          this.roles = data.payload;
        return data
      });
  }*/

  public getUser(): LoggedUser
  {
    if (this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
      return this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY);
    return null;
  }

  public getGroups(): GruppiRuoli[]
  {
    if (this.lss.containsValue(LocalSessionServiceService.USER_GROUPS_KEY))
      return this.lss.getValue(LocalSessionServiceService.USER_GROUPS_KEY);
    return null;
  }

  public getActualGroups(): GruppiRuoli
  {
    if (this.lss.containsValue(LocalSessionServiceService.WORKING_GROUP_KEY))
      return this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    return null;
  }

  get nomeGruppo(): string
  {
    let nomeGruppo = "";
    if(this.lss.containsValue(LocalSessionServiceService.USER_GROUPS_KEY))
    {
      let gruppi: GruppiRuoliDTO[] = this.lss.getValue(LocalSessionServiceService.USER_GROUPS_KEY);
      let g = gruppi.find(f => f.ruoli && f.ruoli.some(s => s.value === this.actualGroup));
      if(g)
        nomeGruppo = g.gruppo;
    }
    return nomeGruppo;
  }

  get actualGroup(): string { return this.lss.containsValue(wgk) ? this.lss.getValue(wgk) : null; }
  get isRup(): boolean { return this.lss.containsValue(rKey) ? this.lss.getValue(rKey) : null; }
  //set actualGroup(codiceGruppo: string) { this.lss.storeValue(wgk, codiceGruppo); this.groupChanged.next(codiceGruppo); }
  get groupType(): GroupType { return fromGroupToGroupType(this.actualGroup); }
  get role(): GroupRole { return fromGroupToRole(this.actualGroup); }
  public get idOrganizzazione(): number { return fromGroupToidOrganizzazione(this.actualGroup); }

  public setActualGroup(codiceGruppo: string, rup: boolean): void
  {
    this.lss.storeValue(wgk , codiceGruppo);
    this.lss.storeValue(rKey, rup);
    this.groupChanged.next(codiceGruppo);
  }

  public get groupChangeObs(): Observable<string> { return this.groupChanged.asObservable(); }

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
  /*public checkRefresh(): void
  {
    this.refreshSession.checkRefresh();
  }*/

  public getEnti(): Promise<any>
  {
    return this.http.get<any>(CONST.WEB_RESOURCE_BASE_URL + "menu/enti.pjson")
      .toPromise().then(data =>
      {
        return data
      });
  }

  /**
* Roles of user
*/
  /* public getRoles(): Promise<ResponseBean<Role[]>>
  {
    return this.http.get(CONST.WEB_RESOURCE_BASE_URL + "utente/ruoli.pjson")
      .toPromise()
      .then(data => (<ResponseBean<Role[]>>data));
  }

  getCurrentlyLoggedUser(): Observable<string[]>
  {
    //{"descrizioneEsito":"OK","codiceEsito":"OK","numeroTotaleOggetti":1,"numeroOggettiRestituiti":1,"payload":["pevadmin"]}
    return this.http.get<BaseResponse<string[]>>(`${CONST.WEB_RESOURCE_BASE_URL}/ruoli`).pipe(
      map((response: BaseResponse<string[]>) =>
      {
        console.log('payload:', response.payload);
        return response.payload;
      })
    )
  } */

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

  public getEnteCss(idEnte:number): Observable<BaseResponse<EnteCss>> {
    return this.http.get<BaseResponse<EnteCss>>(environment.linkFunzioniTrasverali + "/find"
                                               ,{params:{"applicazione":"PAE_PRES_IST","id":idEnte + ""}}
                                               );
  }

  public updateMailPec(mail: string, pec: string, phone: string)
  {
    let user = this.getUser();
    user.emailAddress = mail;
    user.phone = phone;
    user.pec = pec;
    this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
  }

}