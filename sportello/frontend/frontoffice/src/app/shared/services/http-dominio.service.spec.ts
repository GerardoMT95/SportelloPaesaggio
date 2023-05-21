import { TestBed } from '@angular/core/testing';

import { HttpDominioService } from './http-dominio.service';

describe('HttpDominioService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpDominioService = TestBed.get(HttpDominioService);
    expect(service).toBeTruthy();
  });
});
