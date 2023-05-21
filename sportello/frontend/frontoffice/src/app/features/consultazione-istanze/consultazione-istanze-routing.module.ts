import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultazioneIstanzePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: ConsultazioneIstanzePageComponent
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConsultazioneIstanzeRoutingModule { }
