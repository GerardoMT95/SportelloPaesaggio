import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
//import * as fromPipes from "./pipes";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from "@ngx-translate/core";
import { DataTableModule } from 'ng-angular8-datatable';
import { NgxCsvParserModule } from 'ngx-csv-parser';
import { AccordionModule } from 'primeng/accordion';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from "primeng/button";
import { CalendarModule } from 'primeng/calendar';
import { ChartModule } from "primeng/chart";
import { DataViewModule } from "primeng/dataview";
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { KeyFilterModule } from 'primeng/keyfilter';
import { MenuModule } from "primeng/menu";
import { MultiSelectModule } from 'primeng/multiselect';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { PaginatorModule } from "primeng/paginator";
import { CheckboxModule, EditorModule, PanelModule, SelectButtonModule,SpinnerModule } from 'primeng/primeng';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { TooltipModule } from 'primeng/tooltip';
import * as fromComponents from './components';
import * as fromDirectives from "./directive";
import * as fromPipes from './pipes';


const modules: any[] = [
  CommonModule,
  TableModule,
  ButtonModule,
  DialogModule,
  DropdownModule,
  SelectButtonModule,
  FormsModule,
  CalendarModule,
  TabViewModule,
  DataViewModule,
  RadioButtonModule,
  FileUploadModule,
  KeyFilterModule,
  AutoCompleteModule,
  MultiSelectModule,
  ChartModule,
  MenuModule,
  DataTableModule,
  TranslateModule,
  NgbModule,
  FontAwesomeModule,
  ReactiveFormsModule,
  PaginatorModule,
  DropdownModule,
  InputTextareaModule,
  DialogModule,
  AccordionModule,
  BreadcrumbModule,
  TooltipModule,
  ScrollPanelModule,
  CheckboxModule,
  EditorModule,
  PanelModule,
  OverlayPanelModule,
  RouterModule,
  SpinnerModule,
  NgxCsvParserModule
];

@NgModule({
  declarations: [
    ...fromComponents.components,
    ...fromDirectives.directives,
    ...fromPipes.pipes,
  ],
  imports: [
    ...modules
  ],
  exports: [
    ...modules,
    ...fromComponents.components,
    ...fromDirectives.directives,
    ...fromPipes.pipes
  ],
})
export class SharedModule { }
