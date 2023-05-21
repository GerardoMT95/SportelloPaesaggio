import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableCommissioneLocaleComponent } from './table-commissione-locale.component';

describe('TableCommissioneLocaleComponent', () => {
  let component: TableCommissioneLocaleComponent;
  let fixture: ComponentFixture<TableCommissioneLocaleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableCommissioneLocaleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableCommissioneLocaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
