import { NgModule } from '@angular/core';
import { VerbaleCommissioneLocaleRoutingModule } from './gestione-istanza-verbale-com-loc.routing.module';
import { SharedModule } from './../../shared/shared.module';

import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = 
[
    SharedModule,
    VerbaleCommissioneLocaleRoutingModule
]

@NgModule({
    declarations: [...fromPages.pages, ...fromComponents.pages],
    imports: [...modules]
})
export class VerbaleCommissioneLocaleModule { }