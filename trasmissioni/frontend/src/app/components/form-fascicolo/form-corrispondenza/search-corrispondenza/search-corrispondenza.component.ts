import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CONST } from 'src/shared/constants';

@Component({
  selector: 'app-search-corrispondenza',
  templateUrl: './search-corrispondenza.component.html',
  styleUrls: ['./search-corrispondenza.component.css']
})
export class SearchCorrispondenzaComponent implements OnInit {
  @Output() searchChange: EventEmitter<any> = new EventEmitter<any>();

  index: number = null;
  searchForm: FormGroup;
  mittenteAutocomplete: any;
  destinatarioAutocomplete: any;
  oggettoAutocomplete: any;
  public MAX_YEAR = CONST.MAX_YEAR;

  constructor(
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.searchForm = this.formBuilder.group({
      mittente: ['', null],
      destinatario: ['', null],
      oggetto: ['', null],
      dataInvioDa: ['', null],
      dataInvioA: ['', null],
    });
  }

  onClearControl(event: string, name: string): void {
    this.searchForm.get(name).setValue('');
  }

  //TODO
  searchMittente(event: any) {
    this.mittenteAutocomplete = [];
    console.log("TODO");
  }

  searchDestinatario(event: any) {
    this.destinatarioAutocomplete = [];
    console.log("TODO");
  }
  searchOggetto(event: any) {
    this.oggettoAutocomplete = [];
    console.log("TODO");
  }

  onSubmit() {
    this.searchChange.emit(this.searchForm.value);
  }

  onReset() {
    this.searchForm.reset();
  }

}
