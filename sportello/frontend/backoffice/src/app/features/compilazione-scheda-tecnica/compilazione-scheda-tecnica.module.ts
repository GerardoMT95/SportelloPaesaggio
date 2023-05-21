import * as fromComponents from './components';
import * as fromPages from './pages';

import { CompilazioneSchedaTecnicaRoutingModule } from './compilazione-scheda-tecnica-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';

const modules = [SharedModule, CompilazioneSchedaTecnicaRoutingModule];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,

  ],
  imports: [
    ...modules
  ]
})
export class CompilazioneSchedaTecnicaModule {}
