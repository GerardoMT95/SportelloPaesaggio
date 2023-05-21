import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SimpleFormControlData } from './../../../app/components/model/model';

@Component({
  selector: 'app-generic-modal-form',
  templateUrl: './generic-modal-form.component.html',
  styleUrls: ['./generic-modal-form.component.scss']
})
export class GenericModalFormComponent implements OnInit
{
  @Input('open') set open(f: boolean) 
  { 
    if(!f && this.form)
      this.form.reset();
    this.visible = f;
  }
  @Input('title') title: string = "MODALE GENERICO";
  @Input('configuration') formConfiguration: Array<SimpleFormControlData> = [];
  @Input('init') init: any = null;
  @Input('error') errorMessage: string = null;

  @Output('close') onClose: EventEmitter<void> = new EventEmitter();
  @Output('complete') onComplete: EventEmitter<any> = new EventEmitter();

  public form: FormGroup;  
  public validation: boolean = false;
  public visible: boolean = true;

  public nFields: number = 0;

  constructor(private formBuilder: FormBuilder) { }

  public ngOnInit(): void 
  {
    this.form = this.formBuilder.group({});
    if(this.formConfiguration)
    {
      this.formConfiguration.forEach(control => this.form.addControl(control.formControlName, this.formBuilder.control(null, control.validators)));
      this.nFields = this.formConfiguration.filter(f => f.type != 'hidden').length;
      if(this.init) this.form.patchValue({...this.init});
    }
  }

  public save(): void
  {
    this.validation = true;
    if(this.form.valid)
    {
      this.validation = false;
      this.onComplete.emit(this.form.getRawValue());
    }
  }

  public close(): void 
  {  
    this.form.reset();
    this.onClose.emit();
  }

}
