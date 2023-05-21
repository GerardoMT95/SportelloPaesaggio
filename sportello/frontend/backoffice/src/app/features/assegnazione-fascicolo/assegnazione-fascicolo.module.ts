import * as fromComponents from './components';
import * as fromPages from './pages';
import * as fromPipes from "./pipes";

import { SharedModule } from 'src/app/shared/shared.module';
import { AssegnazioneFascicoloRoutingModule } from './assegnazione-fascicolo-routing.module';
import { NgModule } from '@angular/core';

const modules =
[
    SharedModule,
    AssegnazioneFascicoloRoutingModule
]

@NgModule({
    declarations: [...fromPages.pages, ...fromComponents.pages, ...fromPipes.pages],
    imports: [...modules], 
})
export class AssegnazioneFascicoloModule {}