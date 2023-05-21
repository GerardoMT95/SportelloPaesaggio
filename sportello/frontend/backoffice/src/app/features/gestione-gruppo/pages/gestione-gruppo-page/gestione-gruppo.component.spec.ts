import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneGruppoComponent } from './gestione-gruppo.component';

describe('GestioneGruppoComponent', () => {
  let component: GestioneGruppoComponent;
  let fixture: ComponentFixture<GestioneGruppoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneGruppoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneGruppoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
