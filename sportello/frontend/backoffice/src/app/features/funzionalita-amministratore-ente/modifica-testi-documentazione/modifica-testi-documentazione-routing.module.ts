import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ModificaTestiDocumentazionePageComponent} from './pages';

const routes: Routes = [
  {
    path: '',
    component: ModificaTestiDocumentazionePageComponent,
    data: {
      breadcrumbs: [
        {
          label: 'Template Documentazione'
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
export class ModificaTestiDocumentazioneRoutingModule {}
