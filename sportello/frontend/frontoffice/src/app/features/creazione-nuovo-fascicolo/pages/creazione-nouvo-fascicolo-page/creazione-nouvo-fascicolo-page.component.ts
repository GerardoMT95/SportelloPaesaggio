import { PraticaDelegato } from './../../../../shared/models/models';
import { CONST } from './../../../../shared/constants';
import { LoadingService } from './../../../../shared/services/loading.service';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { DialogService } from 'src/app/core/services/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogButtons, DialogIconType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { Router, ActivatedRoute } from '@angular/router';
import { paths } from 'src/app/app-routing.module';
import { AttivitaDaEspletareEnum, Fascicolo, WebContentDTO } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { combineLatest, Observable, Subject } from 'rxjs';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { takeUntil } from 'rxjs/operators';



@Component({
  selector: 'app-creazione-nouvo-fascicolo-page',
  templateUrl: './creazione-nouvo-fascicolo-page.component.html',
  styleUrls: ['./creazione-nouvo-fascicolo-page.component.scss']
})
export class CreazioneNouvoFascicoloPageComponent implements OnInit,OnDestroy
{

  form: FormGroup;
  typeProcedimento: SelectOption[];
  enteDelegato: SelectOption[];
  messaggioEntiAttivi:string;
  unsubscribe$ = new Subject<void>();
  ruoliPratica: SelectOption[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private store: FascicoloStore,
              private formBuilder: FormBuilder,
              private dialogService: DialogService,
              private translateService: TranslateService,
              private fascicoloStore: FascicoloStore,
              private httpDominioService: HttpDominioService,
              private fascicoloService: FascicoloService,
              private loading: LoadingService) { }

  ngOnInit()
  {
    this.store.setBreadcrumbs([{ label: 'Creazione Nuovo Fascicolo' }]);
    this.caricamentoDati();
    this.form = this.buildForm();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  private caricamentoDati(){
    let typeProcedimento$ = this.httpDominioService.getTipiProcedimento();
    let enteDelegato$ = this.httpDominioService.getEnteDelegato();
    let ruoliPratica$ = this.httpDominioService.getRuoloPratica();
    let webContent:WebContentDTO={codiceContenuto:CONST.KEY_WEB_CONTENT_NUOVO_FASCICOLO,contenuto:null,tooltip:null};
    let messaggioEntiAttivi$=this.fascicoloService.getMessaggio(webContent);
    this.loading.emitLoading(true);
    combineLatest([typeProcedimento$, enteDelegato$,messaggioEntiAttivi$,ruoliPratica$])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        ([tipiProcedimento,entiRiceventi,messaggioEntiAttivi,ruoliPratica ]) => {
          this.loading.emitLoading(false);
          this.typeProcedimento=tipiProcedimento;
          this.enteDelegato=entiRiceventi;
          this.messaggioEntiAttivi=messaggioEntiAttivi.payload.contenuto;
          this.ruoliPratica=ruoliPratica;
        });
  }

  buildForm()
  {
    return this.formBuilder.group({
      id: [null],//non gestito in creazione
      codiceFascicolo: '',
      enteDelegato: ['', Validators.required],
      oggetto: ['', Validators.required],
      tipoProcedimento: ['', Validators.required],
      ruoloPratica:['',Validators.required],
      dataCreazione: [null],
      isInSanatoria: [false],
      attivitaDaEspletare: [AttivitaDaEspletareEnum.COMPILA_DOMANDA],
      schedaTecnica: [{}]
    });
  }

  /* public createFascicolo(event: any): void
  {
    this.loading.emitLoading(true);
    this.fascicoloService.addFascicolo(this.form.getRawValue()).then((resp) =>
    {
      this.loading.emitLoading(false);
      if(resp.codiceEsito === CONST.OK)
      {
        const codiceFascicolo: string = resp.payload.codicePraticaAppptr;
        const message =
          this.translateService.instant('CREATION_BUTTON_MESSAGE_1') +
          this.translateService.instant('CREATION_BUTTON_MESSAGE_2') +
          codiceFascicolo;

        const title = this.translateService.instant('CREATION_DOCUMENT_TITLE');
        const router = this.router;
        this.dialogService.showDialog(true, message, title, DialogButtons.CHIUDI, (buttonID: string): void =>
        {
          console.log('Button callback:', buttonID);
          router.navigate([paths.details(codiceFascicolo)]);
        }, DialogType.SUCCESS);

        this.form.get('dataCreazione').patchValue(new Date());
      }
    });
  } */

  public createFascicolo(event: any): void
  {
    let fascicolo = this.form.getRawValue();
    let delegato: PraticaDelegato = null;
    if(fascicolo.delegatoPratica)
    {
      delegato = this.buildDelegato(fascicolo.delegatoPratica[0]);
      fascicolo.praticaDelegato = null;
    }
    this.loading.emitLoading(true);
    this.fascicoloService.creaFascicolo(fascicolo, delegato, event?event.file:null).then((resp) =>
    {
      this.loading.emitLoading(false);
      if(resp.codiceEsito === CONST.OK)
      {
        const codiceFascicolo: string = resp.payload.codicePraticaAppptr;
        const message =
          this.translateService.instant('CREATION_BUTTON_MESSAGE_1') +
          this.translateService.instant('CREATION_BUTTON_MESSAGE_2') +
          codiceFascicolo;

        const title = this.translateService.instant('CREATION_DOCUMENT_TITLE');
        const router = this.router;
        this.dialogService.showDialog(true, message, title, DialogButtons.CHIUDI, (buttonID: string): void =>
        {
          console.log('Button callback:', buttonID);
          router.navigate([paths.details(codiceFascicolo)]);
        }, DialogType.SUCCESS);

        this.form.get('dataCreazione').patchValue(new Date());
      }
    });
  }

  private buildDelegato(dp: any): PraticaDelegato
  {
    let delegato: PraticaDelegato = {
      ...dp,
      comuneNascita: dp.comuneNascita ? dp.comuneNascita.description : null,
      idComuneNascita: dp.comuneNascita ? dp.comuneNascita.value : null,
      comuneNascitaEstero: dp.comuneNascitaEstero ? dp.comuneNascitaEstero.description : null,
      idComuneNascitaEstero: dp.comuneNascitaEstero ? dp.comuneNascitaEstero.value : null,
      provinciaNascita: dp.provinciaNascita ? dp.provinciaNascita.description : null,
      idProvinciaNascita: dp.provinciaNascita ? dp.provinciaNascita.value : null,
      nazioneNascita: dp.nazioneNascita ? dp.nazioneNascita.description : null,
      idNazioneNascita: dp.nazioneNascita ? dp.nazioneNascita.value : null,
      comuneResidenza: dp.comuneResidenza ? dp.comuneResidenza.description : null,
      idComuneResidenza: dp.comuneResidenza ? dp.comuneResidenza.value : null,
      comuneResidenzaEstero: dp.comuneResidenzaEstero ? dp.comuneResidenzaEstero.description : null,
      idComuneResidenzaEstero: dp.comuneResidenzaEstero ? dp.comuneResidenzaEstero.value : null,
      provinciaResidenza: dp.provinciaResidenza ? dp.provinciaResidenza.description : null,
      idProvinciaResidenza: dp.provinciaResidenza ? dp.provinciaResidenza.value : null,
      nazioneResidenza: dp.nazioneResidenza ? dp.nazioneResidenza.description : null,
      idNazioneResidenza: dp.nazioneResidenza ? dp.nazioneResidenza.value : null,
    };
    return delegato;
  }

  buttonPressed()
  {
  }
  get metadata()
  {
    return this.fascicoloStore.state.meta;
  }

}
