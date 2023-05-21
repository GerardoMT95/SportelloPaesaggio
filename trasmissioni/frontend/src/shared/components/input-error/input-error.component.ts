import { Component, Input } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
//import * as defaultMessages from './defaultErrorMessages.json';

@Component({
    selector: 'app-input-error',
    template: `
        <div class="invalid-feedback" *ngIf="validation && errors">
            <span *ngFor="let error of errorList">
                {{error.keyMessage | translate:error.parameters}}
            </span>
        </div>
    `,
    styles: []
})
export class InputErrorComponent /* implements OnInit, OnChanges */
{
    defaultMessages={
            "required": "errors.required",
            "email": "errors.email",
            "minlength": "generic.minlength",
            "maxlength": "generic.maxlength",
            "textRequired": "errors.required",
            "valueRequired": "errors.required",
            "particellaRequired":"errors.particellaRequired",
            "pattern": "errors.patternErrato",
            "stradeRequired": "errors.stradeRequired",
            "allCheckedRequired": "errors.allCheckedRequired",
            "infoLocalizzazioneRequired":"errors.infoLocalizzazioneRequired",
            "combinazioneOrganizzazioneRuoloInvalida":"errors.combinazioneOrganizzazioneRuoloInvalida",
            "dateError": "errors.dateError"
    }
    @Input("errorMessages") errorMessages: any = (<any>this.defaultMessages);
    @Input("errors") errors: ValidationErrors;
    @Input("validation") validation: boolean = false;

    /* public errorList: string[] = []; */

    constructor(){
      null;  
    }

    get errorList(): any[]
    {
        let list: any[] = [];
        if (this.errorMessages && this.errors)
        {
            Object.keys(this.errors).forEach(errorKey =>
                {
                    let keyMessage=this.errorMessages[errorKey];
                    let parameters={};
                    // { "requiredLength": 50, "actualLength": 30 }
                    if(["minlength","maxlength"].includes(errorKey)) {
                        parameters=this.errors[errorKey];
                    }
                    list.push({keyMessage:keyMessage, parameters:parameters});
                });
        }
        return list;
    }

}