import { TestBed } from '@angular/core/testing';

import { GestioneIstanzaComunicazioniService } from './gestione-istanza-comunicazioni.service';

describe('GestioneIstanzaComunicazioniService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GestioneIstanzaComunicazioniService = TestBed.get(GestioneIstanzaComunicazioniService);
    expect(service).toBeTruthy();
  });
});
