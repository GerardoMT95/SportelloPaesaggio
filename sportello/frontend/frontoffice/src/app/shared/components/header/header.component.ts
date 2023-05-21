import { EnteCss } from './../model/model';
import { CONST } from '../../constants';
import { environment } from '../../../../environments/environment';
import { ApiService } from '../../services/api.service';
import { Lang, Menu } from '../model/model';
import { Component, OnInit, Inject, EventEmitter, Output, Input} from '@angular/core';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoggedUser } from '../model/logged-user';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
 import { UserService } from '../../services/user.service';
import { TranslateService } from '@ngx-translate/core';
import { IAuthService, AUTH_SERVICE } from '../auth/IAuthService';
import { Router } from '@angular/router';
import { LoadingService } from '../../services/loading.service';
import { ErrorService } from '../../services/error.service';
import { MAIN_MENU } from './menu';
import { PrimeNgMenu } from 'src/app/core/models/menu.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {

  public ente:EnteCss;
  @Input("zoomMin")
  public zoomMin: number;
  @Input("zoomMax")
  public zoomMax: number;
  @Input("zoom")
  public zoom: number;
  @Output() zoomPagina: EventEmitter<boolean> = new EventEmitter();

  public isPublic:boolean = true;
  public isCollapsed = true;
  public menuLoaded:Promise<boolean>;
  public menuList:PrimeNgMenu={};
  public user:LoggedUser;
  public languageList:Lang[] = CONST.AVAILABLE_LANGUAGE;
  public selectedLanguage:string;
  public multiLang:boolean = environment.multiLang;
  public roles:string[] = [];
  public enti:string[] = [];
  public showRoles:boolean = false;
  public developer:boolean = CONST.DEVELOPER;
  constructor(
    private config: NgbDropdownConfig,
    private api:ApiService,
    private userService:UserService,
    private lss:LocalSessionServiceService,
    private translateService:TranslateService,
    private router: Router,
    private modalService: NgbModal,
    private loadingService:LoadingService,
    private errorService:ErrorService,
    @Inject(AUTH_SERVICE) private authService: IAuthService
  ) {
    this.config.placement = 'bottom-right';
    this.user = this.userService.getUser();
    this.selectedLanguage = this.translateService.getDefaultLang();
    this.subscribeUser();
  }
 
  ngOnInit() {
      this.userService.enteEmitter.subscribe(ente =>{
      this.ente = ente;
    })
    //qui imposto le voci di menu' e a seguire le disattivo se l'utente non ha i ruoli
    this.menuList=MAIN_MENU;
    
    if(this.userService.isLogged())
      this.isPublic = false;

    this.userService.userObservable.subscribe(result => {
      this.user = result;
      this.getMenu();
    })

    if(this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)){
      this.getMenu();
    }
  }

  setLanguage(lang:Lang):void{
    this.translateService.setDefaultLang(lang.code);
    this.translateService.use(lang.code);
    this.selectedLanguage = lang.code;
  }

  getMenu():void{
    this.userService.getRolesMenu().then(resp=>{
      if(resp.codiceEsito==CONST.OK){
        // RISCRIVO I RUOLI DELL'UTENTE SALVATO IN SESSION STORAGE
        this.roles = resp.payload;
        this.menuLoaded = Promise.resolve(true);
      }
      this.loadingService.emitLoading(false);
    });
  }
  

  login():void{
    this.router.navigateByUrl('/login');
  }

  logout():void{
    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    this.authService.logout();
  }


  public subscribeUser():void{
    this.userService.userObservable.subscribe((user:LoggedUser)=>{
      this.user = user;
    });
  }

  public closeRoleDialog():void{
    //this.modalService.open(content,
    //this.showRoles = false;
  }
  public openRoleDialog(content):void{
    this.modalService.dismissAll();
    this.modalService.open(content, {
      centered: true,
      backdrop: 'static'
     });
  }
   
  public loadLinkMenu(link:string ):void{
      this.isCollapsed = !this.isCollapsed;
      //alert(this.router.url + " - " + link);
      //if(this.router.url.indexOf(link) < 0)
      if(this.router.url != link)
          this.loadingService.emitLoading(true);
      else
          this.loadingService.emitLoading(false);
  }

  public closePopup():void{
    if(this.developer)
      this.logout();
    else
      window.close();
  }

  public getLogoUrl():string{
    return environment.linkFunzioniTrasverali + "/download-logo?applicazione=PAE_PRES_IST&id="+this.ente.id;
   }
}
