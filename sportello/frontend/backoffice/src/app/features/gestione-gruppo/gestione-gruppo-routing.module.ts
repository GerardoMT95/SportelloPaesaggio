import { RouterModule, Routes } from '@angular/router';
import { GestioneGruppoComponent } from './pages/gestione-gruppo-page/gestione-gruppo.component';
import { NgModule } from '@angular/core';

const routes: Routes = [{path: '', component: GestioneGruppoComponent}];

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class GestioneGruppoRoutingModule { }