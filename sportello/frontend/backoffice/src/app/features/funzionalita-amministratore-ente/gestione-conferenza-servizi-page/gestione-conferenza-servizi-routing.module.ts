import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneConferenzaServiziPageComponent } from './gestione-conferenza-servizi-page.component';

const routes: Routes = [
  {
    path: ':id',
    component: GestioneConferenzaServiziPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Configurazione conferenza servizi'
        }
      ]
    }
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneConferenzaServiziRoutingModule {}
