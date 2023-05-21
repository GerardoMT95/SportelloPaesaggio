import { DelegatoComponent } from './components/delegato/delegato.component';

import * as fromPages from './pages';
import * as fromServices from './services';

import { CompilazioneIstanzaRoutingModule } from './compilazione-istanza-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { PrivacyComponent } from './components/privacy/privacy.component';
import { DichiarazioniComponent } from './components/dichiarazioni/dichiarazioni.component';
import { AltroTitolareComponent, RichiedenteComponent, TecnicoIncaricatoComponent } from './components';
import { DocumentoRiconoscimentoAllegatoComponent } from './components/documento-riconoscimento-allegato/documento-riconoscimento-allegato.component';

const modules = [SharedModule, CompilazioneIstanzaRoutingModule];

@NgModule({
  declarations: [
    ...fromPages.pages,
    RichiedenteComponent,
    TecnicoIncaricatoComponent,
    AltroTitolareComponent,
    DocumentoRiconoscimentoAllegatoComponent,
    PrivacyComponent,
    DichiarazioniComponent,
    DelegatoComponent
  ],
  imports: [
    ...modules
  ],
  providers: [...fromServices.pages]
})
export class CompilazioneIstanzaModule {}
