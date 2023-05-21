import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioCommissioneComponent } from './dettaglio-commissione.component';

describe('DettaglioCommissioneComponent', () => {
  let component: DettaglioCommissioneComponent;
  let fixture: ComponentFixture<DettaglioCommissioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioCommissioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioCommissioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
