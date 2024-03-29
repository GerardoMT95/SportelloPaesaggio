/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MailComposeComponent } from './mail-compose.component';

describe('MailComposeComponent', () => {
  let component: MailComposeComponent;
  let fixture: ComponentFixture<MailComposeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MailComposeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MailComposeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
