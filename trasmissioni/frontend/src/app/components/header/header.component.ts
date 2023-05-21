import { GruppiRuoliDTO } from './../../../shared/models/plain-type-string-id.model';
import { CONST } from './../../../shared/constants';
import { environment } from './../../../environments/environment';
import { ApiService } from './../../services/api.service';
import { Lang, Menu, PrimeNgMenu, PrimeNgMenuItems, AllowedGroup, EnteCss } from './../model/model';
import { Component, OnInit, Inject, EventEmitter, Output, Input } from '@angular/core';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoggedUser } from '../model/logged-user';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { UserService } from '../../services/user.service';
import { TranslateService } from '@ngx-translate/core';
import { IAuthService, AUTH_SERVICE } from '../auth/IAuthService';
import { ActivatedRoute, Router, RouterStateSnapshot } from '@angular/router';
import { LoadingService } from '../../services/loading.service';
import { ErrorService } from '../../services/error.service';
import { MAIN_MENU } from "./primeNgMenu";
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

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
  menuItems: PrimeNgMenu[];
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

  ngOnInit()
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
    /*this.primeNgMenu = MAIN_MENU.map(item => { 
      // console.log(this.translateService.instant("generic"), 'ITEM LABEL');
      item.items.map(item=> this.translateService.get(item.label)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((response: string) => { item.label = response}));

      return item;
    } );*/
    this.menuItems = MAIN_MENU;
    /* if(this.userService.isLogged)
    {
      let actualGroupType = this.userService.groupType;
      let actualRole = this.userService.role;
      this.menuItems[0].items.forEach((item, index, array) =>
      {
        let grant: AllowedGroup[] = item.grant;
        if (grant && grant.length > 0)
        {
          if (!grant.some(g => g.group === actualGroupType && (!g.roles || g.roles.length > 0 || g.roles.includes(actualRole))))
            array.splice(index, 1);
        }
      });
    } */
    //console.log(this.menuItems);
  }

  titoloHeader(){
    //this.activatedRoute.snapshot
  }

  setLanguage(lang: Lang): void
  {
    this.translateService.setDefaultLang(lang.code);
    this.translateService.use(lang.code);
    this.selectedLanguage = lang.code;
  }

  getMenu(): void
  {
    // this.userService.getRolesMenu().then(resp=>{
    //   if(resp.codiceEsito==CONST.OK){
    //     // RISCRIVO I RUOLI DELL'UTENTE SALVATO IN SESSION STORAGE
    //     this.roles = resp.payload;
    //     console.log('ROLE', this.roles);
    //     this.allowRoute(this.roles);
    //     this.menuLoaded = Promise.resolve(true);
    //   }
    //   this.loadingService.emitLoading(false);
    // });
    /* this.userService.getObservableUser()
    .pipe(takeUntil(this.unsubscribe$)).subscribe((response: LoggedUser) => 
    { */
    /* let roles = new Set<string>();
    response.roles.forEach(item => {
      roles.add(item);
    });
    this.roles = Array.from(roles);
    let enti = new Set<string>();
    if (response.authorities != undefined) {
      response.authorities.forEach(item => {
        if(item.label){enti.add(item.label);}
      });
    }
    this.enti = Array.from(enti); */
    //console.log('ROLE', this.roles);

    /* }); */
    this.evaluateEnti();
    this.userService.groupChangeObs.pipe(takeUntil(this.unsubscribe$)).subscribe(() =>
     {
       this.evaluateEnti();
       this.currentRole=this.userService.role;
    }
     );

  }

  private evaluateEnti(): void
  {
    let ugk = LocalSessionServiceService.USER_GROUPS_KEY;
    //if (this.lss.containsValue(ugk))
      //this.enti = this.lss.getValue(ugk).map(m => { return { gruppo: m.gruppo, codice: m.ruoli[0].id } });
    if (this.lss.containsValue(ugk))
    {
      let tmp = this.lss.getValue(ugk);
      this.enti = tmp.map(m => { return { gruppo: m.gruppo, codici: m.ruoli.map(m=>m.id) } });
    }
  }

  get actualGroup(): string { return this.userService.actualGroup; }

  login(): void
  {
    this.router.navigateByUrl('/login');
  }

  logout(): void
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

  public closeRoleDialog(): void
  {
    //this.modalService.open(content,
    //this.showRoles = false;
  }
  public openRoleDialog(content): void
  {
    this.modalService.open(content);
  }

  public openEnteDialog(content): void
  {
    this.modalService.open(content);
  }

  public loadLinkMenu(link: string): void
  {
    this.isCollapsed = !this.isCollapsed;
    //alert(this.router.url + " - " + link);
    //if(this.router.url.indexOf(link) < 0)
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

  allowRoute(roles: string[]): void
  {
    if (roles.some(item => CONST.roles.indexOf(item) >= 0))
    {
      this.isWithoutRole = false;
    } else
    {
      this.isWithoutRole = true;
    }
  }

  /**
   * Mostra la lista delle sottovoci del menu selezioanto
   * dove è presente il subMenu
   */
  public showHideSubMenu(item: PrimeNgMenuItems): void
  {
    if (item.show)
    {
      //item.icon = 'fa fa-arrow-circle-down';
      item.icon = 'arrow-circle-down';
      item.show = false;
    } else
    {
      //item.icon = 'fa fa-arrow-circle-up';
      item.icon = 'arrow-circle-up';
      item.show = true;
    }
  }


  ngOnDestroy()
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  public cambiaGruppo(): void { this.router.navigate(["private/gestione-gruppo"]); }

  get showCambiaGruppo(): boolean 
  {
    let ugk = LocalSessionServiceService.USER_GROUPS_KEY;
    let show = false;
    if (this.lss.containsValue(ugk))
      show = this.lss.getValue(ugk).length > 1;
    return show;
  }

  public getLogoUrl():string{
    return environment.linkFunzioniTrasverali + "/download-logo?applicazione=" + environment.appName + "&id="+this.ente.id;
   }
}
