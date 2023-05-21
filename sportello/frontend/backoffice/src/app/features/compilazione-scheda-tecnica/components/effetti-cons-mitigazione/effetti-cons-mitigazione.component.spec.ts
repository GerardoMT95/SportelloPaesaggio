import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EffettiConsMitigazioneComponent } from './effetti-cons-mitigazione.component';

describe('EffettiConsMitigazioneComponent', () => {
  let component: EffettiConsMitigazioneComponent;
  let fixture: ComponentFixture<EffettiConsMitigazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EffettiConsMitigazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EffettiConsMitigazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
