import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzePageComponent } from './gestione-istanze-page.component';

describe('GestioneIstanzeComponent', () => {
  let component: GestioneIstanzePageComponent;
  let fixture: ComponentFixture<GestioneIstanzePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
