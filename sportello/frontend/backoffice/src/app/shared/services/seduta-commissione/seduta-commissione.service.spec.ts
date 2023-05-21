import { TestBed } from '@angular/core/testing';

import { SedutaCommissioneService } from './seduta-commissione.service';

describe('SedutaCommissioneService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SedutaCommissioneService = TestBed.get(SedutaCommissioneService);
    expect(service).toBeTruthy();
  });
});
