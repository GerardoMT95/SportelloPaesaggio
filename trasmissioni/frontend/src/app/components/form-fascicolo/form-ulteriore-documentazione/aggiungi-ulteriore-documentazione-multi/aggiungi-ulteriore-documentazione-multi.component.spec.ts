/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AggiungiUlterioreDocumentazioneMultiComponent } from './aggiungi-ulteriore-documentazione-multi.component';

describe('AggiungiUlterioreDocumentazioneMultiComponent', () => {
  let component: AggiungiUlterioreDocumentazioneMultiComponent;
  let fixture: ComponentFixture<AggiungiUlterioreDocumentazioneMultiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AggiungiUlterioreDocumentazioneMultiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiungiUlterioreDocumentazioneMultiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
