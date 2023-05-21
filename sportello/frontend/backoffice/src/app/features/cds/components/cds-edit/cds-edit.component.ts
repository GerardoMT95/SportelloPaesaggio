import { FascicoloService } from './../../../../shared/services/pratica/http-fascicolo.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { Cds, CdsSelectItem, CdsSettings } from './../../models/cds.model';
import { CONST } from './../../../../shared/constants';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CdsService } from './../../services/cds.service';
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { combineLatest, Subject, Subscription, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { Router } from '@angular/router';
import { DataService } from 'src/app/features/gestione-istanza/services';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-cds-edit',
  templateUrl: './cds-edit.component.html',
  styleUrls: ['./cds-edit.component.css']
})
export class CdsEditComponent implements OnInit, OnDestroy {

  public form:FormGroup;
  private unsubscribe$ = new Subject<void>();
  public modalitaList:CdsSelectItem[];
  public attivitaList:CdsSelectItem[];
  public azioneList:CdsSelectItem[];
  public tipoList:CdsSelectItem[];
  public comuneList:CdsSelectItem[];
  public provinciaList:CdsSelectItem[];
  public comunePertinenzaList:CdsSelectItem[];
  public provinciaPertinenzaList:CdsSelectItem[];
  public validation:boolean;
  public comitatoList:CdsSelectItem[];
  public responsabiliList:CdsSelectItem[];
  public funzionariList:CdsSelectItem[];

  private subscriptionAction:Subscription;

  public minDate :Date = new Date();
  public MIN_YEAR:number = CONST.MIN_YEAR;
  public MAX_YEAR:number = CONST.MAX_YEAR;
  public cds: Cds;
  public disable:boolean;
  public canCreate: boolean;
  @Input()
  public idPratica: string;
  @Input()
  public idCds: string;
  @Input()
  public settings: CdsSettings;
  @Input()
  public comitato: boolean;
  @Input("fascicolo")
  public fascicolo:Fascicolo;
  @Output("salva")
  public salvaEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output("avvia")
  public avviaEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output("back")
  public backEmitter: EventEmitter<any> = new EventEmitter<any>();
  private subscription:Subscription;

  constructor(private cdsService:CdsService
             ,private fb:FormBuilder
             ,private fascicoloService:FascicoloService
             ,private loading:LoadingService
             ,private router: Router
             ,public data: DataService
  ) {

  }

  ngOnInit() {
    this.buildForm();
    this.caricamentoDati();
    this.subscription = this.fascicoloService.reloadFascicoloEmitter.subscribe(response =>{
      this.caricamentoDati();
    });
    this.subscriptionAction = this.cdsService.action.subscribe(action =>{
      if(this.comitato == false){
            switch(action){
            case "SALVA":
              this.salva();
              break;
              break;
            case "AVVIA":
              this.avvia();
              break;
            case "DISABLE_EDIT":
              this.disable = true;
              break;
            default:
              break;
           }
      }else{
        switch(action){
          case "SALVA_COMITATO":
            this.salva();
            break;
            break;
          case "AVVIA_COMITATO":
            this.avvia();
            break;
          case "DISABLE_COMITATO_EDIT":
              this.disable = true;
              break;
          default:
            break;
         }
      }
    });

  }
  ngOnDestroy(): void {
    if(this.subscription)
      this.subscription.unsubscribe();
    if(this.subscriptionAction)
      this.subscriptionAction.unsubscribe();
  }

  private buildForm(){
    this.form = this.fb.group({tipo: [null, [Validators.required]]
                              ,attivita: [null, [Validators.required]]
                              ,azione: [null, [Validators.required]]
                              ,termineRichiestaIntegrazione: [null, null]
                              ,terminePareri: [null, null]
                              ,primaSessione: [null, null]
                              ,dataTermine: [null, null]
                              ,comunePertinenza: [null, [Validators.required]]
                              ,provinciaPertinenza: [null, [Validators.required]]
                              ,indirizzoPertinenza: [null, [Validators.required]]
                              ,modalitaIncontro:[null,[Validators.required]]
                              ,link: [null, [Validators.required]]
                              ,comune: [null, [Validators.required]]
                              ,provincia: [null, [Validators.required]]
                              ,cap: [null, [Validators.required]]
                              ,indirizzo: [null, [Validators.required]]
                              ,orario: [null, [Validators.required, Validators.pattern("[012]{1}[0123]{1}[:]{1}[012345]{1}[0-9]{1}")]]
                              ,comitato: [null, null]
                              ,responsabile: [null, Validators.required]
                              ,funzionari: [null, Validators.required]
                              });
  }

  private caricamentoDati(){
    this.loading.emitLoading(true);
    let obsGet$ = this.comitato ? this.cdsService.getComitato(this.idPratica) : this.cdsService.get(this.idPratica, this.idCds);
    let obsModalita$ = this.cdsService.modalita()
    let obsAttivia$ = this.cdsService.attivita(this.idPratica);
    let obsTipo$ = this.cdsService.tipo(this.idPratica);
    let obsCds$ = this.cdsService.getCdsDetail(this.idPratica, this.idCds);
    let obsComitatoList$ = this.comitato && this.fascicolo && this.fascicolo.rup
                         ? this.cdsService.utentiComitato(this.idPratica)
                         : this.cdsService.tipo(this.idPratica);
                         ;
    let obsResponsabiliList$ = 
    // !this.comitato && this.fascicolo && this.fascicolo.rup ? 
                            this.cdsService.utentiResponsabili(this.idPratica)
                            //  : this.cdsService.tipo(this.idPratica);
                             ;
    let obsFunzionariList$ = this.cdsService.utentiFunzionari(this.idPratica);
    let obsCanCreate$ = this.cdsService.canCreateConferenza(this.idPratica);

    let observables:Observable<any>[] =[obsGet$
                                       ,obsModalita$
                                       ,obsAttivia$
                                       ,obsTipo$
                                       ,obsCds$
                                       ,obsComitatoList$
                                       ,obsResponsabiliList$
                                       ,obsFunzionariList$
                                       ,obsCanCreate$
                                       ];
    combineLatest(observables)
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(
      ([get
       ,modalita
       ,attivita
       ,tipo
       ,cds
       ,comitato
       ,responsabile
       ,funzionario
       ,canCreate
      ]) => {
        if(CONST.OK === get.codiceEsito
        && CONST.OK === modalita.codiceEsito
        && CONST.OK === attivita.codiceEsito
        && CONST.OK === tipo.codiceEsito
        && CONST.OK === cds.codiceEsito
        && CONST.OK === comitato.codiceEsito
        && CONST.OK === responsabile.codiceEsito
        && CONST.OK === funzionario.codiceEsito
        && CONST.OK === canCreate.codiceEsito
        ){
          this.modalitaList = modalita.payload;
          this.attivitaList = attivita.payload;
          this.tipoList     = tipo.payload;
          this.settings     = get.payload;
          this.cds = cds.payload;
          if(this.comitato)
            this.comitatoList = comitato.payload;
          else
            this.responsabiliList = responsabile.payload;
          this.funzionariList = funzionario.payload;
          this.canCreate = canCreate.payload;
          this.setFormValues();
        }
        // this.loading.emitLoading(false);
      }
    );
  }


  private setFormValues():void{
    console.log(this.settings);
    this.setControlValue('tipo'                , this.settings.tipo               , this.settings.tipoLabel               );
    this.setControlValue('attivita'            , this.settings.attivita           , this.settings.attivitaLabel           );
    this.setControlValue('azione'              , this.settings.azione             , this.settings.azioneLabel             );
    this.setControlValue('modalitaIncontro'    , this.settings.modalitaIncontro   , this.settings.modalitaIncontroLabel   );
    this.setControlValue('comunePertinenza'    , this.settings.comunePertinenza   , this.settings.comunePertinenzaLabel   );
    this.setControlValue('provinciaPertinenza' , this.settings.provinciaPertinenza, this.settings.provinciaPertinenzaLabel);
    this.setControlValue('comune'              , this.settings.comune             , this.settings.comuneLabel             );
    this.setControlValue('provincia'           , this.settings.provincia          , this.settings.provinciaLabel          );
    this.form.controls.termineRichiestaIntegrazione.setValue(this.settings.termineRichiestaIntegrazione);
    this.form.controls.terminePareri               .setValue(this.settings.terminePareri               );
    this.form.controls.primaSessione               .setValue(this.settings.primaSessione               );
    this.form.controls.dataTermine                 .setValue(this.settings.dataTermine                 );
    this.form.controls.indirizzoPertinenza         .setValue(this.settings.indirizzoPertinenza         );
    this.form.controls.link                        .setValue(this.settings.link                        );
    this.form.controls.cap                         .setValue(this.settings.cap                         );
    this.form.controls.cap                         .setValue(this.settings.cap                         );
    this.form.controls.orario                      .setValue(this.settings.orario                      );
    this.form.controls.indirizzo                   .setValue(this.settings.indirizzo                   );
    this.changeAttivita(false);
    if(this.comitato){
      this.setControlValue('comitato', this.settings.usernameCreatore, this.settings.usernameCreatoreNominativo);
      this.form.controls['comitato'].setValidators([Validators.required]);
      this.form.controls['responsabile'].setValidators(null);
    }else{
      this.setControlValue('responsabile', this.settings.usernameResponsabile, this.settings.nominativoResponsabile);
      this.form.controls['responsabile'].setValidators([Validators.required]);
      this.form.controls['comitato'].setValidators(null);
    }
    this.setControlValue('funzionari', this.settings.partecipanti, null);
    // this.disable = this.settings.completed 
    //             || !this.fascicolo || (!this.fascicolo.rup && !this.fascicolo.rup)
    //             || (this.fascicolo && this.fascicolo.attivitaDaEspletare ===  StatoEnum.InLavorazione)
                // || (this.fascicolo && this.fascicolo.attivitaDaEspletare === StatoEnum.archiviata)
    this.disable = !(this.canCreate && !this.settings.completed);
    this.changeModalita(null);
    if(this.comitato == false)
      this.cdsService.action.next(this.disable ? "DISABLE_EDIT" : "ENABLE_EDIT");
    else
      this.cdsService.action.next(this.disable ? "DISABLE_COMITATO_EDIT" : "ENABLE_COMITATO_EDIT");
  }


  private getControlValue(controlName:string):string{
    if(this.form.controls[controlName].value){
      return this.form.controls[controlName].value.value;
    }
    return null;
  }
  
  private setControlValue(controlName:string, value:any, label:string):void{
    let option: CdsSelectItem;
    if(value && value.length){
      this.loading.emitLoading(true);
      switch(controlName){
        case 'tipo':
          option = this.tipoList.find(x => x.value == value);
          this.form.controls[controlName].setValue(option);
          break;
        case 'attivita':
          option = this.attivitaList.find(x => x.value == value);
          this.form.controls[controlName].setValue(option);
          this.changeAttivita(false);
          break;
        case 'azione':
          if(this.azioneList != null && this.azioneList.length > 0){
            option = this.azioneList.find(x => x.value == value);
            this.form.controls[controlName].setValue(option);
          }
          break;
        case 'modalitaIncontro':
          option = this.modalitaList.find(x => x.value == value);
          this.form.controls[controlName].setValue(option);
        break;
        case 'provinciaPertinenza':
          this.ricercaProvinciaPertinenza({query: value}, true);
          break;
        case 'comunePertinenza':
          this.ricercaProvinciaPertinenza({query: this.settings.provinciaPertinenza}, true, value);
          break;
        case 'provincia':
          this.ricercaProvincia({query: value}, true);
          break;
        case 'comune':
          this.ricercaProvincia({query: this.settings.provincia}, true, value);
          break;
        case 'responsabile':
          option = this.responsabiliList.find(x => x.value == value);
          this.form.controls[controlName].setValue(option);
          break;
        case 'funzionari':
          option = value;
          this.form.controls[controlName].setValue(option);
          break;
        default:
          break;
      }
    }else{
      this.form.controls[controlName].setValue(null);
      this.loading.emitLoading(false);
    }
  }

  private getFormValues():CdsSettings{
    return {tipo                        :this.getControlValue('tipo')
           ,attivita                    :this.getControlValue('attivita')
           ,azione                      :this.getControlValue('azione')
           ,modalitaIncontro            :this.getControlValue('modalitaIncontro')
           ,comunePertinenza            :this.getControlValue('comunePertinenza')
           ,provinciaPertinenza         :this.getControlValue('provinciaPertinenza')
           ,comune                      :this.getControlValue('comune')
           ,provincia                   :this.getControlValue('provincia')
           ,termineRichiestaIntegrazione:this.form.controls.termineRichiestaIntegrazione.value
           ,terminePareri               :this.form.controls.terminePareri               .value
           ,primaSessione               :this.form.controls.primaSessione               .value
           ,dataTermine                 :this.form.controls.dataTermine                 .value
           ,indirizzoPertinenza         :this.form.controls.indirizzoPertinenza         .value
           ,link                        :this.form.controls.link                        .value
           ,cap                         :this.form.controls.cap                         .value
           ,indirizzo                   :this.form.controls.indirizzo                   .value
           ,orario                      :this.form.controls.orario                      .value
           ,usernameCreatore            :this.getControlValue('comitato')
           ,usernameResponsabile        :this.getControlValue('responsabile')
           ,partecipanti                :this.form.controls.funzionari                  .value
           ,id                          :this.settings.id
           };
  }

  public salva():void{
    this.salvaEmitter.emit(this.getFormValues())
  }
  
  public avvia():void{
    this.validation = true;
    if(this.form.valid == false)
      return ;
    this.avviaEmitter.emit(this.getFormValues())
  }

  public back():void{
    this.backEmitter.emit()
  }
  


  public ricercaComune(event:any, setControl?: boolean):void{
    if(setControl)
      this.loading.emitLoading(true);
    this.cdsService.comune(this.form.controls.provincia.value.value, event.query)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.comuneList = response.payload;
        if(setControl){
          let option = this.comuneList.find(x => x.description == event.query);
          this.form.controls['comune'].setValue(option);
        }
        this.loading.emitLoading(false);
        
      }
    });
  }

  public ricercaComunePertinenza(event:any, setControl?: boolean):void{
    if(setControl)
      this.loading.emitLoading(true);
    this.cdsService.comune(this.form.controls.provinciaPertinenza.value.value, event.query)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.comunePertinenzaList = response.payload;
        if(setControl){
          let option = this.comunePertinenzaList.find(x => x.description == event.query);
          this.form.controls['comunePertinenza'].setValue(option);
        }
        this.loading.emitLoading(false);
      }
    });
  }

  public ricercaProvincia(event:any, setControl?: boolean, comune?: string):void{
    if(setControl)
      this.loading.emitLoading(true);    
    this.cdsService.provincia(event.query)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.provinciaList = response.payload;
        if(setControl){
          let option = this.provinciaList.find(x => x.description == event.query);
          this.form.controls['provincia'].setValue(option);
          if(comune){
            this.ricercaComune({query: comune}, true);
          }
        } else {
          this.loading.emitLoading(false);
        }
      }
    });
  }

  public ricercaProvinciaPertinenza(event:any, setControl?:boolean, comunePertinenza?: string):void{
    if(setControl)
      this.loading.emitLoading(true);
    this.cdsService.provincia(event.query)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.provinciaPertinenzaList = response.payload;
        if(setControl){
          let option = this.provinciaPertinenzaList.find(x => x.description == event.query);
          this.form.controls['provinciaPertinenza'].setValue(option);
          if(comunePertinenza){
            this.ricercaComunePertinenza({query: comunePertinenza}, true);
          }
        } else {
          this.loading.emitLoading(false);
        }
      }

    });
  }

  public selezionaProvincia(event:any):void{
    this.form.controls.comune.setValue(null);
  }
  
  public deselezionaProvincia(event:any):void{
    this.form.controls.comune.setValue(null);
  }
  
  public selezionaProvinciaPertinenza(event:any):void{
    this.form.controls.comunePertinenza.setValue(null);
  }
  
  public deselezionaProvinciaPertinenza(event:any):void{
    this.form.controls.comunePertinenza.setValue(null);
  }

  public get enableComune():boolean{
    return this.form.controls.provincia && this.form.controls.provincia.value;
  }
  
  public get enableComunePertinenza():boolean{
    return this.form.controls.provinciaPertinenza && this.form.controls.provinciaPertinenza.value;
  }
  
  public get enableOnLine():boolean{
    if(this.form.controls.modalitaIncontro.value)
    return "1" === this.form.controls.modalitaIncontro.value.value;
    return false;
  }
  
  public get enableFisica():boolean{
    if(this.form.controls.modalitaIncontro.value)
      return "2" === this.form.controls.modalitaIncontro.value.value;
    return false;
  }

  public changeModalita(event:any):void{
    if(this.disable) {
      this.form.disable();
      return ;
    }
    if(!this.enableOnLine){
      this.form.controls.link.setValue(null);
      this.form.controls.link.disable();
      if(!this.disable){
        this.form.controls.cap      .enable()
        this.form.controls.indirizzo.enable()
        this.form.controls.comune   .enable()
        this.form.controls.provincia.enable()
      }else{
        this.form.controls.cap      .disable()
        this.form.controls.indirizzo.disable()
        this.form.controls.comune   .disable()
        this.form.controls.provincia.disable()
      }
    }
    if(!this.enableFisica){
      this.form.controls.cap      .setValue(null);
      this.form.controls.indirizzo.setValue(null);
      this.form.controls.comune   .setValue(null);
      this.form.controls.provincia.setValue(null);
      this.form.controls.cap      .disable()
      this.form.controls.indirizzo.disable()
      this.form.controls.comune   .disable()
      this.form.controls.provincia.disable()
      if(!this.disable){
        this.form.controls.link.enable();
      }else{
        this.form.controls.link.disable();
      }
    }

  }

  public changeAttivita(loading:boolean = true):void{
    if(loading){
      this.loading.emitLoading(true);
      this.form.controls.azione.setValue(null);
    }
    this.azioneList = null;
    if(this.form.controls.attivita.value){
      this.cdsService.azione(this.form.controls.attivita.value.value,this.idPratica)
      .subscribe(result =>{
        if(CONST.OK === result.codiceEsito){
          this.azioneList = result.payload;
          if(!loading)
            this.setControlValue('azione', this.settings.azione, this.settings.azioneLabel);
        }
        if(loading)
          this.loading.emitLoading(false);
      });
    }else{
      if(loading)
        this.loading.emitLoading(false);
    }
  }

  public goToConference():void{
    this.cdsService.goToConference(this.cds.id);
  }
}
