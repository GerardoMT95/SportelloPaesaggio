import { HttpClient, HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
// FONT AWESOME FOR BOOTSTRAP
//import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
// PRIME MG MODULE 
/*import { CalendarModule } from 'primeng/calendar';
import { TabViewModule } from 'primeng/tabview';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FileUploadModule } from 'primeng/fileupload';
import { KeyFilterModule } from 'primeng/keyfilter';
import { DropdownModule } from 'primeng/dropdown';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { MultiSelectModule } from 'primeng/multiselect';*/
// DATATABLE
// BOOTSTRAP FOR ANGULAR
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// import ngx-translate and the http loader
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { UserIdleModule } from 'angular-user-idle';
import { StorageServiceModule } from "angular-webstorage-service";
import { DataTableModule } from 'ng-angular8-datatable';
import { AppRoutingModule } from './app-routing.module';
// APP COMPONENT
import { AppComponent } from './app.component';
import { AuthInterceptor } from './shared/components/auth/AuthInterceptor.interceptor';
import { CanActiveteGuard } from './shared/components/auth/CanActivateGuard.guard';
import { AUTH_SERVICE } from './shared/components/auth/IAuthService';
import { IsLoggedGuard } from './shared/components/auth/IsLoggedGuard.guard';
import { Wso2Auth } from './shared/components/auth/Wso2Auth';
import { HttpAnagraficaInterceptor } from './shared/interceptors/http-anagrafica.interceptor';
import { HttpCasellaDiControlloInterceptor } from './shared/interceptors/http-casella-di-controllo.interceptor';
import { HttpDichiarazioniInterceptor } from './shared/interceptors/http-dichiarazioni.interceptor';
//import { CoreModule } from './core/core.module';
import { HttpDominioInterceptor } from './shared/interceptors/http-dominio.interceptor';
import { HttpElencoAllegatiInterceptor } from './shared/interceptors/http-elenco-allegati.interceptor';
import { HttpFascicoloInterceptor } from './shared/interceptors/http-fascicolo.interceptor';
import { HttpPptrInterceptor } from './shared/interceptors/http-pptr.interceptor';
/*import { AlertComponent } from './shared/components/alert/alert.component';
import { DisableControlDirective } from './shared/directive/disable-control.directive';
import { CanAccessDirective } from './shared/directive/can-access.directive';



import { LoaderComponent } from './shared/components/loader/loader.component';
import { HeaderComponent } from './shared/components/header/header.component';*/
import { ApiService } from './shared/services/api.service';
import { LocalSessionServiceService } from './shared/services/local-session-service.service';
import { SharedModule } from './shared/shared.module';




library.add(fas, far);

//import {TableModule} from "primeng/table";
// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "assets/i18n/", ".json");
}
export function AuthFactory(oauthService: OAuthService, api: ApiService, route: ActivatedRoute, lss:LocalSessionServiceService) {
  return new Wso2Auth(api, oauthService);
}


@NgModule({
  declarations: [
    AppComponent,
    /*ErrorComponent,
    LoginComponent,
    DashboardComponent,*/
    /*LoaderComponent,
    HeaderComponent,
    AlertComponent,
    DisableControlDirective,
    CanAccessDirective,*/
       
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModule,
    FontAwesomeModule,
    DataTableModule,
    FormsModule,
    ReactiveFormsModule,
    StorageServiceModule,
    BrowserAnimationsModule,
    /*CalendarModule,
    TabViewModule,
    DialogModule,
    RadioButtonModule,
    FileUploadModule,
    KeyFilterModule,
    DropdownModule,
    AutoCompleteModule,
    MultiSelectModule,*/
    UserIdleModule.forRoot({idle: 1740, timeout: 60, ping: 120}),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN'
    }),
    OAuthModule.forRoot(),
    //TableModule
    SharedModule,
    //CoreModule,
    //AlertComponent,
    AppRoutingModule
  ],
  providers: [
    CanActiveteGuard,
    IsLoggedGuard,
    {
      provide: AUTH_SERVICE,
      useFactory : AuthFactory,
      deps: [OAuthService, ApiService, ActivatedRoute, LocalSessionServiceService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpDominioInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpDichiarazioniInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpFascicoloInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpPptrInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpCasellaDiControlloInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpElencoAllegatiInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpAnagraficaInterceptor,
      multi: true
    }, 
  ],
  bootstrap: [AppComponent]
})

export class AppModule { 


/*  constructor(library: FaIconLibrary){
    library.addIconPacks(fas);
    library.addIconPacks(far);
  }*/

}
