import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionePECComponent } from './gestione-pec.component';

describe('GestionePECComponent', () => {
  let component: GestionePECComponent;
  let fixture: ComponentFixture<GestionePECComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionePECComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionePECComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
