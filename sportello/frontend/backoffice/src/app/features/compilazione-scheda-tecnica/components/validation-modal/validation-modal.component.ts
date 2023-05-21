import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

export interface ValidationMessage{
  indexPannello: number;
  sezione: string;
  messaggi: string[];
}

//forse non piu' utilizzata
/*@Component({
  selector: 'app-validation-modal',
  templateUrl: './validation-modal.component.html',
  styleUrls: ['./validation-modal.component.scss']
})*/
export class ValidationModalComponent implements OnInit {

  @Input() mostraDialog: boolean;
  @Input() invalidFields: ValidationMessage[];

  @Output() closeDialog: EventEmitter<void> = new EventEmitter<void>();
  @Output() focusTab: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit() {
    
  }

  public close(): void {
    this.mostraDialog = false;
    this.closeDialog.emit();
  }

  public closeAndUpdatePanel(indiceTab: number): void {
    this.mostraDialog = false;
    this.focusTab.emit(indiceTab);
  }

}
