import { TestBed } from '@angular/core/testing';

import { GestioneGruppoService } from './gestione-gruppo.service';

describe('GestioneGruppoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GestioneGruppoService = TestBed.get(GestioneGruppoService);
    expect(service).toBeTruthy();
  });
});
