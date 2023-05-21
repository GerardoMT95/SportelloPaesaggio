import { Component, EventEmitter, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject, combineLatest, Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { CONST } from 'src/app/shared/constants';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { SelectOption } from './../../../../shared/components/select-field/select-field.component';
import { ConfigOption, DestinazioneUrbanistica, Dichiarazione, EffetiMitigazione, EventualiProcedimenti, Fascicolo, HierarchicalOption, Inquadramento, Legittimita, PareriAtti, PPTR, ProcedureEdilizie, SchedaTecnicaDescrizione, Vincolistica } from './../../../../shared/models/models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { SchedaTecnicaConst } from './../../constants/scheda-tecnica-const';


@Component({
  selector: 'app-compilazione-scheda-tecnica-page',
  templateUrl: './compilazione-scheda-tecnica-page.component.html',
  styleUrls: ['./compilazione-scheda-tecnica-page.component.scss'],
  providers: [DataService]
})
export class CompilazioneSchedaTecnicaPageComponent extends AbstractInputPage implements OnInit, OnDestroy
{
  public activeIndex: number;//indice del pannello
  public descOptions: HierarchicalOption[] = [];
  public pptrTable: ConfigOption[] = [];
  
  public validation: boolean = false;
  public mostraDialog: boolean = false;
  public invalidFields: Array<any> = new Array<any>();
  public casellaDiControllo: { [key: string]: Partial<SelectOption>[] };

  public fascicolo: Fascicolo = null;
  public loadedFascicolo = new EventEmitter<boolean>();

  protected unsubscribe$: Subject<void> = new Subject<void>();
  protected fascicoloSubject: BehaviorSubject<Fascicolo> = new BehaviorSubject<Fascicolo>(null);

  constructor(public router: Router,
              public fb: FormBuilder,
              public dialogService: CustomDialogService,
              private loading: LoadingService,
              private dominio: HttpDominioService,
              private shared: DataService)
  {
    super(dialogService, fb, router);
    this.tabFormNames = SchedaTecnicaConst.tabFormNames;
    this.casellaDiControllo = SchedaTecnicaConst.casellaDiControllo;
    this.loading.emitLoading(true);
  }

  ngOnInit()
  {
    this.codiceFascicolo = this.shared.codicePratica;
    this.mainForm = this.fb.group({ valida: [false] });
    this.loading.emitLoading(true);
    this.fascicolo = this.shared.fascicolo;
    let pptrTable$ = this.dominio.findUlterioriContestiPaesaggisticiHierarchical(parseInt(this.fascicolo.tipoProcedimento));
    let tipiEQualificazioni$ = this.dominio.findTipiEQualificazioniHasOptionHierarchical(parseInt(this.shared.fascicolo.tipoProcedimento));
        combineLatest(tipiEQualificazioni$, pptrTable$)
          .pipe(takeUntil(this.unsubscribe$))
          .subscribe(([tpResponse, pptrTableResponse]) =>
          {
            if (tpResponse.codiceEsito === CONST.OK && tpResponse.payload)
              this.descOptions = tpResponse.payload;
            //console.log(tpResponse)
            if (pptrTableResponse.codiceEsito === CONST.OK && pptrTableResponse.payload)
              this.pptrTable = pptrTableResponse.payload;
            this.loading.emitLoading(false);
            this.loadedFascicolo.next(true);
          });
    this.loading.emitLoading(false);
  }

  ngOnDestroy(): void 
  { 
    this.unsubscribe$.next(); 
    this.unsubscribe$.complete(); 
  }

  public indietro()
  {
    this.router.navigate(['gestione-istanze', this.codiceFascicolo,'istanza-presentata']);
  }

  get descrizione(): SchedaTecnicaDescrizione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.descrizione : null; }
  get pptr(): PPTR { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.pptr : null; }
  get legittimita(): Legittimita { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.legittimita : null; }
  get inquadramento(): Inquadramento { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.inquadramento : null; }
  get effettiMitigazione(): EffetiMitigazione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.effetiMitigazione : null; }
  get eventualiProcedimenti(): EventualiProcedimenti { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.eventualiProcedimenti : null; }
  get pareriEAtti(): PareriAtti { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.parreriEAtti : null; }
  get vincolistica(): Vincolistica { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.vincolistica : null; }
  get procedureEdilizie(): ProcedureEdilizie { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.procedureEdilizie : null; }
  get destinazioneUrbanistica(): DestinazioneUrbanistica[] { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.destinazione : null; }
  get dichiarazione(): Dichiarazione { return this.fascicolo && this.fascicolo.schedaTecnica ? this.fascicolo.schedaTecnica.dichiarazione : null; }
  get fascicolo$(): Observable<Fascicolo> { return this.fascicoloSubject.asObservable(); }

  public focusTab(indiceTab: number): void
  {
    this.mostraDialog = false;
    this.activeIndex = indiceTab;
  }

}
