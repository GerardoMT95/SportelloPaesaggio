import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SetupMessaggiComponent } from './components';
import { ConfigurazioneDiogeneComponent } from './components/configurazione-diogene/configurazione-diogene.component';
import { SuperAdminGuard } from './configurations/super-admin.guard';
import { GestioneDisclaimersPageComponent, SezioniCatastaliComponent } from './pages';
import { GestioneDichiarazionePageComponent } from './pages/gestione-dichiarazione-page/gestione-dichiarazione-page.component';
import { IndirizziEntiComponent } from './pages/indirizzi-enti/indirizzi-enti.component';
import { NuovaCommissioneLocalePageComponent } from './pages/nuova-commissione-locale-page/nuova-commissione-locale-page.component';

const routes: Routes = 
[
    {
        path: 'indirizzi-enti',
        data: { breadcrumb: 'Configura indirizzi' },
        canActivate: [SuperAdminGuard],
        component: IndirizziEntiComponent
    },
    {
        path: 'nuova-commissione-locale',
        data: { breadcrumb: 'Configura commissione locale' },
        canActivate: [SuperAdminGuard],
        component: NuovaCommissioneLocalePageComponent
    },
    {
        path: 'sezioni-catastali',
        data: { breadcrumb: 'Configura label sezioni catastali' },
        canActivate: [SuperAdminGuard],
        component: SezioniCatastaliComponent
    },
    {
        path: 'setup-messaggi-sportello',
        data: { breadcrumb: 'Setup messaggi sportello' },
        canActivate: [SuperAdminGuard],
        component: SetupMessaggiComponent
    },
    {
        path: 'configurazione-diogene',
        data: { breadcrumb: 'Configurazione diogene' },
        canActivate: [SuperAdminGuard],
        component: ConfigurazioneDiogeneComponent
    },
    {
        path: 'conf-procedimento',
        data: { breadcrumb: 'Configurazione Procedimenti' },
        canActivate: [SuperAdminGuard],
        loadChildren: () => import('../gestione-procedimenti-page/gestione-procedimenti.module').then(m => m.GestioneProcedimentiModule)
    },
    {
        path: 'dichiarazione/:id',
        data: { breadcrumb: 'Configurazione Sezione Dichiarazione' },
        canActivate: [SuperAdminGuard],
        component: GestioneDichiarazionePageComponent
    },
    {
        path: 'disclaimer/:id',
        data: { breadcrumb: 'Configurazione Sezione Disclaimers' },
        canActivate: [SuperAdminGuard],
        component: GestioneDisclaimersPageComponent
    },
    { path: 'conf-procedimento', redirectTo: 'conf-procedimento/'},
   
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    declarations: []
})
export class AdminAppRoutingModule{}