import { RouterModule, Routes } from '@angular/router';

import { CreazioneNouvoFascicoloPageComponent } from './pages';
import { NgModule } from '@angular/core';

const routes: Routes = [
  {
    path: '',
    component: CreazioneNouvoFascicoloPageComponent,
    data:  {breadcrumb: 'Creazione Nuovo Fascicolo'}
  }
      
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreazioneNuovoFascicoloRoutingModule { }
