import { FormBuilder, FormGroup } from '@angular/forms';
import { Ente } from './../../../../shared/models/models';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-rubrica-ente',
  templateUrl: './rubrica-ente.component.html',
  styleUrls: ['./rubrica-ente.component.scss']
})

export class RubricaEnteComponent implements OnInit {

  /**
   * per distinguere se il component è utilizzato per la rubrica dell'ente oppure per quella istituzionale.
   * Sostanzialmente mi aspetto due valori stringa possibili: "ente", "istituzionale"
   */
  @Input("tipoRubrica") tipoRubrica: string; 
  
  @Input("enti") enti: Ente[];
  @Output("onRicerca") onRicerca: EventEmitter<any> = new EventEmitter<any>();
  @Output("onAnnulla") onAnnulla: EventEmitter<any> = new EventEmitter<any>();
  @Output("onAggiorna") onAggiorna: EventEmitter<Ente> = new EventEmitter<Ente>();
  @Output("onElimina") onElimina: EventEmitter<number> = new EventEmitter<number>();
  @Output("onAggiungi") onAggiungi: EventEmitter<Ente> = new EventEmitter<Ente>(); 
  public displayModifica: boolean = false;

  constructor(private formBuilder: FormBuilder) { }

  public form: FormGroup = this.formBuilder.group({
    denominazione: [""],
    mail: [""],
  });

  public formModifica: FormGroup = this.formBuilder.group({
    id: [null],
    denominazione: [""],
    mail: [""],
  });

  ngOnInit() {

  }

  public azione(id: number, azione: string): void {
    if (azione === "modifica") {
      this.modifica(id);
    }
    else if (azione === "elimina") {
      this.onElimina.emit(id);
    }
    else if (azione === "aggiungi") {
      this.aggiungi(id);
    }
  }

  private aggiungi(idEnte: number): void {
    let ente: Ente = this.enti.find(elem => elem.id === idEnte);
    this.onAggiungi.emit(ente);
  }

  private modifica(id: number): void {
    this.displayModifica = true;
    let enteDaModificare: Ente = this.enti.filter(elem => elem.id === id)[0];
    this.formModifica.setValue({
      id: enteDaModificare.id,
      denominazione: enteDaModificare.denominazione,
      mail: enteDaModificare.mail
    });
  }

  public aggiorna(): void {
    let ente: Ente = {
      id: this.formModifica.get("id").value,
      denominazione: this.formModifica.get("denominazione").value,
      mail: this.formModifica.get("mail").value
    };
    this.onAggiorna.emit(ente);
  }

  public ricerca(): void {
    let container: any = {
      denominazione: this.form.get("denominazione").value,
      mail: this.form.get("mail").value
    };
    this.onRicerca.emit(container);
  }

  public annulla(nomeForm: string): void {
    if (nomeForm === "formRicerca") {
      let valoriDefault: any = {
        denominazione: "",
        mail: ""
      };
      this.form.setValue(valoriDefault);
      this.onAnnulla.emit();
    }
    else if (nomeForm === "formModifica") {
      let valoriDefault: any = {
        id: null,
        denominazione: "",
        mail: ""
      };
      this.formModifica.setValue(valoriDefault);
    }
  }

}
