import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventualiProcedimentiComponent } from './eventuali-procedimenti.component';

describe('EventualiProcedimentiComponent', () => {
  let component: EventualiProcedimentiComponent;
  let fixture: ComponentFixture<EventualiProcedimentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventualiProcedimentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventualiProcedimentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
