import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentazioneRicercaComponent } from './documentazione-ricerca.component';

describe('DocumentazioneRicercaComponent', () => {
  let component: DocumentazioneRicercaComponent;
  let fixture: ComponentFixture<DocumentazioneRicercaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentazioneRicercaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentazioneRicercaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
