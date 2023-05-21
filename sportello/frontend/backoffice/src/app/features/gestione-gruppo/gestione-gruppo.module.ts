import { GestioneGruppoRoutingModule } from './gestione-gruppo-routing.module';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import * as fromComponents from './components';
import * as fromPages from './pages';

const modules = 
[
    SharedModule,
    GestioneGruppoRoutingModule
]

@NgModule({
    declarations: [...fromComponents.pages, ...fromPages.pages],
    imports: [...modules]
})
export class GestioneGruppoModule { }