import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CONST } from '../constants';


@Injectable({ 
  providedIn: 'root'
})
export class ComuneService {

  constructor(
    private http: HttpClient
  ) { }

  // AUTOCOMPLETE PER LA PARTE DI PARTICELLA
  public getResultsEnte(query:string){
    let parameters:any = {
      filter: query,
      limit: 10
    };

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/ente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }
  public getResultsEnteSiti(query:string){
    let parameters:any = {
      filter: query,
      limit: 10
    };

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/ente/autocompleteComuneSiti.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

  // AUTOCOMPLETE PER LA PARTE DI LAVORAZIOEN FASCICOLO
  public getResults(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };
    if(parentId)
      parameters.parentId = parentId;

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/public/comuni/autocomplete.pjson', null,
        {params:parameters}
        )
        .toPromise()
        .then(response => (<any>response).payload);
  }


  // AUTOCOMPLETE PER LA PARTE DI RICERCA
  public getResultsRicerca(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };
    if(parentId)
      parameters.parentId = parentId;

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/public/ente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

  // AUTOCOMPLETE PER LA PARTE DI RICERCA DI ENTE
  public getResultsRicercaEnte(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };
    if(parentId)
      parameters.parentId = parentId;

    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + '/utente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

}
