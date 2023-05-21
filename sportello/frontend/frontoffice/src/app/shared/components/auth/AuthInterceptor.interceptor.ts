import { Injectable, Inject } from '@angular/core'; 
import { CookieStorage } from 'cookie-storage' 
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs'; 
import { Router } from '@angular/router'; 
import { tap } from 'rxjs/operators'; 
import { DialogService } from 'src/app/core/services/dialog.service';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { UserService } from '../../services/user.service';
import { ErrorService } from '../../services/error.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { CONST } from '../../constants';
import { SilentRefreshService } from './silent-refresh-service';
import { environment } from 'src/environments/environment';
 
@Injectable() 
export class AuthInterceptor implements HttpInterceptor 
{ 
 
  private data: Date = null; 
 
  constructor( 
    @Inject(LocalSessionServiceService) private lss, 
    private dialogService: DialogService, 
    private router: Router, 
    private userService: UserService, 
    private errorService: ErrorService, 
    @Inject(OAuthService) private oautSvc 
  ) 
  { 
    setInterval(() => 
    { 
      this.checkLastLogin(); 
    }, CONST.REFRESH_TIME); 
    this.userService.logoutObservable.subscribe( 
      result => 
      { 
        if (result == CONST.REFRESH_SESSION_TIMEOUT) 
        { 
          this.data = new Date(); 
          alert(this.data); 
        } 
      }, 
      err => console.log("USER SERVICE ERROR", err) 
    ); 
  } 
 
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> 
  { 
    this.data = new Date(); 
    var heads: HttpHeaders = req.headers; 
    var finalHead: HttpHeaders; 
    heads = heads.set('X-Requested-With', 'XMLHttpRequest'); 
    if (this.oautSvc) 
    { 
      let jwtToken = this.oautSvc.getAccessToken(); 
 
      if (jwtToken && environment.developer) 
      { 
        heads = heads.set('Authorization', 'Bearer ' + jwtToken); 
      } 
    } 
    if (this.lss.containsValue("gruppoScelto")) 
    { 
      heads = heads.append('GRUPPO-UTENTE', btoa(this.lss.getValue("gruppoScelto"))); 
    } 
    const reqWitHeaders = req.clone({ 
      headers: heads 
    }); 
    return next.handle(reqWitHeaders).pipe(tap((event: HttpEvent<any>) => 
    { 
      if (event instanceof HttpResponse) 
      { 
        if (event.status === 200 && (event.body.codiceEsito && event.body.codiceEsito == CONST.KO)) 
        { 
          this.errorService.emitError(event.body.descrizioneEsito); 
        } 
      } 
    }, (err: any) => 
    { 
      if (err instanceof HttpErrorResponse) 
      { 
        if (req.url.indexOf("/oidc/logout") >= 0) 
          return; 
        if (err.status === 401) 
        { 
          try 
          { 
            this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY); 
            let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem(SilentRefreshService.LSS_KEY_ACCESS, { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem(SilentRefreshService.LSS__KEY_STORED_AT, { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem(SilentRefreshService.LSS__KEY_EXPIRED, { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("id_token_claims_obj", { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("id_token_expires_at", { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("id_token_stored_at", { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("id_token", { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("nonce", { path: "/", secure: !CONST.DEVELOPER }); 
            cookieStorage.removeItem("session_state", { path: "/", secure: !CONST.DEVELOPER }); 
            this.oautSvc.logOut(false); 
          } 
          catch (e) { } 
          this.router.navigate(['login']); 
        } 
        else 
        { 
          console.log("Error html code interceptor login"); 
          this.errorService.emitError(err.status + "");
        } 
        //this.router.navigate(['error'],{queryParams: {error: err.message}});  
      } 
    })); 
  } 
 
  public checkLastLogin(): void 
  { 
    if (this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)) 
    { 
      if (this.data != null) 
      { 
        let now: Date = new Date(); 
        let diff: number = now.getTime() - this.data.getTime(); 
        if (diff > CONST.INACTIVITY_TIME) 
        { 
          //MAGGIORE DI 28 minuti 
          this.oautSvc.silentRefresh().then(info => 
          { 
            console.debug("refresh ok", info); 
          }).catch(err => console.error("refresh error", err)); 
        } 
      } 
    } 
  } 
} 
