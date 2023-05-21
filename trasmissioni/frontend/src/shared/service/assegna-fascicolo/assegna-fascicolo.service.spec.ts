import { TestBed } from '@angular/core/testing';

import { AssegnaFascicoloService } from './assegna-fascicolo.service';

describe('AssegnaFascicoloService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AssegnaFascicoloService = TestBed.get(AssegnaFascicoloService);
    expect(service).toBeTruthy();
  });
});
