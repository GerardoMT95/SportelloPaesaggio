import { NgModule } from '@angular/core';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaParereRoutingModule } from './gestione-istanza-parere-routing.module';
import { ComunicazioneModalComponent } from './components/comunicazione-modal/comunicazione-modal.component';


const modules = [
  SharedModule,
  GestioneIstanzaParereRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components
  ],
  imports: [
    ...modules
  ],
  entryComponents: [
    ComunicazioneModalComponent
  ]
})
export class GestioneIstanzaParereModule { }
