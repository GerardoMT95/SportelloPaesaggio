import { Injectable } from '@angular/core';
//import { URLSearchParams } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { CONST } from '../constants';


@Injectable({
  providedIn: 'root'
})
export class NazionalitaService {

  constructor(
    private http: HttpClient
  ) { }

  public getResults(query:string){
    //let parameters: URLSearchParams = new URLSearchParams();
    //parameters.append('filter',query);
    //parameters.append('limit','10');
    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/public/nazione/autocomplete.pjson',null,{
          params:{'filter':query,
          'limit':"10"}
        })
        .toPromise()
        .then(response => (<any>response).payload);
  }
}
