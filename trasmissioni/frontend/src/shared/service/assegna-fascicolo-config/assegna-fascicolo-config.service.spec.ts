import { TestBed } from '@angular/core/testing';

import { AssegnaFascicoloConfigService } from './assegna-fascicolo-config.service';

describe('AssegnaFascicoloService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AssegnaFascicoloConfigService = TestBed.get(AssegnaFascicoloConfigService);
    expect(service).toBeTruthy();
  });
});
