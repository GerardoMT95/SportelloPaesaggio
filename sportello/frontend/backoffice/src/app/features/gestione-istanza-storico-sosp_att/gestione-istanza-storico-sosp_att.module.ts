import { GestioneStatoPraticaComponent } from './../../shared/components/gestione-stato-pratica/gestione-stato-pratica.component';
import * as fromComponents from './components';
import * as fromPages from './pages';
import { GestioneIstanzaStoricoSospAttRoutingModule } from './gestione-istanza-storico-sosp_att-routing.module';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';

const modules =
[
    SharedModule,
    GestioneIstanzaStoricoSospAttRoutingModule
];

const entryComponents = 
[
    GestioneStatoPraticaComponent
]

@NgModule({
    declarations: [...fromComponents.pages, ...fromPages.pages],
    imports: [...modules]
})
export class GestioneIstanzaStoricoSospAttModule { }