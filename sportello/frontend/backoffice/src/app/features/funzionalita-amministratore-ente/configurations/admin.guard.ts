import { GroupRole } from './../../../shared/models/models';
import { UserService } from './../../../shared/services/user.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';

@Injectable()
export class AdminGuard implements CanActivate
{
    constructor(private router: Router, private user: UserService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree>
    {
        let role: GroupRole = this.user.role;
        let canPass = role === GroupRole.A;
        if(!canPass)
            this.router.navigate(['/login']);
        return canPass;
    }
    
}