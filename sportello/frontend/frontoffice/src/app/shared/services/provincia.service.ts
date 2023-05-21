import { Injectable } from '@angular/core';
import { CONST } from '../constants';
//import { URLSearchParams } from '@angular/http';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProvinciaService {

  constructor(
    private http: HttpClient
  ) { }

  // AUTOCOMPLETE PER LA PARTE DI LAVORAZIOEN FASCICOLO
  public getResults(query:string){
    //let parameters: URLSearchParams = new URLSearchParams();
    //parameters.append('filter',query);
    //parameters.append('limit','10');
    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/public/provincia/autocomplete.pjson',null,{
          params:{'filter':query,
          'limit':"10"}
        })
        .toPromise()
        .then(response => (<any>response).payload);
  }

  // AUTOCOMPLETE PER LA PARTE DI RICERCA
  public getResultsRicerca(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: 10
    };
    if(parentId)
      parameters.parentId = parentId;

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/public/ente/autocompleteProvincia.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

  // AUTOCOMPLETE PER LA PARTE DI RICERCA DI ENTE
  public getResultsRicercaEnte(query:string){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/utente/autocompleteProvincia.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

}
