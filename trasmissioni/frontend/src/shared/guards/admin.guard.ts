import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { GroupType } from 'src/app/components/model/enum';
import { UserService } from 'src/app/services/user.service';

@Injectable({
	providedIn: 'root'
})
export class AdminGuard implements CanActivate
{
	constructor(private user: UserService,
		private router: Router) { }

	async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
	{
		if (!this.user.isLogged ||
			!this.user.actualGroup ||
			this.user.groupType !== GroupType.ADMIN)
		{
			console.error("Utente non ammesso nella sezione di amministrazione!");
			this.router.navigate(['public/fascicolo']);
		}
		else
		{
			return true;
		}
	}
}
