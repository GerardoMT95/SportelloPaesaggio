import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';


@Injectable({
  providedIn: 'root'
})
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
