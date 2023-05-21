
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { GestioneProcedimentiRoutingModule } from './gestione-procedimenti-routing.module';
import * as fromPages from './';
import * as fromComponents from './components';

const modules = [
  SharedModule,
  GestioneProcedimentiRoutingModule
];

@NgModule({
  declarations: [ ...fromPages.pages, ...fromComponents.pages],
  imports: [
    ...modules
  ]
})
export class GestioneProcedimentiModule { }

