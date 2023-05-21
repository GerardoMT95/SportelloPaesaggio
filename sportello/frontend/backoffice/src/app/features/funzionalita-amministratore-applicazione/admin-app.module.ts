import { SuperAdminGuard } from './configurations/super-admin.guard';
import { AdminAppRoutingModule } from './admin-app-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { NgModule } from '@angular/core';
import * as fromComponents from './components';
import * as fromPages from './pages';

const modules = [SharedModule, AdminAppRoutingModule]

@NgModule({
    imports: [...modules],
    declarations: [...fromPages.pages, ...fromComponents.pages],
    providers: [SuperAdminGuard]
})
export class AdminAppModule{}