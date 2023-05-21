import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SelectItem } from 'primeng/primeng';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { copyOf } from 'src/app/core/functions/generic.utils';
import { Assegnamento } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { AssegnaFascicoloConfiguration } from '../../configuration/assegnazione-fascicolo.configuration';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../shared/constants';
import { SearchFields } from './../../../../shared/models/form-search.configurations.models';
import { TabellaAssegnamentoFascicolo } from './../../../../shared/models/models';
import { AssegnazioniSearch } from './../../../../shared/models/search.models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { TableSearch } from './../../models/assegna-fascicolo.models';

@Component({
  selector: 'app-assegnazione-fascicolo-page',
  templateUrl: './assegnazione-fascicolo-page.component.html',
  styleUrls: ['./assegnazione-fascicolo-page.component.scss']
})
export class AssegnazioneFascicoloPageComponent implements OnInit, OnDestroy
{
  public configurationNA = copyOf(AssegnaFascicoloConfiguration.configuration);
  public configurationA = copyOf(AssegnaFascicoloConfiguration.configuration);
  public nCol = AssegnaFascicoloConfiguration.nColumns;

  public notAssigned: TabellaAssegnamentoFascicolo[];
  public assigned: TabellaAssegnamentoFascicolo[];

  public totalItemsNotAssigned: number;
  public totalItemsAssigned: number;

  public filtersNotAssigned: AssegnazioniSearch;
  public filtersAssigned: AssegnazioniSearch;

  //variabili per lo storico
  public _idPratica: string;;
  public _codice: string;
  public _assegnazioni: Assegnamento[];
  public displayStorico: boolean;

  //variabili per nuovo assegnamento
  public displayNewAssignment: boolean;
  public initAssignment: Assegnamento;
  public funzionariOptions: SelectItem[];
  public rupOptions: SelectItem[];

  private unsubscribe$: Subject<void> = new Subject<void>();

  constructor(private route: ActivatedRoute,
              private praticaService: FascicoloService,
              private loading: LoadingService,
              private dialog: CustomDialogService)
  {
    this.initFiltersNotAssigned();
    this.initFiltersAssigned();
    this.filtersNotAssigned.codice = this.route.snapshot.paramMap.get('code');
  }

  ngOnInit()
  {
    this.preparaConfigRicerca(this.configurationNA, false);
    this.preparaConfigRicerca(this.configurationA,  true);
    let getFunz$ = this.praticaService.callGetFunzionario();
    let getNotAssgned$ = this.praticaService.callSearchAssigned(this.filtersNotAssigned);
    let getAssigned$ = this.praticaService.callSearchAssigned(this.filtersAssigned); 
    let _this = this;
    this.loading.emitLoading(true);
    combineLatest([getFunz$, getNotAssgned$, getAssigned$])
      .pipe(takeUntil(this.unsubscribe$)).subscribe(([resFun, resNA, resA]) =>
    {
      if(resFun.codiceEsito === CONST.OK && resNA.codiceEsito === CONST.OK && resA.codiceEsito === CONST.OK)
      {
        _this.rupOptions = resFun.payload.filter(p => p.rup).map(m => { return {label: m.description , value: m.value}});
        _this.funzionariOptions = resFun.payload.filter(p => !p.rup).map(m => { return { label: m.description , value: m.value } });
        _this.notAssigned = resNA.payload.list;
        _this.assigned = resA.payload.list;
        _this.totalItemsAssigned = resA.payload.count;
        _this.totalItemsNotAssigned = resNA.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  private preparaConfigRicerca(conf: SearchFields[], assigned: boolean): void
  {
    let tmpExtra = copyOf(conf[0].extra);
    tmpExtra.otherFields = {giaAssegnato: assigned};
    tmpExtra.endpoint = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/assegnazione/autocompleteCodice";
    conf[0].extra = tmpExtra;
  }

  public getAll(): void
  {
    this.initFiltersAssigned();
    this.initFiltersNotAssigned();
    this.loading.emitLoading(true);
    let getNotAssgned$ = this.praticaService.callSearchAssigned(this.filtersNotAssigned);
    let getAssigned$ = this.praticaService.callSearchAssigned(this.filtersAssigned);
    combineLatest([getNotAssgned$, getAssigned$])
      .pipe(takeUntil(this.unsubscribe$)).subscribe(([resNA, resA]) =>
    {
      if (resNA.codiceEsito === CONST.OK && resA.codiceEsito === CONST.OK)
      {
        this.notAssigned = resNA.payload.list;
        this.assigned = resA.payload.list;
        this.totalItemsAssigned = resA.payload.count;
        this.totalItemsNotAssigned = resNA.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
  }

  public formSearchEvent(event: AssegnazioniSearch, assigned: boolean): void
  {
    event.codice = event.codice.toUpperCase();
    if(event)
      this.conditionAssignement(assigned ? this.filtersAssigned : this.filtersNotAssigned, event, ["page", "limit", "column", "direction"]);
    assigned ? this.searchAssigned() : this.searchNotAssigned();
  }

  public sortByAndPaging(event: TableSearch, assigned: boolean): void
  {
    if (event)
      this.conditionAssignement(assigned ? this.filtersAssigned : this.filtersNotAssigned, event);
    assigned ? this.searchAssigned() : this.searchNotAssigned();
  }

  private searchNotAssigned(): void
  {
    this.loading.emitLoading(true);
    this.praticaService.callSearchAssigned(this.filtersNotAssigned).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK && response.payload)
      {
        this.notAssigned = response.payload.list;
        this.totalItemsNotAssigned = response.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  private searchAssigned(): void
  {
    this.loading.emitLoading(true);
    this.praticaService.callSearchAssigned(this.filtersAssigned).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK && response.payload)
      {
        this.assigned = response.payload.list;
        this.totalItemsAssigned = response.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  public initFiltersNotAssigned(doSearch: boolean = false): void
  {
    this.filtersNotAssigned = new AssegnazioniSearch();
    this.filtersNotAssigned.codice = null;
    this.filtersNotAssigned.giaAssegnato = false;
    this.filtersNotAssigned.page = 1;
    this.filtersNotAssigned.limit = 5;
    if(doSearch) this.searchNotAssigned();
  }

  public initFiltersAssigned(doSearch: boolean = false): void
  {
    this.filtersAssigned = new AssegnazioniSearch();
    this.filtersAssigned.page = 1;
    this.filtersAssigned.limit = 5;
    this.filtersAssigned.giaAssegnato = true;
    if(doSearch) this.searchAssigned();
  }

  public mostraStorico(fascicolo: TabellaAssegnamentoFascicolo): void
  {
    this.loading.emitLoading(true);
    this.praticaService.callGetStoricoAssegnamento(fascicolo.id).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        this._idPratica = fascicolo.id;
        this._codice = fascicolo.codice;
        this._assegnazioni = response.payload;
        this.displayStorico = true;
      }
      this.loading.emitLoading(false);
    });
  }

  public closeStorico(): void
  {
    this._idPratica = null;
    this._codice = null;
    this._assegnazioni = null;
    this.displayStorico = false;
  }
  
  public assegnaFascicolo(event: any): void
  {
    this.initAssignment = event.assegnazioneAttuale;
    this._idPratica = event.id;
    this._codice = event.assegnazioneAttuale.codice;
    this.displayNewAssignment = true;
  }

  public closeAssegnaFascicolo(): void
  {
    this.initAssignment = null;
    this._idPratica = null;
    this._codice = null;
    this.displayNewAssignment = false;
  }

  public saveAssignment(assegnamento: Assegnamento): void
  {
    this.loading.emitLoading(true);
    assegnamento.idFascicolo = this._idPratica;
    let assegnato = this.assigned.some(p => p.id === assegnamento.idFascicolo);
    let call$ = this.praticaService.callSaveAssignemet(assegnamento);
    if(assegnato) call$ = this.praticaService.callNewSaveAssignement(assegnamento);
    call$.subscribe(response =>
    {
      this.loading.emitLoading(false);
      if (response.codiceEsito === CONST.OK)
      {
        this.closeAssegnaFascicolo();
        this.initFiltersNotAssigned();
        this.initFiltersAssigned();
        this.getAll();
      }
    });
  }
  
  private conditionAssignement(leftArg: any, rightArg: any, keyToIgnore?: string[]): void
  {
    Object.keys(rightArg).forEach(key =>
    {
      if (!keyToIgnore || !keyToIgnore.includes(key))
        leftArg[key] = rightArg[key];
    });
  }

  public eliminaAssegnazione(event: TabellaAssegnamentoFascicolo): void
  {
    let message = "Sei sicuro di voler revocare l'assegnazione per la pratica corrente?";
    this.dialog.showDialog(true, message, "Attenzione", DialogButtons.CONFERMA_CHIUDI, (buttonid: any) =>
    {
      if (buttonid == 1)
      {
        this.loading.emitLoading(true);
        this.praticaService.callDisassegnaFascicolo(event).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
            this.getAll();
        });
      }
    }, DialogType.INFORMATION);
  }

}
