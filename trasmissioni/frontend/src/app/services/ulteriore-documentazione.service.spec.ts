import { TestBed } from '@angular/core/testing';

import { UlterioreDocumentazioneService } from './ulteriore-documentazione.service';

describe('UlterioreDocumentazioneService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UlterioreDocumentazioneService = TestBed.get(UlterioreDocumentazioneService);
    expect(service).toBeTruthy();
  });
});
