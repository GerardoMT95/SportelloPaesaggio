import { CompilazioneIstanzaModule } from './../compilazione-istanza/compilazione-istanza.module';
import { CambioOwnershipRoutingModule } from './cambio-ownership-routing.module';
import * as fromComponents from './components';
import * as fromPages from './pages';

import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';


const modules = [SharedModule, CambioOwnershipRoutingModule, CompilazioneIstanzaModule];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,
  ],
  imports: [
    ...modules
  ]
})
export class CambioOwnershipModule {}
