import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaComunicazioniPageComponent } from './gestione-istanza-comunicazioni-page.component';

describe('GestioneIstanzaComunicazioniPageComponent', () => {
  let component: GestioneIstanzaComunicazioniPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaComunicazioniPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaComunicazioniPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaComunicazioniPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
