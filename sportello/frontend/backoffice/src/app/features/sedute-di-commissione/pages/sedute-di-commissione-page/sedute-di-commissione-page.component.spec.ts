import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeduteDiCommissionePageComponent } from './sedute-di-commissione-page.component';

describe('SeduteDiCommissionePageComponent', () => {
  let component: SeduteDiCommissionePageComponent;
  let fixture: ComponentFixture<SeduteDiCommissionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeduteDiCommissionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeduteDiCommissionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
