import { TestBed } from '@angular/core/testing';

import { GestioneIstanzaDocumentazioneService } from './gestione-istanza-documentazione.service';

describe('GestioneIstanzaDocumentazioneService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GestioneIstanzaDocumentazioneService = TestBed.get(GestioneIstanzaDocumentazioneService);
    expect(service).toBeTruthy();
  });
});
