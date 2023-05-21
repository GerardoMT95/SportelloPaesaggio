import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzePageComponent
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzeRoutingModule { }
