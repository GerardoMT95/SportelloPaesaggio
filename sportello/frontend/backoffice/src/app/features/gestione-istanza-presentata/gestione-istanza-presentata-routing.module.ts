import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaPresentataPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaPresentataPageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaPresentataRoutingModule {}
