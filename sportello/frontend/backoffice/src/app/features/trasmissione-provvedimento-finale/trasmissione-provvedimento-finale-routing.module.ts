import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// tslint:disable-next-line: max-line-length
import { TrasmissioneProvvedimentoFinalePageComponent } from './pages/trasmissione-provvedimento-finale-page/trasmissione-provvedimento-finale-page.component';

const routes: Routes = [
  {
    path: '',
    component: TrasmissioneProvvedimentoFinalePageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Template Comunicazioni'
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
export class TrasmissioneProvvedimentoFinaleRoutingModule {}
