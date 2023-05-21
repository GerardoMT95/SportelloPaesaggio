import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AssegnaFascicoloConfigPageComponent } from './pages/assegna-fascicolo-config-page/assegna-fascicolo-config-page.component';

const routes: Routes = [{ path: '', component: AssegnaFascicoloConfigPageComponent}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ConfAssegnazioneFascicoloRoutingModule {}