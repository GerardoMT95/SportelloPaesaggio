import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaTestiComunicazioniPageComponent } from './modifica-testi-comunicazioni-page.component';

describe('ModificaTestiComunicazioniPageComponent', () => {
  let component: ModificaTestiComunicazioniPageComponent;
  let fixture: ComponentFixture<ModificaTestiComunicazioniPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModificaTestiComunicazioniPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificaTestiComunicazioniPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
