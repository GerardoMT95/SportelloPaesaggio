/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FormUdComponent } from './form-ud.component';

describe('FormUdComponent', () => {
  let component: FormUdComponent;
  let fixture: ComponentFixture<FormUdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormUdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormUdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
