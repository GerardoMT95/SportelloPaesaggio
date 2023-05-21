/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { LocalSessionServiceService } from './local-session-service.service';

describe('Service: LocalSessionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LocalSessionServiceService]
    });
  });

  it('should ...', inject([LocalSessionServiceService], (service: LocalSessionServiceService) => {
    expect(service).toBeTruthy();
  }));
});
