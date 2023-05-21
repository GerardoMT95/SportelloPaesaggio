import { AdminGuard } from './configurations/admin.guard';
import { AdminRoutingModule } from './admin-routing.module';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';

const modules = [SharedModule, AdminRoutingModule];

@NgModule({
    imports: [...modules],
    providers: [AdminGuard],
    declarations: []
})
export class AdminModule{}