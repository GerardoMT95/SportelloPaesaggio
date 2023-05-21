import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompilazioneSchedaTecnicaPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: CompilazioneSchedaTecnicaPageComponent
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompilazioneSchedaTecnicaRoutingModule { }
