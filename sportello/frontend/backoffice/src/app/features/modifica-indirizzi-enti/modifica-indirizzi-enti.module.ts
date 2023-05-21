import * as fromComponents from './components';
import * as fromPages from './pages';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ModificaIndirizziEntiRoutingModule } from './modifica-indirizzi-enti-routing.module';

const modules = [
  SharedModule,
  ModificaIndirizziEntiRoutingModule
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
export class ModificaIndirizziEntiModule { }
