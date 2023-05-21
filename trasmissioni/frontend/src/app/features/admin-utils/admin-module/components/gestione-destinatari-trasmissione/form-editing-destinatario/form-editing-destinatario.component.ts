import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { CONST } from './../../../../../../../shared/constants';
import { AdminService } from './../../../../../../services/admin-service/admin.service';
import { LoadingService } from './../../../../../../services/loading.service';
import { EmailOrganizzazioneBaseBean } from './../conf/gestione-destinatari-trasmissione-conf';

@Component({
  selector: 'app-form-editing-destinatario',
  templateUrl: './form-editing-destinatario.component.html',
  styleUrls: ['./form-editing-destinatario.component.scss']
})
export class FormEditingDestinatarioComponent
{
  @Input('isSbap') isSpab: boolean = false;
  @Input('readonly') disable: boolean = false;
  @Input('organizzazioni') options: Array<SelectItem> = []
  @Input('init') set _(init: EmailOrganizzazioneBaseBean) 
  {
    this.buildForm(); 
    if(init)
    {
      this.changeOrganizzazione(init.organizzazioneId, () => 
      {
        this.form.patchValue({...init});
        this.evaluateFields();
      });
    }
  }

  @Output('complete') completeEvent: EventEmitter<EmailOrganizzazioneBaseBean> = new EventEmitter();
  @Output('close') closeEvent: EventEmitter<void> = new EventEmitter();

  public form: FormGroup;
  public submitted: boolean = false;
  public enti: Array<SelectItem> = [];

  constructor(private formBuilder: FormBuilder,
              private loading: LoadingService,
              private service: AdminService) { }

  private buildForm(): void
  {
    this.form = this.formBuilder.group({
      id: [null],
	    email: [null, [Validators.required, Validators.email, Validators.maxLength(200)]],
	    denominazione: [null, [Validators.required, Validators.maxLength(200)]],
	    pec: [false, Validators.required],
	    organizzazioneId: [null, Validators.required],
	    enteId: [null]
    }); 
    if(this.disable)
      this.form.disable();
    this.evaluateFields();
  }

  public changeOrganizzazione(idOrganizzazione: number, _?: ()=>void): void
  {
    if(idOrganizzazione)
    {
      this.loading.emitLoading(true);
      this.service.listEntiByOrganizzazione(idOrganizzazione, this.isSpab).subscribe(response =>
      {
        if(response.codiceEsito == CONST.OK)
        {
          this.enti = response.payload.map(m =>{return {label: m.denominazione, value: m.idEnte}});
          /* this.enti.unshift({label: '-- Nessun ente associato --', value: null}); */
          this.evaluateFields();
          if(_) _();
        }
        this.loading.emitLoading(false);
      });
    }
  }

  public done(): void
  {
    this.submitted = true;
    if(this.form.valid)
    {
      this.submitted = false;
      this.completeEvent.emit(this.form.getRawValue());
    }
  }

  public close(): void
  {
    this.closeEvent.emit();
  }

  private evaluateFields(): void
  {  
    if(!this.form.controls.organizzazioneId.value)
    {
      this.form.controls.email.disable();
      this.form.controls.denominazione.disable();
      this.form.controls.pec.disable();
      this.form.controls.enteId.disable();
    }
    else
    {
      this.form.controls.email.enable();
      this.form.controls.denominazione.enable();
      this.form.controls.pec.enable();
      this.form.controls.enteId.enable();
    }
  }

}
