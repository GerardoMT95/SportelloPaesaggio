import { TestBed } from '@angular/core/testing';

import { SchedaTecnicaService } from './scheda-tecnica.service';

describe('SchedaTecnicaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SchedaTecnicaService = TestBed.get(SchedaTecnicaService);
    expect(service).toBeTruthy();
  });
});
