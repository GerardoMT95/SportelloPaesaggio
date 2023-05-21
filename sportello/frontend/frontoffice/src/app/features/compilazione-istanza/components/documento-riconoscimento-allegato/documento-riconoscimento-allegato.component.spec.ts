/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DocumentoRiconoscimentoAllegatoComponent } from './documento-riconoscimento-allegato.component';

describe('DocumentoRiconoscimentoAllegatoComponent', () => {
  let component: DocumentoRiconoscimentoAllegatoComponent;
  let fixture: ComponentFixture<DocumentoRiconoscimentoAllegatoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentoRiconoscimentoAllegatoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentoRiconoscimentoAllegatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
