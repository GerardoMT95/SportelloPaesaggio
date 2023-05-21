import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CompilazioneAllegatiRoutingModule } from './compilazione-allegati-routing.module';
import * as fromPages from './pages';
import * as formComponents from './components';


const modules = [SharedModule, CompilazioneAllegatiRoutingModule];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...formComponents.components
  ],
  imports: [
    ...modules
  ]
})
export class CompilazioneAllegatiModule { }
