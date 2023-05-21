import * as fromPages from './';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ConfigurazioneTariffeRoutingModule } from './configurazione-tariffe-routing.module';

const modules = [
  SharedModule,
  ConfigurazioneTariffeRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
  ],
  imports: [
    ...modules
  ]
})
export class ConfigurazioneTariffeModule { }

