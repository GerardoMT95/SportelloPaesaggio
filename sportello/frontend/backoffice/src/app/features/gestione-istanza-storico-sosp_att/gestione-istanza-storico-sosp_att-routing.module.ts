import { GestioneIstanzaStoricoSospAttPageComponent } from './pages/gestione-istanza-storico-sosp-att-page/gestione-istanza-storico-sosp-att-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const rountes: Routes =
[
    {
        path: '',
        component: GestioneIstanzaStoricoSospAttPageComponent
    }
]

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(rountes)],
    exports: [RouterModule]
})
export class GestioneIstanzaStoricoSospAttRoutingModule { }