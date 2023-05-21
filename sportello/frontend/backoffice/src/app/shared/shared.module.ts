import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from "@ngx-translate/core";
import { DataTableModule } from 'ng-angular8-datatable';
import { AccordionModule } from 'primeng/accordion';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from "primeng/button";
import { CalendarModule } from 'primeng/calendar';
import { ChartModule } from "primeng/chart";
import { DynamicDialogModule } from 'primeng/components/dynamicdialog/dynamicdialog';
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { KeyFilterModule } from 'primeng/keyfilter';
import { MenuModule } from "primeng/menu";
import { MultiSelectModule } from 'primeng/multiselect';
import { PaginatorModule } from "primeng/paginator";
import { CheckboxModule, ContextMenuModule, EditorModule, OverlayPanelModule, PanelMenuModule, PanelModule, SelectButtonModule, ToggleButtonModule } from 'primeng/primeng';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { TooltipModule } from 'primeng/tooltip';
import { TriStateCheckboxModule } from 'primeng/tristatecheckbox';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import * as fromComponents from './components';
import * as fromDirectives from "./directive";
import * as fromPipes from "./pipes";
import { FooterComponent } from './components/footer/footer.component';



const modules: any[] = 
[
  CommonModule,
  TableModule,
  FormsModule,
  RouterModule,
  ButtonModule,
  DialogModule,
  DropdownModule,
  CalendarModule,
  TabViewModule,
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
  CheckboxModule,
  EditorModule,
  OverlayPanelModule,
  PanelModule,
  PanelMenuModule,
  SelectButtonModule,
  DataViewModule,
  DynamicDialogModule,
  ContextMenuModule,
  ToggleButtonModule,
  TriStateCheckboxModule,
  ScrollPanelModule,  
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
