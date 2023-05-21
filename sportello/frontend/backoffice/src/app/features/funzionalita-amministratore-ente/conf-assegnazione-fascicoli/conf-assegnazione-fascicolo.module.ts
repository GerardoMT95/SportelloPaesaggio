import { NgModule } from '@angular/core';
import { SharedModule } from './../../../shared/shared.module';
import { ConfAssegnazioneFascicoloRoutingModule } from './conf-assegnazione-fascicolo-routing.module';
import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = [SharedModule, ConfAssegnazioneFascicoloRoutingModule];

@NgModule({
    imports: [...modules],
    declarations: [...fromPages.pages, ...fromComponents.pages]
})
export class ConfAssegnazioneFascicoloModule {}