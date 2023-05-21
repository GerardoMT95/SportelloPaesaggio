import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { DemoShellComponent } from "./pages";
import { DemoTableComponent } from "./pages/demo-table/demo-table.component";


const routes: Routes = [
 {path:'', component:DemoShellComponent, data: {breadcrumb: null}, children:[
    {path:'', redirectTo:'info', pathMatch:'full'},
    {path:'info', component:DemoTableComponent}
 ]},

];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DemoPageRoutingModule {}
