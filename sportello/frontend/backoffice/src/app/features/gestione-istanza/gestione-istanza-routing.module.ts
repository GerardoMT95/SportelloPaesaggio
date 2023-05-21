import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GestioneIstanzaPageComponent } from './pages';
import { AuthGuard, CanActivateGestioneIstanza } from './configuration/routes.guard';

const routes: Routes = [
  {
    path: '',
    component: GestioneIstanzaPageComponent,
    children: [
      {
        path: '',
        pathMatch: 'prefix',
        redirectTo: 'istanza-presentata',
        canActivate: [AuthGuard],
      },
      {
        path: 'istanza-protocollazione',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-protocollazione/gestione-istanza-protocollazione.module')//src\app\features\gestione-istanza-protocollazione\gestione-istanza-protocollazione-routing.module.ts
          .then(m => m.GestioneIstanzaProtocollazioneModule)
      },
      {
        path: 'istanza-presentata',
        loadChildren: () => import('../gestione-istanza-presentata/gestione-istanza-presentata.module')
          .then(m => m.GestioneIstanzaPresentataModule),
          canActivate: [AuthGuard],
      },
      {
        path: 'istanza-localizzazione',
        loadChildren: () => import('../gestione-istanza-localizzazione/gestione-istanza-localizzazione.module')
          .then(m => m.GestioneIstanzaLocalizzazioneModule)
      },
      {
        path: 'istanza-storico-stato',
        loadChildren: () => import('../gestione-istanza-storico-sosp_att/gestione-istanza-storico-sosp_att.module')
          .then(m => m.GestioneIstanzaStoricoSospAttModule)
      },
      {
        path: 'istanza-verbale',
        loadChildren: () => import('../gestione-istanza-verb-com-loc/gestione-istanza-verb-com-loc.module')
          .then(m => m.VerbaleCommissioneLocaleModule)
      },
      {
        path: 'istanza-relazione-ente',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-relazione-ente/gestione-istanza-relazione-ente.module')
          .then(m => m.GestioneIstanzaRelazioneEnteModule)
      },
      {
        path: 'istanza-parere',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-parere/gestione-istanza-parere.module')
          .then(m => m.GestioneIstanzaParereModule)
      },
      {
        path: 'istanza-comunicazioni',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-comunicazioni/gestione-istanza-comunicazioni.module')
          .then(m => m.GestioneIstanzaComunicazioniModule)
      },
      {
        path: 'istanza-conferenza',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../cds/cds.module')
          .then(m => m.CdsModule)
      },
      {
        path: 'istanza-documentazione',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-documentazione/gestione-istanza-documentazione.module')
          .then(m => m.GestioneIstanzaDocumentazioneModule)
      },
      {
        path: 'istanza-passa',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../gestione-istanza-passa/gestione-istanza-passa.module')
          .then(m => m.GestioneIstanzaPassaModule)
      },
      {
        path: 'trasmissione-provvedimento-finale',
        canActivate: [CanActivateGestioneIstanza],
        loadChildren: () => import('../trasmissione-provvedimento-finale/trasmissione-provvedimento-finale.module')
          .then(m => m.TrasmissioneProvvedimentoFinaleModule)
      },
      {
        path: 'fascicolo/istanza',
        data: { breadcrumb: 'Istanza' },
        loadChildren: () =>
          import('../compilazione-istanza/compilazione-istanza.module').then(
            (m) => m.CompilazioneIstanzaModule
          )
      },
      {
        path: 'fascicolo/scheda-tecnica',
        data: { breadcrumb: 'Scheda tecnica' },
        loadChildren: () =>
          import('../compilazione-scheda-tecnica/compilazione-scheda-tecnica.module').then(
            (m) => m.CompilazioneSchedaTecnicaModule
          )
      },
      {
        path: 'fascicolo/allegati',
        data: { breadcrumb: 'Allegati' },
        loadChildren: () =>
          import('../compilazione-allegati/compilazione-allegati.module').then(
            (m) => m.CompilazioneAllegatiModule
          )
      },
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestioneIstanzaRoutingModule {}
