/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ConfigurazioneSportelloAmbienteComponent } from './configurazione-sportello-ambiente.component';

describe('ConfigurazioneSportelloAmbienteComponent', () => {
  let component: ConfigurazioneSportelloAmbienteComponent;
  let fixture: ComponentFixture<ConfigurazioneSportelloAmbienteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurazioneSportelloAmbienteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurazioneSportelloAmbienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
