import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestiComunicazioniDetailsComponent } from './testi-comunicazioni-details.component';

describe('TestiComunicazioniDetailsComponent', () => {
  let component: TestiComunicazioniDetailsComponent;
  let fixture: ComponentFixture<TestiComunicazioniDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestiComunicazioniDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestiComunicazioniDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
