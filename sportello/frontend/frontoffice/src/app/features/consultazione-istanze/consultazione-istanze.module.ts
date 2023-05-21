import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ConsultazioneIstanzeRoutingModule } from './consultazione-istanze-routing.module';
import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = [
  SharedModule,
  ConsultazioneIstanzeRoutingModule
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
export class ConsultazioneIstanzeModule { }
