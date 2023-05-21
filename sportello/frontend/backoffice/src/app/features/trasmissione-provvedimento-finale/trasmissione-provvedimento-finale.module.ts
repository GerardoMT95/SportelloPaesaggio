import * as fromComponents from './components';
import * as fromPages from './pages';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { TrasmissioneProvvedimentoFinaleRoutingModule } from './trasmissione-provvedimento-finale-routing.module';
import { TrasmissioneProvvedimentoFinaleDetailsComponent } from './components/trasmissione-provvedimento-finale-details/trasmissione-provvedimento-finale-details.component';
import { DestinatariTableComponent } from './components/destinatari-table/destinatari-table.component';
import { NuovoUlterioriDestinatarioComponent } from './components/nuovo-ulteriori-destinatario/nuovo-ulteriori-destinatario.component';
import { RubricaEnteComponent } from './components/rubrica-ente/rubrica-ente.component';
import { NuovoDestinatarioComponent } from './components/nuovo-destinatario/nuovo-destinatario.component';

const modules = [
  SharedModule,
  TrasmissioneProvvedimentoFinaleRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,
    TrasmissioneProvvedimentoFinaleDetailsComponent,
    DestinatariTableComponent,
    NuovoUlterioriDestinatarioComponent,
    RubricaEnteComponent,
    NuovoDestinatarioComponent
  ],
  imports: [
    ...modules
  ]
})
export class TrasmissioneProvvedimentoFinaleModule { }

