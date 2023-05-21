import { OAuthService } from 'angular-oauth2-oidc';
import { Injectable,Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SilentRefreshService } from '../components/auth/silent-refresh-service';
import { CONST } from 'src/shared/constants';

@Injectable({
  providedIn: 'root'
})
export class RefreshSession {

  constructor(
    private http: HttpClient
    //,@Inject(OAuthService) private oautSvc
    ,private oautSvc: OAuthService
    ,private silentRefreshService: SilentRefreshService
  ) { }

  // Call to refresh session
  public refresh():void {
    this.silentRefreshService.refreshToken();
    /*this.oautSvc.silentRefresh()
                .then(info => console.debug('refresh ok', info))
                .catch(err => console.error('refresh error', err));*/

  }
  /**
   * forceLogout. Remove session token and close window
   */
  public forceLogout():void {
    this.oautSvc.logOut(true);
    //window.close();
  }

  /*public checkRefresh(): void{
    if(this.oautSvc.getIdTokenExpiration() - new Date().getTime()< CONST.REFRESH_TIME){
      this.refresh();
    }
  }*/
}
