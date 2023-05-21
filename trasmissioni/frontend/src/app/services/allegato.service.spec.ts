import { TestBed, inject } from '@angular/core/testing';

import { AllegatoService } from './allegato.service';

describe('AllegatoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AllegatoService]
    });
  });

  it('should be created', inject([AllegatoService], (service: AllegatoService) => {
    expect(service).toBeTruthy();
  }));
});
