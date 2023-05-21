import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaLocalizzazionePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaLocalizzazionePageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaLocalizzazioneRoutingModule {}
