import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentazioneTecnicaComponent } from './documentazione-tecnica.component';

describe('DocumentazioneTecnicaComponent', () => {
  let component: DocumentazioneTecnicaComponent;
  let fixture: ComponentFixture<DocumentazioneTecnicaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentazioneTecnicaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentazioneTecnicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
