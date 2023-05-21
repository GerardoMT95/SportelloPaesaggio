import { TestBed } from '@angular/core/testing';

import { ProvvedimentoFinaleService } from './provvedimento-finale.service';

describe('ProvvedimentoFinaleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProvvedimentoFinaleService = TestBed.get(ProvvedimentoFinaleService);
    expect(service).toBeTruthy();
  });
});
