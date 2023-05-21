import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { LocalSessionServiceService } from './../../../../shared/services/local-session-service.service';

@Injectable()
export class DataService /* implements OnDestroy */
{
  private PRATIA_CONST = "pratica-details";
  private KEY_HAS_PROTOCOLLAZIONE = "has-protocollazione";

  private fascicolo$: BehaviorSubject<Fascicolo> = new BehaviorSubject<Fascicolo>(null);
  private status$: BehaviorSubject<StatoEnum>    = new BehaviorSubject<StatoEnum>(null);
  private subjectBuildingTable: Subject<EventoAppendRiga> = new Subject<EventoAppendRiga>();
  
  constructor(private lss: LocalSessionServiceService) { }
  /* ngOnDestroy(): void 
  {
    console.log("qui svuoto"); 
    this.lss.removeValue(this.PRATIA_CONST);
  } */

  get idPratica(): string { return this.fascicolo ? this.fascicolo.id : null; }
  get codicePratica(): string { return this.fascicolo ? this.fascicolo.codicePraticaAppptr : null; }

  set status(stato: StatoEnum) 
  { 
    let p = this.fascicolo;
    p.attivitaDaEspletare = stato;
    this.fascicolo = p; 
  }
  get status(): StatoEnum { return this.fascicolo ? this.fascicolo.attivitaDaEspletare : null; }
  get statusObservable(): Observable<StatoEnum> { return this.status$.asObservable(); } 

  set hasProtocollazione(bool: boolean) {
    this.lss.storeValue(this.KEY_HAS_PROTOCOLLAZIONE, bool);
  }
  get hasProtocollazione(): boolean{
    return this.lss.containsValue(this.KEY_HAS_PROTOCOLLAZIONE) ? this.lss.getValue(this.KEY_HAS_PROTOCOLLAZIONE) : false;
  }

  set fascicolo(fascicolo: Fascicolo) 
  { 
    if(fascicolo)
    {
      this.lss.storeValue(this.PRATIA_CONST, fascicolo);
      this.fascicolo$.next(fascicolo);
      this.status$.next(fascicolo ? fascicolo.attivitaDaEspletare : null);
    }
    else
      this.lss.removeValue(this.PRATIA_CONST);
  }
  get fascicolo(): Fascicolo { return this.lss.containsValue(this.PRATIA_CONST) ? this.lss.getValue(this.PRATIA_CONST) : null; }
  get fascicoloObservable(): Observable<Fascicolo> { return this.fascicolo$.asObservable(); }

  get subjBuildingTable$(): Subject<EventoAppendRiga> { return this.subjectBuildingTable; }

}

export interface EventoAppendRiga
{
  tabellaRef: string;
  riga: any; //corrisponde alla riga da appendere
}
