import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneStatoPraticaComponent } from './gestione-stato-pratica.component';

describe('GestioneStatoPraticaComponent', () => {
  let component: GestioneStatoPraticaComponent;
  let fixture: ComponentFixture<GestioneStatoPraticaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneStatoPraticaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneStatoPraticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
