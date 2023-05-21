import { Component, OnInit, Inject, Input, Output, EventEmitter } from "@angular/core";
import { Router } from "@angular/router";
import { NgbDropdownConfig, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { AUTH_SERVICE, IAuthService } from "src/app/shared/components/auth/IAuthService";
import { LoggedUser } from "src/app/shared/components/model/logged-user";
import { Menu, Lang, EnteCss } from "src/app/shared/components/model/model";
import { CONST } from "src/app/shared/constants";
import { ApiService } from "src/app/shared/services/api.service";
import { ErrorService } from "src/app/shared/services/error.service";
import { LoadingService } from "src/app/shared/services/loading.service";
import { LocalSessionServiceService } from "src/app/shared/services/local-session-service.service";
import { UserService } from "src/app/shared/services/user.service";
import { environment } from "src/environments/environment";
import { PrimeNgMenu, PrimeNgMenuItems } from "../../models/menu.model";
import { MAIN_MENU } from "./primeNgMenu";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit
{

  public ente:EnteCss;
  @Input("zoomMin")
  public zoomMin: number;
  @Input("zoomMax")
  public zoomMax: number;
  @Input("zoom")
  public zoom: number;
  @Output() zoomPagina: EventEmitter<boolean> = new EventEmitter();

  public isPublic: boolean = true;
  public env = environment;
  public isCollapsed = true;
  public menuLoaded: Promise<boolean>;
  public menuList: Menu[] = new Array();
  public user: LoggedUser;
  public languageList: Lang[] = CONST.AVAILABLE_LANGUAGE;
  public selectedLanguage: string;
  public multiLang: boolean = environment.multiLang;
  public roles: string[] = [];
  public enti: { gruppo: string, codici: string[] }[] = [];
  public currentRole:string='';
  public showRoles: boolean = false;
  primeNgMenu: PrimeNgMenu[] = [];
  menuItems: PrimeNgMenu;
  public developer: boolean = CONST.DEVELOPER;
  isWithoutRole: boolean;
  private unsubscribe$ = new Subject<void>();

  constructor(
    private config: NgbDropdownConfig,
    private api: ApiService,
    private userService: UserService,
    private lss: LocalSessionServiceService,
    private translateService: TranslateService,
    private router: Router,
    private modalService: NgbModal,
    private loadingService: LoadingService,
    private errorService: ErrorService,
    @Inject(AUTH_SERVICE) private authService: IAuthService
  )
  {
    this.config.placement = 'bottom-right';
    this.user = this.userService.getUser();
    this.selectedLanguage = this.translateService.getDefaultLang();
    this.subscribeUser();
  }

  public ngOnInit(): void
  {

    this.userService.enteEmitter.subscribe(ente =>{
      this.ente = ente;
    })
    if (this.userService.isLogged())
      this.isPublic = false;
    this.currentRole=this.userService.role;  
    this.userService.userObservable
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(result =>
      {
        this.user = result;
        this.currentRole=this.userService.role;
        this.getMenu();
      })
      
    if (this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
    {
      this.getMenu();
    }
    this.menuItems = MAIN_MENU;
  }

  public ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  public setLanguage(lang: Lang): void
  {
    this.translateService.setDefaultLang(lang.code);
    this.translateService.use(lang.code);
    this.selectedLanguage = lang.code;
  }

  public getMenu(): void
  {
    this.evaluateEnti();
    this.userService.groupChangeObs.pipe(takeUntil(this.unsubscribe$)).subscribe(
      (newGruppo:string) => {
        this.evaluateEnti();
        this.currentRole=this.userService.role;
      }
      );
  }

  private evaluateEnti(): void
  {
    let ugk = LocalSessionServiceService.USER_GROUPS_KEY;
    if (this.lss.containsValue(ugk))
    {
      let tmp = this.lss.getValue(ugk);
      console.log("Enti: ", tmp);
      this.enti = tmp.map(m => { return { gruppo: m.gruppo, codici: m.ruoli.map(m => m.value) } });
    }
  }

  public login(): void { this.router.navigateByUrl('/login'); }

  public logout(): void
  {
    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    this.authService.logout();
  }


  public subscribeUser(): void
  {
    this.userService.userObservable
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user: LoggedUser) =>
      {
        this.user = user;
      });
  }

  public openRoleDialog(content: any): void
  {
    this.modalService.open(content);
  }

  public openEnteDialog(content: any): void
  {
    this.modalService.open(content);
  }

  public loadLinkMenu(link: string): void
  {
    this.isCollapsed = !this.isCollapsed;
    if (this.router.url != link)
      this.loadingService.emitLoading(true);
    else
      this.loadingService.emitLoading(false);
  }

  public closePopup(): void
  {
    if (this.developer)
      this.logout();
    else
      window.close();
  }
  /**
   * Mostra la lista delle sottovoci del menu selezioanto
   * dove è presente il subMenu
   */
  public showHideSubMenu(item: PrimeNgMenuItems): void
  {
    if (item.show)
    {
      item.icon = 'fa fa-arrow-circle-down';
      //item.icon = 'arrow-circle-down';
      item.show = false;
    } else
    {
      item.icon = 'fa fa-arrow-circle-up';
      //item.icon = 'arrow-circle-up';
      item.show = true;
    }
  }

  public cambiaGruppo(): void { this.router.navigate(["/gestione-gruppo"]); }

  get showCambiaGruppo(): boolean 
  {
    let ugk = LocalSessionServiceService.USER_GROUPS_KEY;
    let show = false;
    if (this.lss.containsValue(ugk))
      show = this.lss.getValue(ugk).length > 1;
    return show;
  }

  get actualGroup(): string { return this.userService.actualGroup; }


 public getLogoUrl():string{
  return environment.linkFunzioniTrasverali + "/download-logo?applicazione=PAE_PRES_IST&id="+this.ente.id;
 }

 public cambiaMailPec(){
  this.router.navigate(['/cambia-mail']);
}
}
