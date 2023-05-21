/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { RichiestePostTrasmissioneComponent } from './richieste-post-trasmissione.component';

describe('RichiestePostTrasmissioneComponent', () => {
  let component: RichiestePostTrasmissioneComponent;
  let fixture: ComponentFixture<RichiestePostTrasmissioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RichiestePostTrasmissioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RichiestePostTrasmissioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
