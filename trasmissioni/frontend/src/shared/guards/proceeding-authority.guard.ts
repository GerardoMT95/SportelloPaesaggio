/*import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivate, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { LoggedUser } from 'src/app/components/model/logged-user';

@Injectable({
	providedIn: 'root'
})
export class ProceedingAuthorityGuard implements CanActivate {
	roles: string[] = [];
	constructor(private userService: UserService, private router: Router) {}

	async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
		this.userService.getObservableUser().subscribe((response: LoggedUser) => {
			let roles = new Set<string>();
			response.roles.forEach(item => {
				roles.add(item);
			});
			this.roles = Array.from(roles);
		});

		return this.allowRoute(this.roles);
	}

	allowRoute(roles: string[]): boolean {
		if (roles.some(item => item === 'ap')) {
			return true;
		} else {
			this.router.navigate(['public/fascicolo']);
		}
	}
}
*/