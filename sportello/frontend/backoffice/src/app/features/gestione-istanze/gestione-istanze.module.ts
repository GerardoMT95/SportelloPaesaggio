import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzeRoutingModule } from './gestione-istanze-routing.module';
import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = [
  SharedModule,
  GestioneIstanzeRoutingModule
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
export class GestioneIstanzeModule { }
