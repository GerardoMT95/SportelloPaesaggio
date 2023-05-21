import { Injectable, Injector } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as data from 'src/assets/mocked-data/dominio.json';

const DOMINIO_URL = 'http://localhost:8080/dominio';

@Injectable()
export class HttpDominioInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //console.log('DOMINIO - INTERECEPT');
    if (req.url === DOMINIO_URL) {
      const dominioData = (data as any).default;
      const dominioType = req.params.get('dominioType');
      if (dominioType) {
        return of(
          new HttpResponse({ status: 200, body: [...dominioData[dominioType]] })
        );
      }

      return of(
        new HttpResponse({ status: 200, body: dominioData })
      );
    }

    return next.handle(req);
  }
}
