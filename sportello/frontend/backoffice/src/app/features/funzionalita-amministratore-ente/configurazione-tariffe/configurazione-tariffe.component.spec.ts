import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {AdminConfigurazioneTariffeComponent} from './configurazione-tariffe.component';

describe('AdminConfigurazioneTariffeComponent', () => {
  let component: AdminConfigurazioneTariffeComponent;
  let fixture: ComponentFixture<AdminConfigurazioneTariffeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminConfigurazioneTariffeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminConfigurazioneTariffeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
