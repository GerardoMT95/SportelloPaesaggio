import { Injectable, EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';
import { PromptDialog } from '../components/model/prompt-dialog.model';

@Injectable({
  providedIn: 'root'
})

export class ShowPromptDialogService {

  promptObject =  new Subject<PromptDialog>();
  promptValidation = new Subject<PromptDialog>(); //usata
  promptEsitoValidation = new Subject<PromptDialog>();
  promptBackObject = new Subject<PromptDialog>();
  promptVerificationObject = new Subject<PromptDialog>();
  //promptDeleteAllegatiObject= new Subject<PromptDialog>(); //usata
  promptDeleteAllegatiObject:Subject<PromptDialog>[] =new Array(); //usata in duplice istanza

  constructor() { 
    this.promptDeleteAllegatiObject['provvedimento']=new Subject<PromptDialog>();
    this.promptDeleteAllegatiObject['allegati']=new Subject<PromptDialog>();
    this.promptDeleteAllegatiObject['adminTemplateDoc']=new Subject<PromptDialog>();
    this.promptDeleteAllegatiObject['responsabile']=new Subject<PromptDialog>();
  }

  showPromptDialog(displayDialog: boolean){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptObject.next(promptDialog);
  }

  showValidationDialog(displayDialog: boolean){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptValidation.next(promptDialog);
  }

  showEsitoValidationDialog(displayDialog: boolean){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptEsitoValidation.next(promptDialog);
  }

  showBackPromptDialog(displayDialog: boolean){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptBackObject.next(promptDialog);
  }

  showVerificationDialog(displayDialog: boolean){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptVerificationObject.next(promptDialog);
  }
  
  showDeleteAllegatiDialog(displayDialog: boolean,sezione:string){
    const promptDialog: PromptDialog={
      displayPrompt: displayDialog
    }
    return this.promptDeleteAllegatiObject[sezione].next(promptDialog);

  }



}
