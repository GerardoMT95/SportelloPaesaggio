import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurazioneTipiProcedimento } from './configurazione-tipi-procedimento.component';

describe('ConfigurazioneTipiProcedimento', () => {
  let component: ConfigurazioneTipiProcedimento;
  let fixture: ComponentFixture<ConfigurazioneTipiProcedimento>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurazioneTipiProcedimento ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurazioneTipiProcedimento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
