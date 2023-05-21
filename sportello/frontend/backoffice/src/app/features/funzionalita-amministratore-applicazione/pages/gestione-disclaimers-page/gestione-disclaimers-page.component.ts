import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from 'src/app/shared/constants';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DisclaimerDetailComponent } from '../../components';
import { GenericProcedimentoConfigComponent } from '../../configurations/GenericProcedimentoConfigComponent';
import { DisclaimerDTO } from '../../models/admin-functions.models';

@Component({
  selector: 'app-gestione-disclaimers-page',
  templateUrl: './gestione-disclaimers-page.component.html',
  styleUrls: ['./gestione-disclaimers-page.component.scss']
})
export class GestioneDisclaimersPageComponent extends GenericProcedimentoConfigComponent implements OnInit {
  items: DisclaimerDTO[] = [];
  dettaglioDichiarazione: DisclaimerDTO;
  isAddDichiarazione: boolean;
  touched: boolean = false;

  constructor(private adminSvc: AdminFunctionsService,
    private loadingService: LoadingService,
    private dialog: CustomDialogService,
    private router: Router,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService,
    private translateService: TranslateService,
    private dialogService: CustomDialogService,
    private fb: FormBuilder
  ) {
    super(router, route, breadcrumbService, translateService, "configurazioneProcedimento.sezioni.disclaimers", adminSvc);
  }

  public showTable: boolean = true;

  intestazioneTabella: TableConfig[] = [
    { field: 'ordine', header: 'configurazioneProcedimento.disclaimers.ordine' },
    { field: 'testo', header: 'configurazioneProcedimento.disclaimers.testo' },
    { field: 'tipoReferente', header: 'configurazioneProcedimento.disclaimers.tipoReferente' },
    { field: 'required', header: 'configurazioneProcedimento.disclaimers.required', type: 'boolean' },
  ];

  ngOnInit() {
    this.loadItems();
  }

  loadItems() {
    this.loadingService.emitLoading(true);
    this.adminSvc.getDisclaimersCorrente(this.idTipoProcedimento).subscribe(response => {
      this.loadingService.emitLoading(false);
      this.items = response.payload;
    });
  }

  annullaModifiche() {
    if (this.touched) {
      this.dialogService.showDialog(true
        , "generic.abbandonaModifiche"
        , "generic.info"
        , DialogButtons.CONFERMA_CHIUDI
        , (buttonID: ButtonType): void => {
          if (buttonID == ButtonType.OK_BUTTON)
            this.loadItems();
        }
        , DialogType.INFORMATION
        , null
      );
    } else {
      this.loadItems();
    }
  }

  /**
   * spostamento in alto della riga
   * @param event riga
   */
  public upItem(index: any) {
    let item: DisclaimerDTO = this.items[index];
    this.items[index] = this.items[index - 1];
    this.items[index - 1] = item;
    this.aggiornaOrdine();
  }

  /**
   * spostamento in basso
   * @param event 
   */
  public downItem(index: any) {
    let item: DisclaimerDTO = this.items[index];
    this.items[index] = this.items[index + 1];
    this.items[index + 1] = item;
    this.aggiornaOrdine();
  }

  aggiornaOrdine() {
    this.items.forEach((el, idx) => {
      el.ordine = idx + 1;
    });
    this.touched=true;
  }

  /**
   * rimozione riga
   * @param event 
   */
  public remove(event: any) {
    this.dialogService.showDialog(true
      , "generic.vuoiEliminare"
      , "generic.warning"
      , DialogButtons.CONFERMA_CHIUDI
      , (buttonID: ButtonType): void => {
        if (buttonID == ButtonType.OK_BUTTON)
        this.rimuovi(event);        
      }
      , DialogType.INFORMATION
      , null
    );
    this.touched=true;
  }

  rimuovi(item: any) {
    let indexItem=-1;
    this.items.forEach((itemLista,index)=>{
      if(itemLista==item){
        indexItem=index;
      }
    });
    if(indexItem>=0)  {
      this.items.splice(indexItem, 1);
    }else{
      alert("Errore nella rimozione dell'elemento!!");
    }
  }

  

  /**
   * edit riga
   * @param event 
   */
  public editItem(item: any) {
    this.dettaglioDichiarazione = item;
    this.showTable = false;
    this.touched=true;
  }



  public addItem(): void {
    this.isAddDichiarazione = true;
    this.showTable = false;
    this.touched=true;
    this.dettaglioDichiarazione={
      id:null,
      required:true,
      testo:null,
      tipoProcedimento:Number.parseInt(this.idTipoProcedimento),
      tipoReferente:"SD",
      ordine:this.items.length+1};
  }

  public closeDettaglio(): void {
    this.showTable = true;
    this.isAddDichiarazione = false;
  }


  public salvaDichiarazioni() {
    if (this.items && this.items.length) {
      this.dialogService.showDialog(true
        , "generic.confermaSalva"
        , "generic.info"
        , DialogButtons.CONFERMA_CHIUDI
        , (buttonID: ButtonType): void => {
          if (buttonID == ButtonType.OK_BUTTON)
            this.confirmSaveDichiarazionePrivacy();
        }
        , DialogType.INFORMATION
        , null
      );
    } else {
      this.dialogService.showDialog(true
        , "generic.almenoUnElemento"
        , "generic.warning"
        , DialogButtons.CHIUDI
        , (buttonID: ButtonType): void => { }
        , DialogType.WARNING
        , null
      );
    }
  }


  public confirmSaveDichiarazionePrivacy(): void {
    this.loadingService.emitLoading(true);
    this.adminSvc.updateDisclaimersCorrente(this.idTipoProcedimento, this.items)
      .subscribe(response => {
        this.loadingService.emitLoading(false);
        if (response.codiceEsito === CONST.OK) {
          this.items = response.payload;
          this.dialogService.showDialog(
            true,
            "generic.operazione_ok",
            "generic.successo",
            DialogButtons.CHIUDI,
            (buttonID: string): void => {
              this.touched=false;
             },
            DialogType.SUCCESS,
            null
          );
        }
      });
  }

  
  public back(): void {
    if (this.touched) {
      this.dialogService.showDialog(true
        , "generic.abbandonaModifiche"
        , "generic.info"
        , DialogButtons.CONFERMA_CHIUDI
        , (buttonID: ButtonType): void => {
          if (buttonID == ButtonType.OK_BUTTON)
            super.back()
        }
        , DialogType.INFORMATION
        , null
      );
    } else {
      super.back();
    }
  }

}
