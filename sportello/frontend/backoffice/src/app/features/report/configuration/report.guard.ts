import { GroupRole, GroupType } from './../../../shared/models/models';
import { UserService } from './../../../shared/services/user.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Injectable } from "@angular/core";
import { GroupedObservable, Observable } from 'rxjs';

@Injectable()
export class ReportGuard implements CanActivate
{
    constructor(private router: Router, private user: UserService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree>
    {
        let role: GroupRole = this.user.role;
        let group:GroupType = this.user.groupType;
        let canPass = (role === GroupRole.D) && 
        [GroupType.EnteDelegato,GroupType.Regione].includes(group);
        if(!canPass)
            this.router.navigate(['/login']);
        return canPass;
    }
    
}