/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AutorizzazioniPaesaggisticheService } from './autorizzazioni-paesaggistiche.service';

describe('Service: AutorizzazioniPaesaggistiche', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AutorizzazioniPaesaggisticheService]
    });
  });

  it('should ...', inject([AutorizzazioniPaesaggisticheService], (service: AutorizzazioniPaesaggisticheService) => {
    expect(service).toBeTruthy();
  }));
});
