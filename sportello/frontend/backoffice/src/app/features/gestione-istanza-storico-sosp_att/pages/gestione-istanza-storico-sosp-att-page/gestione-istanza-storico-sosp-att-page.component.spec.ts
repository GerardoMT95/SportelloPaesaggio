import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaStoricoSospAttPageComponent } from './gestione-istanza-storico-sosp-att-page.component';

describe('GestioneIstanzaStoricoSospAttPageComponent', () => {
  let component: GestioneIstanzaStoricoSospAttPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaStoricoSospAttPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaStoricoSospAttPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaStoricoSospAttPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
