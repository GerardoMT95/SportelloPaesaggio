import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentazioneAllegaComponent } from './documentazione-allega.component';

describe('DocumentazioneAllegaComponent', () => {
  let component: DocumentazioneAllegaComponent;
  let fixture: ComponentFixture<DocumentazioneAllegaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentazioneAllegaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentazioneAllegaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
