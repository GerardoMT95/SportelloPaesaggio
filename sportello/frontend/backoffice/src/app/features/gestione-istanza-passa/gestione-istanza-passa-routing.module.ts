import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaPassaPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaPassaPageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaPassaRoutingModule {}
