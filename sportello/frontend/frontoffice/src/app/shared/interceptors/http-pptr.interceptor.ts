import * as data from 'src/assets/mocked-data/pptr.json';

import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Injectable } from '@angular/core';

const PPTR_URL = 'http://localhost:8080/pptr';

@Injectable()
export class HttpPptrInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //console.log('PPTR - INTERCEPT', data);
    if (req.url === PPTR_URL) {
      const pptrData = (data as any).default;
      return of(
        new HttpResponse({ status: 200, body: pptrData })
      );
    }
    return next.handle(req);
  }
}
