import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './configurations/admin.guard';
import { GestioneConferenzaServiziPageComponent } from './gestione-conferenza-servizi-page/gestione-conferenza-servizi-page.component';

const routes: Routes = 
[
    {
        path: 'servizi-integrazione',
        data: { breadcrumb: 'Configura servizi integrazione' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./gestione-servizi-integrazione/servizi-integrazione.module').then(m => m.ServiziIntegrazioneModule)
    },
    {
        path: 'configura-assegnazioni',
        data: { breadcrumb: 'Configura assegnazioni' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./conf-assegnazione-fascicoli/conf-assegnazione-fascicolo.module').then(m => m.ConfAssegnazioneFascicoloModule)
    },
    {
        path: 'configura-rup',
        data: { breadcrumb: 'Configura rup' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./conf-censimento-rup/conf-censimento-rup.module').then(m => m.ConfCensimentoRupModule)
    },
    {
        path: 'modifica-testi-comunicazioni',
        data: { breadcrumb: 'Configura testi comunicazioni' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./modifica-testi-comunicazioni/modifica-testi-comunicazioni.module').then(m => m.ModificaTestiComunicazioniModule)
    },
    {
        path: 'modifica-testi-documentazione',
        data: { breadcrumb: 'Configura testi documentazioni' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./modifica-testi-documentazione/modifica-testi-documentazione.module').then(m => m.ModificaTestiDocumentazioneModule)
    },
    {
        path: 'conf-procedimento',
        data: { breadcrumb: 'Configurazione Procedimenti' },
        canActivate: [AdminGuard],
        loadChildren: () => import('../gestione-procedimenti-page/gestione-procedimenti.module').then(m => m.GestioneProcedimentiModule)
    },
    {
        path: 'tariffe',
        data: { breadcrumb: 'Configurazione Tariffe' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./configurazione-tariffe/configurazione-tariffe.module').then(m => m.ConfigurazioneTariffeModule)
    },
    {
        path: 'conferenza-servizi',
        data: { breadcrumb: 'Configurazione Conferenza dei Servizi' },
        canActivate: [AdminGuard],
        loadChildren: () => import('./gestione-conferenza-servizi-page/gestione-conferenza-servizi.module').then(m => m.GestioneConferenzaServiziModule)
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    declarations: []
})
export class AdminRoutingModule{}