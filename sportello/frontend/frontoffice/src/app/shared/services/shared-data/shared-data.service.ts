import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Fascicolo } from '../../models/models';

@Injectable({ providedIn: 'root' })
export class SharedDataService 
{
  private subject: BehaviorSubject<Fascicolo> = new BehaviorSubject<Fascicolo>(null);
  //subject utilizzato per appendere nuove righe a tabelle gestite dal BuildingTableComponent
  private subjectBuildingTable: Subject<EventoAppendRiga> = new Subject<EventoAppendRiga>();

  get fascicoloObservable(): Observable<Fascicolo> { return this.subject.asObservable(); }

  get fascicolo(): Fascicolo { return this.subject.getValue(); }
  set fascicolo(fascicolo: Fascicolo) { this.subject.next(fascicolo); }

  get codicePratica(): string { return this.fascicolo ? this.fascicolo.codicePraticaAppptr : null; }
  get idPratica(): string { return this.fascicolo ? this.fascicolo.id : null; }
  get subjBuildingTable$():Subject<EventoAppendRiga>{return this.subjectBuildingTable;}
  
}
/**
 * oggetto utilizzato
 */
export interface EventoAppendRiga{
  tabellaRef:string;
  riga:any; //corrisponde alla riga da appendere
}

