import { ServiziIntegrazionePageComponent } from './pages/servizi-integrazione-page/servizi-integrazione-page.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = 
[
    {
        path: '', 
        component: ServiziIntegrazionePageComponent,
        data: { breadcrumbs: [{ label: 'Configurazione servizi di integrazione'}] }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    declarations: []
})
export class ServiziIntegrazioneRoutingModule {}