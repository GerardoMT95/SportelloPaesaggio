import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaPassaPageComponent } from './gestione-istanza-passa-page.component';

describe('GestioneIstanzaPassaPageComponent', () => {
  let component: GestioneIstanzaPassaPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaPassaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaPassaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaPassaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
