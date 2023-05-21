import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthGuard, CanActivateGestioneIstanza } from './configuration/routes.guard';
import { GestioneIstanzaRoutingModule } from './gestione-istanza-routing.module';
import * as fromDirectives from './directives';
import * as fromComponents from './components';
import * as fromPages from './pages';
import * as fromServices from './services';


const modules = [
  SharedModule,
  GestioneIstanzaRoutingModule
];

@NgModule({
  declarations: [
    ...fromPages.pages,
    ...fromComponents.pages,
    ...fromDirectives.pages,
  ],
  imports: [
    ...modules
  ],
  providers: [...fromServices.pages, CanActivateGestioneIstanza,AuthGuard],
})
export class GestioneIstanzaModule { }
