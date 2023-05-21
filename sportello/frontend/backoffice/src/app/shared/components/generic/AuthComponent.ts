import { OnInit} from "@angular/core";
import { LocalSessionServiceService } from "../../services/local-session-service.service";
import { Router } from "@angular/router";

export abstract class AuthComponent implements OnInit{

    constructor(
        private routerAuth: Router,
        private lssAuth: LocalSessionServiceService
    ){}

    ngOnInit() {
        //TEMP PER PROVA OFFLINE
        if(!this.lssAuth.containsValue(LocalSessionServiceService.LOGGED_USER_KEY)){
            this.routerAuth.navigate(['/login']);   
        }else{
            this.lssAuth.getValue(LocalSessionServiceService.LOGGED_USER_KEY);
        }
    }
}