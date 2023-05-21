import { BaseResponse } from '../components/model/base-response';
import { CONST } from 'src/app/shared/constants';
import { UserService } from './user.service';
import { Injectable } from '@angular/core';
 import { HttpClient } from '@angular/common/http';
 import { Router } from '@angular/router';
 
@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private userService:UserService
  ) { }


  //Redirect in base al ruolo dell'utente
  public redirect():void{
    if(this.userService.isLogged())
      this.router.navigate(['/gestione-istanze/consultazione-istanze']);
    else
      this.router.navigate(['/error']);
  }


  refreshSession():Promise<BaseResponse<boolean>>{
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL +"/logged/user/refresh.pjson",CONST.httpOptions)
    .toPromise().then(data => data);
  }



}
