import { HttpClient, HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, RouterModule, Routes } from '@angular/router';
// BOOTSTRAP FOR ANGULAR
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// FONT AWESOME FOR BOOTSTRAP
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
// import ngx-translate and the http loader
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { UserIdleModule } from 'angular-user-idle';
import { StorageServiceModule } from "angular-webstorage-service";
// DATATABLE
import { DataTableModule } from 'angular7-data-table';
import { AccettaPrivacyComponent } from 'src/shared/components/accetta-privacy/accetta-privacy.component';
import { AssegnaFascicoloConfigComponent } from 'src/shared/components/assegna-fascicolo-config/assegna-fascicolo-config.component';
import { RichiestaAbilitazioneComponent } from 'src/shared/components/richiesta-abilitazione/richiesta-abilitazione.component';
import { VerificaImprontaHashComponent } from 'src/shared/components/verifica-impronta-hash/verifica-impronta-hash.component';
import { AdminGuard } from 'src/shared/guards/admin.guard';
import { AuthGuard } from 'src/shared/guards/auth-guard.guard';
import { LoaderInterceptor } from '../shared/interceptor/loader.interceptor';
import { SharedModule } from '../shared/shared.module';
import { AuthInterceptor } from './../shared/auth/AuthInterceptor.interceptor';
import { AssegnaFascicoloComponent } from './../shared/components/assegna-fascicolo/assegna-fascicolo.component';
import { BreadcrumbComponent } from "./../shared/components/breadcrumb/breadcrumb.component";
// APP COMPONENT
import { AppComponent } from './app.component';
import { AUTH_SERVICE } from './components/auth/IAuthService';
import { Wso2Auth } from './components/auth/Wso2Auth';
import { CartografiaComponent } from './components/cartografia/cartografia.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DettaglioFascicoloComponent } from './components/dettaglio-fascicolo/dettaglio-fascicolo.component';
import { ErrorComponent } from './components/error/error.component';
import { FascicoloPublicComponent } from './components/fascicolo-public/fascicolo-public.component';
import { FascicoloComponent } from './components/fascicolo/fascicolo.component';
import { FormUlterioreDocumentazioneComponent } from './components/form-fascicolo/form-ulteriore-documentazione/form-ulteriore-documentazione.component';
import { IndirizziEntiComponent } from './components/funzoni amministratore/rubrica/indirizzi-enti/indirizzi-enti.component';
import { TemplateComunicazioniComponent } from './components/funzoni amministratore/templating comunicazioni/template-comunicazioni/template-comunicazioni.component';
import { GestioneGruppoComponent } from './components/gestione-gruppo/gestione-gruppo.component';
import { HeaderComponent } from './components/header/header.component';
import { LoaderComponent } from './components/loader/loader.component';
import { LoginComponent } from './components/login/login.component';
import { ManualeComponent } from './components/manuale/manuale.component';
import { NuovoFascicoloComponent } from './components/nuovo-fascicolo/nuovo-fascicolo.component';
import { SearchAccordionComponent } from './components/search-accordion/search-accordion.component';
import { OrderByPipe } from './pipe/order-by.pipe';
import { ApiService } from './services/api.service';
import { LocalSessionServiceService } from './services/local-session-service.service';
import { FooterComponent } from './components/footer/footer.component';
import { PageImportProvvedimentoComponent } from './components/page-import-provvedimento/page-import-provvedimento.component';


library.add(fas, far);


/* import { FormSearchComponent } from './components/form-search/form-search.component';
 */

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient)
{
  return new TranslateHttpLoader(http, "assets/i18n/", ".json");
}
export function AuthFactory(oauthService: OAuthService, api: ApiService, route: ActivatedRoute, lss: LocalSessionServiceService)
{
  return new Wso2Auth(oauthService, api);
}

const appRoutes: Routes = [
  { path: 'error', component: ErrorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'public/fascicolo', component: FascicoloPublicComponent, data: { breadcrumb: 'Consultazione pubblica provvedimenti' } },
  //{ path: 'public/fascicolo-cartografia', component: CartografiaComponent, data: { breadcrumb: 'Cartografia Fascicoli' }, },
  { path: 'private/gestione-gruppo', component: GestioneGruppoComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Gestione Gruppo' } },
  { path: 'private/fascicolo/nuovo', component: NuovoFascicoloComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Nuovo fascicolo' } },
  { path: 'private/fascicolo/dettaglio/:id', component: DettaglioFascicoloComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Dettaglio fascicolo' } },
  { path: 'private/fascicolo/dettaglio/import/:id', component: PageImportProvvedimentoComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Dettaglio fascicolo' } },
  { path: 'private/fascicolo', component: FascicoloComponent, data: { breadcrumb: 'Gestione provvedimenti' }, canActivate: [AuthGuard] },
  { path: 'private/templates', component: TemplateComunicazioniComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Templates di comunicazione' } },
  { path: 'private/verifica-hash', component: VerificaImprontaHashComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Verifica impronta file' } },
  { path: 'private/rubrica', component: IndirizziEntiComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Rubrica enti' } },
  { path: 'private/manuale', component: ManualeComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Manuale' } },
  { path: 'admin', loadChildren: './features/admin-utils/admin-module/admin.module#AdminModule', canActivate: [AdminGuard], data: { breadcrumb: 'Funzioni di amministrazione' } },
  { path: 'richiesta-abilitazione', component: RichiestaAbilitazioneComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Richiesta abilitazione' } },
  { path: 'assegna-fascicolo-config', component: AssegnaFascicoloConfigComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Assegna Fascicolo' } },
  { path: 'assegna-fascicolo', component: AssegnaFascicoloComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Assegna Fascicolo' } },
  { path: 'privacy/accetta', component: AccettaPrivacyComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Accetta privacy' }  },
  { path: '', redirectTo: 'public/fascicolo', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    DashboardComponent,
    SearchAccordionComponent,
    FascicoloPublicComponent,
    FascicoloComponent,
    NuovoFascicoloComponent,
    DettaglioFascicoloComponent,
    LoaderComponent,
    ErrorComponent,
    BreadcrumbComponent,
    OrderByPipe,
    FormUlterioreDocumentazioneComponent,
    GestioneGruppoComponent,
    RichiestaAbilitazioneComponent,
    FooterComponent,
    PageImportProvvedimentoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FontAwesomeModule,
    DataTableModule.forRoot(),
    StorageServiceModule,
    BrowserAnimationsModule,
    SharedModule,
    UserIdleModule.forRoot({ idle: 1740, timeout: 60, ping: 120 }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    RouterModule.forRoot(appRoutes),
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN'
    }),
    OAuthModule.forRoot()

  ],
  providers: [
    {
      provide: AUTH_SERVICE,
      useFactory: AuthFactory,
      deps: [OAuthService, ApiService, ActivatedRoute, LocalSessionServiceService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptor,
      multi: true
    },
  ],
  exports: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
