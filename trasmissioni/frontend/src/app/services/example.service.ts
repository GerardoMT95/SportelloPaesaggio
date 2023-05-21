import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ExampleService {

  constructor(private http: HttpClient) { }

  getCountries(){
    return this.http.get("http://sitpuglia-parchi-svil.it/sitpuglia-autocertificazione-rr242005/public/provincia/list.pjson")
  }
}
