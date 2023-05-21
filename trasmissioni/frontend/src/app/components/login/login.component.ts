import { LoggedUser } from './../model/logged-user';
import { BaseResponse } from './../model/base-response';
import { CONST } from './../../../shared/constants';
import { LocalSessionServiceService } from './../../services/local-session-service.service';
import { LoadingService } from './../../services/loading.service';
import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../services/user.service';
import { IAuthService, AUTH_SERVICE } from '../auth/IAuthService';
import { GlobalService } from '../../services/global.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public errore = false;
  private unsubscribe$ = new Subject<void>();
 
  constructor(private router: Router,
              private activatedRoute:ActivatedRoute,
              private config: NgbProgressbarConfig,
              private userService: UserService,
              private loadingService:LoadingService,
              private globalService:GlobalService,
              @Inject(AUTH_SERVICE) private authService: IAuthService,
              @Inject(LocalSessionServiceService) private lss) 
    {
      this.config.max = 1000;
      this.config.striped = true;
      this.config.animated = true;
      this.config.type = 'success';
      this.config.height = '20px';
  }


  /**
  * TODO: @Antonio La Gatta This should be removed once the service user-info
  * starts to return correct roles for all the clients. Currently just some
  * clients are getting correct roles, but others does not.
 **/
  ngOnInit() {
    /*gestione dell'url di partenza*/
    /**questo component viene instanziato 2 volte, la prima volta quando arrivo da una guard (canActivate) 
     * e non avendo il token viene effettuato  l'oauth login 
     * al termine dell'autenticazione su wso2 page, viene rediretto alla /login (perdendo il valore di previousUrl)
     * pertanto in sequenza a utente non autenticato accade:
     * 1 onInit -> se urlDipartenza non nullo metto in lss il valore
     * 2 onInit -> mi arriva sicuramente nullo e se è presente nella lss lo prelevo e lo cancello
     */
    let urlDiPartenza=this.activatedRoute.snapshot.paramMap.get('previousUrl');
    if(urlDiPartenza!=null){
      this.lss.storeValue('previousUrl',urlDiPartenza);
    }else if(this.lss.containsValue('previousUrl')){
      urlDiPartenza=this.lss.getValue('previousUrl');
    }
    /*end gestione dell'url di partenza*/
    if(!this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)){
      this.authService.login().pipe(takeUntil(this.unsubscribe$)).subscribe(
        async (response:any) => {
        if(response.codiceEsito === 200 || response.codiceEsito==CONST.OK )
        {
          let user : LoggedUser = response.payload;
          this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
          this.userService.emitUser();
          this.errore = false;
          setTimeout(() => {
            this.globalService.redirect(urlDiPartenza);  
          }, 2000);
        }
        else
        { 
          this.errore = true;
          this.loadingService.emitLoading(false);
        }
      });
    }else{
      this.globalService.redirect(urlDiPartenza);  
    }
  }

  ngOnDestroy() {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
  }

}
