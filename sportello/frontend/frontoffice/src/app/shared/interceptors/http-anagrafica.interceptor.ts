//import * as anagrafica from 'src/assets/mocked-data/anagrafica.json';

import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Injectable } from '@angular/core';
import { CONST } from '../constants';

const PROVINCIA_URL = CONST.WEB_RESOURCE_BASE_URL +'public/provincia/autocomplete.pjson';
const NAZIONE_URL = CONST.WEB_RESOURCE_BASE_URL +'public/nazione/autocomplete.pjson';
const COMUNE_URL = CONST.WEB_RESOURCE_BASE_URL +'public/comuni/autocomplete.pjson';

@Injectable()
export class HttpAnagraficaInterceptor implements HttpInterceptor {

  private getPayload(dataOrig:any[],req:HttpRequest<any>){
    let data=dataOrig;
    let baseResponse={"code":"OK","message":"success","size":1,"partialSize":1,payload:[]};
    if(req.body['filter']){
      data=dataOrig.filter(el=>{let r:string=el.label; return r.toUpperCase().includes(req.body['filter'].toUpperCase())});
    }
      baseResponse.size=data.length;
      baseResponse.partialSize=data.length;
      baseResponse.payload=data;
      return baseResponse;
  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    /*console.log('ANAGRAFICA - INTERCEPT', anagrafica);
    if (req.url === NAZIONE_URL) {
      return of(
        new HttpResponse({ status: 200, body: this.getPayload(anagrafica.nazioni,req)})
      );
    }
    else if (req.url === PROVINCIA_URL) {
      return of(
        new HttpResponse({ status: 200, body: this.getPayload(anagrafica.province,req) })
      );
    }
    else if (req.url === COMUNE_URL) {
      return of(
        new HttpResponse({ status: 200, body: this.getPayload(anagrafica.comuni,req) })
      );
    }*/
    return next.handle(req);
  }
}
