import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModificaTestiComunicazioniPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: ModificaTestiComunicazioniPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Template Comunicazioni'
        }
      ]
    }
  },

  /*{
    path: 'details/:id',
    component: ModificaTestiComunicazioniDetailsPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Dettaglio'
        }
      ]
    }
  }*/
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModificaTestiComunicazioniRoutingModule {}
