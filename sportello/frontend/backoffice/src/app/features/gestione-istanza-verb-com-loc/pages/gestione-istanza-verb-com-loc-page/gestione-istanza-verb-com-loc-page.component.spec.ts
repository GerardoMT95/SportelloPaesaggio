import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaVerbComLocPageComponent } from './gestione-istanza-verb-com-loc-page.component';

describe('GestioneIstanzaVerbComLocPageComponent', () => {
  let component: GestioneIstanzaVerbComLocPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaVerbComLocPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaVerbComLocPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaVerbComLocPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
