import { ReportPageComponent } from './pages/report-page/report-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportGuard } from './configuration/report.guard';

const routes: Routes = [{path: '',data: null,component: ReportPageComponent,canActivate: [ReportGuard]}];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  providers: [ReportGuard],
  exports: [RouterModule]
})
export class ReportRoutingModule { }
