import { TestBed } from '@angular/core/testing';

import { HttpAllegatoService } from './http-allegato.service';

describe('HttpAllegatoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpAllegatoService = TestBed.get(HttpAllegatoService);
    expect(service).toBeTruthy();
  });
});
