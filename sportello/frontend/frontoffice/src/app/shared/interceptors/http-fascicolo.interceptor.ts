import { Injectable } from "@angular/core";
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import { Observable, of } from "rxjs";
import * as data from "../../../assets/mocked-data/fascicolo.json";
import { Fascicolo } from "../models/models.js";

const FASCICOLO_URL = "http://localhost:8080/fascicolo";

@Injectable()
export class HttpFascicoloInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //console.log("FASCICOLO - INTERECEPT");
    if (req.url === FASCICOLO_URL) {
      const fascicoloId = req.params.get("id");
      const searchQuery = req.params.get("searchQuery");
      const fascicoloData = (data as any).default;
      if (fascicoloId) {
        let fascicolo = Fascicolo;
        fascicolo = fascicoloData.find(
          item => item.codiceFascicolo === fascicoloId
        );
        return of(new HttpResponse({ status: 200, body: [fascicolo] }));
      } else if (searchQuery) {

        const searchObject: Fascicolo = JSON.parse(searchQuery);

        const res = fascicoloData.filter(item => {
          const val = [];
          Object.keys(searchObject).forEach(key => {
              val.push(item[key] === searchObject[key]);
          });
          return !val.includes(false);
        });
          return of(new HttpResponse({ status: 200, body: [...res] }));
      } else {
        return of(new HttpResponse({ status: 200, body: [...fascicoloData] }));
      }
    }
    return next.handle(req);
  }
}
