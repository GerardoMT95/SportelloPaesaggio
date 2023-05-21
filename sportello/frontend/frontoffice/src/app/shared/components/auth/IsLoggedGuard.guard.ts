import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Observable } from 'rxjs';
import { CONST } from '../../../shared/constants';

@Injectable()
export class IsLoggedGuard implements CanActivate{

    constructor(
        private userService:UserService,
        private router: Router
    ){}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean
    {
        if(this.userService.getUser() != null ){
            return true;
        }else{
            this.router.navigate([CONST.REDIRECT_PAGE]);
            return false;
        }
    }
}