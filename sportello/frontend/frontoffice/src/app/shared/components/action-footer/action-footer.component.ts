import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-action-footer',
  templateUrl: './action-footer.component.html',
  styleUrls: ['./action-footer.component.scss']
})
export class ActionFooterComponent implements OnInit
{

  @Input() isDisabled: boolean;
  @Input() enableAnnulla: boolean = true;
  @Input() enableValida: boolean = true;
  @Input() enableSollevaIncarico: boolean = false;

  @Input() labelValida: string = null;

  @Output() salvaChange = new EventEmitter();
  @Output() annullaChange = new EventEmitter();
  @Output() validaChange = new EventEmitter();
  @Output() indietroChange = new EventEmitter();
  @Output() enableSollevaIncaricoChange = new EventEmitter();

  constructor() { }

  ngOnInit(): void {}

  public salva(): void { this.salvaChange.emit(); }
  public annulla(): void { this.annullaChange.emit(); }
  public valida(): void { this.validaChange.emit(); }
  public indietro(): void { this.indietroChange.emit(); }
  public sollevaIncarico(): void { this.enableSollevaIncaricoChange.emit(); }

}
