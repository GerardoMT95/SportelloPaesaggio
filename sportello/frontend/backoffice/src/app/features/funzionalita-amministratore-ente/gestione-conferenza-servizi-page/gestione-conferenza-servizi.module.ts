import * as fromPages from './';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneConferenzaServiziRoutingModule } from './gestione-conferenza-servizi-routing.module';

const modules = [
  SharedModule,
  GestioneConferenzaServiziRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
  ],
  imports: [
    ...modules
  ]
})
export class GestioneConferenzaServiziModule { }

