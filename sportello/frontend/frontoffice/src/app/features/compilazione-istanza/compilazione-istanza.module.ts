import * as fromComponents from './components';
import * as fromPages from './pages';
import * as fromServices from './services';

import { CompilazioneIstanzaRoutingModule } from './compilazione-istanza-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { PrivacyComponent } from './components/privacy/privacy.component';
import { DichiarazioniComponent } from './components/dichiarazioni/dichiarazioni.component';

const modules = [SharedModule, CompilazioneIstanzaRoutingModule];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,
    PrivacyComponent,
    DichiarazioniComponent,
  ],
  imports: [
    ...modules
  ],
  providers: [...fromServices.pages]
})
export class CompilazioneIstanzaModule {}
