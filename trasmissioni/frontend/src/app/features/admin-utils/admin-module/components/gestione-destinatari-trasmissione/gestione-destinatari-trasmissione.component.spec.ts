import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneDestinatariTrasmissioneComponent } from './gestione-destinatari-trasmissione.component';

describe('GestioneDestinatariTrasmissioneComponent', () => {
  let component: GestioneDestinatariTrasmissioneComponent;
  let fixture: ComponentFixture<GestioneDestinatariTrasmissioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneDestinatariTrasmissioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneDestinatariTrasmissioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
