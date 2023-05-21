import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrasmissioneProvvedimentoFinalePageComponent } from './trasmissione-provvedimento-finale-page.component';

describe('TrasmissioneProvvedimentoFinalePageComponent', () => {
  let component: TrasmissioneProvvedimentoFinalePageComponent;
  let fixture: ComponentFixture<TrasmissioneProvvedimentoFinalePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrasmissioneProvvedimentoFinalePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrasmissioneProvvedimentoFinalePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
