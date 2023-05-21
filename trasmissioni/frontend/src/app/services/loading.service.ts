import { Injectable, EventEmitter } from '@angular/core';

/**
 * Loading service
 */
@Injectable({providedIn: 'root'})
export class LoadingService {

  public loadingObservable: EventEmitter<boolean> = new EventEmitter();

  constructor() {
  }
  /**
   * Emit Loading
   * @param isActiveLoading. Is message error
   */
  public emitLoading(isActiveLoading:boolean){
      //console.log("Loadind is " + isActiveLoading);
      this.loadingObservable.emit(isActiveLoading);
  }

}
