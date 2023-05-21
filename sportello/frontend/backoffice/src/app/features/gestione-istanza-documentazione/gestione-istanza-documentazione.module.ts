import { AggiungiUlterioreDocumentazioneComponent } from './components/aggiungi-ulteriore-documentazione/aggiungi-ulteriore-documentazione.component';
import { NgModule } from '@angular/core';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaDocumentazioneRoutingModule } from './gestione-istanza-documentazione-routing.module';
import { DocumentazioneAllegaComponent } from './components/documentazione-allega/documentazione-allega.component';


const modules = [
  SharedModule,
  GestioneIstanzaDocumentazioneRoutingModule
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
    DocumentazioneAllegaComponent,
    AggiungiUlterioreDocumentazioneComponent
  ]
})
export class GestioneIstanzaDocumentazioneModule { }
