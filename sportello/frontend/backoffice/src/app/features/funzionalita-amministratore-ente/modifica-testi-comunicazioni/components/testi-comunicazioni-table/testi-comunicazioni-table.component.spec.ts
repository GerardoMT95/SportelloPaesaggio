import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestiComunicazioniTableComponent } from './testi-comunicazioni-table.component';

describe('TestiComunicazioniTableComponent', () => {
  let component: TestiComunicazioniTableComponent;
  let fixture: ComponentFixture<TestiComunicazioniTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestiComunicazioniTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestiComunicazioniTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
