import { Component, OnInit, ChangeDetectorRef, Inject } from '@angular/core';
import { LoadingService } from '../../services/loading.service';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from '../../services/user.service';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { LoggedUser } from '../model/logged-user';
import { FormBuilder, Validators, Form, FormGroup } from '@angular/forms';
import { CONST } from '../../constants';
import { Observable } from 'rxjs';
import { SelectItem } from 'primeng/primeng';
import { HttpDominioService } from '../../services/http-dominio.service';
import { HttpClient } from '@angular/common/http';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { BaseResponse } from '../model/base-response';
import { AuthorizationService } from '../../services/authorization.service';
import { AUTH_SERVICE, IAuthService } from '../auth/IAuthService';
import { environment } from 'src/environments/environment';
import { SelectOption } from '../select-field/select-field.component';


@Component({
  selector: 'app-richiesta-abilitazione',
  templateUrl: './richiesta-abilitazione.component.html',
  styleUrls: ['./richiesta-abilitazione.component.css']
})
export class RichiestaAbilitazioneComponent implements OnInit {

  tipoDocumentoOptions$: Observable<SelectOption[]>;
  tipiDocumento:SelectOption[]=[];
  public userLogged: LoggedUser;


  constructor(private lss: LocalSessionServiceService
    , private loadingService: LoadingService
    ,@Inject(AUTH_SERVICE) private authService: IAuthService
    , private fb: FormBuilder
    , private httpDominio: HttpDominioService
    , private http: HttpClient
    , private dialogService: DialogService
    , private userService: UserService) { }
  formRichiesta: FormGroup;
  formData: FormData = new FormData();
  submitted:boolean=false;
  public today: Date = new Date();
  public currentYear:Number=new Date().getFullYear();
  const=CONST;

  ngOnInit() {
    this.userLogged = this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY)
    this.tipoDocumentoOptions$ = this.httpDominio.getDominio('tipoDocumento');
    this.tipoDocumentoOptions$.subscribe(res=>{this.tipiDocumento=res;})
    this.buildForm();
  }

  buildForm() {
    this.formRichiesta = this.fb.group({
      stato: ['', Validators.required],
      citta: [''],
      comune: ['' ],
      provincia: [''],
      via: ['', Validators.required],
      n: ['', Validators.required],
      cap: ['', Validators.required],
      pec: ['', [Validators.required,Validators.email,Validators.pattern(CONST.PATTERN_PEC)]],
      tipo: ['', Validators.required],
      numero: ['', Validators.required],
      rilasciatoIl: [null, Validators.required],
      rilasciatoDa: ['', Validators.required],
      dataScadenza: ['', Validators.required],
      allegatoName: [null, Validators.required]
    });
  }


  richiediAbilitazione() {
    this.formRichiesta.markAllAsTouched();
    this.formRichiesta.updateValueAndValidity();
    this.submitted=true;
    if (this.formRichiesta.invalid) {
      this.dialogService.showDialog(true, 
        'abilitazione.validationMessage',
        'ATTENZIONE',
        DialogButtons.CHIUDI,
          (buttonID: string): void => {
            console.log('Button callback:', buttonID);
          },
          DialogType.WARNING,
          null  );
    }else{
      //chiedo conferma e invio...
      this.dialogService.showDialog(true, 
        'abilitazione.confermi',
        'abilitazione.confirmTitle',
        DialogButtons.CONFERMA_CHIUDI,
          (buttonID: number): void => {
            if(buttonID==1){ //la conferma è sempre con 1
              this.sendRichiesta();      
            }
          },
          DialogType.INFORMATION,
          null  );
    }
    
  }

  erroreNelForm(messaggio:string){
    this.dialogService.showDialog(true, 
      messaggio,
      'abilitazione.esitoTitle',
      DialogButtons.CHIUDI,
      ()=>{},
      DialogType.ERROR,
      null  );
  }
  

  sendRichiesta(){
    let formValue = this.formRichiesta.value;
    //per i campi: stato comune provincia 
    //mando direttamente il valore in chiaro
    formValue['stato']=formValue['stato']['description'];
    if(formValue['comune']){
      formValue['comune']=formValue['comune']['description'];
    }
    if(formValue['provincia']){
      formValue['provincia']=formValue['provincia']['description'];
    }
    if(formValue['tipo']){
      let idx=this.tipiDocumento.findIndex(el=>el.value==formValue['tipo']);
      if(idx>=0){
        formValue['tipo']=this.tipiDocumento[idx].description;
      }else{
          this.erroreNelForm('Tipo documento di identità non codificato!');
          return;
      }
    }else{
      this.erroreNelForm('Tipo documento di identità mancante!');
      return;
    }
    this.formData.delete('data');
    this.formData.append('data', new Blob([JSON.stringify(formValue)], { type: "application/json" }));
    this.loadingService.emitLoading(true);
    this.http.post(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/richiesta-abilitazione.pjson", this.formData)
      .subscribe(
        (data:BaseResponse<string>) => 
        {
          this.loadingService.emitLoading(false);
          if(data.codiceEsito=="OK"){
            this.dialogService.showDialog(true, 
              'abilitazione.esito',
              'abilitazione.esitoTitle',
              DialogButtons.CHIUDI,
              ()=>{
                this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
                this.authService.logout();
              },
              DialogType.INFORMATION,
              'logout'  );
          }/*else{
            this.dialogService.showDialog(true, 
              'abilitazione.esitoError',
              'abilitazione.esitoTitle',
              DialogButtons.CHIUDI,
              ()=>{this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
                this.authService.logout();},
              DialogType.ERROR,
              null  );
          }*/
          
      },
        error => {
          console.log(error);
          this.loadingService.emitLoading(false);
        }
      );

  }

  logout(){
    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    this.authService.logout();
  }

  onFileChange(event) {
    const reader = new FileReader();
    if(event.target.files && event.target.files.length && 
      event.target.files[0].type!="application/pdf" && 
      event.target.files[0].type!="image/png" && 
      event.target.files[0].type!="image/jpeg"){
      this.dialogService.showDialog(true, 
        "Formato errato:"+event.target.files[0].type+" atteso: application/pdf, image/png, image/jpeg !!! ",
          'abilitazione.esitoTitle',
          DialogButtons.CHIUDI,
          ()=>{},
          DialogType.WARNING,
          null  );
      this.formRichiesta.get('allegatoName').patchValue(null);
      return;
    }
    if(event.target.files && event.target.files.length && 
      event.target.files[0].size>=CONST.MAX_1MB){
        let err = 'La dimensione del file(' +
              Math.round(event.target.files[0].size / 1024 / 1024) + ' MB) supera i limiti ammessi (' +
              Math.round(CONST.MAX_1MB / 1024 / 1024) + ' MB)';
        this.dialogService.showDialog(true, 
          err,
            'abilitazione.esitoTitle',
            DialogButtons.CHIUDI,
            ()=>{},
            DialogType.WARNING,
            null  );
      this.formRichiesta.get('allegatoName').patchValue(null);
      return;
    }


    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      //      reader.readAsDataURL(file);
      this.formData.delete('allegato');
      this.formData.append('allegato', file, file.name);

      /*reader.onload = () => {
        this.formRichiesta.patchValue({
          allegato: reader.result
       });
        // need to run CD since file load runs outside of zone
        this.cd.markForCheck();
      };*/
    }
  }

}
