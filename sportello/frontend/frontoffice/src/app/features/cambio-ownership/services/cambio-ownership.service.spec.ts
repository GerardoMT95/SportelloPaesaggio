import { TestBed } from '@angular/core/testing';

import { CambioOwnershipService } from './cambio-ownership.service';

describe('CambioOwnershipService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CambioOwnershipService = TestBed.get(CambioOwnershipService);
    expect(service).toBeTruthy();
  });
});
