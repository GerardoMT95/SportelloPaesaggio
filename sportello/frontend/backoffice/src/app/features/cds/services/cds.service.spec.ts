import { TestBed } from '@angular/core/testing';

import { CdsService } from './cds.service';

describe('CdsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CdsService = TestBed.get(CdsService);
    expect(service).toBeTruthy();
  });
});
