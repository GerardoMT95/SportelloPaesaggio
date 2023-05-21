import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentazioneAmministrativaComponent } from './documentazione-amministrativa.component';

describe('DocumentazioneAmministrativaComponent', () => {
  let component: DocumentazioneAmministrativaComponent;
  let fixture: ComponentFixture<DocumentazioneAmministrativaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentazioneAmministrativaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentazioneAmministrativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
