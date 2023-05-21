import { customEmailValidator } from 'src/app/shared/validators/customValidator';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { LoggedUser, UtenteAttributeBean } from '../model/logged-user';
import { LoadingService } from '../../services/loading.service';
import { UserService } from '../../services/user.service';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { CONST } from '../../constants';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { GestioneGruppoService } from '../../services/gestione-gruppo/gestione-gruppo.service';

@Component({
  selector: 'app-handle-mail-pec-page',
  templateUrl: './handle-mail-pec-page.component.html',
  styleUrls: ['./handle-mail-pec-page.component.scss']
})
export class HandleMailPecPageComponent implements OnInit 
{
  public userLogged: LoggedUser;
  public form: FormGroup;
  public submitted: boolean = false;

  constructor(private loading: LoadingService,
              private userService: UserService,
              private service: GestioneGruppoService,
              private fb: FormBuilder,
              private dialog: CustomDialogService) { }

  public ngOnInit(): void 
  {
    this.loading.emitLoading(false);
    this.userLogged = this.userService.getUser();
    console.log(this.userLogged);
    this.buildForm();
  }

  private buildForm(): void
  {
    this.form = this.fb.group({
      username: [this.userLogged.username],
      nome: [this.userLogged.firstName],
      cognome: [this.userLogged.lastName],
      mail: [this.userLogged.emailAddress, [Validators.required, customEmailValidator]],
      pec: [this.userLogged.pec, [Validators.required, customEmailValidator]],
      phoneNumber: [this.userLogged.phone, [Validators.required, Validators.pattern(CONST.PHONE_PATTERN)]]
    });
  }

  public cambiaMailPec(): void
  {
    this.submitted = true;
    if(this.form.valid)
    {
      this.submitted = false;
      let bean: UtenteAttributeBean = this.form.getRawValue();
      this.loading.emitLoading(true);
      this.service.updateUtenteAttribute(bean).subscribe(response =>
      {
        this.loading.emitLoading(false);
        if(response.payload)
        {
          this.userService.updateMailPec(bean.mail, bean.pec, bean.phoneNumber);
          this.dialog.showDialog(true, "Indirizzi email e pec aggiornati correttamente", 
                                 "Successo", DialogButtons.CHIUDI, null, DialogType.SUCCESS);
        }
          
      });
    }
  }

}
