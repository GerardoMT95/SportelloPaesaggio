import { HttpClient } from '@angular/common/http';
import { EventEmitter, Inject, Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { EnteCss } from '../components/model/model';
import { CONST } from './../../shared/constants';
import { BaseResponse } from './../components/model/base-response';
import { GroupRole, GroupType } from './../components/model/enum';
import { GruppiRuoli, LoggedUser } from './../components/model/logged-user';
import { LocalSessionServiceService } from './local-session-service.service';
import { RefreshSession } from './refreshsession.service';
import { environment } from 'src/environments/environment';
import { fromGroupToGroupType, fromGroupToRole } from '../components/functions/genericFunctions';
import { CookieStorage } from 'cookie-storage';
import { LoadingService } from './loading.service';
import { AUTH_SERVICE, IAuthService } from '../components/auth/IAuthService';

const wgk = LocalSessionServiceService.WORKING_GROUP_KEY;
const vgk = LocalSessionServiceService.VALID_GROUP_KEY;

@Injectable({
  providedIn: 'root'
})
export class UserService
{
  public userObservable: EventEmitter<LoggedUser> = new EventEmitter();
  public logoutObservable: EventEmitter<number> = new EventEmitter();
  private userSubject = new ReplaySubject<LoggedUser>(1);
  private hasUser = false;
  
  public enteEmitter: EventEmitter<EnteCss> = new EventEmitter<EnteCss>();  

  public groupChanged: Subject<string> = new Subject<string>();

  constructor(private lss: LocalSessionServiceService
              ,private http: HttpClient
              ,private refreshSession: RefreshSession
              ,private loadingService:LoadingService
              ,@Inject(AUTH_SERVICE) private authService: IAuthService
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

  public getActualGroups(): string
  {
    if (this.lss.containsValue(LocalSessionServiceService.WORKING_GROUP_KEY))
      return this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    return null;
  }

  get actualGroup(): string { return this.lss.containsValue(wgk) ? this.lss.getValue(wgk) : null; }
  set actualGroup(codiceGruppo: string) { this.lss.storeValue(wgk, codiceGruppo); this.groupChanged.next(codiceGruppo); }

  get validGroup(): boolean { return this.lss.containsValue(vgk) ? this.lss.getValue(vgk) : null; }
  set validGroup(isValid: boolean) { this.lss.storeValue(vgk, isValid); }

  get groupType(): GroupType { return fromGroupToGroupType(this.actualGroup); }
  get role(): GroupRole { return fromGroupToRole(this.actualGroup); }

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

  /**
   * 
   * @returns per l'applicazione PUTT anche gli ETI che sono associati ad un COMUNE in common.ente vanno abilitati alla trasmissione 
   * di alcune sottotipologie di autorizzazioni (Parere....)
   */
  public isComuneAbilitatoTrasmissioneCondoni(){
    let gruppoScelto=null;
    let gruppi=this.getUser().otherFields['GruppoRuoli'];
    gruppi.forEach(el=>{
      if(el.codiceGruppo==this.getActualGroups()){
        gruppoScelto=el;
      }
    });
    return CONST.isPutt() && gruppoScelto &&
    gruppoScelto.additionalProperties && 
    gruppoScelto.additionalProperties['tipologiaEnte']=='CO' && 
    gruppoScelto.additionalProperties['codiceCivilia'];
  }

  /**
   * verifica se è gruppo con organizzazione su entrambi e in caso affermativo effettua ugualianza tra id organizzazione es. 89=89
   * @param ufficio campo ufficio del fascicolo es. ED_89_F
   * @param codiceGruppo ED_89_D
   */
  public isStessaOrganizzazione(ufficio:string,codiceGruppo:string):boolean{
    let ret=false;
    let partsUfficio=ufficio.split('_');
    if(partsUfficio.length<3){
      return ret;
    }
    let partsGruppo=codiceGruppo.split('_');
    if(partsGruppo.length<3){
      return ret;
    }
    return partsGruppo[1]==partsUfficio[1];
  }

  public getEnteCss(idEnte:number): Observable<BaseResponse<EnteCss>> {
    return this.http.get<BaseResponse<EnteCss>>(environment.linkFunzioniTrasverali + "/find"
                                               ,{params:{"applicazione":environment.appName,"id":idEnte + ""}}
                                               );
  }
 
  public isMultiComune(): Observable<BaseResponse<boolean>> {
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL + "/multi-comune");
  }

  public hasCookieToken(){
    let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
    if(cookieStorage.getItem("access_token")){
      return true;
    }
    return false;
  }


  updateLogin(){
    this.loadingService.emitLoading(true);
    this.authService.login().subscribe(
      async (response:any) => {
      if(response.codiceEsito === 200 || response.codiceEsito==CONST.OK )
      {
        let user : LoggedUser = response.payload;
        this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
        this.emitUser();
      }
    });
  }

}