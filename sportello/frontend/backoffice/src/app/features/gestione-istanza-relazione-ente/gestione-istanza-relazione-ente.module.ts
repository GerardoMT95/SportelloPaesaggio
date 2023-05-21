import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaRelazioneEnteRoutingModule } from './gestione-istanza-relazione-ente-routing.module';

import * as fromComponents from './components';
import * as fromPages from './pages';


const modules = [
  SharedModule,
  GestioneIstanzaRelazioneEnteRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,
  ],
  imports: [ ...modules ],
})
export class GestioneIstanzaRelazioneEnteModule { }
