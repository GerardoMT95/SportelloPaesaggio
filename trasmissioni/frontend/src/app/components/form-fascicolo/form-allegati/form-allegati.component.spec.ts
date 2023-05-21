/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FormAllegatiComponent } from './form-allegati.component';

describe('FormAllegatiComponent', () => {
  let component: FormAllegatiComponent;
  let fixture: ComponentFixture<FormAllegatiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormAllegatiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormAllegatiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
