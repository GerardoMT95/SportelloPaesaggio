import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as fromComponents from './components';
import { AdminRoutingModule } from './admin-routing-module';
import { SharedModule } from 'src/shared/shared.module';
import { AdminAttivaModificaComponent } from './components/admin-attiva-modifica/admin-attiva-modifica.component';

const modules: any[] = [
  CommonModule,
  SharedModule,
  AdminRoutingModule
];

@NgModule({
  declarations: [
    ...fromComponents.components,
    AdminAttivaModificaComponent,
  ],
  imports: [
    ...modules
  ]
})
export class AdminModule { }
