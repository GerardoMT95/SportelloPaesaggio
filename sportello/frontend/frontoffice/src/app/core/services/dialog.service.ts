import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { DialogModel, IButton } from '../models/dialog.model';


@Injectable({
  providedIn: 'root'
})
export class DialogService {
  promptObject = new Subject<DialogModel>();
  constructor() {}

  showDialog(displayDialog?: boolean, message?: string, title?: string, buttons?: IButton[], callback?: Function, dialogType?: DialogType, redirectUrl?: string, messageTranslateParams?: any) {
    const dialog: DialogModel = {
      showDialog: displayDialog,
      message: message,
      messageTranslateParams: messageTranslateParams,
      title: title,
      buttons: buttons,
      btnCalback: callback,
      dialogType: dialogType,
      redirectUrl: redirectUrl
    };
    return this.promptObject.next(dialog);
  }

  showDialogCustom(dialog:DialogModel) {
    return this.promptObject.next(dialog);
  }
}

