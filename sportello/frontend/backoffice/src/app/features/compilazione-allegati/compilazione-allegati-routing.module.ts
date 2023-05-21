import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompilazioneAllegatiPageComponent } from './pages/compilazione-allegati-page/compilazione-allegati-page.component';

const routes: Routes = [
  {
    path: '',
    component: CompilazioneAllegatiPageComponent
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompilazioneAllegatiRoutingModule { }
