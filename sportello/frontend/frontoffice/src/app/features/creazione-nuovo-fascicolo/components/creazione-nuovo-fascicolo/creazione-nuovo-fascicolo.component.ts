import { CONST } from 'src/app/shared/constants';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DialogModel } from 'src/app/core/models/dialog.model';
import { DialogService } from 'src/app/core/services/dialog.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { PraticaDelegato } from 'src/app/shared/models/models';

@Component({
  selector: 'app-creazione-nuovo-fascicolo',
  templateUrl: './creazione-nuovo-fascicolo.component.html',
  styleUrls: ['./creazione-nuovo-fascicolo.component.scss']
})
export class CreazioneNuovoFascicoloComponent implements OnInit,OnChanges {
  @Input() form: FormGroup;
  @Output() emitCreation: EventEmitter<any> = new EventEmitter<any>();
  @Input() typeProcedimento: SelectOption[];
  @Input() enteDelegato: SelectOption[];
  @Input() ruoliPratica: SelectOption[];
  isInSanatoriaEnabled:boolean=false;
  @Input() messaggioEntiAttivi:string="";

  public activeIndex: number = 0;
  public file: File = null;
  public delegato: boolean = false;
  public delegatoPratica: Array<PraticaDelegato> = [];
 
  constructor(private fb: FormBuilder, 
    private httpDominio: HttpDominioService, 
    private fascicoloSvc:FascicoloService,
    private router: Router,
    private loadingService:LoadingService,
    private dialogService:DialogService) {}


  ngOnChanges(changes: SimpleChanges): void {
    if(changes.enteDelegato && changes.enteDelegato.currentValue && !changes.enteDelegato.previousValue){
      this.showMessaggio();
    }
  }

  ngOnInit() {
   this.form.get('tipoProcedimento').valueChanges.subscribe(
     el=>{
      this.refreshIsInSanatoriaEnabled(el);});
  }

  showMessaggio(){
    let entiDelegatiList="";
      this.enteDelegato.forEach(ente=>{
        entiDelegatiList+='<li>'+ente.description+'</li>';
      });
      entiDelegatiList='<ul>'+entiDelegatiList+'</ul>';
      this.messaggioEntiAttivi=this.messaggioEntiAttivi.replace("{ENTI_ABILITATI}",entiDelegatiList);
      let _this=this;
      let dialogModel:DialogModel=
        {
          showDialog: true,
          message: this.messaggioEntiAttivi,
          messageTranslateParams: null,
          title: 'ATTENZIONE',
          buttons: DialogButtons.SI_NO,
          btnCalback: (buttonPressed)=>{
            if(buttonPressed!=ButtonType.OK_BUTTON){
              _this.back();
            }
        },
          dialogType: DialogType.INFORMATION,
          redirectUrl: null,
          isLarge:true
      };
      this.dialogService.showDialogCustom(dialogModel); 
  }

  create() {
    this.emitCreation.emit({form: this.form, file: this.file});
  }

  back() {
    this.router.navigate(['/']);
  }

  refreshIsInSanatoriaEnabled(newValue){
    this.isInSanatoriaEnabled=false;
    this.form.get('isInSanatoria').setValue(false);
    let tipoProcedimento=newValue;
    this.typeProcedimento.forEach(element => {
      if((element.value==tipoProcedimento || 
          element.value==tipoProcedimento.value) && 
          element.linked && element.linked=='isInSanatoria'){
            this.isInSanatoriaEnabled=true;
      }
    }); 
  }

  public onSelectFile(files: File[]): void
  {
    this.file = files[0];
  }

  public onCancelFile(event: any): void
  {
    this.file = null;
  }

  public changeRuolo(): void
  {
    if (this.form.controls.ruoloPratica.value == 'DE')
    {
      this.loadingService.emitLoading(true);
      this.fascicoloSvc.getLastDelegato().subscribe(response =>
      {
        if (response.codiceEsito == CONST.OK)
        {
          this.delegato = true;
          this.delegatoPratica = [response.payload];
        }
        this.loadingService.emitLoading(false);
      });
    }
    else
      this.delegato = false;

  }

}
