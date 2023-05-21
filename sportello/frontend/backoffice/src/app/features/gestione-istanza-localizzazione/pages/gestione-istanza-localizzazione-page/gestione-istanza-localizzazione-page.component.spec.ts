import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaLocalizzazionePageComponent } from './gestione-istanza-localizzazione-page.component';

describe('GestioneIstanzaLocalizzazionePageComponent', () => {
  let component: GestioneIstanzaLocalizzazionePageComponent;
  let fixture: ComponentFixture<GestioneIstanzaLocalizzazionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaLocalizzazionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaLocalizzazionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
