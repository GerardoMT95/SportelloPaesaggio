import { customEmailValidator } from 'src/app/shared/validators/customValidator';
import { ButtonType, DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from 'src/app/shared/constants';
import { Ente } from 'src/app/shared/models/models';
import { DestinatarioIstituzionaleDto } from '../../models/admin-functions.models';
import { Button } from 'primeng/primeng';

@Component({
  selector: 'app-dettaglio-ente',
  templateUrl: './dettaglio-ente.component.html',
  styleUrls: ['./dettaglio-ente.component.css']
})
export class DettaglioEnteComponent implements OnInit/* extends AuthComponent */
{
  @Input("dettaglio") dettaglio: DestinatarioIstituzionaleDto;
  @Output("action") action = new EventEmitter<Ente>();

  // Form dettaglio
  public form: FormGroup;

  // dialog for annulla
  public displayBack: boolean = false
  public contentBack: string;
  public titleBack: string;
  public typBack: string = 'info';
  public isConfirmBack: boolean;

  // dialog for : save,  info editor
  public displayEsito: boolean = false
  public contentEsito: string;
  public titleEsito: string;
  public typEsito: string = 'success';
  public isConfirmEsito: boolean;

  public valida: boolean = false;
  public tipologiaEnte: SelectItem[] = CONST.listaTipologieMockup;

  constructor(private router: Router,
    private translateService: TranslateService,
    private fb: FormBuilder,
    private dialog: CustomDialogService)
  {
    /* super(router, lss); */
  }

  ngOnInit()
  {
    this.buildForm();
  }

  /**
* Build form
*/
  buildForm(): void
  {
    this.form = this.fb.group
      ({
        id: [this.dettaglio.id],
        nome: [this.dettaglio.nome],
        tipoEnte: [this.dettaglio.tipoEnte, [Validators.required]],
        pec: [this.dettaglio.pec, [customEmailValidator]],
        email: [this.dettaglio.email, [customEmailValidator]]
      })
  }

  /**
   * Open dialog for confirm go back
   */
  public back(): void
  {
    if(this.form.touched){
      let m = this.translateService.instant('indirizziEnti.dialog.back.content');
      let t = this.translateService.instant('indirizziEnti.dialog.back.title');
      let f = (buttonPressed) => { 
        if(buttonPressed==ButtonType.OK_BUTTON){this.action.emit(null);}
      }
      this.dialog.showDialog(true, m, t, DialogButtons.CONFERMA_CHIUDI, f.bind(this), DialogType.INFORMATION);
    }else{
      this.action.emit(null);
    }
    
  }

  /**
  * Open dialog confirm save
  */
  public openSalva()
  {
    this.valida = true;
    if (this.form.valid)
    {
      this.valida = false;
      /* this.alertData =
      {
        title: this.translateService.instant('indirizziEnti.dialog.salva.title'),
        content: this.translateService.instant('indirizziEnti.dialog.salva.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "save" },
        display: true
      }; */
      let m = this.translateService.instant('indirizziEnti.dialog.salva.content');
      let t = this.translateService.instant('indirizziEnti.dialog.salva.title');
      let f = (buttonPressed) => { 
        if(buttonPressed==ButtonType.OK_BUTTON){
          this.action.emit(this.form.getRawValue()); 
        }
      }
      this.dialog.showDialog(true, m, t, DialogButtons.CONFERMA_CHIUDI, f.bind(this), DialogType.INFORMATION);
    }

  }

  /* callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
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
          case 'goBack':
            this.action.emit(null);
            break;
          case 'save':
            this.action.emit(this.form.getRawValue());
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  } */

}
