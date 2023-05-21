import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungiUlterioreDocumentazioneComponent } from './aggiungi-ulteriore-documentazione.component';

describe('AggiungiUlterioreDocumentazioneComponent', () => {
  let component: AggiungiUlterioreDocumentazioneComponent;
  let fixture: ComponentFixture<AggiungiUlterioreDocumentazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AggiungiUlterioreDocumentazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiungiUlterioreDocumentazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
