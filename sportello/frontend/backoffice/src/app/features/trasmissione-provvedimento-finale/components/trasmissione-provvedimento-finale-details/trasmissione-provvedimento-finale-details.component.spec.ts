import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrasmissioneProvvedimentoFinaleDetailsComponent } from './trasmissione-provvedimento-finale-details.component';

describe('TrasmissioneProvvedimentoFinaleDetailsComponent', () => {
  let component: TrasmissioneProvvedimentoFinaleDetailsComponent;
  let fixture: ComponentFixture<TrasmissioneProvvedimentoFinaleDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrasmissioneProvvedimentoFinaleDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrasmissioneProvvedimentoFinaleDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
