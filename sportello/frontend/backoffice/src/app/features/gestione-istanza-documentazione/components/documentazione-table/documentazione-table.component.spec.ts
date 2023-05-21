import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentazioneTableComponent } from './documentazione-table.component';

describe('DocumentazioneTableComponent', () => {
  let component: DocumentazioneTableComponent;
  let fixture: ComponentFixture<DocumentazioneTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentazioneTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentazioneTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
