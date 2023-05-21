import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

export type Dialog = {
  display?:boolean;
  title:string;
  message:string;
  callback?:string;
  isConfirm?:boolean;
  typ?:string;
  extraData?:any;
}

@Injectable({
  providedIn: 'root'
})
export class OLD_DialogService {
  
  private _listners = new Subject<any>();
  private _listnersCallback = new Subject<any>();

  open(filterBy: any) {
    this._listners.next(filterBy);
  }

  whenOpen(): Observable<any> {
     return this._listners.asObservable();
  }
  
  callback(filterBy: any) {
    this._listnersCallback.next(filterBy);
  }

  whenCallback(): Observable<any> {
    return this._listnersCallback.asObservable();
  }

  constructor() {

  }

  
}
