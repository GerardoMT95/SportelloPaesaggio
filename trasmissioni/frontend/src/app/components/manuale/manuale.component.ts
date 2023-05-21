import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { AllegatoService } from 'src/app/services/allegato.service';
import { ErrorService } from 'src/app/services/error.service';
import { LoadingService } from 'src/app/services/loading.service';
import { UserService } from 'src/app/services/user.service';
import { GroupType } from '../model/enum';

@Component({
  selector: 'app-manuale',
  templateUrl: './manuale.component.html',
  styleUrls: ['./manuale.component.css']
})
export class ManualeComponent implements OnInit {

  constructor(
    private loadingService: LoadingService
    ,private allegatoService:AllegatoService
    ,private userService:UserService
    ,private adminService:AdminService
    ,private errorService:ErrorService
    ) { }

  ngOnInit() {
    setTimeout(() => {
      this.loadingService.emitLoading(false);
    });
  }

  isAdmin(): boolean { 
    return this.userService.groupType == GroupType.ADMIN;/* this.user.role && this.user.role === GroupRole.A; */ 
  }

  public downloadAdmin():void{
    let getManuale$=this.adminService.downloadManuale();
    if(!this.isAdmin()){
      this.errorService.emitError("CUSTOM-WARNING:Ruolo non consentito !!!")
    }else{
      this.doDownload(getManuale$);
    }
  }

  public download():void{
    let getManuale$=this.allegatoService.downloadManuale();
    this.doDownload(getManuale$);
  }

  public doDownload(getManuale$:Observable<HttpResponse<Blob>>):void{
    this.loadingService.emitLoading(true);
    getManuale$
    .subscribe(data =>{
      let blob:Blob = data.body;
      this.loadingService.emitLoading(false);
        let fileName : string = "Manuale Utente.pdf"
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob, fileName);
        } else {
          var link = document.createElement("a");
          if (link.download !== undefined) {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", fileName);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          }
      }
    }, error => {
      this.loadingService.emitLoading(false);
    }, () => {
      this.loadingService.emitLoading(false);
    });
    ;
  }
}
