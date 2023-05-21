import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndirizziEntiTableComponent } from './indirizzi-enti-table.component';

describe('IndirizziEntiTableComponent', () => {
  let component: IndirizziEntiTableComponent;
  let fixture: ComponentFixture<IndirizziEntiTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndirizziEntiTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndirizziEntiTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
