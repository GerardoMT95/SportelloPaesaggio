import { NgModule } from '@angular/core';
import { SharedModule } from './../../../shared/shared.module';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { ConfCensimentoRupRoutingModule } from './conf-censimento-rup-routing.module';

const modules = [SharedModule, ConfCensimentoRupRoutingModule];

@NgModule({
    imports: [...modules],
    declarations: [...fromPages.pages, ...fromComponents.pages]
})
export class ConfCensimentoRupModule {}