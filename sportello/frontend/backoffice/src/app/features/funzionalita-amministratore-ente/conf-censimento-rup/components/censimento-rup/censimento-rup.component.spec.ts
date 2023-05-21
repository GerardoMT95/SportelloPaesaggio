/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CensimentoRupComponent } from './censimento-rup.component';

describe('CensimentoRupComponent', () => {
  let component: CensimentoRupComponent;
  let fixture: ComponentFixture<CensimentoRupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CensimentoRupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CensimentoRupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
