import { TestBed } from '@angular/core/testing';

import { IntegrazioneDocumentaleService } from './integrazione-documentale.service';

describe('IntegrazioneDocumentaleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IntegrazioneDocumentaleService = TestBed.get(IntegrazioneDocumentaleService);
    expect(service).toBeTruthy();
  });
});
