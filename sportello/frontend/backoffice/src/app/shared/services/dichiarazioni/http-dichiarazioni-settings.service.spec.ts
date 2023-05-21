import { TestBed } from '@angular/core/testing';

import { HttpDichiarazioniSettingsService } from './http-dichiarazioni-settings.service';

describe('HttpDichiarazioniSettingsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpDichiarazioniSettingsService = TestBed.get(HttpDichiarazioniSettingsService);
    expect(service).toBeTruthy();
  });
});
