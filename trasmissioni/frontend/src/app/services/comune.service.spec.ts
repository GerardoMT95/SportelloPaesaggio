/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ComuneService } from './comune.service';

describe('Service: Comune', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ComuneService]
    });
  });

  it('should ...', inject([ComuneService], (service: ComuneService) => {
    expect(service).toBeTruthy();
  }));
});
