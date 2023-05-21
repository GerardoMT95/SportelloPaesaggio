import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssegnazioneFascicoloPageComponent } from './assegnazione-fascicolo-page.component';

describe('AssegnazioneFascicoloPageComponent', () => {
  let component: AssegnazioneFascicoloPageComponent;
  let fixture: ComponentFixture<AssegnazioneFascicoloPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssegnazioneFascicoloPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssegnazioneFascicoloPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
