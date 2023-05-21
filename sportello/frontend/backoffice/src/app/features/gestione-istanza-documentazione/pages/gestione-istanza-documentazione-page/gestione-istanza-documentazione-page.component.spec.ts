import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaDocumentazionePageComponent } from './gestione-istanza-documentazione-page.component';

describe('GestioneIstanzaDocumentazionePageComponent', () => {
  let component: GestioneIstanzaDocumentazionePageComponent;
  let fixture: ComponentFixture<GestioneIstanzaDocumentazionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaDocumentazionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaDocumentazionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
