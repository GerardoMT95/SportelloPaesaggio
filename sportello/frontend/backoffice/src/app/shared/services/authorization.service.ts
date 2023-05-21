import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService
{
  private userRoles: Set<string>;

  constructor(private currentUserService: UserService){}

  public checkAuthorization(path: any): Observable<boolean>
  {
    return this.currentUserService.getObservableUser().pipe(
      map(user =>
      {
        let l = new Set<string>();
        for (let i in user.roles)
        {
          l.add(user.roles[i]);
        }
        this.userRoles = l.size ? l : l.add('');
        return this.doCheckAuthorization(path);
      })
    );
  }

  private doCheckAuthorization(path: any): boolean
  {
    const keys = this.parsePath(path);
    if (keys && keys.length && this.userRoles.size)
      return keys.some(permittedRole => this.userRoles.has(permittedRole));
    else
      return false;
  }

  private parsePath(path: any): string[]
  {
    if (typeof path === 'string')
    {
      return path.split('.');
    }
    if (Array.isArray(path))
    {
      return path;
    }
    return [];
  }

}
