import { DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';


export class DialogModel {
    showDialog?: boolean;
    message?: string;
    messageTranslateParams?:any;
    title?: string;
    buttons?: IButton[];
    btnCalback?: Function;
    dialogType?: DialogType; 
    dialogIconClass?: string;
    redirectUrl: string;
    isLarge?: boolean;
}

export interface IButton {
    id: number;
    label: string;
    icon?: string; 
    class?: string   
}
