import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class  HttpPptrService {
  constructor(private httpClient: HttpClient) {}

  getAllPptr() {
    return this.httpClient.get<{ [key: string]: any[] }>('http://localhost:8080/pptr').pipe(
      /** TODO: Add some base response or something like that */
      map((response: { [key: string]: any[] }) => response)
    );
  }
}
