import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateComunicazioniComponent } from './template-comunicazioni.component';

describe('TemplateComunicazioniComponent', () => {
  let component: TemplateComunicazioniComponent;
  let fixture: ComponentFixture<TemplateComunicazioniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateComunicazioniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateComunicazioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
