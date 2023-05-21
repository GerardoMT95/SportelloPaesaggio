import { Injectable } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable, ReplaySubject, BehaviorSubject, Subject } from 'rxjs';
import { EnteDTO } from './../components/model/entity/ente.models';
import { Fascicolo } from '../components/model/fascicolo.models';

@Injectable({
  providedIn: 'root'
})
export class DataService 
{
  private comuniSubject: ReplaySubject<SelectItem[]> = new ReplaySubject<SelectItem[]>();
  private provinceSubject: ReplaySubject<SelectItem[]> = new ReplaySubject<SelectItem[]>();
  private entiSubject: ReplaySubject<EnteDTO[]> = new ReplaySubject<EnteDTO[]>();
  //continua...

  private subject: BehaviorSubject<Fascicolo> = new BehaviorSubject<Fascicolo>(null);
  //subject utilizzato per appendere nuove righe a tabelle gestite dal BuildingTableComponent
  private subjectBuildingTable: Subject<EventoAppendRiga> = new Subject<EventoAppendRiga>();

  get fascicoloObservable(): Observable<Fascicolo> { return this.subject.asObservable(); }

  get fascicolo(): Fascicolo { return this.subject.getValue(); }
  set fascicolo(fascicolo: Fascicolo) { this.subject.next(fascicolo); }

  get codicePratica(): string { return this.fascicolo ? this.fascicolo.codice : null; }
  get idPratica(): string { return this.fascicolo ? this.fascicolo.id : null; }
  get subjBuildingTable$():Subject<EventoAppendRiga>{return this.subjectBuildingTable;}
  
  constructor() { }

  public comuniNext(comuni: SelectItem[]): void { this.comuniSubject.next(comuni); }
  public provinceNext(province: SelectItem[]): void { this.provinceSubject.next(province); }
  public entiNext(enti: EnteDTO[]): void { this.entiSubject.next(enti); }
  public getComuniObs(): Observable<SelectItem[]> { return this.comuniSubject.asObservable(); }
  public getProvinceObs(): Observable<SelectItem[]> { return this.provinceSubject.asObservable(); }


}
/**
 * oggetto utilizzato
 */
export interface EventoAppendRiga{
  tabellaRef:string;
  riga:any; //corrisponde alla riga da appendere
}
