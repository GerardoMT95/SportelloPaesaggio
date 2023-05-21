import { Fascicolo } from 'src/app/shared/models/models';
import { UserService } from './../../../../shared/services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from 'src/app/shared/constants';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { GroupType, StatoEnum } from './../../../../shared/models/models';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { IButton } from 'src/app/core/models/dialog.model';
import { TranslateService } from '@ngx-translate/core';
import { copyOf } from 'src/app/core/functions/generic.utils';

@Component({
  selector: 'app-gestione-istanza-protocollazione-page',
  templateUrl: './gestione-istanza-protocollazione-page.component.html',
  styleUrls: ['./gestione-istanza-protocollazione-page.component.scss']
})
export class GestioneIstanzaProtocollazionePageComponent implements OnInit
{
  public it: any = CONST.IT;
  public submitted: boolean = false;
  public formPrt: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private dataService: DataService,
              private fascicoloService: FascicoloService,
              private dialogService:CustomDialogService,
              private loadingService:LoadingService,
              private userService: UserService,
              private translate: TranslateService,
              private router: Router)
  {
    //console.log("codice fascicolo in protocollazione: ", this.dataService.codicePratica);
  }

  ngOnInit() { 
    this.buildForm(); 
  }

  public confirmSave()
  {
    if( [GroupType.EnteDelegato, GroupType.Regione].includes(this.userService.groupType))
    {
      this.submitted = true;
      if (this.formPrt.valid)
      {
        this.loadingService.emitLoading(true);
        this.fascicoloService.callProtocollaFascicolo(this.formPrt.getRawValue()).subscribe(response =>
        {
          this.loadingService.emitLoading(false);
          if (response.codiceEsito === CONST.OK)
          {
            this.dialogService.showDialog(true, 'generic.operazioneOk', 'generic.info',
              [{ id: ButtonType.OK_BUTTON, label: 'generic.prosegui' }],
              (buttonID: number): void =>
              {
                this.dataService.status = StatoEnum.PresaInCarica;
                let fascicolo = copyOf(this.dataService.fascicolo);
                fascicolo.numeroProtocollo = this.formPrt.controls['numeroProtocollo'].value
                fascicolo.dataProtocollo = this.formPrt.controls['dataProtocollo'].value
                this.dataService.fascicolo = fascicolo;
                this.router.navigate(['gestione-istanze', this.dataService.codicePratica, 'istanza-presentata']);
              }, DialogType.SUCCESS);
          }
          else
          {
            this.dialogService.showDialog(true, 'generic.error', 'generic.warning',
              [{ id: ButtonType.OK_BUTTON, label: 'generic.chiudi' }],
              (buttonID: number): void => { }, DialogType.ERROR);
          }
        });
      }
    }
    else
    {
      console.error("Gruppo non abilitato");
    }
    
  }

  get NOW_YEAR(): string { return CONST.NOW_YEAR.toString(); }
  get NOW_DATE(): Date { return CONST.TODAY; }

  private buildForm(): void
  {
    this.formPrt = this.formBuilder.group
    ({
      codiceFascicolo: this.dataService.codicePratica,
      numeroProtocollo: new FormControl(null, [Validators.required]),
      dataProtocollo: new FormControl(null, [Validators.required])
    });
  }

  public save(): void
  {
    let title: string = this.translate.instant("PROTOCOLLAZIONE_TAB.DIALOG.TITLE");
    let message: string = this.translate.instant("PROTOCOLLAZIONE_TAB.DIALOG.CONTENT");
    let buttons: IButton[] = DialogButtons.CONFERMA_CHIUDI;
    this.dialogService.showDialog(true, message, title, buttons, (buttonId: number) =>
    {
      let _this = this;
      if(buttonId === 1)
      {
        _this.confirmSave();
      }
    }, DialogType.WARNING);
  }
}
