import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, ButtonType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from 'src/app/shared/constants';
import { CdsSaveConfigurazione, SelectItemDto } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { GenericProcedimentoConfigComponent } from '../../funzionalita-amministratore-applicazione/configurations/GenericProcedimentoConfigComponent';

@Component({
  selector: 'app-gestione-conferenza-servizi-page',
  templateUrl: './gestione-conferenza-servizi-page.component.html',
  styleUrls: ['./gestione-conferenza-servizi-page.component.scss']
})
export class GestioneConferenzaServiziPageComponent implements OnInit {

  public form: FormGroup;

  public azioni:SelectItemDto[];
  public azioniAll:SelectItemDto[];
  public attivita:SelectItemDto[];
  public tipi:SelectItemDto[];
  public idTipoProcedimento: string;
  
  public validation:boolean = false;

  constructor(private adminSvc: AdminFunctionsService,
    private loadingService: LoadingService,
    private dialog: CustomDialogService,
    private router: Router,
    private route: ActivatedRoute,
    private translateService: TranslateService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void{
    this.idTipoProcedimento = this.route.snapshot.params['id'];
    this.buildForm();
    this.getConfigurazione();
  }

  private buildForm() {
    this.form = new FormGroup({attivita: new FormControl(null, Validators.required)
                              ,azione  : new FormControl(null, Validators.required)
                              ,tipo    : new FormControl(null, Validators.required)
                             });
   }

   private getConfigurazione(loading:boolean = true):void{
     this.validation = false;
     if(loading)
        this.loadingService.emitLoading(true);
    this.adminSvc.getConfigurazioneCds(this.idTipoProcedimento)
    .subscribe(response =>{
      if(CONST.OK === response.codiceEsito){
        this.form.controls.attivita.setValue(response.payload.attivita);
        this.form.controls.azione  .setValue(response.payload.azioni  );
        this.form.controls.tipo    .setValue(response.payload.tipo    );

        this.azioniAll = response.payload.azioniSelezionabili;
        this.tipi      = response.payload.tipoSelezionabili;
        this.attivita  = response.payload.attivitaSelezionabili;

        this.changeAttivita();
      }
      if(loading)
        this.loadingService.emitLoading(false);
    })
   }

   public salva():void{
    this.validation = true;
    if(this.form.valid){
      this.dialog.showDialog(true
                            ,this.translateService.instant("GESTIONE_CDS.DIALOG.CONFIRM_CONTENT")
                            ,this.translateService.instant("GESTIONE_CDS.DIALOG.CONFIRM_TITLE")
                            ,DialogButtons.CONFERMA_CHIUDI
                            ,(x) => {if(x === ButtonType.OK_BUTTON) {this.confirmSalva();}}
                            ,DialogType.INFORMATION
                            );
      }
   }
   
   public annulla():void{
      this.dialog.showDialog(true
                             ,this.translateService.instant("GESTIONE_CDS.DIALOG.ANNULLA_CONTENT")
                             ,this.translateService.instant("GESTIONE_CDS.DIALOG.ANNULLA_TITLE")
                             ,DialogButtons.CONFERMA_CHIUDI
                             ,(x) => {if(x === ButtonType.OK_BUTTON) {this.getConfigurazione();}}
                             ,DialogType.INFORMATION
                             );
   }
   
   private confirmSalva():void{
     this.loadingService.emitLoading(true);
     let dto :CdsSaveConfigurazione = {azioni:this.form.controls.azione.value
                                      ,attivita:this.form.controls.attivita.value
                                      ,tipo:this.form.controls.tipo.value
                                      };
     this.adminSvc.saveConfigurazioneCds(this.idTipoProcedimento, dto)
     .subscribe(response=>{
       if(CONST.OK == response.codiceEsito){
        this.dialog.showDialog(true
                              ,this.translateService.instant("GESTIONE_CDS.DIALOG.SAVE_CONTENT")
                              ,this.translateService.instant("GESTIONE_CDS.DIALOG.SAVE_TITLE")
                              ,DialogButtons.CHIUDI
                              ,(x) => {}
                              ,DialogType.SUCCESS
                              );
          this.getConfigurazione();
       }else{
         this.loadingService.emitLoading(false);
       }
     })
   }

   public indietro():void {
    this.dialog.showDialog(true
                           ,this.translateService.instant("GESTIONE_CDS.DIALOG.ANNULLA_CONTENT")
                           ,this.translateService.instant("GESTIONE_CDS.DIALOG.ANNULLA_TITLE")
                          ,DialogButtons.CONFERMA_CHIUDI
                          ,(x) => {if(x === ButtonType.OK_BUTTON) {this.router.navigate(['admin', 'conf-procedimento', this.idTipoProcedimento])}}
                          ,DialogType.INFORMATION
                          );
  }


  public changeAttivita(event:any = null):void{
    //recupero gli array di elmenti selezionati
    let attivita : SelectItemDto[] = this.form.controls.attivita.value;
    let azioniValue : SelectItemDto[] = this.form.controls.azione.value;

    let newAzioniValue : SelectItemDto[] = [];
    this.azioni = [];
    //Se ho almeno un' attivita 
    if(attivita){
      for(let azione of this.azioniAll){
        //controllo se una delle attivita selezionate appartenga all'azione.
        //Se si lo aggiungo alle azioni selezionabili
        let attivitaFilter :SelectItemDto[] = attivita.filter(item=>item.value == azione.parent);
        if(attivitaFilter && attivitaFilter.length > 0){
          this.azioni.push(azione);
        }
      }
      for(let azione of azioniValue){
        //controllo se una delle attivita selezionate appartenga all' azione selezionata
        //se si lo aggiungo all'array delle nuove azioni 
        let attivitaFilter :SelectItemDto[] = attivita.filter(item=>item.value == azione.parent);
        if(attivitaFilter && attivitaFilter.length > 0){
          newAzioniValue.push(azione);
        }
      }
    }
    this.form.controls.azione.setValue(newAzioniValue);
  }


}
