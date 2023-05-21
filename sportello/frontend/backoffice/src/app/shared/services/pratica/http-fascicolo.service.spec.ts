import { TestBed } from '@angular/core/testing';

import { FascicoloService } from './http-fascicolo.service';

describe('FascicoloService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FascicoloService = TestBed.get(FascicoloService);
    expect(service).toBeTruthy();
  });
});
