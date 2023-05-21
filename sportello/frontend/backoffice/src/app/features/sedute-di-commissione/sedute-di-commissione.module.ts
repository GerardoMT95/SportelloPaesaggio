import * as fromPages from './pages';
import * as fromComponents from './components';

import { SeduteDiCommissioneRoutingModule } from './sedute-di-commissione-routing.module';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';

const modules = 
[
    SharedModule,
    SeduteDiCommissioneRoutingModule
];

@NgModule({
    declarations: [...fromPages.pages, ...fromComponents.pages],
    imports: [...modules]
})
export class SeduteDiCommissioneModule { }