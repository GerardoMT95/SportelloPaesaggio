import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SezioniComponent } from './sezioni.component';

describe('SezioniComponent', () => {
  let component: SezioniComponent;
  let fixture: ComponentFixture<SezioniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SezioniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SezioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
