import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaParerePageComponent } from './gestione-istanza-parere-page.component';

describe('GestioneIstanzaParerePageComponent', () => {
  let component: GestioneIstanzaParerePageComponent;
  let fixture: ComponentFixture<GestioneIstanzaParerePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaParerePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaParerePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
