import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ValidatorFn } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TableConfig } from 'src/app/core/models/header.model';
import { ViewMapper } from '../../util/UtilViewMapper';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { CustomDialogService } from './../../../core/services/dialog.service';
import { DataService } from './../../../features/gestione-istanza/services/data-service/data.service';



@Component({
  selector: 'app-building-table',
  templateUrl: './building-table.component.html',
  styleUrls: ['./building-table.component.scss'],
})
export class BuildingTableComponent implements OnInit, OnDestroy
{
  @Input() form: FormGroup;
  @Input() formArrayName: string;
  @Input() tableHeaders: TableConfig[] = [];
  @Input() set tableData(data: any[])
  {
    this.initTableData(data);
    this.loaded = true;
  }
  @Input() nomeTabella: string = ""; //serve a filtrare gli eventi di addRiga esterni tramite subjBuildingTable$ in SharedDataService
  @Input() rimuoviAncheUltimaItem: boolean = false;
  @Input() submitted: boolean = false;
  @Input() formValidator: ValidatorFn[] = null;
  @Input() disabled: boolean = false;
  @Input() currentDate:Date=null;
  searchText: string;
  loaded: boolean = false;
  subscriptionAppendRigaEsterno: Subscription;
  //@Output() emitAddRow: EventEmitter<boolean> = new EventEmitter<boolean>();
  constructor(private fb: FormBuilder,
    private serviceData: DataService,
    private dialogService: CustomDialogService) { }

  ngOnDestroy(): void
  {
    if (this.subscriptionAppendRigaEsterno)
    {
      this.subscriptionAppendRigaEsterno.unsubscribe();
    }
  }

  ngOnInit()
  {
    this.subscriptionAppendRigaEsterno =
      this.serviceData.subjBuildingTable$.subscribe(evento =>
      {
        if (this.nomeTabella != "" && evento.tabellaRef == this.nomeTabella)
        {
          //e' di mia competenza
          this.addItemFromExternal(evento.riga);
        }
      });
    if (this.disabled)
      this.form.disable();
  }

  initTableData(tableData)
  {
    this.formArray.clear();
    if (tableData)
    {
      tableData.forEach((value) =>
      {
        this.appendRow(value)
        /*const newFormGroup = this.getFormGrupForTable();
        this.formArray.push(newFormGroup);
        ViewMapper.mapObjectToForm(newFormGroup, value);*/
      });
    }
  }

  appendRow(tableRow: any)
  {
    const newFormGroup = this.getFormGrupForTable();
    this.formArray.push(newFormGroup);
    ViewMapper.mapObjectToForm(newFormGroup, tableRow);
  }

  get formArray()
  {
    return this.form.get(this.formArrayName) as FormArray;
  }

  deleteFiledValue(index: number)
  {
    this.dialogService.showDialog(true,
      'ANNULA.CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
          this.deleteFormValue(index);
        }
      },
      DialogType.WARNING,
      null);
  }


  deleteAll()
  {
    if (this.formArray.length == 0) return;
    this.dialogService.showDialog(true,
      'ANNULA.ALL_CONFIRMATION_MESSAGE',
      'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: string): void =>
      {
        if (buttonID == "1")
        {
          while (this.formArray.length >= 1)
          {
            this.deleteFormValue(0);
          }
        }
      },
      DialogType.WARNING,
      null);
  }

  deleteFormValue(i)
  {
    if (this.formArray.length > 1)
    {
      this.formArray.removeAt(i);
    } else if (this.formArray.length === 1)
    {
      if (this.rimuoviAncheUltimaItem)
      {
        this.formArray.removeAt(i);
      } else
      {
        (this.formArray.controls[0] as FormGroup).reset();
      }
    }
    this.form.markAsDirty();
  }

  addItemFromExternal(tableRow: any)
  {
    this.appendRow(tableRow);
    this.form.markAsDirty();
  }

  addItem()
  {
    (this.form.get(this.formArrayName) as FormArray).push(this.getFormGrupForTable());
    this.form.markAsDirty();
  }

  getFormGrupForTable(): FormGroup
  {
    return this.fb.group({
      ...this.prepareFormBuilder(this.tableHeaders)
    }, { validators: this.formValidator });
  }
  //defaults je ako vec postoje neki elementi koji su ubaceni
  prepareFormBuilder(fields: TableConfig[], defaults?: any)
  {
    const form = {};
    fields.forEach((item) =>
    {
      return (form[item.field] = ['', item.validators]);
    });
    return form;
  }
}
