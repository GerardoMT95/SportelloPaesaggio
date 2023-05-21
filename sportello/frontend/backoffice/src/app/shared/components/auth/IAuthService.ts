import { BaseResponse } from '../model/base-response';
import { InjectionToken } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoggedUser } from '../model/logged-user';

export interface IAuthService { 
    login():Observable<HttpResponse<BaseResponse<LoggedUser>>>;
    logout():void;
}
export let AUTH_SERVICE = new InjectionToken<IAuthService>('IAuthService');
