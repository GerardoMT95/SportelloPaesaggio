/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { GestioneConferenzaServiziPageComponent } from './gestione-conferenza-servizi-page.component';


describe('GestioneConferenzaServiziPageComponent', () => {
  let component: GestioneConferenzaServiziPageComponent;
  let fixture: ComponentFixture<GestioneConferenzaServiziPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneConferenzaServiziPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneConferenzaServiziPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
