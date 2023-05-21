import { ServiziIntegrazioneRoutingModule } from './servizi-integrazione-routing.module';
import { SharedModule } from './../../../shared/shared.module';
import { NgModule } from '@angular/core';

import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = [SharedModule, ServiziIntegrazioneRoutingModule];

@NgModule({
    imports: [...modules],
    declarations: [...fromComponents.pages, ...fromPages.pages]
})
export class ServiziIntegrazioneModule{}