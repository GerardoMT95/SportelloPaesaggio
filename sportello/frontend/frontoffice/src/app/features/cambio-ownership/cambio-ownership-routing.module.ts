import { CambioOwnershipLavorazioneComponent } from './pages/cambio-ownership-lavorazione/cambio-ownership-lavorazione.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: 'lavorazione', component: CambioOwnershipLavorazioneComponent, data: { breadcrumb: 'Subentro' } 
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CambioOwnershipRoutingModule { }
