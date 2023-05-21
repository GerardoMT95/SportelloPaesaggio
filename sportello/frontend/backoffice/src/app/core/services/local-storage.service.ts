import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  setStateStorage(state: any, key: string) {
    localStorage.setItem(key, JSON.stringify(state));
  }

  getStateStorage(key: string) {
    return JSON.parse(localStorage.getItem(key));
  }

  deleteStateStorage(key: string) {
    localStorage.removeItem(key);
  }
}
