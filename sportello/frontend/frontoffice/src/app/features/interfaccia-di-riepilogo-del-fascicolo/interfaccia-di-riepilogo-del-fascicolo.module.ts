import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { InterfacciaDiRiepilogoDelFascicoloRoutingModule } from './interfaccia-di-riepilogo-del-fascicolo-routing.module';
import * as fromPages from './pages';
import * as fromComponents from './components';

const modules = [
  SharedModule,
  InterfacciaDiRiepilogoDelFascicoloRoutingModule
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
export class InterfacciaDiRiepilogoDelFascicoloModule { }
