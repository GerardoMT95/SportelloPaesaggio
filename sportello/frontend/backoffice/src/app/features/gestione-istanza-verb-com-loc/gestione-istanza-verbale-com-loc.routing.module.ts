import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaVerbComLocPageComponent } from './pages';
import { NgModule } from '@angular/core';

const routes: Routes =
[
    {
        path: '',
        component: GestioneIstanzaVerbComLocPageComponent
    }
]

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class VerbaleCommissioneLocaleRoutingModule { }