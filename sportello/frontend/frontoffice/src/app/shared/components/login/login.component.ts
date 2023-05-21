import { LoggedUser } from '../model/logged-user';
import { BaseResponse } from '../model/base-response';
import { CONST } from '../../constants';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { LoadingService } from '../../services/loading.service';
import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../services/user.service';
import { IAuthService, AUTH_SERVICE } from '../auth/IAuthService';
import { GlobalService } from '../../services/global.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public errore = false;
 
  constructor(
    private router: Router,
    private config: NgbProgressbarConfig,
    private userService: UserService,
    private loadingService:LoadingService,
    private globalService:GlobalService,
    @Inject(AUTH_SERVICE) private authService: IAuthService,
    @Inject(LocalSessionServiceService) private lss) {
      this.config.max = 1000;
      this.config.striped = true;
      this.config.animated = true;
      this.config.type = 'success';
      this.config.height = '20px';
  }

  ngOnInit() {
    if(!this.lss.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)){
      this.authService.login().subscribe((response:any) => {
        if(response.codiceEsito == CONST.OK){
          let user : LoggedUser = response.payload;
          this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
          this.redirect();  
        }else{ 
          this.errore = true;
          this.loadingService.emitLoading(false);
        }
      });
    }else{
      this.redirect();  
    }
  }

  redirect(){
    //qui ricontrollo i ruoli
     this.userService.getRolesMenu().then(
       response=>{
         let rolesFiltered=this.userService.filteredRole(response.payload);
         //spelo i ruoli ammessi nell'applicazione 
         let user= this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY);
         user.roles=rolesFiltered;
         this.lss.storeValue(LocalSessionServiceService.LOGGED_USER_KEY, user);
         if(user.roles.length>0){
           //simulo l'unica scelta del gruppo ammesso in presentazione istanza
           this.lss.storeValue('gruppoScelto', 'RICHIEDENTI');
          }
         this.userService.emitUser();
         if (user.roles.length == 0) {
          setTimeout(() => {
            this.router.navigate(['/richiesta-abilitazione']);
          }, 2000);
        } else if (user.roles) {
          this.errore = false;
          setTimeout(() => {
            this.globalService.redirect();
          }, 2000);
        }
       }
    ).catch(error=>{
      this.errore = true;
      this.loadingService.emitLoading(false);
    }
      
    );
  }

  
}
