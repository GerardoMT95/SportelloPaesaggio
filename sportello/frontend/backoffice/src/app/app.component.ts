import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { CookieStorage } from 'cookie-storage';
import { interval, Subject } from 'rxjs';
import { AUTH_SERVICE } from './shared/components/auth/IAuthService';
import { SilentRefreshService } from './shared/components/auth/silent-refresh-service';
import { CONST } from './shared/constants';
import { ApiService } from './shared/services/api.service';
import { ErrorService } from './shared/services/error.service';
import { GlobalService } from './shared/services/global.service';
import { LoadingService } from './shared/services/loading.service';
import { LocalSessionServiceService } from './shared/services/local-session-service.service';
import { RefreshSession } from './shared/services/refreshsession.service';
import { UserService } from './shared/services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit
{
  @ViewChild('error', { static: false }) error: NgbModal;
  @ViewChild('content', { static: false }) content: NgbModal;
  @ViewChild('alert', { static: false }) alert: NgbModal;
  @ViewChild('sessioneScaduta', { static: false }) sessioneScaduta: NgbModal;

  public isTimer: boolean;
  public timeIsUp: boolean;
  public timerCount: number = CONST.COUNTDOWN_TIME;
  public timeout: number;
  public idle: number;
  private intervallo: any;
  private attivitaStartInterval: any;
  private attivitaInterval: any;
  public errorMessage: string;
  public isErrorMessageOpen: boolean = false;
  public isLoadingOperation: boolean = true;
  private unsubscribe$ = new Subject<void>();
  private isErrorPrivacy: boolean = false;

  public zoomChange: number = 0.05;
  public zoomMin: number = 0.8;
  public zoomMax: number = 1.2;
  public zoom: number = 1;
  public zoomPage : any = {"font-size": (this.zoom + "em")};
  private readonly cookieName : string = "COOKIE-FONT";
  private cokieStorage :CookieStorage = new CookieStorage({path:"/",secure:!CONST.DEVELOPER});  

  constructor(
    private translate: TranslateService,
    private modalService: NgbModal,
    private api: ApiService,
    private lss: LocalSessionServiceService,
    private userService: UserService,
    private globalService: GlobalService,
    private loadingService: LoadingService,
    private refreshSession: RefreshSession,
    @Inject(AUTH_SERVICE) private authService,
    private errorService: ErrorService,
    private router: Router
  )
  {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('it');
    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('it');

    this.userService.logoutObservable.subscribe(result =>
    {
      if (result == CONST.OPEN_DIAOLOG_LOGOUT)
      {
        this.intervallo = interval(1000).subscribe(resp =>
        {
          this.timerCount = (CONST.COUNTDOWN_TIME / 1000) - resp;
          if (!this.modalService.hasOpenModals())
            this.modalService.open(this.content);
          if (this.timerCount == 1)
          {
            this.intervallo.unsubscribe();
            this.timerCount = 0;
            this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
            this.userService.emitForceLogout();
          }
        })
      }
    })

  }
  ngOnInit()
  {
    setInterval(() =>
    {
      this.isTokenExpired();
    }, 15_000);
    this.subscribeError();
    this.subscribeLoading();
    let _now: number = new Date().getTime();
    let cokieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
    cokieStorage.setItem(SilentRefreshService.TIME_LAST_OPERATION, '' + _now, { path: "/", secure: !CONST.DEVELOPER });
    this.startCheckInattivita();
    let zoomCookie : string = this.cokieStorage.getItem(this.cookieName);
    if(this.cokieStorage.getItem("accept-cookie") && this.cokieStorage.getItem("accept-cookie").charAt(0) == "1" && zoomCookie && zoomCookie.length){
      try{
        this.zoom = parseFloat(zoomCookie);        
        if(!this.zoom){
          this.zoom = 1;
        }
        if(this.zoom > this.zoomMax){
          this.zoom = this.zoomMax;
        }else if(this.zoom < this.zoomMin){
          this.zoom = this.zoomMin;
        }
        this.zoom = parseFloat(this.zoom.toFixed(2));
        this.zoomPage = {"font-size": (this.zoom + "em")};
      }catch(e){
      }
    }
  }

  /**
     * Function for refresh session, called by UI
     */
  public onStopTimer(): void
  {
    this.intervallo.unsubscribe();
    this.intervallo = null;
    let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
    let _now: number = new Date().getTime();
    cookieStorage.setItem(SilentRefreshService.TIME_LAST_OPERATION, _now + '', { path: "/", secure: !CONST.DEVELOPER });
    this.modalService.dismissAll();
  }

  public startCheckInattivita(): void
  {
    this.attivitaStartInterval = interval(1000).subscribe(resp =>
    {
      let seconds: number = new Date().getSeconds();
      if (seconds == 0 || seconds == 1 || seconds == 59 || seconds == 29 || seconds == 30 || seconds == 31)
      {
        this.checkInattivita();
        this.attivitaStartInterval.unsubscribe();
      } else
      {
        this.checkSessioneScadutaOtherPopup();
      }
    });
  }
  private checkSessioneScadutaOtherPopup(): void
  {
    let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
    if (sessionStorage.getItem(LocalSessionServiceService.LOGGED_USER_KEY) != null
      && cookieStorage.getItem(SilentRefreshService.LSS_KEY_ACCESS) == null
    )
    {
      this.showDialogSessioneScaduta();
    }
  }
  private showDialogSessioneScaduta(): void
  {
    let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
    this.userService.oidcLogout(cookieStorage.getItem(SilentRefreshService.LSS_KEY_ACCESS));
    cookieStorage.removeItem(SilentRefreshService.LSS_KEY_ACCESS, { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem(SilentRefreshService.LSS__KEY_STORED_AT, { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem(SilentRefreshService.LSS__KEY_EXPIRED, { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("JSESSIONID", { path: "/authenticationendpoint" });
    cookieStorage.removeItem("id_token_claims_obj", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token_expires_at", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token_stored_at", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("nonce", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("session_state", { path: "/", secure: !CONST.DEVELOPER });
    sessionStorage.clear();
    if (this.intervallo) this.intervallo.unsubscribe();
    if (this.attivitaStartInterval) this.attivitaStartInterval.unsubscribe();
    if (this.attivitaInterval) this.attivitaInterval.unsubscribe();
    this.intervallo = null;
    this.attivitaStartInterval = null;
    this.attivitaInterval = null;
    this.modalService.dismissAll();
    this.modalService.open(this.sessioneScaduta, { 'keyboard': false, 'backdrop': 'static' })
  }

  /**
  * Method to check inattivita. Return true in caso di innattivita
  */
  private checkInattivitaStato(): boolean
  {
    if (this.authService.getAccessToken() !== null)
    {
      let cookieStorage: CookieStorage = new CookieStorage({ path: "/", secure: !CONST.DEVELOPER });
      let _now: number = new Date().getTime();
      let _lastOperation: number = parseInt(cookieStorage.getItem(SilentRefreshService.TIME_LAST_OPERATION));
      if (isNaN(_lastOperation) || (_now - _lastOperation) > 1800000)
      {
        return true;
      }
    }
    return false;
  }

  public checkInattivita(): void
  {
    this.checInattivitaInteval();
    this.attivitaInterval = interval(SilentRefreshService.INTERVAL_TIMEOUT / 2)
      .subscribe(resp => { this.checInattivitaInteval(); });
  }

  public checInattivitaInteval()
  {
    if (this.checkInattivitaStato())
    {
      this.modalService.dismissAll();
      this.timerCount = CONST.COUNTDOWN_TIME;
      this.modalService.open(this.content, { 'keyboard': false, 'backdrop': 'static' });
      if (this.intervallo)
        this.intervallo.unsubscribe();
      this.intervallo = interval(1000).subscribe(resp =>
      {
        if (this.checkInattivitaStato() == false)
        {
          this.intervallo.unsubscribe();
          this.intervallo = null
          this.modalService.dismissAll();
          this.checkSessioneScadutaOtherPopup();
          return;
        }
        this.timerCount--;
        if (this.timerCount <= 0)
        {
          this.showDialogSessioneScaduta();
        }
      }
      );
    } else
    {
      this.checkSessioneScadutaOtherPopup();
    }
  }

  public goLogin():void{  
  if(CONST.DEVELOPER || !window.opener){
    document.location.href = "/logout.html";
  }
  else
    window.close();
  }

  /**
   * subsribe loading
   */
  public subscribeLoading(): void
  {
    this.loadingService.loadingObservable.subscribe((isActiveLoading: boolean) =>
    {
      this.isLoadingOperation = isActiveLoading;
    });
  }

  public closeErrorDialog(): void
  {
    this.modalService.dismissAll();
    this.isErrorMessageOpen = false;
    if(this.isErrorPrivacy){
      this.isErrorPrivacy = false;
      this.router.navigateByUrl("/privacy/accetta")
    }
  }

  /**
   * subscribe error
   */
  public subscribeError(): void
  {
    this.errorService.errorObservable.subscribe(
      (message: string) =>
      {
        console.log("GET error " + message);
        try
        {
          this.closeErrorDialog();
        } catch (e)
        {
          console.log("ERROR:");
          console.log(e);
        }
        console.log("GET error " + message);
        if (this.isErrorMessageOpen == false)
        {
          switch (message)
          {
            //ERROR HTML CODE
            case "400": this.errorMessage = this.translate.instant("error.400"); break;
            case "401": this.errorMessage = this.translate.instant("error.401"); break;
            case "403": this.errorMessage = this.translate.instant("error.403"); break;
            case "405": this.errorMessage = this.translate.instant("error.405"); break;
            case "415": this.errorMessage = this.translate.instant("error.415"); break;
            case "500": this.errorMessage = this.translate.instant("error.500"); break;
            //ERROR KO PAYLOAD
            case "LOGOUT_ERROR": this.errorMessage = this.translate.instant("error.LOGOUT_ERROR"); break;
            case "VALIDATION": this.errorMessage = this.translate.instant("error.VALIDATION_ERROR"); break;
            case "AUTH_ERROR":
              this.errorMessage = this.translate.instant("error.AUTH_ERROR");
              setTimeout(() =>
              {
                this.api.performLogout().subscribe(response =>
                {
                  if (response.body.code == CONST.OK)
                  {
                    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
                    this.authService.logout();
                  } else
                  {
                    this.errorService.emitError('LOGOUT_ERROR');
                    //alert("Logout non terminata correttamente");
                  }
                }, error =>
                {
                  this.errorService.emitError('LOGOUT_ERROR');
                });
              }, 3000);
              break;
            case "GENERIC_ERROR": this.errorMessage = this.translate.instant("error.GENERIC_ERROR"); break;
            case "PRIVACY_ERROR"         : {
              this.isErrorPrivacy = true;
              this.errorMessage = this.translate.instant("error.PRIVACY_ERROR");
              break;
            }
            default:
              if (message.startsWith("CUSTOM-OPERATION-TO-ADVICE:"))
              {//caso di eccezioni con messaggio da visualizzare all'utente
                this.errorMessage = message.split("CUSTOM-OPERATION-TO-ADVICE:")[1];
              } else
              {
                this.errorMessage = this.translate.instant("error.GENERIC_ERROR");
              }
              break;
          }
          this.isLoadingOperation = false;
          this.modalService.open(this.error);
          this.isErrorMessageOpen = true;
        }
      });
  }

  isTokenExpired()
  {
    let cookieStorage: CookieStorage = new CookieStorage();
    let now = new Date().getTime();
    let expires_at = parseInt(cookieStorage.getItem("expires_at"));
    // console.log("EXPIRE AT", new Date(expires_at - now).getMinutes());
    //console.log("EXPIRE AT", expires_at - now);
    if (expires_at && expires_at - now <= 12000 && expires_at - now > 0)
    {
      this.userService.emitDialogSession();
    } else if (expires_at && expires_at - now < 0)
    {
      cookieStorage.removeItem(LocalSessionServiceService.LOGGED_USER_KEY);
      this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
      this.authService.logout();
    }
    // } else if(expires_at && expires_at - now <= 6000) {
    //   this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    //   this.authService.logout();
    // }
  }

  ngOnDestroy()
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }


  public updateZoom(event) {
    if(event){
      if(this.zoom < this.zoomMax)
         this.zoom = this.zoom + this.zoomChange;
    }else{
      if(this.zoom > this.zoomMin)
        this.zoom = this.zoom - this.zoomChange;
    }
    this.zoom = parseFloat(this.zoom.toFixed(2));
    let date: Date = new Date();
    date.setFullYear(date.getFullYear() + 10);
    if(this.cokieStorage.getItem("accept-cookie") && this.cokieStorage.getItem("accept-cookie").charAt(0) == "1")
    this.cokieStorage.setItem(this.cookieName, this.zoom.toString(), {expires: date});
    this.zoomPage = {"font-size": (this.zoom + "em")};
  } 
}
