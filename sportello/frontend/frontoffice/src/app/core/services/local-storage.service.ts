import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  setStateStorage(state: any) {
    localStorage.setItem('state', JSON.stringify(state));
  }

  getStateStorage() {
    return JSON.parse(localStorage.getItem('state'));
  }

  removeStateStorage() {
    return localStorage.removeItem('state');
  }

}
