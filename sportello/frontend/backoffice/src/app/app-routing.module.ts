import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { ErrorComponent, HandleMailPecPageComponent, LoginComponent, ManualeComponent, RichiestaAbilitazioneComponent, VerificaImprontaHashComponent } from './shared/components';
import { AccettaPrivacyComponent } from './shared/components/accetta-privacy/accetta-privacy.component';


export const paths = {
    verificaHash() { return `verifica-hash`; },
    // Applicant role routes
    /* list()
    {
        return `consultazione-istanze`;
    }, */
    /* create()
    {
        return `creazione-nuovo-fascicolo`;
    }, */
    assign(code?: string): string
    {
        return `assegna-fascicolo/${code ? code : ''}`;
    },
    /* details(id?: number | string)
    {
        return `consultazione-istanze/${id ? id : ':id'}`;
    },
    generateStampa(id?: number | string)
    {
        return `consultazione-istanze/${id ? id : ':id'}/genera-stampa`;
    },
    instance(id?: number | string)
    {
        return `consultazione-istanze/${id ? id : ':id'}/fascicolo/istanza`;
    },
    dataSheet(id?: number | string)
    {
        return `consultazione-istanze/${id ? id : ':id'}/fascicolo/scheda-tecnica`;
    },
    attachment(id?: number | string)
    {
        return `consultazione-istanze/${id ? id : ':id'}/fascicolo/allegati`;
    }, */
    // Management role routes
    listManagement()
    {
        return `gestione-istanze`;
    },
    detailsManagement(id?: number | string)
    {
        return `gestione-istanze/${id ? id : ':id'}`;
    },
    instanceManagement(id?: number | string)
    {
        return `gestione-istanze/${id ? id : ':id'}/fascicolo/istanza`;
    },
    dataSheetManagement(id?: number | string)
    {
        return `gestione-istanze/${id ? id : ':id'}/fascicolo/scheda-tecnica`;
    },
    attachmentManagement(id?: number | string)
    {
        return `gestione-istanze/${id ? id : ':id'}/fascicolo/allegati`;
    },
    commissioneLocale() { return `funzioni-di-amministrazione/sedute-di-commissione`; },
    // Admin role routes
    entityAddressesList()
    {
        return `funzioni-di-amministrazione/modifica-indirizzi-enti`;
    },
    entityAddressDetails(id?: number | string)
    {
        return `funzioni-di-amministrazione/modifica-indirizzi-enti/details/${id ? id : ':id'}`;
    },
    serviziIntegrazione()
    {
        return `admin/servizi-integrazione`;
    },
    configuraAssegnamento()
    {
        return `admin/configura-assegnazioni`;
    },
    configuraRup()
    {
        return `admin/configura-rup`;
    },
    comunicationsTemplateList()
    {
        return `admin/modifica-testi-comunicazioni`;
    },
    modificaTemplateDoc()
    {
        return `admin/modifica-testi-documentazione`;
    },
    configurazioneProcedimentiEnte()
    {
        return `admin/conf-procedimento`;
    },
    configurazioneProcedimentoEnteDetail(id?: number | string)
    {
        return `admin/conf-procedimento/${id ? id : ':id'}`;
    },
    //ADMIN APPLICAZIONE
    modificaIndirizziEnte()
    {
        return `admin-app/indirizzi-enti`;
    },
    nuovaCommissioneLocale()
    {
        return `admin-app/nuova-commissione-locale`;
    },
    configuraLabelSezioniCatastali()
    {
        return `admin-app/sezioni-catastali`;
    },
    setupMessaggiSsortello()
    {
        return `admin-app/setup-messaggi-sportello`;
    },
    configurazioneDiogene()
    {
        return `admin-app/configurazione-diogene`;
    },
    configurazioneProcedimenti()
    {
        return `admin-app/conf-procedimento`;
    },
    configurazioneProcedimentoDetail(id?: number | string)
    {
        return `admin-app/conf-procedimento/${id ? id : ':id'}`;
    },
    reports()
    {
        return `report/`;
    },
    richiestaAbilitazione()
    {
        return `richiesta-abilitazione`;
    },
    manuale()
    {
        return `download-manuale`;
    },
    creaCommissioneLocale()
    {
        return `nuova-commissione-locale`;
    }
};
// gestione-istanze
const routes: Routes =
    [
        // Applicant role routes
        {
            path: '',
            pathMatch: 'full',
            redirectTo: 'login'
        },
        { path: 'error', component: ErrorComponent },
        { path: 'login', component: LoginComponent },
        { path: 'privacy/accetta', component: AccettaPrivacyComponent,  data: { breadcrumb: 'Accetta privacy' }  },
        { path: 'richiesta-abilitazione', data: {breadcrumb: 'Richiesta abilitazione'}, component: RichiestaAbilitazioneComponent },
        { path: 'verifica-hash', data: {breadcrumb: 'Verifica impronta file'}, component: VerificaImprontaHashComponent },
        { path: 'download-manuale', data: {breadcrumb: 'Manuale'}, component: ManualeComponent },
        { path: 'cambia-mail', data: {breadcrumb: 'Cambia Mail'}, component: HandleMailPecPageComponent },
        {
            path: 'gestione-gruppo',
            data: { breadcrumb: 'Gestione gruppi' },
            loadChildren: () => import('./features/gestione-gruppo/gestione-gruppo.module').then(
                (m) => m.GestioneGruppoModule)
        },
        {
            path: 'assegna-fascicolo',
            redirectTo: 'assegna-fascicolo/'
        },
        {
            path: 'assegna-fascicolo/:code',
            data: { breadcrumb: 'Assegna pratica' },
            loadChildren: () =>
                import('./features/assegnazione-fascicolo/assegnazione-fascicolo.module').then((m) =>
                    m.AssegnazioneFascicoloModule)
        },
        {
            path: 'funzioni-di-amministrazione/sedute-di-commissione',
            data: { breadcrumb: 'Sedute di commissione' },
            loadChildren: () => import('./features/sedute-di-commissione/sedute-di-commissione.module')
                                    .then(m => m.SeduteDiCommissioneModule)
        },
        {
            path: 'funzioni-di-amministrazione/modifica-indirizzi-enti',
            data: { breadcrumb: 'Indirizzi enti' },
            loadChildren: () =>
                import('./features/modifica-indirizzi-enti/modifica-indirizzi-enti.module').then(
                    (m) => m.ModificaIndirizziEntiModule
                )
        },
        // Management role routes
        {
            path: 'gestione-istanze',
            loadChildren: () =>
                import('./features/gestione-istanze/gestione-istanze.module').then(
                    (m) => m.GestioneIstanzeModule
                )
        },
        {
            path: 'gestione-istanze/:id',
            data: { breadcrumb: 'Dettaglio' },
            loadChildren: () =>
                import('./features/gestione-istanza/gestione-istanza.module').then(
                    (m) => m.GestioneIstanzaModule
                ),
        },
        {
            path: 'admin',
            loadChildren: () => import('./features/funzionalita-amministratore-ente/admin.module').then(m => m.AdminModule)
        },
        {
            path: 'admin-app',
            loadChildren: () => import('./features/funzionalita-amministratore-applicazione/admin-app.module').then(m => m.AdminAppModule)
        },
        {   
            path: 'report', 
            data: null, 
            loadChildren:()=> import('./features/report/report.module').then(m=>m.ReportModule),
        }, 
    ];

@NgModule({
    imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
