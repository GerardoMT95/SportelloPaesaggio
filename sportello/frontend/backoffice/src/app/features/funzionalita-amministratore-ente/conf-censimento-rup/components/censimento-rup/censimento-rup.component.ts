import { Component, EventEmitter, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { combineLatest, Subject } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { GenericTableComponent } from 'src/app/shared/components';
import { ButtonType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { PlainTypeStringId } from 'src/app/shared/components/model/logged-user';
import { CONST } from 'src/app/shared/constants';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { RupDTO } from '../../model/rup.models';

@Component({
  selector: 'app-censimento-rup',
  templateUrl: './censimento-rup.component.html',
  styleUrls: ['./censimento-rup.component.scss']
})
export class CensimentoRupComponent implements OnInit, OnDestroy {

  items: RupDTO[] = [];
  intestazioneTabella: TableConfig[] = [
    { field: 'denominazione', header: 'rup.denominazione' },
    { field: 'username', header: 'rup.username' },
    { field: 'dataScadenza', header: 'rup.scadenza', type: 'data' },
  ];
  rups: SelectItem[];
  totalItems = 0;
  public pageObj = {page:1,limit:5};
  formInserimentoRup: FormGroup;
  validation: boolean = false;
  const = CONST;
  unsubscribe$ = new Subject<any>();
  hasLoaded$ = new EventEmitter<boolean>();

  constructor(private fb: FormBuilder,
    private adminService: AdminFunctionsService,
    private loadingService: LoadingService,
    private dialogService: CustomDialogService
  ) { }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit() {
    this.loadingService.emitLoading(true);
    combineLatest([this.adminService.getPotenzialiRup(),
    this.adminService.getRupEnte(this.pageObj)])
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(([p, l]) => {
        this.rups = p.payload.map(el => { return { value: el, label: el.description+' ['+el.value+']' } });
        this.items = l.payload.list;
        this.totalItems = l.numeroTotaleOggetti;
        this.hasLoaded$.emit(true);
        this.loadingService.emitLoading(false);
      });
    this.formInserimentoRup = this.fb.group({
      rup: [null, [Validators.required]],
      dataScadenza: [null, [Validators.required]],
    });
  }

  public paginazioneEvent(event: any): void {
    
    if (event.limit) {
      this.pageObj.limit = event.limit;
    }
    if (event.page) {
      this.pageObj.page = event.page;
    }
    this.loadingService.emitLoading(true);
    this.adminService.getRupEnte(this.pageObj).subscribe(l => {
      this.items = l.payload.list;
      this.totalItems = l.numeroTotaleOggetti;
      this.loadingService.emitLoading(false);
    })
  }

  modalEsitoOperazione(el: any) {
    if (el.codiceEsito == CONST.OK && el.payload == 1) {
      this.dialogService.showDialog(true, 'generic.operazioneOk', 'generic.info',
        [{ id: ButtonType.OK_BUTTON, label: 'generic.prosegui' }],
        (buttonID: number): void => {
          this.paginazioneEvent(this.pageObj);
        },
        DialogType.SUCCESS,
        null);
    } else {
      this.dialogService.showDialog(true, 'generic.error', 'generic.warning',
        [{ id: ButtonType.OK_BUTTON, label: 'generic.chiudi' }],
        (buttonID: number): void => {
        },
        DialogType.ERROR,
        null);
    }
  }


  addRup() {
    this.validation = true;
    if (this.formInserimentoRup.valid) {
      let rawData = this.formInserimentoRup.getRawValue();
      let rup = new RupDTO();
      rup.username = rawData.rup.value;
      rup.denominazione = rawData.rup.description;
      rup.dataScadenza = rawData.dataScadenza;
      this.loadingService.emitLoading(true);
      this.adminService.addRup(rup).subscribe(el => {
        this.loadingService.emitLoading(false);
        this.modalEsitoOperazione(el);
      });
      this.validation = false;
      this.formInserimentoRup.reset();
    }
  }

  remove(event) {
    if (event.username) {
      this.dialogService.showDialog(true, 'generic.vuoiEliminare','generic.warning', 
        [{ id: ButtonType.CANCEL_BUTTON, label: 'generic.annulla' },
        { id: ButtonType.OK_BUTTON, label: 'generic.ok' }],
        (buttonID: number): void => {
          if (buttonID === ButtonType.OK_BUTTON) {
            this.loadingService.emitLoading(true);
            this.adminService.deleteRup(event.username).subscribe(el => {
              this.loadingService.emitLoading(false);
              this.modalEsitoOperazione(el);
            });
          }

        },
        DialogType.WARNING,
        null);
    }
  }
}
