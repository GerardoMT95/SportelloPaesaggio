import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { paths } from 'src/app/app-routing.module';
import { getGroupType } from 'src/app/core/functions/generic.utils';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { Allegato, Fascicolo, GroupType, StatoEnum } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { UserService } from 'src/app/shared/services/user.service';
import { buildForbiddenRouteslist } from '../../functions/utils';
import { CONST } from './../../../../shared/constants';
import { LoadingService } from './../../../../shared/services/loading.service';
import { IstanzaFascicolo } from './../../configuration/tabs.const';
import { RoutesHandler } from './../../models/routes-handler.models';
import { DataService } from './../../services/data-service/data.service';

@Component({
  selector: 'app-gestione-istanza-page',
  templateUrl: './gestione-istanza-page.component.html',
  styleUrls: ['./gestione-istanza-page.component.scss']
})
export class GestioneIstanzaPageComponent implements OnInit, OnDestroy
{
  public id: string;
  public codiceFascicolo: string;
  public form: FormGroup;
  public generatedDocuments: Allegato[];
  public currentUser: LoggedUser;
  public currentGroupType: GroupType;
  public statoEnum = StatoEnum;

  private unsubscribe$: Subject<void> = new Subject<void>();
  private routesHandler: RoutesHandler = new RoutesHandler();

  public compilazione: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private adminService: AdminFunctionsService,
              private service: FascicoloService,
              private sharedData: DataService,
              private loading: LoadingService)
  {
    this.loading.emitLoading(true);
    this.codiceFascicolo = this.route.snapshot.paramMap.get('id');
    this.currentUser = this.userService.getUser();
    this.router.events.pipe(takeUntil(this.unsubscribe$)).subscribe(a => 
    {
      if (a instanceof NavigationEnd){
        this.compilazione = a.url.includes("/fascicolo/");
      }
    }); 
    console.log("comp:", this.compilazione);
  }

  ngOnInit()
  {
    /* this.store.setBreadcrumbs([{ label: 'Dettaglio' }]); */
    this.loading.emitLoading(true);
    combineLatest([this.service.callFindByCode(this.codiceFascicolo),
      this.adminService.hasProtocolloAutomatico(this.userService.idOrganizzazione)]).subscribe(
      ([response,respHasProto])=>{
        if (response.codiceEsito === CONST.OK && response.payload)
        {
          this.sharedData.fascicolo = response.payload;
          this.sharedData.hasProtocollazione = respHasProto.payload;
          this.id = this.sharedData.idPratica;
          this.form = this.buildForm();
          this.currentGroupType = getGroupType(this.userService.actualGroup);
          this.routesHandler = buildForbiddenRouteslist(this.sharedData.status, this.sharedData.fascicolo, this.currentGroupType);
          // this.router.navigate(['gestione-istanze', this.codiceFascicolo, this.routesHandler.defaultRoute]);
          this.sharedData.statusObservable.pipe(takeUntil(this.unsubscribe$)).subscribe(status =>
          {
            if(status)
              this.routesHandler = buildForbiddenRouteslist(this.sharedData.status, this.sharedData.fascicolo, this.currentGroupType)
          });
        }
      this.loading.emitLoading(false);
      }
    );
  }

  ngOnDestroy(): void 
  { 
    this.unsubscribe$.next();
    this.sharedData.fascicolo = null; 
    this.sharedData.hasProtocollazione = false; 
  }

  get fascicolo(): Fascicolo { return this.sharedData.fascicolo; }
  get stato(): StatoEnum { return this.sharedData.status; }
  get tabs(): any { return IstanzaFascicolo.tabs; }
  get suspendedOrArchived(): boolean { return this.sharedData.fascicolo.storicoASR && this.sharedData.fascicolo.storicoASR.length > 0 }


  public loadFascicolo(): void
  {
    this.service.callFindByCode(this.codiceFascicolo).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK && response.payload)
        this.sharedData.fascicolo = response.payload;
    });
  }

  public buildForm(): FormGroup
  {
    return this.formBuilder.group
    ({
      codiceFascicolo: [''],
      ente: ['', Validators.required],
      oggetto: ['', Validators.required],
      tipoProcedimento: ['', Validators.required],
      dataCreazione: [null],
      attivitaDaEspletare: [StatoEnum.PresaInCarica]
    });
  }
  
  public back(): void
  {
    this.router.navigate([paths.listManagement()]);
    this.sharedData.fascicolo = null;
  }
}
