import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaRelazioneEntePageComponent } from './gestione-istanza-relazione-ente-page.component';

describe('GestioneIstanzaRelazioneEntePageComponent', () => {
  let component: GestioneIstanzaRelazioneEntePageComponent;
  let fixture: ComponentFixture<GestioneIstanzaRelazioneEntePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaRelazioneEntePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaRelazioneEntePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
