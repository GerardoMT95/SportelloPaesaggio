import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-genera-stampa-domanda',
  templateUrl: './genera-stampa-domanda.component.html',
  styleUrls: ['./genera-stampa-domanda.component.scss']
})
export class GeneraStampaDomandaComponent
{
  @Input("data") descrizione: any[] = [];
  @Input("title") title: string = "DETAILS.GENERATE_PRINT";
  @Output("download") downloadEvent: EventEmitter<"ISTANZA"|"SCHEDA"> = new EventEmitter<"ISTANZA"|"SCHEDA">();

  constructor() { }

  public download(descrizione: "ISTANZA"|"SCHEDA"): void { this.downloadEvent.emit(descrizione); }

}
