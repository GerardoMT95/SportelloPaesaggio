import { Component, OnInit, Output, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder} from '@angular/forms';
import { paths } from 'src/app/app-routing.module';
import { TableConfig } from 'src/app/core/models/header.model';
import { Fascicolo, FascicoloSearch, AttivitaDaEspletareEnum } from 'src/app/shared/models/models';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { Observable, Subscription, Subject, combineLatest } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';

@Component({
  selector: 'app-consultazione-istanze',
  templateUrl: './consultazione-istanze-page.component.html',
  styleUrls: ['./consultazione-istanze-page.component.scss']
})
export class ConsultazioneIstanzePageComponent implements OnInit,OnDestroy {
  [x: string]: any;

  //typeProcedimento$:Observable<SelectOption[]>;
  tipiProcedimento:SelectOption[];
  entiDelegati:SelectOption[];
  fascioloTableHeaders: TableConfig[] = [];
  listFascicolo: Fascicolo[]=[]
  index: number = null;
  first: number = 0;
  totalRecords: number=0;
  loading: boolean =false;
  fascicoloSearch: FascicoloSearch=new FascicoloSearch();
  sortOrder: string;
  sortField: string;
  private unsubscribe$ = new Subject<void>();
  private currentUser:LoggedUser;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private fascicoloStore: FascicoloStore,
    private httpDominioService:HttpDominioService,
    private dialogService: DialogService,
    private loadingService:LoadingService,
    private fascicoloService: FascicoloService,
    private userService:UserService) { }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
		this.unsubscribe$.complete();
  }

  ngOnInit() {
    this.fascicoloStore.setBreadcrumbs([{label: 'Consultazione Istanze'}]);
    this.currentUser = this.userService.getUser();
    this.fascicoloSearch.page=1;
    this.fascicoloSearch.limit=5;
    this.fascioloTableHeaders = [
      {
        header: 'FASCIOLO_FIELDS.FILE_CODE',
        field: 'codicePraticaAppptr'
      },
      {
        header: 'FASCIOLO_FIELDS.OBJECT',
        field: 'oggetto'
      },
      {
        header: 'FASCIOLO_FIELDS.PROCEDURE_TYPE',
        field: 'tipoProcedimento'
      },
      {
        header: 'FASCIOLO_FIELDS.ACTIVITIES_TO_BE_PERFORMED',
        field: 'attivitaDaEspletare'
      },
      {
        header: 'FASCIOLO_FIELDS.ENTE_DELEGATO',
        field: 'enteDelegato'
      },
      {
        header: '',
        field: 'displayButton',
        width: 6
      }
    ];
    let entiDelegati$=this.httpDominioService.getAllEnteDelegato();
    let typeProcedimento$=this.httpDominioService.getTipiProcedimento(true);
    combineLatest([entiDelegati$,typeProcedimento$])
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(([enti,tipiProc])=>{
      this.entiDelegati=enti;
      this.tipiProcedimento=tipiProc;
      this.loadFascicolo();
    });
    //this.typeProcedimento$=this.httpDominioService.getTipiProcedimento()
    //.pipe(takeUntil(this.unsubscribe$));
  }

  get fascicoloList() {
    return this.fascicoloStore.state.filtered;
  }

  /*get metadata() {
    return this.fascicoloStore.state.meta;
  }*/
  navigateToCreate() {
    this.router.navigate([paths.create()]);
  }

  setFascicoloSearch(searchQuery?: FascicoloSearch)
  {
    Object.keys(searchQuery).forEach(key=>{
      this.fascicoloSearch[key]=searchQuery[key];  
    })
    this.fascicoloSearch.page=1;
    //this.fascicoloSearch.sortBy=this.sortField;
    //this.fascicoloSearch.sortType="desc";
    //this.first=0;//serve per refreshare il paginatore
    this.loadFascicolo()
  }


  loadFascicolo() {
    this.loading=true;
    this.fascicoloService.getAllFiascicolo(this.fascicoloSearch).subscribe(partialFascicolo=>{
      //console.log(partialFascicolo);
      this.totalRecords=partialFascicolo.count;
      this.listFascicolo=partialFascicolo.list;
      this.loading=false;
    });
  }

  navigateToDetails(event: Fascicolo) 
  {
    let stampaDomanda = [AttivitaDaEspletareEnum.GENERA_STAMPA_DOMANDA, 
                         AttivitaDaEspletareEnum.ALLEGA_DOCUMENTI_SOTTOSCRITTI];
    if(stampaDomanda.includes(event.attivitaDaEspletare))
      this.router.navigate([paths.generateStampa(event.codicePraticaAppptr)])
    else
      this.router.navigate([paths.details(event.codicePraticaAppptr)]);
  }

  delete(codiceFascicolo: string){
    this.askDelete(codiceFascicolo);
    //controllo che non ci sono pagamenti in corso o ok.... vecchia gestione, ora il controllo è fatto lato be
    /*let _this=this;
    this.loadingService.emitLoading(true)
    this.fascicoloService
    .totPagamentiPerStato(null,codiceFascicolo)
    .toPromise().then(resp=>{
        if(resp.payload && (resp.payload.OK || resp.payload.INCORSO)){
          _this.warningPagamentiAttivi(resp.payload);
        }else{
          _this.askDelete(codiceFascicolo);
        }
    }
    ).finally(()=>this.loadingService.emitLoading(false));*/
  }

  warningPagamentiAttivi(totPag:{OK?: number;KO?: number;INCORSO?: number;}){
    let totPagAttivi=0;
    totPagAttivi+=(totPag.OK?totPag.OK:0);
    totPagAttivi+=(totPag.INCORSO?totPag.INCORSO:0);
    this.dialogService.showDialog(true, 
      'Impossibile cancellare, risultano ancora pagamenti online effettuati (In corso o OK) sulla pratica ('+totPagAttivi+' €) !!!',
      'generic.warning',
      DialogButtons.CHIUDI,
        (buttonID: string): void => {},
        DialogType.WARNING,
        null  );
  }


  askDelete(codiceFascicolo:string){
    this.dialogService.showDialog(true, 
      'ANNULA.CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
        (buttonID: string): void => {
          if(buttonID=="1"){
            this.doDelete(codiceFascicolo);
          }
        },
        DialogType.WARNING,
        null  );
  }

  doDelete(event: string) {
    let fascicoloDelete = new Fascicolo();
    fascicoloDelete.codicePraticaAppptr=event
    this.loadingService.emitLoading(true);
    this.fascicoloService.removeFascicolo(fascicoloDelete).then(
      res=>{
        if(res.payload==1){
          this.dialogService.showDialog(true, 
            'ANNULA.SUCCESSO',
            'ANNULA.TITLE',
            DialogButtons.CHIUDI,
              (buttonID: string): void => {
                this.loadFascicolo();
              },
              DialogType.SUCCESS,
              null  );
      }else{
        this.dialogService.showDialog(true, 
          'ANNULA.ERROR',
          'ANNULA.TITLE',
          DialogButtons.CHIUDI,
            (buttonID: string): void => {
              this.loadFascicolo();
            },
            DialogType.ERROR,
            null  );
      }
    }
    ).finally(()=>this.loadingService.emitLoading(false));
    
  }

  /*loadLazyOld($event){
    this.first = $event.first;
    this.rowsPerPage= $event.rows;
    if($event.sortOrder==-1){
      this.sortOrder="desc";
    }else{
      this.sortOrder="asc";
    }
    this.sortField=$event.sortField;
    if(this.first>=0 && this.rowsPerPage>0){
      this.fascicoloSearch.page=(this.first/this.rowsPerPage)+1;
      //this.fascicoloSearch.limit=this.rowsPerPage;
      this.fascicoloSearch.sortBy=this.sortField;
      this.fascicoloSearch.sortType=this.sortOrder;
    }
    this.loadFascicolo();
  }*/

  loadLazy(event:any){
    Object.keys(event).forEach(
      key=>{
        this.fascicoloSearch[key]=event[key]
      }
    )
    this.loadFascicolo();
  }

}
