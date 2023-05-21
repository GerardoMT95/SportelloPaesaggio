import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Allegato, StatoEnum } from 'src/app/shared/models/models';

@Component({
  selector: 'app-istanza-presentata',
  templateUrl: './istanza-presentata.component.html',
  styleUrls: ['./istanza-presentata.component.scss']
})
export class IstanzaPresentataComponent implements OnInit 
{
  @Input("stato") statofascicolo: StatoEnum;
  @Input() generatedDocuments:Allegato[];

  @Output("download") downloadEvt: EventEmitter<Allegato> = new EventEmitter<Allegato>();

  constructor() { }

  ngOnInit() 
  {  

  }

  public download(event: Allegato): void { this.downloadEvt.emit(event); }
  get statoEnum(): any { return StatoEnum; }

}
