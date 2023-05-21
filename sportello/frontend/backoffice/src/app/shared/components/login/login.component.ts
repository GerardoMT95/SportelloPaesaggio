import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { GlobalService } from '../../services/global.service';
import { UserService } from '../../services/user.service';
import { AUTH_SERVICE, IAuthService } from '../auth/IAuthService';
import { LoadingService } from './../../services/loading.service';
import { LocalSessionServiceService } from './../../services/local-session-service.service';
import { LoggedUser } from './../model/logged-user';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  public errore = false;
  private unsubscribe$ = new Subject<void>();

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private config: NgbProgressbarConfig,
    private userService: UserService,
    private loadingService: LoadingService,
    private globalService: GlobalService,
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
  ngOnInit()
  {
    /*gestione dell'url di partenza*/
    /**questo component viene instanziato 2 volte, la prima volta quando arrivo da una guard (canActivate) 
     * e non avendo il token viene effettuato  l'oauth login 
     * al termine dell'autenticazione su wso2 page, viene rediretto alla /login (perdendo il valore di previousUrl)
     * pertanto in sequenza a utente non autenticato accade:
     * 1 onInit -> se urlDipartenza non nullo metto in lss il valore
     * 2 onInit -> mi arriva sicuramente nullo e se è presente nella lss lo prelevo e lo cancello
     */
    let urlDiPartenza = this.activatedRoute.snapshot.paramMap.get('previousUrl');
    if (urlDiPartenza != null)
    {
      //alert('urlDiPartenza -> to lss ' +urlDiPartenza)
      this.lss.storeValue('previousUrl', urlDiPartenza);
    } else if (this.lss.containsValue('previousUrl'))
    {
      urlDiPartenza = this.lss.getValue('previousUrl');
      //alert('urlDiPartenza from lss <-' +urlDiPartenza)
      //urlDiPartenza=this.lss.removeValue('previousUrl');
    }
    /*end gestione dell'url di partenza*/
    if (!this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
    {
      this.authService.login().pipe(takeUntil(this.unsubscribe$)).subscribe(
        async (response: any) =>
        {
          if (response.codiceEsito === 200 || response.codiceEsito == "OK")
          {
            let user: LoggedUser = response.payload;
            /*console.log("utente: ", user);
            if(user.gruppiRuoli.length > 0)
            {
              //TODO
              console.log("TODO: scelta gruppo")
            }*/
            //user.roles = await this.userService.getCurrentlyLoggedUser().toPromise(); //delete only this line
            //per adesso lascio i ruoli che mi arrivano dallo IAM
            this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
            this.lss.storeValue(LocalSessionServiceService.USER_GROUPS_KEY, user.gruppiRuoli);
            this.userService.emitUser();
            this.errore = false;
            //this.globalService.redirect();  
            setTimeout(() =>
            {
              this.globalService.redirect(urlDiPartenza);
            }, 2000);
          } else
          {
            this.errore = true;
            this.loadingService.emitLoading(false);
          }
        });
    } else
    {
      this.globalService.redirect(urlDiPartenza);
    }
  }

  /**
   * metodo temporaneo per renderlo autonomo dal BE 
   */
  ngOnInitOLDED()
  {
    if (!this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY))
    {
      this.authService.login().pipe(takeUntil(this.unsubscribe$)).subscribe(
        async (response: any) =>
        {
          //mappo la risposta del profilatore (profile manager)
          if (response.codiceEsito == 200)
          {
            let user: LoggedUser =
            {
              idEnte: 0,
              nomeEnte: '',
              username: '',
              authorities: [],
              parchi: [],
              parcoSelected: null,
              nome: '',
              cognome: '',
              cf: '',
              roles: [],
              firstName: '',
              lastName: '',
              gruppiRuoli: null
            };
            console.log('payload:{}', response.payload)
            user.nome = response.payload.nome;
            user.firstName = response.payload.nome;
            user.cognome = response.payload.cognome;
            user.lastName = response.payload.lastName;
            response.payload.gruppiRuoli.forEach(element =>
            {
              let codiceGruppo: String = element.codiceGruppo;
              if (codiceGruppo.startsWith("ROLE_"))
              {
                user.roles.push((codiceGruppo.split("ROLE_")[1]).toLowerCase());
              }
              if (codiceGruppo.startsWith("ENTE_"))
              {
                user.authorities.push(
                  {
                    value: (codiceGruppo.split("ENTE_")[1]),
                    label: element.nomeGruppo
                  }
                );
              }
            });
            //console.log('user:{}',user)
            //user.roles = await this.userService.getCurrentlyLoggedUser().toPromise(); //delete only this line
            //per adesso lascio i ruoli che mi arrivano dallo IAM
            this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
            this.userService.emitUser();
            this.errore = false;
            this.globalService.redirect();
            setTimeout(() =>
            {
              this.globalService.redirect();
            }, 2000);
          } else
          {
            this.errore = true;
            this.loadingService.emitLoading(false);
          }
        });
    } else
    {
      this.globalService.redirect();
    }
  }

  ngOnDestroy()
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
