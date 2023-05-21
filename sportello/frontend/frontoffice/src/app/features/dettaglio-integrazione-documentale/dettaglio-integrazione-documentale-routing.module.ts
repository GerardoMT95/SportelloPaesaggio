import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DettaglioIntegrazionePageComponent } from './pages/dettaglio-integrazione-page/dettaglio-integrazione-page.component';

const routes: Routes = [ { path: '', component: DettaglioIntegrazionePageComponent ,data: { breadcrumb: 'Dettaglio integrazione documentale' }  }  ]

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DettaglioIntegrazioneDocumentaleRoutingModule { }