import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GestioneProcedimentiPageComponent } from './gestione-procedimenti-page.component';

const routes: Routes = [
  {
    path: '',
    component: GestioneProcedimentiPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Gestione procedimenti'
        }
      ]
    }
  },

  {
    path: ':id',
    component: GestioneProcedimentiPageComponent,
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
export class GestioneProcedimentiRoutingModule {}
