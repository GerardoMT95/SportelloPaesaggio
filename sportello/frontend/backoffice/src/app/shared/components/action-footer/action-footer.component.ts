import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-action-footer',
  templateUrl: './action-footer.component.html',
  styleUrls: ['./action-footer.component.scss']
})
export class ActionFooterComponent implements OnInit {

  @Input() isDisabled: boolean;
  @Input() showValidButton: boolean = true;
  @Input() showResetButton: boolean = true;

  @Output() salvaChange = new EventEmitter();
  @Output() annullaChange = new EventEmitter();
  @Output() validaChange = new EventEmitter();
  @Output() indietroChange = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  salva() {
    this.salvaChange.emit();
  }

  annulla() {
    this.annullaChange.emit();
  }

  valida() {
    this.validaChange.emit();
  }

  indietro() {
    this.indietroChange.emit();
  }

}
