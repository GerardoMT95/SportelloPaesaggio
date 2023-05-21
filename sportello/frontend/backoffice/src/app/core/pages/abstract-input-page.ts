import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { FormGroup, AbstractControl, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { DialogButtons, DialogType, ButtonType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { Router } from '@angular/router';
import { paths } from 'src/app/app-routing.module';


export abstract class AbstractInputPage
{
  codiceFascicolo: string;

  constructor(public dialogService: CustomDialogService, public fb: FormBuilder, public router: Router)
  {
    this.numberOfErrorsOnthePage = {};
  }
  mainForm: FormGroup;
  tabFormNames: string[] = [];

  numberOfErrorsOnthePage: { [key: string]: number };
  numOfAllErrors: any;
  /* public abstract salva();
  public abstract saveOperation(); */

  public valida()
  {
    const formValidated = this.validateSpecificRules();
    let errorText = "<ul>";
    let totalErrors = 0;
    this.tabFormNames.forEach(
      (tabFormName: string) =>
      {
        console.log('tab form name:' + tabFormName)
        this.numberOfErrorsOnthePage[tabFormName] = this.returnNumOfErrorsForErrors(this.mainForm.get(tabFormName) as FormGroup)
        totalErrors += this.numberOfErrorsOnthePage[tabFormName];
        if (this.numberOfErrorsOnthePage[tabFormName] > 0)
        {
          errorText += `<li> ${tabFormName} </li>`
        }
      }
    );
    errorText += "</ul>"
    console.log('num of errors:', this.numberOfErrorsOnthePage);
    if (!this.mainForm.valid || totalErrors)
    {
      this.dialogService.showDialog(true, 'VALIDATION.VALIDATION_ERROR_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
        DialogButtons.CHIUDI,
        (buttonID: string): void =>
        {
          /** TODO: Add your action here if needed */
        },
        DialogType.ERROR,
        null, { tabsWithErrors: errorText });
    } else
    {
      this.mainForm.get('valida').patchValue(true);
      //this.saveOperation();
      this.dialogService.showDialog(true, 'VALIDATION.VALIDATION_SUCCESS_MESSAGE', 'VALIDATION.VALIDATION_DOCUMENT_TITLE',
        DialogButtons.CHIUDI, null, DialogType.SUCCESS);
    }


  }
  /** this will be implemented in evry specific page class */
  public validateSpecificRules(): boolean
  {
    return true;
  }


  returnNumOfErrorsForErrors(formGroup: FormGroup): number
  {
    let numOfErrors = 0;

    Object.keys(formGroup.controls).forEach(
      (key) =>
      {
        let control: AbstractControl = formGroup.get(key);
        if (control instanceof FormControl)
        {
          control.markAsTouched({ onlySelf: true });
          control.markAsDirty({ onlySelf: true });
          numOfErrors += control.errors ? 1 : 0;
          if (control.errors) { console.log('Control :' + key); }
        }
        if (control instanceof FormGroup)
        {
          numOfErrors += this.returnNumOfErrorsForErrors(control);
        }

        if (control instanceof FormArray) 
        {
          control.controls.forEach(
            fg =>
            {
              numOfErrors += this.returnNumOfErrorsForErrors(fg as FormGroup);
            }

          )

        }

      }
    );

    return numOfErrors;

  }

  annula()
  {
    this.dialogService.showDialog(true, 'ANNULA.CONFIRMATION_MESSAGE', 'ANNULA.TITLE',
      DialogButtons.OK_CANCEL,
      (buttonID: number): void =>
      {
        /** TODO: Add your action here if needed */
        if (buttonID === ButtonType.OK_BUTTON)
        {
          this.mainForm.reset();
          this.resetAllFormArrays(this.mainForm, 'main');
          //this.saveOperation();
        }
      },
      DialogType.ERROR,
      null)
  }

  resetAllFormArrays(fg: FormGroup, name: string)
  {
    console.log('Working with fg:', name);
    Object.keys(fg.controls).forEach(
      (key) =>
      {
        let control: AbstractControl = fg.get(key);
        if (control instanceof FormGroup)
        {
          this.resetAllFormArrays(control, key);
        }
        if (control instanceof FormArray)
        {
          while (control.length)
          {
            control.removeAt(control.length - 1);
          }
        }
      }
    );
  }


  indietro()
  {
    if (this.mainForm.dirty)
    {
      this.dialogService.showDialog(true, 'CLOSING_UNSAVED_OBJECT', 'INFORMATION_TITLE', DialogButtons.OK_CANCEL,
        (buttonID: number) =>
        {
          console.log('buttonID====', buttonID);
          if (buttonID === ButtonType.OK_BUTTON)
          {
            this.router.navigate([paths.detailsManagement(this.codiceFascicolo)]);
          }
        }
      );
    } else
    {
      this.router.navigate([paths.detailsManagement(this.codiceFascicolo)]);
    }

  }

}