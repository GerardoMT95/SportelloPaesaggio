import { Injectable, EventEmitter } from "@angular/core";
import { Alert } from "../components/model/alert.model";
import { Subject } from "rxjs";


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
