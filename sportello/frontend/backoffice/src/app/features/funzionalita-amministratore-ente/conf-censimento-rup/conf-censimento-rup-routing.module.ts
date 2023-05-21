import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CensimentoRupComponent } from './pages';

const routes: Routes = [{ path: '', component: CensimentoRupComponent}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ConfCensimentoRupRoutingModule {}