import { HttpPptrService } from './http-pptr.service';
import { TestBed } from '@angular/core/testing';

describe('HttpPptrService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpPptrService = TestBed.get(HttpPptrService);
    expect(service).toBeTruthy();
  });
});
