import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LocalSessionServiceService } from '../../services/local-session-service.service';
import { AuthComponent } from '../generic/AuthComponent';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
//import { ervice } from '../../services/fascicolo.service';
//import { CONST } from '../shared/constants';
import { TranslateService } from '@ngx-translate/core';
import { LoadingService } from '../../services/loading.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { defaultSelectModel } from '../model/model';
import { AutoritaService } from 'src/app/services/autorita.service';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable } from 'rxjs';
import { instantiateSupportedAnimationDriver } from '@angular/platform-browser/animations/src/providers';
import { CONST } from 'src/shared/constants';

@Component({
  selector: 'app-nuovo-fascicolo',
  templateUrl: './nuovo-fascicolo.component.html',
  styleUrls: ['./nuovo-fascicolo.component.css']
})

//export class NuovoFascicoloComponent extends AuthComponent {
export class NuovoFascicoloComponent {
  public submitted = false;
  public it: any = "";
  public extraData: any;
  public nuovoFascicoloForm: FormGroup;
  //public ufficiComune: defaultSelectModel[];//lista uffici di competenza
  entiCompetenza$: Observable<SelectItem[]>;
  tipiProcedimento$: Observable<SelectItem[]>;
  //public tipiProcedimento: defaultSelectModel[];
  //form data
  public ufficioComune: string;
  public tipoProcedimento: string;
  public isInSanatoria = false;
  public oggettoIntervento: string;
  public codiceInternoEnte: string;
  public numeroInternoEnte: string;
  public protocolloInternoEnte: string;
  public dataProtocolloInternoEnte: Date;
  public maxLength = 255; //lunghezza massima textarea
  public id: number;


  //oggetto utilizzato per l'alert
  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(
    private router: Router,
    private lss: LocalSessionServiceService,
    private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private loadingService: LoadingService,
    private autoritaService:AutoritaService,
    private autPaesSvc: AutorizzazioniPaesaggisticheService
  ) {
    //super(router, lss);
    //this.it = CONST[this.translateService.getDefaultLang().toUpperCase()];
  }

  ngOnInit() {
    this.entiCompetenza$ = this.autoritaService.getProceduralAuthoritySelectItem();
    this.buildForm();
    //preselezione dell'eventuale singolo valore dell'ente....
    this.entiCompetenza$.subscribe(res=>{if(res && res.length==1){
        this.nuovoFascicoloForm.get('ufficio').setValue(res[0].value);
    }});
    //preselezione dell'eventuale singolo valore del tipo procedimento....
    this.autPaesSvc.getTipiProcedimento().subscribe(
      res=>{if(res && res.length==1){
        this.nuovoFascicoloForm.get('tipoProcedimento').setValue(res[0].value);
    }}
    );
    
    this.loadingService.emitLoading(false);
    this.translateService.onLangChange.subscribe((event: any) => {
      //this.it = CONST[event.lang.toUpperCase()];
    });
 }


  //, Validators.maxLength(CONST.MAX_LENGTH_TEXTAREA)]
  buildForm(): void {
    if(CONST.isPutt()){
      this.nuovoFascicoloForm = this.formBuilder.group({
        ufficio: ['', Validators.required],
        tipoProcedimento: ['', Validators.required],
        dataDelibera:['',Validators.required],
        sanatoria: [false,],
        oggettoIntervento: ['', Validators.required],
        codiceInternoEnte: ['',],
        numeroInternoEnte: ['',],
        protocollo: ['',],
        dataProtocollo: ['',],
        note: ['']
      });
    }else{
      this.nuovoFascicoloForm = this.formBuilder.group({
        ufficio: ['', Validators.required],
        tipoProcedimento: ['', Validators.required],
        sanatoria: [false,],
        oggettoIntervento: ['', Validators.required],
        codiceInternoEnte: ['',],
        numeroInternoEnte: ['',],
        protocollo: ['',],
        dataProtocollo: ['',],
        note: ['']
      });
    }
    
  }
  get f() {
    return this.nuovoFascicoloForm.controls;
  }

  callbackAlert(event: any): void {
    if (event.isChiuso) {
      this.alertData.display = false;
      console.log("close");
      if (event.extraData != null && event.extraData.save) {
        
        this.router.navigate(['/private/fascicolo/dettaglio/' + this.id]);
      }
    }
    else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'back':
            this.router.navigateByUrl(event.extraData.navigate);
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
      console.log("Confirm");
    }
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.nuovoFascicoloForm.valid) {
      this.loadingService.emitLoading(true);
      console.dir(this.nuovoFascicoloForm.getRawValue());
      // cambiato addFascicolo --> add FascicoloToBE
      /* this.autPaesSvc.addFascicoloToBE(this.nuovoFascicoloForm.getRawValue())
        .then(response => { */
      this.autPaesSvc.creaFascicolo(this.nuovoFascicoloForm.getRawValue()).subscribe(response =>
      {
          if (response.codiceEsito == 'OK') {
            this.alertData.display = true;
            this.alertData.title = this.translateService.instant('fascicolo.esitoValidazioneNuovo');
            this.alertData.typ = "success";
            this.alertData.extraData = { save: true, codice: response.payload.codice.replace(/\"/g, "") };
            this.alertData.content = this.translateService.instant('fascicolo.fascicoloSalvatoPronto')+"<br>"+
            this.translateService.instant('fascicolo.codiceFascicoloE')+
            " " + response.payload.codice;
            //console.dir("FormRawValue:", response.payload.formRawValue);
            this.id = response.payload.id;
          } else {
            this.alertData.display = true;
            this.alertData.typ = "danger";
            this.alertData.content = response.descrizioneEsito;
          }
          this.loadingService.emitLoading(false);
        },
        error =>
        {
          this.loadingService.emitLoading(false);
          this.alertData.display = true;
          this.alertData.typ = "danger";
          this.alertData.content = this.translateService.instant('fascicolo.erroreCreazione');
          console.log(error);
        });
    }
  }

  back() {
    if (this.nuovoFascicoloForm.touched) {
      this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('fascicolo.abbandonaModifiche');
      this.alertData.content = this.translateService.instant('fascicolo.sicuroAbbandonoInseriti');
      this.alertData.extraData = { operazione: 'back', navigate: '/private/fascicolo' };
      this.alertData.display = true;
    }else{
      this.router.navigateByUrl('/private/fascicolo'); 
    }

  }
}