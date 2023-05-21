import { Injectable } from '@angular/core';
import { CONST } from 'src/shared/constants';
import { HttpClient } from '@angular/common/http';
import { BaseRestResponse } from '../components/model/base-response';
import { AutocompleteRequest, AutocompleteResponse } from '../components/model/model';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  private limitAutocomplete : number = CONST.LIMIT_AUTOCOMPLETE;


  constructor(private http: HttpClient) { }

  public autocompleteTipo(query : string) : Promise<BaseRestResponse<AutocompleteResponse[]>>{
    let request : AutocompleteRequest={filter:query,limit: this.limitAutocomplete}
    return this.http.post<BaseRestResponse<AutocompleteResponse[]>>(CONST.WEB_RESOURCE_BASE_URL + "indirizzo della risorsa", request)
    .toPromise().then(data => data);
  }
  
}
