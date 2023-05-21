
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import * as fromComponents from './components';

const modules = [
  CommonModule,
  SharedModule
];

@NgModule({
  declarations: [
    ...fromComponents.components
  ],
  imports: [
    ...modules
  ],
  exports: [
    ...fromComponents.components
  ]
})
export class CoreModule {

  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    // Import guard
    if (parentModule) {
      throw new Error(`${parentModule} has already been loaded. Import Core module in the AppModule only.`);
    }
  }
}
