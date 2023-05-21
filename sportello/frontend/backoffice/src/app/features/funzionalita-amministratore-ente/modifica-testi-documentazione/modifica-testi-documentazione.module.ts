import * as fromComponents from './components';
import * as fromPages from './pages';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ModificaTestiDocumentazioneRoutingModule } from './modifica-testi-documentazione-routing.module';

const modules = [
  SharedModule,
  ModificaTestiDocumentazioneRoutingModule
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
export class ModificaTestiDocumentazioneModule { }

