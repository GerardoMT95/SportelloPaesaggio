import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentoRiepilogoIntegrazioneComponent } from './documento-riepilogo-integrazione.component';

describe('DocumentoRiepilogoIntegrazioneComponent', () => {
  let component: DocumentoRiepilogoIntegrazioneComponent;
  let fixture: ComponentFixture<DocumentoRiepilogoIntegrazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentoRiepilogoIntegrazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentoRiepilogoIntegrazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
