import * as fromPages from './pages';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CdsRoutingModule } from './cds-routing.module';

const modules = [SharedModule, CdsRoutingModule];

@NgModule({
  declarations: [...fromPages.pages],
  imports: [...modules],
  // entryComponents: [InviaComunicazioneComponent, 
  //   //DettaglioComunicazioneComponent, 
  //   DettaglioComComponent, NuovaComunicazioneComponent]
})
export class CdsModule {}
