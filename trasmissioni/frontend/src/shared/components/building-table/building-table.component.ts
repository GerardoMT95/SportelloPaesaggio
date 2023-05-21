import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { FormArray, FormArrayName, FormBuilder, FormGroup, Validators, ValidatorFn } from '@angular/forms';

//import { TableConfig } from 'src/app/core/models/header.model';
import { ViewMapper } from '../../util/UtilViewMapper';
import { Subscription } from 'rxjs';
//import { SharedDataService } from '../../services/shared-data/shared-data.service';
//import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
//import { DialogService } from 'src/app/core/services/dialog.service';
import { TableConfig } from 'src/app/components/model/entity/fascicolo.models';
import { DataService } from 'src/app/services/data.service';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-building-table',
  templateUrl: './building-table.component.html',
  styleUrls: ['./building-table.component.scss']
})
export class BuildingTableComponent implements OnInit,OnDestroy {
  @Input() form: FormGroup;
  @Input() formArrayName: string;
  @Input() tableHeaders: TableConfig[] = [];
  @Input() submitted: boolean=false;
  @Input() formValidator:ValidatorFn[]=null;
  @Input() set tableData(data: any[]) {
    this.initTableData(data);
    this.loaded=true;
  }
  @Input() nomeTabella:string=""; //serve a filtrare gli eventi di addRiga esterni tramite subjBuildingTable$ in SharedDataService
  @Input() rimuoviAncheUltimaItem:boolean=false;
  @Input() disabled:boolean=false;
  searchText: string;
  loaded:boolean=false;
  subscriptionAppendRigaEsterno:Subscription;
  //@Output() emitAddRow: EventEmitter<boolean> = new EventEmitter<boolean>();
  constructor(private fb: FormBuilder,
    private serviceData:DataService,
    private translateService:TranslateService
    ) {}

//oggetto utilizzato per l'alert
public alertData =
{
  display: false,
  title: "",
  content: "",
  typ: "",
  extraData: null,
  isConfirm: false,
};


  ngOnDestroy(): void {
    if(this.subscriptionAppendRigaEsterno){
      this.subscriptionAppendRigaEsterno.unsubscribe();
    }
  }

  ngOnInit() {
    this.subscriptionAppendRigaEsterno=
    this.serviceData.subjBuildingTable$.subscribe(evento=>{
      if(this.nomeTabella!="" && evento.tabellaRef==this.nomeTabella){
        //e' di mia competenza
        this.addItemFromExternal(evento.riga);
      }
    });
    if (this.disabled) {
      this.form.disable();
    }
  }

  initTableData(tableData) {
    while(this.formArray.length>0){
      this.formArray.removeAt(0);
    }
    if (tableData) {
      tableData.forEach((value) => {
        this.appendRow(value)
        /*const newFormGroup = this.getFormGrupForTable();
        this.formArray.push(newFormGroup);
        ViewMapper.mapObjectToForm(newFormGroup, value);*/
      });
    }
  }

  appendRow(tableRow:any){
    const newFormGroup = this.getFormGrupForTable();
        this.formArray.push(newFormGroup);
        ViewMapper.mapObjectToForm(newFormGroup, tableRow);
  }

  get formArray() {
    return this.form.get(this.formArrayName) as FormArray;
  }

  deleteFiledValue(index: number){
    this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('TABLE_DELETE_BUTTON');
      this.alertData.content = this.translateService.instant('DELETE_PROMPT_BUTTON_MESSAGE');
      this.alertData.extraData = { operazione: 'elimina',index:index};
      this.alertData.display = true;
    /*this.dialogService.showDialog(true, 
      'ANNULA.CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
        (buttonID: string): void => {
          if(buttonID=="1"){
            this.deleteFormValue(index);
          }
        },
        DialogType.WARNING,
        null  );*/
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'elimina':
            this.deleteFormValue(event.extraData.index);
            break;
          case 'eliminaTutti':  
            while (this.formArray.length >= 1) {
              this.deleteFormValue(0); 
            }
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  deleteAll(){
    if(this.formArray.length==0) return;
    this.alertData.isConfirm = true;
      this.alertData.typ = "info";
      this.alertData.title = this.translateService.instant('ANNULA.TITLE');
      this.alertData.content = this.translateService.instant('ANNULA.CONFIRMATION_MESSAGE');
      this.alertData.extraData = { operazione: 'eliminaTutti'};
      this.alertData.display = true;
    /*this.dialogService.showDialog(true, 
      'ANNULA.ALL_CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
        (buttonID: string): void => {
          if(buttonID=="1"){
            while (this.formArray.length >= 1) {
              this.deleteFormValue(0); 
            }
          }
        },
        DialogType.WARNING,
        null  );*/
  }

  deleteFormValue(i) {
    if (this.formArray.length > 1) {
      this.formArray.removeAt(i);
    } else if (this.formArray.length === 1) {
      if(this.rimuoviAncheUltimaItem){
        this.formArray.removeAt(i);
      }else{
        (this.formArray.controls[0] as FormGroup).reset();
      }
    }
    this.form.markAsDirty();
  }

  addItemFromExternal(tableRow:any){
    this.appendRow(tableRow);
    this.form.markAsDirty();
  }

  addItem() {
    (this.form.get(this.formArrayName) as FormArray).push(this.getFormGrupForTable());
    this.form.markAsDirty();
  }

  getFormGrupForTable(): FormGroup {
    return this.fb.group({
      ...this.prepareFormBuilder(this.tableHeaders)
    },{validators:this.formValidator});
  }
  prepareFormBuilder(fields: TableConfig[], defaults?: any) {
    const form = {};
    fields.forEach((item) => {
      // if (defaults) {
      // 	defaults[item.field] = item.isDate
      // 		? defaults[item.field] !== null
      // 			? new Date(defaults[item.field])
      // 			: defaults[item.field]
      // 		: defaults[item.field];
      // }
      //umesto Validators.required ici ce validator koji se kroz TableConfig prosledi za taj item
      return (form[item.field] = ['', item.validators]);
    });
    return form;
  }


}
