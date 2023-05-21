import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { checkFileSizeTypeFn } from 'src/app/components/functions/genericFunctions';
import { CONST } from './../../../../../shared/constants';
import { ShowAlertService } from './../../../../services/show-alert.service';
import { AllegatoUD } from './../../../model/entity/allegato.models';

@Component({
  selector: 'app-form-ud',
  templateUrl: './form-ud.component.html',
  styleUrls: ['./form-ud.component.scss']
})
export class FormUdComponent implements OnInit
{
  @Input("data") set _(d: any)
  {
    this.data = d;
    if(d && this.form)
      this.form.patchValue({...d});
  } 

  @Output("salva") onSave$: EventEmitter<AllegatoUD> = new EventEmitter();
  @Output("chiudi") onClose$: EventEmitter<void> = new EventEmitter();

  public form: FormGroup;
  public const = CONST;
  public inviato: boolean = false;

  private data: any;

  constructor(private showAlertDialog: ShowAlertService,
              private translateService: TranslateService,
              private fb: FormBuilder) { }

  ngOnInit()
  {
    this.form = this.fb.group({
      titolo: [null, Validators.required],
      descrizione: [null, Validators.required],
      allegato: [null, Validators.required]
    });
    if(this.data)
      this.form.patchValue({ ...this.data });  }

  public allegaFile(event: any): void
  {
    if (event.files && event.files.length > 0)
    {
      let file = event.files[0];
      let fileCheck = checkFileSizeTypeFn(null, CONST.MAX_10MB);
      let errorValidation = fileCheck(file);
      if (errorValidation)
      {
        this.showAlertDialog.showAlertDialog(true, null, this.translateService.instant(errorValidation));
        return;
      }
      this.form.controls.allegato.setValue(event.files[0]);
    }
  }

  public onSubmit(): void 
  { 
    this.inviato = true;
    if(this.form.valid)
    {
      this.inviato = false;
      this.onSave$.emit(this.form.getRawValue()); 
    }
  }
  public svuotaAllegato(): void { this.form.controls.allegato.setValue(null); }
  public onCanceled(): void { this.onClose$.emit(); }
}
