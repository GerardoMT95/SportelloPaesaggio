import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AltroTitolareComponent } from './altro-titolare.component';

describe('AltroTitolareComponent', () => {
  let component: AltroTitolareComponent;
  let fixture: ComponentFixture<AltroTitolareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AltroTitolareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AltroTitolareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
