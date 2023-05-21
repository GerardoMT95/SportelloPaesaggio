import { SilentRefreshService } from './../components/auth/silent-refresh-service';
import { OAuthService } from 'angular-oauth2-oidc';
import { CONST } from 'src/app/shared/constants';
import { Injectable,Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RefreshSession {

  constructor(
    private http: HttpClient
    ,@Inject(OAuthService) private oautSvc
    ,private silentRefreshService: SilentRefreshService
  ) { }

  // Call to refresh session
  public refresh():void {
    this.silentRefreshService.refreshToken();
   /*
    this.oautSvc.silentRefresh()
                .then(info => console.debug('refresh ok', info))
                .catch(err => console.error('refresh error', err));
*/


  }
  /**
   * forceLogout. Remove session token and close window
   */
  public forceLogout():void {
    this.oautSvc.logOut(true);
    //window.close();
  }

  public checkRefresh(): void{
    if(this.oautSvc.getIdTokenExpiration() - new Date().getTime()< CONST.REFRESH_TIME){
      this.refresh();
    }
  }
}
