import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-nuovo-ulteriori-destinatario',
  templateUrl: './nuovo-ulteriori-destinatario.component.html',
  styleUrls: ['./nuovo-ulteriori-destinatario.component.scss']
})
export class NuovoUlterioriDestinatarioComponent implements OnInit {
  @Input() disabled: boolean = false;
  form: FormGroup;
  @Output() addDestinaarioEmitter: EventEmitter<any> = new EventEmitter<any>();
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.buildForm();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      ente: [''],
      mailPec: ['']
    });
  }

  addDestianatario() {
    this.form.get('ente').setValidators(Validators.required);
    this.form.get('ente').updateValueAndValidity();
    this.form.get('mailPec').setValidators([Validators.required, Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$')]);
    this.form.get('mailPec').updateValueAndValidity();
    this.addDestinaarioEmitter.emit(this.form);
  }
}
