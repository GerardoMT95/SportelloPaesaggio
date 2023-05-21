import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcedureEdilizeComponent } from './procedure-edilize.component';

describe('ProcedureEdilizeComponent', () => {
  let component: ProcedureEdilizeComponent;
  let fixture: ComponentFixture<ProcedureEdilizeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcedureEdilizeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcedureEdilizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
