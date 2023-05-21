import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PptrContestiPaesaggisticiComponent } from './pptr-contesti-paesaggistici.component';

describe('PptrContestiPaesaggisticiComponent', () => {
  let component: PptrContestiPaesaggisticiComponent;
  let fixture: ComponentFixture<PptrContestiPaesaggisticiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PptrContestiPaesaggisticiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PptrContestiPaesaggisticiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
