import { NgModule } from '@angular/core';
import { AssegnazioneFascicoloPageComponent } from './pages/assegnazione-fascicolo-page/assegnazione-fascicolo-page.component';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes =
[
    {
        path: '',
        component: AssegnazioneFascicoloPageComponent,
        data:
        {
            breadcrumbs: [{label: 'Assegnazione fascicolo'}]
        }
    }
];

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AssegnazioneFascicoloRoutingModule
{
    
}