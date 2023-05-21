import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-nuovo-destinatario',
  templateUrl: './nuovo-destinatario.component.html',
  styleUrls: ['./nuovo-destinatario.component.scss']
})
export class NuovoDestinatarioComponent implements OnInit {

  @Output("onAggiungi") onAggiungi: EventEmitter<any> = new EventEmitter<any>();

  constructor(private formBuilder: FormBuilder) { }

  public form: FormGroup = this.formBuilder.group({
    denominazione: [""],
    mail: [""],
    salvaInRubricaEnte: [false]
  });

  ngOnInit() {
  }

  public aggiungi(): void {
    this.onAggiungi.emit(this.form.value);
  }

  public annulla(): void {
    this.form.setValue({
      denominazione: "",
      mail: "",
      salvaInRubricaEnte: false
    });
  }

}
