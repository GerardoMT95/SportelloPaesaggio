/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SezioniCatastaliComponent } from './sezioni-catastali.component';

describe('SezioniCatastaliComponent', () => {
  let component: SezioniCatastaliComponent;
  let fixture: ComponentFixture<SezioniCatastaliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SezioniCatastaliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SezioniCatastaliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
