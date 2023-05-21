import { NgModule } from '@angular/core';
import * as fromPages from './pages';
import * as fromComponents from './components';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneIstanzaPresentataRoutingModule } from './gestione-istanza-presentata-routing.module';

const modules = [
  SharedModule,
  GestioneIstanzaPresentataRoutingModule
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
export class GestioneIstanzaPresentataModule { }
