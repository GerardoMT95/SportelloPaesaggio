import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComunicazioniRicercaComponent } from './comunicazioni-ricerca.component';

describe('ComunicazioniRicercaComponent', () => {
  let component: ComunicazioniRicercaComponent;
  let fixture: ComponentFixture<ComunicazioniRicercaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComunicazioniRicercaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComunicazioniRicercaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
