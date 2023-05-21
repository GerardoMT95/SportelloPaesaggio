import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUlterioreDocumentazioneComponent } from './form-ulteriore-documentazione.component';

describe('FormUlterioreDocumentazioneComponent', () => {
  let component: FormUlterioreDocumentazioneComponent;
  let fixture: ComponentFixture<FormUlterioreDocumentazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormUlterioreDocumentazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormUlterioreDocumentazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
