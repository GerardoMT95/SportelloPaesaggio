import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableNoFilterComponent } from './table-no-filter.component';

describe('TableNoFilterComponent', () => {
  let component: TableNoFilterComponent;
  let fixture: ComponentFixture<TableNoFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableNoFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableNoFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
