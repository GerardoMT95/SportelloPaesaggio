import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RowsNumberHandlerComponent } from './rows-number-handler.component';

describe('RowsNumberHandlerComponent', () => {
  let component: RowsNumberHandlerComponent;
  let fixture: ComponentFixture<RowsNumberHandlerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RowsNumberHandlerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RowsNumberHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
