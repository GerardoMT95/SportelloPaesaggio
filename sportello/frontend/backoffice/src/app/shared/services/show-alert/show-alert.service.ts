import { Injectable, EventEmitter } from "@angular/core";
import { Subject } from "rxjs";

export class Alert
{
  showAlert?: boolean;
  formCreated?: boolean;
  message?: string;
}


@Injectable({
  providedIn: "root"
})
export class ShowAlertService {
  alertObject = new Subject<Alert>();
 // alertVerificationObject = new Subject<Alert>();
  success: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() {}

  showAlertDialog(showDialog?: boolean, formCreated?: boolean, message?: string) {
    const alert: Alert = {
      showAlert: showDialog,
      formCreated: formCreated,
      message: message
    };
    return this.alertObject.next(alert);
  }

  // showVerificationAlertDialog(showDialog?: boolean, formCreated?: boolean, message?: string){
  //   const alert: Alert = {
  //     showAlert: showDialog,
  //     formCreated: formCreated,
  //     message: message
  //   };
  //   return this.alertVerificationObject.next(alert);
  // }
}
