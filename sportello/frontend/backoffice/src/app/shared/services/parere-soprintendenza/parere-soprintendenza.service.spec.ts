import { TestBed } from '@angular/core/testing';

import { ParereSoprintendenzaService } from './parere-soprintendenza.service';

describe('ParereSoprintendenzaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ParereSoprintendenzaService = TestBed.get(ParereSoprintendenzaService);
    expect(service).toBeTruthy();
  });
});
