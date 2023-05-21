import { Component, OnInit, Output, EventEmitter, Input, Renderer } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from '../../constants';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { LoggedUser } from '../model/logged-user';

@Component({
  selector: 'app-edit-notify',
  templateUrl: './edit-notify.component.html',
  styleUrls: ['./edit-notify.component.css']
})
export class EditNotifyComponent implements OnInit {

  @Input() display:boolean;
  @Input() titolo:string;
  @Input() labelNotifica:string;
  @Output() emitNotify=new EventEmitter<any>(); //null se ha chiuso la finestra e il chiamante deve settare display a false
  public segnalazioneSubmitted=false;
  public const=CONST;
  public userLogged: LoggedUser;
  formSegnalazione: FormGroup;
  constructor(
    private lss: LocalSessionServiceService
    , private fb: FormBuilder
    , private translateService:TranslateService
  ) { }

  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  ngOnInit() {
    this.userLogged = this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY)
    //form per la segnalazione dell'organizzazione mancante
    this.formSegnalazione=this.fb.group({
      nome: [this.userLogged.nome, ],
      cognome: [this.userLogged.cognome,],
      username: [this.userLogged.username, ],
      notifica: [null, [Validators.required,Validators.minLength(50)]]
    });
  }

  sendSegnalazione() {
    this.segnalazioneSubmitted = true;
    if (this.formSegnalazione.valid) {
      this.alertData = {
        display: true,
        title: this.translateService.instant('generic.confirm'),
        content: this.translateService.instant('abilitazione.confirmSegnalazione'),
        typ: "info",
        extraData: { operazione: 'confermaRichiesta' },
        isConfirm: true,
      }
    }
  }

  doSendSegnalazione(){
    let data=this.formSegnalazione.getRawValue();
    this.emitNotify.emit(data);
    this.formSegnalazione.get('notifica').reset();
    this.segnalazioneSubmitted=false;
  }

  onShow(){
  }

  close(){
    this.emitNotify.emit(null);//emissione di chiusura
  }

  ngOnDestroy(){
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
      this.alertData.display = false;
    else if (event.isConfirm && event.extraData && event.extraData.operazione) {
      switch (event.extraData.operazione) {
        case 'confermaRichiesta':
          this.doSendSegnalazione();
          this.alertData.isConfirm = false;
          this.alertData.extraData = null;
          this.alertData.display = false;
          break;
        default:
          this.alertData.isConfirm = false;
          this.alertData.extraData = null;
          this.alertData.display = false;
          break;
      }
    }
    else {
      this.alertData.isConfirm = false;
      this.alertData.extraData = null;
      this.alertData.display = false;
    }
  }
}
