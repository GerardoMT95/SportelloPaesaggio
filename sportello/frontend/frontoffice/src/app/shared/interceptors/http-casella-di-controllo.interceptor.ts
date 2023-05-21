import { Injectable, Injector } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as data from 'src/assets/mocked-data/casella-di-controllo.json';

const CASELLA_URL = 'http://localhost:8080/casella';

@Injectable()
export class HttpCasellaDiControlloInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //console.log('CASELLA - INTERECEPT');
    if (req.url === CASELLA_URL) {
      const casellaData = (data as any).default;
      const casellaType = req.params.get('dominioType');
      if (casellaType) {
        return of(
          new HttpResponse({ status: 200, body: [...casellaData[casellaType]] })
        );
      }

      return of(
        new HttpResponse({ status: 200, body: casellaData })
      );
    }

    return next.handle(req);
  }
}
