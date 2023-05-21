import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SelectOption } from '../components/select-field/select-field.component';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpElencoAllegatiService {

  constructor(private httpClient: HttpClient) { }

  getAllElencoAllegati() {
    return this.httpClient
      .get('http://localhost:8080/elenco-allegati')
      .pipe(
        map((response) => response
        )
      );
  }
}
