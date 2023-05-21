import { DettaglioIntegrazioneDocumentaleRoutingModule } from './dettaglio-integrazione-documentale-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { NgModule } from '@angular/core';
import * as fromComponents from './components';
import * as fromPages from './pages';

const modules = [SharedModule, DettaglioIntegrazioneDocumentaleRoutingModule]

@NgModule({
    declarations: [...fromPages.pages, ...fromComponents.pages],
    imports: modules
})
export class DettaglioIntegrazioneDocumentaleModule { }