import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GeneraStampaDomandaPageComponent, InterfacciaDiRiepilogoDelFascicoloPageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    data: { breadcrumb: 'Riepilogo Istanza' },
    component: InterfacciaDiRiepilogoDelFascicoloPageComponent,

  },
  {
    path: 'genera-stampa',
    component: GeneraStampaDomandaPageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Genera Stampa'
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
export class InterfacciaDiRiepilogoDelFascicoloRoutingModule { }
