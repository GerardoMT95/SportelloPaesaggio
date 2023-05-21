import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { UserService } from 'src/app/services/user.service';
import { CONST } from 'src/shared/constants';
import { RegistrationStatus } from '../model/entity/fascicolo.models';
import { InformazioniDTO } from './../model/entity/info.models';
import { LineaTemporaleDTO } from './../model/entity/informazioni.models';

@Component({
  selector: 'app-page-import-provvedimento',
  templateUrl: './page-import-provvedimento.component.html',
  styleUrls: ['./page-import-provvedimento.component.scss']
})
export class PageImportProvvedimentoComponent implements OnInit, OnDestroy {
  public rsEnum = RegistrationStatus;
  public dettaglio: InformazioniDTO = new InformazioniDTO();
  public fascicoloForm: FormGroup;
  public esitoForm: FormGroup;//lo passo al child e poi lo gestisco qui per il salvataggio dei dati
  public activeIndex: number = 0;
  public stato: string = "";

  public it: any = CONST.IT;
  public id: string;

  unsubscribe$ = new Subject<any>();
  public timeline: LineaTemporaleDTO;


  public validGroup: boolean = null;

  constructor(private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private router: Router,
    private loadingService: LoadingService,
    private promptDialogService: ShowPromptDialogService,
    private translateService: TranslateService,
    private autPaesSvc: AutorizzazioniPaesaggisticheService,
    private userService: UserService) {
    this.loadingService.emitLoading(true);
  }

  ngOnInit() {
    localStorage.clear();
    this.validGroup = this.userService.validGroup;
    this.id = this.route.snapshot.paramMap.get('id');
    combineLatest(this.autPaesSvc.findInformazioniGetAll(this.id),
      this.autPaesSvc.lineaTemporale(this.id),
    )
      .pipe(takeUntil(this.unsubscribe$)).subscribe
      (
        ([response, timeResponse]) => {
          if (response.codiceEsito === CONST.OK) {
            this.dettaglio = response.payload;
            this.stato = response.payload.stato.toString();
            if (timeResponse.codiceEsito === CONST.OK) {
              this.timeline = timeResponse.payload
            }
          }
        }
      );
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  backToFascicolo() {
    this.router.navigate(["/private/fascicolo/dettaglio/",this.id]);
  }

  reloadFascicolo() {
    this.router.navigate(["/private/fascicolo/dettaglio/" ,this.id]);
  }

}
