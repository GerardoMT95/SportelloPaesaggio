import { ReportRoutingModule } from './report-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReportPageComponent } from './pages/report-page/report-page.component';
import { ReportSearchComponent } from './components/report-search/report-search.component';
import { ReportTableComponent } from './components/report-table/report-table.component';
import { ReportNewComponent } from './components/report-new/report-new.component';

const modules = [
  SharedModule,
  ReportRoutingModule
];

@NgModule({
  declarations: [
  ReportPageComponent,
  ReportSearchComponent,
  ReportTableComponent,
  ReportNewComponent],
  imports: [
    ...modules
  ]
})
export class ReportModule { }
