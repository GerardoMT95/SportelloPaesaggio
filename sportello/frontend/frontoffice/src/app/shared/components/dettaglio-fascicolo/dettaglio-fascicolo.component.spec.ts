import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioFascicoloComponent } from './dettaglio-fascicolo.component';

describe('DettaglioFascicoloComponent', () => {
  let component: DettaglioFascicoloComponent;
  let fixture: ComponentFixture<DettaglioFascicoloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioFascicoloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioFascicoloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
