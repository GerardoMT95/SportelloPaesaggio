import { Injectable, EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';

/**
 * Loading service
 */
@Injectable({providedIn: 'root'})
export class LoadingService {

  public loadingObservable: EventEmitter<boolean> = new EventEmitter();
  public loadingDownloadObservable: EventEmitter<boolean> = new EventEmitter();
  public subjectProgressBar:Subject<number>=new Subject<number>();

  constructor() {
  }
  /**
   * Emit Loading
   * @param isActiveLoading. Is message error
   */
  public emitLoading(isActiveLoading:boolean){
      this.loadingObservable.emit(isActiveLoading);
  }

  public emitLoadingDownload(isActiveLoading:boolean){
    this.loadingDownloadObservable.emit(isActiveLoading);
  }

}
