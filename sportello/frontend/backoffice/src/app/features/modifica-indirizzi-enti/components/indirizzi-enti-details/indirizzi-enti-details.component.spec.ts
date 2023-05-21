import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndirizziEntiDetailsComponent } from './indirizzi-enti-details.component';

describe('IndirizziEntiDetailsComponent', () => {
  let component: IndirizziEntiDetailsComponent;
  let fixture: ComponentFixture<IndirizziEntiDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndirizziEntiDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndirizziEntiDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
