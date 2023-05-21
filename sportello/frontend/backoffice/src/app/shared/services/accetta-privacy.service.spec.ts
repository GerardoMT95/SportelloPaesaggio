import { TestBed, inject } from '@angular/core/testing';

import { AccettaPrivacyService } from './accetta-privacy.service';

describe('AccettaPrivacyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccettaPrivacyService]
    });
  });

  it('should be created', inject([AccettaPrivacyService], (service: AccettaPrivacyService) => {
    expect(service).toBeTruthy();
  }));
});
