import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaRelazioneEntePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaRelazioneEntePageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaRelazioneEnteRoutingModule {}
