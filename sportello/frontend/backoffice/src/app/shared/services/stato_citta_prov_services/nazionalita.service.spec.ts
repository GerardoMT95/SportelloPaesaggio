import { TestBed } from '@angular/core/testing';

import { NazionalitaService } from './nazionalita.service';

describe('NazionalitaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NazionalitaService = TestBed.get(NazionalitaService);
    expect(service).toBeTruthy();
  });
});
