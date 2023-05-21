import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccettaPrivacyComponent } from './accetta-privacy.component';

describe('AccettaPrivacyComponent', () => {
  let component: AccettaPrivacyComponent;
  let fixture: ComponentFixture<AccettaPrivacyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccettaPrivacyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccettaPrivacyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
