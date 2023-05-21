import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SelectOption } from '../components/select-field/select-field.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpAllegatoService {

  constructor(private httpClient: HttpClient) { }

  getTipoDocumento(): Observable<SelectOption[]> {
    return this.httpClient.get<SelectOption[]>('http://localhost:8080/tipo').pipe(
      map(
        (response: SelectOption[]) => {
          return response;
        })
    );

  }
}
