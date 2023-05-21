import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaProtocollazionePageComponent } from './gestione-istanza-protocollazione-page.component';

describe('GestioneIstanzaProtocollazionePageComponent', () => {
  let component: GestioneIstanzaProtocollazionePageComponent;
  let fixture: ComponentFixture<GestioneIstanzaProtocollazionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaProtocollazionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaProtocollazionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
