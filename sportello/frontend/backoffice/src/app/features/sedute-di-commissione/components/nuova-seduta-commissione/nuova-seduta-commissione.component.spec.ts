import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaSedutaCommissioneComponent } from './nuova-seduta-commissione.component';

describe('NuovaSedutaCommissioneComponent', () => {
  let component: NuovaSedutaCommissioneComponent;
  let fixture: ComponentFixture<NuovaSedutaCommissioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaSedutaCommissioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaSedutaCommissioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
