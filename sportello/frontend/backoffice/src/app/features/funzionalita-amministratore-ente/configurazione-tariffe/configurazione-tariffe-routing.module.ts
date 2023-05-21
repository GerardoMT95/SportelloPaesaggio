import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminConfigurazioneTariffeComponent } from './configurazione-tariffe.component';

const routes: Routes = [
  {
    path: ':id',
    component: AdminConfigurazioneTariffeComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Configurazione Tariffe'
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
export class ConfigurazioneTariffeRoutingModule {}
