import { BaseResponse } from './../model/base-response';
import { LoggedUser } from './../model/logged-user';
import { environment } from './../../../environments/environment';
import { CONST } from './../../../shared/constants';
import { HttpResponse } from '@angular/common/http';
import { LocalSessionServiceService } from './../../services/local-session-service.service';
import { ApiService } from '../../services/api.service';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAuthService } from './IAuthService';
import { Inject } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable()
export class JossoAuth implements IAuthService{
    private assertion_id:string;
    constructor( 
        private api: ApiService, 
        private route: ActivatedRoute,
        @Inject(LocalSessionServiceService) private lss
    ){}
    
    public login() : Observable<HttpResponse<BaseResponse<LoggedUser>>> {
        this.assertion_id =  this.route.snapshot.queryParamMap.get(environment.josso.jossoAssertionId);
        if(this.assertion_id){
            return this.api.performLogin(this.assertion_id);
        }else{
          document.location.href =  CONST.URL_JOSSO_LOGIN;
        }    
    }
 
    public logout(): void {
        document.location.href = CONST.URL_JOSSO_LOGOUT;    
    }

}