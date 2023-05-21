import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { environment } from 'src/environments/environment';
import { CONST } from '../constants';
import { BaseResponse } from './../components/model/base-response';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalService
{

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService)
  { 

  }


  //Redirect in base al ruolo dell'utente
  public redirect(urlDiPartenza: string = ""): void
  {
    if (this.userService.isLogged())
    {
      let gruppoUtentePredefinito=null;
      if(urlDiPartenza && urlDiPartenza != ""){
        //se nell'url ho il gruppo con cui proseguire....
        let urlPartenzaObj=new URL(urlDiPartenza,environment.baseUrl);
        if(urlPartenzaObj.searchParams.has(CONST.GRUPPO_UTENTE_HEADER)){
          gruppoUtentePredefinito=urlPartenzaObj.searchParams.get(CONST.GRUPPO_UTENTE_HEADER);  
        }
      }
      if(this.userService.actualGroup)
      {
        let redirectUrl = urlDiPartenza && urlDiPartenza != "" ? urlDiPartenza : "/gestione-istanze";
        this.router.navigateByUrl(redirectUrl);
      }
      else
      {
        if (urlDiPartenza)
          this.router.navigate(['/gestione-gruppo', { previousUrl: urlDiPartenza,gruppoUtentePredefinito:gruppoUtentePredefinito }]);
        else
         this.router.navigate(['/gestione-gruppo']);
      }
      
    } 
    else
      this.router.navigate(['/error']);
  }


  refreshSession(): Promise<BaseResponse<boolean>>
  {
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL + "/logged/user/refresh.pjson", CONST.httpOptions).toPromise().then(data => data);
  }
}