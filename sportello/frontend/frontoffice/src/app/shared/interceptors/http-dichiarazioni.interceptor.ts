import { Injectable, Injector } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as data from 'src/assets/mocked-data/dichiarazioni-settings.json';

const DOMINIO_URL = 'http://localhost:8080/dichiarazioni';

@Injectable()
export class HttpDichiarazioniInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //console.log('DICHI - INTERECEPT', data);
    if (req.url === DOMINIO_URL) {
      const dichiarazioniSettings: any = (data as any).default;
      const typeProcedimento = req.params.get('typeProcedimento');
      //console.log('DICHI - INTERECEPT - tipo:', typeProcedimento);
      return of(
        new HttpResponse({
          status: 200,
          body: [
            ...dichiarazioniSettings.settings.filter(
              sett => sett['typeProcedimento'] == typeProcedimento
            )
          ]
        })
      );
    }

    return next.handle(req);
  }
}
