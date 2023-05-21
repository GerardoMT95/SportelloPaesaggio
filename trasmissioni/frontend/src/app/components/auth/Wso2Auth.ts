import { authConfig } from './oauth2.config';
import { ApiService } from '../../services/api.service';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OAuthService, JwksValidationHandler } from 'angular-oauth2-oidc';
import { IAuthService } from './IAuthService';
import { Inject, Injectable } from '@angular/core';
import { BaseResponse } from '../model/base-response';
import { LoggedUser } from '../model/logged-user';
import { environment } from 'src/environments/environment';
import { SilentRefreshService } from './silent-refresh-service';
import { CookieStorage } from 'cookie-storage';

@Injectable()
export class Wso2Auth implements IAuthService{
    private assertion_id:string;
    constructor( 
        private oauthService: OAuthService,
        private api: ApiService,
    ){ 
        this.configureOauthService();
    }
 
    private configureOauthService() {
        
        this.oauthService.configure(authConfig);
        this.oauthService.tokenValidationHandler = new JwksValidationHandler();
        //this.oauthService.setupAutomaticSilentRefresh();
        this.oauthService.setStorage(new CookieStorage({ path: "/" }));
        this.oauthService.jwks = environment.wso2.jwks;
     
        this.oauthService.tryLogin({});
        
    }

    public obtainAccessToken():void {
        this.oauthService.initImplicitFlow();
    }
    
    public getUserName(): string {

        const accessToken = this.getAccessToken();
        const claims = this.getUserClaims();
        console.log('access token ',accessToken);
        this.getUserInfo();
        return claims['sub'].split('@')[0];

    }

    public getRoles(): string[] {
        return this.getUserClaims()['groups'];
    }

    public getUserInfo(): string {
        const idToken =  this.oauthService.getIdToken();
        console.log('id token ',idToken);
        return typeof idToken['sub'] !== 'undefined' ? idToken['sub'].toString() : '';
    }

    public getAccessToken(): string {
        return this.oauthService.getAccessToken();
    }

    public getUserClaims(): object {
     return this.oauthService.getIdentityClaims();
    }

    public isLoggedIn(): boolean {
        if (this.oauthService.getAccessToken() === null) {
            return false;
        }
        return true;
    }

    public login() : Observable<HttpResponse<BaseResponse<LoggedUser>>> {
        if(this.isLoggedIn()){
            var result = this.api.performOauthLogin();
            return result;
          }else{
            this.obtainAccessToken();
            return null;
          }
    }
    
    public logout(): void {
        this.oauthService.logoutUrl = environment.wso2.serverUrl + environment.wso2.logoutUrl
                                    + "?id_token_hint=" + this.oauthService.getAccessToken()
                                    ;
        sessionStorage.clear();
        this.oauthService.logOut(false);
    }

    public logOut(): void {
        this.logout();
    }
}
