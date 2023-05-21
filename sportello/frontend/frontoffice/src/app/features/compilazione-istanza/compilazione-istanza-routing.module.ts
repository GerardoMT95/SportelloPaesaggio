import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompilazioneIstanzaPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: CompilazioneIstanzaPageComponent,
    data: { breadcrumb: 'Sezione istanza' }
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompilazioneIstanzaRoutingModule { }
