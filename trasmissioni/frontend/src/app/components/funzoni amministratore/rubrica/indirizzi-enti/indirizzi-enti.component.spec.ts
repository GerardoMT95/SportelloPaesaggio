import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndirizziEntiComponent } from './indirizzi-enti.component';

describe('IndirizziEntiComponent', () => {
  let component: IndirizziEntiComponent;
  let fixture: ComponentFixture<IndirizziEntiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndirizziEntiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndirizziEntiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
