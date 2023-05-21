import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SelectOption } from '../components/select-field/select-field.component';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpCasellaDiControlloService {
  constructor(private httpClient: HttpClient) {}

  getCasella(casellaType: string): Observable<Partial<SelectOption[]>> {
    const httpParams = new HttpParams().set('casellaType', casellaType);
    return this.httpClient
      .get<Partial<SelectOption[]>>('http://localhost:8080/casella', {
        params: httpParams
      })
      .pipe(
        /** TODO: Add some base response or something like that */
        map((response: Partial<SelectOption[]>) => response)
      );
  }

  getAllCasella() {
    return this.httpClient
      .get<{ [key: string]: Partial<SelectOption[]> }>('http://localhost:8080/casella')
      .pipe(
        /** TODO: Add some base response or something like that */
        map((response: { [key: string]: Partial<SelectOption[]> }) => response)
      );
  }
}
