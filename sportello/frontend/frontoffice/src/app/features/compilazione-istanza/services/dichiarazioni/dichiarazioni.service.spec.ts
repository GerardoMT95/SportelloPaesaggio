import { TestBed } from '@angular/core/testing';

import { DichiarazioniService } from './dichiarazioni.service';

describe('DichiarazioniService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DichiarazioniService = TestBed.get(DichiarazioniService);
    expect(service).toBeTruthy();
  });
});
