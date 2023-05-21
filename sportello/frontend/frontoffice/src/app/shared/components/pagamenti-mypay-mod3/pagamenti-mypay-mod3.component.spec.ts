/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PagamentiMypayMod3Component } from './pagamenti-mypay-mod3.component';

describe('PagamentiMypayMod3Component', () => {
  let component: PagamentiMypayMod3Component;
  let fixture: ComponentFixture<PagamentiMypayMod3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PagamentiMypayMod3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PagamentiMypayMod3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
