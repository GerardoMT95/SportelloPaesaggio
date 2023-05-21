import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaParerePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaParerePageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaParereRoutingModule {}
