import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
 import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  private readonly WORKFLOW_EVENTS = environment['roles'];
  private userRoles: Set<string>;
  // do your remember the step 1 ? it is used here
  constructor(private currentUserService: UserService) {
  }
  // returns a boolean observable
  public checkAuthorization(path: any): Observable<boolean> {
    // we are loading the roles only once
    //if (!this.userRoles) {
      return this.currentUserService.getObservableUser().pipe(
        map(user => {
          let l = new Set<string>();
          for(let i in user.roles){
            l.add(user.roles[i]);
          }
          this.userRoles = l;
          //this.doCheckAuthorization(path);
          return this.doCheckAuthorization(path);
        })
      );
    //}
    //return of(this.doCheckAuthorization(path));
  }

  private doCheckAuthorization(path: any): boolean {
    const keys = this.parsePath(path);
    if (keys.length) {
      const entry = this.findEntry(this.WORKFLOW_EVENTS, keys);
      if (entry && entry['permittedRoles'] && this.userRoles.size) {
        return entry.permittedRoles.some(permittedRole => this.userRoles.has(permittedRole));
      }
    }
    return false;
  }

  private parsePath(path: any): string[] {
    if (typeof path === 'string') {
      return path.split('.');
    }
    if (Array.isArray(path)) {
      return path;
    }
    return [];
  }

  private findEntry(currentObject: any, keys: string[], index = 0) {
    const key = keys[index];
    if (currentObject[key] && index < keys.length - 1) {
      return this.findEntry(currentObject[key], keys, index + 1);
    } else if (currentObject[key] && index === keys.length - 1) {
      return currentObject[key];
    } else {
      return false;
    }
  }
  
}
