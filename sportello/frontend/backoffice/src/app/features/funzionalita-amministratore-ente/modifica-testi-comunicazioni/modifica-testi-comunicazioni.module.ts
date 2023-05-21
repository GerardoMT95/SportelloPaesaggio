import * as fromComponents from './components';
import * as fromPages from './pages';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ModificaTestiComunicazioniRoutingModule } from './modifica-testi-comunicazioni-routing.module';

const modules = [
  SharedModule,
  ModificaTestiComunicazioniRoutingModule
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
export class ModificaTestiComunicazioniModule { }

