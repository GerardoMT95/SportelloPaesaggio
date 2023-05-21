import * as fromComponents from './components';
import * as fromPages from './pages';

import { CreazioneNuovoFascicoloRoutingModule } from './creazione-nuovo-fascicolo-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';

const modules = [
  SharedModule,
  CreazioneNuovoFascicoloRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components
  ],
  imports: [
    ...modules
  ]
})

export class CreazioneNuovoFascicoloModule { }
