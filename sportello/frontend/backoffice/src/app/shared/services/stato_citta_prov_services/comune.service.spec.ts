import { TestBed } from '@angular/core/testing';

import { ComuneService } from './comune.service';

describe('ComuneService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ComuneService = TestBed.get(ComuneService);
    expect(service).toBeTruthy();
  });
});
