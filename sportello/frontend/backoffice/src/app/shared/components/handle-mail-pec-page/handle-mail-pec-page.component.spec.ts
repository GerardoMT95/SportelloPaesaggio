/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { HandleMailPecPageComponent } from './handle-mail-pec-page.component';

describe('HandleMailPecPageComponent', () => {
  let component: HandleMailPecPageComponent;
  let fixture: ComponentFixture<HandleMailPecPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HandleMailPecPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HandleMailPecPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
