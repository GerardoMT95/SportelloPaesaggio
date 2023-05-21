import { HttpClient, HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
// FONT AWESOME FOR BOOTSTRAP
//import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
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
import { CoreModule } from './core/core.module';
import { AuthInterceptor } from './shared/components/auth/AuthInterceptor.interceptor';
import { AUTH_SERVICE } from './shared/components/auth/IAuthService';
import { Wso2Auth } from './shared/components/auth/Wso2Auth';
import { ApiService } from './shared/services/api.service';
import { LocalSessionServiceService } from './shared/services/local-session-service.service';
import { SharedModule } from './shared/shared.module';

library.add(fas, far);

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "assets/i18n/", ".json");
}
export function AuthFactory(oauthService: OAuthService, api: ApiService, route: ActivatedRoute, lss: LocalSessionServiceService) {
  return new Wso2Auth(api, oauthService);
}

@NgModule({
  declarations: [AppComponent],
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
    UserIdleModule.forRoot({ idle: 1740, timeout: 60, ping: 120 }),
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
    SharedModule,
    CoreModule,
    AppRoutingModule
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
  ],
  bootstrap: [AppComponent]
})
export class AppModule {


  /*constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);
    library.addIconPacks(far);
  }*/

}
