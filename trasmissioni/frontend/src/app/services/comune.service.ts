import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CONST } from 'src/shared/constants';
import { BaseResponse } from '../components/model/base-response';
import { EnteDTO } from '../components/model/entity/ente.models';
import { SelectItem } from 'primeng/components/common/selectitem';
//import { CONST } from '../shared/constants';

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
        .post(CONST.WEB_RESOURCE_BASE_URL + 'ente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }

  
  /**
   * mocked
   * @param query 
   */
  public getResultsEnteSiti(query:string){
    let parameters:any = {
      filter: query,
      limit: 10
    };
    
    return new Promise((resolve,reject)=>{
       resolve(CONST.MockComuni);} );
    /*return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + 'ente/autocompleteComuneSiti.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);*/
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
        .post(CONST.WEB_RESOURCE_BASE_URL + 'public/comuni/autocomplete.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }


  // AUTOCOMPLETE PER LA PARTE DI RICERCA COMUNE
    
   /*mockComuni=["Bari",
   "Barletta",
   "Mola di Bari",
   "Sammichele di Bari",
   "Sannicandro di Bari"
  ];*/
  /**
   * 
   * @param query  es:adelf
   * @param parentId  id provincia
   */
  public getResultsRicerca(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };
    if(parentId)
      parameters.parentId = parentId;
     return new Promise((resolve,reject)=>{
        resolve(CONST.MockComuni);} );

    /*return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + 'public/ente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);*/
  }

  public cercaComuni(query: string, limit?: number): Observable<BaseResponse<any[]>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/public/ente/autocompleteComuni";
    let httpParams = new HttpParams().set("filter", query);
    if(limit) httpParams = httpParams.append("limit", limit.toString());
    httpParams = httpParams.append("soloPuglia", "true");
    let params = { params: httpParams };
    return this.http.get<BaseResponse<any[]>>(url, params);
  }

  // AUTOCOMPLETE PER LA PARTE DI RICERCA DI ENTE
  /*public getResultsRicercaEnte(query:string, parentId?:number){
    let parameters:any = {
      filter: query,
      limit: CONST.MAX_RESULT_AUTOCOMPLETE
    };
    if(parentId)
      parameters.parentId = parentId;
    //mock
      return new Promise((resolve,reject)=>{
        resolve(CONST.MockComuniUser);} );
    return this.http
        .post(CONST.WEB_RESOURCE_BASE_URL + 'utente/autocompleteComune.pjson', parameters)
        .toPromise()
        .then(response => (<any>response).payload);
  }*/

  public findComuniEProvince(): Observable<BaseResponse<EnteDTO[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/ente/findComuniEProvince";
    return this.http.get<BaseResponse<EnteDTO[]>>(url);
  }

  public autocompleteNazioni(filter: string, limit?: number): Observable<BaseResponse<SelectItem[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/ente/autocompleteNazioni";
    let httpParams: HttpParams = new HttpParams().set("filter", filter);
    if (limit)
      httpParams = httpParams.append("limit", limit.toString());
    return this.http.get<BaseResponse<SelectItem[]>>(url, { params: httpParams });
  }

  public autocompleteProvice(filter: string, limit?: number): Observable<BaseResponse<SelectItem[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/ente/autocompleteProvince";
    let httpParams: HttpParams = new HttpParams().set("filter", filter);
    if(limit)
      httpParams = httpParams.append("limit", limit.toString());
    return this.http.get<BaseResponse<SelectItem[]>>(url, {params: httpParams});
  }

  public autocompleteComuni(filter: string, denominazioneProv: string, idProvincia: number, limit?: number): Observable<BaseResponse<SelectItem[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/ente/autocompleteComuni";
    let httpParams: HttpParams = new HttpParams().set("filter", filter);
    if(denominazioneProv)
      httpParams = httpParams.append("denominazioneProvincia", denominazioneProv);
    if(idProvincia)
      httpParams = httpParams.append("idProvincia", idProvincia.toString());
    if (limit)
      httpParams = httpParams.append("limit", limit.toString());
    return this.http.get<BaseResponse<SelectItem[]>>(url, { params: httpParams });
  }

}
