import { NuovaComunicazioneComponent } from '../../shared/components/nuova-comunicazione/nuova-comunicazione.component';
import { DettaglioComComponent } from './components/dettaglio-com/dettaglio-com.component';
import * as fromComponents from './components';
import * as fromPages from './pages';

//import { DettaglioComunicazioneComponent } from './components/dettaglio-comunicazione/dettaglio-comunicazione.component';
import { GestioneIstanzaComunicazioniRoutingModule } from './gestione-istanza-comunicazioni-routing.module';
import { InviaComunicazioneComponent } from './components/invia-comunicazione/invia-comunicazione.component';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';

const modules = [SharedModule, GestioneIstanzaComunicazioniRoutingModule];

@NgModule({
  declarations: [...fromPages.pages, ...fromComponents.components],
  imports: [...modules],
  entryComponents: [InviaComunicazioneComponent, 
    //DettaglioComunicazioneComponent, 
    DettaglioComComponent, NuovaComunicazioneComponent]
})
export class GestioneIstanzaComunicazioniModule {}
