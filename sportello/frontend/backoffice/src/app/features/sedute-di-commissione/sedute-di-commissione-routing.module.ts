import { SeduteDiCommissionePageComponent } from './pages/sedute-di-commissione-page/sedute-di-commissione-page.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes =
[
    {
        path: '',
        component: SeduteDiCommissionePageComponent
    }
]
@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SeduteDiCommissioneRoutingModule { }