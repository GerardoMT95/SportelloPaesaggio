import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { LocalizzazioneValues } from './../../models/comp-istanza.model';

@Injectable()
export class LocalizzazioneService
{
    private subject$: BehaviorSubject<LocalizzazioneValues> = new BehaviorSubject<LocalizzazioneValues>(this.init());

    get localizzazioneObs(): Observable<LocalizzazioneValues> { return this.subject$.asObservable(); }

    get localizzazione(): LocalizzazioneValues { return this.subject$.getValue(); }
    set localizzazione(value: LocalizzazioneValues) { this.subject$.next(value); }
    
    /**utilizzato per segnalare il salvataggio immediato del fascicolo in caso di cambio tipoSelezioneLocalizzazione */
    public salvaFascicolo$: Subject<any> = new Subject<any>();

    private init(): LocalizzazioneValues { return {comuneSelezionato: false, bpParchiRiserve: false, bpImmobileAreeInteresse: false}};
}