import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndirizziEntiSearchComponent } from './indirizzi-enti-search.component';

describe('IndirizziEntiSearchComponent', () => {
  let component: IndirizziEntiSearchComponent;
  let fixture: ComponentFixture<IndirizziEntiSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndirizziEntiSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndirizziEntiSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
