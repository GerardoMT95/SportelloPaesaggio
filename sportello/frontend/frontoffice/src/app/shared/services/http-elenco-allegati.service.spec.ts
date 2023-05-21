import { TestBed } from '@angular/core/testing';

import { HttpElencoAllegatiService } from './http-elenco-allegati.service';

describe('HttpElencoAllegatiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpElencoAllegatiService = TestBed.get(HttpElencoAllegatiService);
    expect(service).toBeTruthy();
  });
});
