import { Injectable, Injector } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as data from 'src/assets/mocked-data/elenco-allegati.json';

const ELENCO_ALLEGATI_URL = 'http://localhost:8080/elenco-allegati';

@Injectable()
export class HttpElencoAllegatiInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //console.log('DOMINIO - INTERECEPT');
    if (req.url === ELENCO_ALLEGATI_URL) {
      const elencoAllegatiData = (data as any).default;
      return of(
        new HttpResponse({ status: 200, body: elencoAllegatiData })
      );
    }

    return next.handle(req);
  }
}
