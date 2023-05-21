import { Component, OnInit, ChangeDetectorRef, Inject, OnDestroy } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, Validators, Form, FormGroup, ValidationErrors } from '@angular/forms';
import { CONST } from '../../constants';
import { Observable, combineLatest, Subject } from 'rxjs';
import { SelectItem, SelectItemGroup } from 'primeng/primeng';
import { HttpClient } from '@angular/common/http';
import { LoadingService } from 'src/app/services/loading.service';
import { IAuthService, AUTH_SERVICE } from 'src/app/components/auth/IAuthService';
import { LoggedUser } from 'src/app/components/model/logged-user';
import { LocalSessionServiceService } from 'src/app/services/local-session-service.service';
import { BaseResponse } from 'src/app/components/model/base-response';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { PlainTypeStringId, GruppiRuoliDTO } from 'src/shared/models/plain-type-string-id.model';
import { fromGroupToGroupType } from 'src/app/components/functions/genericFunctions';
import { takeUntil } from 'rxjs/operators';
import { GestioneGruppoService } from 'src/app/services/gestione-gruppo.service';
import { UtenteAttributeDTO } from 'src/app/components/model/model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-richiesta-abilitazione',
  templateUrl: './richiesta-abilitazione.component.html',
  styleUrls: ['./richiesta-abilitazione.component.css']
})
export class RichiestaAbilitazioneComponent implements OnInit,OnDestroy {

  tipoDocumentoOptions$: Observable<SelectItem[]>;
  public userLogged: LoggedUser;
  public const = CONST;
  public organizzazioni: SelectItemGroup[] = [];
  private gruppiAttivi: PlainTypeStringId[];
  public gruppiUtente: PlainTypeStringId[] = [];
  public ruoliAttiviOrganizzazione = new Map<String, Set<String>>();//{ED_43 = [A,D,F]}
  public ruoli: SelectItem[] = [
    { value: 'A', label: 'Amministratore', disabled: false },
    { value: 'D', label: 'Dirigente', disabled: false },
    { value: 'F', label: 'Funzionario', disabled: false }
  ];
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };
  loaded: boolean = false;
  public displayOrganizzazioneMancante=false;
  public myGruppi:GruppiRuoliDTO[];

  constructor(private lss: LocalSessionServiceService
    , private loadingService: LoadingService
    , @Inject(AUTH_SERVICE) private authService: IAuthService
    , private fb: FormBuilder
    , private http: HttpClient
    , private translateService: TranslateService
    , private router: Router 
    , private autPaeSvc: AutorizzazioniPaesaggisticheService
    , private gestioneGruppoService: GestioneGruppoService) {
     }
  formRichiesta: FormGroup;
  formData: FormData = new FormData();
  submitted: boolean = false;
  public testoPrivacy:string;
  public testoDichMendaci:string;
  public today: Date = new Date();
  public currentYear: Number = new Date().getFullYear();
  private unsubscribe$=new Subject<void>();
  public oldData:UtenteAttributeDTO=null;
  
  ngOnDestroy(){
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  withoutRole(codiceGruppo: string): string {
    return codiceGruppo.split('_')[0] + '_' + codiceGruppo.split('_')[1];
  }
  role(codiceGruppo: string): string {
    return codiceGruppo.split('_')[2];
  }

  ngOnInit() {
    this.userLogged = this.lss.getValue(LocalSessionServiceService.LOGGED_USER_KEY)
    /**this.userLogged.gruppiRuoli.forEach(gruppo => {
      this.gruppiUtente.push({ id: gruppo.codiceGruppo, nome: gruppo.descrizioneGruppo });
    });*/
    this.testoDichMendaci='abilitazione.dichMendaci';
    this.loadingService.emitLoading(true);
    //prelevo tutti i gruppi attivi e anche quelli su cui sono già abilitato....
    combineLatest(this.autPaeSvc.getGruppiOrganizzazioni(),
                  this.autPaeSvc.getTestoPrivacy(),
                  this.gestioneGruppoService.readGruppi(),
                  this.autPaeSvc.getLastRichiestaAbilitazioneData())
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(
      ([response,responsePrivacy,responseGruppi,responseOldData])=>{
         this.myGruppi=responseGruppi.payload;
        if(this.myGruppi && this.myGruppi.length>0){
          this.myGruppi.forEach(tipoOrganizzazione=>{
            tipoOrganizzazione.ruoli.forEach(gruppo=>
              {
                this.gruppiUtente.push({ id: gruppo.id, nome: gruppo.nome });
              });
          });
          this.oldData=responseOldData.payload;
        }
      this.testoPrivacy=responsePrivacy.payload;
      this.gruppiAttivi = response.payload;
      //mi creo una lista di organizzazzioni e la lista di ruoli e poi quando sceglie l'utente, verifico che 
      //esiste il gruppo relativo
      this.gruppiAttivi.forEach(gruppo => {
        //salto l'elaborazione dei gruppi a cui già appartengo
        let idxMieiGruppi = this.gruppiUtente.findIndex(el => el.id == gruppo.id);
        if (idxMieiGruppi >= 0) return;
        let index = this.organizzazioni.findIndex(el => el.label == fromGroupToGroupType(gruppo.id.split('_')[0]));
        if (index < 0) {
          //aggiungo il tipo organizzazione
          this.organizzazioni.push({ label: fromGroupToGroupType(gruppo.id.split('_')[0]), items: [] });
          index = this.organizzazioni.findIndex(el => el.label == fromGroupToGroupType(gruppo.id.split('_')[0]));
        }
        let indexOrganizzazione = this.organizzazioni[index].items.findIndex(el => el.value == this.withoutRole(gruppo.id));
        if (indexOrganizzazione < 0) {//aggiungo l'organizzazione a meno del ruolo
          this.organizzazioni[index].items.push({ value: this.withoutRole(gruppo.id), label: gruppo.nome });
        }
        //aggiungo in mappa dei ruoli ammessi per l'organizzazione
        if (!this.ruoliAttiviOrganizzazione.has(this.withoutRole(gruppo.id))) {
          this.ruoliAttiviOrganizzazione.set(this.withoutRole(gruppo.id),
            new Set<String>([this.role(gruppo.id)]));
        } else {
          this.ruoliAttiviOrganizzazione.get(this.withoutRole(gruppo.id))
            .add(this.role(gruppo.id));
        }
      });
      this.loaded = true;
      this.loadingService.emitLoading(false);
      this.buildForm();
      if(!this.testoPrivacy){
        this.alertData = {
          display: true,
          title: this.translateService.instant('abilitazione.validationTitle'),
          content: this.translateService.instant('abilitazione.informativaPrivacyAlert'),
          typ: "danger",
          extraData: null,
          isConfirm: false,
        }
      }
    });
  }

  buildForm() {
    let telefono='';
    let fax='';
    //riprendo questi due campi dal valore eventuale dell'ultima richiesta abilitazione
    if(this.oldData ){
      telefono=this.oldData.phoneNumber;
      fax=this.oldData.mobileNumber;
    }
    this.formRichiesta = this.fb.group({
      nome: [this.userLogged.firstName, [Validators.required,Validators.maxLength(CONST.MAX_LEN_NOME)]],
      cognome: [this.userLogged.lastName, [Validators.required,Validators.maxLength(CONST.MAX_LEN_COGNOME)]],
      email: [this.userLogged.emailAddress, [Validators.required, Validators.email]],
      telefono: [telefono, [Validators.required, Validators.pattern(CONST.PHONE_PATTERN),Validators.maxLength(CONST.MAX_LEN_TELEFONO)]],
      fax: [fax, [Validators.pattern(CONST.PHONE_PATTERN),Validators.maxLength(CONST.MAX_LEN_FAX)]],
      capoNome: ['', [Validators.required,Validators.maxLength(CONST.MAX_LEN_NOME)]],
      capoCognome: ['', [Validators.required,Validators.maxLength(CONST.MAX_LEN_COGNOME)]],
      capoEmail: ['', [Validators.required, Validators.email]],
      capoTelefono: ['', [Validators.required, Validators.pattern(CONST.PHONE_PATTERN),Validators.maxLength(CONST.MAX_LEN_TELEFONO)]],
      organizzazione: [null, Validators.required],
      ruolo: [null, Validators.required],
      inQualitaDi: [null, [Validators.required,Validators.maxLength(CONST.MAX_LEN_RUOLO_DITTA)]],
      allegatoName: [null, Validators.required],
      privacyAccettato: [null, Validators.required],
      dichMendaciAccettato: [null, Validators.required],
      privacy: [this.testoPrivacy, Validators.required],
      dichMendaci: [this.testoDichMendaci, Validators.required],
    });
    this.formRichiesta.setValidators([this.allDisclaimerChecked,this.selezioneGruppoRuoloValida.bind(this)]);
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
      this.alertData.display = false;
    else if (event.isConfirm && event.extraData && event.extraData.operazione) {
      switch (event.extraData.operazione) {
        case 'confermaRichiesta':
          this.sendRichiesta();
          break;
        case 'doLogout':
          this.router.navigate(["/"]);  
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

  richiediAbilitazione() {
    this.formRichiesta.markAsTouched();
    this.formRichiesta.updateValueAndValidity();
    this.submitted = true;
    if (this.formRichiesta.invalid) {
      this.alertData = {
        display: true,
        title: this.translateService.instant('abilitazione.validationTitle'),
        content: this.translateService.instant('abilitazione.validationMessage'),
        typ: "danger",
        extraData: null,
        isConfirm: false,
      }
    } else {
      //chiedo conferma e invio...
      this.alertData = {
        display: true,
        title: this.translateService.instant('abilitazione.confirmTitle'),
        content: this.translateService.instant('abilitazione.confirm'),
        typ: "info",
        extraData: { operazione: 'confermaRichiesta' },
        isConfirm: true,
      }
    }
  }

  sendRichiesta() {
    let formValue = this.formRichiesta.value;
    //rimetto stesso nome e cognome
    formValue.nome=this.userLogged.firstName;
    formValue.cognome=this.userLogged.lastName;
    this.formData.delete('data');
    this.formData.append('data', new Blob([JSON.stringify(formValue)], { type: "application/json" }));
    this.loadingService.emitLoading(true);
    this.http.post(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/richiesta-abilitazione.pjson", this.formData)
      .subscribe(
        (data: BaseResponse<string>) => {
          this.loadingService.emitLoading(false);
          if (data.codiceEsito == "OK") {
            this.alertData = {
              display: true,
              title: this.translateService.instant('abilitazione.esitoTitle'),
              content: this.translateService.instant('abilitazione.esito'),
              typ: "info",
              extraData: { operazione: 'doLogout' },
              isConfirm: true,
            }
          } /*else {
            this.alertData = {
              display: true,
              title: this.translateService.instant('abilitazione.esitoTitle'),
              content: this.translateService.instant('abilitazione.esitoError'),
              typ: "error",
              extraData: { operazione: 'doLogout' },
              isConfirm: true,
            }
          }*/

        },
        error => {
          console.log(error);
          this.loadingService.emitLoading(false);
        }
      );
  }

  logout() {
    this.lss.removeValue(LocalSessionServiceService.LOGGED_USER_KEY);
    this.authService.logout();
  }

  onFileChange(event) {
    if(event.target.files && event.target.files.length && 
      event.target.files[0].type!="application/pdf" && 
      event.target.files[0].type!="image/png" && 
      event.target.files[0].type!="image/jpeg"){
      this.alertData.display = true;
      this.alertData.typ = "info";
      this.alertData.isConfirm = false;
      this.alertData.content = "Formato errato:"+event.target.files[0].type+" atteso: application/pdf, image/png, image/jpeg !!! ";
      this.formRichiesta.get('allegatoName').patchValue(null);
      return;
    }
    if(event.target.files && event.target.files.length && 
      event.target.files[0].size>=CONST.MAX_1MB){
      this.alertData.display = true;
      this.alertData.typ = "info";
      this.alertData.isConfirm = false;
      let err = 'La dimensione del file(' +
              Math.round(event.target.files[0].size / 1024 / 1024) + ' MB) supera i limiti ammessi (' +
              Math.round(CONST.MAX_1MB / 1024 / 1024) + ' MB)';
      this.alertData.content = err;
      this.formRichiesta.get('allegatoName').patchValue(null);
      return;
    }
    //arrivato qui è tutto ok...
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      this.formData.delete('allegato');
      this.formData.append('allegato', file, file.name);
    }
  }

  allDisclaimerChecked(control: FormGroup): ValidationErrors | null {
    const privacy = control.get('privacyAccettato').value;
    const dichMendaci = control.get('dichMendaciAccettato').value;
    if (!(privacy && dichMendaci)) {
      return { 'allCheckedRequired': true };
    }
    return null;
  }

  selezioneGruppoRuoloValida(control: FormGroup): ValidationErrors | null {
    const organizzazione = control.get('organizzazione').value;
    const ruolo = control.get('ruolo').value;
    let codice_gruppo = organizzazione + '_' + ruolo;
    let idxGruppo = this.gruppiAttivi.findIndex(el => el.id == codice_gruppo);
    if (idxGruppo < 0) {
      return { 'combinazioneOrganizzazioneRuoloInvalida': true };
    }
    return null;
  }

  cambioOrganizzazione(event) {
    let valOrganizzazione = event.value;
    //this.formRichiesta.get('ruolo').patchValue(null);
    //setto disabled i ruoli che non mi spettano
    let ruoliAttivi = this.ruoliAttiviOrganizzazione.get(valOrganizzazione);
    this.ruoli.forEach(el => {
      ruoliAttivi.has(el.value) ? el.disabled = false : el.disabled = true;
    });
    //console.log("ruoliattivi:", ruoliAttivi);
    //console.log("ruoli:", this.ruoli);
  }

  //restituisce un oggetto errors comprensivo degli errors del formcontrol+ gli error del formgroup filtrati
  mergeErrorsValidation(errorsControl:ValidationErrors|null,errorsFiltered:string){
    let ret=null;
    if(errorsControl){
      ret={...errorsControl};
    }
    if(this.formRichiesta.errors && this.formRichiesta.errors[errorsFiltered]){
      !ret?ret={...errorsControl}:ret={...ret};
      ret[errorsFiltered]=this.formRichiesta.errors[errorsFiltered];
    }
    return ret;
  }

  openModalOrganizzazioneMancante(){
    this.displayOrganizzazioneMancante=true;
  }
  
  doSendSegnalazione(formValue){
    let formData: FormData = new FormData();
    formData.append('data', new Blob([JSON.stringify(formValue)], { type: "application/json" }));
    this.loadingService.emitLoading(true);
    this.http.post(CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/segnala-organizzazione-mancante.pjson", formData)
      .subscribe(
        (data: BaseResponse<string>) => {
          this.loadingService.emitLoading(false);
          if (data.codiceEsito == "OK") {
            this.alertData = {
              display: true,
              title: this.translateService.instant('generic.info'),
              content: this.translateService.instant('generic.operazioneOk'),
              typ: "info",
              extraData: { operazione: 'doLogout' },
              isConfirm: true,
            }
          } else {
            this.alertData = {
              display: true,
              title: this.translateService.instant('generic.errore'),
              content: this.translateService.instant('error.GENERIC_ERROR'),
              typ: "danger",
              extraData: null,
              isConfirm: true,
            }
          }
        },
        error => {
          console.log(error);
          this.loadingService.emitLoading(false);
        }
      );
  }

  sendSegnalazione(event){
    if(event){
      this.doSendSegnalazione(event);
    }
    this.displayOrganizzazioneMancante=false;
  }
  
}
