import { UserService } from 'src/app/shared/services/user.service';
import { GroupType } from './../../../shared/models/models';
import { DataService } from './../services/data-service/data.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from "@angular/router";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { buildForbiddenRouteslist } from '../functions/utils';
import { getGroupType } from 'src/app/core/functions/generic.utils';
import { LocalSessionServiceService } from 'src/app/shared/services/local-session-service.service';

type booleaOrUrlTree = boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree>;

@Injectable()
export class CanActivateGestioneIstanza implements CanActivate
{
    constructor(private dataService: DataService,
                private userService: UserService,
                private _router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): booleaOrUrlTree
    {
        if(!this.dataService || !this.dataService.fascicolo) return false;
        let groupType: GroupType = getGroupType(this.userService.actualGroup);
        let routesHandler = buildForbiddenRouteslist(this.dataService.status, this.dataService.fascicolo, groupType);
        if(routesHandler.forbiddenRoutes.includes(route.url[route.url.length - 1].toString()))
        {
            let newRoute = ['gestione-istanze', this.dataService.codicePratica, routesHandler.defaultRoute];
            this._router.navigate(newRoute);
            return false;
        }
        return true;
    } 
    
}

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private lss : LocalSessionServiceService, private router: Router) {}
  
    canActivate(route: ActivatedRouteSnapshot,  state: RouterStateSnapshot) {
      if(this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)) {
        return true;
      }
      //passo l'url di partenza alla rotta successiva (login) in modo che dopo l'auth il 
      //redirect lo faccio verso questa rotta
      if(state && state.url){
        this.router.navigate(['login',{previousUrl: state.url}]);
      }else{
        this.router.navigate(['login']);
      }
      
      return false;
    }
  
  }