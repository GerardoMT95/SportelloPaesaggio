import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { paths } from 'src/app/app-routing.module';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { TableConfig } from 'src/app/core/models/header.model';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { Allegato, Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { FascicoloService } from '../../../../shared/services/pratica/http-fascicolo.service';
import { BFile, DocumentType } from './../../../../shared/models/allegati.model';
import { HttpAllegatoService } from './../../../../shared/services/http-allegato.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';


@Component({
  selector: 'app-gestione-istanza-presentata-page',
  templateUrl: './gestione-istanza-presentata-page.component.html',
  styleUrls: ['./gestione-istanza-presentata-page.component.scss']
})
export class GestioneIstanzaPresentataPageComponent implements OnInit,OnDestroy
{
  public codiceFascicolo: string;
  public form: FormGroup;
  public generatedDocuments: Allegato[];
  public subscribedDocuments: Allegato[] = [];
  entiDelegati: SelectOption[];
  unsubscribe$ = new Subject<void>();
  loaded:boolean=false;
  public files: Array<BFile> = [];
  public types: DocumentType[] = [
    { label: "Istanza", type: 700, required: true, multiple: false, accept: ["application/pdf"] },
    { label: "Dichiarazione tecnica", type: 701, required: true, multiple: false, accept: ["application/pdf"] },
  ]

  constructor(private route: ActivatedRoute,
              private router: Router,
              private store: FascicoloStore,
              private formBuilder: FormBuilder,
              private dataService: DataService,
              private service: FascicoloService,
              private loading: LoadingService,
              private allegati: AllegatoService,
              private httpDominioService:HttpDominioService)
  {  }

  ngOnDestroy(): void
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit()
  {
    this.store.setBreadcrumbs([{ label: 'Dettaglio' }]);
    this.loading.emitLoading(true);
    let entiDelegati$ = this.httpDominioService.getEnteDelegato();
    let dich$=this.service.callGetDichiarazioni(this.fascicolo.id);
    this.loading.emitLoading(true);
    combineLatest([dich$, entiDelegati$])
      .pipe(takeUntil(this.unsubscribe$)).subscribe(([dich, entiDeleg]) =>
    {
      this.loading.emitLoading(false);
      if(dich.codiceEsito === CONST.OK)
        this.files = dich.payload;
      this.entiDelegati=entiDeleg;
      this.form = this.buildForm();
      ViewMapper.mapObjectToForm(this.form, this.fascicolo);
      this.loaded=true;
    });
    
    
    /*this.service.callGetDichiarazioni(this.fascicolo.id).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.files = response.payload;
      this.loading.emitLoading(false);
    });*/
  }

  public downloadFile(event: BFile): void
  {
    this.loading.emitLoading(true);
    this.allegati.downloadAllegatoFascicolo(event.id,this.fascicolo.id, '/istruttoria/allegati/download.pjson')
    //this.allegati.callDownloadAllegato(event.id)
    .subscribe(response =>
    {
      if(response.status == 200)
        downloadFile(response.body, event.name);
      this.loading.emitLoading(false);
    });
  }

  get fascicolo(): Fascicolo { return this.dataService.fascicolo;/* this.store.state.fascicolo ;*/ }
  get metadata() { return this.store.state.meta; }
  get statoFascicolo(): StatoEnum { return this.dataService.status; }

  public buildForm(): FormGroup
  {
    return this.formBuilder.group({
      codiceFascicolo: [{ value: '', disabled: true }],
      enteDelegato: [{ value: '', disabled: true }],
      oggetto: [{ value: '', disabled: true }],
      tipoProcedimento: [{ value: '', disabled: true }],
      dataCreazione: [null],
      attivitaDaEspletare: [StatoEnum.PresaInCarica]
    });
  }

  public sezioniDetails(event: 'istanza' | 'scheda' | 'allegati'): void
  {
    switch (event)
    {
      case 'istanza':
        this.router.navigate([paths.instanceManagement(this.fascicolo.codicePraticaAppptr)]);
        break;
      case 'scheda':
        this.router.navigate([paths.dataSheetManagement(this.fascicolo.codicePraticaAppptr)]);
        break;
      case 'allegati':
        this.router.navigate([paths.attachmentManagement(this.fascicolo.codicePraticaAppptr)]);
        break;
      default:
        break;
    }
  }

  get headers(): TableConfig[]
  {
    return [
      {
        field: "type",
        type: "type",
        header: "Tipo file"
      },
      {
        field: "name",
        type: "text",
        header: "Nome file"
      },
      {
        field: "uploadDate",
        type: "date",
        header: "Data caricamento"
      },
      {
        field: "checksum",
        type: "text",
        header: "Impronta hash"
      }
    ]
  }
}
