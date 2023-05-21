import { LoadingService } from 'src/app/services/loading.service';
import { CONST } from 'src/shared/constants';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { DataConf } from './data-conf';

@Component({
  selector: 'app-gestione-pec',
  templateUrl: './gestione-pec.component.html',
  styleUrls: ['./gestione-pec.component.scss']
})
export class GestionePECComponent implements OnInit 
{
  public form: FormGroup;
  public validation: boolean = false;

  public readonly caspost: any[] = DataConf.formConf["casellapost"];
  public readonly mailIn : any[] = DataConf.formConf["mailin"];
  public readonly mailOut: any[] = DataConf.formConf["mailout"];

  //EYE PASSWORD
  public tipo:string = 'password';
  public classEye:boolean = true;

  //oggetto utilizzato per l'alert
  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private formBuilder: FormBuilder,
              private loadingService: LoadingService,
              private service: AdminService) { }

  ngOnInit() 
  {
    this.buildForm();
    this.loadingService.emitLoading(true);
    this.service.getConfigurationPec().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.form.patchValue({...response.payload});
      this.loadingService.emitLoading(false);
    });
  }

  private buildForm(): void
  {
    this.form = this.formBuilder.group({
      //Casella postale
      pecIndirizzo: [null, [Validators.required, Validators.email, Validators.maxLength(255)]],
      pecNome: [null, [Validators.required, Validators.maxLength(255)]],
      pecUsername: [null, [Validators.required, Validators.maxLength(255)]],
      pecPassword: [null, [Validators.required, Validators.maxLength(255)]],
      //Conf mail in
      pecHostIn: [null, [Validators.required, Validators.maxLength(255)]],
      pecProtocolloIn: [null],
      pecPortaSslIn: [null, [Validators.required]],
      pecTipoProtocolloIn: [null, [Validators.required]],
      pecSslIn: [null, [Validators.required]],
      pecStartTlsIn: [null, [Validators.required]],
      pecAutenticazioneIn: [null],
      //Conf mail out
      pecHostOut: [null, [Validators.required, Validators.maxLength(255)]],
      pecPortaSslOut: [null, [Validators.required]],
      pecPortaTlsOut: [null, [Validators.required]],
      pecTipoProtocolloOut: [null, [Validators.required, Validators.maxLength(255)]],
      pecSslOut: [null],
      pecTlsOut: [null],
      pecStartTlsOut: [null],
      pecAutenticazioneOut: [null]
    });
  }

  public salva()
  {
    this.validation = true;
    if(this.form.valid)
    {
      this.loadingService.emitLoading(true);
      this.service.saveOrUpdateConfigurationPec(this.form.getRawValue()).subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
        {
          this.alertData =
          {
            display: true,
            title: "Successo",
            content: "Operazione effettuata con successo",
            typ: "success",
            extraData: null,
            isConfirm: false,
          }
        }
        this.loadingService.emitLoading(false);
      });
    }
  }

  public resetDefault(): void
  {
    this.loadingService.emitLoading(true);
    this.service.resetConfigurationPec().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this.alertData =
        {
          display: true,
          title: "Successo",
          content: "Operazione effettuata con successo",
          typ: "success",
          extraData: null,
          isConfirm: false,
        }
        this.form.patchValue({...response.payload});
      }
      this.loadingService.emitLoading(false);
    });
  }

  callbackAlert(event: any) 
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso) 
    {
      this.alertData.display = false;
    } 
    else if (event.isConfirm) 
    {
      if (event.extraData && event.extraData.operazione) 
      {
        switch (event.extraData.operazione) 
        {
          case '':
            //TODO
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }
  /**
   * Switch per l'eye password
   */
   switchPassword():void{
    this.classEye = !this.classEye;
    this.tipo = this.classEye ? 'password' : 'text';
  }
}
