import { UserService } from '../../../shared/services/user.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, ActivatedRoute } from '@angular/router';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { GroupType } from 'src/app/shared/models/models';

@Injectable()
export class SuperAdminGuard implements CanActivate
{
    constructor(private user: UserService,
                private activatedRoute: ActivatedRoute, 
                private router: Router){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree>
    {
        let urlDiPartenza = this.activatedRoute.snapshot.paramMap.get('previousUrl');
        let canActivate: boolean = this.user.groupType === GroupType.Amministratore; 
        if(!canActivate)
            this.router.navigate([urlDiPartenza]);
        return canActivate;
    }
    
}