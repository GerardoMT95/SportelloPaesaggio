import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaComunicazioniPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaComunicazioniPageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaComunicazioniRoutingModule {}
