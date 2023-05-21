
import { Inject, Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';
import { CookieStorage } from 'cookie-storage';
import { CONST } from '../../constants';
/**
 * Class to manage silent refresh
 */
@Injectable({
    providedIn: 'root'
})
export class SilentRefreshService
{


    public static readonly LSS_KEY_ACCESS: string = 'access_token';
    public static readonly LSS__KEY_STORED_AT: string = 'access_token_stored_at';
    public static readonly LSS__KEY_EXPIRED: string = 'expires_at';
    public static readonly INTERVAL_TIMEOUT: number = 60000;
    private static readonly LIMIT_REFRESH: number = 1800000;
    private static readonly LIMIT_LAST_OPERATION: number = 1800000;
    public static readonly TIME_LAST_OPERATION: string = 'TIME_LAST_OPERATION';
    /**
     * Cosntructor
     * @param authService 
     */
    constructor(@Inject(OAuthService) private authService)
    {
        //ADD MESSAGE LISTENER
        window.addEventListener('message', this.refreshMessageEventListener);
        //SET INTERVAL
        setInterval(() =>
        {
            if (this.authService.getAccessToken() !== null
                && (this.authService.getAccessTokenExpiration() - new Date().getTime()) < SilentRefreshService.LIMIT_REFRESH)
            {
                this.refreshToken();
            }
        }, SilentRefreshService.INTERVAL_TIMEOUT);
    }
    /**
     * Method to refresh token
     */
    public refreshToken(): void
    {
        if (environment.wso2.silentRefresh.indexOf(window.location.origin) < 0)
        {
            window.open('/assets/silent-refresh.html'
                , '_blank'
                , 'toolbar=no,status=no,menubar=no,scrollbars=no,resizable=no,left=10000, top=10000, width=10, height=10, visible=none');
        } else
        {
            var existingIframe = document.getElementById(this.authService.silentRefreshIFrameName);
            if (existingIframe)
                document.body.removeChild(existingIframe);

            var iframe = document.createElement('iframe');
            iframe.id = this.authService.silentRefreshIFrameName;
            iframe.setAttribute('src', environment.wso2.silentRefresh);
            iframe.style['display'] = 'none';
            document.body.appendChild(iframe);
        }
    }
    /**
     * Save session data
     * @param parameters 
     */
    public saveSessionData(parameters: string[]): void
    {
        let cookieStorage = new CookieStorage();
        let _now: number = new Date().getTime();
        cookieStorage.setItem(SilentRefreshService.LSS_KEY_ACCESS, parameters['access_token'], { path: "/", secure: !CONST.DEVELOPER });
        cookieStorage.setItem(SilentRefreshService.LSS__KEY_STORED_AT, '' + _now, { path: "/", secure: !CONST.DEVELOPER });
        cookieStorage.setItem(SilentRefreshService.LSS__KEY_EXPIRED, '' + (_now + parseInt(parameters['expires_in']) * 1000), { path: "/", secure: !CONST.DEVELOPER });
    }
    /**
     * Message listener
     */
    private refreshMessageEventListener = (e: MessageEvent) =>
    {
        let origin: string = e.origin;
        let hashData: string = e.data;
        let parameters: string[] = [];
        if (origin == window.location.origin && hashData && hashData.length > 1)
        {
            hashData = hashData.substr(1);
            var vars = hashData.split('&');
            for (var i = 0; i < vars.length; i++)
            {
                var pair = vars[i].split('=');
                parameters[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]);
            }
            this.saveSessionData(parameters);
        }
    };

}
