import { Component, EventEmitter, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { CONST } from 'src/app/shared/constants';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { GenericProcedimentoConfigComponent } from '../../configurations/GenericProcedimentoConfigComponent';
import { DichiarazioneDTO } from '../../models/admin-functions.models';

@Component({
  selector: 'app-gestione-dichiarazione-page',
  templateUrl: './gestione-dichiarazione-page.component.html',
  styleUrls: ['./gestione-dichiarazione-page.component.scss']
})
export class GestioneDichiarazionePageComponent extends GenericProcedimentoConfigComponent implements OnInit {
  
  public form: FormGroup;

  constructor(private adminSvc: AdminFunctionsService,
    private loadingService: LoadingService,
    private dialog: CustomDialogService,
    private router: Router,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService,
    private translateService:TranslateService,
    private dialogService:CustomDialogService,
    private fb:FormBuilder
) 
{ 
super(router, route, breadcrumbService, translateService, "TAB_TITLE.STATEMENT_TAB", adminSvc);
}


public submitted: boolean=false;
public loadedFormData = new EventEmitter<boolean>();

public alertData =
{
  display: false,
  title: "",
  content: "",
  typ: "",
  extraData: null,
  isConfirm: false,
};

ngOnInit() {
  this.loadingService.emitLoading(true);
  this.adminSvc.getDichiarazioneCorrente(this.idTipoProcedimento).subscribe(
      response=>{
        this.buildForm(response.payload);
        this.loadedFormData.next(true);
        this.loadingService.emitLoading(false);
      }
  );
}

private buildForm(dichiarazioneCorrente:DichiarazioneDTO){
  this.form = this.fb.group({
    labelCb:[dichiarazioneCorrente.labelCb,[Validators.required]],
    testo:[dichiarazioneCorrente.testo,[Validators.required]],
  });
}

public openSalva() {
  this.submitted=true;
  if (this.form.valid) {
    this.alertData =
    {
      title: this.translateService.instant('templateDocumentazioni.dialog.salva.title'),
      content: this.translateService.instant('templateDocumentazioni.dialog.salva.content'),
      typ: "info",
      isConfirm: true,
      extraData: { operazione: "save" },
      display: true
    };
  }else{
    this.alertData =
  {
    title: this.translateService.instant('generic.warning'),
    content:this.translateService.instant('generic.allRequired'),
    typ: "warning",
    isConfirm: false,
    extraData: {},
    display: true
  };
  }
}


public back(): void {
  if(this.form.touched){
    this.alertData =
    {
      title: this.translateService.instant('templateComunicazioni.dialog.back.title'),
      content: this.translateService.instant('templateComunicazioni.dialog.back.content'),
      typ: "info",
      isConfirm: true,
      extraData: { operazione: "goBack" },
      display: true
    };
  }else{
    this.doGoBack();
  }
}

public openInfo(testo:string): void
{
  this.alertData =
  {
    title: this.translateService.instant('templateComunicazioni.dialog.info.title'),
    content:testo, //this.translateService.instant('templateComunicazioni.dialog.info.content'),
    typ: "info",
    isConfirm: false,
    extraData: {},
    display: true
  };
}

callbackAlert(event: any) {
  this.alertData.isConfirm = false;
  if (event.isChiuso) {
    this.alertData.display = false;
  }
  else if (event.isConfirm) {
    if (event.extraData && event.extraData.operazione) {
      switch (event.extraData.operazione) {
        case 'save':
          this.doSaveData();
          break;
        case 'goBack':
          this.doGoBack()
          break;
        default:
          break;
      }
    }
    this.alertData.display = false;
  }
}

doGoBack(){
  super.back();
}

doSaveData(){
  let formArrData=this.form.getRawValue();
  this.loadingService.emitLoading(true);
  this.adminSvc.updateDichiarazioneCorrente(this.idTipoProcedimento,formArrData).subscribe(response=>{
    this.loadingService.emitLoading(false);
    if(response.codiceEsito==CONST.OK && response.payload>=1){
      this.alertData =
        {
          title: "generic.info",
          content: "generic.operazione_ok",
          typ: "success",
          display: true,
          extraData: null,
          isConfirm: false
        };
        this.form.markAsUntouched();
    }
  });
}


}


