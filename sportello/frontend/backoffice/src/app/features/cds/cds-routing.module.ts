import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CdsComponent } from './pages/cds/cds.component';

const routes: Routes = [
  {
    path: '',
    component: CdsComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CdsRoutingModule {}
