import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiepilogoFascicoliComponent } from './riepilogo-fascicoli.component';

describe('RiepilogoFascicoliComponent', () => {
  let component: RiepilogoFascicoliComponent;
  let fixture: ComponentFixture<RiepilogoFascicoliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiepilogoFascicoliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiepilogoFascicoliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
