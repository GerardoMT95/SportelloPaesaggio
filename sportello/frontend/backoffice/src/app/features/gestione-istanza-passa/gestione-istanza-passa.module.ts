import { NgModule } from '@angular/core';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaPassaRoutingModule } from './gestione-istanza-passa-routing.module';

const modules = [
  SharedModule,
  GestioneIstanzaPassaRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.components,
  ],
  imports: [
    ...modules
  ]
})
export class GestioneIstanzaPassaModule { }
