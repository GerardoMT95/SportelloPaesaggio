import { RouterModule, Routes, PreloadAllModules } from '@angular/router';

import { NgModule } from '@angular/core';
import { CanActiveteGuard } from './shared/components/auth/CanActivateGuard.guard';
import { ErrorComponent, LoginComponent, ManualeComponent, VerificaImprontaHashComponent } from './shared/components';
import { IsLoggedGuard } from './shared/components/auth/IsLoggedGuard.guard';
import { RichiestaAbilitazioneComponent } from './shared/components/richiesta-abilitazione/richiesta-abilitazione.component';

export const paths = {
  list() { return `gestione-istanze/consultazione-istanze`; },
  verificaHash() { return `verifica-hash`; },
  create() { return `gestione-istanze/creazione-nuovo-fascicolo`; },
  details(id?: number | string) { return `gestione-istanze/${id ? id : ':id'}/fascicolo`; },
  generateStampa(id?: number | string) { return `gestione-istanze/${id ? id : ':id'}/fascicolo/genera-stampa`; },
  instance(id?: number | string) { return `gestione-istanze/${id ? id : ':id'}/fascicolo/istanza`; },
  dataSheet(id?: number | string) { return `gestione-istanze/${id ? id : ':id'}/fascicolo/scheda-tecnica`; },
  attachment(id?: number | string) { return `gestione-istanze/${id ? id : ':id'}/fascicolo/allegati`; }
};

const routes: Routes = [
  {path: 'error', component: ErrorComponent },
  {path: 'login', component: LoginComponent },
  {path: 'richiesta-abilitazione', component: RichiestaAbilitazioneComponent,   canActivate: [IsLoggedGuard],data:{breadcrumb:'Richiesta abilitazione'}},
  {path: 'dashboard', redirectTo: 'gestione-istanze/consultazione-istanze', pathMatch: 'full'},
  {path: '', redirectTo: 'gestione-istanze/consultazione-istanze', pathMatch: 'full'},
  {
    path: 'gestione-istanze',
    redirectTo: 'gestione-istanze/consultazione-istanze',
    canActivate:[CanActiveteGuard]
  },
  { path: 'download-manuale', component: ManualeComponent, canActivate: [IsLoggedGuard], data: { breadcrumb: 'Manuale' } },
  { path: 'verifica-hash', component: VerificaImprontaHashComponent, canActivate: [IsLoggedGuard], data: { breadcrumb: 'Verifica impronta file' } },
  { path: "cambio-ownership", loadChildren: "./features/cambio-ownership/cambio-ownership.module#CambioOwnershipModule" },
  {
    path: 'gestione-istanze/consultazione-istanze',
    canActivate: [CanActiveteGuard],
    data: {breadcrumb: 'Gestione Istanze'},
    loadChildren: () => import('./features/consultazione-istanze/consultazione-istanze.module')
      .then(m => m.ConsultazioneIstanzeModule)
    
  },
  {
    path: 'gestione-istanze/:id/fascicolo',
    data: {breadcrumb: 'Gestione Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/interfaccia-di-riepilogo-del-fascicolo/interfaccia-di-riepilogo-del-fascicolo.module')
      .then(m => m.InterfacciaDiRiepilogoDelFascicoloModule)
  },
  {
    path: 'gestione-istanze/creazione-nuovo-fascicolo',
    data: {breadcrumb: 'Nuova Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/creazione-nuovo-fascicolo/creazione-nuovo-fascicolo.module')
      .then(m => m.CreazioneNuovoFascicoloModule)
  },
  {
    path: 'gestione-istanze/:id/fascicolo/istanza',
    data: {breadcrumb: 'Gestione Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/compilazione-istanza/compilazione-istanza.module')
      .then(m => m.CompilazioneIstanzaModule)
  },
  {
    path: 'gestione-istanze/:id/fascicolo/scheda-tecnica',
    data: {breadcrumb: 'Gestione Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/compilazione-scheda-tecnica/compilazione-scheda-tecnica.module')
      .then(m => m.CompilazioneSchedaTecnicaModule)
  },
  {
    path: 'gestione-istanze/:id/fascicolo/allegati',
    data: {breadcrumb: 'Gestione Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/compilazione-allegati/compilazione-allegati.module')
      .then(m => m.CompilazioneAllegatiModule)
  },
  {
    path: 'integrazione-documentale/:id/:idIntegrazione',
    data: {breadcrumb: 'Gestione Istanza'},
    canActivate: [CanActiveteGuard],
    loadChildren: () => import('./features/dettaglio-integrazione-documentale/dettaglio-integrazione-istanza.module')
      .then(m => m.DettaglioIntegrazioneDocumentaleModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
