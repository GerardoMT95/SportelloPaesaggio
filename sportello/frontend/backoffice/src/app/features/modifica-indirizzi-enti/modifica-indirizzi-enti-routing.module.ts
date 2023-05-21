import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ModificaIndirizziEntiPageComponent } from './pages';
import { ModificaIndirizziEntiDetailsPageComponent } from './pages/modifica-indirizzi-enti-details-page/modifica-indirizzi-enti-details-page.component';

const routes: Routes = [
  {
    path: '',
    component: ModificaIndirizziEntiPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Indirizzi Enti'
        }
      ]
    }
  },

  {
    path: 'details/:id',
    component: ModificaIndirizziEntiDetailsPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Dettaglio'
        }
      ]
    }
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModificaIndirizziEntiRoutingModule {}
