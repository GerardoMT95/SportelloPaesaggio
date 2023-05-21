/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FormAreeParchiComponent } from './form-aree-parchi.component';

describe('FormAreeParchiComponent', () => {
  let component: FormAreeParchiComponent;
  let fixture: ComponentFixture<FormAreeParchiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormAreeParchiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormAreeParchiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
