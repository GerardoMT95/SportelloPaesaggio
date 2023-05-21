import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaCommissioneLocalePageComponent } from './nuova-commissione-locale-page.component';

describe('NuovaCommissioneLocalePageComponent', () => {
  let component: NuovaCommissioneLocalePageComponent;
  let fixture: ComponentFixture<NuovaCommissioneLocalePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaCommissioneLocalePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaCommissioneLocalePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
