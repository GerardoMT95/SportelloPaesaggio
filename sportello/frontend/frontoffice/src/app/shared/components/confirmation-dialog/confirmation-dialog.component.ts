import { Component, OnInit } from '@angular/core';
import { DialogService } from 'src/app/core/services/dialog.service';
import { IButton, DialogModel } from 'src/app/core/models/dialog.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit {
  display: boolean = false;
  message: string;
  messageTranslateParams?: any;
  title: string;
  buttons: IButton[];
  btnFun: Function;
  iconClass: string = DialogIconType.INFORMATION;
  dialogTypeClass: string;
  redirectUrl: string;
  isLarge=false;
  styleDialog:any={ width: '450px', minWidth: '250px' };

  constructor(private serviceDialog: DialogService, private router: Router) {}

  ngOnInit() {
    this.serviceDialog.promptObject.subscribe((response: DialogModel) => {
      this.display = response.showDialog;
      this.message = response.message;
      this.messageTranslateParams = response.messageTranslateParams;
      this.title = response.title;
      this.buttons = response.buttons;
      this.setDialogCssClasses(response.dialogType);
      this.btnFun = response.btnCalback;
      this.redirectUrl = response.redirectUrl;
      this.isLarge=response.isLarge;
      if(this.isLarge==true){
        this.styleDialog={width: '70vw'};
      }else{
        this.styleDialog={ width: '450px', minWidth: '250px' };
      }
    });
  }

  setDialogCssClasses(dialogType: DialogType) {
    switch (dialogType) {
      case DialogType.INFORMATION:
        this.iconClass = DialogIconType.INFORMATION;
        this.dialogTypeClass = 'information-message';
        break;

      case DialogType.SUCCESS:
        this.iconClass = DialogIconType.SUCCESS;
        this.dialogTypeClass = 'success-message';
        break;

      case DialogType.WARNING:
        this.iconClass = DialogIconType.WARNING;
        this.dialogTypeClass = 'warning-message';
        break;

      case DialogType.ERROR:
        this.iconClass = DialogIconType.ERROR;
        this.dialogTypeClass = 'error-message';
        break;

      default:
        this.iconClass = DialogIconType.INFORMATION;
        this.dialogTypeClass = 'information-message';
        break;
    }
  }
  closeDialog() {
    this.display = false;
  }

  buttonClicked(btnInfo: IButton) {
    this.display = false;
    if (this.btnFun) {
      console.log('button is pressed!!!');
      this.btnFun(btnInfo.id);
    }
    if (this.redirectUrl) {
      this.display = false;
      this.router.navigate([this.redirectUrl]);
    }
  }
}

export enum ButtonType {
  CANCEL_BUTTON=0, 
  OK_BUTTON=1,
  CLOSE_BUTTON=3
}

export class DialogButtons {
  public static SI_NO: IButton[] = [
    { id: ButtonType.CANCEL_BUTTON, label: 'NO', icon: 'ml-3 fa fa-times' },
    { id: ButtonType.OK_BUTTON, label: 'YES', icon: 'ml-3 fa fa-check' }
  ];
  public static OK_CANCEL: IButton[] = [
    { id: ButtonType.CANCEL_BUTTON, label: 'BUTTONS.CANCEL_BUTTON', icon: 'ml-3 fa fa-times' },
    { id: ButtonType.OK_BUTTON, label: 'BUTTONS.OK_BUTTON', icon: 'ml-3 fa fa-save' }
  ];
  public static OK_CANCEL_CHIUDI: IButton[] = [
    { id: ButtonType.CLOSE_BUTTON, label: 'BUTTONS.CHIUDI_BUTTON', icon: 'ml-3 fa fa-times' },
    { id: ButtonType.CANCEL_BUTTON, label: 'BUTTONS.CANCEL_BUTTON', icon: 'ml-3 fa fa-arrow-left' },
    { id: ButtonType.OK_BUTTON, label: 'BUTTONS.OK_BUTTON', icon: 'ml-3 fa fa-check' }
  ];
  public static CHIUDI: IButton[] = [{ id: 3, label: 'BUTTONS.CHIUDI_BUTTON', icon: 'ml-3 fa fa-times' }];
  public static CONFERMA_CHIUDI: IButton[] = [
    { id: ButtonType.CLOSE_BUTTON, label: 'BUTTONS.CHIUDI_BUTTON', icon: 'ml-3 fa fa-times' },
    { id: ButtonType.OK_BUTTON, label: 'BUTTONS.CONFERMA_BUTTON', icon: 'ml-3 fa fa-check'  }
  ];
}

export enum DialogIconType {
  INFORMATION = 'fa fa-info-circle dialog-icon',
  WARNING = 'fa fa-warning style-warning',
  ERROR = 'fa fa-times fa-w-11 style-danger',
  SUCCESS = 'fa fa-check style-success'
}

export enum DialogType {
  INFORMATION,
  WARNING,
  ERROR,
  SUCCESS
}


