import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { CookieStorage } from 'cookie-storage';
import { SelectItem } from 'primeng/components/common/selectitem';
import { interval, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ComuneService } from 'src/app/services/comune.service';
import { CONST } from '../shared/constants';
import { AUTH_SERVICE } from './components/auth/IAuthService';
import { SilentRefreshService } from './components/auth/silent-refresh-service';
import { TipoEnte } from './components/model/entity/ente.models';
import { ApiService } from './services/api.service';
import { DataService } from './services/data.service';
import { ErrorService } from './services/error.service';
import { GlobalService } from './services/global.service';
import { LoadingService } from './services/loading.service';
import { LocalSessionServiceService } from './services/local-session-service.service';
import { RefreshSession } from './services/refreshsession.service';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit
{
  @ViewChild('error') error: NgbModal;
  @ViewChild('warning') warning: NgbModal;
  @ViewChild('content') content: NgbModal;
  @ViewChild('sessioneScaduta') sessioneScaduta: NgbModal;
  @ViewChild('alert') alert: NgbModal;

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
  public isErrorPrivacy: boolean = false;
  public isWarningMessageOpen: boolean = false;
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
    @Inject(DataService) private dataService: DataService,
    private enteService: ComuneService,
    @Inject(AUTH_SERVICE) private authService,
    private errorService: ErrorService,
    private router: Router
  )
  {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('it');
    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('it');


    this.userService.logoutObservable
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(result =>
      {
        if (result == CONST.OPEN_DIAOLOG_LOGOUT)
        {
          this.intervallo = interval(1000)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe(resp =>
            {
              //this.timerCount = CONST.COUNTDOWN_TIME / 1000 - resp;
              this.timerCount = CONST.COUNTDOWN_TIME  - resp;
              if (!this.modalService.hasOpenModals())
                this.modalService.open(this.content);
              if (this.timerCount <= 1)
              {
                this.intervallo.unsubscribe();
                this.timerCount = 0;
                this.modalService.dismissAll();
                this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
                this.lss.removeValue(LocalSessionServiceService.USER_GROUPS_KEY);
                this.lss.removeValue(LocalSessionServiceService.WORKING_GROUP_KEY);
                this.authService.logout();
              }
            });
        }
      });

  }


  ngOnInit() 
  {
    this.getComuniEProvince();
    /*setInterval(() =>
    {
      this.isTokenExpired();
    }, 15_000);*/
    this.subscribeError();
    this.subscribeLoading();

    let _now: number = new Date().getTime();
    let cookieStorage = new CookieStorage();
    cookieStorage.setItem(SilentRefreshService.TIME_LAST_OPERATION, _now + '' , { path: "/" });
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

  ngOnDestroy()
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  public onStopTimer(): void
  {
    this.intervallo.unsubscribe();
    this.intervallo = null;
    let cookieStorage:CookieStorage = new CookieStorage({path:"/",secure:!CONST.DEVELOPER});
    let _now: number = new Date().getTime();
    cookieStorage.setItem(SilentRefreshService.TIME_LAST_OPERATION, _now + '', { path: "/" });
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
      }
    });
  }
  /**
   * Method to check inattivita. Return true in caso di innattivita
   */
  private checkInattivitaStato(): boolean
  {
    if (this.authService.getAccessToken() !== null)
    {
      let cookieStorage: CookieStorage = new CookieStorage();
      let _now: number = new Date().getTime();
      let _lastOperation: number = parseInt(cookieStorage.getItem(SilentRefreshService.TIME_LAST_OPERATION));
      if (isNaN(_lastOperation) || (_now - _lastOperation) > 1800000)
      {
        return true;
      }
    }
    return false;
  }

  /* public checkInattivita(): void
  {
    this.attivitaInterval = interval(SilentRefreshService.INTERVAL_TIMEOUT / 2)
      .subscribe(resp =>
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
              this.intervallo = null;
              this.modalService.dismissAll();
              return;
            }
            this.timerCount--;
            if (this.timerCount <= 0)
            {
              this.intervallo.unsubscribe();
              this.intervallo = null;
              this.modalService.dismissAll();
              let cookieStorage: CookieStorage = new CookieStorage();
              cookieStorage.removeItem(SilentRefreshService.LSS_KEY_ACCESS, { path: "/" });
              cookieStorage.removeItem(SilentRefreshService.LSS__KEY_STORED_AT, { path: "/" });
              cookieStorage.removeItem(SilentRefreshService.LSS__KEY_EXPIRED, { path: "/" });
              cookieStorage.removeItem("JSESSIONID", { path: "/authenticationendpoint" });
              sessionStorage.clear();
              this.modalService.open(this.sessioneScaduta, { 'keyboard': false, 'backdrop': 'static' })
              this.attivitaInterval.unsubscribe();
            }
          }
          );
        }
      }
      );
  } */

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
    cookieStorage.removeItem("JSESSIONID", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("JSESSIONIDLF7", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("JSESSIONIDLF7Signed", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("JSESSIONID", { path: "/authenticationendpoint", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token_claims_obj", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token_expires_at", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token_stored_at", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("id_token", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("nonce", { path: "/", secure: !CONST.DEVELOPER });
    cookieStorage.removeItem("session_state", { path: "/", secure: !CONST.DEVELOPER });
    sessionStorage.clear();
    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    this.lss.removeValue(LocalSessionServiceService.USER_GROUPS_KEY);
    this.lss.removeValue(LocalSessionServiceService.WORKING_GROUP_KEY);
    if (this.intervallo) this.intervallo.unsubscribe();
    if (this.attivitaStartInterval) this.attivitaStartInterval.unsubscribe();
    if (this.attivitaInterval) this.attivitaInterval.unsubscribe();
    this.intervallo = null;
    this.attivitaStartInterval = null;
    this.attivitaInterval = null;
    this.modalService.dismissAll();
    this.modalService.open(this.sessioneScaduta, { 'keyboard': false, 'backdrop': 'static' })
  }

  public goLogin():void{  
  if(CONST.DEVELOPER || !window.opener){
    this.router.navigateByUrl('/login');
  }
  else
    window.close();
  }

  /**
   * subsribe loading
   */
  public subscribeLoading(): void
  {
    this.loadingService.loadingObservable
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (isActiveLoading: boolean) =>
        {
          this.isLoadingOperation = isActiveLoading;
        }
      );
  }

  isTokenExpired()
  {
    let now = new Date().getTime();
    let cookieStorage = new CookieStorage();
    //let expires_at = this.lss.getValue("expires_at");
    let expires_at = parseInt(cookieStorage.getItem("expires_at"));
    // console.log("EXPIRE AT", new Date(expires_at - now).getMinutes());
    //console.log("EXPIRE AT", expires_at - now);
    if (expires_at && expires_at - now <= 12000 && expires_at - now > 0)
    {
      this.userService.emitDialogSession();
    } else if (expires_at && expires_at - now < 0)
    {
      this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
      this.authService.logout();
    }
    // } else if(expires_at && expires_at - now <= 6000) {
    //   this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    //   this.authService.logout();
    // }
  }

  public closeErrorDialog(): void
  {
    this.modalService.dismissAll();
    this.isErrorMessageOpen = false;
    this.isWarningMessageOpen = false;
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
              {
                //caso di eccezioni con messaggio da visualizzare all'utente
                this.errorMessage = message.split("CUSTOM-OPERATION-TO-ADVICE:")[1];
                //this.router.navigate(['/private/fascicolo']);
              }
              else if(message.startsWith("CUSTOM-WARNING:"))
              {
                this.errorMessage = message.split("CUSTOM-WARNING:")[1];
                this.isWarningMessageOpen = true;
              } 
              else 
              {
                this.errorMessage = this.translate.instant("error.GENERIC_ERROR");
              }
              break;
          }
          this.isLoadingOperation = false;
          if(!this.isWarningMessageOpen)
          {
            this.modalService.open(this.error);
            this.isErrorMessageOpen = true;
          }
          else
          {
            this.modalService.open(this.warning);
          }
        }
      });
  }

  getComuniEProvince(): void
  {
    this.enteService.findComuniEProvince().subscribe(enti =>
    {
      if(enti.codiceEsito === CONST.OK && enti.payload)
      {
        this.dataService.entiNext(enti.payload);
        let comuni: SelectItem[] = [];
        let province: SelectItem[] = [];
        enti.payload.forEach(ente =>
        {
          if(ente.tipo === TipoEnte.comune)
            comuni.push({ label: ente.descrizione, value: ente.codice });
          else if (ente.tipo === TipoEnte.provincia)
            province.push({ label: ente.descrizione, value: ente.codice });
        });
        this.dataService.comuniNext(comuni);
        this.dataService.provinceNext(province);
      }
    });
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
