import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaDocumentazionePageComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaDocumentazionePageComponent,
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaDocumentazioneRoutingModule {}
