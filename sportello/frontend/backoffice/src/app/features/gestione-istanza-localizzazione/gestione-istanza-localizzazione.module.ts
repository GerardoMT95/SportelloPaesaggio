import { NgModule } from '@angular/core';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaLocalizzazioneRoutingModule } from './gestione-istanza-localizzazione-routing.module';
import { LocalizzazioneService } from '../compilazione-istanza/services/localizzazione/localizzazione.service';

const modules = [
  SharedModule,
  GestioneIstanzaLocalizzazioneRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages
  ],
  imports: [
    ...modules
  ],
  providers: [LocalizzazioneService]
})
export class GestioneIstanzaLocalizzazioneModule { }
