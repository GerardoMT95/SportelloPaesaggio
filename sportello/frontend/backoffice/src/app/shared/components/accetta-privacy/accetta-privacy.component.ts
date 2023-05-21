import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { AccettaPrivacyService } from '../../services/accetta-privacy.service';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { LoadingService } from '../../services/loading.service';
import { CONST } from '../../constants';


@Component({
  selector: 'app-accetta-privacy',
  templateUrl: './accetta-privacy.component.html',
  styleUrls: ['./accetta-privacy.component.css']
})
export class AccettaPrivacyComponent implements OnInit {
  public testoPrivacy:string;

  ///// DIALOG /////
  public display: boolean
  public type: string
  public hasConfirm: boolean
  public content: string
  public title: string
  public extraData: any;

constructor(private formBuilder: FormBuilder
           ,private router: Router
           ,private service:AccettaPrivacyService
           ,private lss:LocalSessionServiceService
           ,private translateService: TranslateService
           ,private loadingService:LoadingService
) { }

ngOnInit() {
  this.loadingService.emitLoading(true);
  this.service.testoPrivacy()
  .subscribe(response =>{
    if(response && response.codiceEsito && response.codiceEsito == CONST.OK){
      this.testoPrivacy = response.payload;
    }
    this.loadingService.emitLoading(false);
  });
}

public openAccettaPrivacy():void{
    this.type = 'info'
    this.hasConfirm = true;
    this.translateService.get("privacyAccetta.contentConfirm")
    .subscribe(content=>{
      this.content = content;
      this.translateService.get("privacyAccetta.titleConfirm")
      .subscribe(title=>{
        this.title = title;
        this.display = true;
      })
    })
}

public callback(event: any):void{
  if (event.isConfirm) {
    this.accettaPrivacy();
  }
  this.display = false;
  this.extraData = "";
}

private accettaPrivacy():void{
  this.loadingService.emitLoading(true);
  this.service.accettaPrivacy()
  .subscribe(response =>{
    if(response && response.codiceEsito && response.codiceEsito == CONST.OK){
      this.type = 'success'
      this.hasConfirm = false;
      this.translateService.get("privacyAccetta.contentSuccess")
      .subscribe(content=>{
      this.content = content;
      this.translateService.get("privacyAccetta.titleSuccess")
    .subscribe(title=>{
      this.title = title;
      this.display = true;
    });
      });
      this.router.navigateByUrl("/login");
    }
    this.loadingService.emitLoading(false);
  });
}

}
