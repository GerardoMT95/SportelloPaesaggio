import { Injectable, EventEmitter } from '@angular/core';

/**
 * Error service
 */
@Injectable({providedIn: 'root'})
export class ErrorService {

  public errorObservable: EventEmitter<string> = new EventEmitter();

  constructor() {
  }
  /**
   * Emit error
   * @param message. Is message error
   */
  public emitError(message:string){
      console.log("Emit error " + message);
      this.errorObservable.emit(message);
  }

}
