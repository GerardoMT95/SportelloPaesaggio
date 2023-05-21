import { NgModule } from '@angular/core';
import { GestioneIstanzaProtocollazioneRoutingModule } from './gestione-istanza-protocollazione-routing.module';
import { SharedModule } from './../../shared/shared.module';

import * as fromPages from './pages';
import * as fromPipes from './pipes';
import * as fromComponents from './components';

const modules =
[
    SharedModule,
    GestioneIstanzaProtocollazioneRoutingModule
]

@NgModule({
    declarations: [... fromPages.pages, ...fromPipes.pages, ...fromComponents.pages],
    imports: [...modules]
})
export class GestioneIstanzaProtocollazioneModule { }