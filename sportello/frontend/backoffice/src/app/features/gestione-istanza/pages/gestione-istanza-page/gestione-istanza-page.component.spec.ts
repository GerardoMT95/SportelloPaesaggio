import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaPageComponent } from './gestione-istanza-page.component';

describe('GestioneIstanzaPageComponent', () => {
  let component: GestioneIstanzaPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
