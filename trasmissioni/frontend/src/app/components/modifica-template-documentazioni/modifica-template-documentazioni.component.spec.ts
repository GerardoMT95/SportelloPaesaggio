import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaTemplateDocumentazioniComponent } from './modifica-template-documentazioni.component';

describe('ModificaTemplateDocumentazioniComponent', () => {
  let component: ModificaTemplateDocumentazioniComponent;
  let fixture: ComponentFixture<ModificaTemplateDocumentazioniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModificaTemplateDocumentazioniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificaTemplateDocumentazioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
