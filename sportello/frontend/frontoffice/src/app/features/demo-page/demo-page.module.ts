import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as fromComponents from './components';
import * as fromPages from './pages';
import { DemoPageRoutingModule } from './demo-page-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';


const modules: any[] = [
  CommonModule,
  SharedModule,
  DemoPageRoutingModule
]

@NgModule({
  declarations: [
    ...fromComponents.components,
    ...fromPages.pages,

  ],
  imports: [
    ...modules
  ]
})
export class DemoPageModule { }
