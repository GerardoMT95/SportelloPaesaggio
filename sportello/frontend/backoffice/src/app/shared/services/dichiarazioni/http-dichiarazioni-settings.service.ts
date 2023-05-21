import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpDichiarazioniSettingsService {

  constructor(private httpClient: HttpClient) { }

  getDichiarazioneSettings(typeProcedimento: string) {
    const httpParams = new HttpParams().set('typeProcedimento', typeProcedimento);
    return this.httpClient.get<any>('http://localhost:8080/dichiarazioni',{params: httpParams})
    .pipe(
      /** TODO: Add some base response or something like that */
      map( (response: any[]) => {return response;})
    ); 
  }
}
